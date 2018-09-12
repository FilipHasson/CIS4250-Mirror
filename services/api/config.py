from os import environ as env

DEPLOYMENT = env.get("DEPLOYMENT") == "development"
POSTGRES_DB = env.get("POSTGRES_DB", "docker")
POSTGRES_HOST = env.get("POSTGRES_HOST", "localhost")
POSTGRES_USER = env.get("POSTGRES_USER", "docker")
POSTGRES_PASSWORD = env.get("POSTGRES_PASSWORD", "docker")
DB_CONFIG = f"{POSTGRES_USER}:{POSTGRES_PASSWORD}@{POSTGRES_HOST}/{POSTGRES_DB}"

class Config:
    SQLALCHEMY_DATABASE_URI = "postgresql+psycopg2://" + DB_CONFIG
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    TESTING = False
    DEBUG = DEPLOYMENT
