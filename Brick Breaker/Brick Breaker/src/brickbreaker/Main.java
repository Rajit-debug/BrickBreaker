package brickbreaker;

/* The class Main draws the main frame on which the game components will be placed and
   the game will be played */

import javax.swing.*;

public class Main {

	public static void main(String[] args) {

		JFrame frame = new JFrame();		//create a JFrame named frame.
		Game game = new Game();
		frame.setBounds(10,10,700,600);		//specifying the position and size of the frame.
		frame.setTitle("Brick Breaker!");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		
	}

}
