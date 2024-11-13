package recipe.model;

public class Follows {
	protected int followId;
	protected Users following;
	protected Users follower;

	public Follows(int followId, Users following, Users follower) {
		this.followId = followId;
		this.following = following;
		this.follower = follower;
	}

	public Follows(int followId) {
		this.followId = followId;
	}

	public int getFollowId() {
		return followId;
	}

	public void setFollowId(int followId) {
		this.followId = followId;
	}

	public Users getFollowing() {
		return following;
	}

	public void setFollowing(Users following) {
		this.following = following;
	}

	public Users getFollower() {
		return follower;
	}

	public void setFollower(Users follower) {
		this.follower = follower;
	}
}
