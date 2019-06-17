package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managing.DataBase;


@WebServlet("/KidsInfo")
@MultipartConfig
public class KidsInfo extends HttpServlet {
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
	
    public KidsInfo() {
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
		
		PrintWriter out = response.getWriter();
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(	
						"SELECT FN_surname, FN_name, FN_patr, Birth_date, Adr_postal, Adr_region, Adr_city, Adr_district, Adr_street\r\n" + 
						"FROM Діти WHERE Kid_ID = " + Integer.parseInt(request.getParameter("KidID")));
			
			if(rs != null) {
				
				rs.next();
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = format.parse ( rs.getDate(4).toString() );
				} catch (ParseException e) {e.printStackTrace();}    
				
				SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
				String dateString = format2.format( date );
				
				out.append("<input value='Прізвище:' readonly></input><input type='text' id='surname' value='" + isNull(rs.getString(1)) + "'></input><br/>");
				out.append("<input value=\"Ім'я:\" readonly><input type='text' id='name' value='" + isNull(rs.getString(2)) + "'></input><br/>");
				out.append("<input value='По-батькові:' readonly><input type='text' id='patr' value='" + isNull(rs.getString(3)) + "'></input><hr/>");
				
				out.append("<input value='Дата народження:' readonly><input type='text' id='birthDate' value='" + isNull(dateString) + "'></input><hr/>");
				
				out.append("<input value='Поштова адреса:' readonly><input type='text' id='postal' value='" + isNull(rs.getString(5)) + "'></input><br/>");
				out.append("<input value='Область:' readonly><input type='text' id='region' value='" + isNull(rs.getString(6)) + "'></input><br/>");
				out.append("<input value='Місто:' readonly><input type='text' id='city' value='" + isNull(rs.getString(7)) + "'></input><br/>");
				out.append("<input value='Район:' readonly><input type='text' id='district' value='" + isNull(rs.getString(8)) + "'></input><br/>");
				out.append("<input value='Вулиця:' readonly><input type='text' id='street' value='" + isNull(rs.getString(9)) + "'></input><hr/>");

				
				ResultSet rsReturn = st.executeQuery("SELECT Квитанції.Benefit_ID, Квитанції.Meal_ID, [Рядки чеку].PK_Service\r\n" + 
						"FROM (Квитанції INNER JOIN [Рядки чеку] ON Квитанції.Receipt_ID = [Рядки чеку].PK_Receipt)\r\n" + 
						"WHERE (((Квитанції.Receipt_ID) In (SELECT DISTINCT Receipt_ID\r\n" + 
						"FROM Відвідування_дитини_в_окремий_день\r\n" + 
						"WHERE PK_ID = 1000002 AND Month(PK_Date) = Month(Date())\r\n" + 
						")));\r\n" + 
						"");
				
				out.append("<input value='Пільги:' readonly></input><select class='sel'>");
				
				int benefit = 0;
				int meal = 0;
				if(rsReturn.next()) {
					benefit = rsReturn.getInt(1);
					meal = rsReturn.getInt(2);
				}	
				
				ArrayList<Integer> services = new ArrayList<Integer>();
				services.add(rsReturn.getInt(3));
				while(rsReturn.next()) { services.add(rsReturn.getInt(3));}
				
				rs = st.executeQuery("SELECT * FROM Пільги");
				while(rs.next()) {
					out.append("<option " + (benefit == rs.getInt(1) ? "selected" : "") + " value = '"+rs.getInt(1)+"'>(" + rs.getInt(1) + ") " + rs.getString(2));
				}
				
				out.append("</select><br/>");
				out.append("<input value='Харчування:' readonly></input><select class='sel'>");
				
				rs = st.executeQuery("SELECT * FROM Харчування");
				while(rs.next()) {
					out.append("<option " + (meal == rs.getInt(1) ? "selected" : "") + " value = '"+rs.getInt(1)+"'>(" + rs.getInt(1) + ") " + rs.getString(2));
				}
				
				out.append("</select><br/>");
				out.append("<input value='Додаткові послуги:' readonly></input><select class='sel'>");
				
				rs = st.executeQuery("SELECT * FROM [Додаткові послуги]");
				while(rs.next()) {
					out.append("<option " + (services.contains(rs.getInt(1)) ? "selected" : "") + " value = '"+rs.getInt(1)+"'>(" + rs.getInt(1) + ") " + rs.getString(2));
				}
				
				out.append("</select><br/>");
				out.append("<input value='Група:' style='width: 370px;' readonly></input><br/>");
				out.append("<select id='groupsList' style='width: 370px;' title=\"Список груп\">");
				
				rs = st.executeQuery(	
						"SELECT PK_Group_ID\r\n" + 
						"FROM Належать WHERE PK_Kid_ID = " + Integer.parseInt(request.getParameter("KidID")) + " AND Date_leave=NULL");
				int id = 0;
				if(rs.next()) {
					id = rs.getInt(1);
				}
				
				rs = st.executeQuery("SELECT Group_ID, Type FROM Групи");
				
				while(rs.next()) {
					out.append("<option " + (id == rs.getInt(1) ? "selected" : "") + " value = '"+rs.getInt(1)+"'>(" + rs.getInt(1) + ") " + rs.getString(2));
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
