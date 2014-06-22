import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

class PlayerObject {
	private double locationX;
	private double locationY;
	private int dx, dy;
	private int width;
	
	public PlayerObject(double locationX, double locationY, int width) {
		this.locationX = locationX;
		this.locationY = locationY;
		this.width = width;
	}
	
	public double getLocationX(){
		return locationX;
	}
	
	public double getLocationY(){
		return locationY;
	}
	
	public void setDelta(int dx, int dy){
		this.dx = dx;
		this.dy = dy;
		
		System.out.println("Delta: x " + dx + ", y " + dy);
	}
	
	public Pair<Integer> getDelta(){
		return new Pair<Integer>(dx, dy);
	}
	
	public void paintHero(Graphics g){
		g.setColor(Color.CYAN);
        g.fillRect((int) getLocationX() - width / 2, (int) getLocationY() - width / 2, 10, 10);
	}
	
}
