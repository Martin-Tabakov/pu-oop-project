import Panels.Board.Tile;
import UI.MainWindow;

public class Application {
    public static void main(String[] args) {
        Tile.changeSize(75, 75);
        MainWindow mainWindow = new MainWindow(7, 9);
    }
}