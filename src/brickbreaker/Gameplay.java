package brickbreaker;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener,ActionListener 
{
	private boolean play=false;
	private int score=0;
	private int totalBricks=21;	// 7*3 bricks
	private Timer timer; //set timer
	private int delay=8;//speed of ball
	private int playerX = 310; //starting position for x axis 	
	private int ballposX= 120;//initial position of ball
	private int ballposY= 350;
	private int ballXdir= -1; //direction of ball
	private int ballYdir= -2;
	private MapGenerator map;
	
	public Gameplay()
	{
		map= new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer= new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{
	//background 
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
	//drawing map
		map.draw((Graphics2D)g);
		
	//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 2);
		g.fillRect(691, 0, 3, 592);
		
	//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
			
	//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
	//scores 
		g.setColor(Color.blue);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString("Score: "+score, 560, 30);

	//Won
		if(totalBricks<=0)
		{
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.blue);
			g.setFont(new Font("serif",Font.BOLD, 28));
			g.drawString("You Won: ", 260,300);
			
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("Press Enter to Restart",250,350);
		}
		
	//If you loose
		if(ballposY>570)
		{
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.blue);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("Game over, Scores: "+score, 210,300);
			
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("Press Enter to restart",250,350);
		}
	g.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play==true)
		{
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) //creating intersection of ball and paddle 
			{
				ballYdir=-ballYdir;
			}
			//logic for intersection of brick and ball and disappear the brick 
			A: for(int i=0;i<map.map.length;i++) //first map is obj of MapGenerator and second map is map array in MapGenerator class
			{
				for(int j=0;j<map.map[0].length;j++)
				{
					if(map.map[i][j]>0)
					{
						int brickX=j*map.brickWidth+80;
						int brickY=i*map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						Rectangle rect=new Rectangle(brickX,brickY, brickWidth,brickHeight);
						//create rectangle around ball to detect intersection
						Rectangle ballRect= new Rectangle(ballposX,ballposY, 20,20);
						//create one more rectangle and pass reference 
						Rectangle brickRect=rect;
						
						if(ballRect.intersects(brickRect)) //if the ball rectangle and brick rectangle intersects make brick disappear or make its value 0 
						{
							map.setBrickValue(0,i,j);
							totalBricks--;
							score+=5;
							
							if(ballposX+19<=brickRect.x|| ballposX+1>=brickRect.x+brickRect.width)// for left or right intersection 
							{
								ballXdir=-ballXdir;
								
							}
							else
							{
								ballYdir=-ballYdir;
							}
							break A;
						
						}
					}
				}
			}
					
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			if(ballposX<0) //left border
			{	ballXdir=-ballXdir;}
			if(ballposY<0) //top border
				{ballYdir=-ballYdir;}
			if(ballposX > 670) //right border
				{ballXdir=-ballXdir;}
			
		}
		repaint();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(playerX>=600)
			{
				playerX=600;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(playerX<10)
			{
				playerX=10;
			}
			else {
				moveLeft();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER) // when pressing enter should start a new game 
		{
			if(!play)
			{
				play=true;
				ballposX= 120;
				ballposY=350;
				ballXdir=-1;
				ballYdir=-2;
				playerX=310;
				score=0;
				totalBricks=21;
				map=new MapGenerator(3,7);
				repaint();	
			}
		}
	}
	public void moveRight() {
		play=true;
		playerX+=20;
	} 	
	public void moveLeft() {
		play=true;
		playerX-=20;
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
}
	