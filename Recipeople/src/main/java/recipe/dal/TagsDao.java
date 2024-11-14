package recipe.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import recipe.model.*;

public class TagsDao {
  private Connection conn;

  public TagsDao(Connection conn) {
    this.conn = conn;
  }

  public void createTag(Tags tag) throws SQLException {
    String sql = "INSERT INTO Tags (TagId, TagName, Category) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, tag.getTagId());
      stmt.setString(2, tag.getTagName());
      stmt.setString(3, tag.getCategory());
      stmt.executeUpdate();
    }
  }

  public Tags readTag(int tagId) throws SQLException {
    String sql = "SELECT * FROM Tags WHERE TagId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, tagId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return new Tags(rs.getInt("TagId"), rs.getString("TagName"), rs.getString("Category"));
      }
    }
    return null;
  }

  public void updateTag(int tagId, Tags tag) throws SQLException {
    String sql = "UPDATE Tags SET TagName = ?, Category = ? WHERE TagId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, tag.getTagName());
      stmt.setString(2, tag.getCategory());
      stmt.setInt(3, tagId);
      stmt.executeUpdate();
    }
  }

  public void deleteTag(int tagId) throws SQLException {
    String sql = "DELETE FROM Tags WHERE TagId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, tagId);
      stmt.executeUpdate();
    }
  }
}
