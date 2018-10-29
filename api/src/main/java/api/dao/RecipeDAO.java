package api.dao;

import api.object.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;
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
            ResultSet resultSet = super.findByInt("recipe", "id", id);
            try {
                resultSet.next();
                return getRecipeFromResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Recipe getRecipeFromResultSet(ResultSet resultSet){
        try {
            return new Recipe(
                    resultSet.getInt("id"),
                    resultSet.getInt("account_id"),
                    resultSet.getDouble("portions"),
                    //Recipe.stringToCategory(resultSet.getString("categories")), // Single Category
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
