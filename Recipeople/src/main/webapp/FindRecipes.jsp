<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
                <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
                    <title>Find a Recipe</title>
                </head>

                <body>
                    <form action="findrecipes" method="post">
                        <h1>Search for a Recipe by Name</h1>
                        <p>
                            <label for="recipename">Recipe Name</label>
                            <input id="recipename" name="recipename" value="${fn:escapeXml(param.recipename)}">
                        </p>
                        <p>
                            <input type="submit">
                            <br /><br /><br />
                            <span id="successMessage"><b>${messages.success}</b></span>
                        </p>
                    </form>
                    <br />
                    <div id="recipeCreate"><a href="recipecreate">Create Recipe</a></div>
                    <br />
                    <h1>Matching Recipes</h1>
                    <table border="1">
                        <tr>
                            <th>Recipe Name</th>
                            <th>Minutes</th>
                            <th>Steps</th>
                            <th>Description</th>
                            <th>Submitted At</th>
                            <th>Contributor</th>
                            <th>Delete Recipe</th>
                            <th>Update Recipe</th>
                        </tr>
                        <c:forEach items="${recipes}" var="recipe">
                            <tr>
                                <td>
                                    <c:out value="${recipe.getRecipeName()}" />
                                </td>
                                <td>
                                    <c:out value="${recipe.getMinutes()}" />
                                </td>
                                <td>
                                    <c:out value="${recipe.getSteps()}" />
                                </td>
                                <td>
                                    <c:out value="${recipe.getDescription()}" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${recipe.getSubmittedAt()}" pattern="yyyy-MM-dd" />
                                </td>
                                <td>
                                    <c:out value="${recipe.getContributorId()}" />
                                </td>
                                <td><a href="recipedelete?recipeId=<c:out value='${recipe.getRecipeId()}' />">Delete</a>
                                </td>
                                <td><a href="recipeupdate?recipeId=<c:out value='${recipe.getRecipeId()}' />">Update</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </body>

                </html>