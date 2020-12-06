<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Add Counter</title>
		<link type="text/css" rel="stylesheet" href="css/counter.css">
		
	</head>
	
	<body>
		<div id="rcorners1">
			<p class= "add"> ${AddCounter} </p>
		</div>
		<div id="rcorners2">
			<p class= "update"></p>
		</div>
		<div id="rcorners3">
			<p class= "delete"></p>
		</div>
		<form action="">
			<p>
				<a href="StudentControllerServlet">Back to List</a>
			</p>
		</form>
	</body>
</html>