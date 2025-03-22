import javax.swing.*;

class Tile extends JButton {
    Integer row;
    Integer col;

    Tile(Integer row, Integer col) {
        this.row=row;
        this.col=col;
    }
}
