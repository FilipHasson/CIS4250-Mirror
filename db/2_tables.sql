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
  iron_mg REAL DEFAULT 0
  protein_g REAL DEFAULT 0,
  sodium_mg REAL DEFAULT 0,
  vitamin_a_iu REAL DEFAULT 0,
  vitamin_c_mg REAL DEFAULT 0,
);

-- RECIPE ----------------------------------------------------------------------
DROP TABLE IF EXISTS recipe CASCADE;
CREATE TABLE recipe (
  id SERIAL PRIMARY KEY,
  account_id INTEGER REFERENCES account NOT NULL,
  serving_count REAL NOT NULL,
  serving_size TEXT NOT NULL,
  categories RECIPE_CATEGORY [],
  steps TEXT [],
  description TEXT,
  view_count INTEGER DEFAULT 0,
  star_count INTEGER DEFAULT 0
);

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
  nutrition_id INTEGER REFERENCES nutrition UNIQUE,
  recipe_id INTEGER REFERENCES recipe UNIQUE,
  time_created TIMESTAMP DEFAULT now() NOT NULL,
  time_updated TIMESTAMP DEFAULT now() NOT NULL,
  CONSTRAINT title_const UNIQUE(title)
);

-- MEAL ------------------------------------------------------------------------
-- todo
