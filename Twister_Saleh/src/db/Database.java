package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException ; 

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClients;
import com.mongodb.*;


import java.net.UnknownHostException ;



public class Database {
	
	private DataSource dataSource;
	private static Database database ;

	
	public Database(String jndiname) throws SQLException{
		
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/"+jndiname) ;
		}catch(NamingException e) {
			throw new SQLException(jndiname + " is missing in JNDI! : " +e.getMessage()) ;
		}
	}

	public Connection getConnection() throws SQLException{
		
		return dataSource.getConnection() ;
	}
	
	public static Connection getMySQLConnection() throws SQLException{
		
		if(DBStatic.mysql_pooling == false) {
			return (DriverManager.getConnection("jdbc:mysql://" + DBStatic.mysql_host + "/" 
			+ DBStatic.mysql_db, DBStatic.mysql_username, DBStatic.mysql_password)) ;
		}else {
			if(database == null){
				database = new Database("jdbc/db") ;
			}
			return (database.getConnection()) ;
			
		}
	}
		
	public static  MongoCollection<Document> getMongoCollection (String c) throws UnknownHostException{
			MongoClient m = MongoClients.create("mongodb://"+ DBStatic.mongo_url);
			MongoDatabase db = m.getDatabase(DBStatic.mongo_db) ;
			MongoCollection<Document> mc = db.getCollection(c) ;
			return mc ;
			
		}
	
    public static DBCollection getMongoCollection2(String nom) throws UnknownHostException{
    	Mongo m = new Mongo(DBStatic.mongo_url);
    	DB db = m.getDB(DBStatic.mongo_db);
    	return db.getCollection(nom);
    }
		
		
}
	
