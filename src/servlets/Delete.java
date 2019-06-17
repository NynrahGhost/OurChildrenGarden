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

import managing.DataBase;


@WebServlet("/Delete")
@MultipartConfig
public class Delete extends HttpServlet {
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
	
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		switch(request.getParameter("Entity")) {
			case "Parent" : DataBase.executeQuery("DELETE FROM [Опікуни] WHERE Protector_ID = " + Integer.parseInt(request.getParameter("ID"))); break;
			case "Kid" : DataBase.executeQuery("DELETE FROM [Діти] WHERE Kid_ID = " + Integer.parseInt(request.getParameter("ID"))); break;
			case "Tutor" : DataBase.executeQuery("DELETE FROM [Вихователі] WHERE Tutor_ID = " + Integer.parseInt(request.getParameter("ID"))); break;
			case "Group" : DataBase.executeQuery("DELETE FROM [Групи] WHERE Group_ID = " + Integer.parseInt(request.getParameter("ID"))); break;
		}
	}

}
