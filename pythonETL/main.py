import sys
import os
sys.path.append(os.path.dirname(os.path.abspath(__file__)))
from database import connection
from database.models.saveToDatabase import save_to_database
from services.apiOpenMeteo import fetch_api

if __name__ == "__main__":
    # testa a conexão com o banco de dados
    if not connection.test_connection():
        print("🚫 ENCERRANDO EXECUÇÃO POR FALHA DE CONEXÃO ...")
        exit(1)

    # Cria as tabelas necessárias no banco de dados
    if not connection.create_tables():
        print("🚫 ENCERRANDO EXECUÇÃO POR FALHA NA EXECUÇÃO DO SCRIPT SQL ...")
        exit(1)

    # Busca Dados na API
    data = fetch_api()

    if data is None:
        print("🚫 - ENCERRANDO EXECUÇÃO - DADOS DA API RETORNARAM NONE !!!")
        exit(1)

    if save_to_database(data):
        print("🎉 - EXTRAÇÃO DE DADOS CONCLUÍDO COM SUCESSO!")
    else:
        print("🚫 FALHA AO SALVAR DADOS NO BANCO DE DADOS")
        exit(1)

