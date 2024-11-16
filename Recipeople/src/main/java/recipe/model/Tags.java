package recipe.model;

public class Tags {
    protected int tagId;
    protected String tagName;
    protected Category category;

    public enum Category {
        cuisine, ingredient, diet, time, prep_method, occasion, feature, specific_date, flavor_mood, 
        condiment, course
    }

    public Tags(int tagId, String tagName, Category category) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.category = category;
    }

    public Tags(int tagId) {
        this.tagId = tagId;
    }
    
    public Tags(String tagName, Category category) {
        this.tagName = tagName;
        this.category = category;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
