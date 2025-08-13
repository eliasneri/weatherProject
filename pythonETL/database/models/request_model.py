from config import settings
from datetime import datetime

class RequestModel:
    def __init__(self, cursor):
        self.cursor = cursor

    def insert_requests(self, data):
        try:
            self.cursor.execute("""
                                INSERT INTO requests (
                                    request_latitude,
                                    request_longitude,
                                    request_city,
                                    request_city_uf,
                                    request_temperature_abbrev,
                                    request_generated
                                ) VALUES (
                                             %s,
                                             %s,
                                             %s,
                                             %s,
                                             %s,
                                             %s
                                         )
                                    RETURNING request_id
                                """, (
                                    data['latitude'],
                                    data['longitude'],
                                    settings.DEFAULT_CITY.upper(),
                                    settings.DEFAULT_UF.upper(),
                                    data['current_units']['temperature_2m'],
                                    datetime.now()
                                ))

            result = self.cursor.fetchone()
            return result[0] if result else None

        except Exception as e:
            print(f"❌ ❌ ❌ - ERRO AO INSERIR REQUESTS: {e}")
