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
  CREATE TYPE MEAL_TUPLE AS (
    type MEAL_TYPE,
    food_id INTEGER,
    serve_amount INTEGER
  );
  CREATE TYPE COMMENT_TUPLE AS (
    comment_time TIMESTAMP,
    comment TEXT
  );
  EXCEPTION WHEN OTHERS
  THEN
END;
$$;
