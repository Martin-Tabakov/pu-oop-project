package UI;

import Board.*;
import Team.*;

import java.awt.Graphics;
import javax.swing.JFrame;

public class MainWindow extends JFrame {

    private final int width;
    private final int height;

    Draw draw;
    Board board;
    TeamManager teamManager = new TeamManager();

    public MainWindow(int width,int height) {
        this.width = width;
        this.height = height;
        this.board = new Board(this.width, this.height);
        this.draw = new Draw();

        this.setSize((this.width+2)*Tile.width,(this.height+2)*Tile.height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw.setGraphic(g);
        draw.drawBoard(board);
        draw.drawTeam(teamManager.north);
        draw.drawTeam(teamManager.south);
    }
}