package view;

import java.awt.FontFormatException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import constants.Constants;

public class View {
	/*
	 * Esta clase es como la clase "padre" de toda la GUI, organiza, cambia e inicializa todas las escenas y la ventana
	 * 
	 * Una vez que se ha hecho login correctamente se inicia todo (se encarga de ello la clase Controller)
	 * 
	 */
	private MainWindow mw;
	private Login l;
	private Filter f;
	private Search s;
	private Results r;
	private User u;
	private UserOptions o;
	private UsersAdministration a;
	private int selected;
	private float valuation;
	private JComponent[] scene1 = new JComponent[1];
	private JComponent[] scene2 = new JComponent[4];
	private JComponent[] scene3 = new JComponent[1];
	private JComponent[] scene4 = new JComponent[1];
	public View() {
		try {
			Constants.initLabelsFont();
		} catch (FontFormatException | IOException e) {
			Constants.showError();
		}
		initLogin();
		mw.changeScene(scene1, Constants.SCENE1_POINTS);
		selected = 0;
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
	
	public String getUsername() {
		return u.getUsername();
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
		addPointsListener();
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
	
	public int getFilterF() {
		return f.getFilter();
	}
	
	public int getFilterR() {
		return selected;
	}
	
	public float getValuation() {
		return valuation/10;
	}
	
	public void setFilterR(int i) {
		selected = i;
	}
	
	private void addPointsListener() {
		getResultComponents()[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(getResultComponents()[4].isEnabled()) {
					getResultComponents()[5].setEnabled(true);
				}
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				if(getResultComponents()[4].isEnabled()) {
					valuation = ((JSlider) getResultComponents()[4]).getValue();
				}
			}
		});
	}
	
	public JRadioButton[] getOptions() {
		return r.getOptions();
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
