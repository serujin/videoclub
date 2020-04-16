package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

import constants.Constants;

public class Results extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JList<Object> resultList;
	public JScrollPane resultPane;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private int selected;
	private Option o1;
	private Option o2;
	private Option o3;
	private Option[] options = {o1,o2,o3};
	private JButton inventory1;
	private JButton inventory2;
	private JButton inventory3;
	private JSlider points;
	private JButton rented;
	private JButton repair;
	
	public Results() {
		this.setSize(Constants.RESULT_W, Constants.RESULT_H);
		this.setBackground(Constants.BG_COLOR);
		this.setLayout(null);
		this.setName("resultcontainer");
		initActionPane();
		initResultList();
		initOptions();
	}
	
	private void initOptions() {
		ButtonGroup group = new ButtonGroup();
		for(int i=0; i<options.length; i++) {
			int margin = Constants.RESULT_OPTION_MARGIN_LEFT + Constants.RESULT_OPTION_MARGIN_LEFT*i;
			int x = (Constants.RESULT_OPTION_X+Constants.RESULT_OPTION_W)*i + margin;
			options[i] = new Option(i, x, Constants.RESULT_OPTION_Y, Constants.RESULT_OPTION_W, Constants.RESULT_OPTION_H, Constants.RESULT_OPTION_TITLES);
			options[i].changeColor(Constants.BG_COLOR);
			addListener(i);
			group.add(options[i]);
			this.add(options[i]);
		}
		options[0].setSelected(true);
		selected = 0;
		activateOption(0);
	}

	private void addListener(int i) {
		options[i].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(options[i].isSelected()) {
					activateOption(i);
				}
			}
		});
	}
	
	private void addPointsListener(JSlider s) {
		s.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if(s.isEnabled()) {
					rented.setEnabled(true);
				}
			}
			
			@Override 
			public void mouseExited(MouseEvent e) {
				if(s.isEnabled()) {
					System.out.println(s.getValue());
				}
			}
		});
	}

	private void activateOption(int i) {
		if(i==0) {
			inventory1.setEnabled(true);
			inventory2.setEnabled(true);
			inventory3.setEnabled(true);
			points.setEnabled(false);
			rented.setEnabled(false);
			repair.setEnabled(false);
		}
		if(i==1) {
			inventory1.setEnabled(false);
			inventory2.setEnabled(false);
			inventory3.setEnabled(false);
			points.setEnabled(true);
			points.setValue(25);
			rented.setEnabled(false);
			repair.setEnabled(false);
		}
		if(i==2) {
			inventory1.setEnabled(false);
			inventory2.setEnabled(false);
			inventory3.setEnabled(false);
			points.setEnabled(false);
			rented.setEnabled(false);
			repair.setEnabled(true);
		}
		selected = i;
	}
	
	private void initResultList() {
		String[] a = new String[120];
		for(int i=0;i<a.length;i++) { //20, 30
			a[i]="AAAAAAAAAAAAAAAAAAAA | AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA | 1234 | Ciencia-Ficción | Stop-Motion | 3.5";
		}
		resultPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		resultList = new JList<>(a);
		resultList.setFont(Constants.resultFont);
		resultList.setBackground(Constants.BG_COLOR);
		resultList.setForeground(Color.WHITE);
		resultPane.setViewportView(resultList);
		resultList.setCellRenderer(new SelectedListCellRenderer());
		resultPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, Constants.RESULT_LIST_H));
		resultPane.setSize(Constants.RESULT_LIST_W, Constants.RESULT_LIST_H);
		resultPane.setLocation(Constants.RESULT_LIST_X, Constants.RESULT_LIST_Y);
		resultPane.setBackground(Constants.BG_COLOR);
		resultPane.setBorder(new LineBorder(Color.BLACK, Constants.BORDER_RESULT_SIZE));
		this.add(resultPane);
	}
	
	private void initActionPane() {
		p1 = new JPanel();
		p1.setBackground(Color.BLACK);
		p1.setSize(Constants.RESULT_ACTION_PANE_W, Constants.RESULT_ACTION_PANE_H);
		p1.setLocation(Constants.RESULT_ACTION_PANE_X, Constants.RESULT_ACTION_PANE_Y);
		p1.setLayout(null);	
		inventory1 = new JButton("Alquilar los artículos seleccionados");
		inventory2 = new JButton("Reparar los artículos seleccionados");
		inventory3 = new JButton("Eliminar los artículos seleccionados");
		rented = new JButton("Puntuar y devolver los artículos seleccionados");
		repair = new JButton("Marcar como reparados los artículos seleccionados");
		formatButton(inventory1,0);
		formatButton(inventory2,1);
		formatButton(inventory3,2);
		formatButton(rented,4);
		formatButton(repair,5);
		formatPoints();
		initPointsLabels();
		p1.add(points);
		p1.validate();	
		this.add(p1);
	}

	private void initPointsLabels() {
		Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for(float i=0f; i<51f; i+=1f) {
        	if(i%5==0) {
        		labels.put((int) i, new JLabel(String.valueOf(i/10)));
        	}
        }
        points.setLabelTable(labels);

        points.setPaintLabels(true);
	}

	private void formatPoints() {
		points = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
		points.setMinorTickSpacing(1);
        points.setMajorTickSpacing(5);
        points.setPaintTicks(true);
		points.setSize(Constants.RESULT_ACTION_BUTTON_W, Constants.RESULT_ACTION_BUTTON_H);
		points.setLocation(Constants.RESULT_ACTION_BUTTON_X, (Constants.RESULT_ACTION_BUTTON_H*3)+Constants.RESULT_ACTION_BUTTON_Y);
		points.setEnabled(false);
		points.setBorder(new PointsBorder(null, "Puntuación", 0, 0, null));
		points.setPaintTrack(false);
		addPointsListener(points);
	}
	
	private void formatButton(JButton b, int row) {
		b.setSize(Constants.RESULT_ACTION_BUTTON_W, Constants.RESULT_ACTION_BUTTON_H);
		b.setLocation(Constants.RESULT_ACTION_BUTTON_X, (Constants.RESULT_ACTION_BUTTON_H*row)+Constants.RESULT_ACTION_BUTTON_Y);
		b.setFont(Constants.actionButtonFont);
		b.setFocusPainted(false);
		b.setEnabled(false);
		b.setBorder(null);
		p1.add(b);
	}
	
	public int getSelected() {
		return selected;
	}

	public JComponent[] getMyComponents() {
		return new JComponent[] {resultList,inventory1,inventory2,inventory3,points,rented,repair};
	}

} 

class SelectedListCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (isSelected) {
            c.setBackground(Color.RED);
            c.setForeground(Constants.BG_COLOR);
        }
        return c;
    }
}

class PointsBorder extends TitledBorder {
	private static final long serialVersionUID = 1L;

	public PointsBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont) {
		super(border, title, titleJustification, titlePosition, titleFont);
		this.setTitleFont(Constants.pointsFont);
	}
}
