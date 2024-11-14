package recipe.dal;

import recipe.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RecipesDao {

    protected ConnectionManager connectionManager;

    private static RecipesDao instance = null;

    protected RecipesDao() {
        connectionManager = new ConnectionManager();
    }

    public static RecipesDao getInstance() {
        if (instance == null) {
            instance = new RecipesDao();
        }
        return instance;
    }

    public Recipes create(Recipes recipe) throws SQLException {
        String insertRecipe = "INSERT INTO Recipe(RecipeName, Minutes, Steps, Description, SubmittedAt, ContributorId) VALUES(?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRecipe, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, recipe.getRecipeName());
            insertStmt.setInt(2, recipe.getMinutes());
            insertStmt.setString(3, recipe.getSteps());
            insertStmt.setString(4, recipe.getDescription());
            insertStmt.setTimestamp(5, recipe.getSubmittedAt());
            insertStmt.setInt(6, recipe.getUser().getUserId());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int recipeId = -1;
            if (resultKey.next()) {
                recipeId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            recipe.setRecipeId(recipeId);
            return recipe;
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
            if (resultKey != null) {
                resultKey.close();
            }
        }
    }

    public Recipes getRecipeById(int recipeId) throws SQLException {
        String selectRecipe = "SELECT RecipeId, RecipeName, Minutes, Steps, Description, SubmittedAt, ContributorId FROM Recipe WHERE RecipeId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecipe);
            selectStmt.setInt(1, recipeId);
            results = selectStmt.executeQuery();

            UsersDao usersDao = UsersDao.getInstance();
            if (results.next()) {
                int resultRecipeId = results.getInt("RecipeId");
                String recipeName = results.getString("RecipeName");
                int minutes = results.getInt("Minutes");
                String steps = results.getString("Steps");
                String description = results.getString("Description");
                Timestamp submittedAt = results.getTimestamp("SubmittedAt");
                int contributorId = results.getInt("ContributorId");

                Users user = usersDao.getUserById(contributorId);

                Recipes recipe = new Recipes(resultRecipeId, recipeName, minutes, steps, description, submittedAt,
                    user);
                return recipe;
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

    public List<Recipes> getRecipesByContributorId(int contributorId) throws SQLException {
        List<Recipes> recipes = new ArrayList<Recipes>();
        String selectRecipes = "SELECT RecipeId, RecipeName, Minutes, Steps, Description, SubmittedAt, ContributorId FROM Recipe WHERE ContributorId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecipes);
            selectStmt.setInt(1, contributorId);
            results = selectStmt.executeQuery();

            UsersDao usersDao = UsersDao.getInstance();
            Users user = usersDao.getUserById(contributorId);
            while (results.next()) {
                int recipeId = results.getInt("RecipeId");
                String recipeName = results.getString("RecipeName");
                int minutes = results.getInt("Minutes");
                String steps = results.getString("Steps");
                String description = results.getString("Description");
                Timestamp submittedAt = results.getTimestamp("SubmittedAt");

                Recipes recipe = new Recipes(recipeId, recipeName, minutes, steps, description, submittedAt,
                    user);
                recipes.add(recipe);
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
        return recipes;
    }

    public Recipes delete(Recipes recipe) throws SQLException {
        String deleteRecipe = "DELETE FROM Recipe WHERE RecipeId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteRecipe);
            deleteStmt.setInt(1, recipe.getRecipeId());
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

    public Recipes updateDescription(Recipes recipe, String newDescription) throws SQLException {
        String updateRecipe = "UPDATE Recipes SET Description=?,SubmittedAt=? WHERE RecipeId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateRecipe);
            updateStmt.setString(1, newDescription);

            Timestamp newSubmittedAtTimestamp = new Timestamp(System.currentTimeMillis());
            updateStmt.setTimestamp(2, newSubmittedAtTimestamp);

            updateStmt.setInt(3, recipe.getRecipeId());
            updateStmt.executeUpdate();

            recipe.setDescription(newDescription);
            recipe.setSubmittedAt(newSubmittedAtTimestamp);
            return recipe;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    public List<Recipes> getMostLikedRecipes(Integer k) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}