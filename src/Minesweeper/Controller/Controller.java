package Minesweeper.Controller;
import Minesweeper.Model.Board;
import Minesweeper.Model.FieldTypes;
import Minesweeper.Model.GameSettings;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller implements ActionListener {
    private Board board; //Reference to model

    public Controller(Board board) { //Konstruktor
        this.board = board;

        for(int i = 0; i < GameSettings.noOfButtons; i++) {
            for(int j = 0; j < GameSettings.noOfButtons; j++) {
                board.getButtons()[i][j].addActionListener(this);
            }
        }
    }

    //On button clicked:
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();
        String name = source.getName().substring(6, source.getName().length()); //e.g. Button1_1 => 1_1
        String coords[] = name.split("_"); //e.g. 1_1 => {1, 1}
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        onButtonClicked(x, y);
    }

    public void onButtonClicked(int x, int y) {
        if(board.getButtons()[x][y].getFieldType() == FieldTypes.BOMB) { //Bomb
            board.getButtons()[x][y].setText("B!");
            gameOver();
        }
        else {
            int bombs = countBombsInNeighborhood(x, y);
            if (bombs > 0) {
                disableButton(x, y);
                board.getButtons()[x][y].setText(String.valueOf(bombs));
            } else {
                checkNeighbours(x, y);
            }

            if(countActiveFields() == GameSettings.noOfBombs) //Won
                victory();
        }
    }

    private int countBombsInNeighborhood(int x, int y) {
        int bombs = 0;
        for(int xn = -1; xn <= 1; xn++) {
            for(int yn = -1; yn <= 1; yn++) {
                if(x + xn >= 0 && x + xn < GameSettings.noOfButtons &&
                        y + yn >= 0 && y + yn < GameSettings.noOfButtons) {

                    if(board.getButtons()[x+xn][y+yn].getFieldType() == FieldTypes.BOMB)
                        bombs++;
                }
            }
        }

        return bombs;
    }

    //Recursively reveals fields which aren't next to the bomb
    private void checkNeighbours(int x, int y) {
        if(!board.getButtons()[x][y].isEnabled())
            return;

        disableButton(x, y);

        for(int xn = -1; xn <= 1; xn++) {
            for (int yn = -1; yn <= 1; yn++) {
                if(x + xn >= 0 && x + xn < GameSettings.noOfButtons &&
                        y + yn >= 0 && y + yn < GameSettings.noOfButtons &&
                        (Math.abs(xn) != Math.abs(yn))) {

                    int bombs = countBombsInNeighborhood(x + xn, y + yn);
                    if(bombs > 0)
                        board.getButtons()[x+xn][y+yn].setText(String.valueOf(bombs));
                    else
                        checkNeighbours(x+xn, y+yn);

                    disableButton(x + xn, y + yn);
                }
            }
        }
    }

    private void disableButton(int x, int y) {
        board.getButtons()[x][y].setBackground(GameSettings.buttonDisabledColor);
        board.getButtons()[x][y].setEnabled(false);
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(board.getWindow(),  "Game Over!");
        restartGame();
    }

    private void victory() {
        JOptionPane.showMessageDialog(board.getWindow(),  "You won!");
        restartGame();
    }

    //Returns number of active fields
    private int countActiveFields() {
        int remaining = 0;

        for(int i = 0; i < GameSettings.noOfButtons; i++) {
            for (int j = 0; j < GameSettings.noOfButtons; j++) {
                if(board.getButtons()[i][j].isEnabled())
                    remaining++;
            }
        }

        return remaining;
    }

    private void restartGame() {
        //Reset buttons
        for(int i = 0; i < GameSettings.noOfButtons; i++) {
            for (int j = 0; j < GameSettings.noOfButtons; j++) {
                board.getButtons()[i][j].setEnabled(true);
                board.getButtons()[i][j].setBackground(GameSettings.buttonEnabledColor);
                board.getButtons()[i][j].setText("");
                board.getButtons()[i][j].setFieldType(FieldTypes.EMPTY);
            }
        }

        board.placeBombs();
    }
}
