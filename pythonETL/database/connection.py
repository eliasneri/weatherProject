from pathlib import Path

import psycopg2
from config import settings

"""
connection on postgres
"""
def get_connection():
    return psycopg2.connect(
        host=settings.DB_HOST,
        port=settings.DB_PORT,
        dbname=settings.DB_NAME,
        user=settings.DB_USER,
        password=settings.DB_PASS
    )

"""
connection test
"""
def test_connection():
    print("⏳ - TESTANDO CONEXÃO COM BANCO DE DADOS !!!")
    try:
        conn = get_connection()
        cursor = conn.cursor()

        cursor.execute("SELECT version();")
        db_version = cursor.fetchone()

        print("🔌 - CONEXÃO BEM-SUCEDIDA !!!")
        print("📌 - VERSÃO DO POSTGRESQL: ", db_version[0])

        cursor.close()
        conn.close()
        return True

    except Exception as e:
        print("❌ - FALHA NA CONEXÃO COM O BANCO DE DADOS !!!")
        print("ERRO:", e)
        return False

"""
executing table creation with script
"""
def create_tables():
    try:
        print("⏳ - CRIANDO TABELAS NO BANCO DE DADOS !!!")
        sql_path = Path(__file__).resolve().parent.joinpath("sql", "create_tables.sql")

        if not sql_path.exists():
            print(f"❌ ❌ ❌ - ARQUIVO SQL NÃO ENCONTRADO !!!")
            return False

        conn = get_connection()
        with conn.cursor() as cursor:
            sql = sql_path.read_text(encoding="utf-8")
            cursor.execute(sql)
        conn.commit()

        print(f"✅ - SCRIPT EXECUTADO COM SUCESSO: {sql_path}")
        return True

    except Exception as e:
        print(f"❌ ❌ ❌ - FALHA AO EXECUTAR SCRIPT !!! {e}")
        return False

    finally:
        if 'conn' in locals():
            conn.close()