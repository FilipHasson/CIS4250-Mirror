-- init.sql
-- run by docker container on container creation to initialize database

-- EXTENSIONS ------------------------------------------------------------------

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- TYPES -----------------------------------------------------------------------

DO LANGUAGE plpgsql
$$
BEGIN
  CREATE TYPE RECIPE_CATEGORY AS ENUM (
    'atkins',
    'beverage',
    'breakfast',
    'comfort',
    'dessert',
    'dinner',
    'keto',
    'lunch',
    'quick',
    'salad',
    'sauce',
    'side',
    'snack',
    'soup'
  );
  CREATE TYPE MEAL_TYPE AS ENUM (
    'breakfast',
    'snack1',
    'lunch',
    'snack2',
    'dinner',
    'snack3'
  );
    EXCEPTION WHEN OTHERS
    THEN
END;
$$;

-- TABLES AND VIEWS ------------------------------------------------------------

-- Account
CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    username VARCHAR(25),
    password CHAR(32),
    email VARCHAR(128)
);
CREATE UNIQUE INDEX IF NOT EXISTS unique_username_idx
    ON account (trim(lower(username)));
CREATE UNIQUE INDEX IF NOT EXISTS unique_email_idx
    ON account (trim(lower(email)));

-- Nutrition
CREATE TABLE IF NOT EXISTS nutrition (
    id SERIAL PRIMARY KEY,

    protein REAL DEFAULT 0,
    total_lipid REAL DEFAULT 0,
    carbohydrate REAL DEFAULT 0,
    ash REAL DEFAULt 0,
    water REAL DEFAULT 0,
    sugars REAL DEFAULT 0,
    total_fiber REAL DEFAULT 0,
    solu_fiber REAL DEFAULT 0,
    insolu_fiber REAL DEFAULT 0,
    calcium REAL DEFAULT 0,
    iron REAL DEFAULT 0,
    zinc REAL DEFAULT 0,
    magnesium REAL DEFAULT 0,
    phosphorus REAL DEFAULT 0,
    potassium REAL DEFAULT 0,
    sodium REAL DEFAULT 0,
    manganese REAL DEFAULT 0,
    vitamin_a REAL DEFAULT 0,
    vitamin_d REAL DEFAULT 0,
    vitamin_c REAl DEFAULT 0,
    vitamin_k REAL DEFAULT 0,
    vitamin_e REAL DEFAULT 0,
    thiamin REAL DEFAULT 0,
    riboflavin REAL DEFAULT 0,
    niacin REAL DEFAULT 0,
    vitamin_b6 REAL DEFAULT 0,
    folate REAL DEFAULT 0,
    fat_trans REAL DEFAULT 0,
    fat_sat REAL DEFAULT 0,
    fat_mono REAL DEFAULT 0,
    fat_poly REAL DEFAULT 0,
    alcohol REAL DEFAULT 0,
    caffeine REAL DEFAULT 0,
    copper REAL DEFAULT 0,
    folic_acid REAL DEFAULT 0,
    energy REAL DEFAULT 0

);

CREATE OR REPLACE VIEW nutrition_macros AS
    SELECT
        total_fiber +
        sugars
            AS carbohydrates,
        fat_mono +
        fat_poly +
        fat_sat +
        fat_trans
            AS fats,
        protein
    FROM
        nutrition;

CREATE OR REPLACE VIEW nutrition_vitamins AS
    SELECT
        folate +
        folic_acid
            AS vitamin_b9,
        niacin,
        riboflavin,
        vitamin_a,
        vitamin_b6,
        vitamin_c,
        vitamin_d,
        vitamin_e,
        vitamin_k
    FROM
        nutrition;

CREATE OR REPLACE VIEW nutrition_minerals AS
    SELECT
        calcium,
        copper,
        iron,
        magnesium,
        phosphorus,
        potassium,
        sodium,
        zinc
    FROM
        nutrition;

-- Recipe
CREATE TABLE IF NOT EXISTS recipe (
  id SERIAL PRIMARY KEY,
  account_id INTEGER REFERENCES account NOT NULL,
  portions REAL NOT NULL,
  categories RECIPE_CATEGORY[],
  steps TEXT[]
);

-- Food
CREATE TABLE IF NOT EXISTS food (
    id SERIAL PRIMARY KEY,
    nutrition_id INTEGER REFERENCES nutrition NOT NULL,
    recipe_id INTEGER REFERENCES recipe,
    time_created TIMESTAMP NOT NULL,
    time_updated TIMESTAMP NOT NULL,
    view_count INTEGER DEFAULT 0,
    star_count INTEGER DEFAULT 0
);

-- Meal
-- CREATE TABLE IF NOT EXISTS meal (
--     id SERIAL PRIMARY KEY,
--     account_id INTEGER NOT NULL REFERENCES account,
--     date DATE,
--     position INTEGER NOT NULL DEFAULT 0,
--   type MEAL_TYPE NOT NULL
-- );
-- CREATE UNIQUE INDEX IF NOT EXISTS unique_combination_idx
--     ON meal (account_id, date, position, trim(lower(type)));
