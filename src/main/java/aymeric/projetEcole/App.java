package aymeric.projetEcole;

import java.awt.EventQueue;

import controller.Controller;
import ihm.IhmMain;

/**
 * Hello world!
 *
 */
public class App {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controller ctrl = new Controller();
					IhmMain window = new IhmMain(ctrl);
					ctrl.setIhm(window);
					window.getframe().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
