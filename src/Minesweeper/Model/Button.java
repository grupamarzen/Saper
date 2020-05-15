package Minesweeper.Model;
import Minesweeper.Model.FieldTypes;

import javax.swing.*;


public class Button extends JButton {
    private FieldTypes fieldType;


    //Getters, setters:
    public FieldTypes getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldTypes fieldType) {
        this.fieldType = fieldType;
    }
}
