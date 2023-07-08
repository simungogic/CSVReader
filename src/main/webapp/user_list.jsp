<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Listing</title>
<style>
	#users {
	  font-family: Arial, Helvetica, sans-serif;
	  border-collapse: collapse;
	  width: 60%;
	  position: absolute;
	  left: 20%;
	}
	
	#users td, #users th {
	  border: 1px solid #ddd;
	  padding: 8px;
	}
	
	#users tr:nth-child(even){background-color: #f2f2f2;}
	
	#users tr:hover {background-color: #ddd;}
	
	#users th {
	  padding-top: 12px;
	  padding-bottom: 12px;
	  text-align: left;
	  background-color: #6495ED;
	  color: white;
	}
</style>
</head>
<body>
<div>
	<a href="/CSVReader/index.html">Back To Upload Page</a>
</div>
<table id="users">
	<tr>
		<th>ID</th>
		<th>Ime</th>
		<th>Prezime</th>
		<th>Datum Rodjenja</th>
	</tr>
	<c:forEach items="${userList}" var="user">
		<tr>
			<td>${user.id}</td>
			<td>${user.firstName}</td>
			<td>${user.lastName}</td>
			<td>${user.dateOfBirth}</td>
		</tr>
	</c:forEach>
</table>

</body>
</html>