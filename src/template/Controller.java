package template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import application.VideoClubApplication;
import constants.Constants;
import model.DataBase;
import model.Select;
import view.View;

public class Controller {
//This class should bridge graphics and database
	private View v;
	private DataBase db = null;
	private ArrayList<Integer> ids;
	private int loginTries;
	private static final String HELP_TEXT="<--- Búsqueda de artículos --->\n\n"
			+ "1º Usa el filtro para indicar al sistema que quieres buscar\n"
			+ "2º Inserta en la barra de búsqueda aquel elemento que quieras buscar\n"
			+ "3º Pulsa Intro o haz click en buscar para pedir al sistema los datos deseados\n"
			+ "4º Cambia entre stock, alquilados y reparándose para buscar donde deseas\n\n"
			+ "Sólo puedes aplicar un filtro de búsqueda a la vez\n"
			+ "Hay opciones diferentes según donde estés buscando el artículo (stock, alquilados y reparando)\n\n"
			+ "<--- Opciones de Usuario --->\n\n"
			+ "Opciones para todos los usuarios: Cambiar de contraseña, cerrar Sesión y añadir un artículo\n"
			+ "Opciones solo para administrador: Gestionar usuarios (añadir y eliminar usuarios) y ver usuarios totales\n\n"
			+ "<--- Añadir artículos --->\n\n"
			+ "Dentro de las opciones de usuario se muestra la ayuda correspondiente para ello\n\n"
			+ "<--- Actualizar artículos --->\n\n"
			+ "Selecciona el artículo a actualizar y pulsa el botón de actualizar";
	public Controller() {
		loginTries = 0;
		try {
			db = new DataBase();
		} catch (SQLException | IOException e) {
			Constants.showError();
		}
		v = new View();
		initLoginButton(v.getLoginButton()[0]);
	}
	
