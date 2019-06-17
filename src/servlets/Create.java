package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managing.DataBase;


@WebServlet("/Create")
@MultipartConfig
public class Create extends HttpServlet {
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
	
    public Create() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		switch(request.getParameter("Entity")) {
			case "Parent" : DataBase.executeQuery("INSERT INTO [������](Protector_ID, FN_Surname, FN_Name, Phone_mob) VALUES(" + Integer.parseInt(request.getParameter("ID"))+", '�����', '�����', 'XXX XXX XX XX')"); break;
			case "Kid" : {DataBase.executeQuery("INSERT INTO [ĳ��](Kid_ID, FN_Surname, FN_Name, Birth_date, Adr_city, Adr_street) VALUES("+Integer.parseInt(request.getParameter("ID"))+", \"����\", \"������\", #01/01/2008#, \"̳���\", \"������\")"); 
				DataBase.executeQuery("INSERT INTO [���������]() VALUES()");
				
				ResultSet rs = DataBase.doQuery("SELECT COUNT(Receipt_ID) FROM ���������");
				
				int year = Calendar.getInstance().get(Calendar.YEAR);
				int month = Calendar.getInstance().get(Calendar.YEAR);
				
				YearMonth yearMonthObject = YearMonth.of(year, month);
				int daysInMonth = yearMonthObject.lengthOfMonth(); 
				
				try {
					for(int day = 1; day <= daysInMonth; day++) {
						DataBase.executeQuery("INSERT INTO [³���������_������_�_�������_����](PK_ID, PK_Date, Receipt_ID) VALUES("+Integer.parseInt(request.getParameter("ID"))+", #"+month+"/"+day+"/"+year+"#, " +rs.getInt(1)+")");
					}
				} catch (SQLException e) {e.printStackTrace();}
				
				break;				
			}
			case "Tutor" : DataBase.executeQuery("INSERT INTO [���������](Protector_ID, FN_Surname, FN_Name, Phone_mob) VALUES(" + Integer.parseInt(request.getParameter("ID"))+"'�����', '��������', 'XXX XXX XX XX'"); break;
			case "Group" : DataBase.executeQuery("INSERT INTO [�����](Group_ID, Type) VALUES("+Integer.parseInt(request.getParameter("ID"))+", \"���� �����\")"); break;
		}
	}

}
