package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import managing.DataBase;


@WebServlet("/UpdateParent")
@MultipartConfig
public class UpdateParent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static Connection conn;
	public static Connection conn2;
	@Override
    public void init(ServletConfig config) throws ServletException
    {
    	try {
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e1) {e1.printStackTrace();}
    	
    	
    	try {
			conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Ghost/Client-Server/ChildrenGarden/src/managing/AIS.accdb");
			conn2 = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Ghost/Client-Server/ChildrenGarden/src/managing/Passwords.accdb");
    	} catch (SQLException e) {e.printStackTrace();}
    }
	
    public UpdateParent() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		
		try {
			System.out.println(request.getParameter("KidsToAssign"));
			JSONArray kids = new JSONArray("["+request.getParameter("KidsToAssign")+"]");
			List<Integer> kidsAdd = new ArrayList<Integer>();
		
			for (int i=0; i<kids.length(); i++) {
		    	kidsAdd.add( kids.getInt(i) );
			}
			
			kids = new JSONArray("["+request.getParameter("KidsToDischarge")+"]");
			List<Integer> kidsDelete = new ArrayList<Integer>();
			
			for (int i=0; i<kids.length(); i++) {
		    	kidsDelete.add( kids.getInt(i) );
			}
			
			List<String> params = new ArrayList<String>();
			params.add(request.getParameter("Surname"));
			params.add(request.getParameter("Name"));
			params.add(request.getParameter("Patr"));
			
			params.add(request.getParameter("Work"));
			params.add(request.getParameter("Pos"));
			
			params.add(request.getParameter("Tel"));
			params.add(request.getParameter("TelWork"));
			params.add(request.getParameter("Tel1"));
			params.add(request.getParameter("Tel2"));
			
			
			DataBase.updateParent(Integer.parseInt(request.getParameter("ParentID")), params, kidsAdd, kidsDelete, conn);
			conn2.createStatement().executeQuery("UPDATE Parent SET Password = '"+request.getParameter("Password")+"' WHERE Phone = '"+request.getParameter("Tel")+"'");
		} catch (JSONException | SQLException e) {e.printStackTrace();}
	}

}
