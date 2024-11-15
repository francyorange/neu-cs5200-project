package recipe.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import recipe.model.*;

public class RecipeTagDao {
  private Connection conn;

  public RecipeTagDao(Connection conn) {
    this.conn = conn;
  }

  public void createRecipeTag(RecipeTag recipeTag) throws SQLException {
    String sql = "INSERT INTO RecipeTag (RecipeId, TagId) VALUES (?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, recipeTag.getRecipeId());
      stmt.setInt(2, recipeTag.getTagId());
      stmt.executeUpdate();
    }
  }

  public RecipeTag readRecipeTag(int recipeId, int tagId) throws SQLException {
    String sql = "SELECT * FROM RecipeTag WHERE RecipeId = ? AND TagId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, recipeId);
      stmt.setInt(2, tagId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return new RecipeTag(rs.getInt("RecipeId"), rs.getInt("TagId"));
      }
    }
    return null;
  }

  public void updateRecipeTag(int recipeId, int tagId, RecipeTag recipeTag) throws SQLException {
    String sql = "UPDATE RecipeTag SET RecipeId = ?, TagId = ? WHERE RecipeId = ? AND TagId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, recipeTag.getRecipeId());
      stmt.setInt(2, recipeTag.getTagId());
      stmt.setInt(3, recipeId);
      stmt.setInt(4, tagId);
      stmt.executeUpdate();
    }
  }

  public void deleteRecipeTag(int recipeId, int tagId) throws SQLException {
    String sql = "DELETE FROM RecipeTag WHERE RecipeId = ? AND TagId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, recipeId);
      stmt.setInt(2, tagId);
      stmt.executeUpdate();
    }
  }

  public List<Integer> findRecipesByTagCategory(String category) throws SQLException {
    String sql = "SELECT RecipeId FROM RecipeTag rt JOIN Tags t ON rt.TagId = t.TagId WHERE t.Category = ?";
    List<Integer> recipeIds = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, category);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        recipeIds.add(rs.getInt("RecipeId"));
      }
    }
    return recipeIds;
  }
}
