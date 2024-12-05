package recipe.dal;

import recipe.model.*;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ReviewsDao {
    protected ConnectionManager connectionManager;

    private static ReviewsDao instance = null;
    protected ReviewsDao() {
        connectionManager = new ConnectionManager();
    }
    public static ReviewsDao getInstance() {
        if(instance == null) {
            instance = new ReviewsDao();
        }
        return instance;
    }

    public Reviews create(Reviews review) throws SQLException {
        String insertReview = "INSERT INTO Reviews(Content, Rating, "
            + "Created, UserId, RecipeId)"
            + " VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();

            insertStmt = connection.prepareStatement(insertReview,
                Statement.RETURN_GENERATED_KEYS);

            insertStmt.setString(1, review.getContent());
            insertStmt.setInt(2, review.getRating());
            insertStmt.setTimestamp(3, review.getCreated());
            insertStmt.setInt(4, review.getUser().getUserId());
            insertStmt.setInt(5, review.getRecipe().getRecipeId());

            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int reviewId = -1;
            if (resultKey.next()) {
                reviewId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            review.setReviewId(reviewId);
            return review;
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

    public Reviews updateContent(Reviews review, String newContent) throws SQLException {
        String updateReview = "UPDATE Reviews SET Content=?,Created=? WHERE ReviewId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateReview);
            updateStmt.setString(1, newContent);

            Timestamp newCreatedTimestamp = new Timestamp(System.currentTimeMillis());
            updateStmt.setTimestamp(2, newCreatedTimestamp);

            updateStmt.setInt(3, review.getReviewId());
            updateStmt.executeUpdate();

            review.setContent(newContent);
            review.setCreated(newCreatedTimestamp);
            return review;
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

    public Reviews delete(Reviews review) throws SQLException {
        String deleteReview = "DELETE FROM Reviews WHERE ReviewId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteReview);
            deleteStmt.setInt(1, review.getReviewId());
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

    public Reviews getReviewById(int reviewId) throws SQLException {
        String selectReview = "SELECT ReviewId, Content, Rating, Created, UserId, RecipeId "
            + "FROM Reviews WHERE ReviewId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectReview);
            selectStmt.setInt(1, reviewId);
            results = selectStmt.executeQuery();

            UsersDao usersDao = UsersDao.getInstance();
            RecipesDao recipesDao = RecipesDao.getInstance();

            if(results.next()) {
                int resultReviewId = results.getInt("ReviewId");
                String content = results.getString("Content");
                int rating = results.getInt("Rating");
                Timestamp created = results.getTimestamp("Created");
                int userId = results.getInt("UserId");
                int recipeId = results.getInt("RecipeId");

                Users user = usersDao.getUserById(userId);
                Recipes recipe = recipesDao.getRecipeById(recipeId);

                Reviews review = new Reviews(resultReviewId, content, rating, created, user, recipe);
                return review;
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

    public List<Reviews> getReviewsForRecipe(Recipes recipe) throws SQLException {
        List<Reviews> reviews = new ArrayList<Reviews>();
        String selectReviews = "SELECT ReviewId, Content, Rating, Created, UserId, RecipeId "
            + "FROM Reviews WHERE RecipeId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectReviews);
            selectStmt.setInt(1, recipe.getRecipeId());
            results = selectStmt.executeQuery();

            UsersDao usersDao = UsersDao.getInstance();
            RecipesDao recipesDao = RecipesDao.getInstance();

            while(results.next()) {
                int reviewId = results.getInt("ReviewId");
                String content = results.getString("Content");
                int rating = results.getInt("Rating");
                Timestamp created = results.getTimestamp("Created");
                int userId = results.getInt("UserId");
                int recipeId = results.getInt("RecipeId");

                Users user = usersDao.getUserById(userId);
                Recipes resultRecipe = recipesDao.getRecipeById(recipeId);

                Reviews review = new Reviews(reviewId, content, rating, created, user, resultRecipe);
                reviews.add(review);
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
        return reviews;
    }

    public List<Reviews> getReviewsForUser(Users user) throws SQLException {
        List<Reviews> reviews = new ArrayList<Reviews>();
        String selectReviews = "SELECT ReviewId, Content, Rating, Created, UserId, RecipeId "
            + "FROM Reviews WHERE UserId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectReviews);
            selectStmt.setInt(1, user.getUserId());
            results = selectStmt.executeQuery();

            UsersDao usersDao = UsersDao.getInstance();
            RecipesDao recipesDao = RecipesDao.getInstance();

            while(results.next()) {
                int reviewId = results.getInt("ReviewId");
                String content = results.getString("Content");
                int rating = results.getInt("Rating");
                Timestamp created = results.getTimestamp("Created");
                int userId = results.getInt("UserId");
                int recipeId = results.getInt("RecipeId");

                Users resultUser = usersDao.getUserById(userId);
                Recipes recipe = recipesDao.getRecipeById(recipeId);

                Reviews review = new Reviews(reviewId, content, rating, created, resultUser, recipe);
                reviews.add(review);
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
        return reviews;
    }
}