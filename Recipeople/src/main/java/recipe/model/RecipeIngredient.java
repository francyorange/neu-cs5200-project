package recipe.model;
public class RecipeIngredient {
  private int recipeId;
  private int ingredientId;

  public RecipeIngredient(int recipeId, int ingredientId) {
    this.recipeId = recipeId;
    this.ingredientId = ingredientId;
  }

  // Getters and setters
  public int getRecipeId() { return recipeId; }
  public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

  public int getIngredientId() { return ingredientId; }
  public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }
}
