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
public class SubmitDiary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitDiary() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String email, title, score, body, date;
		
		body      	= request.getParameter("body");
		email 		= request.getParameter("email");
		score 	= request.getParameter("score");
		title 	= request.getParameter("title");
		date  	= request.getParameter("date");
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
			String sql = "INSERT INTO diary3 (email, title, score, body) " +
						 "VALUES (\'" + email + "\', \'" + title + "\', \'" + score + "\', \'" + body + "\');";
			//sql = "SELECT * FROM student";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		 	//STEP 5: Extract data from result set
			// Nah
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
				"	<head><title>Diary Submit</title> "+
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
				"		<h1 align=\"center\">Success! submit another?</h1>\n" +
	"<h1>New Diary</h1>"+
    "<form action='http://3.131.82.23:80/FirstServlet/UserHome' method='post'>"+
    "	<input type='hidden' name='email' value='" + email + "'/>"+
    "	<button type='submit' class='submit'>back Home</button>"+
    "</form>"+
    "<form action='http://3.131.82.23:80/FirstServlet/SubmitDiary' method='post'>"+
    "	<input type='hidden' name='email' value='" + email + "'/><br>"+
    "	<input type='text' name='title' placeholder='title' required/><br>"+
    "	<input type='text' name='score' placeholder='Despresso Score' required/><br>"+
    "	<textarea name='body' placeholder='diary here'/></textarea><br>"+
    //"	<input type='text' name='date' placeholder='Date'/><br>"+
    "	<button type='submit' class='submit'>Submit Diary</button><br>"+
    "</form>"+
				"	</body>\n" +
				"</html>"
		);
		
	}
	  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
