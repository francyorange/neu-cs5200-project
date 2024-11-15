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


/**
 * FindRecipessers is the primary entry point into the application.
 * 
 * Note the logic for doGet() and doPost() are almost identical. However, there is a difference:
 * doGet() handles the http GET request. This method is called when you put in the /findusers
 * URL in the browser.
 * doPost() handles the http POST request. This method is called after you click the submit button.
 * 
 * To run:
 * 1. Run the SQL script to recreate your database schema: http://goo.gl/86a11H.
 * 2. Insert test data. You can do this by running blog.tools.Inserter (right click,
 *    Run As > JavaApplication.
 *    Notice that this is similar to Runner.java in our JDBC example.
 * 3. Run the Tomcat server at localhost.
 * 4. Point your browser to http://localhost:8080/BlogApplication/findusers.
 */
@WebServlet("/findRecipes")
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
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Recipes> recipes = new ArrayList<Recipes>();
        
        // Retrieve and validate name.
        String name = req.getParameter("name");
        String tagCategory = req.getParameter("tagCategory");
        
        if ((name == null || name.trim().isEmpty()) && (tagCategory == null || tagCategory.trim().isEmpty())) {
            messages.put("success", "Please enter a valid recipe name or tag category.");
        } else {
            try {
                if (name != null && !name.trim().isEmpty()) {
                    recipes = recipesDao.getRecipesByName(name);
                    messages.put("success", "Displaying results for recipe name: " + name);
                } else if (tagCategory != null && !tagCategory.trim().isEmpty()) {
                    recipes = recipeTagDao.findRecipesByTagCategory(tagCategory);
                    messages.put("success", "Displaying results for tag category: " + tagCategory);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        req.setAttribute("recipes", recipes);
        
        req.getRequestDispatcher("/FindRecipes.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Recipes> recipes = new ArrayList<Recipes>();
        
        // Retrieve and validate name.
        String name = req.getParameter("name");
        String tagCategory = req.getParameter("tagCategory");
        
        if ((name == null || name.trim().isEmpty()) && (tagCategory == null || tagCategory.trim().isEmpty())) {
            messages.put("success", "Please enter a valid recipe name or tag category.");
        } else {
            try {
                if (name != null && !name.trim().isEmpty()) {
                    recipes = recipesDao.getRecipesByName(name);
                    messages.put("success", "Displaying results for name: " + name);
                } else if (tagCategory != null && !tagCategory.trim().isEmpty()) {
                    recipes = recipeTagDao.findRecipesByTagCategory(tagCategory);
                    messages.put("success", "Displaying results for tag category: " + tagCategory);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        req.setAttribute("recipes", recipes);
        
        req.getRequestDispatcher("/FindRecipes.jsp").forward(req, resp);
    }
}
