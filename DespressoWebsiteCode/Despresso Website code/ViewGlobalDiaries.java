package despressoMeter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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
 * Servlet implementation class ViewDiaries
 */
public class ViewGlobalDiaries extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewGlobalDiaries() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String email, diaryEmail, title="", score="", body="", date="";
		email = request.getParameter("email");
		//String[] diary = new String[4];
		ArrayList<String[]> rows = new ArrayList<String[]>(0);
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
			 System.out.println("Creating statement...   and email = " + email);
			 stmt = (Statement) conn.createStatement();
			 String sql;
			 //sql = "SELECT * FROM diary3 WHERE email=\'"+ email + "\';";
			 sql = "SELECT * FROM diary3;";
			 System.out.println(sql);
			 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			 System.out.println(rs);
			 
		 	//STEP 5: Extract data from result set
			 while(rs.next()){
				 String[] diary = new String[4];
				 //Retrieve by column name
				 diaryEmail = rs.getString("email");
				 diary[0] = rs.getString("title");
				 System.out.println(diary[0]);
				 diary[1] = rs.getString("score");
				 diary[2] = rs.getString("body");
				 diary[3] = rs.getString("date");
				 rows.add(diary);
				 //gender = rs.getString("gender");
				 //ID = rs.getString("ID");
				 //birth = rs.getString("birth");
				 //race = rs.getString("race");
				 //income = rs.getString("income");
				 //marital_status = rs.getString("marital_status");
				 //sex_orientation = rs.getString("sex_orientation");
				 
				 System.out.println(diaryEmail + title);
			 }
		} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		////////////////////////////////////
		response.setContentType("text/html;charset=UTF-8");
		final PrintWriter out = response.getWriter();
		
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		String allDiariesHtml = docType + 
		//out.println(docType +
				"<html>\n" +
				"	<head><title>View Diaries</title> "+
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
				"		<ul>\n";
		for (int i = 0; i < rows.size(); i++){// add all rows of diaries together
			allDiariesHtml += "<li><b>" + rows.get(i)[0] + "</b>  |  Despresso Score: " + rows.get(i)[1] +
					"  <br>  " + rows.get(i)[2] + "  <br>  </li>\n";
		}
				allDiariesHtml += 
						"<p>" + rows.size() + " total entries</p>" +
					    "<form action='http://3.131.82.23:80/FirstServlet/UserHome' method='post'>"+
					    "	<input type='hidden' name='email' value='" + email + "'/>"+
					    "	<button type='submit' class='submit'>back Home</button>"+
					    "</form>"+
					    "<form action='http://3.131.82.23:80/FirstServlet/FilterGlobalDiaries' method='post'>"+
				        "<p>filter by specified category and value. options: gender, race, marital_status, sex_orientation</p>"+
					    "<input name='category' placeholder='category' type='text' required/>"+
					    "<input name='value' placeholder='value' type='text' required/>"+
					    "	<input type='hidden' name='email' value='" + email + "'/>"+
				        "<button type='submit' class='submit'>filter</button>"+
				    "</form>"+
				"	</body>\n" +
				"</html>";
		out.println(allDiariesHtml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
