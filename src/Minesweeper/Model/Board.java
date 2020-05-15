package Minesweeper.Model;
import javax.swing.*;
import java.util.Random;


public class Board {
    private JFrame window; //Main window
    private Button[][] buttons; //2D array of buttons


    //Constructor - creates view, controller, and buttons
    public Board() {
        window = new JFrame(GameSettings.windowTitle);
        createButtons();
    }

    private void createButtons() {
        buttons = new Button[GameSettings.noOfButtons][GameSettings.noOfButtons];
        for(int i = 0; i < GameSettings.noOfButtons; i++) {
            for(int j = 0; j < GameSettings.noOfButtons; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setName("Button" + String.valueOf(i) + "_" + String.valueOf(j));
                buttons[i][j].setFieldType(FieldTypes.EMPTY);
            }
        }

        placeBombs();
    }

    public void placeBombs() {
        Random random = new Random();
        int x, y;
        int bombsPlaced = 0;

        //Until bombsPlaced = noOfBombs
        while (bombsPlaced < GameSettings.noOfBombs) {
            x = random.nextInt(GameSettings.noOfButtons);
            y = random.nextInt(GameSettings.noOfButtons);
            if (buttons[x][y].getFieldType() != FieldTypes.BOMB) {
                buttons[x][y].setFieldType(FieldTypes.BOMB);
                bombsPlaced++;
            }
        }
    }

    //Getters
    public JFrame getWindow() {
        return window;
    }

    public Button[][] getButtons() {
        return buttons;
    }
}
