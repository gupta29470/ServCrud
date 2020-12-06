package com.java.servlet.Fun_University.web;

import java.util.*;
import java.sql.*;
import javax.sql.*;

public class StudentDBUtil {
	private static DataSource dataSource;

	public StudentDBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Method to data from database
	public List<Student> getStudent() throws Exception {

		List<Student> students = new ArrayList<>();

		// For database operations
		Connection conn = null;
		Statement stmt = null;
		ResultSet res = null;

		try {
			
			// Get database connection.
			conn = dataSource.getConnection();

			// Create SQL select query
			String sql = "select id, firstName, lastName, email, concat(day(entryDate),' ', monthname(entryDate),' ', year(entryDate)) as entryDate from Fun_Student \r\n"
					+ "order by YEAR(entryDate) DESC, MONTH(entryDate) DESC, DAY(entryDate) desc,\r\n"
					+ "HOUR(entryDate) desc, MINUTE(entryDate) desc, SECOND(entryDate) desc";
			
			// Create statement.
			stmt = conn.createStatement();

			
			// Process result.
			res = stmt.executeQuery(sql);
			while (res.next()) {
				int id = res.getInt("id");
				String firstName = res.getString("firstName");
				String lastName = res.getNString("lastName");
				String email = res.getString("email");
				String entryDate = res.getString("entryDate");
				

				// Create new Student object.
				Student temp = new Student(id, firstName, lastName, email, entryDate);
				

				// Add student objct to list
				students.add(temp);
			}
			
			return students;

		} 
		
		finally {
			// Best practice to close database connections.
			close(conn, stmt, res);
		}

	}

	
	// Method to close database connections.
	public static void close(Connection conn, Statement stmt, ResultSet res) throws Exception {
		if (conn != null) { // not actually close connection but makes it available for someone else
			conn.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (res != null) {
			res.close();
		}
	}

	
	
	// Method to "ADD" new data into database
	public static void addStudent(Student theStudent) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			
			// Get database connection.
			conn = dataSource.getConnection();

			// Create SQL insert query
			String sql = "insert into Fun_Student(firstName, lastName, email)" + "values(?, ?, ?)";
			stmt = conn.prepareStatement(sql);

			
			// Set parameters values to student
			// From 1 because 0 is ID
			stmt.setString(1, theStudent.getFirstName());
			stmt.setString(2, theStudent.getLastName());
			stmt.setString(3, theStudent.getEmail());

			
			// Execute the statement
			stmt.execute();

		} 
		
		finally {
			// Best practice to close database connections.
			close(conn, stmt, null);
		}

	}

	
	// Method to get student data on update form.
	public Student getStudent(String theStudentId) throws Exception {
		
		// Creating student object.
		Student theStudent = null;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		int studentId;

		try {
			
			// Convert StudentID(string) to integer.
			studentId = Integer.parseInt(theStudentId);

			
			// Get database connection
			conn = dataSource.getConnection();

			// Create SQL query to get all data for a particular student id.
			String sql = "select * from Fun_Student where id = ?";

			// Create Prepared Statement.
			stmt = conn.prepareStatement(sql);

			// Set parameters.
			stmt.setInt(1, studentId);

			// Execute Query
			res = stmt.executeQuery();

			if (res.next()) {
				String firstName = res.getString("firstName");
				String lastName = res.getString("lastName");
				String email = res.getString("email");

				theStudent = new Student(studentId, firstName, lastName, email);
			} 
			else {
				throw new Exception("Could not find student id : " + studentId);
			}
			
			return theStudent;
		} 
		finally {
			// Best practice to close database connections.
			close(conn, stmt, res);
		}

	}

	
	
	// Method to "UPDATE" student data.
	public void updateStudent(Student theStudent) throws Exception {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			
			// Get database connection
			conn = dataSource.getConnection();
			
			
			// Create SQL update query
			String sql = "update Fun_Student set firstName = ?, lastName = ?, email = ?, entryDate = now() where id = ?";
			
			
			// Create Prepare Statement
			stmt = conn.prepareStatement(sql);
			
			
			// Set parameters
			stmt.setString(1, theStudent.getFirstName());
			stmt.setString(2, theStudent.getLastName());
			stmt.setString(3, theStudent.getEmail());
			stmt.setInt(4, theStudent.getId());
			
			
			// Execute statement
			stmt.execute();
			
		}
		finally {
			close(conn, stmt, null);
		}

	}
	
	
	// Method to "DELETE" student data
	public void deleteStudent(int studentId) throws Exception{
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			
			// Get database connection
			conn = dataSource.getConnection();
			
			
			// Create SQL delete query
			String sql = "delete from Fun_Student where id = ?";
			
			
			//Prepare the statement
			stmt = conn.prepareStatement(sql);
			
			
			// Set parameters
			stmt.setInt(1, studentId);
			
			
			// Execute the statement
			stmt.execute();
			
		}
		finally {
			close(conn, stmt, null);
		}
	}

}
