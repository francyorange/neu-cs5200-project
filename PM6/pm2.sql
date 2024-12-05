CREATE SCHEMA IF NOT EXISTS Recipeople;

USE Recipeople;

SET GLOBAL local_infile = 1;

DROP TABLE IF EXISTS Reviews;

DROP TABLE IF EXISTS RecipeTag;

DROP TABLE IF EXISTS RecipesCalculatedData;

DROP TABLE IF EXISTS RecipeIngredient;

DROP TABLE IF EXISTS Nutrition;

DROP TABLE IF EXISTS Interactions;

DROP TABLE IF EXISTS Recipes;

DROP TABLE IF EXISTS Ingredients;

DROP TABLE IF EXISTS Tags;

DROP TABLE IF EXISTS Follows;

DROP TABLE IF EXISTS Users;

-- Create tables
CREATE TABLE Users (
    UserId INT PRIMARY KEY,
    UserName VARCHAR(255) NOT NULL,
    HealthGoal ENUM(
        'MaintainWeight',
        'LoseWeight',
        'GainMuscle'
    ) NOT NULL
);

CREATE TABLE Follows (
    FollowId INT PRIMARY KEY AUTO_INCREMENT,
    FollowingId INT,
    FollowerId INT,
    FOREIGN KEY (FollowingId) REFERENCES Users (UserId) ON DELETE CASCADE,
    FOREIGN KEY (FollowerId) REFERENCES Users (UserId) ON DELETE CASCADE,
    UNIQUE (FollowingId, FollowerId)
);

CREATE TABLE Recipes (
    RecipeId INT PRIMARY KEY AUTO_INCREMENT,
    RecipeName VARCHAR(255) NOT NULL,
    Minutes INT,
    Steps TEXT,
    Description TEXT NOT NULL,
    SubmittedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ContributorId INT,
    FOREIGN KEY (ContributorId) REFERENCES Users (UserId) ON DELETE SET NULL
);

CREATE TABLE Interactions (
    InteractionId INT PRIMARY KEY AUTO_INCREMENT,
    UserId INT,
    RecipeId INT,
    InteractionType ENUM('Like', 'Save', 'Vote') NOT NULL,
    FOREIGN KEY (UserId) REFERENCES Users (UserId) ON DELETE SET NULL,
    FOREIGN KEY (RecipeId) REFERENCES Recipes (RecipeId) ON DELETE SET NULL
);

CREATE TABLE Reviews (
    ReviewId INT PRIMARY KEY AUTO_INCREMENT,
    Content TEXT,
    Rating INT,
    Created TIMESTAMP,
    UserId INT,
    RecipeId INT,
    FOREIGN KEY (UserId) REFERENCES Users (UserId) ON DELETE SET NULL,
    FOREIGN KEY (RecipeId) REFERENCES Recipes (RecipeId) ON DELETE CASCADE
);

CREATE TABLE Ingredients (
    IngredientId INT PRIMARY KEY,
    IngredientName VARCHAR(255) NOT NULL,
    IngredientType ENUM(
        'Dairy',
        'Fruit',
        'Grain',
        'Liquid',
        'Meat',
        'Miscellaneous',
        'Nut',
        'Oil',
        'Seafood',
        'Spice',
        'Sweetener',
        'Vegetable'
    ) NOT NULL
);

CREATE TABLE RecipeIngredient (
    RecipeId INT,
    IngredientId INT,
    PRIMARY KEY (RecipeId, IngredientId),
    FOREIGN KEY (RecipeId) REFERENCES Recipes (RecipeId) ON DELETE CASCADE,
    FOREIGN KEY (IngredientId) REFERENCES Ingredients (IngredientId) ON DELETE CASCADE
);

CREATE TABLE Nutrition (
    RecipeId INT PRIMARY KEY,
    Calories FLOAT,
    TotalFat FLOAT,
    Sugar FLOAT,
    Sodium FLOAT,
    Protein FLOAT,
    SaturatedFat FLOAT,
    Carbohydrates FLOAT,
    FOREIGN KEY (RecipeId) REFERENCES Recipes (RecipeId) ON DELETE CASCADE
);

CREATE TABLE Tags (
    TagId INT PRIMARY KEY,
    TagName VARCHAR(255) NOT NULL,
    Category ENUM(
        'condiment',
        'course',
        'cuisine',
        'diet',
        'feature',
        'flavor_mood',
        'ingredient',
        'occasion',
        'prep_method',
        'specific_date',
        'time'
    ) NOT NULL
);

CREATE TABLE RecipeTag (
    RecipeId INT,
    TagId INT,
    PRIMARY KEY (RecipeId, TagId),
    FOREIGN KEY (RecipeId) REFERENCES Recipes (RecipeId) ON DELETE CASCADE,
    FOREIGN KEY (TagId) REFERENCES Tags (TagId) ON DELETE CASCADE
);

CREATE TABLE RecipesCalculatedData (
    RecipeId INT PRIMARY KEY,
    AverageRating FLOAT,
    StepCount INT,
    IngredientCount INT,
    ReviewsCount INT,
    FOREIGN KEY (RecipeId) REFERENCES Recipes (RecipeId)
);

-- Load data into tables
LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/Users.csv' INTO
TABLE Users FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/Follows.csv' INTO
TABLE Follows FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS (FollowingId, FollowerId);

LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/Recipes.csv' INTO
TABLE Recipes FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS (
    RecipeId,
    RecipeName,
    Minutes,
    Steps,
    Description,
    SubmittedAt,
    ContributorId
);

LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/Interactions.csv' INTO
TABLE Interactions FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS (
    InteractionId,
    UserId,
    RecipeId,
    InteractionType
);

LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/Reviews.csv' INTO
TABLE Reviews FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS (
    ReviewId,
    Content,
    Rating,
    Created,
    UserId,
    RecipeId
);

LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/Ingredients.csv' INTO
TABLE Ingredients FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS (
    IngredientId,
    IngredientName,
    IngredientType
);

LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/RecipeIngredient.csv' INTO
TABLE RecipeIngredient FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS (RecipeId, IngredientId);

LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/Nutrition.csv' INTO
TABLE Nutrition FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS (
    RecipeId,
    Calories,
    TotalFat,
    Sugar,
    Sodium,
    Protein,
    SaturatedFat,
    Carbohydrates
);

LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/Tags.csv' INTO
TABLE Tags FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS (TagId, TagName, Category);

LOAD DATA LOCAL INFILE '/workspaces/neu-cs5200-project/PM6/data/RecipeTag.csv' INTO
TABLE RecipeTag FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS (RecipeId, TagId);

-- Query to check row counts
SELECT 'Users' AS table_name, COUNT(*) AS row_count
FROM Users
UNION ALL
SELECT 'Follow', COUNT(*)
FROM Follows
UNION ALL
SELECT 'Recipes', COUNT(*)
FROM Recipes
UNION ALL
SELECT 'Interactions', COUNT(*)
FROM Interactions
UNION ALL
SELECT 'Reviews', COUNT(*)
FROM Reviews
UNION ALL
SELECT 'Ingredients', COUNT(*)
FROM Ingredients
UNION ALL
SELECT 'RecipeIngredient', COUNT(*)
FROM RecipeIngredient
UNION ALL
SELECT 'Nutrition', COUNT(*)
FROM Nutrition
UNION ALL
SELECT 'Tags', COUNT(*)
FROM Tags
UNION ALL
SELECT 'RecipeTag', COUNT(*)
FROM RecipeTag;