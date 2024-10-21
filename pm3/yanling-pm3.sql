USE Recipeople;

-- 1. What are the top 10 highest-rated recipes?
SELECT r.RecipeId, r.RecipeName, AVG(rv.Rating) AS AverageRating
FROM Recipes r
    JOIN Reviews rv ON r.RecipeId = rv.RecipeId
GROUP BY
    r.RecipeId,
    r.RecipeName
ORDER BY AverageRating DESC
LIMIT 10;

-- 2. What is the average number of ingredients per recipe ?
SELECT AVG(IngredientCount) AS AvgIngredientsPerRecipe
FROM (
        SELECT RecipeId, COUNT(IngredientId) AS IngredientCount
        FROM RecipeIngredient
        GROUP BY
            RecipeId
    ) AS RecipeCounts;

-- 3. Which recipes are most frequently reviewed?
SELECT r.RecipeName, COUNT(rv.ReviewId) AS ReviewCount
FROM Recipes r
    JOIN Reviews rv ON r.RecipeId = rv.RecipeId
GROUP BY
    r.RecipeName
ORDER BY ReviewCount DESC;

-- 4. Which recipes have the highest total calories?
SELECT r.RecipeName, n.Calories
FROM Recipes r
    JOIN Nutrition n ON r.RecipeId = n.RecipeId
ORDER BY n.Calories DESC
LIMIT 5;