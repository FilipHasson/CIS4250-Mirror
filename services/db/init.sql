-- init.sql
-- run by docker container on container creation to initialize database

-- EXTENSIONS ------------------------------------------------------------------

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- TYPES -----------------------------------------------------------------------

DO LANGUAGE plpgsql
$$
BEGIN
	CREATE TYPE VISIBILITY AS ENUM (
		'public',
		'private',
		'deleted',
		'archived',
		'banned'
	);
	CREATE TYPE PRIVILEGE AS ENUM (
		'administrator',
		'moderator',
		'user'
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
	password CHAR(64),
	email VARCHAR(128),
	priviledge PRIVILEGE NOT NULL DEFAULT 'user',
	status VISIBILITY NOT NULL DEFAULT 'public'
);
CREATE UNIQUE INDEX IF NOT EXISTS unique_username_idx
	ON account (trim(lower(username)));
CREATE UNIQUE INDEX IF NOT EXISTS unique_email_idx
	ON account (trim(lower(email)));

-- Category
CREATE TABLE IF NOT EXISTS category (
	id SERIAL PRIMARY KEY,
	name VARCHAR(25)
);

-- Nutrition
CREATE TABLE IF NOT EXISTS nutrition (
	id SERIAL PRIMARY KEY,

	carbohydrate_fiber REAL DEFAULT 0,
	carbohydrate_sugar REAL DEFAULT 0,
	fat_monounsaturated REAL DEFAULT 0,
	fat_polyunsaturated REAL DEFAULT 0,
	fat_saturated REAL DEFAULT 0,
	fat_trans REAL DEFAULT 0,
	protein REAL DEFAULT 0,

	betaine REAL DEFAULT 0,
	folate REAL DEFAULT 0,
	folic_acid REAL DEFAULT 0,
	niacin REAL DEFAULT 0,
	pantothenic_acid REAL DEFAULT 0,
	riboflavin REAL DEFAULT 0,
	thiamin REAL DEFAULT 0,
	vitamin_a REAL DEFAULT 0,
	vitamin_b12 REAL DEFAULT 0,
	vitamin_b6 REAL DEFAULT 0,
	vitamin_c REAL DEFAULT 0,
	vitamin_d REAL DEFAULT 0,
	vitamin_e REAL DEFAULT 0,
	vitamin_k REAL DEFAULT 0,

	calcium REAL DEFAULT 0,
	copper REAL DEFAULT 0,
	flouride REAL DEFAULT 0,
	iron REAL DEFAULT 0,
	magnesium REAL DEFAULT 0,
	manganese REAL DEFAULT 0,
	phosphorus REAL DEFAULT 0,
	potassiom REAL DEFAULT 0,
	selenium REAL DEFAULT 0,
	sodium REAL DEFAULT 0,
	zinc REAL DEFAULT 0,

	alcohol REAL DEFAULT 0,
	caffeine REAL DEFAULT 0,
	water REAL DEFAULT 0
);

CREATE OR REPLACE VIEW nutrition_macros AS
	SELECT
		carbohydrate_fiber +
		carbohydrate_sugar
			AS carbohydrates,
		fat_monounsaturated +
		fat_polyunsaturated +
		fat_saturated +
		fat_trans
			AS fats,
		protein
	FROM
		nutrition;

CREATE OR REPLACE VIEW nutrition_vitamins AS
	SELECT
		betaine,
		folate +
		folic_acid
			AS vitamin_b9,
		niacin,
		pantothenic_acid,
		riboflavin,
		thiamin,
		vitamin_a,
		vitamin_b12,
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
		flouride,
		iron,
		magnesium,
		manganese,
		phosphorus,
		potassiom,
		selenium,
		sodium,
		zinc
	FROM
		nutrition;

-- Recipe
CREATE TABLE IF NOT EXISTS recipe (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50),
	header VARCHAR(100),
	description TEXT,
	author INTEGER NOT NULL REFERENCES account,
	date_created TIMESTAMP NOT NULL,
	date_modified TIMESTAMP NOT NULL
);

-- Food
CREATE TABLE IF NOT EXISTS food (
	id SERIAL PRIMARY KEY,
	category_id INTEGER REFERENCES category NOT NULL,
	nutrition_id INTEGER REFERENCES nutrition NOT NULL,
	recipe_id INTEGER REFERENCES recipe,
	date_created TIMESTAMP NOT NULL,
	date_updated TIMESTAMP NOT NULL,
	view_count INTEGER DEFAULT 0,
	star_count INTEGER DEFAULT 0,
	status VISIBILITY DEFAULT 'public'
);

-- Step
CREATE TABLE IF NOT EXISTS step (
	id SERIAL PRIMARY KEY,
	recipe_id INTEGER NOT NULL REFERENCES recipe,
	position INTEGER NOT NULL,
	description TEXT
);

-- Meal
CREATE TABLE IF NOT EXISTS meal (
	id SERIAL PRIMARY KEY,
	account_id INTEGER NOT NULL REFERENCES account,
	date DATE,
	position INTEGER NOT NULL DEFAULT 0,
	type VARCHAR(25)
);
CREATE UNIQUE INDEX IF NOT EXISTS unique_combination_idx
	ON meal (account_id, date, position, trim(lower(type)));
