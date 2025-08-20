# Weather Project 🌤️

Projeto Python para extração de dados meteorológicos utilizando API pública (Open-Meteo) e armazenamento em banco de dados PostgreSQL.

## 📁 Estrutura do Projeto

```
weatherProject/
├── 📁 venv/                    # Ambiente virtual Python
├── 📁 config/
│   ├── __init__.py
│   └── settings.py             # Configurações do projeto
├── 📁 database/
│   ├── __init__.py
│   └── 📁 models/
│       ├── __init__.py
│       ├── currents_model.py   # Modelo para dados atuais
│       ├── dailies_model.py     # Modelo para dados diários
│       ├── hourlies_model.py    # Modelo para dados horários
│       ├── request_model.py    # Modelo para requisições
│       └── saveToDatabase.py   # Funções de salvamento
├── 📁 sql/
│   ├── create_tables.sql       # Script de criação das tabelas
│   └── connection.py           # Conexão com banco de dados
├── 📁 services/
│   ├── __init__.py
│   └── apiOpenMeteo.py         # Serviço de consulta à API
├── 📄 Dockerfile               # Container Python ETL
├── 📄 .env                     # Variáveis de ambiente
├── 📄 main.py                  # Arquivo principal
└── 📄 requirements.txt         # Dependências do projeto
```

## 🚀 Funcionalidades

- Extração de dados meteorológicos da API Open-Meteo
- Armazenamento de dados atuais, diários e horários
- Configuração flexível de localização
- Histórico de 3 dias passados e previsão de 3 dias
- Banco de dados PostgreSQL para persistência

## 📋 Pré-requisitos

- Python 3.13
- PostgreSQL
- pip (gerenciador de pacotes Python)

## 🛠️ Instalação e Configuração

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd weatherProject
cd pythonETL
```

### 2. Crie o ambiente virtual
```bash
python -m venv venv
```

### 3. Ative o ambiente virtual

**Windows:**
```bash
venv\Scripts\activate
```

**Linux/macOS:**
```bash
source venv/bin/activate
```

### 4. Instale as dependências
```bash
pip install -r requirements.txt
```

### 5. Configure o banco de dados PostgreSQL
Certifique-se de que o PostgreSQL está instalado e rodando. Crie um banco de dados chamado `weather_db` (ou conforme configurado no .env).

### 6. Configure as variáveis de ambiente
Crie/edite o arquivo `.env` na raiz do projeto com as seguintes configurações:

```env
# BANCO DE DADOS
DB_HOST=localhost
DB_PORT=5432
DB_NAME=weather_db
DB_USER=postgres
DB_PASS=sua_senha

# LOCALIZAÇÃO PADRÃO
DEFAULT_CITY=Nome_da_Cidade
DEFAULT_UF=UF
CITY_LATITUTE=-22.7547
CITY_LONGITUDE=-47.4144

# Parâmetros API
API_URL=https://api.open-meteo.com/v1/forecast
DAILY=sunrise,sunset
HOURLY=temperature_2m
CURRENT=temperature_2m
PAST_DAYS=3
FORECAST_DAYS=3
```

#### Exemplos de cidades configuradas:
```env
# Americana, SP
#DEFAULT_CITY=Americana
#DEFAULT_UF=SP
#CITY_LATITUTE=-22.7388
#CITY_LONGITUDE=-47.3319

# Santa Bárbara d'Oeste, SP
#DEFAULT_CITY=Santa Bárbara d'Oeste
#DEFAULT_UF=SP
#CITY_LATITUTE=-22.7547
#CITY_LONGITUDE=-47.4144

# Nova Odessa, SP
#DEFAULT_CITY=Nova Odessa
#DEFAULT_UF=SP
#CITY_LATITUTE=-22.7799
#CITY_LONGITUDE=-47.2963

# Salvador, BA (configuração ativa)
DEFAULT_CITY=Salvador
DEFAULT_UF=BA
CITY_LATITUTE=-12.9704
CITY_LONGITUDE=-38.5124
```

## ▶️ Execução

### 1. Ative o ambiente virtual (se não estiver ativo)
```bash
# Windows
venv\Scripts\activate

# Linux/macOS
source venv/bin/activate
```

### 2. Execute o projeto
```bash
python main.py
```

## 🗃️ Banco de Dados

O projeto utiliza PostgreSQL com as seguintes tabelas:
- **currents**: Dados meteorológicos atuais
- **dailies**: Dados meteorológicos diários (nascer/pôr do sol)
- **hourlies**: Dados meteorológicos horários (temperatura)
- **requests**: Log das requisições realizadas

## 🌐 API Utilizada

O projeto utiliza a [Open-Meteo API](https://api.open-meteo.com/), uma API pública e gratuita para dados meteorológicos que não requer chave de API.

## 📝 Personalização

Para alterar a localização dos dados meteorológicos:
1. Edite o arquivo `.env`
2. Modifique as variáveis `DEFAULT_CITY`, `DEFAULT_UF`, `CITY_LATITUDE` e `CITY_LONGITUDE`
3. Execute novamente o projeto
