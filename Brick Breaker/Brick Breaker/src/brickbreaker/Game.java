package brickbreaker;

/* The class Game listens for key commands from the player and moves the paddle accordingly.
   The class Game also draws the components of the game and shows a winning or losing message
   at the end of the game. The user will also have a choice to restart the game by pressing the 
   Enter key. 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener {

	private boolean startPlay = false;
	private int score = 0;
	private int bricks = 21;
	private Timer time;
	private int delay = 8;
	private int paddleX = 310;
	private int ballX = 120;
	private int ballY = 350;
	private int ballXDir = -2;
	private int ballYDir = -3;
	private int brickX;
	private int brickY;
	private int brickWidth;
	private int brickHeight;
	private MapGenerator map;
	
	/*
	 * Constructor
	 * Pre: none
	 * Post: The timer is started.
	 */
	public Game() {
		
		map = new MapGenerator(3, 7);
		
		addKeyListener(this);					//adds an object that listens for a key press event.
		setFocusable(true);						//lets component gain focus
		
		/*decides whether or not focus traversal keys 
		 are allowed to be used when the current component has focus.*/
		setFocusTraversalKeysEnabled(false);
		
		time = new Timer(delay, this);			//declaration of timer object
		time.start();							//timer starts
		
	}
	
	/*
	 * Function that draws ball, paddle, score, background, etc.
	 * Pre: none
	 * Post: A graphics object, g, is created. Screen will display 
	 * a ball and a paddle. If player wins, a congratulatory message is shown, otherwise a 
	 * losing message is depicted along with the score.
	 */
	public void paint(Graphics g)	{
		
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//draw map
		map.draw((Graphics2D)g);
		
		//border
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score: "+score, 565, 30);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(paddleX, 550, 100, 8);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballX, ballY, 20, 20);
		
		//if player wins, a congratulatory message will pop up on the screen.
		if(bricks <= 0) {
			startPlay = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won!", 260, 300);
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		//end game if ball goes out from the bottom
		//display a game over message along with the score and an option to restart the game.
		if(ballY > 570) {
			startPlay = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: " + score, 190, 300);
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		time.start();
		
		if(startPlay)	{
			//creating an imaginary rectangle around the ball to check for intersection with paddle
			if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(paddleX, 550, 100, 8)))	{
				ballYDir = -ballYDir;
			}
			
			A: for(int i = 0; i < map.map.length; i++) {
				for(int j = 0; j < map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						brickX = j * map.brickWidth + 80;
						brickY = i * map.brickHeight + 50;
						brickWidth = map.brickWidth;
						brickHeight = map.brickHeight;
						
						Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
						Rectangle brickRect = rectangle;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							bricks -= 1;
							score += 5;
							
							if(ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width) {
								ballXDir = -ballXDir;
							} else {
								ballYDir = -ballYDir;
							}
							break A;
						}
					}
				}
			}
			ballX += ballXDir;
			ballY += ballYDir;
			if(ballX < 0) {
				ballXDir = -ballXDir;		//change direction of ball (ball bounces off left wall)
			}
			if(ballY < 0) {
				ballYDir = -ballYDir;		//change direction of ball (ball bounces of top wall
			}
			if(ballX > 670) {
				ballXDir = -ballXDir;		//change direction of ball (ball bounces of right wall)
			}
		}
		
		repaint();		//need to call repaint method to keep redrawing the paddle and other components
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		//if right arrow key is pressed and paddle is not all the way to the right side
		//of the screen, then call moveRight().
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT)	{
			if(paddleX >= 600)	{
				paddleX = 600;
			} else	{
				moveRight();
			}
		}
		
		//if left arrow key is pressed and paddle is not all the way to the left side
		//of the screen, then call moveLeft().
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT)	{
			if(paddleX < 10)	{
				paddleX = 10;
			} else	{
				moveLeft();
			}
		}
		
		//if enter is pressed, reload the game
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!startPlay) {
				startPlay = true;
				ballX = 120;
				ballY = 350;
				ballXDir = -2;
				ballYDir = -3;
				paddleX = 310;
				score = 0;
				bricks = 21;
				map = new MapGenerator(3, 7);		//want to generate the map again if player plays again.
				
				repaint();
			}
		}
		
	}
	
	/*
	 * The moveRight method allows the paddle to move right
	 * Pre: none
	 * Post: The paddle will be seen moving to the right on the screen.
	 */
	public void moveRight()	{
		
		startPlay = true;
		paddleX += 20;		//paddle will move right
		
	}

	/*
	 * The moveLeft method allows the paddle to move left
	 * Pre: none
	 * Post: The paddle will be seen moving to the left on the screen.
	 */
	public void moveLeft()	{
		
		startPlay = true;
		paddleX -= 20;		//paddle will move left
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}	
	
}
