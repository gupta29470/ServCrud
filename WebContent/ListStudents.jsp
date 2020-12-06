<%@ page import="java.util.*, com.java.servlet.Fun_University.web.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Fun University</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Fun University</h2>
		</div>
	</div>


	<div id="container">

		<div id="content">

			<!-- put new button: Add Student -->
			<input type="button" value="Add Student"
				onclick="window.location.href='AddStudentForm.jsp'; return false;"
				class="addStudentButton" />


			<table>

				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Entry/Update Date</th>
					<th>Action</th>
				</tr>

				<c:forEach var="tempStudent" items="${StudentList}">

					<!-- Set up link for each student -->
					<c:url var="templink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD" />

						<!-- Each link has unique student id so we are updating according 
						to student id -->
						<c:param name="studentId" value="${tempStudent.id}" />

					</c:url>
					
					<!-- Set up link to delete a student -->
					<c:url var="deletelink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE" />

						<!-- Each link has unique student id so we are deleting according 
						to student id -->
						<c:param name="studentId" value="${tempStudent.id}" />

					</c:url>

					<tr>
						<td>${tempStudent.firstName}</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
						<td>${tempStudent.entryDate}</td>
						<td>
							<a href="${templink}">Update</a>
							|
							<a href="${deletelink}"
							onclick="if(!(confirm('Are you sure you want to delete this student'))) return false">
							Delete</a>
						</td>
					</tr>

				</c:forEach>

			</table>

		</div>

	</div>
</body>
</html>