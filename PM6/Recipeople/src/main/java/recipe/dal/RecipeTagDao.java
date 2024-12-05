package recipe.dal;

import recipe.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeTagDao {
    protected ConnectionManager connectionManager;

    private static RecipeTagDao instance = null;
    protected RecipeTagDao() {
        connectionManager = new ConnectionManager();
    }
    public static RecipeTagDao getInstance() {
        if (instance == null) {
            instance = new RecipeTagDao();
        }
        return instance;
    }

    public RecipeTag create(RecipeTag recipeTag) throws SQLException {
        String sql = "INSERT INTO RecipeTag (RecipeId, TagId) VALUES (?, ?);";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recipeTag.getRecipeId());
            stmt.setInt(2, recipeTag.getTagId());
            stmt.executeUpdate();
            return recipeTag;
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

    public RecipeTag getRecipeTag(int recipeId, int tagId) throws SQLException {
        String sql = "SELECT RecipeId, TagId FROM RecipeTag WHERE RecipeId = ? AND TagId = ?;";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recipeId);
            stmt.setInt(2, tagId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Recipes recipe = new Recipes(rs.getInt("RecipeId"));
                Tags tag = new Tags(rs.getInt("TagId"));
                return new RecipeTag(recipe, tag);
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

    public RecipeTag delete(int recipeId, int tagId) throws SQLException {
        String sql = "DELETE FROM RecipeTag WHERE RecipeId = ? AND TagId = ?;";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recipeId);
            stmt.setInt(2, tagId);
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

    public List<Integer> findRecipesByTagName(String tagName) throws SQLException {
        String sql = "SELECT rt.RecipeId FROM RecipeTag rt " +
                     "JOIN Tags t ON rt.TagId = t.TagId " +
                     "WHERE t.TagName = ?;";
        List<Integer> recipeIds = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, tagName);
            rs = stmt.executeQuery();

            while (rs.next()) {
                recipeIds.add(rs.getInt("RecipeId"));
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
        return recipeIds;
    }
}

