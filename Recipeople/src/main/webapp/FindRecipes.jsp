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
                        <h1>Search for a Recipe by ID or Tag Name</h1>
                        <p>
                            <label for="recipeId">Recipe ID</label>
                            <input id="recipeId" name="recipeId" value="${fn:escapeXml(param.recipeId)}">
                        </p>
                        <p>
                            <label for="tagName">Tag Name</label>
                            <input id="tagName" name="tagName" value="${fn:escapeXml(param.tagName)}">
                        </p>
                        <p>
                            <input type="submit" value="Search">
                            <br /><br /><br />
                            <span id="successMessage"><b>${messages.success}</b></span>
                        </p>
                    </form>
                    <br />
                    <div id="recipeCreate"><a href="recipecreate">Create Recipe</a></div>
                    <div id="reciperecommendations"><a href="reciperecommendations">Recommend Recipes</a></div>
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
                                    <a href="recipecontributors?recipeid=${recipe.recipeId}">
                                        <c:out value="${recipe.user.userId}" />
                                    </a>
                                </td>
                                <td><a href="recipedelete?recipeid=${recipe.recipeId}">Delete</a></td>
                                <td><a href="recipeupdate?recipeid=${recipe.recipeId}">Update</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </body>

                </html>