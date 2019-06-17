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


@WebServlet("/GroupList")
public class GroupList extends HttpServlet {
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
	
    public GroupList() {
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
		
		PrintWriter out = response.getWriter();
		
		ResultSet rs = null;
		if(Integer.parseInt((String)session.getAttribute("UserType")) == 3) {
			rs = DataBase.getGroupsOfAdmin(conn);
			
			out.append("<div class='kidField'><a><div class='kid' onclick='createGroup()' style='text-align: center; background-color: #7734ff;'>Додати групу</div></a></div>");
		} else {
			rs = DataBase.getGroupsOfTutor(Integer.parseInt(userID), conn);
		}
		
		try {
			if(rs != null) {
				if(Integer.parseInt((String)session.getAttribute("UserType")) == 3) {
					while(rs.next()) {
						out.append(	"<div class='kidField'>"
								+ 		"<a style='display:flex;'>"
								+ 			"<div style='flex-grow: 3;' onclick='openTable(event)' class='kid' id='" + rs.getInt(1) + "'>(" + rs.getInt(1) + ") " + rs.getString(2) + "</div>"
								+ 			"<button onclick='deleteGroup(event)' style='background-color:red; border-radius:25px; margin:auto; height:34px; border: none;'>Видалити</button>"
								+		"</a>"
								+ 	"<div class='kidTable' id='t" + rs.getInt(1) + "'></div></div>");
					}
				} else {
					while(rs.next()) {
						out.append("<div class='kidField'><a><div onclick='openTable(event)' class='kid' id='" + rs.getInt(1) + "'>" + rs.getString(2) + "</div></a><div class='kidTable' id='t" + rs.getInt(1) + "'></div></div>");
					}
				}
			} else {
				out.append("До вас на прив'язано жодної групи");
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		out.flush();
	}

}
