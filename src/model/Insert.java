package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Insert {
	private Connection c;
	private File articles;
	private File articlesGuide;
	private static final String INSERT_USER = "INSERT INTO USERS(USERNAME,PASSWORD) VALUES(?,?)";
	private static final String MESSAGE = "<--- Como añadir artículos --->\n\n"
			+ "Escribe en el fichero \"articles.txt\" que encontrarás en el directorio del programa\n\n"
			+ "Añadir Películas --> P--Nombre--Director--Año--Género(1 = Ciencia-Ficción o 2 = Animación)--Animación(solo si se especifica 2 en el género)--Nº Copias\n"
			+ "Añadir Series -----> S--Nombre--Director--Año--Nº Copias\n"
			+ "Añadir Temporadas -> T--Nombre(opcional)\n"
			+ "Añadir Capítulos --> C--Nombre\n\n"
			+ "*** Importante ***\n\n"
			+ "Las temporadas de las series deben ir justo debajo de su serie\n"
			+ "Los capítulos de las temporadas deben ir justo debajo de su temporada\n\n"
			+ "Ejemplo:\n"
			+ "S--Élite--Carlos Montero--2018--3"
			+ "P--Peter Pan--Clyde Geronimi--1954--2--Dibujos Animados--4\n"
			+ "S--Élite--Carlos Montero--2018--3\n"
			+ "T--\n"
			+ "C--Bienvenidos\n"
			+ "C--Deseo\n"
			+ "...\n"
			+ "T--Temporada 2\n"
			+ "...";
	private static final String ANIMATION_MOVIE = "INSERT INTO ARTICLES(NAME,ARTICLE_TYPE,DIRECTOR,YEAR,MOVIE_TYPE,ANIMATION,STOCK) VALUES(?,?,?,?,?,?,?)";
	private static final String SCIFI_MOVIE = "INSERT INTO ARTICLES(NAME,ARTICLE_TYPE,DIRECTOR,YEAR,MOVIE_TYPE,STOCK) VALUES(?,?,?,?,?,?)";
	private static final String SERIE = "INSERT INTO ARTICLES(NAME,ARTICLE_TYPE,DIRECTOR,YEAR,STOCK) VALUES(?,?,?,?,?)";
	private static final String SEASON = "INSERT INTO ARTICLES(NAME,ARTICLE_TYPE,YEAR,SERIES_PARENT,STOCK) VALUES(?,?,?,?,?)";
	private static final String EPISODE = "INSERT INTO ARTICLES(NAME,ARTICLE_TYPE,YEAR,SERIES_PARENT,SEASON_PARENT,EPISODE_NUMBER,STOCK) VALUES(?,?,?,?,?,?,?)";
	public Insert(Connection c) throws SQLException, IOException {
		this.c = c;
		articles = new File("articles.txt");
		articlesGuide = new File("articlesGuide.txt");
		if(articles.createNewFile()) {
			articlesGuide.createNewFile();
			FileWriter fw = new FileWriter(articlesGuide);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(MESSAGE);
            bw.close();
		}
	}
	
	public void user(String user, String password) throws SQLException {
		PreparedStatement p = c.prepareStatement(INSERT_USER);
		p.setString(1, user);
		p.setString(2, password);
		p.executeUpdate();
	}
	
	public void articles() throws SQLException, NumberFormatException, IOException {
		Select select = new Select(c);
		String help = "1º Consulta en la carpeta del proyecto el archivo \"articlesGuide.txt\"\n"
				+ "2º Agrega tus artículos en el archivo \"articles.txt\" siguiendo al detalle\n"
				+ "		la guía del archivo \"articlesGuide.txt\"\n"
				+ "3º Pulsa el botón de aceptar de aquí debajo para añadir los artículos escritos";
		JOptionPane.showMessageDialog(null, help,"Añadir artículos",JOptionPane.PLAIN_MESSAGE);
		insertFromFile(select);
		JOptionPane.showMessageDialog(null, "Artículos añadidos con éxito","Añadir artículos",JOptionPane.PLAIN_MESSAGE);
	}

	private void insertFromFile(Select select) throws IOException, SQLException {
		addArticles(select);
	}

	private void addArticles(Select select) throws IOException, SQLException {
		int currentEpisode = 1;
		int stock = 0;
		PreparedStatement insertArticles=null;
		FileReader fr = new FileReader(articles);
		BufferedReader input = new BufferedReader(fr); 
		String line;
		String seriesName = null;
		String seasonsName = null;
		int seasonsNumber = 0;
		int seriesYear = 0;
		while((line=input.readLine()) != null) {
			String[] data = line.split("--");
			if(data[0].equals("P")) {
				if(Integer.parseInt(data[4])==1) {
					insertArticles = c.prepareStatement(SCIFI_MOVIE);
					insertArticles.setString(1, data[1]);
					insertArticles.setString(2, "P");
					insertArticles.setString(3, data[2]);
					insertArticles.setInt(4, Integer.parseInt(data[3]));
					insertArticles.setString(5, "Ciencia-Ficción");
					insertArticles.setInt(6, Integer.parseInt(data[5]));
				} else {
					insertArticles = c.prepareStatement(ANIMATION_MOVIE);
					insertArticles.setString(1, data[1]);
					insertArticles.setString(2, "P");
					insertArticles.setString(3, data[2]);
					insertArticles.setInt(4, Integer.parseInt(data[3]));
					insertArticles.setString(5, "Animación");
					insertArticles.setString(6, data[5]);
					insertArticles.setInt(7, Integer.parseInt(data[6]));
				}
			} 
			if(data[0].equals("S")) {
				stock = Integer.parseInt(data[4]);
				insertArticles = c.prepareStatement(SERIE);
				insertArticles.setString(1, data[1]);
				insertArticles.setString(2, "S");
				insertArticles.setString(3, data[2]);
				seriesYear = Integer.parseInt(data[3]);
				insertArticles.setInt(4, Integer.parseInt(data[3]));
				insertArticles.setInt(5, stock);
				seriesName = data[1];
			}
			if(data[0].equals("T")) {
				seasonsNumber++;
				insertArticles = c.prepareStatement(SEASON);
				if(data.length==2) {
					insertArticles.setString(1, data[1]);
					seasonsName = data[1];
				} else {
					insertArticles.setString(1, "Temporada "+seasonsNumber);
					seasonsName = "Temporada "+seasonsNumber;
				}
				insertArticles.setString(2, "T");
				insertArticles.setInt(3, seriesYear);
				insertArticles.setString(4, seriesName);
				insertArticles.setInt(5, stock);
				currentEpisode = 1;
			}	
			if(data[0].equals("C"))  {
				insertArticles = c.prepareStatement(EPISODE);
				insertArticles.setString(1, data[1]);
				insertArticles.setString(2, "C");
				insertArticles.setInt(3, seriesYear);
				insertArticles.setString(4, seriesName);
				insertArticles.setString(5, seasonsName);
				insertArticles.setInt(6, currentEpisode);
				insertArticles.setInt(7, stock);
				currentEpisode++;
			}
			insertArticles.executeUpdate();
		}
		input.close();
		fr.close();
	}
}
