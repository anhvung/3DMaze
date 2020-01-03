package game;

import View.StartMenu;

public class MainExecuteHere {
	public static final String source = "src/data/3Dmaze.txt";

	public static void main() {
		System.out.println("laby stored at : " + source);
		new StartMenu();

	}

}
