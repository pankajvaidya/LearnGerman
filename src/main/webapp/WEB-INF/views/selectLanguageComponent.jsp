<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>

<h2>Enter German Language Data set</h2>
    
<form method="post" action="blankOutLangageComponent"> 
    Enter Text : <br> <textarea name="languageText" rows="10" cols="80" maxlength="700" required></textarea>
    <br>
    Select Option to test: 
    <select type="text" name="languageComponent"/>
    	<c:forEach items="${languageComponentList}" var="languageComponent">
        	<option value="${languageComponent.language_component_id}">${languageComponent.language_component_name}</option>
    	</c:forEach>
    </select>
    <br>
<input type="submit" value="Submit">
</form>



<br><br><br>
<b>Known limitations:</b><br>
The application pulls out the words der, das and die even when they are pronouns and not articles.
</body>

    



<body>

</body>
</html>