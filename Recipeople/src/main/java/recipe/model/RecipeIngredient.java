package recipe.model;

public class RecipeIngredient {
    protected Recipes recipe;
    protected Ingredients ingredient;

    public RecipeIngredient(Recipes recipe, Ingredients ingredient) {
        this.recipe = recipe;
        this.ingredient = ingredient;
    }

    public RecipeIngredient(int recipeId, int ingredientId) {
        this.recipe = new Recipes(recipeId);
        this.ingredient = new Ingredients(ingredientId);
    }

    public Recipes getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipes recipe) {
        this.recipe = recipe;
    }

    public Ingredients getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredients ingredient) {
        this.ingredient = ingredient;
    }

    public int getRecipeId() {
        return recipe.getRecipeId();
    }

    public int getIngredientId() {
        return ingredient.getIngredientId();
    }
}
