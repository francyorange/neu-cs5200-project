package recipe.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import recipe.model.*;

public class RecipeIngredientDao {
  private Connection conn;

  public RecipeIngredientDao(Connection conn) {
    this.conn = conn;
  }

  public void createRecipeIngredient(RecipeIngredient recipeIngredient) throws SQLException {
    String sql = "INSERT INTO RecipeIngredient (RecipeId, IngredientId) VALUES (?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, recipeIngredient.getRecipeId());
      stmt.setInt(2, recipeIngredient.getIngredientId());
      stmt.executeUpdate();
    }
  }

  public RecipeIngredient readRecipeIngredient(int recipeId, int ingredientId) throws SQLException {
    String sql = "SELECT * FROM RecipeIngredient WHERE RecipeId = ? AND IngredientId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, recipeId);
      stmt.setInt(2, ingredientId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return new RecipeIngredient(rs.getInt("RecipeId"), rs.getInt("IngredientId"));
      }
    }
    return null;
  }

  public void updateRecipeIngredient(int recipeId, int ingredientId, RecipeIngredient recipeIngredient)
      throws SQLException {
    String sql = "UPDATE RecipeIngredient SET RecipeId = ?, IngredientId = ? WHERE RecipeId = ? AND IngredientId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, recipeIngredient.getRecipeId());
      stmt.setInt(2, recipeIngredient.getIngredientId());
      stmt.setInt(3, recipeId);
      stmt.setInt(4, ingredientId);
      stmt.executeUpdate();
    }
  }

  public void deleteRecipeIngredient(int recipeId, int ingredientId) throws SQLException {
    String sql = "DELETE FROM RecipeIngredient WHERE RecipeId = ? AND IngredientId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, recipeId);
      stmt.setInt(2, ingredientId);
      stmt.executeUpdate();
    }
  }
}
