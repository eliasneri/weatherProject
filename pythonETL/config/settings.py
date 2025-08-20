import os
from dotenv import load_dotenv

"""
Configurações de conexão, obtidas pelo .env
"""

load_dotenv()

"""
database settings!
"""
DB_HOST = os.getenv("DB_HOST", "localhost")
DB_PORT = os.getenv("DB_PORT", "5432")
DB_NAME = os.getenv("DB_NAME", "weather_db")
DB_USER = os.getenv("DB_USER", "admin")
DB_PASS = os.getenv("DB_PASS", "")

"""
default city
"""
DEFAULT_CITY = os.getenv("DEFAULT_CITY", "null")
DEFAULT_UF = os.getenv("DEFAULT_UF", "null")
CITY_LATITUDE = os.getenv("CITY_LATITUTE", "52.52")
CITY_LONGITUDE = os.getenv("CITY_LONGITUDE", "13.41")

"""
params API
"""
API_URL = os.getenv("API_URL", "")
DAILY = os.getenv("DAILY", "sunrise,sunset")
HOURLY = os.getenv("HOURLY", "temperature_2m")
CURRENT = os.getenv("CURRENT", "temperature_2m")
PAST_DAYS = os.getenv("PAST_DAYS", "1")
FORECAST_DAYS = os.getenv("FORECAST_DAYS", "1")

