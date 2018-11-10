package api.dao;

import api.object.Ingredient;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientDAO extends DAO {

    public ArrayList<Ingredient> getIngredientsByRecipe(int id){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM servings WHERE recipe_id = ?";

        try{
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);

            resultSet= statement.executeQuery();
            while (resultSet.next()){
                ingredients.add(new Ingredient(id,
                        resultSet.getInt("food_id"),
                        resultSet.getDouble("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return ingredients;
    }

    public int insertIngredients(ArrayList<Ingredient> ingredients){
        int count = 0;
        for (Ingredient ingredient : ingredients){
            count += insertIngredient(ingredient);
        }
        return count;
    }

    public int insertIngredient(Ingredient ingredient){
        Connection connection = super.connect();
        PreparedStatement statement;
        String query = "INSERT INTO servings (recipe_id, food_id, quantity) VALUES (?, ?, ?)";

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,ingredient.getRecipe_id());
            statement.setInt(2,ingredient.getFood_id());
            statement.setDouble(3,ingredient.getQuantity());

            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteByRecipeId(int id){
        return super.deleteByInt("servings","recipe_id", id);
    }
}
