import javax.swing.*;

public class gameFrame extends JFrame{

    //Constructor Function of gameFrame class
    gameFrame() {

        //Instance of gamePanel class
        this.add(new gamePanel());
        //To set title of game
        this.setTitle("Snake Game!!");

        //Frame operations & adjustments
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

        //If we want to open the game window at the center of our screen
        this.setLocationRelativeTo(null);
    }
}
