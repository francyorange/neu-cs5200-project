package recipe.model;

public class Ingredients {
    protected int ingredientId;
    protected String ingredientName;
    protected IngredientType ingredientType;
    public enum IngredientType {
        Vegetable, Fruit, Meat, Dairy, Grain, Spice, Other
    }

    public Ingredients(int ingredientId, String ingredientName, IngredientType ingredientType) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
    }

    public Ingredients(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Ingredients(String ingredientName, IngredientType ingredientType) {
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public recipe.model.Ingredients.IngredientType getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(recipe.model.Ingredients.IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }
}