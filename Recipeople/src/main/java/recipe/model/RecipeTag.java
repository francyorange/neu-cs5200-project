package recipe.model;

public class RecipeTag {
    protected Recipes recipe;
    protected Tags tag;

    public RecipeTag(Recipes recipe, Tags tag) {
        this.recipe = recipe;
        this.tag = tag;
    }

    public RecipeTag(int recipeId, int tagId) {
        this.recipe = new Recipes(recipeId);
        this.tag = new Tags(tagId);
    }

    public Recipes getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipes recipe) {
        this.recipe = recipe;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public int getRecipeId() {
        return recipe.getRecipeId();
    }

    public int getTagId() {
        return tag.getTagId();
    }
}
