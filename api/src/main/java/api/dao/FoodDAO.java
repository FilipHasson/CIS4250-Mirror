package api.dao;

import api.object.Food;
import api.object.Recipe;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Food DAO for YUMM app
 * @author Filip Hasson
 * @version 1.0
 * @since 2018-10-26
 */
public class FoodDAO extends DAO{

    public List<Food> findAll(){
        ResultSet resultSet = super.findAll("food");
        List<Food> foods = new ArrayList<>();

        try {
            while (resultSet.next()) {
                foods.add(getFoodFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foods;
    }

    public List<Food> findAllOrderByFieldLimit(String field, int limit){
        ResultSet resultSet =  super.findAllOrderByFieldLimit("food",field,limit);
        List<Food> foods = new ArrayList<>();

        try {
            while (resultSet.next()) {
                foods.add(getFoodFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foods;
    }

    public List<Integer> findAllIds(){
        ResultSet resultSet = super.findAllField("food", "id");
        List<Integer> foods = new ArrayList<>();

        try {
            while (resultSet.next()){
                foods.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foods;
    }

    public List<Food> findByRecipeCategory(String category){
        return findByRecipeCategory(Recipe.stringToCategory(category));
    }

    public List<Food> findByAccountId(int accountId){
        List<Food> foods = new ArrayList<>();
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM food, recipe WHERE food.recipe_id = recipe.id AND recipe.account_id = ?";

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,accountId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                foods.add(getFoodFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return foods;
    }


    public List<Food> findByRecipeCategory(Recipe.Category category){
        List<Food> foods = new ArrayList<>();
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM food, recipe WHERE food.recipe_id = recipe.id AND recipe.categories @> ARRAY[?::recipe_category]";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,category.toString().toLowerCase());

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                foods.add(getFoodFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return foods;
    }

    public Food findByInt(String field, int searchIndex){
        if (0 != searchIndex) {
            try {
                ResultSet resultSet = super.findByInt("food",field,searchIndex);
                resultSet.next();
                return getFoodFromResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Food findById(int id){
        return findByInt("id",id);
    }

    public Food findByRecipeId(int id){
        return findByInt("recipe_id",id);
    }

    public long insertFood(Food food){
        Connection connection = super.connect();
        PreparedStatement statement;
        String query = "INSERT INTO food (title, serving_count, serving_size, nutrition_id, recipe_id, time_created, time_updated) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,food.getTitle());
            statement.setDouble(2,food.getServing_count());
            statement.setString(3,food.getServing_size());
            statement.setInt(4,food.getNutritionId());
            statement.setLong(5,food.getRecipeId());
            statement.setObject(6,food.getTimeCreated());
            statement.setObject(7,food.getTimeUpdated());

            return super.checkUpdated(connection,statement,statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return 0;
    }

    public int updateFood(Food food){
        Connection connection = super.connect();
        PreparedStatement statement;
        int affectedRows = 0;
        String query = "UPDATE food SET title = ?, serving_count = ?, serving_size = ?, time_updated  = ? WHERE id = ?";

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,food.getTitle());
            statement.setDouble(2,food.getServing_count());
            statement.setString(3,food.getServing_size());
            statement.setObject(4,food.getTimeUpdated());
            statement.setInt(5,food.getId());

            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return affectedRows;
    }

    public List<Food> search(String title){
//        SELECT food.* FROM food, plainto_tsquery($1) AS q
//        WHERE (tsv_food_title @@ q) AND recipe_id IS NULL;
        List<Food> foods = new ArrayList<>();
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
//        String query = "SELECT * FROM food, recipe WHERE food.recipe_id = recipe.id AND recipe.account_id = ?";

        String query = "SELECT * FROM food, plainto_tsquery(?) AS q WHERE (tsv_food_title @@ q) AND recipe_id IS NULL LIMIT 10";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,title);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                foods.add(getFoodFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return foods;
    }

    public int deleteFood(Food food){
        Connection connection = super.connect();
        PreparedStatement statement;
        int affectedRows = 0;
        int c;
        String deleteFood = "DELETE FROM food WHERE id = ?";
        String deleteServings = "DELETE FROM servings WHERE recipe_id = ?";
        String deleteNutrition = "DELETE FROM nutrition WHERE id = ?";
        String deleteRecipe = "DELETE FROM recipe WHERE id = ?";


        try{
            statement = connection.prepareStatement(deleteFood);
            statement.setInt(1,food.getId());
            c = statement.executeUpdate();
            if (0 == c) System.out.println("Did not delete FOOD");
            affectedRows += c;


            statement = connection.prepareStatement(deleteServings);
            statement.setInt(1, food.getRecipeId());
            c = statement.executeUpdate();
            if (0 == c) System.out.println("Did not delete Servings");
            affectedRows += c;

            statement = connection.prepareStatement(deleteNutrition);
            statement.setInt(1, food.getNutritionId());
            c = statement.executeUpdate();
            if (0 == c) System.out.println("Did not delete Nutrition");
            affectedRows += c;

            statement = connection.prepareStatement(deleteRecipe);
            statement.setInt(1, food.getRecipeId());
            c = statement.executeUpdate();
            if (0 == c) System.out.println("Did not delete Recipe");
            affectedRows += c;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return affectedRows;
    }

    private Food getFoodFromResultSet(ResultSet resultSet){
        try {
            return new Food(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getDouble("serving_count"),
                    resultSet.getString("serving_size"),
                    resultSet.getInt("nutrition_id"),
                    resultSet.getInt("recipe_id"),
                    resultSet.getObject("time_created", OffsetDateTime.class),
                    resultSet.getObject("time_updated", OffsetDateTime.class)
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
