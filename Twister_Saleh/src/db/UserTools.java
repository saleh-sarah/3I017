package db;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import service.ErrorJSON;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class UserTools {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static int DUREE = 6000 ;
	
	
	public static String generateKey() {
		int count = 32 ;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
		int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
		builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}


	public static boolean loginAlreadyExists(String login) throws SQLException, ClassNotFoundException{
		boolean res ;
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		String query = "SELECT * FROM user WHERE login = \"" + login +"\"" ;
		ResultSet rs = s.executeQuery(query) ;
		res = rs.next();
		rs.close();
		s.close() ;
		c.close(); 
		return res;
	}
	
	public static boolean checkedPassword(String login, String password) throws SQLException, ClassNotFoundException{
		
		boolean res ;
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		String query = "SELECT password FROM user WHERE login = \"" + login + "\" AND password = \""  + password  + "\"";
		ResultSet rs = s.executeQuery(query) ;
		res = rs.next() ;
		rs.close();
		s.close() ;
		c.close() ;
		return res ;
		
	}
	
	public static boolean userAlreadyConnected(String login) throws SQLException, ClassNotFoundException{
		boolean res ;
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		int id = getID(login) ;
		String query = "SELECT * FROM session WHERE userID =" + id  ;
		ResultSet rs = s.executeQuery(query) ;
		res = rs.next() ;
		rs.close() ;
		s.close();
		c.close() ;
		return res ;

	}
	
	public static int getID(String login) throws SQLException, ClassNotFoundException{
		
		int res ;
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		String query = "SELECT id FROM user WHERE login = \"" + login + "\"" ;
		ResultSet rs = s.executeQuery(query) ;
		rs.next() ;
		res = rs.getInt(1) ;
		rs.close();
		s.close();
		c.close();
		return res ;

	}
	
	public static void removeConnection(String login)throws SQLException, ClassNotFoundException {
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		int id_user = getID(login) ;
		String update = "DELETE FROM session WHERE userID =" + id_user  ;
		int res = s.executeUpdate(update) ;
		s.close();
		c.close();
	
	}
	
	public static String getConnectionKey(String login, String root) throws SQLException, ClassNotFoundException{
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		ResultSet rs ;
		int id = getID(login) ;
		
		//Création de la clé et verifie si elle n'existe pas déja.
		
		String key, query ;
		do {
			key = generateKey() ;
			query = "SELECT * from session where s_key=\"" + key +"\"" ;
			rs = s.executeQuery(query) ;			
		}while (rs.next()) ;
		
		if(root.equals("false")) {
			long now = new Date().getTime() ;
			String q = "(\"" + key +"\"," + id + "," +now + ",0)" ;
			
 			 query = "INSERT INTO session (s_key, userID, s_date, root) VALUES " + q;
 			 
		}else {
			long now = new Date().getTime() ;
			query = "INSERT INTO session (s_key, userID, s_date, root) VALUES (\"" + key +"\"," + id + "," + now + ",1)" ;
		}
		s.executeUpdate(query) ;
		rs.close();
		s.close();
		c.close();
		return key ;
		
	}
	
	public static boolean UserExistsFromLogin(String login) throws SQLException , ClassNotFoundException{
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		ResultSet rs ;
		boolean res ;
		String query = "SELECT * from user where login=\"" + login + "\"" ;
		rs = s.executeQuery(query) ;
		res = rs.next() ;
		rs.close();
		s.close() ;
		c.close();
		return res ;
	}
	
	public static boolean isPasswordSecured(String password) {
		boolean s1 = false  ;
		boolean s2 = false  ;
		if(password.length() < 10)
			return false ;
		String[] t = password.split("") ;
		for(int i = 0 ; i < password.length() ; i++) {
			if(t[i].contains("-") || t[i].contains("_") || t[i].contains(".") || t[i].contains("!") )
				s1=true ;
			if (t[i].matches("-?\\d+(\\.\\d+)?"))
				s2 = true ;
		}

		return (s1 && s2) ;
	}
	
	public static boolean insertUser(String login, String password, String nom, String prenom, String sexe, String birthday ) throws SQLException, ClassNotFoundException{
		boolean res ;
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		String update = "INSERT INTO user (login, password, nom, prenom, sexe, birthday) VALUES (\""  + login +"\",\"" + password + "\",\"" + nom +
				"\",\"" + prenom + "\",\"" + sexe + "\",\"" + birthday + "\")";
		int n = s.executeUpdate(update) ;
		res = (n != 0) ;
		s.close(); 
		c.close();
		return res ;
	}
	
	public static boolean isUserConnected(String key) throws SQLException, ClassNotFoundException{

		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		ResultSet rs ;
		String query = "SELECT s_date, root FROM session WHERE s_key=\"" + key + "\"";
		rs = s.executeQuery(query) ;
		
		boolean res = rs.next();
		if(!res) {
			rs.close();
			s.close() ;
			c.close() ;
			return false ;
 		}
	
		
		// On teste si la cle a expire
		long before =  rs.getLong(1);
		Boolean root = rs.getBoolean(2) ;
		Date now = new Date() ;
		int r = (int) (before - now.getTime()) ; 
		if( r > DUREE && (!root)) {
			rs.close() ;
			s.close();
			c.close();
			if(!removeConnectionByKey(key))
				throw new SQLException() ;
			return false ;	
		}
		Date d = new Date() ;
		String update = "UPDATE session SET s_date=" + d.getTime() + " WHERE s_key =\"" + key + "\"" ;
		int nb = s.executeUpdate(update) ;
		s.close();
		c.close();
		return (nb != 0) ;
		
		
	}
	
	public static int getIDByKey(String key) throws SQLException, ClassNotFoundException{
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		ResultSet rs ;
		
		String query = "SELECT userID FROM session where s_key=\"" + key + "\"" ;
		
		rs = s.executeQuery(query) ;
		
		rs.next() ;
		int r = rs.getInt(1) ;
		rs.close();
		s.close(); c.close() ;
		return r ;
		
	}
	
	public static String getLoginByID(int id) throws SQLException,ClassNotFoundException{
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		ResultSet rs ;
		String query = "SELECT login FROM user where id=" + id  ;
		rs = s.executeQuery(query) ;
		rs.next() ;
		String r = rs.getString(1) ;
		rs.close() ;
		s.close() ;
		c.close();
		return r ;
		
	
	}	
	
	public static boolean removeConnectionByKey(String key)throws SQLException, ClassNotFoundException {
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		int id = getIDByKey(key) ;
		String update = "DELETE FROM session WHERE userID =" + id   ;
		int res = s.executeUpdate(update) ;
		s.close();
		c.close();
		boolean r = (res != 0) ; 
		return r ;
	
	}
	//Permet de récupérerle nom et le prenom d'un utilisateur ainsi que le nombre de followers, de following
	public static JSONObject getInformation(int id) throws SQLException, ClassNotFoundException, JSONException{
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		ResultSet rs ;
		JSONObject monJSON = ErrorJSON.serviceAccepted();
		String query = "SELECT nom, prenom FROM user WHERE id="+ id ; 
		rs = s.executeQuery(query) ;
		rs.next();
		monJSON.put("nom", rs.getString(1)) ;
		monJSON.put("prenom", rs.getString(2)) ;
		
		
		query = "SELECT count(*) FROM friend WHERE userID="+ id ;
		rs = s.executeQuery(query) ;
		rs.next();
		monJSON.put("nombre de followers", rs.getInt(1)) ;
		
		query =  "SELECT count(*) FROM friend WHERE followerID="+ id ;
		rs = s.executeQuery(query) ;
		rs.next();
		monJSON.put("nombre de following", rs.getInt(1)) ;
		monJSON.put("u_id", id);

		
		rs.close();
		s.close();
		c.close();
		return monJSON ;
	}
	
	
	//Permet de savoir si id suit myID
	public static JSONObject isFollower(int myID, int id) throws SQLException, ClassNotFoundException, JSONException{
		//Class.forName("com.mysql.jdbc.driver");
		Connection c = Database.getMySQLConnection();
		Statement s = c.createStatement() ;
		ResultSet rs ;
		JSONObject monJSON = ErrorJSON.serviceAccepted();
		String query =  "SELECT * FROM friend WHERE userID="+ myID + " AND followerID =" + id  ;
		rs = s.executeQuery(query);
		
		monJSON.put("is_follower", rs.next()) ;
		rs.close();
		s.close();
		c.close();
		return monJSON;
		
	}
	// Rend la liste des logins à partir d'une liste d'id
	
	
	public static List<String> getLoginsByIDS(List<Integer> l)throws SQLException,ClassNotFoundException{
		//Class.forName("com.mysql.jdbc.Driver");
		List<String> res = new ArrayList<String>();
		Connection c = db.Database.getMySQLConnection();
		Statement s = c.createStatement();
		for(int i=0;i<l.size();i++){
			String query = "SELECT login FROM user WHERE id=" + l.get(i);
			ResultSet rs = s.executeQuery(query);
			rs.next();
			res.add(rs.getString(1));
			rs.close();
		}
		s.close();
		c.close();
		return res;
	}


}
