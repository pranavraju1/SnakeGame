import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class Board extends JPanel implements ActionListener {
    // initialze snake game
    int B_HEIGHT = 400;
    int B_WIDTH = 400;
    int MAX_DOTS = 1600;
    int DOT_SIZE = 10;// size of each dot
    int DOT = 3;// initial no. of dots for snake
    int x[] = new int[MAX_DOTS];// the maximum size of the snake is the size of the frame
    int y[] = new int[MAX_DOTS];
    int apple_x;// initial coordinates ofapple
    int apple_y;

    Image body, head, apple;// creating the object for the images

    Timer timer;
    int DELAY = 150;// decreasing the delay increases the speed of the snake

    boolean leftDirection = false;
    boolean rightDirection = true;// we set it to true so that the snake start moving left to right at the beging
                                  // of the game
    boolean upDirection = false;
    boolean downDirection = false;

    boolean inGame = true;// to check if you are still in the game

    Board() {// the cont for the methods
        TAdaptor tAdaptor = new TAdaptor();
        addKeyListener(tAdaptor);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setBackground(Color.BLACK);
        initGame();
        loadImages();
    }

    // intitialize game
    public void initGame() {
        DOT = 3;
        // initialize snake position
        x[0] = 250;
        y[0] = 250;
        for (int i = 0; i < DOT; i++) {
            x[i] = x[0] + DOT_SIZE * i;// this is when you want the snake to start from a horizontal position in case
                                       // of vertical we will apply it to y
            y[i] = y[0];
        }

        // initialize apple's position
        locateApple();

        // fot timer
        timer = new Timer(DELAY, this);// the listener here is this
        timer.start();
    }

    // load images from resources folder to image objects
    public void loadImages() {
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }

    // draw images at at snakes and apples position
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    // draw image
    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);// here this is an observer which our board class
            for (int i = 0; i < DOT; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }
        else{
            gameOver(g);

            timer.stop();
        } 
    }

    // randomizing the apple's position
    public void locateApple() {
        apple_x = ((int) (Math.random() * 39)) * DOT_SIZE;// the random function generates a number from 0 to 1 so here
                                                          // we multiply it with 39 making it the limt
        apple_y = ((int) (Math.random() * 39)) * DOT_SIZE;// therefore here the limit would be [0 to 39]
    }

    // check collisions with border and body
    public void checkCollision() {
        for (int i = 1; i < DOT; i++) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i])
                inGame = false;// BODY
        }
        if (x[0] < 0 || x[0] >= B_WIDTH)
            inGame = false; // BORDER
        if (y[0] < 0 || y[0] >= B_HEIGHT)
            inGame = false;
    }

    //game over
    public void gameOver(Graphics g){
        String msg="Game Over";
        int score=(DOT-3)*10;
        String scoreMsg="Score: "+Integer.toString(score);
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontMetrics.stringWidth(msg))/2,B_HEIGHT/4);
        // in above the msg should start from the height/4 which is te y coordinate
        // in case of x we have: x + length of msg + x =width => 2x+length(msg)=width => x=(width-legth(msg))/2
        g.drawString(scoreMsg,(B_WIDTH-fontMetrics.stringWidth(scoreMsg))/2,3*B_HEIGHT/4);
        //in the above the height will be at 3height/4
    }


    @Override
    public void actionPerformed(ActionEvent actionevent) {// all the functions below are implementd when the timer
                                                          // increaments
        if (inGame) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    // make snake move
    public void move() {
        for (int i = DOT - 1; i > 0; i--) {// here we start from the last dot
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (leftDirection) {// we change the moment of head
            x[0] -= DOT_SIZE;
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
    }

    // make the snake eat apple
    public void checkApple() {
        if (apple_x == x[0] && apple_y == y[0]) {
            DOT++;
            locateApple();
        }
    }

    // Implements contrls
    private class TAdaptor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && !downDirection) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && !upDirection) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
