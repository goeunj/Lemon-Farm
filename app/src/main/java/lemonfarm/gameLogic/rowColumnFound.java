package cmpt276.as3.lemonfarm.gameLogic;

import java.util.ArrayList;

/**
 * Has two array lists that keep track of lemons found
 * in the row and column of the corresponding hint box
 *
 * For each lemon found, increments by one
 * Instance of this class is used to keep track of new hint box values
 */

public class rowColumnFound {
    private optionManager manager = optionManager.getInstance();
    private ArrayList<Integer> rowValFound;
    private ArrayList<Integer> columnValFound;
    private static rowColumnFound foundArray;

    private rowColumnFound(){
        rowValFound = new ArrayList<>();
        columnValFound = new ArrayList<>();
    }

    public static rowColumnFound getInstance(){
        if (foundArray == null){
            foundArray = new rowColumnFound();
        }
        return foundArray;
    }

    public void setRowValFound(){
        rowValFound.clear();
        for (int i=0; i< Integer.parseInt(manager.getMyOption().get(0).getBoardRow()); i++){
            rowValFound.add(i, 0);
        }
    }

    public void setColumnValFound(){
        columnValFound.clear();
        for (int i=0; i< Integer.parseInt(manager.getMyOption().get(0).getBoardColumn()); i++){
            columnValFound.add(i, 0);
        }
    }

    public void addRowIndexVal(int index, int val){
        rowValFound.set(index, rowValFound.get(index) + val);
    }

    public void addColumnIndexVal(int index, int val){
        columnValFound.set(index, columnValFound.get(index) + val);
    }

    public ArrayList<Integer> getRowValFound(){
        return this.rowValFound;
    }

    public ArrayList<Integer> getColumnValFound(){
        return this.columnValFound;
    }
}
