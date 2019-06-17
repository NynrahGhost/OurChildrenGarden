package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managing.DataBase;


@WebServlet("/GroupTable")
@MultipartConfig
public class GroupTable extends HttpServlet {
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
	
    public GroupTable() {
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
            return;
        }
		
		ResultSet rs = DataBase.getGroupKid(
				Integer.parseInt(request.getParameter("GroupID")),
				Integer.parseInt(request.getParameter("Month")),
				Integer.parseInt(request.getParameter("Year")),
				conn);
		
		PrintWriter out = response.getWriter();
		
		int month = Integer.parseInt(request.getParameter("Month"));
		
		String sel="<select name=\"Month\" style='padding: 3.5px;'>\r\n" + 
				"    <option "+ (month == 1 ? "selected" : "") + " value=\"1\">Січень</option>\r\n" + 
				"    <option "+ (month == 2 ? "selected" : "") + " value=\"2\">Лютий</option>\r\n" + 
				"    <option "+ (month == 3 ? "selected" : "") + " value=\"3\">Березень</option>\r\n" + 
				"    <option "+ (month == 4 ? "selected" : "") + " value=\"4\">Квітень</option>\r\n" + 
				"    <option "+ (month == 5 ? "selected" : "") + " value=\"5\">Травень</option>\r\n" + 
				"    <option "+ (month == 6 ? "selected" : "") + " value=\"6\">Червень</option>\r\n" + 
				"    <option "+ (month == 7 ? "selected" : "") + " value=\"7\">Липень</option>\r\n" + 
				"    <option "+ (month == 8 ? "selected" : "") + " value=\"8\">Серпень</option>\r\n" + 
				"    <option "+ (month == 9 ? "selected" : "") + " value=\"9\">Вересень</option>\r\n" + 
				"    <option "+ (month == 10 ? "selected" : "") + " value=\"10\">Жовтень</option>\r\n" + 
				"    <option "+ (month == 11 ? "selected" : "") + " value=\"11\">Листопад</option>\r\n" + 
				"    <option "+ (month == 12 ? "selected" : "") + " value=\"12\">Грудень</option>\r\n" + 
				"   </select>";
		
		out.append(sel);
		
		out.append("<input type=\"number\" name=\"Year\" min=\"2000\" max=\""+ Calendar.getInstance().get(Calendar.YEAR) +"\" value='" + request.getParameter("Year") + "'>");
		
		out.append("<button type=\"button\" onclick=\"document.getElementById('" + request.getParameter("GroupID") + "').click()\">Відкрити</button>");
		
		if(Integer.parseInt((String)session.getAttribute("UserType")) == 3) {
			out.append("<input type='text' id='type' placeholder=\"Нова назва/тип\"></input>");
		}
		
		out.append("<button type=\"button\" onclick=\"document.getElementById('" + request.getParameter("GroupID") + "').click()\" style='float: right;'>Зберегти</button>");
		
		out.append("<div style='overflow: auto;'><table class='table table-sm table-primary' style='margin-bottom: 0;'>");
		
		try {
			if(rs != null) {
				int id = 0;
				
				out.append("<thead><tr><th>ПІБ</th>");
				
				SortedSet<Date> dates = new TreeSet<Date>();;
				
				while(rs.next()) {
					dates.add(rs.getDate(5));
				}
				
				for(Date date : dates) {
					out.append("<th>" + date.toString() + "</th>");
				}
				
				out.append("</thead></tr><tbody>");
				
				rs.beforeFirst();
				while(rs.next()) {
					
					//out.append("<tr class='bg-primary'>");
					
					if(id != rs.getInt(1)) {
						id = rs.getInt(1);
						out.append("</tr>"); //class='bg-primary'
						out.append("<tr><td>" + rs.getString(2) + " " + rs.getString(3).charAt(0) + ". " + rs.getString(4).charAt(0) + ".</td>");
					}
					
					out.append("<td><a><input type=\"checkbox\" name=\"option2\" value=\"a2\"/ " + (rs.getBoolean(6) ? "checked" : "") + "></a></td>");
					
				}
				
				out.append("</tbody>");
				
			} else {
				out.append("Жодного заняття в вашої дитини ще не відбулося!");
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		out.append("</table></div>");
		
		out.append("<div style='text-align: center;'><textarea placeholder='Коментар до поведінки' style='width: 90%;'></textarea></div>");
	    
		out.flush();
	}

}
