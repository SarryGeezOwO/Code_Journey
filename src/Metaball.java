
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Metaball extends JPanel implements Runnable {
	
	static final int FRAME_WIDTH = 700;
	static final int FRAME_HEIGHT = 700;
	Random rand;
	Thread t;
	Blob[] blob = new Blob[5];
	
	Metaball() {
		rand = new Random();
		this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.setBackground(Color.black);
		for(int i = 0; i < blob.length; i++) {
			blob[i] = new Blob(rand.nextInt(FRAME_WIDTH), rand.nextInt(FRAME_HEIGHT));			
		}
		
		
		t = new Thread(this);
		t.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setPaintMode();
		for(int i = 0; i < FRAME_WIDTH; i++) {
			for(int j = 0; j < FRAME_HEIGHT; j++) {
				float sum = 0f;
				for(Blob b : blob) {
					// Distance between current Pixel and and The current blob position
					float distance = dist(i, j, b.pos.x, b.pos.y);
					sum += 100 * (b.radius / distance); // scaling
				}
				//Color col = color_int((int)sum);
				// we divide the sum by 255 to match the max color value before converting to a HSb value
				g.setColor(hsb_Color(Math.clamp((sum / 255), 0, 0.9f))); // HSB only takes 0 and 1, that's why it's 0.9 to get rid of red color
				g.fillRect(i, j, 1, 1);
			}
		}
		for(Blob b : blob) {
			b.update();
			//b.draw(g); // open this code line to show raw data
		}
	}
	
	public Color hsb_Color(float val) {
		// never change this value lmao
		Color c = new Color(0x15, 0x89, 0xFF);

		// Get saturation and brightness.
		float[] hsbVals = new float[3];
		Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbVals);

		// Uses Degree as HUE ( 0.5 == 180 degrees) ranges from 0 - 360 ( 0 - 1 )
		c = new Color(Color.HSBtoRGB(val, hsbVals[1], hsbVals[2]));
		return c;
	}
	
	public Color color_int(int x) {
		return new Color(Math.clamp(x, 0, 255), Math.clamp(x, 0, 255), Math.clamp(x, 0, 255));
	}
	
	public int dist(double x1, double y1, double x2, double y2) {
		int res = (int) Math.sqrt( (Math.pow((x2-x1), 2) + (Math.pow((y2-y1), 2))) );
		return res;
	}
	
	@Override
	public void run() {
		while(true) {
			repaint();
		}
	}
	
	
}
