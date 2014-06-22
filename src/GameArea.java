import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameArea extends JPanel{
	private int areaWidth;
	private int areaHeight;
	private int playerWidth = 10;
	private PlayerObject hero;
	private char[][] envMap;
	private Environment env;
	private int mapWidth = 38;
	private int mapHeight = 36;
	private int environmentWidth = 10;
	
	public GameArea(int height, int width){
		areaWidth = width;
		areaHeight = height;
		hero = new PlayerObject((mapHeight/2) * environmentWidth + environmentWidth / 2, 
				(mapWidth/2) * environmentWidth + environmentWidth / 2, playerWidth);
		
		env = new Environment((mapHeight/2), (mapWidth/2), 
				mapHeight, mapWidth, environmentWidth);
		env.enableGrid();
	}
	
	public void eventLoop() throws InterruptedException{
		while (true){
			// update objects
			
			
			// check collisions
			checkCollisions();
			
			
			repaint();
			Thread.sleep(100);
		}
	}
	
	private void checkCollisions(){

	}

	
	public void paint(Graphics g){
		super.paint(g);
		
		// paint environment
		env.paintEnvironment(g);

		// paint hero
		hero.paintHero(g);
	}
	
	public void handleKeyPress(KeyEvent e) {
		int code = e.getKeyCode();
		
		switch (code){
		// hero's movement keys
		case KeyEvent.VK_A:
			System.out.println("An a was pressed");
			
			break;
		case KeyEvent.VK_D:
			System.out.println("A d was pressed");
			
			break;
		case KeyEvent.VK_S:
			System.out.println("A s was pressed");
			
			break;
		case KeyEvent.VK_W:
			System.out.println("A w was pressed");
			
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("A space was pressed");
			
			break;
		}
		
		repaint();
	}

	public void handleKeyRelease(KeyEvent e) {}

	public void handleKeyTyped(KeyEvent e) {}

	public void handleMousePressed(int x, int y) {
		// adjust for actual click location
		y -= 30;
		x -= 7;
		int dx, dy;
		if ((x - hero.getLocationX()) > 0){
			dx = 1;
		} else if ((x - hero.getLocationX()) < 0){
			dx = -1;
		} else {
			dx = 0;
		}
		
		if ((y - hero.getLocationY()) > 0){
			dy = 1;
		} else if ((y - hero.getLocationY()) < 0){
			dy = -1;
		} else {
			dy = 0;
		}

		env.calculatePath(new Pair<Integer>(dx,dy), new Pair<Integer>(x/environmentWidth, y/environmentWidth));
		System.out.println("Clicked: " + x/environmentWidth + " , " + y/environmentWidth);
		System.out.println("Clicked: " + x + " , " + y);
		repaint();
	}
	
}
