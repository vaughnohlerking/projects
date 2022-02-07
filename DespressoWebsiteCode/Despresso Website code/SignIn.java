package despressoMeter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class SignIn
 */
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String email, name="", ID="", password="", birth="", gender="", race="", income="", location="", marital_status="", sex_orientation="";
		email = request.getParameter("email");
		
		///////////////////////////////
		// JDBC driver name and database URL
		 String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		 //String DB_URL = "jdbc:mysql://52.26.86.130:3306/student";
		 String DB_URL = "jdbc:mysql://localhost:3306/despresso2";

		 // Database credentials
		 String USER = "root";
		 String PASS = "";

		 Connection conn = null;
		 Statement stmt = null;
		 //STEP 2: Register JDBC driver
		 try {
			 Class.forName("com.mysql.jdbc.Driver");
			 //STEP 3: Open a connection
			 System.out.println("Connecting to database...");
			 conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			 //STEP 4: Execute a query
			 System.out.println("Creating statement...");
			 stmt = (Statement) conn.createStatement();
			 String sql;
			 sql = "SELECT * FROM user WHERE email=\'"+ email + "\';";
			 System.out.println(sql);
			 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			 
		 	//STEP 5: Extract data from result set
			 while(rs.next()){
				 //Retrieve by column name
				 name = rs.getString("name");
				 location = rs.getString("location");
				 gender = rs.getString("gender");
				 ID = rs.getString("ID");
				 birth = rs.getString("birth");
				 race = rs.getString("race");
				 income = rs.getString("income");
				 marital_status = rs.getString("marital_status");
				 sex_orientation = rs.getString("sex_orientation");
				 
				 System.out.println(email);
			 }
		} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		////////////////////////////////////
		response.setContentType("text/html;charset=UTF-8");
		final PrintWriter out = response.getWriter();
		
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		out.println(docType +
				"<html>\n" +
				"	<head><title>User Registration</title> "+
				"	<style>"
				+ "	body { "
				//+		"background-color:tan;"
				+ "		background-image: url('http://3.144.19.75/Pic/sighn.png');"
				+ "		font-family: Arial, sans-serif;"
				+ "		paddingLeft: 30px;"
				+ "}" +
				"	</style>" +
				"	</head>\n" +
				"	<body>\n" +
				"		<h1 align=\"center\"><img src='http://3.144.19.75/Pic/200.gif'>Hello " + name + ", the following is your profile info:</h1>\n" +
				"		<ul>\n" +
				"			<li><b>Your name</b>: " + name + "</li>\n" +
				"			<li><b>Your email</b>: " + email + "</li>\n" +
				"			<li><b>Your password</b>: " + password + "</li>\n" +
				"			<li><b>Your gender</b>: " + gender + "</li>\n" +
				"			<li><b>Your ID</b>: " + ID + "</li>\n" +
				"			<li><b>Your birthdate</b>: " + birth + "</li>\n" +
				"			<li><b>Your race</b>: " + race + "</li>\n" +
				"			<li><b>Your income</b>: " + income + "</li>\n" +
				"			<li><b>Your marital status</b>: " + marital_status + "</li>\n" +
				"			<li><b>Your sexual orientation</b>: " + sex_orientation + "</li>\n" +
				"		</ul>\n" +
				"	</body>\n" +
				"</html>"
		);
		//response.sendRedirect("http://www.google.com");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
