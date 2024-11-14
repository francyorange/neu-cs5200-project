<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Recipe</title>
</head>
<body>
	<h1>Create a Recipe</h1>
	<form action="recipecreate" method="post">
		<p>
			<label for="recipename">RecipeName</label>
			<input id="recipename" name="recipename" value="">
		</p>
		<p>
			<label for="minutes">Minutes</label>
			<input id="minutes" name="minutes" value="">
		</p>
		<p>
			<label for="steps">Steps</label>
			<input id="steps" name="steps" value="">
		</p>
		<p>
			<label for="description">Description</label>
			<input id="description" name="description" value="">
		</p>
		<p>
			<label for="contributor">Contributor</label>
			<input id="contributor" name="contributor" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>

</body>
</html>