package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import constants.Constants;

public class MainWindow extends JFrame {
	JLabel login;
	public MainWindow() {
		this.setSize(new Dimension(Constants.W, Constants.H));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle(Constants.WINDOW_TITLE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setLayout(null);
	}
	
	private void initBg(JComponent c, int x, int y) {
		login = new JLabel();
		login.setSize(Constants.W, Constants.H);
		login.setLocation(0, 0);
		ImageIcon iconImg = new ImageIcon(getClass().getClassLoader().getResource(Constants.LOGIN_BG_PATH));
		Icon icon = new ImageIcon(iconImg.getImage().getScaledInstance(login.getWidth(), login.getHeight(), Image.SCALE_SMOOTH));
		login.setIcon(icon);
		this.getContentPane().add(login);
		login.add(c);
		c.setLocation(x, y);
		c.validate();
		this.repaint();
	}
	
	private void addComponent(JComponent c, int x, int y) {
		if(c.getName().equals("login")) {
			initBg(c,x,y);
		} else {
			this.getContentPane().add(c);
			c.setLocation(x, y);
			c.validate();
			this.repaint();
		}
	}
	
	private void removeComponents() {
		this.getContentPane().removeAll();
	}
	
	public void changeScene(JComponent[] components, Point[] positions) {
		removeComponents();
		for(int i=0;i<components.length;i++) {
			addComponent(components[i], positions[i].x, positions[i].y);
		}
	}
}
