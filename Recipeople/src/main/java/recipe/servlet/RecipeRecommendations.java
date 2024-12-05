package recipe.servlet;

import recipe.dal.*;
import recipe.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/reciperecommendations")
public class RecipeRecommendations extends HttpServlet {

    protected RecipesDao recipesDao;
    protected RecipeTagDao recipeTagDao;
    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        recipesDao = RecipesDao.getInstance();
        recipeTagDao = RecipeTagDao.getInstance();
        usersDao = UsersDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Recipes> recipes = new ArrayList<>();

        // Retrieve and validate inputs.E
        String userIdStr = req.getParameter("userId");

        if ((userIdStr == null || userIdStr.trim().isEmpty())) {
            messages.put("success", "Please enter a valid user ID.");
        } else {
            try {
                if (userIdStr != null && !userIdStr.trim().isEmpty()) {
                    int userId = Integer.parseInt(userIdStr);
                    Users user = usersDao.getUserById(userId);
                    recipes = recipesDao.getRecipeRecommendations(user);
                    if (recipes.size() > 0) {
                        // recipes.add(recipe);
                        messages.put("success", "Displaying recommendations for user ID: " + userId + ". User's goal: "
                                + user.getHealthGoal().name());
                    } else {
                        messages.put("success", "No recommendations found with the specified user ID: " + userId);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            } catch (NumberFormatException e) {
                messages.put("success", "Invalid user ID format.");
            } catch (NullPointerException e) {
                messages.put("success", "User ID not fund: " + userIdStr);
            }
        }
        req.setAttribute("recipes", recipes);
        req.getRequestDispatcher("/RecipeRecommendations.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
