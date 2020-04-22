package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import constants.Constants;

public class UsersAdministration extends JPanel {
	private JPanel p;
	private JList<String> users;
	private JButton addUser;
	private JButton delUser;
	private JButton back;
	public UsersAdministration() {
		this.setSize(Constants.USER_ADMINISTRATION_W, Constants.USER_ADMINISTRATION_H);
		this.setBackground(Constants.BG_COLOR);
		this.setLayout(null);
		this.setName("useradministration");
		initTitle();
		initUsersList();
		initActionPane();
		initBack();
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
	
	private void initUsersList() {
		JScrollPane resultPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		users = new JList<>();
		users.setFont(Constants.userAdminListFont);
		users.setBackground(Constants.BG_COLOR);
		users.setForeground(Color.WHITE);
		resultPane.setViewportView(users);
		users.setCellRenderer(new SelectedListCellRenderer());
		resultPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, Constants.RESULT_LIST_H));
		resultPane.setSize(Constants.RESULT_LIST_W, Constants.RESULT_LIST_H);
		resultPane.setLocation(Constants.RESULT_LIST_X, (int) (Constants.H/1.8-Constants.RESULT_LIST_H/2.0));
		resultPane.setBackground(Constants.BG_COLOR);
		resultPane.setBorder(new LineBorder(Color.BLACK, Constants.BORDER_RESULT_SIZE));
		this.add(resultPane);
	}
	
	private void initActionPane() {
		p = new JPanel();
		p.setBackground(Color.BLACK);
		p.setSize(Constants.RESULT_ACTION_PANE_W, Constants.RESULT_ACTION_PANE_H/2);
		p.setLocation((int) (Constants.RESULT_ACTION_PANE_X*1.1), (int) (Constants.H/1.8-Constants.RESULT_ACTION_PANE_H/4.0));
		p.setLayout(null);	
		addUser = new JButton("Añadir un usuario");
		delUser = new JButton("Eliminar los usuarios seleccionados");
		formatButton(addUser,0);
		formatButton(delUser,1);
		p.validate();	
		this.add(p);
	}
	
	private void formatButton(JButton b, int row) {
		b.setSize(Constants.RESULT_ACTION_BUTTON_W, Constants.RESULT_ACTION_PANE_H/4);
		b.setLocation(Constants.RESULT_ACTION_BUTTON_X, (Constants.RESULT_ACTION_PANE_H/4*row));
		b.setFont(Constants.insideUserAdminButtonsFont);
		b.setFocusPainted(false);
		b.setBorder(null);
		p.add(b);
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
		return new JComponent[] {users,addUser,delUser,back};
	}
}
