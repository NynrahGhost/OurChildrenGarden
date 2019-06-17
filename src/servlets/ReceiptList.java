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


@WebServlet("/ReceiptList")
public class ReceiptList extends HttpServlet {
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
	
    public ReceiptList() {
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
		
		
		if(Integer.parseInt((String)session.getAttribute("UserType")) == 3) {
			out.append(	"<div class='kidField' style='text-align:center;'>"
					+ 		"<a>"
					+ 			"<div class='kid' style='text-align: center; background-color: #7734ff;'>Додати групу</div>"
					+ 			"<input id='fee' placeholder='Благодійний внесок'><input id='month' placeholder='Плата за місяць'><input id='day' placeholder='Плата за день'><input id='part' placeholder='Частина оплати державою'><button onclick='createTariff()'>Новий тариф</button><br/>"
					+		"</a>"
					+ 	"</div>");
		}
		
		//try {
			
		//} catch (SQLException e) {e.printStackTrace();}
		
		out.flush();
	}

}
