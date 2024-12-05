package recipe.dal;

import recipe.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NutritionsDao {
    protected ConnectionManager connectionManager;

    private static NutritionsDao instance = null;

    protected NutritionsDao() {
        connectionManager = new ConnectionManager();
    }

    public static NutritionsDao getInstance() {
        if (instance == null) {
            instance = new NutritionsDao();
        }
        return instance;
    }

    public Nutritions create(Nutritions nutrition) throws SQLException {
        String insertNutrition = "INSERT INTO Nutrition(Calories, TotalFat, Sugar, Sodium, Protein, SaturatedFat, Carbohydrates, RecipeId) VALUES(?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertNutrition, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setFloat(1, nutrition.getCalories());
            insertStmt.setFloat(2, nutrition.getTotalFat());
            insertStmt.setFloat(3, nutrition.getSugar());
            insertStmt.setFloat(4, nutrition.getSodium());
            insertStmt.setFloat(5, nutrition.getProtein());
            insertStmt.setFloat(6, nutrition.getSaturatedFat());
            insertStmt.setFloat(7, nutrition.getCarbohydrates());
            insertStmt.setInt(8, nutrition.getRecipe().getRecipeId());
            insertStmt.executeUpdate();

            return nutrition;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
        }
    }

    public Nutritions getNutritionByRecipeId(int recipeId) throws SQLException {
        String selectNutrition = "SELECT Calories, TotalFat, Sugar, Sodium, Protein, SaturatedFat, Carbohydrates, RecipeId FROM Nutrition WHERE RecipeId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectNutrition);
            selectStmt.setInt(1, recipeId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                float calories = results.getFloat("Calories");
                float totalFat = results.getFloat("TotalFat");
                float sugar = results.getFloat("Sugar");
                float sodium = results.getFloat("Sodium");
                float protein = results.getFloat("Protein");
                float saturatedFat = results.getFloat("SaturatedFat");
                float carbohydrates = results.getFloat("Carbohydrates");
                RecipesDao recipeDao = RecipesDao.getInstance();
                Recipes recipe = recipeDao.getRecipeById(recipeId);
                Nutritions nutrition = new Nutritions(calories, totalFat, sugar, sodium, protein,
                        saturatedFat, carbohydrates, recipe);
                return nutrition;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return null;
    }

    public Nutritions delete(Nutritions nutrition) throws SQLException {
        String deleteNutrition = "DELETE FROM Nutrition WHERE RecipeId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteNutrition);
            deleteStmt.setInt(1, nutrition.getRecipe().getRecipeId());
            deleteStmt.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
}