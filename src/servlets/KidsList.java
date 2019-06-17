package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		
		int userType = Integer.parseInt((String) session.getAttribute("UserType"));
		PrintWriter out = response.getWriter();
		
		if(userType == 1) {
			
			ResultSet rs = DataBase.getKidsOfParents(Integer.parseInt(userID), conn);
			
			try {
				if(rs != null) {
					while(rs.next()) {
						out.append("<div class='kidField'><a><div onclick='openTable(event)' class='kid' id='" + rs.getInt(1) + "'>" + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + "</div></a><div class='kidTable' id='t" + rs.getInt(1) + "'></div></div>");
					}
					
				} else {
					out.append("До вас не прив'язано жодної дитини");
				}
			} catch (SQLException e) {e.printStackTrace();}
			
		} else if(userType == 3) {
			
			try {
				Statement st = conn.createStatement();
				
				ResultSet rs = st.executeQuery("SELECT Kid_ID, FN_Surname, FN_Name, FN_Patr FROM Діти;");
				
				out.append("<div class='kidField'><a><div class='kid' onclick='createKid()' style='text-align: center; background-color: #7734ff;'>Додати дитину</div></a></div>");
				
				if(rs != null) {
					while(rs.next()) {
						out.append(	"<div class='kidField'>"
								+ 		"<a style='display:flex;'>"
								+ 			"<div style='flex-grow: 3;' onclick='openTable(event)' class='kid' id='" + rs.getInt(1) + "'>" + rs.getString(2) + " " + rs.getString(3) + " " + isNull(rs.getString(4)) + "</div>"
								+ 			"<button onclick='deleteKid(event)' style='background-color:red; border-radius:25px; margin:auto; height:34px; border: none;'>Видалити</button>"
								+ 		"</a>"
								+ 		"<div class='kidTable' style='text-align: center;' id='t" + rs.getInt(1) + "'></div>"
								+ 	"</div>");
					}
				} else {
					out.append("Жодної дитини не знайдено");
				}
			} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		out.flush();
	}
	
	private static String isNull(String str) {
		return str == null ? "" : str;
	}

}
