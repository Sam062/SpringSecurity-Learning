<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="registerUser" method="POST">
		<pre>
			User Registration
		NAME : <input type="text" name="userName">
		EMAIL: <input type="email" name="userEmail">
		PWD  : <input type="password" name="pwd">
		ROLES: 
			<input type="checkbox" name="roles" value="ADMIN"> ADMIN
			<input type="checkbox" name="roles" value="EMPLOYEE"> EMPLOYEE
			<input type="checkbox" name="roles" value="STUDENT"> STUDENT
				
			<input type="submit" value="Register User">
	</pre>
	</form>
	
	<div>
		${saveStatus}
	</div>
</body>
</html>