package recipe.dal;

import recipe.model.*;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InteractionsDao {
    protected ConnectionManager connectionManager;

    private static InteractionsDao instance = null;
    protected InteractionsDao() {
        connectionManager = new ConnectionManager();
    }
    public static InteractionsDao getInstance() {
        if(instance == null) {
            instance = new InteractionsDao();
        }
        return instance;
    }

    public Interactions create(Interactions interaction) throws SQLException {
        String insertInteraction = "INSERT INTO Interactions(UserId, RecipeId, InteractionType)"
            + " VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();

            insertStmt = connection.prepareStatement(insertInteraction,
                Statement.RETURN_GENERATED_KEYS);

            insertStmt.setInt(1, interaction.getUser().getUserId());
            insertStmt.setInt(2, interaction.getRecipe().getRecipeId());
            insertStmt.setString(3, ingredient.getInteractionType().name());

            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int interactionId = -1;
            if (resultKey.next()) {
                interactionId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            interaction.setInteractionId(interactionId);
            return interaction;
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

    public Interactions delete(Interactions interaction) throws SQLException {
        String deleteInteraction = "DELETE FROM Interactions WHERE InteractionId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteInteraction);
            deleteStmt.setInt(1, interaction.getInteractionId());
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

    public Interactions getInteractionById(int interactionId) throws SQLException {
        String selectInteraction = "SELECT InteractionId, UserId, RecipeId, InteractionType "
            + "FROM Interactions WHERE InteractionId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectInteraction);
            selectStmt.setInt(1, interactionId);
            results = selectStmt.executeQuery();

            UsersDao usersDao = UsersDao.getInstance();
            RecipesDao recipesDao = RecipesDao.getInstance();

            if(results.next()) {
                int resultInteractionId = results.getInt("InteractionId");

                int userId = results.getInt("UserId");
                int recipeId = results.getInt("RecipeId");

                Users user = usersDao.getUserById(userId);
                Recipes recipe = recipesDao.getRecipeById(recipeId);

                Interactions.InteractionType interactionType = Interactions.InteractionType.valueOf(
                    results.getString("InteractionType"));
                )

                Interactions interaction = new Interactions(resultInteractionId, user, recipe, interactionType);
                return interaction;
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

    public List<Interactions> getInteractionsForRecipe(Recipes recipe) throws SQLException {
        List<Interactions> interactions = new ArrayList<Interactions>();
        String selectInteractions = "SELECT InteractionId, UserId, RecipeId, InteractionType "
            + "FROM Interactions WHERE RecipeId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectInteractions);
            selectStmt.setString(1, recipe.getRecipeId());
            results = selectStmt.executeQuery();

            UsersDao usersDao = UsersDao.getInstance();
            RecipesDao recipesDao = RecipesDao.getInstance();

            while(results.next()) {
                int interactionId = results.getInt("InteractionId");

                int userId = results.getInt("UserId");
                int recipeId = results.getInt("RecipeId");

                Users user = usersDao.getUserById(userId);
                Recipes resultRecipe = recipesDao.getRecipeById(recipeId);

                Interactions.InteractionType interactionType = Interactions.InteractionType.valueOf(
                    results.getString("InteractionType"));
                )

                Interactions interaction = new Interactions(InteractionId, user, resultRecipe, interactionType);
                interactions.add(interaction);
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
        return interactions;
    }

    public List<Interactions> getInteractionsForUser(Users user) throws SQLException {
        List<Interactions> interactions = new ArrayList<Interactions>();
        String selectInteractions = "SELECT InteractionId, UserId, RecipeId, InteractionType "
            + "FROM Interactions WHERE UserId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectInteractions);
            selectStmt.setString(1, user.getUserId());
            results = selectStmt.executeQuery();

            UsersDao usersDao = UsersDao.getInstance();
            RecipesDao recipesDao = RecipesDao.getInstance();

            while(results.next()) {
                int interactionId = results.getInt("InteractionId");

                int userId = results.getInt("UserId");
                int recipeId = results.getInt("RecipeId");

                Users resultUser = usersDao.getUserById(userId);
                Recipes recipe = recipesDao.getRecipeById(recipeId);

                Interactions.InteractionType interactionType = Interactions.InteractionType.valueOf(
                    results.getString("InteractionType"));
                )

                Interactions interaction = new Interactions(InteractionId, resultUser, recipe, interactionType);
                interactions.add(interaction);
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
        return interactions;
    }
}

