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


@SuppressWarnings("serial")
@WebServlet("/recipeupdate")
public class RecipeUpdate extends HttpServlet {

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

    try {
      int recipeId = Integer.parseInt(req.getParameter("recipeid"));
      Recipes recipe = recipesDao.getRecipeById(recipeId);
      if (recipe == null) {
        messages.put("success", "RecipeId does not exist.");
      } else {
        req.setAttribute("recipe", recipe);
      }
    } catch (NumberFormatException | NullPointerException e) {
      messages.put("success", "Please enter a valid RecipeId.");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }

    req.getRequestDispatcher("/RecipeUpdate.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    try {
      int recipeId = Integer.parseInt(req.getParameter("recipeid"));
      Recipes recipe = recipesDao.getRecipeById(recipeId);
      if (recipe == null) {
        messages.put("success", "RecipeId does not exist. No update to perform.");
      } else {
        String newDescription = req.getParameter("description");
        if (newDescription == null || newDescription.trim().isEmpty()) {
          messages.put("success", "Please enter a valid Description.");
        } else {
          recipe = recipesDao.updateDescription(recipe, newDescription);
          messages.put("success", "Successfully updated Recipe ID " + recipeId);
        }
        req.setAttribute("recipe", recipe);
      }
    } catch (NumberFormatException | NullPointerException e) {
      messages.put("success", "Please enter a valid RecipeId.");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }

    req.getRequestDispatcher("/RecipeUpdate.jsp").forward(req, resp);
  }
}