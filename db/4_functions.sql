-- Add function to search for foods
DROP FUNCTION IF EXISTS food_search;
CREATE FUNCTION food_search(TEXT) RETURNS SETOF food AS $$
  BEGIN
    RETURN QUERY
    SELECT food.* FROM food, plainto_tsquery($1) AS q
    WHERE (tsv_food_title @@ q);
  END;
$$ LANGUAGE plpgsql;

-- Add function to search for recipes
DROP FUNCTION IF EXISTS recipe_search;
CREATE FUNCTION recipe_search(TEXT) RETURNS SETOF food AS $$
  BEGIN
    RETURN QUERY
    SELECT food.* FROM food, plainto_tsquery($1) AS q
    WHERE (tsv_food_title @@ q) AND recipe_id IS NOT NULL;
  END;
$$ LANGUAGE plpgsql;

-- Add function to search for ingredients
DROP FUNCTION IF EXISTS ingredient_search;
CREATE FUNCTION ingredient_search(TEXT) RETURNS SETOF food AS $$
  BEGIN
    RETURN QUERY
    SELECT food.* FROM food, plainto_tsquery($1) AS q
    WHERE (tsv_food_title @@ q) AND recipe_id IS NULL;
  END;
$$ LANGUAGE plpgsql;
