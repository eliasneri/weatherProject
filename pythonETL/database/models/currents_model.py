class CurrentsModel:
    def __init__(self, cursor):
        self.cursor = cursor

    def insert_currents(self, data, request_id):
        try:
            self.cursor.execute("""
                                INSERT INTO currents (
                                    current_fk_request_id,
                                    current_day,
                                    current_temperature
                                ) VALUES (
                                             %s,
                                             %s,
                                             %s
                                         )
                                """, (
                                    request_id,
                                    data['current']['time'],
                                    int(data['current']['temperature_2m'] * 100)
                                ))
            return True

        except Exception as e:
            print(f"❌ ❌ ❌ ERRO AO INSERIR CURRENTS: {e}")
            return False

