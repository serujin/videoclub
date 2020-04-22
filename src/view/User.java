package view;

import java.awt.Color;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import constants.Constants;

public class User extends JPanel {
	private JLabel name;
	private JButton options;
	public User(boolean isAdmin) {
		this.setSize(Constants.USER_W,Constants.USER_H);
		this.setBackground(Constants.BG_COLOR);
		this.setLayout(null);
		this.setName("user");
		initLabels(isAdmin);
		initUserLogo();
		initButton();
	}
	
	private void initLabels(boolean isAdmin) {
		name = new JLabel();
		if(isAdmin) {
			JLabel admin = new JLabel("Administrador");
			format(admin,Constants.USERNAME_X,Constants.ADMIN_Y);
		}
		format(name,Constants.USERNAME_X,Constants.USERNAME_Y);
	}
	
	public String getUsername() {
		return name.getText();
	}
	
	private void format(JLabel l, int x, int y) {
		l.setSize(Constants.USER_LABEL_W, Constants.USER_LABEL_H);
		l.setLocation(x, y);
		l.setForeground(Color.WHITE);
		l.setFont(Constants.userFont);
		this.add(l);
	}
	
	private void initUserLogo() {
		JLabel l = new JLabel();
		l.setSize(Constants.USER_IMG_SIZE,Constants.USER_IMG_SIZE);
		ImageIcon iconImg = new ImageIcon(getClass().getClassLoader().getResource(Constants.USER_IMG_PATH));
		Icon icon = new ImageIcon(iconImg.getImage().getScaledInstance(Constants.USER_IMG_SIZE, Constants.USER_IMG_SIZE, Image.SCALE_SMOOTH));
		l.setIcon(icon);
		l.setLocation(Constants.USER_PADDING, Constants.USER_PADDING);
		this.add(l);
	}
	
	private void initButton() {
		options = new JButton("Opciones");
		options.setSize(Constants.OPT_BUTTON_W, Constants.USER_LABEL_H);
		options.setLocation(Constants.USERNAME_X, Constants.OPT_BUTTON_Y);	
		options.setFont(Constants.userOptionsFont);
		options.setFocusPainted(false);
		this.add(options);
	}
	
	public void setUsername(String s) {
		this.name.setText(s);
	}
	
	public JComponent[] getMyComponents() {
		return new JComponent[] {name,options};
	}
}
