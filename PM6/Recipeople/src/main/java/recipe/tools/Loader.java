package recipe.tools;

import recipe.dal.*;
import recipe.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Loader {

	public static void main(String[] args) throws SQLException, IOException {
		String filePath = "src/main/resources/data/";
		UsersDao usersDao = UsersDao.getInstance();
		// Test UsersDao methods
		Users user1 = new Users(1535, "user1", Users.HealthGoal.GainMuscle);
		Users user2 = new Users(1676, "user2", Users.HealthGoal.LoseWeight);
		usersDao.create(user1);
		usersDao.create(user2);
		Users user = usersDao.getUserById(1535);
		System.out.println(user.getUserId() + " " + user.getUserName() + " " + user.getHealthGoal().name());
		List<Users> users = usersDao.getUsersByHealthGoal(Users.HealthGoal.GainMuscle);
		for (Users u : users) {
			System.out.println(u.getUserId() + " " + u.getUserName() + " " + u.getHealthGoal().name());
		}
		usersDao.updateUserName(user1, "user111");
		usersDao.delete(user1);
		usersDao.delete(user2);

		// Test users data loading
		usersDao.loadUsersFromCSV(filePath + "Users.csv");
		users = usersDao.getUsersByHealthGoal(Users.HealthGoal.GainMuscle);
		for (Users u : users) {
			System.out.println(u.getUserId() + " " + u.getUserName() + " " + u.getHealthGoal().name());
		}

		// Test FollowsDao methods
		FollowsDao followsDao = FollowsDao.getInstance();
		followsDao.loadFollowsFromCSV(filePath + "Follows.csv");
		Follows follow = followsDao.getFollowById(1);
		System.out.println(follow.getFollower().getUserId() + " " + follow.getFollowing().getUserId());
		List<Follows> follows = followsDao.getFollowersByFollowingId(follow.getFollowing().getUserId());
		for (Follows f : follows) {
			System.out
					.println(f.getFollowId() + " " + f.getFollowing().getUserId() + " " + f.getFollower().getUserId());
		}
		follows = followsDao.getFollowingsByFollowerId(follow.getFollower().getUserId());
		for (Follows f : follows) {
			System.out
					.println(f.getFollowId() + " " + f.getFollowing().getUserId() + " " + f.getFollower().getUserId());
		}
		user1 = usersDao.getUserById(1535);
		user2 = usersDao.getUserById(1676);
		follow = new Follows(user1, user2);
		followsDao.delete(follow);
	}
}