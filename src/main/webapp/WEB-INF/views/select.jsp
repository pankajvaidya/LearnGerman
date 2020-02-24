<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>

<h2>Enter German Language Data set</h2>
    
<form method="post" action="blankOutLangageComponent"> 
    Enter Text : <br> <textarea name="languageText" rows="10" cols="70" required></textarea>
    <br>
    Select Option to test: 
    <select type="text" name="languageComponent"/>
    	<c:forEach items="${languageComponentList}" var="map">
        	<option value="${map.language_component_id}">${map.language_component_name}</option>
    	</c:forEach>
    </select>
<input type="submit" value="Submit">
</form>



<br><br><br>
<b>Known limitations:</b><br>
The application pulls out the words der, das and die even when they are pronouns and not articles.
</body>

    



<body>

</body>
</html>