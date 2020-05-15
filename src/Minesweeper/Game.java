package Minesweeper;
import Minesweeper.Controller.Controller;
import Minesweeper.Model.Board;
import Minesweeper.View.View;


public class Game {
    private static Board board;
    private static View view;
    private static Controller controller;

    public static void main(String[] args) {
        board = new Board();
        view = new View(board);
        controller = new Controller(board);
    }
}
