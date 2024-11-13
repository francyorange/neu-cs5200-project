package recipe.model;

public class Tags {
  private int tagId;
  private String tagName;
  private String category;

  public Tags(int tagId, String tagName, String category) {
    this.tagId = tagId;
    this.tagName = tagName;
    this.category = category;
  }

  // Getters and setters
  public int getTagId() { return tagId; }
  public void setTagId(int tagId) { this.tagId = tagId; }

  public String getTagName() { return tagName; }
  public void setTagName(String tagName) { this.tagName = tagName; }

  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
}
