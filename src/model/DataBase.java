package model;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import constants.Constants;

public class DataBase {
	private Connection c;
	private Insert insert;
	private Select select;
	private Update update;
	private Delete delete;
	
	/*
	 * 
	 * Esta clase es la clase "padre" de la base de datos, se encarga de organizar, crear, actualizar
	 * 		y mimar la base de datos 
	 * 
	 */
	public DataBase() throws SQLException, IOException {
		File db = new File(Constants.DB_NAME);
		if (!db.exists()) {
			createDB();
		} else {
			initDB();
		}
	}

	private void createDB() throws SQLException, IOException {
		connectDB();
		createTables();
		insert = new Insert(c);
		select = new Select(c);
		update = new Update(c);
		delete = new Delete(c);
		initAdmin();
	}

	private void createTables() throws SQLException {
		Statement s = c.createStatement();
		for (String query : Constants.CREATE_QUERYS) {
			s.execute(query);
		}
	}

	private void initDB() throws SQLException, IOException {
		connectDB();
		insert = new Insert(c);
		select = new Select(c);
		update = new Update(c);
		delete = new Delete(c);
	}

	private void initAdmin() throws SQLException {
		String adminUser;
		String adminPassword;
		adminUser = JOptionPane.showInputDialog(
				"Introduce el usuario del administrador del sistema, no podrá ser cambiado en un futuro");
		adminPassword = JOptionPane.showInputDialog(
				"Introduce la contraseña del administrador del sistema, podrá cambiarla mas adelante si es necesario");
		insert.user(adminUser, adminPassword);
	}

	private void connectDB() throws SQLException {
		c = DriverManager.getConnection(Constants.DB + Constants.DB_NAME);
	}

	public Insert insert() {
		return insert;
	}

	public Select select() {
		return select;
	}
	
	public Update update() {
		return update;
	}
	
	public Delete delete() {
		return delete;
	}
}
