package view;

import java.awt.Color;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import constants.Constants;

public class UserOptions extends JPanel {
	private JLabel username;
	private JLabel totalUsersLabel;
	private JButton changePassword;
	private JButton changeSession;
	private JButton addElement;
	private JButton userAdmin;
	private JButton back;
	public UserOptions(boolean isAdmin) {
		this.setSize(Constants.USER_OPTIONS_W,Constants.USER_OPTIONS_H);
		this.setBackground(Constants.BG_COLOR);
		this.setLayout(null);
		this.setName("useroptions");
		initTitle();
		initUserImg();
		initLabels();
		initButtons();
		initBack();
		initAdmin(isAdmin);
	}
	
	private void initTitle() {
		JLabel l = new JLabel();
		l.setSize(Constants.USER_OPTIONS_TITLE_W, Constants.USER_OPTIONS_TITLE_H);
		ImageIcon iconImg = new ImageIcon(getClass().getClassLoader().getResource(Constants.USER_TITLE_PATH));
		Icon icon = new ImageIcon(iconImg.getImage().getScaledInstance(Constants.USER_OPTIONS_TITLE_W, Constants.USER_OPTIONS_TITLE_H, Image.SCALE_SMOOTH));
		l.setIcon(icon);
		l.setLocation(Constants.USER_OPTIONS_TITLE_X, Constants.USER_OPTIONS_TITLE_Y);
		this.add(l);
	}
	
	private void initUserImg() {
		JLabel l = new JLabel();
		l.setSize(Constants.USER_OPTIONS_IMG_SIZE,Constants.USER_OPTIONS_IMG_SIZE);
		ImageIcon iconImg = new ImageIcon(getClass().getClassLoader().getResource(Constants.USER_IMG_PATH));
		Icon icon = new ImageIcon(iconImg.getImage().getScaledInstance(Constants.USER_OPTIONS_IMG_SIZE, Constants.USER_OPTIONS_IMG_SIZE, Image.SCALE_SMOOTH));
		l.setIcon(icon);
		l.setLocation(Constants.USER_OPTIONS_IMG_X, Constants.USER_OPTIONS_IMG_Y);
		this.add(l);
	}
	
	private void initLabels() {
		username = new JLabel();
		username.setSize(Constants.USER_OPTIONS_LABEL_W, Constants.USER_OPTIONS_LABEL_H);
		username.setLocation(Constants.USER_OPTIONS_LABEL_X, Constants.USER_OPTIONS_LABEL_Y);
		username.setForeground(Color.WHITE);
		username.setFont(Constants.insideUserLabelsFont);
		this.add(username);
	}
	
	private void initButtons() {
		changePassword = new JButton("Cambiar de contraseña");
		changeSession = new JButton("Cerrar sesión");
		addElement = new JButton("Añadir un artículo");
		changePassword.setSize(Constants.USER_OPTIONS_BUTTON_W, Constants.USER_OPTIONS_BUTTON_H);
		changePassword.setLocation(Constants.USER_OPTIONS_BUTTON_X, Constants.USER_OPTIONS_BUTTON_Y);
		changePassword.setFont(Constants.insideUserButtonsFont);
		changeSession.setSize(Constants.USER_OPTIONS_BUTTON_W, Constants.USER_OPTIONS_BUTTON_H);
		changeSession.setLocation(Constants.USER_OPTIONS_SESSION_X, Constants.USER_OPTIONS_BUTTON_Y);
		changeSession.setFont(Constants.insideUserButtonsFont);
		addElement.setSize(Constants.USER_OPTIONS_BUTTON_W, Constants.USER_OPTIONS_BUTTON_H);
		addElement.setLocation(Constants.USER_OPTIONS_SESSION_X-Constants.USER_OPTIONS_BUTTON_W/2,Constants.USER_OPTIONS_BUTTON_Y+Constants.USER_OPTIONS_BUTTON_H);
		addElement.setFont(Constants.insideUserButtonsFont);
		this.add(changePassword);
		this.add(changeSession);
		this.add(addElement);
	}
	
	private void initAdmin(boolean isAdmin) {
		totalUsersLabel = new JLabel("X");
		userAdmin = new JButton("Gestión de Usuarios");
		JLabel l = new JLabel("Usuarios totales en el sistema: ");
		userAdmin.setSize(Constants.USER_OPTIONS_BUTTON_W,Constants.USER_OPTIONS_BUTTON_H);
		userAdmin.setLocation(Constants.USER_OPTIONS_IMG_X*2, (int) (Constants.USER_OPTIONS_BUTTON_Y*1.9));
		userAdmin.setFont(Constants.insideUserButtonsFont);
		l.setSize((int) (Constants.USER_OPTIONS_BUTTON_W*1.5),Constants.USER_OPTIONS_BUTTON_H);
		l.setLocation((int) (Constants.USER_OPTIONS_IMG_X*2+Constants.USER_OPTIONS_BUTTON_W*1.1), (int) (Constants.USER_OPTIONS_BUTTON_Y*1.9));
		l.setFont(Constants.insideUserButtonsFont);
		l.setForeground(Color.WHITE);
		totalUsersLabel.setSize(Constants.USER_OPTIONS_BUTTON_W/5,Constants.USER_OPTIONS_BUTTON_H);
		totalUsersLabel.setLocation((int) (Constants.USER_OPTIONS_IMG_X*2+Constants.USER_OPTIONS_BUTTON_W*2.23), (int) (Constants.USER_OPTIONS_BUTTON_Y*1.905));
		totalUsersLabel.setFont(Constants.insideUserButtonsFont);
		totalUsersLabel.setForeground(Color.WHITE);
		if(!isAdmin) {
			userAdmin.setEnabled(false);
		}
		this.add(userAdmin);
		this.add(l);
		this.add(totalUsersLabel);
	}
	
	private void initBack() {
		back = new JButton("Volver");
		back.setSize(Constants.W/8, Constants.RESULT_ACTION_PANE_H/4);
		back.setLocation((int) (Constants.W-Constants.W/7.5), Constants.H-Constants.RESULT_ACTION_PANE_H/3);
		back.setFont(Constants.insideUserButtonsFont);
		back.setFocusPainted(false);
		back.setBorder(null);
		this.add(back);
	}
	
	public JComponent[] getMyComponents() {
		return new JComponent[] {username,totalUsersLabel,changePassword,changeSession,addElement,userAdmin,back};
	}
	
	public void setUsername(String s) {
		this.username.setText(s);
	}
	
	public void setTotalUsers(int i, boolean isAdmin) {
		if(isAdmin) {
			this.totalUsersLabel.setText(String.valueOf(i));
		}
	}
}