	private void initLoginButton(JButton b) {
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					loginAction();
				} catch (SQLException e1) {
					loginError();
				}
			}
		});
		
		v.getLoginTextFields()[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						loginAction();
					} catch (SQLException e1) {
						loginError();
					}
				}
			}
		});
	}
	
	private void initUpdateButton() {
		((JButton) v.getResultComponents()[7]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(getSelectedElements().length<2) {
						if(getSelectedElements().length>0) {
							db.update().article(ids.get(getSelectedElements()[0]));
							findResults();
						} else {
							JOptionPane.showMessageDialog(null,"Selecciona un elemento para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null,"Solo se puede actualizar un elemento a la vez", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private boolean login() throws SQLException {
		return db.select().login(v.getLoginTextFields()[0].getText(), v.getLoginTextFields()[1].getText());
	}
	
	private void loginError() {
		if(loginTries==3) {
			JOptionPane.showMessageDialog(null,"Intentos de inicio de sesión agotados\nEl programa se cerrará por seguridad","Login", JOptionPane.ERROR_MESSAGE);
			v.getFrame().dispatchEvent(new WindowEvent(v.getFrame(), WindowEvent.WINDOW_CLOSING));
			System.exit(0);
		}
		loginTries++;
		JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrecta\nIntentos restantes: "+(4-loginTries),"Login", JOptionPane.WARNING_MESSAGE);
	}
	
	private void loginAction() throws SQLException {
		boolean isAdmin = login();
		v.initAll(isAdmin);
		JOptionPane.showMessageDialog(null,"Bienvenido al sistema "+v.getLoginTextFields()[0].getText(),"Bienvenido", JOptionPane.PLAIN_MESSAGE);
		v.loginSuccessful();
		initAll(isAdmin);
	}

	private void initAll(boolean isAdmin) throws SQLException {
		v.setUserLabels(v.getLoginTextFields()[0].getText(),getTotalUsers(),isAdmin);
		initAddArticleButton();
		initChangeSessionButton();
		initHelpButton();
		initSearchArticleButton();
		initInventoryButton1();
		initInventoryButton2();
		initInventoryButton3();
		initRentedButton();
		initRepairButton();
		initResultOptionsSearch();
		initChangePasswordButton();
		initUserAdministrationButton();
		initAddUsersButton();
		initDelUsersButton();
		initUpdateButton();
	}
	
	private void initSearchArticleButton() {
		((JButton) v.getSearchComponents()[1]).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					findResults();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
			
		});
		
		((JTextField) v.getSearchComponents()[0]).addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						findResults();
					} catch (SQLException e1) {
						Constants.showError();
					}
				}
			}
		});
	}
	
	private void findResults() throws SQLException {
		ids = new ArrayList<>(); 
		String text = ((JTextField) v.getSearchComponents()[0]).getText();
		ArrayList<String> data = new ArrayList<>();
		if(!text.equals("") && filterRIsSelected()) {
			ArrayList<String> requestedData = db.select().articles(v.getFilterF(), v.getFilterR(), text);
			for(int i=0;i<requestedData.size();i++) {
				ids.add(Integer.parseInt(requestedData.get(i).split("--")[0]));
				data.add(FormatString.getString(requestedData.get(i), db.select(),v.getFilterF(), v.getFilterR(), text));
			}
		} else {
			data.add("Inserte un texto para buscar, seleccione la localización del artículo y pulse Enter");
			ids.add(-1);
		}
		if(data.size()==0) {
			data.add("No se encontratron resultados para la búsqueda: "+text);
			data.add("Si estás buscando por valoración introduce un punto en vez de una coma");
			ids.add(-1);
			ids.add(-1);
		}
		((JList) v.getResultComponents()[0]).setListData(data.toArray());
	}
	
	private int getTotalUsers() throws SQLException {
		return db.select().count("USERS");
	}
	
	private void initAddArticleButton() {
		((JButton) v.getUserOptionsComponents()[4]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db.insert().articles();
				} catch (SQLException | IOException e1) {
					JOptionPane.showMessageDialog(null,"Error irreparable en la base de datos\nEl programa se cerrará por seguridad","ERROR", JOptionPane.ERROR_MESSAGE);
					v.getFrame().dispatchEvent(new WindowEvent(v.getFrame(), WindowEvent.WINDOW_CLOSING));
					System.exit(0);
				}
			}
			
		});
	}
	
	private void initChangeSessionButton() {
		((JButton) v.getUserOptionsComponents()[3]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null,"¿Quieres iniciar sesión de nuevo?", "Inicio de sesión", JOptionPane.YES_NO_OPTION);
				v.getFrame().dispatchEvent(new WindowEvent(v.getFrame(), WindowEvent.WINDOW_CLOSING));
				if(option==JOptionPane.YES_OPTION) {
					VideoClubApplication.initController();
				} else {
					JOptionPane.showMessageDialog(null,"El programa se cerrará\n¡Hasta la próxima!","Cerrando aplicación", JOptionPane.PLAIN_MESSAGE);
					System.exit(0);
				}
				
			}
		});
	}
	
	private void initHelpButton() {
		((JButton) v.getFilterComponents()[0]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,HELP_TEXT,"Ayuda", JOptionPane.PLAIN_MESSAGE);
			}
		});
	}	

	private void initInventoryButton1() {
		((JButton) v.getResultComponents()[1]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for(int i : getSelectedElements()) {
						db.update().rentElement(ids.get(i));
					}
					findResults();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private void initInventoryButton2() {
		((JButton) v.getResultComponents()[2]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for(int i : getSelectedElements()) {
						db.update().repairElement(ids.get(i));
					}
					findResults();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private void initInventoryButton3() {
		((JButton) v.getResultComponents()[3]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for(int i : getSelectedElements()) {
						db.delete().articles(ids.get(i));
					}
					findResults();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private void initRentedButton() {
		((JButton) v.getResultComponents()[5]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for(int i : getSelectedElements()) { 
						db.update().returnElement(ids.get(i), v.getValuation());
					}
					findResults();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private void initRepairButton() {
		((JButton) v.getResultComponents()[6]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for(int i : getSelectedElements()) {
						db.update().restoreElement(ids.get(i));
					}
					findResults();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private int[] getSelectedElements() {
		return ((JList) v.getResultComponents()[0]).getSelectedIndices();
	}
	
	private int[] getSelectedUsers() {
		return ((JList) v.getUserAdministrationComponents()[0]).getSelectedIndices();
	}
	
	private void initResultOptionsSearch() {
		v.getOptions()[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					activateOption(0);
					findResults();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
		v.getOptions()[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					activateOption(1);
					findResults();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
		v.getOptions()[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					activateOption(2);
					findResults();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private void initChangePasswordButton() {
		((JButton) v.getUserOptionsComponents()[2]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db.update().password(db.select().getUserID(v.getUsername()));
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private void initUserAdministrationButton() {
		((JButton) v.getUserOptionsComponents()[5]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					searchUsers();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private void initDelUsersButton() {
		((JButton) v.getUserAdministrationComponents()[2]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for(int i : getSelectedUsers()) {
						db.delete().user(ids.get(i));
					}
					searchUsers();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private void initAddUsersButton() {
		((JButton) v.getUserAdministrationComponents()[1]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String user;
				String password;
				user = JOptionPane.showInputDialog("Introduce el nombre del usuario a añadir");
				password = JOptionPane.showInputDialog("Introduce la contraseña del usuario a añadir");
				try {
					db.insert().user(user, password);
					searchUsers();
				} catch (SQLException e1) {
					Constants.showError();
				}
			}
		});
	}
	
	private void searchUsers() throws SQLException {
		ids = new ArrayList<>(); 
		ArrayList<String> data = db.select().getAllUsers();
		ArrayList<String> usersData = new ArrayList<>();
		for(String s : data) {
			ids.add(Integer.parseInt(s.split("--")[0]));
			usersData.add(s.split("--")[1]);
		}
		((JList) v.getUserAdministrationComponents()[0]).setListData(usersData.toArray());
	}
	
	private void activateOption(int i) {
		if(i==0) {
			v.getResultComponents()[1].setEnabled(true);
			v.getResultComponents()[2].setEnabled(true);
			v.getResultComponents()[3].setEnabled(true);
			v.getResultComponents()[4].setEnabled(false);
			v.getResultComponents()[5].setEnabled(false);
			v.getResultComponents()[6].setEnabled(false);
			v.getResultComponents()[7].setEnabled(true);
		}
		if(i==1) {
			v.getResultComponents()[1].setEnabled(false);
			v.getResultComponents()[2].setEnabled(false);
			v.getResultComponents()[3].setEnabled(false);
			v.getResultComponents()[4].setEnabled(true);
			((JSlider) v.getResultComponents()[4]).setValue(25);
			v.getResultComponents()[5].setEnabled(false);
			v.getResultComponents()[6].setEnabled(false);
			v.getResultComponents()[7].setEnabled(false);
		}
		if(i==2) {
			v.getResultComponents()[1].setEnabled(false);
			v.getResultComponents()[2].setEnabled(false);
			v.getResultComponents()[3].setEnabled(false);
			v.getResultComponents()[4].setEnabled(false);
			v.getResultComponents()[5].setEnabled(false);
			v.getResultComponents()[6].setEnabled(true);
			v.getResultComponents()[7].setEnabled(false);
		}
		v.setFilterR(i);
	}
	
	private boolean filterRIsSelected() {
		boolean b = false;
		for(JRadioButton p : v.getOptions()) {
			if(p.isSelected()) {
				b = true;
			}
		}
		return b;
	}

}

class FormatString {
	/*
	 * Esta clase recibe el string de la consulta de la base de datos de la búsqueda y le da formato 
	 * 		al resultado para mostrarlo en pantalla
	 */
	public static String getString(String s, Select select, int filterF, int filterR, String text) throws SQLException {
		return getReturnData(s.split("--"), select, filterF, filterR, text);
	}
	/*
	 * Esta joyita de aquí se ocupa de formatear el espacio que hay en los resultados de la lista para que todo quede 
	 * 		ordenadito y bonico, estoy bastante contento de lo que he acabado consiguiendo :D
	 */
	private static String getReturnData(String[] sp, Select select, int filterF, int filterR, String text) throws SQLException {
		String returnData = "";
		int type = getType(sp[1]);
		String table = getTable(filterR); 
		int cOne = select.getMaxLength(filterF, 0, text);
		int cTwo = select.getMaxLength(filterF, 1, text);
		int cThree = select.getMaxLength(filterF, 2, text);
		int cFour = select.getMaxLength(filterF, 3, text);
		int cFive = select.getMaxLength(filterF, 4, text);
		int cSix = select.getMaxLength(filterF, 5, text);
		int cSeven = select.getMaxLength(filterF, 6, text);
		DecimalFormat df = new DecimalFormat("#.00");
		String sOne = "Capítulo";
		String sTwo = sp[3];
		String sThree = sp[4];
		String sFour = sp[3];
		String sFive = "";
		String sSix = "";
		String sSeven = table+sp[5];
		if(type==1) {
			sOne = "Película";
			sTwo = sp[4];
			sThree = sp[2];		
			sSix = df.format(Float.parseFloat(sp[sp.length-1]))+"/5,0";		
			sSeven = table+sp[sp.length-2];
		}
		if(type==2) {
			sOne = "Serie";
			sTwo = sp[4];
			sThree = sp[2];
			sSix = df.format(Float.parseFloat(sp[6]))+"/5,0";
		}
		if(type==3) {
			sOne = "Temporada";
			sFour = sp[2];
			sSix = df.format(Float.parseFloat(sp[6]))+"/5,0";
		}
		if(type==4) {
			sFour = sp[5];
			sFive = sp[2];
			sSix = df.format(Float.parseFloat(sp[7]))+"/5,0";
			sSeven = table+sp[6];
		}
		returnData += formatLength(sOne, cOne, false)+"|";
		returnData += formatLength(sTwo, cTwo, true)+"|";
		returnData += formatLength(sThree, cThree, true)+" |";
		returnData += formatLength(sFour, cFour, false)+"|";
		if(filterF==0 || filterF==3) {
			returnData += formatLength(sFive, cFive, false)+"|";
		}
		if(filterF==4 ||filterF==5) {
			if(filterF==4) {
				sFive=sp[5];
			} else {
				sFive=sp[6];
			}
			returnData += formatLength(sFive, cFive, false)+"|";
		} else {
			returnData += formatLength(sSix, cSix, false)+"|";
		}
		if(filterF==7) {
			returnData += formatLength(sFive, cFive, false)+"|";
			returnData += formatLength(sSix, cSix, false)+"|";
		}
		returnData += formatLength(sSeven, cSeven, false);
		return returnData;
	}

	private static String getTable(int filterR) {
		if(filterR==1) {
			return "Alquiladas ";
		}
		if(filterR==2) {
			return "Reparando ";
		}
		return "Stock ";
	}
	
	private static int getType(String type) {
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

	private static String formatLength(String s, int length, boolean endSpace) {
		String formatS = " "+s+" ";
		String pattern = " ";
		if(s.equals("")) {
			formatS = " ";
			length++;
		}
		for(int i=s.length();i<length;i++) {
			formatS += pattern;
		}
		return formatS;
	}
}
