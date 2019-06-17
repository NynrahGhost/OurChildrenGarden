package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hsqldb.types.Charset;

import managing.DataBase;

@WebServlet("/Authentication")
@MultipartConfig
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static Connection conn;
	public static Connection conn2;
	
    public Authentication() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
    	//DataBase.init();
    	
    	
    	try {
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e1) {e1.printStackTrace();}
    	
    	
    	try {
			conn2 = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Ghost/Client-Server/ChildrenGarden/src/managing/Passwords.accdb");
			conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Ghost/Client-Server/ChildrenGarden/src/managing/AIS.accdb");
		} catch (SQLException e) {e.printStackTrace();}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		
		String password = request.getParameter("Password");

		if(DataBase.getPassword(Integer.parseInt(request.getParameter("UserType")), request.getParameter("PhoneNumber"),conn2).equals(password)) {
	    	response.getWriter().append("Successfull login");
	    	
	    	HttpSession session = request.getSession();
	    	session.setAttribute("UserID", DataBase.getID(request.getParameter("PhoneNumber"),conn));
	    	session.setAttribute("UserType", request.getParameter("UserType"));
	    	response.getWriter().append((String) session.getAttribute("UserID"));

		} else {
			response.getWriter().append("Incorrect user/password !");
		}
	}

	
	public static String hexDecoder(String hex) {
	    int l = hex.length();
	    byte[] data = new byte[l/2];
	    for (int i = 0; i < l; i += 2) {
	        data[i/2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
	                             + Character.digit(hex.charAt(i+1), 16));
	    }
	    
	    return new String(data, StandardCharsets.US_ASCII); //, StandardCharsets.US_ASCII
	}
}
