import javax.swing.*;
import java.awt.*;


public class SandSimulator extends JFrame {
	SimulationPanel pane;
	SandSimulator() {
		pane = new SimulationPanel();
		this.add(pane);
		this.setTitle("Sand Simulation ver.2.0");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBackground(new Color(0X110e1f));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new SandSimulator();
	}

}
