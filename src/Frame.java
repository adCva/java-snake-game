import javax.swing.JFrame;

public class Frame extends JFrame {
    
    Frame() {
        this.add(new Panel());
        this.setTitle("Snake");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}