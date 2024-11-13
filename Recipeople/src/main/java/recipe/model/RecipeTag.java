package recipe.model;


public class RecipeTag {
  private int recipeId;
  private int tagId;

  public RecipeTag(int recipeId, int tagId) {
    this.recipeId = recipeId;
    this.tagId = tagId;
  }

  // Getters and setters
  public int getRecipeId() { return recipeId; }
  public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

  public int getTagId() { return tagId; }
  public void setTagId(int tagId) { this.tagId = tagId; }
}
