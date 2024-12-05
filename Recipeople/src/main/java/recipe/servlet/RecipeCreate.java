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

@SuppressWarnings("serial")
@WebServlet("/recipecreate")
public class RecipeCreate extends HttpServlet {

	protected RecipesDao recipesDao;
	protected UsersDao usersDao;

	@Override
	public void init() throws ServletException {
		recipesDao = RecipesDao.getInstance();
		usersDao = UsersDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<>();
		req.setAttribute("messages", messages);
		req.getRequestDispatcher("/RecipeCreate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<>();
		req.setAttribute("messages", messages);

		// Retrieve and validate form parameters
		String recipeName = req.getParameter("recipename");
		String description = req.getParameter("description");
		String stringMinutes = req.getParameter("minutes");
		String steps = req.getParameter("steps");
		String stringContributorId = req.getParameter("contributor");

		if (recipeName == null || recipeName.trim().isEmpty() || description == null || description.trim().isEmpty()) {
			messages.put("message", "Invalid recipe details. Name and description cannot be empty.");
		} else {
			try {
				int minutes = Integer.parseInt(stringMinutes);
				int contributorId = Integer.parseInt(stringContributorId);
				Timestamp submittedAt = new Timestamp(new Date().getTime());

				// Retrieve contributor (user) by ID
				Users contributor = usersDao.getUserById(contributorId);
				if (contributor == null) {
					messages.put("message", "Contributor with ID " + contributorId + " not found.");
				} else {
					// Create new recipe
					Recipes recipe = new Recipes(recipeName, minutes, steps, description, submittedAt, contributor);
					recipe = recipesDao.create(recipe);
					messages.put("message", "Successfully created recipe ID: " + recipe.getRecipeId());
				}
			} catch (NumberFormatException e) {
				messages.put("message", "Invalid number format for minutes or contributor ID.");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		req.getRequestDispatcher("/RecipeCreate.jsp").forward(req, resp);
	}
}
