package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UpdateKid")
@MultipartConfig
public class UpdateKid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static Connection conn;
	
	@Override
    public void init(ServletConfig config) throws ServletException
    {
    	try {
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e1) {e1.printStackTrace();}
    	
    	
    	try {
			conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Ghost/Client-Server/ChildrenGarden/src/managing/AIS.accdb");
		} catch (SQLException e) {e.printStackTrace();}
    }
	
    public UpdateKid() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		try {
			//System.out.println(request.getParameter("BirthDate").replace('-', '/'));
			conn.createStatement().executeUpdate("UPDATE Діти "
					+ 		"SET FN_Surname=\""+request.getParameter("Surname")+"\", FN_Name=\""+request.getParameter("Name")+"\", FN_Patr=\""+request.getParameter("Patr")
					+		"\", Birth_date=#"+request.getParameter("BirthDate")+"#, Adr_postal="+Integer.parseInt(request.getParameter("Postal"))+", Adr_region=\""+request.getParameter("Region")
					+ 		"\", Adr_city=\""+request.getParameter("City")+"\", Adr_district=\""+request.getParameter("District")+"\", Adr_street=\""+request.getParameter("Street")
					+ 		"\" WHERE Kid_ID = " + Integer.parseInt(request.getParameter("KidID")) + ";");
		} catch (SQLException e) {e.printStackTrace();}
	}
}