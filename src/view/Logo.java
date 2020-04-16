package view;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Image;
import constants.Constants;

public class Logo extends JLabel {
	public Logo(int size) {
		setSize(size,size);
		ImageIcon iconImg = new ImageIcon(getClass().getClassLoader().getResource(Constants.LOGO_PATH));
		Icon icon = new ImageIcon(iconImg.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
		setIcon(icon);
	}
}
