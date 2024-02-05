import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel implements Runnable {
	static final int SCREEN_WIDTH = 1200;
	static final int SCREEN_HEIGHT = 600;
	int counter = 1;
	Thread thr;
	
	double elapsedTime;
	double addTime = 0.0175;
	List<Double> wave = new ArrayList<Double>();
	
	ImageIcon add;
	ImageIcon minus;
	
	public ImageIcon scaleIMG(ImageIcon icn, int w, int h) {
		Image img = icn.getImage();
		Image newImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return icn = new ImageIcon(newImg);
	}
	
	DrawPanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(new Color(0,0,0));
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 105));
		thr = new Thread(this);
		thr.start();
		
		add = scaleIMG(
				new ImageIcon("img/add.png"), 25, 25);
		minus = scaleIMG(
				new ImageIcon("img/minus.png"), 25, 25);
		
		JButton fast = new JButton();
		fast.setFocusable(false);
		fast.setIcon(add);
		fast.setBackground(new Color(0, 0, 0, 1));
		fast.addActionListener(e -> counter++);
		fast.setFont(new Font("Kanit", Font.BOLD, 20));
		JButton slow = new JButton();
		slow.setFocusable(false);
		slow.setBackground(new Color(0, 0, 0, 1));
		slow.setIcon(minus);
		slow.addActionListener(e -> counter--);
		slow.setFont(new Font("Kanit", Font.BOLD, 20));
		this.add(slow);
		this.add(fast);
	}
	
	public void paint(Graphics g) {
		/**
		 *  This function / method runs every frame that's why i use
		 *  this function as a update method :)
		 */
		super.paint(g);
		double x = 0;
		double y = 0;
		
		g.translate(320, 350);
		for(int i = 0; i < counter; i++) {
			// ---> Calculation Block <--- //
			double prevX = x;
			double prevY = y;			
			int n = i * 2 + 1;
			double radius = 100 * (4 / (n * Math.PI));
			x += radius * Math.cos(n * elapsedTime);
			y += radius * Math.sin(n * elapsedTime);
			
			// ---> Main Circle Draw <--- //
			g.setColor(new Color(255, 255, 255, 80));
			g.drawOval(
					(int)(prevX-radius),
					(int)(prevY-radius),
					(int)radius*2,
					(int)radius*2
			);
			
			// ---> Radius Line <--- //
			g.setColor(new Color(255, 255, 255, 255));
			g.drawLine(
					(int)prevX,
					(int)prevY,
					(int)x,
					(int)y
			);
			
			// ---> Point draw <--- //
			g.setColor(new Color(255, 255, 0, 255));
			g.fillOval((int)x-5, (int)y-5, 10, 10);
		}
		
		// ---> Texts related <--- //
		g.setColor(Color.white);
		g.setFont(new Font("Poppins", Font.PLAIN, 20));
		g.drawString("X value : " + (int)x, -290, -320);
		g.drawString("Y value : " + (int)y, -290, -290);
		g.drawString("Point count : " + counter, -290, -260);
		wave.addFirst(y);
		
		// ---> Connecting line between Wave and Point <--- //
		g.translate(350, 0);
		double ass = wave.get(0);
		g.setColor(new Color(255, 255, 255, 120));
		g.drawLine((int)x-350, (int)y, 0, (int)ass);
		
		// ---> Wave Draw code block <--- //
	 	g.setColor(Color.white);
		for(int i = 0; i<wave.size(); i++) {
			double shit = wave.get(i);
			g.fillOval(i, (int)shit, 5, 5);
		}
		
		elapsedTime += addTime;
		
		if(wave.size() > 500) {
			wave.removeLast();
		}
	}

	@Override
	public void run() {
		// the actual loop method
		// except this method does not have the
		// capability to draw on a canvas
		// that's why i have to use the paint() function
		long lastTime = System.nanoTime(); 
		double fps = 80.0;
		double ns = 1000000000/fps;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				revalidate();
				repaint();
				delta--;
			}
		}
	}
}
