-- Add function to search for foods
CREATE FUNCTION food_search(TEXT) RETURNS SETOF food AS $$
  BEGIN
    RETURN QUERY
    SELECT food.* FROM food, plainto_tsquery($1) AS q
    WHERE (tsv_food_title @@ q);
  END;
$$ LANGUAGE plpgsql;

-- Add function to search for recipes
CREATE FUNCTION recipe_search(TEXT) RETURNS SETOF food AS $$
  BEGIN
    RETURN QUERY
    SELECT food.* FROM food, plainto_tsquery($1) AS q
    WHERE (tsv_food_title @@ q) AND recipe_id IS NOT NULL;
  END;
$$ LANGUAGE plpgsql;

-- Add function to search for ingredients
CREATE FUNCTION ingredient_search(TEXT) RETURNS SETOF food AS $$
  BEGIN
    RETURN QUERY
    SELECT food.* FROM food, plainto_tsquery($1) AS q
    WHERE (tsv_food_title @@ q) AND recipe_id IS NULL;
  END;
$$ LANGUAGE plpgsql;
