package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import constants.Constants;

public class Login extends JPanel {
	private JTextField username;
	private JPasswordField password;
	private JButton submit;
	public Login() {
		this.setSize(Constants.LOGIN_W,Constants.LOGIN_H);
		this.setBackground(Constants.BG_COLOR);
		this.setLayout(null);
		this.setName("login");
		initLogo();
		initLogin();
		initUsername();
		initPassword();
		initSubmit();
	}
	
	private void initLogo() {
		Logo logo = new Logo(Constants.LOGIN_LOGO_SIZE);
		logo.setLocation(Constants.LOGIN_LOGO_X, Constants.LOGIN_LOGO_Y);
		this.add(logo);
	}
	
	private void initLogin() {
		JLabel login = new JLabel();
		login.setSize(Constants.LOGIN_IMG_W, Constants.LOGIN_IMG_H);
		login.setLocation(Constants.LOGIN_IMG_X, Constants.LOGIN_IMG_Y);
		ImageIcon iconImg = new ImageIcon(getClass().getClassLoader().getResource(Constants.LOGIN_IMG_PATH));
		Icon icon = new ImageIcon(iconImg.getImage().getScaledInstance(login.getWidth(), login.getHeight(), Image.SCALE_SMOOTH));
		login.setIcon(icon);
		this.add(login);
	}
	
	private void initUsername() {
		username = new JTextField("Username");
		username.setSize(Constants.INPUT_W, Constants.INPUT_H);
		username.setLocation(Constants.INPUT_X,Constants.INPUT_1_Y);
		username.addMouseListener(new Listener(username,username.getText()));
		this.add(username);
	}
	
	private void initPassword() {
		password = new JPasswordField("A3cf00ksldmm2DD`C");
		password.setSize(Constants.INPUT_W, Constants.INPUT_H);
		password.setLocation(Constants.INPUT_X, Constants.INPUT_2_Y);
		password.addMouseListener(new Listener(password,password.getText()));
		this.add(password);
	}
	
	private void initSubmit() {
		submit = new JButton("Login");
		submit.setSize(Constants.SUBMIT_W, Constants.SUBMIT_H);
		submit.setLocation(Constants.SUBMIT_X, Constants.SUBMIT_Y);
		this.add(submit);
	}	
	
	public JTextField[] getMyTextfields() {
		return new JTextField[] {username, password};
	}
	
	public JButton[] getMyButtons() {
		return new JButton[] {submit};
	}
}

class Listener extends MouseAdapter {
	private JTextField t;
	private String text;
	public Listener(JTextField t, String text) {
		this.t = t;
		this.text = text;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(t.getText().equals(text)) {
			t.setText("");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(t.getText().equals("")) {
			t.setText(text);
		}
	}
	
}
