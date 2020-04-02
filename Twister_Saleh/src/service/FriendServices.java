package service;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.UserTools ;
import db.FriendTools ;

public class FriendServices {
	
	public static JSONObject addFriend(String key, String id) throws JSONException{
		if(key == null || id == null)
			return ErrorJSON.serviceRefused("argument(s) manquant(s)", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			int ID = Integer.parseInt(id) ;
			String login = UserTools.getLoginByID(ID) ;
			if(!UserTools.UserExistsFromLogin(login))
				return ErrorJSON.serviceRefused("login incorrect ", 1002) ;
			int myID = UserTools.getIDByKey(key) ;
			if(FriendTools.areYouAlreadyFriend(myID, ID))
				return ErrorJSON.serviceRefused("Impossible d'ajouter le following, vous êtes déja ami !", 1011) ;
			if(!FriendTools.insertFriend(myID, ID))
				return ErrorJSON.serviceRefused("Impossible d'ajouter le following", 1007) ;
			JSONObject monJSON = ErrorJSON.serviceAccepted() ;
			monJSON.put("following_added ", ID) ;
			return monJSON;		
		}catch(SQLException e) {
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}catch( ClassNotFoundException  e) {
		return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}
	}
	
	public static JSONObject deleteFriend(String key, String idFriend) throws JSONException{
		if(key == null || idFriend ==  null)
			return ErrorJSON.serviceRefused("argument(s) manquant(s)", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			int id = Integer.parseInt(idFriend) ;
			String login = UserTools.getLoginByID(id) ;
			if(!UserTools.UserExistsFromLogin(login))
				return ErrorJSON.serviceRefused("login incorrect ", 1002) ;
			int myID = UserTools.getIDByKey(key) ; 
			if(!FriendTools.areYouAlreadyFriend(myID, id))
				return ErrorJSON.serviceRefused("Impossible de supprimer l'abonnement puisque vous ne suivez pas l'utilisateur", 1008) ;
			if(!FriendTools.deleteFriend(myID, id))
				return ErrorJSON.serviceRefused("Impossible de supprimer l'abonnement ", 1009) ;
			JSONObject monJSON = ErrorJSON.serviceAccepted();
			monJSON.put("following_deleted", id) ;
			return monJSON ;			
		}
		catch(SQLException e) {
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}catch( ClassNotFoundException  e) {
		return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}
	}
	
	public static JSONObject ListFollowers(String key, String id) throws JSONException{
		if(key == null)
			return ErrorJSON.serviceRefused("argument manquant", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			int userID = Integer.parseInt(id) ;
			String login = UserTools.getLoginByID(userID);
			if(!UserTools.UserExistsFromLogin(login))
				return ErrorJSON.serviceRefused("login incorrect ", 1002) ;
			JSONArray listFollowers = FriendTools.listFollowers(userID) ;
			JSONObject monJSON = ErrorJSON.serviceAccepted() ;
			monJSON.put("list_followers", listFollowers) ;
			return monJSON ;			
		}
		catch(SQLException e) {
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}catch( ClassNotFoundException  e) {
		return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}		
	}
	
	public static JSONObject ListFollowings(String key, String id) throws JSONException{
		if(key == null)
			return ErrorJSON.serviceRefused("argument manquant", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			int userID = Integer.parseInt(id) ;
			String login = UserTools.getLoginByID(userID);
			if(!UserTools.UserExistsFromLogin(login))
				return ErrorJSON.serviceRefused("login incorrect ", 1002) ;
			JSONArray listFollowings = FriendTools.listFollowings(userID) ;
			JSONObject monJSON = ErrorJSON.serviceAccepted() ;
			monJSON.put("list_followings", listFollowings) ;
			return monJSON ;			
		}
		catch(SQLException e) {
			return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}catch( ClassNotFoundException  e) {
		return ErrorJSON.serviceRefused(e.getMessage(), 1000) ;
		}		
	}

}
