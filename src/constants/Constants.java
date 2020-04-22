package constants;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Constants {
	
	/*
	 * 
	 * Esta clase tiene todas las constantes (como su propio nombre indica) que he podido (por el tiempo) sacar,
	 * 		las demás constantes son fruto de cálculos con ésta clase (posiciones relativas de componentes,
	 * 		tamaños...)
	 * 
	 * También se encarga de cargar las fuentes de texto del sistema
	 * 
	 */
	
	private Constants() {}
	public static void initLabelsFont() throws FontFormatException, IOException {
		filterFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/6.5));
		helpFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/8.5));
		userFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/9.5));
		userOptionsFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/10.5));
		rentFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/3.5));
		resultFont = Font.createFont(Font.TRUETYPE_FONT, FONT_2).deriveFont((float) (FILTER_W/14.6));
		pointsFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/8.7));
		insideUserLabelsFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/1.7));
		insideUserButtonsFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/3.9));
		insideUserAdminButtonsFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/5.9));
		actionButtonFont = Font.createFont(Font.TRUETYPE_FONT, FONT_1).deriveFont((float) (FILTER_W/8.5));
		userAdminListFont = Font.createFont(Font.TRUETYPE_FONT, FONT_2).deriveFont((float) (FILTER_W/5.6));
		registerFonts();
	}
	private static void registerFonts() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(filterFont);
		ge.registerFont(helpFont);
		ge.registerFont(userFont);
		ge.registerFont(userOptionsFont);
		ge.registerFont(rentFont);
		ge.registerFont(resultFont);
		ge.registerFont(pointsFont);
		ge.registerFont(insideUserLabelsFont);
		ge.registerFont(insideUserButtonsFont);
		ge.registerFont(actionButtonFont);
		ge.registerFont(insideUserAdminButtonsFont);
		ge.registerFont(userAdminListFont);
	}
	//HERE ALL ABOUT GRAPHICS
	public static final int W = (int) (Toolkit.getDefaultToolkit().getScreenSize().width/1.3);
	public static final int H = (int) (Toolkit.getDefaultToolkit().getScreenSize().height/1.3);
	public static final String WINDOW_TITLE = "Video Vision";
	public static final Color BG_COLOR = new Color(99,0,0);
	private static final File FONT_1 = new File("fonts/Custom.ttf");
	private static final File FONT_2 = new File("fonts/Result.ttf");
	public static Font filterFont;
	public static Font helpFont;
	public static Font userFont;
	public static Font userOptionsFont;
	public static Font rentFont;
	public static Font resultFont;
	public static Font pointsFont;
	public static Font insideUserLabelsFont;
	public static Font insideUserButtonsFont;
	public static Font actionButtonFont;
	public static Font insideUserAdminButtonsFont;
	public static Font userAdminListFont;
	//HERE ALL ABOUT LOGO 
	public static final String LOGO_PATH = "VD_LOGO.png";
	public static final String LOGIN_IMG_PATH = "LOGIN.png";
	//HERE ALL ABOUT LOGIN PANEL
	public static final int LOGIN_W = W/4;
	public static final int LOGIN_H = (int) (H/1.2);
	public static final int LOGIN_X = W/2 - LOGIN_W/2;
	public static final int LOGIN_Y = (int) (H/2.0 - LOGIN_H/1.95);
	public static final int LOGIN_LOGO_SIZE = LOGIN_W/2;
	public static final int LOGIN_LOGO_X = LOGIN_W/2 - LOGIN_LOGO_SIZE/2;
	public static final int LOGIN_LOGO_Y = LOGIN_H/20;
	public static final int LOGIN_IMG_W = (int) (LOGIN_W/1.3);
	public static final int LOGIN_IMG_H = LOGIN_IMG_W/16*9;
	public static final int LOGIN_IMG_X = LOGIN_W/2 - LOGIN_IMG_W/2;
	public static final int LOGIN_IMG_Y = (int) (LOGIN_H/3.5);
	public static final int INPUT_W = (int) (LOGIN_W/1.3);
	public static final int INPUT_H = LOGIN_H/20;
	public static final int INPUT_X = LOGIN_W/2 - INPUT_W/2;
	public static final int INPUT_1_Y = (int) (LOGIN_H/1.9);
	public static final int INPUT_2_Y = (int) (LOGIN_H/1.7);
	public static final int SUBMIT_W = (int) (LOGIN_W/2.5);
	public static final int SUBMIT_H = LOGIN_H/21;
	public static final int SUBMIT_X = LOGIN_W/2 - SUBMIT_W/2;
	public static final int SUBMIT_Y = (int) (LOGIN_H/1.4);
	public static final String LOGIN_BG_PATH = "FONDO_LOGIN.png";
	public static final Point LOGIN_POINT = new Point(LOGIN_X,LOGIN_Y);
	//HERE ALL ABOUT FILTER PANEL
	public static final int FILTER_W = W/10;
	public static final int FILTER_H = H;
	public static final int FILTER_X = 0;
	public static final int FILTER_Y = 0;
	public static final int FILTER_LOGO_SIZE = FILTER_W;
	public static final int FILTER_LOGO_X = - FILTER_W/100;
	public static final int FILTER_LOGO_Y = - FILTER_LOGO_SIZE/10;
	public static final String FILTER_TEXT_PATH = "FILTER.png";
	public static final int FILTER_TEXT_W = (int) (FILTER_W/1.2);
	public static final int FILTER_TEXT_H = (int) (FILTER_W/3.0);
	public static final int FILTER_TEXT_X = FILTER_W/2 - FILTER_TEXT_W/2;
	public static final int FILTER_TEXT_Y = (int) (FILTER_H/6.3);
	public static final int OPTION_W = FILTER_H-FILTER_W/8;
	public static final int OPTION_H = FILTER_H/20;
	public static final int OPTION_X = FILTER_W/8;
	public static final int OPTION_Y = (int) (FILTER_H/3.9);
	public static final int OPTION_MARGIN_TOP = (int) (FILTER_H/30);
	public static final String[] OPTION_TITLES = {" Nombre", " Director", " Año", " Valoración", " Género", " Animación", " Temporada", " Capítulo"};
	public static final int HELP_BUTTON_W = (int) (FILTER_W/1.2);
	public static final int HELP_BUTTON_H = FILTER_H/30;
	public static final int HELP_BUTTON_X = FILTER_W/2 - HELP_BUTTON_W/2;
	public static final int HELP_BUTTON_Y = (int) (FILTER_H - HELP_BUTTON_X*2.75 - HELP_BUTTON_H);
	public static final Point FILTER_POINT = new Point(FILTER_X,FILTER_Y);
	//HERE ALL ABOUT USER PANEL
	public static final int USER_W = W/6;
	public static final int USER_H = H/7;
	public static final int USER_X = W - USER_W;
	public static final int USER_Y = 0;
	public static final int USER_LABEL_W = (int) (USER_W/2.2);
	public static final int USER_LABEL_H = USER_H/6;
	public static final int USER_PADDING = (int) (USER_H/10.8);
	public static final int USER_IMG_SIZE = (int) (USER_H/1.2);
	public static final int USERNAME_X = (USER_PADDING*2) + USER_IMG_SIZE;
	public static final int USERNAME_Y = (int) (USER_H/3.2);
	public static final int ADMIN_Y = (int) (USERNAME_Y + USER_LABEL_H*1.1);
	public static final int OPT_BUTTON_Y = USER_H - USER_LABEL_H - USER_PADDING;
	public static final int OPT_BUTTON_W = (int) (USER_W/2.2);
	public static final String USER_IMG_PATH = "USER.png";
	public static final Point USER_POINT = new Point(USER_X,USER_Y);
	//HERE ALL ABOUT SEARCH PANEL 
	public static final int SEARCH_W = W - USER_W - FILTER_W;
	public static final int SEARCH_H = USER_H;
	public static final int SEARCH_X = FILTER_W;
	public static final int SEARCH_Y = 0;
	public static final String SEARCH_TEXT_PATH = "SEARCH.png";
	public static final int SEARCH_TEXT_W = SEARCH_W/3;
	public static final int SEARCH_TEXT_H = SEARCH_H/3;
	public static final int SEARCH_TEXT_X = (SEARCH_H/7)*2;
	public static final int SEARCH_TEXT_Y = SEARCH_H/7;
	public static final int SEARCH_BAR_W = (int) (SEARCH_W*0.9);
	public static final int SEARCH_BAR_H = SEARCH_H/4;
	public static final int SEARCH_BAR_X = SEARCH_H/7;
	public static final int SEARCH_BAR_Y = (int) (SEARCH_H/1.5 - SEARCH_BAR_H/2.0);
	public static final int SEARCH_BUTTON_W = SEARCH_W - SEARCH_BAR_W - (SEARCH_BAR_X*2);
	public static final int SEARCH_BUTTON_H = SEARCH_BAR_H;
	public static final int SEARCH_BUTTON_X = SEARCH_BAR_X + SEARCH_BAR_W;
	public static final int SEARCH_BUTTON_Y = SEARCH_BAR_Y;
	public static final Point SEARCH_POINT = new Point(SEARCH_X, SEARCH_Y);
	//HERE ALL ABOUT RESULTS PANEL 
	public static final int RESULT_W = W - FILTER_W;
	public static final int RESULT_H = H - USER_H;
	public static final int RESULT_X = FILTER_W;
	public static final int RESULT_Y = USER_H;
	public static final int RESULT_LIST_W = (int) (RESULT_W/1.5);
	public static final int RESULT_LIST_H =	RESULT_H-W/12;
	public static final int RESULT_LIST_X = RESULT_W/25;
	public static final int RESULT_LIST_Y = RESULT_W/20;
	public static final int RESULT_OPTION_X = 0;
	public static final int RESULT_OPTION_Y = RESULT_H/100;
	public static final int RESULT_OPTION_W = RESULT_W/12;
	public static final int RESULT_OPTION_H = (int) (RESULT_H/13.5);
	public static final int RESULT_OPTION_MARGIN_LEFT = RESULT_W/24;
	public static final String[] RESULT_OPTION_TITLES = {" Inventario", " Alquilados", " Reparando"};
	public static final int BORDER_RESULT_SIZE = 3;
	public static final int RESULT_ACTION_PANE_W = (int) (RESULT_W/4);
	public static final int RESULT_ACTION_PANE_H = (int) (RESULT_H/1.5);
	public static final int RESULT_ACTION_PANE_X = RESULT_LIST_X+RESULT_LIST_W+(RESULT_W-(RESULT_LIST_X+RESULT_LIST_W+RESULT_ACTION_PANE_W))/2;
	public static final int RESULT_ACTION_PANE_Y = RESULT_H/2 - RESULT_ACTION_PANE_H/2;
	public static final int RESULT_ACTION_BUTTON_W = RESULT_ACTION_PANE_W;
	public static final int RESULT_ACTION_BUTTON_H = RESULT_ACTION_PANE_H/7;
	public static final int RESULT_ACTION_BUTTON_X = 0;
	public static final int RESULT_ACTION_BUTTON_Y = 0;	
	public static final Point RESULT_POINT = new Point(RESULT_X,RESULT_Y);
	//HERE ALL ABOUT USEROPTIONS PANEL
	public static final int USER_OPTIONS_W = W;
	public static final int USER_OPTIONS_H = H;
	public static final int USER_OPTIONS_X = 0;
	public static final int USER_OPTIONS_Y = 0;
	public static final int USER_OPTIONS_IMG_SIZE = W/5;
	public static final int USER_OPTIONS_IMG_X = USER_OPTIONS_W/10;
	public static final int USER_OPTIONS_IMG_Y = USER_OPTIONS_W/8;
	public static final int USER_OPTIONS_LABEL_W = USER_OPTIONS_W/2;
	public static final int USER_OPTIONS_LABEL_H = USER_OPTIONS_H/11;
	public static final int USER_OPTIONS_LABEL_X = USER_OPTIONS_IMG_X*2+USER_OPTIONS_IMG_SIZE;
	public static final int USER_OPTIONS_LABEL_Y = USER_OPTIONS_IMG_Y + USER_OPTIONS_IMG_SIZE/5;
	public static final int USER_OPTIONS_BUTTON_W = USER_OPTIONS_LABEL_W/2;
	public static final int USER_OPTIONS_BUTTON_H = USER_OPTIONS_LABEL_H;
	public static final int USER_OPTIONS_BUTTON_X = USER_OPTIONS_LABEL_X;
	public static final int USER_OPTIONS_BUTTON_Y = (int) (USER_OPTIONS_LABEL_Y+ USER_OPTIONS_LABEL_H + USER_OPTIONS_BUTTON_H/2.1);
	public static final int USER_OPTIONS_SESSION_X = USER_OPTIONS_BUTTON_X+USER_OPTIONS_BUTTON_W;	
	public static final String USER_TITLE_PATH = "USER_TITLE.png";
	public static final int USER_OPTIONS_TITLE_W = USER_OPTIONS_W/3;
	public static final int USER_OPTIONS_TITLE_H = USER_OPTIONS_H/7;
	public static final int USER_OPTIONS_TITLE_X = USER_OPTIONS_TITLE_W/27;
	public static final int USER_OPTIONS_TITLE_Y = USER_OPTIONS_TITLE_X;
	public static final Point USER_OPTIONS_POINT = new Point(USER_OPTIONS_X,USER_OPTIONS_Y);
	//HERE ALL ABOUT USER ADMINISTRATION PANEL
	public static final int USER_ADMINISTRATION_W = W;
	public static final int USER_ADMINISTRATION_H = H;
	public static final Point USER_ADMINISTRATION_POINT = new Point(0,0);
	//POSITIONS ARRAYS
	public static final Point[] SCENE1_POINTS = {LOGIN_POINT};
	public static final Point[] SCENE2_POINTS = {FILTER_POINT,SEARCH_POINT,RESULT_POINT,USER_POINT};
	public static final Point[] SCENE3_POINTS = {USER_OPTIONS_POINT};
	public static final Point[] SCENE4_POINTS = {USER_ADMINISTRATION_POINT};
	//HERE ALL ABOUT DB
	public static final String DB = "jdbc:sqlite:";
	public static final String DB_NAME = "vc.db";
	private static final String CREATE_USERS_TABLE = "CREATE TABLE 'USERS' ("+
			"'ID'				INTEGER PRIMARY KEY AUTOINCREMENT," +
			"'USERNAME'			TEXT,"+
			"'PASSWORD'			TEXT"+
			")";
	private static final String CREATE_ARTICLE_TABLE = "CREATE TABLE 'ARTICLES' (" + 
			"'ID'				INTEGER PRIMARY KEY AUTOINCREMENT," + 
			"'ARTICLE_TYPE'      TEXT," +
			"'NAME'				TEXT," + 
			"'DIRECTOR'			TEXT," + 
			"'YEAR'				INTEGER," + 
			"'MOVIE_TYPE'		TEXT," + 
			"'ANIMATION'		TEXT," + 
			"'SERIES_PARENT'    TEXT," +
			"'SEASON_PARENT'	TEXT," + 
			"'EPISODE_NUMBER'	INTEGER," + 
			"'STOCK'			INTEGER," + 
			"'RENTED'			INTEGER DEFAULT 0," + 
			"'REPAIRING'		INTEGER DEFAULT 0," + 
			"'VALUATION'		REAL DEFAULT 2.5" +
			")";
	public static final String[] CREATE_QUERYS = {CREATE_USERS_TABLE,CREATE_ARTICLE_TABLE};
	//Sé que está feo pero no he podido hacer más
	public static void showError() {
		 JOptionPane.showMessageDialog(null,"ERROR ", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
