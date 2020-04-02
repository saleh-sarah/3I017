package db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import service.ErrorJSON;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FriendTools {
	
	public static boolean insertFriend(int myID, int id) throws SQLException, ClassNotFoundException{
		boolean res ;
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		String update = "INSERT INTO friend (userID, followerID) VALUES (" + id + "," +  myID + ")" ;
		int r = s.executeUpdate(update) ;
		res = (r!=0) ;
		s.close() ;
		c.close();
		return res ;
		
	}
	
	public static boolean areYouAlreadyFriend(int myID, int id)throws SQLException, ClassNotFoundException {
		boolean res ;
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		String query = "SELECT * FROM friend WHERE userID=" + id + " AND followerID=" + myID ;
		ResultSet rs = s.executeQuery(query) ;
		res = rs.next();
		rs.close();
		s.close();
		c.close();
		return res;
	}
	
	public static boolean deleteFriend(int myID, int id)throws SQLException, ClassNotFoundException{
		boolean res ;
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		String update = "DELETE FROM friend where userID="+ id + " AND followerID=" + myID ;
		int r = s.executeUpdate(update) ;
		res = (r!=0) ;
		s.close();
		c.close();
		return res  ;
		
	}
	
	public static List<Integer> listFollowingsID(int id) throws SQLException, ClassNotFoundException{
		//Class.forName("com.mysql.jdbc.Driver");
		List<Integer> listId = new ArrayList<Integer>();
		Connection c = db.Database.getMySQLConnection();
		Statement st = c.createStatement();
		String query = "SELECT userID FROM friend WHERE followerID=" + id;
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			listId.add(rs.getInt(1));
		}
		rs.close();
		st.close();
		c.close();
		return listId;

	}
	
	public static List<Integer> listFollowersID(int id) throws SQLException, ClassNotFoundException{
		//Class.forName("com.mysql.jdbc.Driver");
		List<Integer> listId = new ArrayList<Integer>();
		Connection c = db.Database.getMySQLConnection();
		Statement st = c.createStatement();
		String query = "SELECT followerID FROM friend WHERE userID=" + id;
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			listId.add(rs.getInt(1));
		}
		rs.close();
		st.close();
		c.close();
		return listId;

	}
	
	public static JSONArray getLoginsFromListID(List<Integer> listId)throws SQLException,ClassNotFoundException,JSONException{
		//Class.forName("com.mysql.jdbc.Driver");
		JSONArray res = new JSONArray();
		Connection c = db.Database.getMySQLConnection();
		Statement st = c.createStatement();
		String query;
		ResultSet rs;
		for(int i=0;i<listId.size();i++){
			query = "SELECT login FROM user WHERE id=" + listId.get(i);
			rs = st.executeQuery(query);
			rs.next();
			JSONObject monJSON = new JSONObject();
			monJSON.put("login",rs.getString(1));
			monJSON.put("id",listId.get(i));
			res.put(monJSON);
			rs.close();
		}
		st.close();
		c.close();
		return res;
	}
	
	
	public static JSONArray listFollowings(int id)throws SQLException,ClassNotFoundException,JSONException{
		//Class.forName("com.mysql.jdbc.Driver");
		List<Integer> listId = listFollowingsID(id);
		JSONArray res = getLoginsFromListID(listId);
		return res;
	}
	
	public static JSONArray listFollowers(int id)throws SQLException,ClassNotFoundException,JSONException{
		//Class.forName("com.mysql.jdbc.Driver");
		List<Integer> listId = listFollowersID(id);
		JSONArray res = getLoginsFromListID(listId);
		return res;
	}

}
