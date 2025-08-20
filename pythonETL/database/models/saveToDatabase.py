from database.connection import get_connection
from database.models.request_model import RequestModel
from database.models.currents_model import CurrentsModel
from database.models.dailies_model import dailiesModel
from database.models.hourlies_model import hourliesModel

def save_to_database(data):
    """
    Orquestrador para salvar todos os dados no banco de dados dentro de uma transação.
    
    Args:
        data: Dados retornados da API OpenMeteo

    """
    conn = None
    cursor = None

    try:
        print("⏳ - INICIANDO PROCESSO DE SALVAMENTO NO BANCO DE DADOS...")

        conn = get_connection()
        cursor = conn.cursor()

        print("🔄 - INICIANDO TRANSAÇÃO...")

        request_model = RequestModel(cursor)
        currents_model = CurrentsModel(cursor)
        dailies_model = dailiesModel(cursor)
        hourlies_model = hourliesModel(cursor)

        print("📝 - INSERINDO DADOS DA REQUEST...")
        request_id = request_model.insert_requests(data)

        if not request_id:
            print("Falha ao inserir dados da request - ID não retornado")

        print(f"✅ - REQUEST INSERIDA COM ID: {request_id}")

        print("📝 - INSERINDO DADOS CURRENTS...")
        if not currents_model.insert_currents(data, request_id):
            print("Falha ao inserir dados currents")

        print("✅ - CURRENTS INSERIDOS COM SUCESSO")

        print("📝 - INSERINDO DADOS dailies...")
        if not dailies_model.insert_dailies(data, request_id):
            print("Falha ao inserir dados dailies")

        print("✅ - dailies INSERIDOS COM SUCESSO")

        print("📝 - INSERINDO DADOS hourlies...")
        if not hourlies_model.insert_hourlies(data, request_id):
            print("Falha ao inserir dados hourlies")

        print("✅ - hourlies INSERIDOS COM SUCESSO")

        conn.commit()
        print("✅ - TRANSAÇÃO CONFIRMADA - TODOS OS DADOS SALVOS COM SUCESSO!")

        return True

    except Exception as e:
        if conn:
            conn.rollback()
            print("🔄 - ROLLBACK EXECUTADO - NENHUM DADO FOI SALVO")

        print(f"❌ ❌ ❌ - ERRO NO PROCESSO DE SALVAMENTO: {e}")
        return False

    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()
        print("🔌 CONEXÃO COM BANCO DE DADOS ENCERRADA")