package model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import constants.Constants;

public class DataBase {
	private Connection c;
	private File db;
	private Insert insert;
	private Select select;

//This class should hold the database of the program
	public DataBase() throws SQLException {
		db = new File(Constants.DB_NAME);
		if (!db.exists()) {
			createDB();
		} else {
			initDB();
		}
	}

	public Insert insert() {
		return insert;
	}

	public Select select() {
		return select;
	}

	private void createDB() throws SQLException {
		connectDB();
		insert = new Insert(c);
		select = new Select(c);
		createTables();
		initAdmin();
	}

	private void createTables() throws SQLException {
		Statement s = c.createStatement();
		for (String query : Constants.CREATE_QUERYS) {
			s.execute(query);
		}
	}

	private void initDB() throws SQLException {
		connectDB();
		insert = new Insert(c);
		select = new Select(c);
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
}
