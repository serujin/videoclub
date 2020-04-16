package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import constants.Constants;

public class Filter extends JPanel {
	private int selected;
	private Option o1;
	private Option o2;
	private Option o3;
	private Option o4;
	private Option o5;
	private Option o6;
	private Option o7;
	private Option o8;
	private Option[] options = {o1,o2,o3,o4,o5,o6,o7,o8};
	private JButton help;
	public Filter() {
		this.setSize(Constants.FILTER_W,Constants.FILTER_H);
		this.setBackground(Constants.BG_COLOR);
		this.setLayout(null);
		this.setName("filter");
		initText();
		initLogo();
		initOptions();
		initHelp();
		options[0].setSelected(true);
	}
	
	private void initText() {
		JLabel l = new JLabel();
		l.setSize(Constants.FILTER_TEXT_W,Constants.FILTER_TEXT_H);
		ImageIcon iconImg = new ImageIcon(getClass().getClassLoader().getResource(Constants.FILTER_TEXT_PATH));
		Icon icon = new ImageIcon(iconImg.getImage().getScaledInstance(Constants.FILTER_TEXT_W, Constants.FILTER_TEXT_H, Image.SCALE_SMOOTH));
		l.setIcon(icon);
		l.setLocation(Constants.FILTER_TEXT_X, Constants.FILTER_TEXT_Y);
		this.add(l);
	}
	
	private void initLogo() {
		Logo logo = new Logo(Constants.FILTER_LOGO_SIZE);
		logo.setLocation(Constants.FILTER_LOGO_X, Constants.FILTER_LOGO_Y);
		this.add(logo);
	}
	
	private void initOptions() {
		ButtonGroup group = new ButtonGroup();
		for(int i=0; i<options.length; i++) {
			int y = Constants.OPTION_Y+(Constants.OPTION_H+Constants.OPTION_MARGIN_TOP)*i;
			options[i] = new Option(i, Constants.OPTION_X, y, Constants.OPTION_W, Constants.OPTION_H, Constants.OPTION_TITLES);
			addListener(i);
			group.add(options[i]);
			this.add(options[i]);
		}
	}

	private void addListener(int i) {
		options[i].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(options[i].isSelected()) {
					selected=i;
				}
			}
		});
	}
	
	private void initHelp() {
		help = new JButton("Ayuda");
		help.setSize(Constants.HELP_BUTTON_W, Constants.HELP_BUTTON_H);
		help.setLocation(Constants.HELP_BUTTON_X, Constants.HELP_BUTTON_Y);
		help.setFont(Constants.helpFont);
		help.setFocusPainted(false);
		this.add(help);
	}
	
	public int getFilter() {
		return selected;
	}

	public JComponent[] getMyComponents() {
		return new JComponent[] {help};
	}
	
}

class Option extends JRadioButton {
	public Option(int i, int x, int y, int w, int h, String[] options) {
		this.setSize(w,h);
		this.setLocation(x,y);
		this.setBackground(new Color(99,0,0));
		this.setText(options[i]);
		this.setFont(Constants.filterFont);
		this.setForeground(Color.WHITE);
		this.setFocusPainted(false);
	}
	public void changeColor(Color c) {
		this.setBackground(c);
	}
}

