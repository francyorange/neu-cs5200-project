package recipe.model;
import java.sql.Timestamp;

public class Reviews {
    protected int reviewId;
    protected String content;
    protected int rating;
    protected Timestamp created;
    protected Users user;
    protected Recipes recipe;

    public Reviews(int reviewId, String content, int rating, Timestamp created,
        Users user, Recipes recipe) {
        this.reviewId = reviewId;
        this.content = content;
        this.rating = rating;
        this.created = created;
        this.user = user;
        this.recipe = recipe;
    }

    public Reviews(int reviewId) {
        this.reviewId = reviewId;
    }

    public Reviews(String content, int rating, Timestamp created,
        Users user, Recipes recipe) {
        this.content = content;
        this.rating = rating;
        this.created = created;
        this.user = user;
        this.recipe = recipe;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
       this.reviewId = reviewId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Users getUser() {
       return user;
    }

    public void setUser(Users user) {
       this.user = user;
    }

    public Recipes getRecipe() {
       return recipe;
    }

    public void setRecipe(Recipes recipe) {
       this.recipe = recipe;
    }
}

