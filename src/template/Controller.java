package template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import application.VideoClubApplication;
import model.DataBase;
import view.View;

public class Controller {
//This class should bridge graphics and database
	private View v;
	private DataBase db = null;
	private int loginTries;
	
	public Controller() {
		loginTries = 0;
		try {
			db = new DataBase();
		} catch (SQLException e) {
			e.printStackTrace();
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
		v.setUserLabels(v.getLoginTextFields()[0].getText(),getTotalUsers(),isAdmin);
		initAddArticleButton();
		initChangeSessionButton();
		initHelpButton();
		initSearchButton();
	}
	
	private int getTotalUsers() throws SQLException {
		return db.select().countUsers();
	}
	
	private void initAddArticleButton() {
		((JButton) v.getUserOptionsComponents()[4]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db.insert().article();
				} catch (SQLException e1) {
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
		String helpText;
		helpText="<--- Búsqueda de artículos --->\n\n"
				+ "1º Usa el filtro para indicar al sistema que quieres buscar\n"
				+ "2º Inserta en la barra de búsqueda aquel elemento que quieras buscar\n"
				+ "3º Haz click en buscar para pedir al sistema los datos deseados\n"
				+ "4º Cambia entre stock, alquilados y reparándose para buscar donde deseas\n\n"
				+ "Sólo puedes aplicar un filtro de búsqueda a la vez\n"
				+ "Hay opciones diferentes según donde estés buscando el artículo (stock, alquilados y reparando)\n\n"
				+ "<--- Opciones de Usuario --->\n\n"
				+ "Opciones para todos los usuarios: Cambiar de contraseña, cerrar Sesión y añadir un artículo\n"
				+ "Opciones solo para administrador: Gestionar usuarios (añadir y eliminar usuarios) y ver usuarios totales";
		((JButton) v.getFilterComponents()[0]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,helpText,"Ayuda", JOptionPane.PLAIN_MESSAGE);
			}
		});
	}
	
	private void initSearchButton() {
		((JButton) v.getSearchComponents()[1]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
}
