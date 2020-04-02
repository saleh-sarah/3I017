package db;

import java.lang.reflect.Array;
import java.net.UnknownHostException ;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List ;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tomcat.util.json.JSONParser;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import java.lang.Object;
import org.bson.types.ObjectId;

import service.ErrorJSON;
import service.UserServices;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageTools {
	
	
	public static JSONObject insertPost(int id,String message) throws UnknownHostException, ClassNotFoundException, SQLException, JSONException{
		MongoCollection<Document> mc = Database.getMongoCollection("message") ;
		Document d = new Document() ;
		Document auteur = new Document() ;
		String login = UserTools.getLoginByID(id);
		auteur.append("authorID", id) ;
		auteur.append("login", login) ;
		d.append("author", auteur) ;
		d.append("message", message) ;
		GregorianCalendar date = new GregorianCalendar() ;
		d.append("date", date.getTime()) ;
		d.append("like", new Document()) ;
		d.append("comments", new ArrayList<Document>())  ;
		mc.insertOne(d);
		
		JSONObject json = new JSONObject();
		json.put("_id", d.get("_id"));
		json.put("message",d.get("message"));
		json.put("author",d.get("author"));
		json.put("date",d.get("date"));
		json.put("comments",d.get("comments"));
		json.put("like",d.get("like"));
		return json ;
		
		
	}
	
	
	public static void deletePost(String messageID) throws UnknownHostException {
		MongoCollection<Document> mc = Database.getMongoCollection("message") ;
		Document d = new Document() ;
		d.append("_id", new ObjectId(messageID)) ;
		mc.deleteOne(d);
	}
	
	public static boolean isItMyPost(int authorID, String messageID) throws UnknownHostException, ClassNotFoundException, SQLException{
		MongoCollection<Document> mc = Database.getMongoCollection("message") ;
		Document d = new Document() ;
		d.append("_id",new ObjectId(messageID)) ;
		Document author = new Document();
		author.append("authorID", authorID);
		author.append("login", UserTools.getLoginByID(authorID));
		d.append("author", author) ;
		MongoCursor<Document> cursor = mc.find(d).iterator() ;
		boolean res = (cursor.hasNext()) ;
		cursor.close();
		return res ;
	}
	
	
	public static JSONObject insertComment(int commentatorID, String messageID, String c) throws UnknownHostException, ClassNotFoundException, SQLException, JSONException {
		MongoCollection<Document> mc = Database.getMongoCollection("message") ;
		Document d = new Document() ;
		d.append("_id", new ObjectId(messageID)) ;
		MongoCursor<Document> cursor = mc.find(d).iterator() ;
		Document d2 = cursor.next() ;
		
		int commentID = ((ArrayList<Document>) JSON.parse(d2.get("comments").toString())).size();
		Document author = new Document() ;
		author.append("commentatorID", commentatorID) ;
		author.append("login", UserTools.getLoginByID(commentatorID));
		

		
		Document comment = new Document() ;
		comment.append("id", commentID ) ;
		comment.append("author", author);
		comment.append("comment", c);
		GregorianCalendar date = new GregorianCalendar() ;
		comment.append("date", date.getTime()) ;
		
		Document champ = new Document();
		champ.append("comments", comment);

		Document modif = new Document() ;
		modif.append("$push", champ) ;
		mc.updateOne(d, modif) ;
		cursor.close();
		return new JSONObject(JSON.serialize(comment));
	
	}
	
	// Rend les messages d'un seul utilisateur triés selon la date (page profil).
	
	public static JSONArray listMessages(int authorID, int nombre) throws UnknownHostException, JSONException, ClassNotFoundException, SQLException{
		MongoCollection<Document> mc = Database.getMongoCollection("message") ;
		JSONArray listmessages = new JSONArray() ;
		Document author = new Document() ;
		author.append("authorID", authorID) ;
		author.append("login", UserTools.getLoginByID(authorID)) ;
		
		
		Document d3 = new Document();
		d3.append("author", author) ;
		
		Document date = new Document() ;
		date.append("date", -1 );
		MongoCursor<Document> cursor ;
		if(nombre != -1)
				cursor =  mc.find(d3).sort(date).limit(nombre).iterator();
		else {
			cursor = mc.find(d3).sort(date).iterator();
		}
		
		while (cursor.hasNext()){
			Document n = cursor.next() ;
			JSONObject monJSON = new JSONObject() ;
			monJSON.put("_id",n.get("_id") ) ;
			monJSON.put("authorID",n.get("author") ) ;
			monJSON.put("date",n.get("date") ) ;
			monJSON.put("like",n.get("like") ) ;
			monJSON.put("comments",n.get("comments") ) ;
			monJSON.put("message",n.get("message") ) ;	
			listmessages.put(monJSON) ;
		}
		cursor.close(); 
		return listmessages ;
	}
	
	
	// Rend le fil d'actualité d'un utilisateur 
	
	
	public static JSONArray Newsfeed(int id, int nb) throws ClassNotFoundException, SQLException, JSONException, UnknownHostException {
		 JSONArray listMessages = new JSONArray();
		 MongoCollection<Document> mc = Database.getMongoCollection("message") ;
		 Document author = new Document() ;
		 author.append("authorID", id) ;
		 author.append("login", UserTools.getLoginByID(id)) ;
		 
		 List<Integer> listFollowingsID = FriendTools.listFollowingsID(id);
		 List<String> logins = UserTools.getLoginsByIDS(listFollowingsID);
		 List<Document> tab = new ArrayList<Document>();
		 for(int i = 0 ; i< listFollowingsID.size() ; i ++) {
			 Document t = new Document() ;
			 t.append("authorID", listFollowingsID.get(i)) ;
			 t.append("login", logins.get(i)) ;
			 tab.add(t) ;		 
		 }
		 
		 Document query = new Document() ;
		 Document m_in = new Document() ;
		 m_in.append("$in", tab) ;
		 query.append("author",m_in) ;
		 //query.append("_id", d) ;
		 Document date = new Document() ;
		 date.append("date", -1) ;
		 MongoCursor<Document> cursor ;
		 if(nb != -1 ) {
			 cursor = mc.find(query).sort(date).limit(nb).iterator();
			 System.out.println("cc2");
		 }
			 
		 else {
			 System.out.println("cc");
			 cursor = mc.find(query).sort(date).iterator();
		 }
		 boolean res = cursor.hasNext();
		 System.out.println(res);
		 while(cursor.hasNext()) {
		 JSONObject monJSON = new JSONObject() ;
		 Document d2 = cursor.next();
		 monJSON.put("_id",d2.get("_id"));
		 monJSON.put("author",d2.get("author"));
		 monJSON.put("date",d2.get("date"));
		 monJSON.put("message",d2.get("message"));
		 monJSON.put("comments",d2.get("comments"));
		 monJSON.put("like",d2.get("like"));
		 listMessages.put(monJSON) ;
		 }
		 return listMessages ;
		
	}
	
	public static boolean PostExists(String messageID) throws UnknownHostException {
		MongoCollection<Document> mc = Database.getMongoCollection("message") ;
		Document d = new Document() ;
		d.append("_id", new ObjectId(messageID)) ;
		MongoCursor<Document> cursor = mc.find(d).iterator() ;
		boolean res = cursor.hasNext();
		cursor.close();
		return res ;
		
		
		
	}
	
	
	public static JSONArray listAllMessages() throws ClassNotFoundException, SQLException, JSONException, UnknownHostException {
		 JSONArray listMessages = new JSONArray();
		 MongoCollection<Document> mc = Database.getMongoCollection("message") ;
		 
		 
		 Document objet = new Document() ;
		 
		 Document date = new Document() ;
		 date.append("date", -1) ;
		 
		 MongoCursor<Document> cursor ;
		 cursor = mc.find().sort(date).iterator();
		 
		 
		 while(cursor.hasNext()) {
			 JSONObject monJSON = new JSONObject() ;
			 Document d2 = cursor.next();
			 monJSON.put("_id",d2.get("_id"));
			 monJSON.put("author",d2.get("author"));
			 monJSON.put("date",d2.get("date"));
			 monJSON.put("message",d2.get("message"));
			 monJSON.put("comments",d2.get("comments"));
			 monJSON.put("like",d2.get("like"));
			 listMessages.put(monJSON) ;
		 }
		 return listMessages ;
		
	}
	
	
	/** Map **/
	
	public static String mapDF() {
		String res = "function() {"
				+ "var text = this.message ;"
				+ "if(this.message != undefined){"
					+ "var id = this.id;"
					+ "var words = text.match(/\\w+/g);"
					+ "var tf = {};"
					+ "for(var i=0;i<words.length;i++){"
					+ 	"if(tf[words[i]] == null){"
					+ 		"tf[words[i]] = 1;"
					+ 	"}else{"
					+ 		"tf[words[i]] += 1;"
					+ 	"}"
					+ "}"
					+ "for(w in tf){"
					+ 	"emit(w,1);"
					+ "}"
				+ "}"
			+ "}";
			return res;
					
	}
	
	/** Reduce */
	public static String reduceDF(){
		String res = "function(key,val){"
			+ "return val.length;"
		+ "}";
		return res;
	}
	
	/**
	 * Map pour TDIDF (index)
	 */
	public static String mapTFIDF(){
		String res = "function(){"
			+ "var text = this.message;"
			+ "if(this.content != undefined){"
				+ "var id = this._id;"
				+ "var words = text.match(/\\w+/g);"
				+ "var tf = {};"
				+ "for(var i=0;i<words.length;i++){"
				+ 	"if(tf[words[i]] == null){"
				+ 		"tf[words[i]] = 1;"
				+ 	"}else{"
				+ 		"tf[words[i]] += 1;"
				+ 	"}"
				+ "}"
				+ "for(w in tf){"
				+ 	"var ret = {};"
				+ 	"ret[id] = tf[w];"
				+ 	"emit(w,ret);"
				+ "}"
			+ "}"
		+ "}";
		return res;
	}
	
	/**
	 * Reduce pour TDIDF (index)
	 */
	public static String reduceTFIDF(){
		String res = "function(key,val){"
			+ "var ret = {};"
			+ "for(var i=0;i<val.length;i++){"
				+ "for(var d in val[i]){"
					+ "ret[d] = val[i][d];"
				+ "}"
			+ "}"
			+ "return ret;"
		+ "}";
		return res;
	}
	
	/**
	 * Finalize pour TDIDF (index)
	 */
	public static String f(){
		String res = "function(k,v){"
			+ "var df = Object.keys(v).length;"
			+ "for(var d in v){"
				+ "v[d] = v[d] * Math.log(N/df);"
			+ "}"
			+ "return v;"
		+ "}";
		return res;
	}
	
	
	public static ArrayList<DBObject> getMessageByQuery(DBCollection index, DBCollection message, String query, int index_min, int nb){
		String[] q = query.split(" ");
		HashSet<String> w = new HashSet<String>(Arrays.asList(q));
		HashMap<String,Double> scores = new HashMap <String,Double>();
		for(String s : w){
			BasicDBObject obj = new BasicDBObject();
			obj.put("_id", s);
			DBCursor curseur = index.find(obj);
			if(curseur.hasNext()){
				DBObject res = curseur.next();
				HashMap<String,Double> docs = (HashMap<String,Double>) res.get("value");
				for (Entry<String, Double> d : docs.entrySet()){
					String id = d.getKey();
					double val = Double.valueOf(d.getValue());
					Double sc = scores.get(id);
					sc = (sc == null)?val:(sc+val);
					scores.put(id, sc);
				}
			}
		}
		
		//Trie par ordre croissant
		List<Map.Entry<String, Double>> entries = new ArrayList<Map.Entry<String,Double>>(scores.entrySet());
		Collections.sort(entries,new Comparator<Map.Entry<String, Double>>(){
			public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b){
				return b.getValue().compareTo(a.getValue());
			}
		});
		
		//Recuperer les messages
		ArrayList<DBObject> ret = new ArrayList<DBObject>();
		int cpt = 0;
		for(int i=index_min;i<entries.size();i++){
			BasicDBObject obj = new BasicDBObject();
			obj.put("_id",Integer.parseInt(entries.get(i).getKey()));
			DBCursor curseur = message.find(obj);
			if(curseur.hasNext()){
				DBObject res = curseur.next();
				ret.add(res);
				cpt++;
				if(cpt == nb){
					break;
				}
			}
		}
		return ret;
	}
	
	public static JSONArray listTextMessages(String text, int index_min, int nb)throws UnknownHostException, JSONException{/*throws SQLQueryException*/
		DBCollection message = Database.getMongoCollection2("message");
		DBCollection index = Database.getMongoCollection2("tfidf");
		ArrayList<DBObject> ret = getMessageByQuery(index, message, text, (index_min == -1)?0:index_min, nb);
		JSONArray listMessages = new JSONArray();
		for(DBObject document : ret){
			JSONObject json = new JSONObject();
			json.put("_id", document.get("_id"));
			json.put("content",document.get("content"));
			json.put("author",document.get("author"));
			json.put("date",document.get("date"));
			json.put("comments",document.get("comments"));
			listMessages.put(json);
		}
		return listMessages;
	}
	
}
