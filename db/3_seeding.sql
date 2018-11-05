CREATE EXTENSION IF NOT EXISTS tablefunc;
CREATE SCHEMA IF NOT EXISTS usda;

-- Add temporary columns and constraints to yumm database tables
ALTER TABLE food ADD COLUMN IF NOT EXISTS usda_id INTEGER;
ALTER TABLE nutrition ADD COLUMN IF NOT EXISTS usda_id INTEGER;
ALTER TABLE food DROP CONSTRAINT IF EXISTS title_const;
ALTER TABLE food ADD CONSTRAINT title_const UNIQUE (title);

-- Add product data
DROP TABLE IF EXISTS usda.products CASCADE;
CREATE TABLE usda.products (
  ndb_number INTEGER,
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
FROM usda.products
ON CONFLICT ON CONSTRAINT title_const DO NOTHING;

-- Add nutrition data
DROP TABLE IF EXISTS usda.nutrition CASCADE;
CREATE TABLE usda.nutrition (
  ndb_no INTEGER,
  nutrient_code INTEGER,
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
  203,  -- protein
  204,  -- total fat
  205,  -- total carbs
  208,  -- calories
  269,  -- sugars
  291,  -- fibre
  301,  -- calcium
  303,  -- iron
  307,  -- sodium
  318,  -- vitamin a
  401,  -- vitamin c
  601,  -- cholesterol
  605,  -- trans fat
  606  -- saturated fat
);
DELETE FROM usda.nutrition WHERE output_value = 0;

-- Transform USDA nutrition data
INSERT INTO nutrition (
  usda_id,
  calcium_mg,
  calories,
  carbs_fibre_g,
  carbs_sugar_g,
  carbs_total_g,
  cholesterol_mg,
  fat_sat_g,
  fat_total_g,
  fat_trans_g,
  iron_mg,
  protein_g,
  sodium_mg,
  vitamin_a_iu,
  vitamin_c_mg
)
SELECT
  ndb_no,
  coalesce(calcium_mg, 0) as calcium_mg,
  coalesce(calories, 0) as calories,
  coalesce(carbs_fibre_g, 0) as carbohydrate_g,
  coalesce(carbs_sugar_g, 0) as cholesterol_mg,
  coalesce(carbs_total_g, 0) as fat_mono_g,
  coalesce(cholesterol_mg, 0) as fat_poly_g,
  coalesce(fat_sat_g, 0) as fat_sat_g,
  coalesce(fat_total_g, 0) as fat_trans_g,
  coalesce(fat_trans_g, 0) as folate_mcg,
  coalesce(iron_mg, 0) as magnesium_mg,
  coalesce(protein_g, 0) as manganese_mg,
  coalesce(sodium_mg, 0) as niacin_mg,
  coalesce(vitamin_a_iu, 0) as potassium_mg,
  coalesce(vitamin_c_mg, 0) as protein_g
FROM crosstab(
  $$SELECT ndb_no, nutrient_code, output_value FROM usda.nutrition nutrient_code ORDER BY 1,2$$
) AS t(
  -- needs to be in order of nutrient_code
  ndb_no INTEGER,
  protein_g REAL,
  fat_total_g REAL,
  carbs_total_g REAL,
  calories REAL,
  carbs_sugar_g REAL,
  carbs_fibre_g REAL,
  calcium_mg REAL,
  iron_mg REAL,
  sodium_mg REAL,
  vitamin_a_iu REAL,
  vitamin_c_mg REAL,
  cholesterol_mg REAL,
  fat_trans_g REAL,
  fat_sat_g REAL
);

-- Link nutrition and food tables
UPDATE food
SET nutrition_id = nutrition.id
FROM nutrition
WHERE nutrition.usda_id = food.usda_id;

-- Clean up
ALTER TABLE food DROP CONSTRAINT title_const;
ALTER TABLE food DROP COLUMN usda_id;
ALTER TABLE nutrition DROP COLUMN usda_id;
DELETE FROM food WHERE nutrition_id IS NULL;
DROP SCHEMA usda CASCADE;
DROP EXTENSION tablefunc;
