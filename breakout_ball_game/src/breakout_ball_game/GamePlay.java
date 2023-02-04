package breakout_ball_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener, KeyListener{
	private boolean play; //Game shouldn't start automatically
	private int score; //Initial score will be 0
	private int totalBricks;
	
	//Timer
	private Timer timer;
	private int delay = 6;
	
	//Paddle position
	private int paddleX;
	
	//Ball position
	private int ballPosX;
	private int ballPosY;
	private int ballDirX; //How much the ball should increment in x direction
	private int ballDirY;
	
	BrickMapGenerator map;
	
	public void initialState() {
		play = false;
		score = 0;
		totalBricks = 42;
		paddleX = 325;
		ballPosX = 320;
		ballPosY = 300;
		ballDirX = -1;
		ballDirY = -2;
		
		map = new BrickMapGenerator(3,7);
	}
	
	public GamePlay() {
		initialState();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		
		//Formatting background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 750, 600);
		
		//Creating border
		g.setColor(Color.YELLOW);
		g.fillRect(1,1,750,3); //Top border
		g.fillRect(1,1,2,592); //Left border
		g.fillRect(734,1,3,600); //Right border
		
		//Paddle
		g.setColor(Color.GREEN);
		g.fillRect(paddleX, 550, 100, 8);
		
		// the ball
		g.setColor(Color.ORANGE);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		//Drawing map
		map.drawMap((Graphics2D) g);
		
		//Displaying score:
		g.setColor(Color.GREEN);
		g.drawString("Score: " + score, 350, 30);
		
		//Gameover
		if(ballPosY>580) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			
			g.setColor(Color.RED);
			g.drawString("Score: " + score, 350, 300);
			g.drawString("Press ENTER to play again", 300, 320);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(paddleX <= 0) {
				paddleX = 0;
			}else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(paddleX >= 635) {
				paddleX = 635;
			}else {
				moveRight();
			}
		}

		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				initialState();
			}
		}
		
		repaint();
	}

	private void moveLeft() {
		// TODO Auto-generated method stub
		play = true;
		paddleX -= 20;
	}
	
	private void moveRight() {
		// TODO Auto-generated method stub
		play = true;
		paddleX += 20;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(play) {
			ballPosX += ballDirX;
			ballPosY += ballDirY;
			
			//Conditions for rebound against the walls
			//Left and right
			if(ballPosX <= 0 || ballPosX >= 714) { //714 because right border starts at 734px and ball width is 20 so 734-20
				ballDirX = -ballDirX;
			}
			//Top
			if(ballPosY <= 0) {
				ballDirY = -ballDirY;
			}
			//Bottom peddal
			Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
			Rectangle paddleRect = new Rectangle(paddleX, 550, 100, 8);
			if(ballRect.intersects(paddleRect)) {
				ballDirY = -ballDirY;
			}
			
			//Map bricks intersection
			for(int i = 0; i<map.getMap().length; i++) {
				for(int j = 0; j<map.getMap()[0].length; j++) {
					if(map.getMap()[i][j]>0) {
						int w = map.getWidth();
						int h = map.getHeight();
						int brickPosX = 45+j*w;
						int brickPosY = 50+i*h;
						
						Rectangle brickRect = new Rectangle(brickPosX, brickPosY, w, h);
						
						if(brickRect.intersects(ballRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=10;
							
							//First condition is when the ball strikes the left border of brick, second is for right border
							if(ballPosX+19 <= brickPosX || ballPosX+1 >= brickPosX+w) {
								ballDirX = -ballDirX;
							}else {
								ballDirY = -ballDirY;
							}
							
							
						}
					}
					
				}
			}
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}