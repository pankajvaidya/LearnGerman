<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home page</title>
</head>

<h2>Please fill the blank text and submit</h2>
<form method="post" action="evaluateLangageComponent"> 

	${modifiedLanguageText}
	<input type="hidden" name="languageText" value="${languageText}">
	<input type="hidden" name="modifiedLanguageText" value="${modifiedLanguageText}">
	<input type="hidden" name="languageComponent" value="${languageComponent}">
    <input type="submit" value="Submit">
    
</form>
<body>
</body>
</html>