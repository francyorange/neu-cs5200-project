package recipe.servlet;

import recipe.dal.*;
import recipe.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/recipecreate")
public class RecipeCreate extends HttpServlet {

	protected RecipesDao recipesDao;

	@Override
	public void init() throws ServletException {
		recipesDao = RecipesDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		req.getRequestDispatcher("/RecipeCreate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		String recipeName = req.getParameter("recipename");
		String description = req.getParameter("description");
		if (recipeName == null || recipeName.trim().isEmpty() || description == null || description.trim().isEmpty()) {
			messages.put("success", "Invalid Recipe details.");
		} else {
			String stringMinutes = req.getParameter("minutes");
			String steps = req.getParameter("steps");
			String stringContributorId = req.getParameter("contributor");
			try {
				int minutes = Integer.parseInt(stringMinutes);
				int contributorId = Integer.parseInt(stringContributorId);
				Timestamp submittedAt = new Timestamp(new Date().getTime());
				UsersDao usersDao = UsersDao.getInstance();
				Users contributor = usersDao.getUserById(contributorId);
				Recipes recipe = new Recipes(recipeName, minutes, steps, description, submittedAt, contributor);
				recipe = recipesDao.create(recipe);
				messages.put("success", "Successfully created " + recipeName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			} catch (NumberFormatException e) {
				messages.put("success", "Invalid number format for minutes or contributor ID.");
			}
		}
		req.getRequestDispatcher("/RecipeCreate.jsp").forward(req, resp);
	}
}
