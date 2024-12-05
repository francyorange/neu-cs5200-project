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
                    <form action="reciperecommendations" method="post">
                        <h1>Recommend for Recipes for User</h1>
                        <p>
                            <label for="userId">User ID</label>
                            <input id="userId" name="userId" value="${fn:escapeXml(param.userId)}">
                        </p>
                        <p>
                            <input type="submit" value="Recommend">
                            <br /><br /><br />
                            <span id="successMessage"><b>${messages.success}</b></span>
                        </p>
                    </form>
                    <br />
                    <br />
                    <h1>Recommending Recipes</h1>
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
                                <td><a href="recipereviews?recipeid=${recipe.recipeId}">
                                        <c:out value="${recipe.recipeName}" />
                                    </a></td>
                                <td>
                                    <c:out value="${recipe.minutes}" />
                                </td>
                                <td>
                                    <c:out value="${recipe.steps}" />
                                </td>
                                <td>
                                    <c:out value="${recipe.description}" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${recipe.submittedAt}" pattern="MM-dd-yyyy hh:mm:ss" />
                                </td>
                                <td>
                                    <c:out value="${recipe.user.userId}" />
                                </td>
                                <td><a href="recipedelete?recipeId=${recipe.recipeId}">Delete</a></td>
                                <td><a href="recipeupdate?recipeId=${recipe.recipeId}">Update</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </body>

                </html>