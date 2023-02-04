package breakout_ball_game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class BrickMapGenerator {
	private int width;
	private int height;
	private int[][] map;
	
	public BrickMapGenerator(int row, int col) {
		map = new int[row][col];
		
		for(int i = 0; i<row; i++) {
			for(int j = 0; j<col; j++) {
				map[i][j]=1;
			}
		}
		
		height = 150/row;
		width = 650/col;
	}	

	public void setBrickValue(int val, int r, int c) {
		map[r][c] = val;
	}
	
	public void drawMap(Graphics2D g) {
		
		for(int i = 0; i<map.length; i++) {
			for(int j = 0; j<map[0].length; j++) {
				if(map[i][j]>0) {
					g.setColor(Color.WHITE);
					g.fillRect(width*j+45, height*i+50, width, height);
					
					//Adding border to each brick
					g.setColor(Color.BLACK);
					g.setStroke(new BasicStroke(3));
					g.drawRect(width*j+45, height*i+50, width, height);
				}
			}
		}
		
	}
	
	public int[][] getMap() {
		return map;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}