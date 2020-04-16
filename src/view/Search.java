package view;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import constants.Constants;

public class Search extends JPanel {
	private JTextField bar;
	private JButton submit;
	public Search() {
		this.setSize(Constants.SEARCH_W, Constants.SEARCH_H);
		this.setLayout(null);
		this.setBackground(Constants.BG_COLOR);
		this.setName("search");
		initText();
		initBar();
		initButton();
	}
	
	private void initText() {
		JLabel l = new JLabel();
		l.setSize(Constants.SEARCH_TEXT_W,Constants.SEARCH_TEXT_H);
		ImageIcon iconImg = new ImageIcon(getClass().getClassLoader().getResource(Constants.SEARCH_TEXT_PATH));
		Icon icon = new ImageIcon(iconImg.getImage().getScaledInstance(Constants.SEARCH_TEXT_W, Constants.SEARCH_TEXT_H, Image.SCALE_SMOOTH));
		l.setIcon(icon);
		l.setLocation(Constants.SEARCH_TEXT_X, Constants.SEARCH_TEXT_Y);
		this.add(l);
	}
	
	private void initBar() {
		bar = new JTextField();
		bar.setSize(Constants.SEARCH_BAR_W, Constants.SEARCH_BAR_H);
		bar.setLocation(Constants.SEARCH_BAR_X, Constants.SEARCH_BAR_Y);
		this.add(bar);
	}
	
	private void initButton() {
		submit = new JButton("Buscar");
		submit.setSize(Constants.SEARCH_BUTTON_W, Constants.SEARCH_BUTTON_H);
		submit.setLocation(Constants.SEARCH_BUTTON_X, Constants.SEARCH_BUTTON_Y);
		submit.setFont(Constants.userFont);
		submit.setFocusPainted(false);
		this.add(submit);
	}
	
	public JComponent[] getMyComponents() {
		return new JComponent[] {bar,submit};
	}
}
