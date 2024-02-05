
import javax.swing.*;

public class Renderer3D extends JFrame{

	Renderer3D() {
		this.add(new RunPanel());
		this.setTitle("3D Renderer");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Renderer3D();
	}
}
