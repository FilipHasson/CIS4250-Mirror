package api.dao;

import api.object.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Recipe findById(int id){
        return getRecipeFromResultSet(super.findByInt("recipe","id",id));
    }

    private Recipe getRecipeFromResultSet(ResultSet resultSet){
        try {
            return new Recipe(
                    resultSet.getInt("id"),
                    resultSet.getInt("account_id"),
                    resultSet.getDouble("portions"),
                    Recipe.stringToCategory(resultSet.getString("categories")),
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
