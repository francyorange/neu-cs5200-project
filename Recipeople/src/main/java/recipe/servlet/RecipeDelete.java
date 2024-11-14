package recipe.servlet;

import recipe.dal.*;
import recipe.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/recipedelete")
public class RecipeDelete extends HttpServlet {
	
	protected RecipesDao recipesDao;

	@Override
	public void init() throws ServletException {
		recipesDao = RecipesDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        messages.put("title", "Delete Recipe");        
        req.getRequestDispatcher("/RecipeDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate RecipeId.
        String recipeIdStr = req.getParameter("recipeid");
        if (recipeIdStr == null || recipeIdStr.trim().isEmpty()) {
            messages.put("title", "Invalid RecipeId");
            messages.put("disableSubmit", "true");
        } else {
            int recipeId = Integer.parseInt(recipeIdStr);
            try {
                Recipes recipe = recipesDao.getRecipeById(recipeId);
           
                if (recipe != null) {
                    recipe = recipesDao.delete(recipe);
                    if (recipe == null) {
                        messages.put("title", "Successfully deleted recipe with ID " + recipeId);
                        messages.put("disableSubmit", "true");
                    } else {
                        messages.put("title", "Failed to delete recipe with ID " + recipeId);
                        messages.put("disableSubmit", "false");
                    }
                } else {
                    messages.put("title", "Recipe with ID " + recipeId + " does not exist.");
                    messages.put("disableSubmit", "true");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/RecipeDelete.jsp").forward(req, resp);
    }
}