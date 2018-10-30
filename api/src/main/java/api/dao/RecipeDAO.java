package api.dao;

import api.object.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Recipe DAO for YUMM app
 * @author Filip Hasson
 * @version 1.0
 * @since 2018-10-26
 */
public class RecipeDAO extends DAO{

    public List<Recipe> findAll(){
        ResultSet resultSet = super.findAll("recipe");
        List<Recipe> recipes = new ArrayList<>();

        try {
            while (resultSet.next()) {
                recipes.add(getRecipeFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    public List<Recipe> findAllOrderByTimeLimit(int limit){
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM recipe, food WHERE food.recipe_id = recipe.id ORDER BY food.time_created LIMIT "+limit;
        List<Recipe> recipes = new ArrayList<>();

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recipes.add(getRecipeFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    public List<Recipe> findByCategory(Recipe.Category category){
        ResultSet resultSet = super.findByString("recipe","category",category.toString());
        List<Recipe> recipes = new ArrayList<>();

        try {
            while (resultSet.next()) {
                recipes.add(getRecipeFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    public Recipe findById(int id){
        if (0 != id) {
            Connection connection = super.connect();
            PreparedStatement statement;
            ResultSet resultSet;
            String query = "SELECT * FROM recipe WHERE id = ?";

            try {
                Recipe recipe;
                statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                resultSet.next();
                recipe = getRecipeFromResultSet(resultSet);
                super.disconnect(connection);
                return recipe;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    public long insertRecipe(Recipe recipe){
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "INSERT INTO recipe (account_id, portions, categories, steps, view_count, star_count) VALUES (?, ?, CAST(? AS recipe_category[]), ?, ?, ?)";
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,recipe.getAccountId());
            statement.setDouble(2,recipe.getPortions());
            Array enumArray = connection.createArrayOf("recipe_category",recipe.getCategories());
            Array stepArray = connection.createArrayOf("TEXT",recipe.getSteps());
            statement.setArray(3,enumArray);
            statement.setArray(4,stepArray);
            statement.setInt(5,recipe.getViews());
            statement.setInt(6,recipe.getStars());

            return super.checkUpdated(connection,statement,statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return 0;
    }

    public int updateRecipe(Recipe recipe){
        Connection connection = super.connect();
        PreparedStatement statement;
        String query = "UPDATE recipe SET account_id = ?, portions = ?, categories = CAST(? AS recipe_category[]), steps = ?, view_count = ?, star_count = ? WHERE id = ?";
        int affectedRows = 0;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,recipe.getAccountId());
            statement.setDouble(2,recipe.getPortions());
            Array enumArray = connection.createArrayOf("recipe_category",recipe.getCategories());
            Array stepArray = connection.createArrayOf("TEXT",recipe.getSteps());
            statement.setArray(3,enumArray);
            statement.setArray(4,stepArray);
            statement.setInt(5,recipe.getViews());
            statement.setInt(6,recipe.getStars());
            statement.setInt(7,recipe.getId());

            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return affectedRows;
    }

    private Recipe getRecipeFromResultSet(ResultSet resultSet){
        try {
            return new Recipe(
                    resultSet.getInt("id"),
                    resultSet.getInt("account_id"),
                    resultSet.getDouble("portions"),
                    Recipe.stringsToCategories((String[])resultSet.getArray("categories").getArray()),
                    (String[])resultSet.getArray("steps").getArray(),
                    resultSet.getInt("view_count"),
                    resultSet.getInt("star_count")
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
