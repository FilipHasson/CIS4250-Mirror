package api.dao;

import api.object.Food;
import api.object.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Food findById(int id){
        if (0 != id) {
            try {
                ResultSet resultSet = super.findByInt("food","id",id);
                resultSet.next();
                return getFoodFromResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Food getFoodFromResultSet(ResultSet resultSet){
        try {
            return new Food(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
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
