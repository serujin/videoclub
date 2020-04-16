package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Select {
	private Statement s;
	private static final String SERIE = "Serie    |";
	private static final String MOVIE = "Película |";
	public Select(Connection c) throws SQLException {
		s = c.createStatement();
	}
	
	public int countUsers() throws SQLException {
		int i = 0;
		ResultSet r = s.executeQuery("SELECT * FROM USERS");
		while(r.next()) {
			i++;
		}
		return i;
	}
	
	public boolean login(String username, String password) throws SQLException {
		String sql = "SELECT ID FROM USERS WHERE USERNAME = '"+username+"' AND PASSWORD = '"+password+"';";
		ResultSet r = s.executeQuery(sql);
		int id = r.getInt(1);
		return id==1;
	}
	
	private String formatType(int type) {
		if(type==0) {
			return SERIE;
		}
		return MOVIE;
	}
	
	private String formatLength(String s, int length, boolean start) {
		String formatS = "";
		if(start) {
			formatS += " | ";
		}
		formatS += s;
		for(int i=formatS.length();i<length;i++) {
			formatS += " ";
		}
		return formatS;
	}
}
