package servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import service.UserServices;


public class Logout extends HttpServlet {

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		JSONObject monJSON ;
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		String key = request.getParameter("key");
		try {
			monJSON = UserServices.logout(key) ;
			out.println(monJSON) ;
		}catch(JSONException e){
			out.println("JSON ERROR") ;
		}
	

	}

}
