package recipe.dal;

import recipe.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FollowsDao {
	protected ConnectionManager connectionManager;
	private static FollowsDao instance = null;

	protected FollowsDao() {
		connectionManager = new ConnectionManager();
	}

	public static FollowsDao getInstance() {
		if (instance == null) {
			instance = new FollowsDao();
		}
		return instance;
	}

	public Follows create(Follows follow) throws SQLException {
		String insertFollow = "INSERT INTO Follows(FollowingId,FollowerId) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;

		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFollow, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, follow.getFollowing().getUserId());
			insertStmt.setInt(2, follow.getFollower().getUserId());
			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int followId = -1;
			if (resultKey.next()) {
				followId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			follow.setFollowId(followId);
			return follow;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null)
				connection.close();
			if (insertStmt != null)
				insertStmt.close();
			if (resultKey != null)
				resultKey.close();
		}
	}

	public Follows getFollowById(int followId) throws SQLException {
		String selectFollow = "SELECT FollowingId,FollowerId FROM Follows WHERE FollowId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;

		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFollow);
			selectStmt.setInt(1, followId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();

			if (results.next()) {
				int followingId = results.getInt("FollowingId");
				int followerId = results.getInt("FollowerId");

				Users following = usersDao.getUserById(followingId);
				Users follower = usersDao.getUserById(followerId);
				return new Follows(followId, following, follower);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null)
				connection.close();
			if (selectStmt != null)
				selectStmt.close();
			if (results != null)
				results.close();
		}
		return null;
	}

	public List<Follows> getFollowingsByFollowerId(int followerId) throws SQLException {
		List<Follows> followsList = new ArrayList<>();
		String selectFollows = "SELECT FollowId,FollowingId,FollowerId FROM Follows WHERE FollowerId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;

		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFollows);
			selectStmt.setInt(1, followerId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			Users follower = usersDao.getUserById(followerId);

			while (results.next()) {
				int followId = results.getInt("FollowId");
				int followingId = results.getInt("FollowingId");
				Users following = usersDao.getUserById(followingId);
				Follows follow = new Follows(followId, following, follower);
				followsList.add(follow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null)
				connection.close();
			if (selectStmt != null)
				selectStmt.close();
			if (results != null)
				results.close();
		}
		return followsList;
	}

	public List<Follows> getFollowersByFollowingId(int followingId) throws SQLException {
		List<Follows> followsList = new ArrayList<>();
		String selectFollows = "SELECT FollowId,FollowingId,FollowerId FROM Follows WHERE FollowingId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;

		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFollows);
			selectStmt.setInt(1, followingId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			Users following = usersDao.getUserById(followingId);

			while (results.next()) {
				int followId = results.getInt("FollowId");
				int followerId = results.getInt("FollowerId");
				Users follower = usersDao.getUserById(followerId);
				Follows follow = new Follows(followId, following, follower);
				followsList.add(follow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null)
				connection.close();
			if (selectStmt != null)
				selectStmt.close();
			if (results != null)
				results.close();
		}
		return followsList;
	}

	public Follows delete(Follows follow) throws SQLException {
		String deleteFollow = "DELETE FROM Follows WHERE FollowId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;

		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFollow);
			deleteStmt.setInt(1, follow.getFollowId());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null)
				connection.close();
			if (deleteStmt != null)
				deleteStmt.close();
		}
	}
}
