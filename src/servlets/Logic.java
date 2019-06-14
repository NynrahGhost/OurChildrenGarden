package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logic")
public class Logic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Logic() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();

        String userID = (String) session.getAttribute("UserID");
        
        PrintWriter out = response.getWriter();
        // если объект ранее не установлен
        if(userID == null) {
            // устанавливаем объект с ключом name
            //session.setAttribute("name", "Tom Soyer");
            response.sendRedirect("index.jsp");
            out.println("Session data are set");
        }
        else {
            out.println("Name: " + userID);
            // удаляем объект с ключом name
            session.removeAttribute("UserID");
        }

        out.close();
        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}


