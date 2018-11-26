package api.dao;

import api.object.Favorite;
import api.object.Recipe;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO extends DAO{

    public List<Recipe> findByAccountId(int accountId, String category)
    {
        List<Recipe> recipe = new ArrayList<>();
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
        String tempStr = category.replace("\"", "\'");

        // String query = "SELECT * FROM favorites, recipe WHERE favorites.account_id = " + accountId + " AND favorites.recipe_id = recipe.id AND \'" + tempStr + "\'=ANY(recipe.categories);";

        String query = "select * from recipe where categories @> ANY(select categories from recipe right join (select recipe_id from favorites where account_id = " + accountID + ") as f on recipe.id = f.recipe_id);"

        System.out.println(query);

        try {
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recipe.add(getRecipeFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return recipe;
    }

    private Recipe getRecipeFromResultSet(ResultSet resultSet){
        try {
            Recipe recipe =  new Recipe(
                    resultSet.getInt("recipe_id"),
                    resultSet.getInt("account_id"),
                    Recipe.stringsToCategories((String[])resultSet.getArray("categories").getArray()),
                    (String[])resultSet.getArray("steps").getArray(),
                    resultSet.getInt("view_count"),
                    resultSet.getInt("star_count"),
                    new ArrayList<>(),
                    resultSet.getString("description")
                );

            recipe.setIngredients(new IngredientDAO().getIngredientsByRecipe(recipe.getId()));
            return recipe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
