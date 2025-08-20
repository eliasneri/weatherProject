import sys

import requests
from config import settings

def fetch_api():
    if not settings.API_URL:
        print("❌ ❌ ❌ API URL NOT SET")
        sys.exit(1)

    print(f"🔍 - BUSCANDO DADOS PARA A CIDADE: {settings.DEFAULT_CITY}, {settings.DEFAULT_UF}...")

    params={
        "latitude":settings.CITY_LATITUDE,
        "longitude":settings.CITY_LONGITUDE,
        "daily":settings.DAILY,
        "hourly":settings.HOURLY,
        "current":settings.CURRENT,
        "past_days":settings.PAST_DAYS,
        "forecast_days":settings.FORECAST_DAYS
    }

    response = requests.get(settings.API_URL, params=params)
    if response.status_code == 200:
        print("✅ SUCCESSFULLY !!! FETCHED DATA")
        return response.json()
    else:
        print(f"❌ ERRO: {response.status_code}")
        return None