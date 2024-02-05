import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class RunPanel extends JPanel implements Runnable{

		class Vector3 {
			float x,y,z;
			Vector3(float x, float y, float z) {
				this.x = x;
				this.y = y;
				this.z = z;
			}
		}
	
		static final int FRAME_WIDTH = 800;
		static final int FRAME_HEIGHT = 800;
		Thread t;
		float angle;
		float depth = 1f;
		boolean isHold;
		float camRotX, camRotY;
		int camX = 300;
		
		Vector3[] points = new Vector3[8];
		
		class AL extends KeyAdapter {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_W)
					depth += 0.025;
				if(e.getKeyCode()==KeyEvent.VK_S)
					depth -= 0.025;
				if(e.getKeyCode()==KeyEvent.VK_A)
					camX -= 5;
				if(e.getKeyCode()==KeyEvent.VK_D)
					camX += 5;
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
					camRotX -= 0.1f;
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
					camRotX += 0.1f;
				if(e.getKeyCode()==KeyEvent.VK_UP)
					camRotY += 0.1f;
				if(e.getKeyCode()==KeyEvent.VK_DOWN)
					camRotY -= 0.1f;
			}
			public void keyReleased(KeyEvent e) {
				
			}
		}
		
		RunPanel() {
			  points[0] = new Vector3(-0.5f, -0.5f, -0.5f);
			  points[1] = new Vector3(0.5f, -0.5f, -0.5f);
			  points[2] = new Vector3(0.5f, 0.5f, -0.5f);
			  points[3] = new Vector3(-0.5f, 0.5f, -0.5f);
			  points[4] = new Vector3(-0.5f, -0.5f, 0.5f);
			  points[5] = new Vector3(0.5f, -0.5f, 0.5f);
			  points[6] = new Vector3(0.5f, 0.5f, 0.5f);
			  points[7] = new Vector3(-0.5f, 0.5f, 0.5f);
			
			this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
			this.setBackground(new Color(0, 0, 0));
			this.addKeyListener(new AL());
			this.setFocusable(true);
			t = new Thread(this);
			t.start();
		}
		
		public void paint(Graphics g) {
			super.paint(g);		
			g.translate(camX, FRAME_HEIGHT / 2);				

			float[][] rotationX = {
	             {1, 0, 0},
	             {0, (float) Math.cos(camRotY), (float) -Math.sin(camRotY)},
	             {0, (float) Math.sin(camRotY), (float) Math.cos(camRotY)},
	        };

			float[][] rotationY = {
                 {(float) Math.cos(camRotX), 0, (float) Math.sin(camRotX)},
                 {0, 1, 0},
                 {(float) -Math.sin(camRotX), 0, (float) Math.cos(camRotX)},
            };
			
			float[][] rotationZ = {
				{(float) Math.cos(angle), (float) -Math.sin(angle), 0},
				{(float) Math.sin(angle), (float) Math.cos(angle), 0},
				{0, 0, 1}
			};
			
			g.setColor(Color.white);
			List<Vector3> projectedPoints = new ArrayList<Vector3>();
			for(Vector3 v : points) {
				Vector3 rotated = matrixMultiplicationVec(rotationY, v);
				rotated = matrixMultiplicationVec(rotationX, rotated);
				//rotated = matrixMultiplicationVec(rotationZ, rotated);
				float[][] projection = {
						{depth,0,0},
						{0,depth,0},
						{0,0,0}
				};
				Vector3 projected2D = matrixMultiplicationVec(projection, rotated);
				projected2D.x *= 200; 
				projected2D.y *= 200; 
				projected2D.z *= 200; 
				projectedPoints.add(projected2D);
				g.setColor(new Color(150, 150, 150));
				g.fillOval((int)projected2D.x-5, (int)projected2D.y-5, 10, 10);
			}
			
			drawAllFaces(projectedPoints, g);
			
		/*
			for(int i = 0; i < 4; i++) {
				connectLine(i, (i + 1) % 4, g, projectedPoints);
				connectLine(i + 4, ((i + 1) % 4) + 4, g, projectedPoints);
				connectLine(i, i + 4, g, projectedPoints);
			}
		*/
			
			angle += 0.01;
		}
		
		public void drawAllFaces(List<Vector3> pp, Graphics g) {
			int def = 50;
			int[] topFaceX = {(int) pp.get(0).x, (int) pp.get(1).x, (int) pp.get(5).x, (int) pp.get(4).x};
			int[] topFaceY = {(int) pp.get(0).y, (int) pp.get(1).y, (int) pp.get(5).y, (int) pp.get(4).y};
			drawFace(g, new Color(255, 255, def, 100), topFaceX, topFaceY);
			
			int[] bottomX = {(int) pp.get(3).x, (int) pp.get(2).x, (int) pp.get(6).x, (int) pp.get(7).x};
			int[] bottomY = {(int) pp.get(3).y, (int) pp.get(2).y, (int) pp.get(6).y, (int) pp.get(7).y};
			drawFace(g, new Color(def, def, 255, 100), bottomX, bottomY);

			int[] rightX = {(int) pp.get(1).x, (int) pp.get(2).x, (int) pp.get(6).x, (int) pp.get(5).x};
			int[] rightY = {(int) pp.get(1).y, (int) pp.get(2).y, (int) pp.get(6).y, (int) pp.get(5).y};
			drawFace(g, new Color(def, def*4, def, 100), rightX, rightY);
			
			int[] leftX = {(int) pp.get(0).x, (int) pp.get(4).x, (int) pp.get(7).x, (int) pp.get(3).x};
			int[] leftY = {(int) pp.get(0).y, (int) pp.get(4).y, (int) pp.get(7).y, (int) pp.get(3).y};
			drawFace(g, new Color(def*4, def, def, 100), leftX, leftY);
			
			int[] backX = {(int) pp.get(4).x, (int) pp.get(5).x, (int) pp.get(6).x, (int) pp.get(7).x};
			int[] backY = {(int) pp.get(4).y, (int) pp.get(5).y, (int) pp.get(6).y, (int) pp.get(7).y};
			drawFace(g, new Color(def, def, def, 100), backX, backY);
			
			int[] frontX = {(int) pp.get(0).x, (int) pp.get(1).x, (int) pp.get(2).x, (int) pp.get(3).x};
			int[] frontY = {(int) pp.get(0).y, (int) pp.get(1).y, (int) pp.get(2).y, (int) pp.get(3).y};
			drawFace(g, new Color(def, def, def, 100), frontX, frontY);
			
		}
		
		public void drawFace(Graphics g, Color c, int[] x, int[] y) {
			g.setColor(c);
			g.fillPolygon(x, y, 4);
		}
		
		public void connectLine(int i, int j, Graphics g, List<Vector3> list) {
			Vector3 a = list.get(i);
			Vector3 b = list.get(j);
			g.setColor(Color.white);
			g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
		}
		
		public float[][] vecToMatrix(Vector3 v) {
			float[][] m = new float[3][3];
			m[0][0] = v.x;
			m[1][0] = v.y;
			m[2][0] = v.z;
			return m;
		}
		
		public Vector3 matrixToVec(float[][] m) {
			Vector3 v = new Vector3(
						m[0][0],
						m[1][0],
						m[2][0]
					);
			return v;
		}
		
		public Vector3 matrixMultiplicationVec(float[][] a, Vector3 b) {
			float[][] m = matrixMultiplication(a, b);
			return matrixToVec(m);
		}
		
		public Vector3 matrixMultiplicationVec(float[][] a, float[][] b) {
			float[][] m = matrixMultiplication(a, b);
			return matrixToVec(m);
		}

		public float[][] matrixMultiplication(float[][] a, Vector3 b) {
			float[][] m = vecToMatrix(b);
			return matrixMultiplication(a, m);
		}
		
		public float[][] matrixMultiplication(float[][] a, float[][] b) {
			int colsA = a[0].length;
			int rowsA = a.length;
			int colsB = b[0].length;
			int rowsB = b.length;
			if(colsA != rowsB) {
				System.out.println("A must be yes to B");
				return null;
			}
			float result[][] = new float[rowsA][colsB];
			for(int i = 0; i < rowsA; i++) {
				for(int j = 0; j < colsB; j++) {
					float sum = 0;
					for(int k = 0; k < colsB; k++) {
						sum += a[i][k] * b[k][j];
					}
					result[i][j] = sum;
				}
			}
			return result;
		}
		
		@Override
		public void run() {
			long last = System.nanoTime();
			double fps = 60.0;
			double ns = 1000000000 / fps;
			double delta = 0.0;
			while(true) {
				long now = System.nanoTime();
				delta += (now - last) / ns;
				last = now;
				if(delta >= 1) {
					repaint();
					delta--;
				}
			}
		}
	}
