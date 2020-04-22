package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Select {
	/*
	 * Esta clase necesita limpieza pero es funcional, si hubiera tenido mas tiempo lo hubiera hecho,
	 * 		perdón de antemano, cualquier duda la resolveré encantado
	 */
	private Connection c;
	private static final String SELECT_ALL_ARTICLES = "SELECT ID,ARTICLE_TYPE,NAME,DIRECTOR,YEAR,MOVIE_TYPE,ANIMATION,SERIES_PARENT,"
			+ "SEASON_PARENT,";
	public Select(Connection c) throws SQLException {
		this.c = c;
	}
	
	public int getType(int id) throws SQLException {
		String sql = "SELECT ARTICLE_TYPE FROM ARTICLES WHERE ID = "+id;
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		String type = r.getString(1);
		r.close();
		if(type.equals("P")) {
			return 1;
		}
		if(type.equals("S")) {
			return 2;
		}
		if(type.equals("T")) {
			return 3;
		}
		return 4;	
	}
	

	public int getSeasonParentID(int seasonID) throws SQLException {
		String sql = "SELECT ID FROM ARTICLES WHERE ARTICLE_TYPE = 'S' AND ID < "+seasonID;
		Statement s = c.createStatement();
		ResultSet r = s .executeQuery(sql);
		int id = r.getInt(1); 
		r.close();
		return id;
	}
	
	public int count(String table) throws SQLException {
		String sql = "SELECT COUNT(*) FROM "+table;
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		int count = r.getInt(1); 
		r.close();
		return count;
	}
	
	public float valuation(int id) throws SQLException {
		String sql = "SELECT VALUATION FROM ARTICLES WHERE ID = "+id;
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		float valuation = r.getFloat(1); 
		r.close();
		return valuation;
	}
	
	public boolean login(String username, String password) throws SQLException {
		String sql = "SELECT ID FROM USERS WHERE USERNAME = '"+username+"' AND PASSWORD = '"+password+"'";
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		boolean succesful = r.getInt(1)==1;
		r.close();
		return succesful;
	}
	
	public int getSeriesData() throws SQLException {
		String sql = "SELECT MAX(ID) FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'S'";
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);		
		int maxID = r.getInt(1); 
		r.close();
		return maxID;
	}
	
	public ArrayList<String> articles(int filterF, int filterR, String search) throws SQLException {
		String sql = filteredSQL(filterF, filterR,search);
		ArrayList<String> articlesData = new ArrayList<>();
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		while(r.next()) {
			articlesData.add(fillData(sql,r));
		}
		r.close();
		return articlesData;
	}
	private String fillData(String sql, ResultSet r) throws SQLException {
		String temp = null;
		String data = "";
		for(int i=1;i<12;i++) {
			temp = "";
			if(r.getString(i)!=null) {
				temp += r.getString(i);
				if(i<11) {
					temp += "--";
				}
				data += temp;
			}
		}
		return data;
	}

	private String filteredSQL(int filterF, int filterR, String search) {
		String sql = SELECT_ALL_ARTICLES;
		if(filterR == 0) {
			sql += "STOCK,VALUATION FROM ARTICLES WHERE STOCK > 0 ";
		}
		if(filterR == 1) {
			sql += "RENTED,VALUATION FROM ARTICLES WHERE RENTED > 0 ";
		}
		if(filterR == 2) {
			sql += "REPAIRING,VALUATION FROM ARTICLES WHERE REPAIRING > 0 ";
		}
		if(filterF == 0) {
			sql += " AND NAME LIKE '%"+search+"%' ORDER BY NAME ASC";
		} 
		if(filterF == 1) {
			sql += " AND DIRECTOR LIKE '%"+search+"%' AND SERIES_PARENT IS NULL ORDER BY DIRECTOR ASC";
		} 
		if(filterF == 2) {
			sql += " AND YEAR LIKE '%"+search+"%' AND SERIES_PARENT IS NULL ORDER BY YEAR ASC";
		} 
		if(filterF == 3) {
			sql += " AND VALUATION LIKE '%"+search+"%' ORDER BY VALUATION ASC";
		} 
		if(filterF == 4) {
			sql += " AND MOVIE_TYPE LIKE '%"+search+"%' AND MOVIE_TYPE IS NOT NULL ORDER BY MOVIE_TYPE ASC";
		}
		if(filterF == 5) {
			sql += " AND ANIMATION LIKE '%"+search+"%' AND ANIMATION IS NOT NULL ORDER BY ANIMATION ASC";
		}
		if(filterF == 6) {
			sql += " AND NAME LIKE '%"+search+"%' AND ARTICLE_TYPE LIKE 'T' ORDER BY NAME ASC";
		}
		if(filterF == 7) {
			sql += " AND NAME LIKE '%"+search+"%' AND SEASON_PARENT IS NOT NULL ORDER BY NAME ASC";
		}
		return sql;
	}
	
	private int getMax(String maxField, int filterF, String textToFind, int column) throws SQLException {
		String filter = "";
		if(filterF==0) {
			filter = "NAME";
		}
		if(filterF==1) {
			filter = "DIRECTOR";
		}
		if(filterF==2) {
			filter = "YEAR";
		}
		if(filterF==3) {
			filter = "VALUATION";
		}
		if(filterF==4) {
			filter = "MOVIE_TYPE";
		}
		if(filterF==5) {
			filter = "ANIMATION";
		}
		if(filterF==6) {
			filter = "ARTICLE_TYPE LIKE 'T' AND NAME";
		}
		if(filterF==7) {
			filter = "ARTICLE_TYPE LIKE 'C' AND NAME";
		}
		if(column==4) {
			filter = "ARTICLE_TYPE NOT LIKE 'T' AND "+filter;
		}
		if(column==2 && filterF!=7) {
			filter = "ARTICLE_TYPE NOT LIKE 'T' AND ARTICLE_TYPE NOT LIKE 'C' AND "+filter;
		}
		if(filterF==6 && column==2) {
			filter = "ARTICLE_TYPE LIKE 'T' AND NAME";
		}
		String sql = "SELECT MAX(LENGTH("+maxField+")) FROM ARTICLES WHERE "+filter+" LIKE '%"+textToFind+"%'";
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		int maxLength = r.getInt(1);
		return maxLength;
	}
	
	public int getMaxLength(int filterF, int column, String text) throws SQLException {
		if(column==0 && (filterF==0 || filterF==3)) {
			return 9;
		}
		if(column==0 && !(filterF==0 || filterF==3)) {
			return 8;
		}
		String[][] maxFieldMovie = { //FILTERF,COLUMN
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","ARTICLE_TYPE","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","VALUATION","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","VALUATION","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","ARTICLE_TYPE","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","MOVIE_TYPE","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","ANIMATION","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"}};
		String[][] maxFieldSeries = { //FILTERF,COLUMN
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","ARTICLE_TYPE","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","VALUATION","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","VALUATION","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","NAME","DIRECTOR","ARTICLE_TYPE","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"}};
		String[][] maxFieldSeason = { //FILTERF,COLUMN
				{"ARTICLE_TYPE","YEAR","SERIES_PARENT","NAME","ARTICLE_TYPE","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","SERIES_PARENT","NAME","ARTICLE_TYPE","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","SERIES_PARENT","NAME","ARTICLE_TYPE","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"}};
		String[][] maxFieldEpisode = { //FILTERF,COLUMN
				{"ARTICLE_TYPE","YEAR","SERIES_PARENT","SEASON_PARENT","NAME","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","SERIES_PARENT","SEASON_PARENT","NAME","VALUATION","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE","ARTICLE_TYPE"},
				{"ARTICLE_TYPE","YEAR","SERIES_PARENT","SEASON_PARENT","NAME","VALUATION","ARTICLE_TYPE"}};
		int movie = 0;
		int series = 0;
		int season = 0;
		int episode = 0;
		if(filterF==1 || filterF==2) {
			movie = getMax(maxFieldMovie[filterF][column],filterF,text,column);
			series = getMax(maxFieldSeries[filterF][column],filterF,text,column);
			return Math.max(movie, series);
		}
		if(filterF==4 || filterF==5) {
			movie = getMax(maxFieldMovie[filterF][column],filterF,text,column);
			return movie;
		}
		if(filterF==6) {
			season = getMax(maxFieldSeason[filterF][column],filterF,text,column);
			return season;
		}
		if(filterF==7) {
			episode = getMax(maxFieldEpisode[filterF][column],filterF,text,column);
			return episode;
		}
		movie = getMax(maxFieldMovie[filterF][column],filterF,text,column);
		series = getMax(maxFieldSeries[filterF][column],filterF,text,column);
		season = getMax(maxFieldSeason[filterF][column],filterF,text,column);
		episode = getMax(maxFieldEpisode[filterF][column],filterF,text,column);
		return Math.max(Math.max(movie, season), Math.max(series, episode));
	}
	
	private int getNextSeriesID(int seriesID) throws SQLException {
		String sql = "SELECT MAX(ID) FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'S'";
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		int maxID = r.getInt(1);
		r.close();
		int id = -1;
		if(maxID == seriesID) {
			return id;
		} 
		sql = "SELECT ID FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'S' AND ID > "+seriesID;
		Statement s1 = c.createStatement();
		ResultSet r1 = s1.executeQuery(sql);
		id = r1.getInt(1); 
		r1.close();
		return id;
	}
	
	public int seriesParentID(int seasonID) throws SQLException {
		String sql = "SELECT MAX(ID) FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'S' AND ID < "+seasonID;
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		int parentID = r.getInt(1);
		r.close();
		return parentID;
	}
	
	public int getEpisodeParentID(int episodeID) throws SQLException {
		String sql = "SELECT MAX(ID) FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'T' AND ID < "+episodeID;
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		int parentID = r.getInt(1);
		r.close();
		return parentID;
	}
	
	private int getPreviousSeriesID(int seriesID) throws SQLException {
		String sql = "SELECT MIN(ID) FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'S'";
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		int minID = r.getInt(1);
		r.close();
		int id = -1;
		if(minID == seriesID) {
			return id;
		}
		sql = "SELECT MAX(ID) FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'S' AND ID < "+seriesID;
		Statement s1 = c.createStatement();
		ResultSet r1 = s1.executeQuery(sql);
		id = r1.getInt(1); 
		r1.close();
		return id;
	}
	
	private int getNextSeasonID(int seasonID) throws SQLException {
		String sql = "SELECT MAX(ID) FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'T'";
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		int maxID = r.getInt(1);
		r.close();
		int id = -1;
		if(maxID == seasonID) {
			return id;
		} 
		sql = "SELECT ID FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'T' AND ID > "+seasonID;
		Statement s1 = c.createStatement();
		ResultSet r1 = s1.executeQuery(sql);
		id = r1.getInt(1); 
		r1.close();
		return id;
	}
	
	public ArrayList<Integer> getSeriesSeasonID(int seriesID) throws SQLException {
		ArrayList<Integer> seasonsID = new ArrayList<>();
		int nextSeriesID = getNextSeriesID(seriesID);
		String sql = "SELECT ID FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'T' AND ID BETWEEN "+seriesID+" AND "+nextSeriesID;
		if(nextSeriesID<0) {
			int previousSeriesID = getPreviousSeriesID(seriesID);
			sql = "SELECT ID FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'T' AND ID BETWEEN "+seriesID+" AND "+getMaxID(); 
			if(previousSeriesID<0) {
				sql = "SELECT ID FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'T'";
			}
		}
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		while(r.next()) {
			seasonsID.add(r.getInt(1));
		}
		r.close();
		return seasonsID;
	}

	public ArrayList<Integer> getSeasonEpisodesID(int seasonID) throws SQLException {
		ArrayList<Integer> episodesID = new ArrayList<>();
		int nextSeasonID = getNextSeasonID(seasonID);
		String sql = "SELECT ID FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'C' AND ID BETWEEN "+seasonID+" AND "+nextSeasonID;
		if(nextSeasonID<0) {
			int previousSeasonID = getPreviousSeasonID(seasonID);
			sql = "SELECT ID FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'C' AND ID BETWEEN "+seasonID+" AND "+getMaxID(); 
			if(previousSeasonID<0) {
				sql = "SELECT ID FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'C'";
			}
		}
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		while(r.next()) {
			episodesID.add(r.getInt(1));
		}
		r.close();
		return episodesID;
	}
	
	private int getPreviousSeasonID(int seasonID) throws SQLException {
		String sql = "SELECT MIN(ID) FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'T'";
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery(sql);
		int minID = r.getInt(1);
		r.close();
		int id = -1;
		if(minID == seasonID) {
			return id;
		} else {
			sql = "SELECT MAX(ID) FROM ARTICLES WHERE ARTICLE_TYPE LIKE 'T' AND ID < "+seasonID;
			Statement s1 = c.createStatement();
			ResultSet r1 = s1.executeQuery(sql);
			id = r1.getInt(1); 
			r1.close();
		}
		return id;
	}

	private int getMaxID() throws SQLException {
		Statement s = c.createStatement();
		String sql = "SELECT MAX(ID) FROM ARTICLES";
		ResultSet r = s.executeQuery(sql);
		int maxID = r.getInt(1);
		r.close();
		return maxID;
	}

	public float getValuation(int id) throws SQLException {
		Statement s = c.createStatement();
		String sql = "SELECT VALUATION FROM ARTICLES WHERE ID = "+id;
		ResultSet r = s.executeQuery(sql);
		float valuation = r.getFloat(1);
		r.close();
		return valuation;
	}

	public int getStock(int id) throws SQLException {
		Statement s = c.createStatement();
		String sql = "SELECT STOCK FROM ARTICLES WHERE ID = "+id;
		ResultSet r = s.executeQuery(sql);
		int stock = r.getInt(1);
		r.close();
		return stock;
	}

	public int getUserID(String username) throws SQLException {
		Statement s = c.createStatement();
		String sql = "SELECT ID FROM USERS WHERE USERNAME LIKE '"+username+"'";
		ResultSet r = s.executeQuery(sql);
		int userID = r.getInt(1);
		r.close();
		return userID;
	}

	public ArrayList<String> getAllUsers() throws SQLException {
		ArrayList<String> usersData = new ArrayList<>();
		Statement s = c.createStatement();
		String sql = "SELECT ID, USERNAME, PASSWORD FROM USERS";
		ResultSet r = s.executeQuery(sql);
		while(r.next()) {
			usersData.add(r.getString(1)+"--Usuario: "+formatString(r.getString(2),getMaxLengthU())+"| Contraseña: "+formatString(r.getString(3),getMaxLengthP()));
		}
		r.close();
		return usersData;
	}
	
	public String formatString(String s, int length) {
		String formatS = " " + s + " ";
		String pattern = "_";
		for(int i=formatS.length();i<length;i++) {
			formatS += pattern;
			if(i==length-2) {
				pattern = " ";
			}
		}
		return formatS;
	}
	
	private int getMaxLengthU() throws SQLException {
		Statement s = c.createStatement();
		String sql = "SELECT MAX(LENGTH(USERNAME)) FROM USERS";
		ResultSet r = s.executeQuery(sql);
		int maxLength = r.getInt(1);
		r.close();
		return maxLength+2;		
	}
	
	private int getMaxLengthP() throws SQLException {
		Statement s = c.createStatement();
		String sql = "SELECT MAX(LENGTH(PASSWORD)) FROM USERS";
		ResultSet r = s.executeQuery(sql);
		int maxLength = r.getInt(1);
		r.close();
		return maxLength+2;		
	}

	public int getLength(int i, String string, String string2) throws SQLException {
		Statement s = c.createStatement();
		String sql = "SELECT MAX(LENGTH("+string+")) FROM ARTICLES WHERE "+string+" LIKE '%"+string2+"%'";
		ResultSet r = s.executeQuery(sql);
		int maxLength = r.getInt(1);
		r.close();
		return maxLength;
	}
}
