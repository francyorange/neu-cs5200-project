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
@WebServlet("/userblogposts")
public class RecipeReviews extends HttpServlet {

  protected ReviewsDao reviewsDao;

  @Override
  public void init() throws ServletException {
    reviewsDao = ReviewsDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    int recipeId = (int) req.getAttribute("recipeid");

    if (recipeId <= 0) {
      messages.put("title", "Invalid recipeid.");
    } else {
      messages.put("title", "Reviews for Recipe ID " + recipeId);
    }

    List<Reviews> reviews = new ArrayList<>();
    if (recipeId > 0) {
      try {
        Recipes recipe = new Recipes(recipeId);
        reviews = reviewsDao.getReviewsForRecipe(recipe);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.setAttribute("reviews", reviews);
    req.getRequestDispatcher("/RecipeReviews.jsp").forward(req, resp);
  }
}