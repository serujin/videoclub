package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Insert {
	private Connection c;
	private PreparedStatement p;
	private ResultSet r;
	private Statement s;
	private static final String INSERT_USER = "INSERT INTO USERS(USERNAME,PASSWORD) VALUES(?,?)";
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES(NAME,DIRECTOR,YEAR,TYPE) VALUES(?,?,?,?)";
	private static final String INSERT_EPISODE = "INSERT INTO EPISODES(NAME,ID) VALUES(?,?)";
	private static final String INSERT_MOVIE = "INSERT INTO MOVIES(ID,TYPE,ATYPE) VALUES(?,?,?)";
	private static final String INSERT_SEASON = "INSERT INTO SEASONS(NAME,ID) VALUES(?,?)";
	
	public Insert(Connection c) throws SQLException {
		this.c = c;
		s = c.createStatement();
	}
	
	public void user(String user, String password) throws SQLException {
		p = c.prepareStatement(INSERT_USER);
		p.setString(1, user);
		p.setString(2, password);
		p.executeUpdate();
	}
	
	public void article() throws SQLException {
		boolean movie = false;
		int option = JOptionPane.showConfirmDialog(null,"¿Deseas añadir una película?", "Tipo de Artículo", JOptionPane.YES_NO_OPTION);
		if(option==JOptionPane.YES_OPTION) {
			movie = true;
		} 
		int quantity = Integer.parseInt(JOptionPane.showInputDialog(null,"¿Cuántas copias deseas añadir?"));
		insertArticle(movie, quantity);
		if(movie) {
			insertMovie();
		} else {
			insertSeason(quantity);
		}
		JOptionPane.showMessageDialog(null,"Artículo añadido con éxito al inventario","Añadir artículos", JOptionPane.PLAIN_MESSAGE);
	}

	private void insertArticle(boolean movie, int quantity) throws SQLException {
		String articleType = " serie";
		int type = 0;
		if(movie) {
			articleType = " película";
			type = 1;
		}
		String name = JOptionPane.showInputDialog("Inserte el nombre de la"+articleType);
		String director = JOptionPane.showInputDialog("Inserte el nombre y apellidos del director de la"+articleType);
		int year = Integer.parseInt(JOptionPane.showInputDialog("Inserte el año de lanzamiento de la"+articleType));
		p = c.prepareStatement(INSERT_ARTICLE);
		p.setString(1, name);
		p.setString(2, director);
		p.setInt(3, year);
		p.setInt(4, type);
		p.executeUpdate();
		addStock("ARTICLES",quantity);
	}
	
	private void insertMovie() throws SQLException {
		r = s.executeQuery("SELECT MAX(ID) FROM ARTICLES");
		int articleID = r.getInt(1);
		String type;
		String animation = "";
		int option = JOptionPane.showConfirmDialog(null,"¿La película es de animación?", "Tipo de Película", JOptionPane.YES_NO_OPTION);
		if(option==JOptionPane.YES_OPTION) {
			type = "Animación";
			animation =  JOptionPane.showInputDialog("Inserte el tipo de animación de la película");
		} else {
			type = "Ciencia-Ficción";
		}
		p = c.prepareStatement(INSERT_MOVIE);
		p.setInt(1, articleID);
		p.setString(2, type);
		p.setString(3, animation);
		p.executeUpdate();
	}
	
	private void insertSeason(int quantity) throws SQLException {
		r = s.executeQuery("SELECT MAX(ID) FROM ARTICLES");
		int articleID = r.getInt(1);
		int seasons = Integer.parseInt(JOptionPane.showInputDialog(null, "¿Cuántas temporadas conforman la serie?"));
		for(int i=0;i<seasons;i++) {
			int option = JOptionPane.showConfirmDialog(null,"¿La temporada "+(i+1)+" tiene nombre?", "Nombre de temporada", JOptionPane.YES_NO_OPTION);
			String name;
			if(option==JOptionPane.YES_OPTION) {
				name = JOptionPane.showInputDialog("Inserte el nombre de la temporada "+(i+1));
			} else {
				name = "Temporada "+(i+1);
			}
			p = c.prepareStatement(INSERT_SEASON);
			p.setString(1, name);
			p.setInt(2, articleID);
			p.executeUpdate();
			addStock("SEASONS",quantity);
			insertEpisode(quantity);
		}
	}
	
	private void insertEpisode(int quantity) throws SQLException {
		r = s.executeQuery("SELECT MAX(ID) FROM SEASONS");
		int seasonID = r.getInt(1);
		int episodes = Integer.parseInt(JOptionPane.showInputDialog(null,"¿Cuántas capítulos conforman la temporada?"));
		for(int i=0;i<episodes;i++) {
			String name = JOptionPane.showInputDialog("Inserte el nombre del capítulo "+(i+1));
			p = c.prepareStatement(INSERT_EPISODE);
			p.setString(1, name);
			p.setInt(2, seasonID);
			p.executeUpdate();
			addStock("EPISODES", quantity);
		}
	}
	
	private void addStock(String table, int quantity) throws SQLException {
		r = s.executeQuery("SELECT MAX(ID) FROM "+table);
		int ID = r.getInt(1);
		String sql = "INSERT INTO "+table+"_STATE (ID,STOCK) VALUES(?,?)";
		p = c.prepareStatement(sql);
		p.setInt(1, ID);
		p.setInt(2, quantity);
		p.executeUpdate();
	}
}
