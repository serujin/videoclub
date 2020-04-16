package view;

import java.awt.FontFormatException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constants.Constants;

public class View {
	private MainWindow mw;
	private Login l;
	private Filter f;
	private Search s;
	private Results r;
	private User u;
	private UserOptions o;
	private UsersAdministration a;
	private JComponent[] scene1 = new JComponent[1];
	private JComponent[] scene2 = new JComponent[4];
	private JComponent[] scene3 = new JComponent[1];
	private JComponent[] scene4 = new JComponent[1];
	public View() {
		try {
			Constants.initLabelsFont();
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		initLogin();
		mw.changeScene(scene1, Constants.SCENE1_POINTS);		
	}
	
	private void setSceneChanger(JButton button, JComponent[] scene, Point[] positions) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mw.changeScene(scene, positions);
			}
		});
	}
	
	private void initLogin() {
		mw = new MainWindow();
		l = new Login();
		scene1[0]=l;
	}
	
	public void loginSuccessful() {
		mw.changeScene(scene2, Constants.SCENE2_POINTS);
	}
	
	public void initAll(boolean admin) {
		f = new Filter();
		scene2[0]=f;
		s = new Search();
		scene2[1]=s;
		r = new Results();
		scene2[2]=r;
		u = new User(admin);
		scene2[3]=u;
		o = new UserOptions(admin);
		scene3[0]=o;
		a = new UsersAdministration();
		scene4[0]=a;
		initSceneChanger();
	}
	
	private void initSceneChanger() {
		setSceneChanger((JButton) u.getMyComponents()[1], scene3, Constants.SCENE3_POINTS);
		setSceneChanger((JButton) o.getMyComponents()[5], scene4, Constants.SCENE4_POINTS);
		setSceneChanger((JButton) o.getMyComponents()[6], scene2, Constants.SCENE2_POINTS);
		setSceneChanger((JButton) a.getMyComponents()[3], scene3, Constants.SCENE3_POINTS);
	}
	
	public MainWindow getFrame() {
		return mw;
	}

	public JTextField[] getLoginTextFields() {
		return l.getMyTextfields();
	}
	
	public JButton[] getLoginButton() {
		return l.getMyButtons();
	}

	public JComponent[] getFilterComponents() {
		return f.getMyComponents();
	}

	public JComponent[] getSearchComponents() {
		return s.getMyComponents();
	}

	public JComponent[] getResultComponents() {
		return r.getMyComponents();
	}

	public JComponent[] getUserComponents() {
		return u.getMyComponents();
	}

	public JComponent[] getUserOptionsComponents() {
		return o.getMyComponents();
	}

	public JComponent[] getUserAdministrationComponents() {
		return a.getMyComponents();
	}
	
	public void setUserLabels(String username, int totalUsers, boolean isAdmin) {
		u.setUsername(username);
		o.setUsername(username);
		o.setTotalUsers(totalUsers, isAdmin);
	}
}
