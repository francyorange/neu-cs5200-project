package recipe.model;

import java.sql.Timestamp;

public class Recipe {

    private int recipeId;
    private String recipeName;
    private int minutes;
    private String steps;
    private String description;
    private Timestamp submittedAt;
    private int contributorId;

    public Recipe(String recipeName, int minutes, String steps, String description, Timestamp submittedAt,
            int contributorId) {
        this.recipeName = recipeName;
        this.minutes = minutes;
        this.steps = steps;
        this.description = description;
        this.submittedAt = submittedAt;
        this.contributorId = contributorId;
    }

    public Recipe(int recipeId, String recipeName, int minutes, String steps, String description, Timestamp submittedAt,
            int contributorId) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.minutes = minutes;
        this.steps = steps;
        this.description = description;
        this.submittedAt = submittedAt;
        this.contributorId = contributorId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }

    public int getContributorId() {
        return contributorId;
    }

    public void setContributorId(int contributorId) {
        this.contributorId = contributorId;
    }

    @Override
    public String toString() {
        return "Recipe [recipeId=" + recipeId + ", recipeName=" + recipeName + ", minutes=" + minutes + ", steps="
                + steps + ", description=" + description + ", submittedAt=" + submittedAt + ", contributorId="
                + contributorId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + recipeId;
        result = prime * result + ((recipeName == null) ? 0 : recipeName.hashCode());
        result = prime * result + minutes;
        result = prime * result + ((steps == null) ? 0 : steps.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((submittedAt == null) ? 0 : submittedAt.hashCode());
        result = prime * result + contributorId;
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
        Recipe other = (Recipe) obj;
        if (recipeId != other.recipeId)
            return false;
        if (recipeName == null) {
            if (other.recipeName != null)
                return false;
        } else if (!recipeName.equals(other.recipeName))
            return false;
        if (minutes != other.minutes)
            return false;
        if (steps == null) {
            if (other.steps != null)
                return false;
        } else if (!steps.equals(other.steps))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (submittedAt == null) {
            if (other.submittedAt != null)
                return false;
        } else if (!submittedAt.equals(other.submittedAt))
            return false;
        if (contributorId != other.contributorId)
            return false;
        return true;
    }
}