package despressoMeter;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class HelloWorld
 */
public class EditProfileSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfileSubmit() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String ID, name, email, password, birth, race, location, gender
		,income,marital_status, sex_orientation;
		name 		= request.getParameter("name");
		//ID      	= request.getParameter("ID");
		email 		= request.getParameter("email");
		location 	= request.getParameter("location");
		gender 		= request.getParameter("gender");
		gender		= gender.substring(0,1);
		password 	= request.getParameter("password");
		income  	= request.getParameter("income");
		marital_status 	= request.getParameter("marital_status");
		sex_orientation 	= request.getParameter("sex_orientation");
		birth		= request.getParameter("birth");
		race 		= request.getParameter("race");
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
			String sql = "DELETE FROM user WHERE email = '" + email +"';";
			
			//sql = "SELECT * FROM student";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO user (name, email, password, gender, race, income, location, marital_status, sex_orientation) " +
					 "VALUES ( \'" + name + "\', \'" +
					email + "\', \'" + password +
					"\', \'" + gender + "\', \'" + race + "\', \'" + 
					income + "\', \'" + location + "\', \'" + marital_status +
					"\', \'" + sex_orientation + "\');";
			stmt.executeUpdate(sql);
		 	//STEP 5: Extract data from result set

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
				"	<head><title>Delete Diary</title> "+
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
				"		<h1 align=\"center\">DespressoMeter</h1>\n" +
				"			<p> " + name + " updated their profile</p>" +
				"<form action='http://3.131.82.23:80/FirstServlet/UserHome' method='post'>"+
		    		"<input type='hidden' name='email' value='" + email + "'/>"+
		    		"<button type='submit' class='submit'>Back Home</button>"+
		    	"</form>"+
				"	</body>\n" +
				"</html>"
		);
		//response.sendRedirect("http://3.144.19.75:80/SignInScreen.html");
	}
	  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
