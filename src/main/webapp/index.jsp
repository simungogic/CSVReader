<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSV File Uploader</title>
<style>
	.wrapper {
	  position: absolute;
	  left: 40%;
	  top: 10%;
	  width: 20%;
	  border: black 1px solid;
	  border-radius: 10px;
	}
	div {
		text-align: center;
	}
	#output_link {
	  background: none!important;
	  border: none;
	  padding: 0!important;
	  /*optional*/
	  font-family: arial, sans-serif;
	  /*input has OS specific font-family*/
	  color: #069;
	  text-decoration: underline;
	  cursor: pointer;
	  margin-top: 7px;
	}
	#submit, input[type=file]::file-selector-button {
	  background-color:  #008CBA;
	  border: none;
	  color: white;
	  padding: 8px 16px;
	  text-align: center;
	  text-decoration: none;
	  display: inline-block;
	  font-size: 12px;
	}
	#submit {
		width: 50%;
	}
	#submit:hover, input[type=file]::file-selector-button:hover {
		background-color:  #00008B;
	}
	form div {
		margin-bottom: 10px;
	}
	div input {
		margin-left: 25%;
	}
</style>
</head>
<body>
	<div class="wrapper">
		<div><h1>CSV File Uploader</h1></div>
		<form id="upload_form" action="main" method="post" enctype="multipart/form-data">
			<div>
				<input type="file" name="csv_file" accept=".csv" required />
			</div>
			<div>
				<button id="submit" type="submit">Submit</button>
			</div>
		</form>
 		<form action="listing">
			 <button id="output_link" type="submit">Get Database Output</button>
		</form>
	</div>
</body>
<script>
	<c:if test = "${not empty errors}">
		console.log("Rows not inserted: ");
		<c:forEach items="${errors}" var="error">
			console.log("${error}");
		</c:forEach>
	</c:if>
	console.log("\n");
	<c:if test = "${not empty users}">
		console.log("Rows inserted: ");
		<c:forEach items="${users}" var="user">
			console.log("${user.firstName}" + ", " + "${user.lastName}" + ", " + "${user.dateOfBirth}");
		</c:forEach>
	</c:if>
</script>
</html>