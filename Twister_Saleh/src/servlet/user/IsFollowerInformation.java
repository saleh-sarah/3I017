package servlet.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import org.json.JSONException;
import org.json.JSONObject;

import db.UserTools;
import service.UserServices ;

public class IsFollowerInformation extends HttpServlet {

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject monJSON ;
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		String key = request.getParameter("key");
		String userID = request.getParameter("userID") ;
		String otherID = request.getParameter("otherID") ;
		try {
			monJSON = UserServices.isFollowerInformation(key, userID, otherID) ;
			out.println(monJSON) ;
		}catch(JSONException e){
			out.println("JSON ERROR") ;
		}

	}

}
