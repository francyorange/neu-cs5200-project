<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Delete a Recipe</title>
</head>
<body>
    <h1>${messages.title}</h1>
    
    <form action="recipedelete" method="post">
        <p>
            <label for="recipeid">Recipe ID</label>
            <input id="recipeid" name="recipeid" value="${fn:escapeXml(param.recipeid)}" 
                   <c:if test="${messages.disableSubmit eq 'true'}">disabled="disabled"</c:if> >
        </p>
        
        <p>
            <input type="submit" value="Delete Recipe" 
                   <c:if test="${messages.disableSubmit eq 'true'}">disabled="disabled"</c:if> >
        </p>
    </form>
</body>
</html>
