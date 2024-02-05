import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grain extends Rectangle {
	
	boolean collided = false;
	final int constant = SimulationPanel.TILE_SIZE;
	Random rand;
	List<String> chance = new ArrayList<String>(); 
	Grain(int x, int y, int size) {
		super(x,y,size,size);
		rand = new Random();
	}
	
	public void move() {
		if(collided == false) {
			chance.removeAll(chance);
			chance.add("A"); //left
			chance.add("B"); // right
			if(y < SimulationPanel.FRAME_SIZE-constant) {
				y += constant;
			}
			for(Grain s : SimulationPanel.grainCol) {
				if((y+constant) == s.y && x==s.x) {
					collided = true;
				}
			}
		}
		else {
			FallSand();
		}
	}
	
	public void FallSand() {
		int count = CheckForCount();
		if(count == 2)
			return;
		else if(count == 1) {
			if(chance.contains("A")) {
				if(x-constant >= 0) {
					x -= constant;
					collided = false;
				}
			}
			else if(chance.contains("B")) {
				if(x+constant <= SimulationPanel.FRAME_SIZE-constant) {
					x += constant;
					collided = false;
				}
			}
		}
		else if (count == 0) {
			int temp = rand.nextInt(2);
			if(temp == 0) { 
				if(chance.contains("A")) {
					if(x-constant >= 0) {
						x -= constant;
						collided = false;							
					}
				}
			}
			if(temp == 1) { 
				if(chance.contains("B")) {
					if(x+constant <= SimulationPanel.FRAME_SIZE-constant) {
						x += constant;
						collided = false;							
					}
				}
			}
		}
		return;
	}
	
	public int CheckForCount() {
		int count = 0;
		for(Grain s : SimulationPanel.grainCol) {
			if((x-constant)==s.x && (y+constant)==s.y) {
				chance.remove("A");
				chance.add("C");
				count++;
			}
			if((x+constant)==s.x && (y+constant)==s.y) {
				chance.remove("B");
				chance.add("C");
				count++;
			}
		}
		return count;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(0xd6a74a));
		g.fillRect(x, y, width, height);
	}
}
