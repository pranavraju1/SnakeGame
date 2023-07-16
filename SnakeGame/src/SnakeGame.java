import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;

    SnakeGame() {
        board = new Board();
        add(board);// here we add the board to the frame
        pack();// this alows it to get the dimensions ie 400x400 from board class
        setResizable(false);// when we use the pack func we need to use this also because here it should not
                            // be resizble by the user
        setVisible(true);// now the jFrame would not be hidden anymore
    }

    public static void main(String[] args) throws Exception {
        // initializing snake game
        SnakeGame snakeGame = new SnakeGame();
    }
}
