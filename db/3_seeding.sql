CREATE EXTENSION IF NOT EXISTS tablefunc;
CREATE SCHEMA IF NOT EXISTS usda;

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
ALTER TABLE food DROP CONSTRAINT title_const;

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
  203,204,205,208,255,262,269,291,301,304,306,307,309,
  315,318,324,401,405,406,415,417,430,601,606,645,646
);
DELETE FROM usda.nutrition WHERE output_value = 0;

-- Transform USDA nutrition data
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
  ndb_no INTEGER,
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

-- Link nutrition and food tables
UPDATE food
SET nutrition_id = nutrition.id
FROM nutrition
WHERE nutrition.usda_id = food.usda_id;

-- Clean up
DELETE FROM food WHERE nutrition_id IS NULL;
ALTER TABLE food DROP COLUMN usda_id;
ALTER TABLE nutrition DROP COLUMN usda_id;
DROP SCHEMA usda CASCADE;
DROP EXTENSION tablefunc;
