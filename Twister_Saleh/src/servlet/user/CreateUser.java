package servlet.user;
import service.UserServices ;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;


public class CreateUser extends HttpServlet {

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		
		JSONObject monJSON ;
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		String login = request.getParameter("login");
		String password = request.getParameter("password") ;
		String sexe = request.getParameter("sexe");
		String date = request.getParameter("date") ;
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom") ;
		
		try {
			monJSON = UserServices.createUser(login, password, nom, prenom, sexe, date) ;
			out.println(monJSON) ;
		}catch(JSONException e){
			out.println("JSON ERROR") ;
		}
		
		
		
	}

}
