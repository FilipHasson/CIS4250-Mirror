from collections import namedtuple

from flask import Flask, jsonify, request, url_for
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.sql.expression import literal_column
from sqlalchemy import func, select, String


from config import Config, env

api = Flask(__name__)
api.config.from_object(Config)

db = SQLAlchemy(api)


def query(statement, as_json=True):
    result = db.engine.execute(statement)
    Record = namedtuple("Record", result.keys())
    records = [Record(*r) for r in result.fetchall()]
    records = [r[0] if len(r) == 1 else r for r in records]
    return jsonify(records) if as_json else records


def query_json(statement):
    statement = f"SELECT row_to_json(t) from ({statement}) t"
    result = db.engine.execute(statement)
    Record = namedtuple("Record", result.keys())
    records = [Record(*r) for r in result.fetchall()]
    records = [r[0] if len(r) == 1 else r for r in records]
    return jsonify(records[0] if records else {})


@api.route("/food")
def endpoint_foods():
    statement = "SELECT id FROM food ORDER BY time_created DESC LIMIT 100"
    return query(statement)


@api.route("/food/<food_id>")
def endpoint_food(food_id):
    statement = f"""
      SELECT * FROM food
      JOIN nutrition ON food.nutrition_id = nutrition.id
      WHERE food.id={food_id}
    """
    return query_json(statement)


@api.route("/")
def endpoint_status():
    endpoints = [
        {
            url_for(rule.endpoint, **{arg: "[%s]" % arg for arg in rule.arguments})
            .replace("%5B", "{")
            .replace("%5D", "}"): {
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
                """,
                as_json=False,
            )
        except:
            tables = []
        response = {
            "endpoints": endpoints,
            "environment": dict(env),
            "db_tables": tables,
        }
    return jsonify(response)
