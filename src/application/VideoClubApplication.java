package application;

import template.Controller;

public class VideoClubApplication {
	static Controller c;
	public static void main(String[] args) {
		initController();
	}
	public static void initController() {
		c = new Controller();
	}
}


