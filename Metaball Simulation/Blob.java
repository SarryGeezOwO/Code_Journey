import java.awt.*;
import java.util.Random;

public class Blob {
	Vector2 pos;
	Vector2 vel;
	int radius;
	Random rand;
	
	Blob(double x, double y) {
		rand = new Random();
		pos = new Vector2(x,y);
		vel = new Vector2(rand.nextDouble(-1, 1) * rand.nextInt(6, 12), rand.nextDouble(-1, 1) * rand.nextInt(6, 12));
		radius = rand.nextInt(60, 120);
	}
	
	public void update() {
		pos.x += vel.x;
		pos.y += vel.y;
		
		if(pos.x > Metaball.FRAME_WIDTH || pos.x < 0) {
			vel.x *= -1;
		}
		if(pos.y > Metaball.FRAME_HEIGHT || pos.y < 0) {
			vel.y *= -1;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.drawOval((int)pos.x-radius, (int)pos.y-radius, radius*2, radius*2);
	}
}
