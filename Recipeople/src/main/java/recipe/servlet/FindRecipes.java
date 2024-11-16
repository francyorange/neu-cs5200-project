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
@WebServlet("/findrecipes")
public class FindRecipes extends HttpServlet {

    protected RecipesDao recipesDao;
    protected RecipeTagDao recipeTagDao;

    @Override
    public void init() throws ServletException {
        recipesDao = RecipesDao.getInstance();
        recipeTagDao = RecipeTagDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Recipes> recipes = new ArrayList<>();

        // Retrieve and validate inputs.
        String recipeIdStr = req.getParameter("recipeId");
        String tagName = req.getParameter("tagName");

        if ((recipeIdStr == null || recipeIdStr.trim().isEmpty()) && 
            (tagName == null || tagName.trim().isEmpty())) {
            messages.put("success", "Please enter a valid recipe ID or tag name.");
        } else {
            try {
                if (recipeIdStr != null && !recipeIdStr.trim().isEmpty()) {
                    int recipeId = Integer.parseInt(recipeIdStr);
                    Recipes recipe = recipesDao.getRecipeById(recipeId);
                    if (recipe != null) {
                        recipes.add(recipe);
                        messages.put("success", "Displaying results for recipe ID: " + recipeId);
                    } else {
                        messages.put("success", "No recipe found with the specified recipe ID: " + recipeId);
                    }
                } else if (tagName != null && !tagName.trim().isEmpty()) {
                    List<Integer> recipeIds = recipeTagDao.findRecipesByTagName(tagName);
                    for (Integer id : recipeIds) {
                        Recipes recipe = recipesDao.getRecipeById(id);
                        if (recipe != null) {
                            recipes.add(recipe);
                        }
                    }
                    messages.put("success", "Displaying results for tag name: " + tagName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            } catch (NumberFormatException e) {
                messages.put("success", "Invalid recipe ID format.");
            }
        }
        req.setAttribute("recipes", recipes);
        req.getRequestDispatcher("/FindRecipes.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
