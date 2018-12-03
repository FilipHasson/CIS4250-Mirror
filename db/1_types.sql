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
  CREATE TYPE ACTIVITY_LEVEL AS ENUM (
    'extremely_inactive',
    'sedentary',
    'moderately_active',
    'vigorously_active',
    'extremely_active'
  );
  CREATE TYPE COMMENT_TUPLE AS (
    comment_time TIMESTAMP,
    comment TEXT
  );
  EXCEPTION WHEN OTHERS
  THEN
END;
$$;
