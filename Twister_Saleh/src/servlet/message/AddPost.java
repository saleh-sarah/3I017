package servlet.message;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import service.MessageServices; ;


public class AddPost extends HttpServlet {

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		JSONObject monJSON ;
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		String key = request.getParameter("key");
		String message = request.getParameter("message") ;
		try {
			monJSON = MessageServices.addPost(message, key) ;
			out.println(monJSON) ;
		}catch(JSONException e){
			out.println("JSON ERROR") ;
		}
		
		
		
	}

}