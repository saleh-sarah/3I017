package service;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import db.UserTools ;

public class UserServices {
	
	public static JSONObject login(String login, String password, String root) throws JSONException{
		if(login == null || password == null || root == null) {
			return ErrorJSON.serviceRefused("argument manquant", -1) ;
		}
		try {
			
			if(! UserTools.UserExistsFromLogin(login)) {
				return ErrorJSON.serviceRefused("Le login n'existe pas. Veuillez réessayer.", 1001) ;
			}
			if(!UserTools.checkedPassword(login, password)){
				return ErrorJSON.serviceRefused("Mot de passe, et/ou login incorrect(s) ", 1002) ;
			}
			if(UserTools.userAlreadyConnected(login)){
				UserTools.removeConnection(login) ;
			}
			String key = UserTools.getConnectionKey(login, root) ;
			JSONObject response = ErrorJSON.serviceAccepted() ;
			response.put("key", key) ;
			response.put("login", login) ;
			return response ;
		}catch(SQLException e){
			return ErrorJSON.serviceRefused(e.getMessage() , 1000) ;
		}catch(ClassNotFoundException e){
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}
		
		
	}
	
	public static JSONObject createUser(String login, String password, String nom, 
			String prenom, String sexe, String birthday) throws JSONException{
		if(login == null || password == null || nom == null | prenom == null || sexe == null 
				|| birthday == null) {
			return ErrorJSON.serviceRefused("Argument(s) manquant(s).", -1) ; 
		}
		try {
			if(UserTools.loginAlreadyExists(login)) {
				return ErrorJSON.serviceRefused("Le login existe déja.", 1003) ;
			}
			if(!UserTools.isPasswordSecured(password)) {
				return ErrorJSON.serviceRefused("Mot de passe trop simple", 1004);
			}
			if(!UserTools.insertUser(login, password, nom, prenom, sexe, birthday))
				return ErrorJSON.serviceRefused("Impossible d'insérer l'utilisateur dans la base de données", 1005) ;
			JSONObject monJSON = ErrorJSON.serviceAccepted()  ;
			monJSON.put("login", login) ;
			monJSON.put("password", password) ;
			return monJSON ;
		}
		catch(SQLException e){
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}catch(ClassNotFoundException e){
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}
		
	}
	
	public static JSONObject logout(String key) throws JSONException {
		if(key == null)
			return ErrorJSON.serviceRefused("Argument manquant", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			if(!UserTools.removeConnectionByKey(key))
				return ErrorJSON.serviceRefused("Impossible de vous déconnecter.", 1006) ;
			return ErrorJSON.serviceAccepted() ;
		}catch(SQLException e) {
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}catch( ClassNotFoundException  e) {
		return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}
	}
	
	
	public static JSONObject getUserInformations(String key, String login) throws JSONException{
		if(key == null || login == null){
			return ErrorJSON.serviceRefused("Argument(s) manquant(s)", -1) ;
		}try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			
			int id = UserTools.getID(login);
			JSONObject monJSON = UserTools.getInformation(id) ;
		return monJSON ;
		}catch(SQLException e) {
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}catch( ClassNotFoundException  e) {
		return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}
	}
	
	public static JSONObject isFollowerInformation(String key, String userID, String otherID) throws JSONException{
		if(key == null || userID == null){
			return ErrorJSON.serviceRefused("Argument(s) manquant(s)", -1) ;
		}try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			int myID= Integer.parseInt(userID);
			int id = Integer.parseInt(otherID) ;
			
			JSONObject monJSON = UserTools.isFollower(myID, id) ;
		return monJSON ;
		}catch(SQLException e) {
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}catch( ClassNotFoundException  e) {
		return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}
	}
	


}
