from collections import namedtuple

from flask import Flask, jsonify, request, url_for
from flask_sqlalchemy import SQLAlchemy

from config import Config, env

api = Flask(__name__)
api.config.from_object(Config)

db = SQLAlchemy(api)


def query(statement):
    result = db.engine.execute(statement)
    Record = namedtuple("Record", result.keys())
    records = [Record(*r) for r in result.fetchall()]
    records = [r[0] if len(r) == 1 else r for r in records]
    return records


@api.route("/")
def endpoint_status():
    endpoints = [
        {
            url_for(
                rule.endpoint, **{arg: "[%s]" % arg for arg in rule.arguments}
            ): {
                "method": rule.endpoint,
                "actions": [
                    method
                    for method in rule.methods
                    & {"GET", "POST", "PUT", "PATCH", "DELETE"}
                ],
            }
        }
        for rule in api.url_map.iter_rules()
        if rule.endpoint != "static"
    ]
    # Omit sensitive information in production
    if api.config.get("ENV") == "production":
        response = {"endpoints": endpoints}
    else:
        try:
            tables = query(
                """
                SELECT table_name
                FROM information_schema.tables
                WHERE table_schema='public'
                AND table_type='BASE TABLE'
                """
            )
        except:
            tables = []
        response = {
            "endpoints": endpoints,
            "environment": dict(env),
            "db_tables": tables,
        }
    return jsonify(response)
