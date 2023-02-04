package breakout_ball_game;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		GamePlay gameplay = new GamePlay();
		
		frame.setSize(750, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Breakout Ball Game");
		frame.setResizable(false);
		frame.add(gameplay);
		frame.setVisible(true);
		
	}

}