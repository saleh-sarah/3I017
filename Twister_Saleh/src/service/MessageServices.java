package service;

import java.net.UnknownHostException ;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List ;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import service.ErrorJSON;

import db.MessageTools;
import db.UserTools ;


public class MessageServices {
	
	public static JSONObject addPost(String message, String key) throws JSONException {
		if(message == null || key == null)
			return ErrorJSON.serviceRefused("Argument(s) manquant(s)", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			int id = UserTools.getIDByKey(key);
			MessageTools.insertPost(id, message);
			JSONObject monJSON = MessageTools.insertPost(id, message);;
			monJSON.put("message_added", message) ;
			monJSON.put("status", "OK");
			return monJSON ;
		}catch(SQLException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}catch(ClassNotFoundException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}catch(UnknownHostException e){
			return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}

	}

	
	public static JSONObject deletePost(String messageID, String key) throws JSONException {
		if(messageID == null || key == null)
			return ErrorJSON.serviceRefused("Argument(s) manquant(s)", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			
			if(! MessageTools.PostExists(messageID))
				return ErrorJSON.serviceRefused("Le post n'existe pas.", 1009) ;
			int authorID = UserTools.getIDByKey(key) ;
			if( ! MessageTools.isItMyPost(authorID, messageID))
				return ErrorJSON.serviceRefused("Le post ne vous appartient pas.", 1010) ;
			MessageTools.deletePost(messageID);
			return ErrorJSON.serviceAccepted() ;
		}catch(SQLException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}catch(ClassNotFoundException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}catch(UnknownHostException e){
			return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}

	}
	
	public static JSONObject addComment(String messageID, String key, String comment) throws JSONException {
		if(messageID == null || key == null|| comment == null)
			return ErrorJSON.serviceRefused("Argument(s) manquant(s)", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			
			if(! MessageTools.PostExists(messageID))
				return ErrorJSON.serviceRefused("Le post n'existe pas.", 1009) ;
			int commentatorID = UserTools.getIDByKey(key) ;
			MessageTools.insertComment(commentatorID, messageID, comment);
			JSONObject monJSON =  MessageTools.insertComment(commentatorID, messageID, comment);
			monJSON.put("comment_added", comment) ;
			monJSON.put("status", "OK");
			return monJSON ;
		}catch(SQLException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}catch(ClassNotFoundException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}catch(UnknownHostException e){
			return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}

	}
	
	
	public static JSONObject ListPosts(String key, String id, String friends, String nb, String message) throws JSONException, UnknownHostException {
		if(id == null || key == null)
			return ErrorJSON.serviceRefused("Argument(s) manquant(s)", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;

			if(id != null && friends != null) {
				int userID = Integer.parseInt(id) ;
				String login = UserTools.getLoginByID(userID);
				if( ! UserTools.UserExistsFromLogin(login))
					return ErrorJSON.serviceRefused("Utilisateur non existant", 1002) ;
				int number = Integer.parseInt(nb) ;
				JSONArray listMessages = MessageTools.Newsfeed(userID, number) ;
				JSONObject monJSON = ErrorJSON.serviceAccepted() ;
				monJSON.put("posts", listMessages) ;
				return monJSON ;	
			}
			if(friends == null && id != null) {
				int userID = Integer.parseInt(id) ;
				String login = UserTools.getLoginByID(userID);
				if( ! UserTools.UserExistsFromLogin(login))
					return ErrorJSON.serviceRefused("Utilisateur non existant", 1002) ;
				int number = Integer.parseInt(nb) ;
				JSONArray listMessages = MessageTools.listMessages(userID, number) ;
				JSONObject monJSON = ErrorJSON.serviceAccepted();
				monJSON.put("posts", listMessages) ;
				return monJSON ;
			}
			if(message != null && id != null && friends == null) {
				JSONArray messages = MessageTools.listTextMessages(message,-1,5);
				JSONObject monJSON = ErrorJSON.serviceAccepted() ;
				monJSON.put("messages",messages);
				return monJSON;
			}
			
			return ErrorJSON.serviceRefused("Probleme de paramètres", -1) ;
		}catch(SQLException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}catch(ClassNotFoundException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}

	}
	
	public static JSONObject ListAllMessages(String key) throws JSONException, UnknownHostException {
		if(key == null)
			return ErrorJSON.serviceRefused("Argument(s) manquant(s)", -1) ;
		try {
			if(!UserTools.isUserConnected(key))
				return ErrorJSON.serviceRefused("Vous n'êtes pas connecté.", 1005) ;
			JSONArray listMessages = MessageTools.listAllMessages();
			JSONObject monJSON = ErrorJSON.serviceAccepted();
			monJSON.put("messages", listMessages);
			return monJSON ;
		}catch(SQLException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}catch(ClassNotFoundException e){
		 	return ErrorJSON.serviceRefused(e.getMessage(), 1000);
		}

	}
	
}
