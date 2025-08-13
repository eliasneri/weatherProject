class dailiesModel:
    def __init__(self, cursor):
        self.cursor = cursor

    def insert_dailies(self, data, request_id):
        try:
            daily_data = [
                (request_id, sunrise_value, sunset_value)
                for sunrise_value, sunset_value in zip(data['daily']['sunrise'], data['daily']['sunset'])
            ]
            self.cursor.executemany("""
                INSERT INTO dailies (
                    daily_fk_request_id,
                    daily_sunrise,
                    daily_sunset
                ) VALUES (
                    %s,
                    %s,
                    %s
                )
            """, daily_data)

            return True

        except Exception as e:
            print(f"❌ ❌ ❌ - ERRO AO INSERIR DAILIES: {e}")
            return False

