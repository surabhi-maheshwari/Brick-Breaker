package brickbreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {

	public int map[][];
	public int brickWidth;
	public int brickHeight;
	public MapGenerator(int row,int col)// instantiating the size of the grid of bricks 
	{
		map = new int [row][col];
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
			map[i][j]=1; //1 will detect this brick is not intersected, value changes to 0 if brick and ball are intersected
			}
		}
		brickWidth=540/col;
		brickHeight=150/row;
		
	}
	public void draw(Graphics2D g)
	{
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				if(map[i][j]>0)
				{
					g.setColor(Color.white);
					g.fillRect(j*brickWidth + 80, i*brickHeight + 50 , brickWidth, brickHeight); //draw complete rectangle of bricks
					
					g.setStroke(new BasicStroke(3)); //make border of bricks
					g.setColor(Color.black);
					g.drawRect(j*brickWidth + 80, i*brickHeight + 50 , brickWidth, brickHeight);// draw different bricks
					
				}
			}
		}	
			
	}
	public void setBrickValue(int value, int row, int col)//change the value of brick to zero if it is intersected
	{
		map[row][col]=value;
	} 
}

