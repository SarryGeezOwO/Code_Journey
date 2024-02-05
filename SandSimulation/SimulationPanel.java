import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SimulationPanel extends JPanel implements Runnable, MouseListener {
	
	static final int FRAME_WIDTH = 600;
	static final int FRAME_HEIGHT = 600;
	static final int TILE_SIZE = 2;
	static final double fps = 120.0;
	Thread thread;
	
	float[][] grid;
	int columns, rows;
	
	int xMouse, yMouse;
	boolean isClicked = false;
	int matrix = 15;
	
	float hueValue = 1;
	
	SimulationPanel() {
		this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.setBackground(Color.black);
		this.addMouseListener(this);
		columns = FRAME_WIDTH / TILE_SIZE;
		rows = FRAME_HEIGHT / TILE_SIZE;
		grid = make2DArray(columns, rows);
		
		thread = new Thread(this);
		thread.start();
	}
	
	public Color checkColor(float val) {
		Color c = new Color(0x15, 0x89, 0xFF);

		// Get saturation and brightness.
		float[] hsbVals = new float[3];
		Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbVals);

		// Uses Degree as HUE ( 0.5 == 180 degrees)
		c = new Color(Color.HSBtoRGB(val, hsbVals[1], hsbVals[2]));
		return c;
	}
	
	public void paint(Graphics g) {	
		super.paint(g);
		if(isClicked) addGrain();
		
		for (int i = 0; i < columns; i++) {
			for(int j = 0; j < rows; j++) {
				g.setColor(checkColor(grid[i][j]));
				int x = i * TILE_SIZE;
				int y = j * TILE_SIZE;
				if(grid[i][j] > 0) 
					g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
			}
		}
		
		// Create a duplicate array so that the original array don't get messed up
		float[][] nextGen = make2DArray(columns, rows);
		for (int i = 0; i < columns; i++) {
			for(int j = 0; j < rows; j++) {
				float state = grid[i][j];
				if(state > 0) {
					float belowR = 1;
					float belowL = 1;
					float below = grid[i][j+1];
						// Check For the bottom right of current sand
					if(i + 1 >= 0 && i + 1 <= columns - 1)
						belowR = grid[i+1][j+1];		
						// Check For the bottom left of current sand
					if (i-1 >= 0 && i - 1 <= columns - 1)
						belowL = grid[i-1][j+1];
						
					
					// Collision Checking
					if(below == 0 && j < rows - 1) {
						nextGen[i][j+1] = grid[i][j];
					}else if(belowR == 0 && j < rows - 1) {
						nextGen[i+1][j+1] = grid[i][j];
					}else if(belowL == 0 && j < rows - 1) {
						nextGen[i-1][j+1] = grid[i][j];
					}else {
						// if there's no available space in either LEFT or RIGHT then stay to the current POSITION
						nextGen[i][j] = grid[i][j];
					}
				}
			}
		}
		// Replace the old grid with the new adjusted grid !
		grid = nextGen;
	}
	
	public float[][] make2DArray(int c, int r) {
		float[][] arr = new float[c+1][];
		for(int i = 0; i <arr.length; i++) {
			arr[i] = new float[r+1];
			for(int j = 0; j < arr[i].length; j++) {
				arr[i][j] = 0; // Set every tile to a default state of 0 ( nothing ) where 1 has a state of a SAND
			}
		}
		return arr;
	}
	
	public void addGrain() {
		hueValue += 0.001;
		
		var extent = Math.floor(matrix/2);
		for(var i = -extent; i <= extent; i++) {
			for(var j = -extent; j <= extent; j++) {
				var mouseC = xMouse + i;
				var mouseR = yMouse + j;
				if(mouseC >= 0 && mouseC <= columns -1 && mouseR >= 0 && mouseR <= rows -1)
					grid[(int)mouseC][(int)mouseR] = hueValue;
			}
		}
		
	}
	
	public void mousePos() {
		// Get the MOUSE POSITION from a screen
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		SwingUtilities.convertPointFromScreen(b, this);
		// Scale the mouse POSITION based on tile size
		xMouse = (int) b.getX()/TILE_SIZE;
		yMouse = (int) b.getY()/TILE_SIZE;
	}
	
	public void run() {
		// To run certain codes in a FPS nature
		long lastTime = System.nanoTime();
		double ns = 1000000000 / fps;
		double delta = 0.0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				mousePos();
				repaint();
				delta--;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==this) {
			isClicked = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==this) {
			isClicked = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
