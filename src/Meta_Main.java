
import javax.swing.*;

public class Meta_Main extends JFrame {

	Meta_Main() {
		this.add(new Metaball());
		this.setTitle("Metaball Simulation");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
		
		
	}
	
	public static void main(String[] args) {
		new Meta_Main();
	}

}
