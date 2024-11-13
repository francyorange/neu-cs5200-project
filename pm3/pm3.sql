USE Recipeople;

-- 1. What are the top 10 most liked recipes?
-- Insight: Understanding the most liked recipes helps identify popular content that resonates with users, which can be featured or promoted. These insights can drive more traffic, user engagement, and retention by showcasing these recipes in marketing campaigns, newsletters, or personalized recommendations.

SELECT R.RecipeName, COUNT(I.InteractionId) AS TotalLikes
FROM Recipes R
    JOIN Interactions I ON R.RecipeId = I.RecipeId
WHERE
    I.InteractionType = "Like"
GROUP BY
    R.RecipeName
ORDER BY TotalLikes DESC
LIMIT 10;

-- 2. What are the top 5 tags used in recipes?
-- Insight: Knowing the most frequently used tags allows you to identify trending categories or themes that users are interested in (e.g., "time-to-make," "dietary"). This can inform content strategy by creating more recipes around popular tags and improving discoverability through better search and filtering options.

SELECT T.TagName, COUNT(RT.RecipeId) AS TagCount
FROM Tags T
    JOIN RecipeTag RT ON T.TagId = RT.TagId
GROUP BY
    T.TagName
ORDER BY TagCount DESC
LIMIT 5;

-- 3. What are the top 10 frequently saved recipes and their associated health goals?
-- Insight: Help better understand which recipes resonate with different health goals, aiding in personalized recommendations.This data reveals which recipes align with specific health goals (e.g., weight loss, muscle gain). By understanding this, the platform can better personalize recipe recommendations based on user health preferences, potentially increasing user satisfaction and promoting healthier lifestyle choices.

SELECT R.RecipeName, U.HealthGoal, COUNT(I.InteractionId) AS TotalSaves
FROM
    Recipes R
    JOIN Interactions I ON R.RecipeId = I.RecipeId
    JOIN Users U ON I.UserId = U.UserId
WHERE
    I.InteractionType = 'Save'
GROUP BY
    R.RecipeName,
    U.HealthGoal
ORDER BY TotalSaves DESC
LIMIT 10;

-- 4. Which users have the highest average recipe rating for the recipes they've contributed?
-- Insight: Help spotlight top contributors and leverage their recipes for further engagement or content promotion.

SELECT U.UserName, AVG(Rev.Rating) AS AverageRating
FROM
    Users U
    JOIN Recipes R ON U.UserId = R.ContributorId
    JOIN Reviews Rev ON R.RecipeId = Rev.RecipeId
GROUP BY
    U.UserName
ORDER BY AverageRating DESC
LIMIT 5;

-- 5. Which recipes are liked the most by users with the "GainMuscle" health goal?
-- Insight: Help tailor recipe recommendations specifically for users looking to build muscle, by promoting recipes that have been liked the most by other users with similar goals.

SELECT R.RecipeName, U.HealthGoal, COUNT(I.InteractionId) AS TotalLikes
FROM
    Recipes R
    JOIN Interactions I ON R.RecipeId = I.RecipeId
    JOIN Users U ON I.UserId = U.UserId
WHERE
    I.InteractionType = 'Like'
GROUP BY
    R.RecipeName,
    U.HealthGoal
ORDER BY TotalLikes DESC
LIMIT 10;

-- 6. What are the top 10 highest-rated recipes?
-- Insight: Identifying the highest-rated recipes helps showcase content that provides the best experience and value to users.

SELECT r.RecipeId, r.RecipeName, AVG(rv.Rating) AS AverageRating
FROM Recipes r
    JOIN Reviews rv ON r.RecipeId = rv.RecipeId
GROUP BY
    r.RecipeId,
    r.RecipeName
ORDER BY AverageRating DESC
LIMIT 10;

-- 7. What is the average number of ingredients per recipe?
-- Insight: Knowing the average number of ingredients can help balance the creation of easy and complex recipes, catering to different user preferences and improving the recipe browsing experience.

SELECT AVG(IngredientCount) AS AvgIngredientsPerRecipe
FROM (
        SELECT RecipeId, COUNT(IngredientId) AS IngredientCount
        FROM RecipeIngredient
        GROUP BY
            RecipeId
    ) AS RecipeCounts;

-- 8. Which recipes are most frequently reviewed?
-- Insight: Frequently reviewed recipes indicate high user interaction and interest. Promoting these recipes can encourage further engagement and provide new users with valuable insights through user reviews.
SELECT r.RecipeName, COUNT(rv.ReviewId) AS ReviewCount
FROM Recipes r
    JOIN Reviews rv ON r.RecipeId = rv.RecipeId
GROUP BY
    r.RecipeName
ORDER BY ReviewCount DESC
LIMIT 10;

-- 9. Which recipes have the highest total calories?
-- Insight: Understanding which recipes are the most calorie-dense allows the platform to offer more targeted nutritional guidance. These insights can be used to create specialized content for users seeking high-energy meals (e.g., athletes) or to highlight healthier alternatives, improving user satisfaction and promoting balanced dietary choices.

SELECT r.RecipeName, n.Calories
FROM Recipes r
    JOIN Nutrition n ON r.RecipeId = n.RecipeId
ORDER BY n.Calories DESC
LIMIT 10;

-- 10. What are the top 10 most followed users?
-- Insight: Identifying the most followed users helps highlight influential community members. Promoting these users and their content can encourage community interaction and foster engagement through collaborations or featured posts.

SELECT u.UserName, COUNT(f.FollowerId) AS FollowersCount
FROM Users u
    JOIN Followings f ON u.UserId = f.FolloweeId
GROUP BY
    u.UserName
ORDER BY FollowersCount DESC
LIMIT 5;