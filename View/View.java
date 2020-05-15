package Minesweeper.View;
import Minesweeper.Model.Board;
import Minesweeper.Model.GameSettings;
import javax.swing.*;


public class View {
    private Board board; //Reference to the board

    //Constructor
    public View(Board board) {
        this.board = board;
        createWindow();
        addButtons();
    }

    //Creating main window
    private void createWindow() {
        JFrame window = board.getWindow();

        //Window settings
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(GameSettings.windowSize, GameSettings.windowSize + 32);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setVisible(true);
    }

    //Add buttons to the main window
    private void addButtons() {
        JFrame window = board.getWindow();
        JButton[][] buttons = board.getButtons();
        int buttonSize = GameSettings.windowSize / GameSettings.noOfButtons;

        for(int x = 0; x < GameSettings.noOfButtons; x++) {
            for(int y = 0; y < GameSettings.noOfButtons; y++) {
                //Button settings
                buttons[x][y].setLayout(null);
                buttons[x][y].setBackground(GameSettings.buttonEnabledColor);
                buttons[x][y].setSize(buttonSize, buttonSize);
                buttons[x][y].setLocation(x * buttonSize, y * buttonSize);
                buttons[x][y].setVisible(true);

                window.add(buttons[x][y]);
            }
        }
    }
}
