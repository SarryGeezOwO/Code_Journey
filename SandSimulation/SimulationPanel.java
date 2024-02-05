import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class SimulationPanel extends JPanel implements Runnable, MouseMotionListener {
	
	static final int FRAME_SIZE = 500;
	static final Dimension FRAME_DIMENSION = new Dimension(FRAME_SIZE, FRAME_SIZE);
	static final int TILE_SIZE = 25;
	Thread gThread;
	Image img;
	Graphics graphics;
	
	int xMouse;
	int yMouse;
	int rateConst = 10;
	int rate;
	boolean rateCheck = true;
	
	static List<Grain> grainCol = new ArrayList<Grain>();
	public void addGrain() {
		if(rate > 0) {
			if(rateCheck) {
				Grain gr = new Grain(
						xMouse*TILE_SIZE, yMouse*TILE_SIZE, TILE_SIZE);
				grainCol.add(gr);
				rateCheck = false;
			}
			rate--;
		}
		if(rate <= 0) {
			rateCheck = true;
			rate = rateConst;
		}
	}
	
	SimulationPanel() {
		this.setFocusable(true);
		this.addMouseMotionListener(this);
		this.setPreferredSize(FRAME_DIMENSION);
		rate = rateConst;
		
		gThread = new Thread(this);
		gThread.start();
	}
	
	public void paint(Graphics g) {
		img = createImage(getWidth(), getHeight());
		graphics = img.getGraphics();
		draw(graphics);
		g.drawImage(img,0,0,this);
	}
	
	public void draw(Graphics g) {
		for(Grain p : grainCol) {
			p.draw(g);
		}
	}
	
	public void move() {
		for(Grain p : grainCol) {
			p.move();
		}
	}

	public void MousePos() {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		SwingUtilities.convertPointFromScreen(b, this);
		xMouse = (int) b.getX()/TILE_SIZE;
		yMouse = (int) b.getY()/TILE_SIZE;
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 25.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				MousePos();
				move();
				revalidate();
				repaint();
				delta--;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		addGrain();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}