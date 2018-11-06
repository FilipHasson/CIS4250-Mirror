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

-- Add food titles
INSERT INTO food (usda_id, title)
SELECT ndb_number, initcap(long_name)
FROM usda.products
ON CONFLICT ON CONSTRAINT title_const DO NOTHING;

-- Add serving size data
DROP TABLE IF EXISTS usda.servings CASCADE;
CREATE TABLE usda.servings (
  ndb_no INTEGER,
  measurement_amount TEXT,
  measurement_type TEXT,
  quantity_amount TEXT,
  quantity_type TEXT,
  preperation_state TEXT
);
COPY usda.servings (
  ndb_no,
  measurement_amount,
  measurement_type,
  quantity_amount,
  quantity_type,
  preperation_state
)
FROM '/Serving_size.csv' WITH CSV HEADER DELIMITER AS ',';
ALTER TABLE usda.servings DROP COLUMN preperation_state;
UPDATE food
    SET
      serving_count = t.serving_count,
      serving_size = REGEXP_REPLACE(LOWER(t.serving_size), $$\s*\|.+|\($|^["\s]+|^[^\d\w]*|[^\d\w]*$|^x[\s\d."']+$$, '', 'g')
FROM (
    SELECT
    ndb_no,
    CASE
        WHEN (quantity_amount IS NOT NULL AND quantity_amount != '') THEN quantity_amount::REAL
        WHEN (measurement_amount IS NOT NULL AND measurement_amount != '') THEN measurement_amount::REAL
        ELSE NULL
    END AS serving_count,
    CASE
        WHEN (quantity_amount IS NOT NULL AND quantity_amount != '') THEN quantity_type
        WHEN (measurement_amount IS NOT NULL AND measurement_amount != '') THEN measurement_type
        ELSE NULL
      END AS serving_size
    FROM usda.servings
) t
WHERE food.usda_id = t.ndb_no;
DELETE FROM food WHERE serving_size IS NULL OR serving_size = '';

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
