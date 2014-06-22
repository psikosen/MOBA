import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Environment {
	private char[][] envMap;
	private int xindex, yindex;
	private int environmentWidth;
	private boolean grid;
	private int mapWidth;
	private int mapHeight;
	private ArrayList<Pair<Integer>> path;
	private ArrayList<Pair<Integer>> nodeRotation;
	
	public Environment(int x, int y, int mapHeight, int mapWidth, int envWidth){
		grid = false;
		xindex = x;
		yindex = y;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.environmentWidth = envWidth;
		this.path = new ArrayList<Pair<Integer>>();
		this.nodeRotation = new ArrayList<Pair<Integer>>();
		
		initialize();
	}
	
	public void enableGrid(){
		grid = true;
	}
	
	private void initialize(){
		// read in world map
		FileInputStream in = null;
		char c, i, j;
		
		try {
			in = new FileInputStream("environment.txt");
			
			envMap = new char[mapHeight][mapWidth];				
			
			for (i = 0 ; i < mapHeight ; ++i){
				for (j = 0 ; j < mapWidth ; ){
					c = (char) in.read();
					if (c < 122 && c > 97){
						envMap[i][j] = c;
						++j;
					}
				}
			}

			if (in != null) {
				in.close();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		
		printEnvironment();
		
		// populate rotation checks
		nodeRotation.add(new Pair<Integer>(0, 1));
		nodeRotation.add(new Pair<Integer>(1, 0));
		nodeRotation.add(new Pair<Integer>(0, -1));
		nodeRotation.add(new Pair<Integer>(-1, 0));
	}
	
	public void printEnvironment(){
		int i, j;
		for (i = 0 ; i < mapHeight ; ++i){
			System.out.print(":");
			for (j = 0 ; j < mapWidth ; ++j){
				System.out.print(envMap[i][j]);
			}
			System.out.println();
		}
	}
	
	public void paintEnvironment(Graphics g){
		int i, j;
		for (i = 0 ; i < mapHeight ; ++i){
			for (j = 0 ; j < mapWidth ; ++j){
				switch (envMap[i][j]){
				case 'e':
					g.setColor(Color.red);
					g.fillRect(j*environmentWidth, i*environmentWidth, environmentWidth, environmentWidth);
					break;
				case 'r':
					g.setColor(Color.green);
					g.fillRect(j*environmentWidth, i*environmentWidth, environmentWidth, environmentWidth);
					// print routing grid
					if (grid){
						g.setColor(Color.white);
						g.fillArc(j*environmentWidth+environmentWidth/4, i*environmentWidth+environmentWidth/4,
								environmentWidth/2, environmentWidth/2, 0, 360);
					}
					break;
				default:
					System.out.println("Error: Unrecognized character in environment array.");
				}
			}
		}
		
		// draw path
		Pair<Integer> currentLoc = new Pair<Integer>(xindex, yindex);
		for (Pair<Integer> loc : path){
			g.setColor(Color.black);
			g.drawLine(currentLoc.getX()*environmentWidth + environmentWidth/2, currentLoc.getY()*environmentWidth + environmentWidth/2,
					loc.getX()*environmentWidth + environmentWidth/2, loc.getY()*environmentWidth + environmentWidth/2);
			//System.out.println(currentLoc.getX() + " " + currentLoc.getY() + " " + loc.getX() + " " + loc.getY());
			currentLoc = loc;
		}
	}

	public void calculatePath(Pair<Integer> delta, Pair<Integer> clickLoc) {
		double distance = distanceCalculator(clickLoc.getX(), clickLoc.getY(), xindex, yindex), newDistance;
		newDistance = distance;
		Pair<Integer> newLoc = null;

		this.path = new ArrayList<Pair<Integer>>();

		System.out.println("Click: " + clickLoc.getX() + " , " + clickLoc.getY() + " Index: " + xindex + " , " + yindex);
		System.out.println("Distance = " + distance);
		
		// calculate first node to move
		int xloc = xindex, yloc = yindex;
		int iteration = 0;
		while ((xloc != clickLoc.getX() || yloc != clickLoc.getY()) && iteration < 20){
			//System.out.println("\nLocation: " + (xloc) + " , " + (yloc));
			distance = distanceCalculator(clickLoc.getX(), clickLoc.getY(), xloc, yloc);
			//System.out.println("Distance = " + newDistance);
			for (Pair<Integer> direction : nodeRotation){
				newDistance = distanceCalculator(clickLoc.getX(),clickLoc.getY(), 
						xloc + direction.getX(), yloc + direction.getY());

				//System.out.println("Index: " + (xloc + direction.getX()) + " , " + (yloc + direction.getY()));				
				//System.out.println("Distance = " + newDistance);
				
				if (newDistance < distance){

					distance = newDistance;
					newLoc = new Pair<Integer>(xloc + direction.getX(), yloc + direction.getY());
				}
			}
			xloc = newLoc.getX();
			yloc = newLoc.getY();			
			++iteration;
			path.add(newLoc);
		}

		System.out.println("Length of path: " + path.size());
	}
	
	private double distanceCalculator(int x1, int y1, int x2, int y2){
		return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}
}
