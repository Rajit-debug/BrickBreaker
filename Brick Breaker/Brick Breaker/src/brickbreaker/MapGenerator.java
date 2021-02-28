package brickbreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/* The class MapGenerator generates the bricks on the screen. */

public class MapGenerator {

	public int map[][];			//2D-Array
	public int brickWidth;
	public int brickHeight;
	
	/*
	 * Constructor
	 * Pre: none
	 * Post: Receives the number of rows and columns and determines how many 
	 * rows and columns should be generated for a particular number of bricks.
	 */
	public MapGenerator(int row, int col) {
		
		map = new int[row][col];
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;		//making array element equal to one will detect the particular brick
									//that hasn't intersected with the ball.
			}
		}
		
		brickWidth = 540/col;
		brickHeight = 150/row;
		
	}
	
	/*
	 * This method draws bricks at particular positions
	 * Pre: none
	 * Post: Bricks appear on the screen.
	 */
	public void draw(Graphics2D g) {
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(map[i][j] > 0) {
					g.setColor(Color.white);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
		
	}
	
	public void setBrickValue(int value, int row, int col) {
		
		map[row][col] = value;
		
	}
	
}
