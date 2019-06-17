package managing;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import servlets.Authentication;
 
public class DataBase {
 
	public static boolean run = true;
	
	public static Connection conn;
	
	public static void main(String ar[]){  
		
		init();
		Date d = new Date(1,6,2019);
		for(int i = 0; i < 28; i++) {
			executeQuery("	INSERT INTO Відвідування_дитини_в_окремий_день (PK_ID, PK_Date, Is_present)\r\n" + 
							"VALUES (1000002, #06/"+ d.getDate() +"/2019#, TRUE);");
			d.setDate(d.getDate()+1);
		}
	}
	
	public static void init() {
		
		try {
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e1) {e1.printStackTrace();}
		
		try {
			conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Ghost/Client-Server/ChildrenGarden/src/managing/AIS.accdb");
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void executeQuery(String query) {
		try {
			if(conn == null) { init(); }
			
			Statement s = conn.createStatement();
			s.executeUpdate(query);
			/*ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
		    	System.out.println(rs.getString(1));
			}*/
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static ResultSet doQuery(String query) {
		try {
			if(conn == null) { init(); }
			
			Statement s = conn.createStatement();
			//s.executeUpdate(query);
			ResultSet rs = s.executeQuery(query);
			return rs;
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	
	
	private static PreparedStatement attendance;
	private static PreparedStatement password;
	private static PreparedStatement protectorID;
	private static PreparedStatement getKidsOfParent;
	
	public static String getPassword(int type, String phoneNumber, Connection conn) {
		try {
			//if(conn == null) {init();}
			
			/*if(password == null) {
				password = conn.prepareStatement(	"SELECT Password\r\n" + 
													"FROM Опікуни\r\n" + 
													"WHERE Phone_mob =?;");
			}
		
			password.setString(1, phoneNumber);*/
			
			String q = "SELECT Password FROM ";
			switch(type) {
			case 1: q += "Parent"; break;
			case 2: q += "Tutor"; break;
			case 3: q += "Administrator"; break;
			}
			q += " WHERE Phone = '" + phoneNumber + "'";
			
			ResultSet rs = conn.createStatement().executeQuery(q);
			rs.next();
			//System.out.println(rs.getString(1) + "YES HERE!");
			
			return rs.getString(1);
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static String getID(String phoneNumber, Connection conn) {
		try {
			if(conn == null) {init();}
			
			if(protectorID == null) {
				protectorID = conn.prepareStatement("SELECT Protector_ID\r\n" + 
													"FROM Опікуни\r\n" + 
													"WHERE Phone_mob =?;");
			}
		
			protectorID.setString(1, phoneNumber);
			
			ResultSet rs = protectorID.executeQuery();
			rs.next();
			//System.out.println(rs.getString(1) + "YES HERE!");
			
			return rs.getString(1);
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static void getAttendance(String surname, String name, String patronym) {
		try {
			if(conn == null) init();
			
			if(attendance == null) {
				attendance = conn.prepareStatement("SELECT Діти.FN_surname, Діти.FN_name, Діти.FN_patr, PK_Date, Is_present, Comment_Behaviour, Comment_Reason\r\n" + 
						"FROM Відвідування_дитини_в_окремий_день, Діти\r\n" + 
						"WHERE FN_surname =? AND FN_name =? AND FN_patr =?;");
			}
		
			attendance.setString(1, surname);
			attendance.setString(2, name);
			attendance.setString(3, patronym);
			
			ResultSet rs = attendance.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + (rs.getString(6) != null ? rs.getString(6) : rs.getString(7)));
			}
			
		} catch (SQLException e) {e.printStackTrace();}
		
	}
	
	public static ResultSet getKidsOfParents(int id, Connection conn) {
		try {
			if(conn == null) {init();}
			
			if(getKidsOfParent == null) {
				getKidsOfParent = conn.prepareStatement(	
						"SELECT Kid_ID, FN_Surname, FN_Name, FN_Patr\r\n" + 
						"FROM Діти\r\n" + 
						"WHERE Kid_ID in \r\n" + 
						"(\r\n" + 
						"SELECT PK_Kid_ID\r\n" + 
						"FROM [Опікує-Опікується]\r\n" + 
						"WHERE PK_Prot_ID =?\r\n" + 
						")");
			}
		
			getKidsOfParent.setInt(1, id);
			
			return getKidsOfParent.executeQuery();
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ResultSet getKidAttendance(int id, Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(
					"SELECT Is_present, PK_Date, Comment_Behaviour, Comment_Reason "
					+ "FROM [Відвідування_дитини_в_окремий_день] "
					+ "WHERE PK_ID =" + id + ";");
		} catch (SQLException e) {e.printStackTrace();}
		return rs;
	}
	
	public static ResultSet getGroupsOfTutor(int id, Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(	
						"SELECT Group_ID, Type\r\n" + 
						"FROM Групи\r\n" + 
						"WHERE Group_ID IN\r\n" + 
						"(\r\n" + 
						"SELECT PK_Group_ID\r\n" + 
						"FROM Викладає\r\n" + 
						"WHERE PK_Tutor_ID = " + id + "\r\n" + // AND Date_end = NULL
						");");
			
			return rs;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ResultSet getGroupsOfAdmin(Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(	
						"SELECT Group_ID, Type\r\n" + 
						"FROM Групи;");
			
			return rs;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ResultSet getParents(Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(	
						"SELECT Protector_ID, FN_surname, FN_name, FN_patr\r\n" + 
						"FROM Опікуни;");
			
			return rs;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ResultSet getTutors(Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(	
						"SELECT Tutor_ID, FN_surname, FN_name, FN_patr\r\n" + 
						"FROM Вихователі;");
			
			return rs;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ResultSet getKids(Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(	
						"SELECT Kid_ID, FN_surname, FN_name, FN_patr\r\n" + 
						"FROM Діти;");
			
			return rs;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ResultSet getKidsOfParent(int id,Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(	
						"SELECT Kid_ID\r\n" + 
						"FROM Діти\r\n" + 
						"WHERE Kid_ID in \r\n" + 
						"(\r\n" + 
						"SELECT PK_Kid_ID\r\n" + 
						"FROM [Опікує-Опікується]\r\n" + 
						"WHERE PK_Prot_ID = "+id+"\r\n" + 
						");");
			
			return rs;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ResultSet getParentInfo(int id, Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(	
						"SELECT FN_surname, FN_name, FN_patr, Work_comp, Work_pos, Phone_mob, Phone_work, Phone_add1, Phone_add2\r\n" + 
						"FROM Опікуни WHERE Protector_ID = " + id);
			
			return rs;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ResultSet getTutorInfo(int id, Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(	
						"SELECT FN_surname, FN_name, FN_patr, Phone_mob, Phone_add1, Phone_add2, Birth_date\r\n" + 
						"FROM Вихователі WHERE Tutor_ID = " + id);
			
			return rs;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ResultSet getGroupKid(int id, int month, int year, Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(
						"SELECT Kid_ID, FN_surname, FN_name, FN_patr, PK_Date, Is_present\r\n" + 
						"FROM Діти INNER JOIN Відвідування_дитини_в_окремий_день ON Діти.Kid_ID = Відвідування_дитини_в_окремий_день.PK_ID\r\n" + 
						"WHERE Kid_ID IN\r\n" + 
						"(\r\n" + 
						"SELECT PK_Kid_ID\r\n" + 
						"FROM Належать\r\n" + 
						"WHERE PK_Group_ID = " + id + "\r\n" + 
						(month < 9 ? 
						") AND PK_Date BETWEEN #0" + month + "/1/" + year + "# AND #0" + (month+1) + "/1/" + year + "#-1;":
						(month == 9 ? 
						") AND PK_Date BETWEEN #0" + month + "/1/" + year + "# AND #" + (month+1) + "/1/" + year + "#-1;":
						(month == 9 ? 
						") AND PK_Date BETWEEN #" + month + "/1/" + year + "# AND #" + (month+1) + "/1/" + year + "#-1;":
						(month == 12 ? 
						") AND PK_Date BETWEEN #" + month + "/1/" + year + "# AND #" + (month+1) + "/1/" + year+1 + "#-1;":
						
						") AND PK_Date BETWEEN #" + month + "/1/" + year + "# AND #" + (month+1) + "/1/" + year + "#-1;")))));
			
			return rs;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static void updateParent(int id, List<String> params, List<Integer> kidsAdd,  List<Integer> kidsDelete, Connection conn) {
		try {
			Statement s = conn.createStatement();
				//System.out.println(params);
			s.executeUpdate("UPDATE Опікуни "
					+ 		"SET FN_Surname=\""+params.get(0)+"\", FN_Name=\""+params.get(1)+"\", FN_Patr=\""+params.get(2)
					+		"\", Work_comp=\""+params.get(3)+"\", Work_pos=\""+params.get(4)+"\", Phone_mob=\""+params.get(5)
					+ 		"\", Phone_work=\""+params.get(6)+"\", Phone_add1=\""+params.get(7)+"\", Phone_add2=\""+params.get(8)
					+ 		"\" WHERE Protector_ID = " + id + ";");
			
			for(int kid : kidsAdd) {
				s.executeUpdate("	INSERT INTO [Опікує-опікується] (PK_Kid_ID, PK_Prot_ID)\r\n" + 
								"	VALUES ("+kid+", "+id+");");
			}
			for(int kid : kidsDelete) {
				s.executeUpdate("	DELETE FROM [Опікує-опікується] WHERE PK_Kid_ID = "+kid+" AND PK_Prot_ID = "+id+";");
			}
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void updateTutor(int id, List<String> params, List<Integer> kidsAdd,  List<Integer> kidsDelete, Connection conn) {
		try {
			Statement s = conn.createStatement();
				//System.out.println(params);
			s.executeUpdate("UPDATE Вихователі "
					+ 		"SET FN_Surname=\""+params.get(0)+"\", FN_Name=\""+params.get(1)+"\", FN_Patr=\""+params.get(2)
					+		"Phone_mob=\""+params.get(3)+"\", Phone_add1=\""+params.get(4)+"\", Phone_add2=\""+params.get(5)
					+ 		"\" Birth_date = #"+params.get(6)+"# WHERE Tutor_ID = " + id + ";");
			
			for(int kid : kidsAdd) {
				s.executeUpdate("	INSERT INTO [Опікує-опікується] (PK_Kid_ID, PK_Prot_ID)\r\n" + 
								"	VALUES ("+kid+", "+id+");");
			}
			for(int kid : kidsDelete) {
				s.executeUpdate("	DELETE FROM [Опікує-опікується] WHERE PK_Kid_ID = "+kid+" AND PK_Prot_ID = "+id+";");
			}
		} catch (SQLException e) {e.printStackTrace();}
	}
}
