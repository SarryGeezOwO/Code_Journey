
import javax.swing.*;
import java.awt.*;

public class Engine extends JFrame {
	
	Engine() {
		this.add(new SimulationPanel());
		this.setTitle("SandSimulation Cellular Automata version");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Engine();
	}

}
