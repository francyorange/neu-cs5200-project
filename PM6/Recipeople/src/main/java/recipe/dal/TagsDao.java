package recipe.dal;

import recipe.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagsDao {
    protected ConnectionManager connectionManager;

    private static TagsDao instance = null;
    protected TagsDao() {
        connectionManager = new ConnectionManager();
    }
    public static TagsDao getInstance() {
        if (instance == null) {
            instance = new TagsDao();
        }
        return instance;
    }

    public Tags create(Tags tag) throws SQLException {
        String insertTag = "INSERT INTO Tags(TagId, TagName, Category) VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertTag);

            insertStmt.setInt(1, tag.getTagId());
            insertStmt.setString(2, tag.getTagName());
            insertStmt.setString(3, tag.getCategory().name());

            insertStmt.executeUpdate();
            return tag;
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

    public Tags delete(Tags tag) throws SQLException {
        String deleteTag = "DELETE FROM Tags WHERE TagId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteTag);
            deleteStmt.setInt(1, tag.getTagId());
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

    public Tags getTagById(int tagId) throws SQLException {
        String selectTag = "SELECT TagId, TagName, Category FROM Tags WHERE TagId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTag);
            selectStmt.setInt(1, tagId);
            results = selectStmt.executeQuery();

            if(results.next()) {
                int resultTagId = results.getInt("TagId");
                String tagName = results.getString("TagName");
                Tags.Category category = Tags.Category.valueOf(results.getString("Category"));  // Convert String to Category enum

                Tags tag = new Tags(resultTagId, tagName, category);
                return tag;
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

    public List<Tags> getTagsForRecipe(int recipeId) throws SQLException {
        List<Tags> tagsList = new ArrayList<>();
        String selectTagsForRecipe = "SELECT t.TagId, t.TagName, t.Category " +
                                     "FROM Tags t INNER JOIN RecipeTags rt ON t.TagId = rt.TagId " +
                                     "WHERE rt.RecipeId = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTagsForRecipe);
            selectStmt.setInt(1, recipeId);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int tagId = results.getInt("TagId");
                String tagName = results.getString("TagName");
                Tags.Category category = Tags.Category.valueOf(results.getString("Category"));  // Convert String to Category enum

                Tags tag = new Tags(tagId, tagName, category);
                tagsList.add(tag);
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
        return tagsList;
    }
}
