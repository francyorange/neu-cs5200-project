package recipe.model;

public class Nutrition {
    protected float calories;
    protected float totalFat;
    protected float sugar;
    protected float sodium;
    protected float protein;
    protected float saturatedFat;
    protected float carbohydrates;
    protected Recipe recipe;

    public Nutrition(float calories, float totalFat, float sugar, float sodium, float protein, float saturatedFat,
            float carbohydrates, Recipe recipe) {
        this.calories = calories;
        this.totalFat = totalFat;
        this.sugar = sugar;
        this.sodium = sodium;
        this.protein = protein;
        this.saturatedFat = saturatedFat;
        this.carbohydrates = carbohydrates;
        this.recipe = recipe;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(float totalFat) {
        this.totalFat = totalFat;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(float saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(calories);
        result = prime * result + Float.floatToIntBits(totalFat);
        result = prime * result + Float.floatToIntBits(sugar);
        result = prime * result + Float.floatToIntBits(sodium);
        result = prime * result + Float.floatToIntBits(protein);
        result = prime * result + Float.floatToIntBits(saturatedFat);
        result = prime * result + Float.floatToIntBits(carbohydrates);
        result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Nutrition other = (Nutrition) obj;
        if (Float.floatToIntBits(calories) != Float.floatToIntBits(other.calories))
            return false;
        if (Float.floatToIntBits(totalFat) != Float.floatToIntBits(other.totalFat))
            return false;
        if (Float.floatToIntBits(sugar) != Float.floatToIntBits(other.sugar))
            return false;
        if (Float.floatToIntBits(sodium) != Float.floatToIntBits(other.sodium))
            return false;
        if (Float.floatToIntBits(protein) != Float.floatToIntBits(other.protein))
            return false;
        if (Float.floatToIntBits(saturatedFat) != Float.floatToIntBits(other.saturatedFat))
            return false;
        if (Float.floatToIntBits(carbohydrates) != Float.floatToIntBits(other.carbohydrates))
            return false;
        if (recipe == null) {
            if (other.recipe != null)
                return false;
        } else if (!recipe.equals(other.recipe))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Nutrition [calories=" + calories + ", totalFat=" + totalFat + ", sugar=" + sugar + ", sodium=" + sodium
                + ", protein=" + protein + ", saturatedFat=" + saturatedFat + ", carbohydrates=" + carbohydrates
                + ", recipe=" + recipe + "]";
    }

}