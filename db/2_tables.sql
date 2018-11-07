-- ACCOUNT ---------------------------------------------------------------------
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

-- NUTRITION -------------------------------------------------------------------
DROP TABLE IF EXISTS nutrition CASCADE;
CREATE TABLE nutrition (
  id SERIAL PRIMARY KEY,
  calcium_mg REAL DEFAULT 0,
  calories REAL DEFAULT 0,
  carbs_fibre_g REAL DEFAULT 0,
  carbs_sugar_g REAL DEFAULT 0,
  carbs_total_g REAL DEFAULT 0,
  cholesterol_mg REAL DEFAULT 0,
  fat_sat_g REAL DEFAULT 0,
  fat_total_g REAL DEFAULT 0,
  fat_trans_g REAL DEFAULT 0,
  iron_mg REAL DEFAULT 0,
  protein_g REAL DEFAULT 0,
  sodium_mg REAL DEFAULT 0,
  vitamin_a_iu REAL DEFAULT 0,
  vitamin_c_mg REAL DEFAULT 0
);

-- RECIPE ----------------------------------------------------------------------
DROP TABLE IF EXISTS recipe CASCADE;
CREATE TABLE recipe (
  id SERIAL PRIMARY KEY,
  account_id INTEGER REFERENCES account NOT NULL,
  categories RECIPE_CATEGORY [],
  description TEXT,
  view_count INTEGER DEFAULT 0,
  star_count INTEGER DEFAULT 0,
  steps TEXT []
);

-- TOKEN -----------------------------------------------------------------------
DROP TABLE IF EXISTS token CASCADE;
CREATE TABLE token (
  id SERIAL PRIMARY KEY,
  account_id INTEGER REFERENCES account NOT NULL,
  expiry TIMESTAMP NOT NULL
);

-- FOOD ------------------------------------------------------------------------
DROP TABLE IF EXISTS food CASCADE;
CREATE TABLE food (
  id SERIAL PRIMARY KEY,
  title TEXT NOT NULL,
  serving_count REAL NOT NULL DEFAULT 0,
  serving_size TEXT,
  nutrition_id INTEGER REFERENCES nutrition UNIQUE,
  time_created TIMESTAMP DEFAULT now() NOT NULL,
  time_updated TIMESTAMP DEFAULT now() NOT NULL
);

-- Servings --------------------------------------------------------------------
DROP TABLE IF EXISTS servings CASCADE;
CREATE TABLE servings (
  recipe_id INTEGER REFERENCES recipe NOT NULL,
  food_id INTEGER REFERENCES food NOT NULL,
  quantity REAL DEFAULT 0,
  PRIMARY KEY(food_id, recipe_id)
);


-- Create a GIN index for food table's title row
ALTER TABLE food ADD COLUMN IF NOT EXISTS tsv_food_title tsvector;
CREATE INDEX IF NOT EXISTS  tsv_food_title_idx ON food USING gin(tsv_food_title);
UPDATE food SET tsv_food_title = setweight(to_tsvector(coalesce(title,'')), 'A');

-- Add trigger to automatically update trigger on insert and update
DROP FUNCTION IF EXISTS food_text_search_trigger;
CREATE FUNCTION food_text_search_trigger() RETURNS trigger AS $$
BEGIN new.tsv_food_title :=
  setweight(to_tsvector(coalesce(new.title,'')), 'A');
  return new;
  end
$$ LANGUAGE plpgsql;
CREATE TRIGGER update_search_meta BEFORE INSERT OR UPDATE
ON food FOR EACH ROW EXECUTE PROCEDURE food_text_search_trigger();

-- MEAL ------------------------------------------------------------------------
-- todo
