package UI;

import Board.Board;
import java.awt.Graphics;
import javax.swing.JFrame;

public class MainWindow extends JFrame {
    Draw draw;

    public MainWindow() {
        this.setSize(1100, 900);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.draw = new Draw();
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.draw.setGraphic(g);
        this.draw.drawBoard(new Board(9, 7));
    }
}