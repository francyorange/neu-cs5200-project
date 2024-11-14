package recipe.dal;

import recipe.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {
	protected ConnectionManager connectionManager;
	private static UsersDao instance = null;

	protected UsersDao() {
		connectionManager = new ConnectionManager();
	}

	public static UsersDao getInstance() {
		if (instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}

	public Users create(Users user) throws SQLException {
		String insertUser = "INSERT INTO Users(UserId,UserName,HealthGoal) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			insertStmt.setInt(1, user.getUserId());
			insertStmt.setString(2, user.getUserName());
			insertStmt.setString(3, user.getHealthGoal().name());
			insertStmt.executeUpdate();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null)
				connection.close();
			if (insertStmt != null)
				insertStmt.close();
		}
	}

	public Users getUserById(int userId) throws SQLException {
		String selectUser = "SELECT UserName,HealthGoal FROM Users WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setInt(1, userId);
			results = selectStmt.executeQuery();
			if (results.next()) {
				String userName = results.getString("userName");
				Users.HealthGoal healthGoal = Users.HealthGoal.valueOf(results.getString("HealthGoal"));
				Users user = new Users(userId, userName, healthGoal);
				return user;
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

	public List<Users> getUsersByHealthGoal(Users.HealthGoal healthGoal) throws SQLException {
		List<Users> Users = new ArrayList<>();
		String selectUsers = "SELECT UserId,UserName FROM Users WHERE HealthGoal=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;

		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUsers);
			selectStmt.setString(1, healthGoal.name());
			results = selectStmt.executeQuery();

			while (results.next()) {
				int userId = results.getInt("UserId");
				String userName = results.getString("UserName");
				Users User = new Users(userId, userName, healthGoal);
				Users.add(User);
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
		return Users;
	}

	public Users updateUserName(Users user, String newUserName) throws SQLException {
		String updateUerName = "UPDATE Users SET UserName=? WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUerName);
			updateStmt.setString(1, newUserName);
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();
			user.setUserName(newUserName);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null)
				connection.close();
			if (updateStmt != null)
				updateStmt.close();
		}
	}

	public Users updateHealthGoal(Users user, Users.HealthGoal newHealthGoal) throws SQLException {
		String updateUerName = "UPDATE Users SET HealthGoal=? WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUerName);
			updateStmt.setString(1, newHealthGoal.name());
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();
			user.setHealthGoal(newHealthGoal);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null)
				connection.close();
			if (updateStmt != null)
				updateStmt.close();
		}
	}

	public Users delete(Users user) throws SQLException {
		String deleteUser = "DELETE FROM Users WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setInt(1, user.getUserId());
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

	public void loadUsersFromCSV(String csvFilePath) throws SQLException, IOException {
		String insertUserSQL = "INSERT INTO Users (UserId,UserName,HealthGoal) VALUES (?,?,?)";
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
				Connection connection = connectionManager.getConnection();
				PreparedStatement insertStmt = connection.prepareStatement(insertUserSQL);) {
			br.readLine(); // Skip the header row
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(",");
				if (fields.length != 3) {
					System.err.println("Skipping invalid line" + line);
					continue;
				}
				try {
					int userId = Integer.parseInt(fields[0]);
					String userName = fields[1];
					String healthGoal = fields[2];
					insertStmt.setInt(1, userId);
					insertStmt.setString(2, userName);
					insertStmt.setString(3, healthGoal);
					insertStmt.executeUpdate();
				} catch (NumberFormatException e) {
					System.err.println("Invalid line: " + line);
					e.printStackTrace();
				}
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}