package recipe.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import recipe.model.*;

public class RecipeIngredientDao {
    protected ConnectionManager connectionManager;

    private static RecipeIngredientDao instance = null;
    protected RecipeIngredientDao() {
        connectionManager = new ConnectionManager();
    }
    public static RecipeIngredientDao getInstance() {
        if (instance == null) {
            instance = new RecipeIngredientDao();
        }
        return instance;
    }

    public RecipeIngredient create(RecipeIngredient recipeIngredient) throws SQLException {
        String sql = "INSERT INTO RecipeIngredient (RecipeId, IngredientId) VALUES (?, ?);";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recipeIngredient.getRecipeId());
            stmt.setInt(2, recipeIngredient.getIngredientId());
            stmt.executeUpdate();
            return recipeIngredient;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public RecipeIngredient getRecipeIngredient(int recipeId, int ingredientId) throws SQLException {
        String sql = "SELECT RecipeId, IngredientId FROM RecipeIngredient WHERE RecipeId = ? AND IngredientId = ?;";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recipeId);
            stmt.setInt(2, ingredientId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Recipes recipe = new Recipes(rs.getInt("RecipeId"));
                Ingredients ingredient = new Ingredients(rs.getInt("IngredientId"));
                return new RecipeIngredient(recipe, ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return null;
    }

    public RecipeIngredient delete(int recipeId, int ingredientId) throws SQLException {
        String sql = "DELETE FROM RecipeIngredient WHERE RecipeId = ? AND IngredientId = ?;";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recipeId);
            stmt.setInt(2, ingredientId);
            stmt.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public List<Ingredients> getIngredientsForRecipe(int recipeId) throws SQLException {
        List<Ingredients> ingredientsList = new ArrayList<>();
        String sql = "SELECT i.IngredientId, i.IngredientName, i.IngredientType " +
                     "FROM RecipeIngredient ri JOIN Ingredients i ON ri.IngredientId = i.IngredientId " +
                     "WHERE ri.RecipeId = ?;";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recipeId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int ingredientId = rs.getInt("IngredientId");
                String ingredientName = rs.getString("IngredientName");
                Ingredients.IngredientType ingredientType = Ingredients.IngredientType.valueOf(rs.getString("IngredientType"));
                Ingredients ingredient = new Ingredients(ingredientId, ingredientName, ingredientType);
                ingredientsList.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return ingredientsList;
    }
}

