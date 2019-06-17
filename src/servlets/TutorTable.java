package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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


@WebServlet("/TutorTable")
@MultipartConfig
public class TutorTable extends HttpServlet {
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
	
    public TutorTable() {
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
		
		ResultSet rs = DataBase.getTutorInfo(Integer.parseInt(request.getParameter("ParentID")), conn);
		
		PrintWriter out = response.getWriter();
		
		try {
			if(rs != null) {
				
				rs.next();
				
				out.append("<input value='Прізвище:' readonly></input><input type='text' id='surname' value='" + isNull(rs.getString(1)) + "'></input><br/>");
				out.append("<input value=\"Ім'я:\" readonly><input type='text' id='name' value='" + isNull(rs.getString(2)) + "'></input><br/>");
				out.append("<input value='По-батькові:' readonly><input type='text' id='patr' value='" + isNull(rs.getString(3)) + "'></input><hr/>");
				
				out.append("<input value='Мобільний телефон:' readonly><input type='text' id='tel' value='" + isNull(rs.getString(4)) + "'></input><br/>");
				out.append("<input value='Додатковий телефон:' readonly><input type='text' id='tel1' value='" + isNull(rs.getString(5)) + "'></input><br/>");
				out.append("<input value='Додатковий телефон:' readonly><input type='text' id='tel2' value='" + isNull(rs.getString(6)) + "'></input><hr/>");
				
				out.append("<input value='Дата народження:' readonly><input type='text' id='birthDate' value='" + rs.getDate(7) + "'></input><hr/>");
				
				out.append("<input value='Новий пароль:' readonly><input type='text' id='password'></input><hr/>");
				
				out.append("<input value='Групи:' readonly style='width: 370px;'><br/>");
				
				out.append("<select id='kidsList' style='width: 370px;' title=\"Список дітей\" multiple>"); //onload='event.target.selectpicker()' class=\"selectpicker\" data-live-search=\"true\" 
				
				ResultSet rs2 = DataBase.getKids(conn);
				ResultSet rs3 = DataBase.getKidsOfParent(Integer.parseInt(request.getParameter("ParentID")),conn);
				
				ArrayList<Integer> kids = new ArrayList<Integer>();
				
				while(rs3.next()) { kids.add(rs3.getInt(1));}
				
				while(rs2.next()) {
					out.append("<option " + (kids.contains(rs2.getInt(1)) ? "selected" : "") + " value='"+rs2.getInt(1)+"'>(" + rs2.getInt(1) + ") " + rs2.getString(2) + " " + rs2.getString(3) + " " + rs2.getString(4) + " " + "</option>");
				}
				
				out.append("</select><br/>");
				out.append("<button style=\"width: 370px;\" onclick='send(event)'>Зберегти</button>");
			} else {
				out.append("Сталася помилка!");
			}
		} catch (SQLException e) {e.printStackTrace();}

		out.flush();
	}
	
	private static String isNull(String str) {
		return str == null ? "" : str;
	}

}
