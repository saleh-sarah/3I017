package servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import service.UserServices;

//import services.UserServices;

public class Login extends HttpServlet{
	
	protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException{
		
		
		JSONObject monJSON ;
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		String login = request.getParameter("login" ) ;
		String password = request.getParameter("password") ;
		String root = request.getParameter("root") ;
		try {
			monJSON = UserServices.login(login, password, root) ;
			out.println(monJSON) ;
		}catch(JSONException e) {
			out.println("JSON ERROR") ;
		}
	}

}
