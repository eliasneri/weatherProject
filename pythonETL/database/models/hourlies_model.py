class hourliesModel:
    def __init__(self, cursor):
        self.cursor = cursor

    def insert_hourlies(self, data, request_id):
        try:
            hourly_data = [
                (request_id, time_value, int(temperature_value * 100))
                for time_value, temperature_value in zip(data['hourly']['time'], data['hourly']['temperature_2m'])
            ]

            self.cursor.executemany("""
                INSERT INTO hourlies (
                    hourly_fk_request_id,
                    hourly_time,
                    hourly_temperature
                ) VALUES (
                    %s,
                    %s,
                    %s
                )
                """, (hourly_data))

            return True

        except Exception as e:
            print(f"❌ ❌ ❌ - ERRO AO INSERTAR HOURLIES {e}")
            return False
