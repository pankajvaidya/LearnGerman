<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>    
<!DOCTYPE html>
<html>
<head>

</head>
<body>

	<h2> Evaluated Text </h2>
	${evaluationResultString}
	<hr>
	<h2> Original Text </h2>
	${languageText}
	<hr>
	<table border="1">
		<tr>
			<th>Correct</th>
			<th>Total</th>
			<th>Percentage</th>
		</tr>
		<tr>
			<td>${correctCount}</td>
			<td>${totalCount}</td>
			<td>${percentage}</td>
		</tr>
	</table>
	<hr>
	<a href="select">enter new German language data set</a>
	<br><br>
	<a href="/">Home Page</a>
</body>
</html>