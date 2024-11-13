package recipe.model;

public class Interactions {
    protected int interactionId;
    protected Users user;
    protected Recipes recipe;
    protected InteractionType interactionType;
    public enum InteractionType {
        Like, Save, Vote
    }

    public Interactions(int interactionId, Users user, Recipes recipe,
        InteractionType interactionType) {
        this.interactionId = interactionId;
        this.user = user;
        this.recipe = recipe;
        this.interactionType = interactionType;
    }

    public Interactions(int interactionId) {
        this.interactionId = interactionId;
    }

    public Interactions(Users user, Recipes recipe,
        InteractionType interactionType) {
        this.user = user;
        this.recipe = recipe;
        this.interactionType = interactionType;
    }

    public int getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(int interactionId) {
        this.interactionId = interactionId;
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

    public recipe.model.Interactions.InteractionType getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(recipe.model.Interactions.InteractionType interactionType) {
        this.interactionType = interactionType;
    }
}