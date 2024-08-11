import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new DrawPanel());
        pack();
        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);
    }

    public static void main(String[] args) { 
        new MainFrame();
    } 
} 
