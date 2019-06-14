package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managing.DataBase;


@WebServlet("/KidsTable")
@MultipartConfig
public class KidsTable extends HttpServlet {
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
	
    public KidsTable() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("UserID");
		if(userID == null) {
            //response.sendRedirect("index.jsp");
            return;
        }
		//System.out.println(request.getParameter("KidID"));
		ResultSet rs = DataBase.getKidAttendance(Integer.parseInt(request.getParameter("KidID")), conn);
		
		PrintWriter out = response.getWriter();
		
		out.append("<table class='table table-sm table-dark'>");
		
		try {
			if(rs != null) {
				while(rs.next()) {
					if(rs.getBoolean(1)) {
						out.append("<tr class='bg-primary'><td>" + rs.getDate(2).toString() + "</td><td>" + rs.getString(3) + "</td></tr>");
					} else {
						out.append("<tr class='bg-danger'><td>" + rs.getDate(2).toString() + "</td><td>" + rs.getString(4) + "</td></tr>");
					}
				}
			} else {
				out.append("Жодного заняття в вашої дитини ще не відбулося!");
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		out.append("</table>");
		
		out.flush();
	}

}
