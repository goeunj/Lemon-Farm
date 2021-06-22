package cmpt276.as3.lemonfarm.gameLogic;

import android.annotation.SuppressLint;
import android.widget.Button;

/**
 * Class with main game logic
 *
 * Sets row and column val
 *       decreases rowVal and columnVal each time a lemon is found in its row and column
 *       used as print value for hint box/buttons when clicked
 * Sets hint value
 *      finds the index at which the button is clicked
 *      able to set button text with correct values with this index
 * Getters for activity importing this class to get the desired values
 */

public class gameManager {
    private optionManager manager = optionManager.getInstance();
    private hintBox hintManager = hintBox.getInstance();
    private rowColumnFound foundManager = rowColumnFound.getInstance();
    private random randomManager = random.getInstance();

    private Integer columnVal, rowVal;
    private Integer hintRow, hintColumn;
    private static gameManager game;

    private gameManager(){
        columnVal = null;
        rowVal = null;

        hintRow = null;
        hintColumn = null;
    }

    public static gameManager getInstance() {
        if (game == null) {
            game = new gameManager();
        }
        return game;
    }

    public void setRowColumnFound() {
        foundManager.setRowValFound();
        foundManager.setColumnValFound();
    }

    public void setRowColumnVal(Button button) {
        if (randomManager.getRandom().contains(button.getId())) {
            foundManager.addRowIndexVal(this.hintRow, 1);
            foundManager.addColumnIndexVal(this.hintColumn, 1);
        }
        this.rowVal = hintManager.hintRowVal().get(this.hintRow) - foundManager.getRowValFound().get(this.hintRow);
        this.columnVal = hintManager.hintColumnVal().get(this.hintColumn) - foundManager.getColumnValFound().get(this.hintColumn);
    }

    @SuppressLint("ResourceType")
    public void setHintVal(Button button) {
        final int row = Integer.parseInt(manager.getMyOption().get(0).getBoardRow());
        final int column = Integer.parseInt(manager.getMyOption().get(0).getBoardColumn());

        int m = 0, index = m;
        while (m < row) {
            if (index <= button.getId() && button.getId() < column * (m + 1)) {
                this.hintRow = m;
            }
            index = column * (m + 1);
            m++;
        }

        int n = 0, index1 = n;
        while (n < column) {
            if (index1 < button.getId()) {
                index1 = index1 + column;
            } else if (index1 > button.getId()) {
                n++;
                index1 = n;
            } else if (index1 == button.getId()) {
                this.hintColumn = n;
                break;
            }
        }
    }

    public Integer getRowVal(){
        return this.rowVal;
    }

    public Integer getColumnVal(){
        return this.columnVal;
    }

    public Integer getHintRow(){
        return this.hintRow;
    }

    public Integer getHintColumn(){
        return this.hintColumn;
    }
}
