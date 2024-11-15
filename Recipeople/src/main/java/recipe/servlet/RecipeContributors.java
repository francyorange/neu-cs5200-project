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
@WebServlet("/recipecontributors")
public class RecipeContributors extends HttpServlet {

  protected UsersDao usersDao;

  @Override
  public void init() throws ServletException {
    usersDao = UsersDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Map<String, String> messages = new HashMap<>();
    req.setAttribute("messages", messages);

    int recipeId = -1;
    try {
      recipeId = Integer.parseInt(req.getParameter("recipeid"));
    } catch (NumberFormatException e) {
      messages.put("title", "Invalid recipeid.");
    }

    if (recipeId <= 0) {
      messages.put("title", "Invalid recipeid.");
    } else {
      messages.put("title", "Contributors for Recipe ID " + recipeId);
    }

    List<Users> users = new ArrayList<>();
    if (recipeId > 0) {
      RecipesDao recipesDao = RecipesDao.getInstance();
      try {
        Recipes recipe = recipesDao.getRecipeById(recipeId);
        if (recipe != null) {
          users = recipesDao.getContributorsForRecipe(recipe);
          if (users.isEmpty()) {
            messages.put("title", "No contributors found for Recipe ID " + recipeId);
          }
        } else {
          messages.put("title", "Recipe not found for ID " + recipeId);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        messages.put("title", "An error occurred while retrieving contributors.");
        messages.put("error", "SQL Error: " + e.getMessage());
      }
    }

    req.setAttribute("users", users);
    req.getRequestDispatcher("/RecipeContributors.jsp").forward(req, resp);
  }
}