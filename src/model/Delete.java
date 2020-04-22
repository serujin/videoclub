package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import constants.Constants;
import javax.swing.JOptionPane;

public class Delete {
	Connection c;
	public Delete(Connection c) throws SQLException {
		this.c = c;
	}
	
	public void articles(int id) throws SQLException {
		if(id>0) {
			int option = JOptionPane.showConfirmDialog(null,"¿Realmente deseas eliminar este artículo?\nEl artículo desaparecerá del sistema junto con todas sus copias", "Eliminar artículo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(option==JOptionPane.YES_OPTION) {
				Statement s = c.createStatement();
				s.execute("DELETE FROM ARTICLES WHERE ID ="+id);
			}
		} else {
			Constants.showError();
		}
	}

	public void user(int id) throws SQLException {
		if(id>1) {
			int option = JOptionPane.showConfirmDialog(null,"¿Realmente deseas eliminar a este usuario?\nEl usuario desaparecerá del sistema", "Eliminar usuario", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(option==JOptionPane.YES_OPTION) {
				Statement s = c.createStatement();
				s.execute("DELETE FROM USERS WHERE ID ="+id);
			} 
		} else {
			if(id<0) {
				Constants.showError();
			} else {
				JOptionPane.showMessageDialog(null,"No puedes eliminarte a ti mismo", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
