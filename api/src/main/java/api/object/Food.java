package api.object;

import api.dao.NutritionDAO;
import api.dao.RecipeDAO;

import java.time.OffsetDateTime;

/**
 * -- Food
 * CREATE TABLE IF NOT EXISTS food (
 *     id SERIAL PRIMARY KEY,
 *     nutrition_id INTEGER REFERENCES nutrition NOT NULL,
 *     recipe_id INTEGER REFERENCES recipe,
 *     time_created TIMESTAMP NOT NULL,
 *     time_updated TIMESTAMP NOT NULL,
 *     view_count INTEGER DEFAULT 0,
 *     star_count INTEGER DEFAULT 0
 * );
 */


public class Food {
    private int id;
    private int nutritionId;
    private Nutrition nutrition;
    private int recipeId;
    private Recipe recipe;
    private OffsetDateTime timeCreated;
    private OffsetDateTime timeUpdated;

    public Food(int id, int nId, int rId, OffsetDateTime created, OffsetDateTime updated){
        RecipeDAO recipeDAO = new RecipeDAO();
        NutritionDAO nutritionDAO = new NutritionDAO();
        this.id = id;
        this.nutritionId = nId;
        this.recipeId = rId;
        this.timeCreated = created;
        this.timeUpdated = updated;
        this.recipe = recipeDAO.findById(this.recipeId);
        this.nutrition = nutritionDAO.findById(this.nutritionId);
    }

}
