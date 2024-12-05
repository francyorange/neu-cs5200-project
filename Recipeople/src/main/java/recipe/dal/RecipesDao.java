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
        String insertRecipe = "INSERT INTO Recipes(RecipeName, Minutes, Steps, Description, SubmittedAt, ContributorId) VALUES(?,?,?,?,?,?);";
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
        String selectRecipe = "SELECT RecipeId, RecipeName, Minutes, Steps, Description, SubmittedAt, ContributorId FROM Recipes WHERE RecipeId=?;";
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

    public List<Users> getContributorsForRecipe(Recipes recipe) throws SQLException {
        List<Users> contributors = new ArrayList<>();
        String selectContributors = "SELECT ContributorId FROM Recipes WHERE RecipeId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectContributors);
            selectStmt.setInt(1, recipe.getRecipeId());
            results = selectStmt.executeQuery();

            UsersDao usersDao = UsersDao.getInstance();
            while (results.next()) {
                int contributorId = results.getInt("ContributorId");
                Users user = usersDao.getUserById(contributorId);
                contributors.add(user);
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
        return contributors;
    }

    public Recipes delete(Recipes recipe) throws SQLException {
        String deleteRecipe = "DELETE FROM Recipes WHERE RecipeId=?;";
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
            if (connection != null) {
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    public List<Recipes> getRecipeRecommendations(Users user) throws SQLException {
        List<Recipes> recipes = new ArrayList<>();
        String selectRecipes = "SELECT R.RecipeId, COUNT(I.InteractionId) AS TotalLikes " +
                "FROM Recipes R " +
                "JOIN Interactions I ON R.RecipeId = I.RecipeId " +
                "JOIN Users U ON I.UserId = U.UserId " +
                "WHERE I.InteractionType = 'Like' " +
                "AND U.HealthGoal = ? " +
                "GROUP BY R.RecipeId " +
                "ORDER BY TotalLikes DESC " +
                "LIMIT 3;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecipes);
            selectStmt.setString(1, user.getHealthGoal().name());
            results = selectStmt.executeQuery();

            while (results.next()) {
                int resultRecipeId = results.getInt("RecipeId");
                Recipes recipe = getRecipeById(resultRecipeId);
                recipes.add(recipe);
            }
            return recipes;
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
    }
}
