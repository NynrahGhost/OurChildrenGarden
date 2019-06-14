package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managing.DataBase;


@WebServlet("/KidsList")
public class KidsList extends HttpServlet {
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
	
    public KidsList() {
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
		
		ResultSet rs = DataBase.getKidsOfParents(Integer.parseInt(userID), conn);
		
		PrintWriter out = response.getWriter();
		
		try {
			if(rs != null) {
				while(rs.next()) {
					out.append("<div class='kidField'><a><div onclick='openTable(event)' class='kid' id='" + rs.getInt(1) + "'>" + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + "</div></a><div class='kidTable' id='t" + rs.getInt(1) + "'></div></div>");
				}
			} else {
				out.append("До вас на прив'язано жодної дитини");
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		out.flush();
	}

}
