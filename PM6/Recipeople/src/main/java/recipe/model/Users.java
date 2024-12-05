package recipe.model;

public class Users {
	protected int userId;
	protected String userName;
	protected HealthGoal healthGoal;

	public Users(int userId, String userName, HealthGoal healthGoal) {
		this.userId = userId;
		this.userName = userName;
		this.healthGoal = healthGoal;
	}

	public Users(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public HealthGoal getHealthGoal() {
		return healthGoal;
	}

	public void setHealthGoal(HealthGoal healthGoal) {
		this.healthGoal = healthGoal;
	}

	public enum HealthGoal {
		MaintainWeight, GainMuscle, LoseWeight
	}

}
