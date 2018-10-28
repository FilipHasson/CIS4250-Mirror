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

-- TABLES ------------------------------------------------------------

-- Account
DROP TABLE IF EXISTS account CASCADE;
CREATE TABLE account (
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
DROP TABLE IF EXISTS nutrition CASCADE;
CREATE TABLE nutrition (
  id SERIAL PRIMARY KEY,
  usda_id INT,
  caffeine_mg REAL DEFAULT 0,
  calcium_mg REAL DEFAULT 0,
  calories REAL DEFAULT 0,
  carbohydrate_g REAL DEFAULT 0,
  cholesterol_mg REAL DEFAULT 0,
  fat_mono_g REAL DEFAULT 0,
  fat_poly_g REAL DEFAULT 0,
  fat_sat_g REAL DEFAULT 0,
  fat_trans_g REAL DEFAULT 0,
  folate_mcg REAL DEFAULT 0,
  magnesium_mg REAL DEFAULT 0,
  manganese_mg REAL DEFAULT 0,
  niacin_mg REAL DEFAULT 0,
  potassium_mg REAL DEFAULT 0,
  protein_g REAL DEFAULT 0,
  riboflavin_mg REAL DEFAULT 0,
  sodium_mg REAL DEFAULT 0,
  sugars_g REAL DEFAULT 0,
  thiamin_mg REAL DEFAULT 0,
  total_fiber_g REAL DEFAULT 0,
  total_lipid_g REAL DEFAULT 0,
  vitamin_a_iu REAL DEFAULT 0,
  vitamin_b6_mg REAL DEFAULT 0,
  vitamin_c_mg REAL DEFAULT 0,
  vitamin_d_iu REAL DEFAULT 0,
  vitamin_k_mcg REAL DEFAULT 0,
  water_g REAL DEFAULT 0,
  zinc_mg REAL DEFAULT 0
);

-- Recipe
DROP TABLE IF EXISTS recipe CASCADE;
CREATE TABLE recipe (
  id SERIAL PRIMARY KEY,
  account_id INTEGER REFERENCES account NOT NULL,
  portions REAL NOT NULL,
  categories RECIPE_CATEGORY [],
  steps TEXT []
);

-- Food
DROP TABLE IF EXISTS food CASCADE;
CREATE TABLE food (
  id SERIAL PRIMARY KEY,
  title TEXT NOT NULL,
  usda_id INTEGER UNIQUE,
  nutrition_id INTEGER REFERENCES nutrition UNIQUE,
  recipe_id INTEGER REFERENCES recipe UNIQUE,
  time_created TIMESTAMP DEFAULT now() NOT NULL,
  time_updated TIMESTAMP DEFAULT now() NOT NULL,
  view_count INTEGER DEFAULT 0,
  star_count INTEGER DEFAULT 0
);

-- Meal
-- DROP TABLE IF EXISTS meal CASCADE;
-- CREATE TABLE meal (
--     id SERIAL PRIMARY KEY,
--     account_id INTEGER NOT NULL REFERENCES account,
--     date DATE,
--     position INTEGER NOT NULL DEFAULT 0,
--   type MEAL_TYPE NOT NULL
-- );
-- CREATE UNIQUE INDEX IF NOT EXISTS unique_combination_idx
--     ON meal (account_id, date, position, trim(lower(type)));


CREATE EXTENSION IF NOT EXISTS tablefunc;
CREATE SCHEMA IF NOT EXISTS usda;

-- Add Product Data
DROP TABLE IF EXISTS usda.products CASCADE;
CREATE TABLE usda.products (
  ndb_number INT,
  long_name TEXT,
  data_source TEXT,
  gtin_upc TEXT,
  manufacturer TEXT,
  date_modified TEXT,
  date_available TEXT,
  ingredients_english TEXT
);
COPY usda.products (
  ndb_number,
  long_name,
  data_source,
  gtin_upc,
  manufacturer,
  date_modified,
  date_available,
  ingredients_english
)
FROM '/Products.csv' WITH CSV HEADER DELIMITER AS ',';
ALTER TABLE usda.products
  DROP COLUMN data_source,
  DROP COLUMN  gtin_upc,
  DROP COLUMN  manufacturer,
  DROP COLUMN date_modified,
  DROP COLUMN date_available,
  DROP COLUMN ingredients_english;
INSERT INTO food (usda_id, title)
SELECT ndb_number, initcap(long_name)
FROM usda.products;

-- Add Nutrition Data
DROP TABLE IF EXISTS usda.nutrition CASCADE;
CREATE TABLE usda.nutrition (
  ndb_no INT,
  nutrient_code INT,
  nutrient_name TEXT,
  derivation_code TEXT,
  output_value REAL,
  output_uom TEXT
);
COPY usda.nutrition (
  ndb_no,
  nutrient_code,
  nutrient_name,
  derivation_code,
  output_value,
  output_uom
)
FROM '/Nutrients.csv' WITH CSV HEADER DELIMITER AS ',';
ALTER TABLE usda.nutrition
  DROP COLUMN nutrient_name,
  DROP COLUMN derivation_code,
  DROP COLUMN output_uom;
DELETE FROM usda.nutrition WHERE nutrient_code NOT IN (
  203,204,205,208,255,262,269,291,301,304,306,307,309,
  315,318,324,401,405,406,415,417,430,601,606,645,646
);
DELETE FROM usda.nutrition WHERE output_value = 0;
INSERT INTO nutrition (
  usda_id,
  caffeine_mg,
  calcium_mg,
  calories,
  carbohydrate_g,
  cholesterol_mg,
  fat_mono_g,
  fat_poly_g,
  fat_sat_g,
  fat_trans_g,
  folate_mcg,
  magnesium_mg,
  manganese_mg,
  niacin_mg,
  potassium_mg,
  protein_g,
  riboflavin_mg,
  sodium_mg,
  sugars_g,
  thiamin_mg,
  total_fiber_g,
  total_lipid_g,
  vitamin_a_iu,
  vitamin_b6_mg,
  vitamin_c_mg,
  vitamin_d_iu,
  vitamin_k_mcg,
  water_g,
  zinc_mg
)
SELECT
  ndb_no,
  coalesce(caffeine_mg, 0) as caffeine_mg,
  coalesce(calcium_mg, 0) as calcium_mg,
  coalesce(calories, 0) as calories,
  coalesce(carbohydrate_g, 0) as carbohydrate_g,
  coalesce(cholesterol_mg, 0) as cholesterol_mg,
  coalesce(fat_mono_g, 0) as fat_mono_g,
  coalesce(fat_poly_g, 0) as fat_poly_g,
  coalesce(fat_sat_g, 0) as fat_sat_g,
  coalesce(fat_trans_g, 0) as fat_trans_g,
  coalesce(folate_mcg, 0) as folate_mcg,
  coalesce(magnesium_mg, 0) as magnesium_mg,
  coalesce(manganese_mg, 0) as manganese_mg,
  coalesce(niacin_mg, 0) as niacin_mg,
  coalesce(potassium_mg, 0) as potassium_mg,
  coalesce(protein_g, 0) as protein_g,
  coalesce(riboflavin_mg, 0) as riboflavin_mg,
  coalesce(sodium_mg, 0) as sodium_mg,
  coalesce(sugars_g, 0) as sugars_g,
  coalesce(thiamin_mg, 0) as thiamin_mg,
  coalesce(total_fiber_g, 0) as total_fiber_g,
  coalesce(total_lipid_g, 0) as total_lipid_g,
  coalesce(vitamin_a_iu, 0) as vitamin_a_iu,
  coalesce(vitamin_b6_mg, 0) as vitamin_b6_mg,
  coalesce(vitamin_c_mg, 0) as vitamin_c_mg,
  coalesce(vitamin_d_iu, 0) as vitamin_d_iu,
  coalesce(vitamin_k_mcg, 0) as vitamin_k_mcg,
  coalesce(water_g, 0) as water_g,
  coalesce(zinc_mg, 0) as zinc_mg
FROM crosstab(
  $$SELECT ndb_no, nutrient_code, output_value FROM usda.nutrition nutrient_code ORDER BY 1,2$$
) AS t(
  ndb_no INT,
  protein_g REAL,
  total_lipid_g REAL,
  carbohydrate_g REAL,
  calories REAL,
  water_g REAL,
  caffeine_mg REAL,
  sugars_g REAL,
  total_fiber_g REAL,
  calcium_mg REAL,
  magnesium_mg REAL,
  potassium_mg REAL,
  sodium_mg REAL,
  zinc_mg REAL,
  manganese_mg REAL,
  vitamin_a_iu REAL,
  vitamin_d_iu REAL,
  vitamin_c_mg REAL,
  thiamin_mg REAL,
  riboflavin_mg REAL,
  niacin_mg REAL,
  vitamin_b6_mg REAL,
  folate_mcg REAL,
  vitamin_k_mcg REAL,
  cholesterol_mg REAL,
  fat_trans_g REAL,
  fat_sat_g REAL,
  fat_mono_g REAL,
  fat_poly_g REAL
);

-- Link two together
UPDATE food
SET nutrition_id = nutrition.id
FROM nutrition
WHERE nutrition.usda_id = food.usda_id;

-- Clean up
DELETE FROM food WHERE nutrition_id IS NULL;
ALTER TABLE food DROP COLUMN usda_id;
ALTER TABLE nutrition DROP COLUMN usda_id;
DROP SCHEMA usda CASCADE;
