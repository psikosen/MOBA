import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MOBA extends JFrame implements KeyListener, MouseListener{
	private GameArea panel;
	private int HEIGHT = 400;
	private int WIDTH = 400;
	
	public static void main(String[] args) throws InterruptedException{
		MOBA moba = new MOBA();
		moba.begin();
	}
	
	public MOBA(){
		super("MOBA");
		panel = new GameArea(HEIGHT, WIDTH);
		panel.setBackground(Color.BLACK);
		add(panel);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		addKeyListener(this);
		addMouseListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void begin() throws InterruptedException{
		panel.eventLoop();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	    panel.handleKeyPress(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		panel.handleKeyRelease(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		
	}

	@Override
	public void mouseExited(MouseEvent me) {
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		panel.handleMousePressed(me.getX(), me.getY());
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		
	}
}
