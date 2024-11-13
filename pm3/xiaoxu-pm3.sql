USE Recipeople;

/** About Recipe
We offer recipe recommendations based on their popularity from various 
perspectives, including ratings, reviews, likes, saves, votes, and calories, health goals.
These recommendations are tailored to different groups of users for a more personalized experience.
**/


-- 1. What are the top 10 most liked recipes?
-- Value: recommend the most popular recipes to all users
SELECT R.RecipeName, COUNT(I.InteractionId) AS TotalLikes
FROM Recipes R
JOIN Interactions I ON R.RecipeId = I.RecipeId
WHERE I.InteractionType = 'Like'
GROUP BY R.RecipeName
ORDER BY TotalLikes DESC
LIMIT 10;

-- 2. Which recipes are liked the most across different health goals?   
-- Value: Help personalize recipe recommendations based on users’ health goals (e.g., recipes liked by users aiming to "LoseWeight")
SELECT R.RecipeName, U.HealthGoal, COUNT(I.InteractionId) AS TotalLikes
FROM Recipes R
JOIN Interactions I ON R.RecipeId = I.RecipeId
JOIN Users U ON I.UserId = U.UserId
WHERE I.InteractionType = 'Like'
GROUP BY R.RecipeName, U.HealthGoal
ORDER BY TotalLikes DESC
LIMIT 10;


-- 3. What are the top 10 frequently saved recipes and their associated health goals?
-- Value: help better understand which recipes resonate with different health goals, aiding in personalized recommendations
SELECT R.RecipeName, U.HealthGoal, COUNT(I.InteractionId) AS TotalSaves
FROM Recipes R
JOIN Interactions I ON R.RecipeId = I.RecipeId
JOIN Users U ON I.UserId = U.UserId
WHERE I.InteractionType = 'Save'
GROUP BY R.RecipeName, U.HealthGoal
ORDER BY TotalSaves DESC
LIMIT 10;

-- 4. Which recipes are most frequently reviewed? (top 10)
SELECT r.RecipeName, COUNT(rv.ReviewId) AS ReviewCount
FROM Recipes r
    JOIN Reviews rv ON r.RecipeId = rv.RecipeId
GROUP BY
    r.RecipeName
ORDER BY ReviewCount DESC
LIMIT 10;

-- 5. What are the top 10 highest-rated recipes?
SELECT r.RecipeId, r.RecipeName, AVG(rv.Rating) AS AverageRating
FROM Recipes r
    JOIN Reviews rv ON r.RecipeId = rv.RecipeId
GROUP BY
    r.RecipeId,
    r.RecipeName
ORDER BY AverageRating DESC
LIMIT 10;

-- 6. Which recipes have the highest total calories?
SELECT r.RecipeName, n.Calories
FROM Recipes r
    JOIN Nutrition n ON r.RecipeId = n.RecipeId
ORDER BY n.Calories DESC
LIMIT 5;

/** About Tags **/

-- 7. What are the top 5 tags used in recipes? (most popular tag)
SELECT T.TagName, COUNT(RT.RecipeId) AS TagCount
FROM Tags T
JOIN RecipeTag RT ON T.TagId = RT.TagId
GROUP BY T.TagName
ORDER BY TagCount DESC
LIMIT 5;

/** About Users **/

-- 8. Which users have the highest average recipe rating for the recipes they've contributed?
-- Value: Help spotlight top contributors and leverage their recipes for further engagement or content promotion
SELECT U.UserName, AVG(Rev.Rating) AS AverageRating
FROM Users U
JOIN Recipes R ON U.UserId = R.ContributorId
JOIN Reviews Rev ON R.RecipeId = Rev.RecipeId
GROUP BY U.UserName
ORDER BY AverageRating DESC
LIMIT 5;

-- 9. What are the top 10 most followed users?
SELECT u.UserName, COUNT(f.FollowerId) AS FollowersCount
FROM Users u
JOIN Followings f ON u.UserId = f.FolloweeId
GROUP BY u.UserName
ORDER BY FollowersCount DESC
LIMIT 5;

/** About Ingredients **/

-- 10. What is the average number of ingredients per recipe ?
SELECT AVG(IngredientCount) AS AvgIngredientsPerRecipe
FROM (
        SELECT RecipeId, COUNT(IngredientId) AS IngredientCount
        FROM RecipeIngredient
        GROUP BY
            RecipeId
    ) AS RecipeCounts;

