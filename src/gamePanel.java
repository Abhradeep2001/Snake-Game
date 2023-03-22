import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener {
    //Setting screen width
    static final int SCREEN_WIDTH=700;
    //Setting screen heigth
    static final int SCREEN_HEIGTH=700;
    //Setting object size in this game
    static final int UNIT_SIZE=20;
    //To calculate how many objects can we fit within the game screen
    static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_HEIGTH)/UNIT_SIZE;
    //To set Delay (More the delay, less will be the movement of the snake
    static final int DELAY=50;
    //Array's to hold the co-ordinates(x,y) of all the body parts of the snake including its head
    final int[]x=new int[UNIT_SIZE];
    final int[]y=new int[UNIT_SIZE];
    int bodyParts=6;

    int foodEaten=0;
    //Co-ordinates(x,y) of food item
    int foodX, foodY;
    //Setting Up the initial direction of the snake
    char direction= 'R';  //To move Right
    boolean running=false;
    //Instance of Timer class
    Timer timer;
    //Instance of Random class
    Random random;

    //Constructor function of gamePanel class
    gamePanel(){
        random=new Random();;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGTH));
        this.setBackground(new Color(0,25,25));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    //Function start the game
    public void startGame(){
        newFood();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            //To set the color of the food
            g.setColor(Color.YELLOW);
            //To draw the food
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
            //To draw the Snake
            for (int i = 0; i < bodyParts; i++) {
                //To draw the Head of our snake (index=0)
                if (i == 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                //To draw the Body of our snake
                else {
                    g.setColor(Color.cyan);
                    //To generate a random color for the body of our snake(optional)
//                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Setting the Score Text(To keep count of Total Score)
            g.setColor(Color.GREEN);
            g.setFont(new Font("Ink Free",Font.BOLD,55));

            //To align the text using font metrics method
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score: "+foodEaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+foodEaten))/2, g.getFont().getSize());
        }
        else{
            //To stop the game
            gameOver(g);
        }

    }
    public void newFood(){
        //Setting the size of the food
        foodX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        foodY=random.nextInt((int)(SCREEN_HEIGTH/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
        for(int i=bodyParts;i>0;i--){
            //To move the snake horizontally
            x[i]=x[i-1];
            //To move the snake vertically
            y[i]=y[i-1];
        }
        //To change the direction of movement
        switch (direction){
            //Move Up
            case 'U': y[0]=y[0]-UNIT_SIZE;
            break;
            //Move Down
            case 'D': y[0]=y[0]+UNIT_SIZE;
            break;
            //Move Left
            case 'L': x[0]=x[0]-UNIT_SIZE;
            break;
            //Move Right
            case 'R': x[0]=x[0]+UNIT_SIZE;
            break;
        }
    }
    public void checkFood(){
        //To check if the Head position of snake is equal to the co-ordinates of the food
        if((x[0] == foodX) && (y[0] == foodY)){
            bodyParts++; //Increase the length of the snake
            foodEaten++;
            //To generate new food
            newFood();

        }

    }
    public void checkCollisions(){
        //To check if head collides with the body of the snake
        for(int i=bodyParts;i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running=false;
            }
        }
        //To check if head touches left border
        if(x[0]<0){
            running=false;
        }
        //To check if head touches right border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        //To check if head touches top border
        if(y[0]<0){
            running=false;
        }
        //To check if head touches bottom border
        if(y[0]>SCREEN_HEIGTH){
            running=false;
        }
        //To stop the timer after collision
        if(!running){
            timer.stop();
        }

    }
    //Function when game is over
    public void gameOver(Graphics g){
        //To display Total Score at the End
        g.setColor(Color.GREEN);
        g.setFont(new Font("Ink Free",Font.BOLD,55));

        //To align the text using font metrics method
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score: "+foodEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score: "+foodEaten))/2, g.getFont().getSize());

        //Setting the Game Over Text
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Ink Free",Font.BOLD,65));

        //To align the text using font metrics method
        FontMetrics metrics2=getFontMetrics(g.getFont());
        g.drawString("GAME OVER!!",(SCREEN_WIDTH-metrics2.stringWidth("GAME OVER!!"))/2, SCREEN_HEIGTH/2);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        //TODO Auto generated list
        if(running){
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }
    //Inner Class To Actually Control the Snake
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            //Switch to examine the e key event
            switch (e.getKeyCode()){
                //Handle the left direction movement
                case KeyEvent.VK_LEFT :
                    if(direction!='R'){
                        direction='L';
                    }
                    break;
                //Handle the right direction movement
                case KeyEvent.VK_RIGHT :
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
                //Handle the up direction movement
                case KeyEvent.VK_UP :
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                //Handle the down direction movement
                case KeyEvent.VK_DOWN :
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
            }

        }
    }
}
