
import javax.swing.*;
import java.awt.*;

public class FourierMain extends JFrame {
	DrawPanel panel;
	FourierMain() {
		panel = new DrawPanel();
		this.add(panel);
		this.setTitle("Fourier Series Challenge");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new FourierMain();
	}

}
