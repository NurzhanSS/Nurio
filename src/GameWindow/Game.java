package GameWindow;

import javax.swing.JFrame;

public class Game {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Nurio");
        GPanel panel = new GPanel();
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

}
