package service;


import org.json.JSONException;
import org.json.JSONObject;


public class ErrorJSON {
	
	
	public static JSONObject serviceRefused(String message, int codeError) throws JSONException {
		
		
		JSONObject err = new JSONObject();
		err.put("status", "KO") ;
		err.put("error_message", message) ;
		err.put("errorID", codeError) ;
		
		return err; 
		
	}
	
	public static JSONObject serviceAccepted() throws JSONException {
		
		JSONObject ok = new JSONObject();
		ok.put("status", "OK") ;
		return ok ;
	}
	
	
	

}
