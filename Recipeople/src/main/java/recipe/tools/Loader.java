package recipe.tools;

import recipe.dal.*;
import recipe.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class Loader {
	

	public static void main(String[] args) throws SQLException, IOException {
		UsersDao usersDao = UsersDao.getInstance();
		String userFildPath = "/Users/xiaoxuchen/Desktop/CS5200/neu-cs5200-project/data/Users.csv";
//		usersDao.loadUsers(userFildPath);
		usersDao.loadUsersFromCSV(userFildPath);
//		Users user =  new Users(123, "d", Users.HealthGoal.GainMuscle);
//		System.out.println(user.getUserId() + user.getUserName() + user.getHealthGoal().name());
//		
	}
}