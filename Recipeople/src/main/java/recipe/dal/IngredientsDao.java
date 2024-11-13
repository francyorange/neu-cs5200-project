package recipe.dal;

import recipe.model.*;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IngredientsDao {
    protected ConnectionManager connectionManager;

    private static IngredientsDao instance = null;
    protected IngredientsDao() {
        connectionManager = new ConnectionManager();
    }
    public static IngredientsDao getInstance() {
        if (instance == null) {
            instance = new IngredientsDao();
        }
        return instance;
    }

    public Ingredients create(Ingredients ingredient) throws SQLException {
        String insertIngredient = "INSERT INTO Ingredients(IngredientId, IngredientName, IngredientType)"
            + " VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
              connection = connectionManager.getConnection();
              insertStmt = connection.prepareStatement(insertIngredient);

              insertStmt.setInt(1, ingredient.getIngredientId());
              insertStmt.setString(2, ingredient.getIngredientName());
              insertStmt.setString(3, ingredient.getIngredientType().name());

              insertStmt.executeUpdate();

              return ingredient;
        } catch (SQLException e) {
              e.printStackTrace();
              throw e;
        } finally {
              if(connection != null) {
                  connection.close();
              }
              if(insertStmt != null) {
                  insertStmt.close();
              }
        }
    }

    public Ingredients delete(Ingredients ingredient) throws SQLException {
        String deleteIngredient = "DELETE FROM Ingredients WHERE IngredientId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteIngredient);
            deleteStmt.setString(1, ingredient.getIngredientId());
            deleteStmt.executeUpdate();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }

    public Ingredients getIngredientById(int ingredientId) throws SQLException {
        String selectIngredient =
            "SELECT IngredientId,IngredientName,IngredientType " +
                "FROM Ingredients " +
                "WHERE IngredientId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectIngredient);
            selectStmt.setInt(1, ingredientId);
            results = selectStmt.executeQuery();

            if(results.next()) {
                int resultIngredientId = results.getInt("IngredientId");
                String ingredientName = results.getString("IngredientName");
                Ingredients.IngredientType ingredientType = Ingredients.IngredientType.valueOf(
                    results.getString("IngredientType");
                )

                Ingredients ingredient = new Ingredients(resultIngredientId, ingredientName, ingredientType);
                return ingredient;
          }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return null;
    }

    public List<Ingredients> getIngredientsForRecipe(int recipeId) throws SQLException {
        List<Ingredients> ingredientsList = new ArrayList<Ingredients>();
        String selectIngredientsForRecipe = "SELECT i.IngredientId, i.IngredientName, i.IngredientType " +
            "FROM Ingredients i " +
            "INNER JOIN RecipeIngredient ri ON i.IngredientId = ri.IngredientId " +
            "WHERE ri.RecipeId = ?;";

        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectIngredientsForRecipe);
            selectStmt.setInt(1, recipeId);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int ingredientId = results.getInt("IngredientId");
                String ingredientName = results.getString("IngredientName");
                Ingredients.IngredientType ingredientType = Ingredients.IngredientType.valueOf(results.getString("IngredientType"));

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
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }

        return ingredientsList;
    }
}