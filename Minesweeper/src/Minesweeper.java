import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Minesweeper {

    Integer NUM_ROWS;
    Integer NUM_COLS;
    Integer TILE_SIZE;
    int height=700;
    int width=700;

    JFrame frame = new JFrame();
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    Tile[][] board;
    int mineCount;
    int tilesClicked=0;

    ArrayList<Tile> mineList;
    boolean gameOver = false;
    Random rand=new Random();

    Minesweeper(int rh,int ch,int mines){
        NUM_ROWS=rh;
        NUM_COLS=ch;
        mineCount=mines;
        TILE_SIZE=height/NUM_ROWS;
        board=new Tile[NUM_ROWS][NUM_COLS];
        frame.setTitle("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setText("Minesweeper");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        buttonPanel.setLayout(new GridLayout(NUM_ROWS,NUM_COLS));
        buttonPanel.setBackground(Color.GREEN);
        frame.add(buttonPanel);

        for(int row=0;row<NUM_ROWS;row++){
            for(int col=0;col<NUM_COLS;col++){
                Tile tile=new Tile(row,col);
                board[row][col]=tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0,0,0,0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 30));
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) {
                            return;
                        }
                        Tile tile = (Tile) e.getSource();

                        //left click
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if ("".equals(tile.getText())) {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                }
                                else {
                                    checkMine(tile.row, tile.col);
                                }
                            }
                        }
                        //right click
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            if ("".equals(tile.getText()) && tile.isEnabled()) {
                                tile.setText("🚩");
                            }
                            else if ("🚩".equals(tile.getText())) {
                                tile.setText("");
                            }
                        }
                    }
                });

                buttonPanel.add(tile);

            }
        }

        frame.setVisible(true);

        setMines();
    }

    void setMines() {
        mineList = new ArrayList<>();
        int mineLeft = mineCount;
        while (mineLeft > 0) {
            int r = rand.nextInt(NUM_ROWS); //0-7
            int c = rand.nextInt(NUM_COLS);

            Tile tile = board[r][c];
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
    }

    void revealMines() {
        for (Tile tile : mineList) {
            tile.setText("💣");
        }

        gameOver = true;
        textLabel.setText("Game Over!");
    }

    int countMine(int r, int c) {
        if (r < 0 || r >= NUM_ROWS || c < 0 || c >= NUM_COLS) {
            return 0;
        }
        if (mineList.contains(board[r][c])) {
            return 1;
        }
        return 0;
    }

    void checkMine(int r, int c) {
        if (r < 0 || r >= NUM_ROWS || c < 0 || c >= NUM_COLS) {
            return;
        }

        Tile tile = board[r][c];
        if (!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false);
        tilesClicked += 1;

        int minesFound = 0;

        //top 3
        minesFound += countMine(r-1, c-1);  //top left
        minesFound += countMine(r-1, c);    //top
        minesFound += countMine(r-1, c+1);  //top right

        //left and right
        minesFound += countMine(r, c-1);    //left
        minesFound += countMine(r, c+1);    //right

        //bottom 3
        minesFound += countMine(r+1, c-1);  //bottom left
        minesFound += countMine(r+1, c);    //bottom
        minesFound += countMine(r+1, c+1);  //bottom right

        if (minesFound > 0) {
            tile.setText(Integer.toString(minesFound));
        }
        else {
            tile.setText("");

            //top 3
            checkMine(r-1, c-1);    //top left
            checkMine(r-1, c);      //top
            checkMine(r-1, c+1);    //top right

            //left and right
            checkMine(r, c-1);      //left
            checkMine(r, c+1);      //right

            //bottom 3
            checkMine(r+1, c-1);    //bottom left
            checkMine(r+1, c);      //bottom
            checkMine(r+1, c+1);    //bottom right
        }

        if (tilesClicked == NUM_ROWS * NUM_COLS - mineList.size()) {
            gameOver = true;
            textLabel.setText("Mines Cleared!");
        }
    }

}