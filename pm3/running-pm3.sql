USE Recipeople;

-- 1. What are the top 10 most liked recipes?
SELECT R.RecipeName, COUNT(I.InteractionId) AS TotalLikes
FROM Recipes R
JOIN Interactions I ON R.RecipeId = I.RecipeId
WHERE I.InteractionType = 'Like'
GROUP BY R.RecipeName
ORDER BY TotalLikes DESC
LIMIT 10;


-- 2. What are the top 10 ingredients are used in the most recipes?
SELECT I.IngredientName, COUNT(RI.RecipeId) AS RecipeCount
FROM Ingredients I
JOIN RecipeIngredient RI ON I.IngredientId = RI.IngredientId
GROUP BY I.IngredientName
ORDER BY RecipeCount DESC
LIMIT 10;


-- 3. What are the top 5 tags used in recipes?
SELECT T.TagName, COUNT(RT.RecipeId) AS TagCount
FROM Tags T
JOIN RecipeTag RT ON T.TagId = RT.TagId
GROUP BY T.TagName
ORDER BY TagCount DESC
LIMIT 5;


-- 4. What are the top 10 frequently saved recipes and their associated health goals?
SELECT R.RecipeName, U.HealthGoal, COUNT(I.InteractionId) AS TotalSaves
FROM Recipes R
JOIN Interactions I ON R.RecipeId = I.RecipeId
JOIN Users U ON I.UserId = U.UserId
WHERE I.InteractionType = 'Save'
GROUP BY R.RecipeName, U.HealthGoal
ORDER BY TotalSaves DESC
LIMIT 10;
-- Value: help better understand which recipes resonate with different health goals,
-- aiding in personalized recommendations.



-- 5. Which users have the highest average recipe rating for the recipes they've contributed?
SELECT U.UserName, AVG(Rev.Rating) AS AverageRating
FROM Users U
JOIN Recipes R ON U.UserId = R.ContributorId
JOIN Reviews Rev ON R.RecipeId = Rev.RecipeId
GROUP BY U.UserName
ORDER BY AverageRating DESC
LIMIT 5;
-- Value: Help spotlight top contributors and leverage their recipes for further
-- engagement or content promotion.


-- 6. What are the top 10 frequently used ingredients in high-rated recipes?
SELECT I.IngredientName, COUNT(RI.RecipeId) AS RecipeCount, AVG(Rev.Rating) AS AverageRating
FROM Ingredients I
JOIN RecipeIngredient RI ON I.IngredientId = RI.IngredientId
JOIN Reviews Rev ON RI.RecipeId = Rev.RecipeId
GROUP BY I.IngredientName
HAVING AVG(Rev.Rating) > 4
ORDER BY RecipeCount DESC
LIMIT 10;
-- Value: Provides actionable insights to focus on key ingredients that contribute
-- to highly rated recipes.


-- 7. Which recipes are liked the most across different health goals?
SELECT R.RecipeName, U.HealthGoal, COUNT(I.InteractionId) AS TotalLikes
FROM Recipes R
JOIN Interactions I ON R.RecipeId = I.RecipeId
JOIN Users U ON I.UserId = U.UserId
WHERE I.InteractionType = 'Like'
GROUP BY R.RecipeName, U.HealthGoal
ORDER BY TotalLikes DESC
LIMIT 10;
-- Value: Help personalize recipe recommendations based on users’ health goals
-- (e.g., recipes liked by users aiming to "LoseWeight").
