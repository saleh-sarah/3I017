package db;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import service.MessageServices;
import service.UserServices;

public class TestJDBC {

	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JSONException, UnknownHostException{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(2);
		l.add(1);
		//System.out.println(UserServices.login("loulou", "bonjour.123", "false"));
		//System.out.println(FriendTools.listFollowers(2));
		//MessageTools.deletePost("5c7d9477c94885490723b6a7");
		//System.out.println(MessageTools.listMessages(1, -1));
		//System.out.println(MessageServices.addPost("hello", "ZRTAX93YUVTMOQDY5JCBWO5DIIVLG6HE"));
		System.out.println(MessageTools.insertComment(2, "5cb8864cdbe5eb5224caa173", "tt"));
	}
}
