package cmpt276.as3.lemonfarm.gameLogic;

import java.util.ArrayList;

/**
 * Has two array lists, column and row, that keep track of
 * the number of lemons in the column and row of
 * a specified button
 */

public class hintBox {
    private optionManager manager = optionManager.getInstance();
    private random randomManager = random.getInstance();
    private ArrayList<Integer> hintBoxRow;
    private ArrayList<Integer> hintBoxColumn;
    private static hintBox hintBox;

    private hintBox(){
        hintBoxRow = new ArrayList<>();
        hintBoxColumn = new ArrayList<>();
    }

    public static hintBox getInstance(){
        if (hintBox == null){
            hintBox = new hintBox();
        }
        return hintBox;
    }

    public ArrayList<Integer> hintRowVal(){
        final int column = Integer.parseInt(manager.getMyOption().get(0).getBoardColumn());
        final int row = Integer.parseInt(manager.getMyOption().get(0).getBoardRow());
        final int numb = Integer.parseInt(manager.getMyOption().get(0).getNumbOfLemon());

        int j=0;
        int index = 0;
        hintBoxRow.clear();

        for(int i =0; i<row; i++){
            int count = 0;
            while(j<numb){
                if (index <= randomManager.getRandom().get(j) && randomManager.getRandom().get(j) < column*(i+1)){
                    count++;
                    j++;
                }else{
                    index = column*(i+1);
                    break;
                }
            }
            hintBoxRow.add(i, count);
        }
        return hintBoxRow;
    }

    public ArrayList<Integer> hintColumnVal(){
        final int column = Integer.parseInt(manager.getMyOption().get(0).getBoardColumn());
        final int row = Integer.parseInt(manager.getMyOption().get(0).getBoardRow());
        final int numb = Integer.parseInt(manager.getMyOption().get(0).getNumbOfLemon());

        hintBoxColumn.clear();

        for(int i =0; i<column; i++){
            int index = i;
            int count = 0;
            int j=0;
            while(j<numb){
                if (index == randomManager.getRandom().get(j)){
                    count++;
                    j++;
                    index = index + column;
                }else if (randomManager.getRandom().get(j) > index){
                    index = index + column;
                }else if (randomManager.getRandom().get(j) < index){
                   j++;
                }
                if (index > column*(row-1)+i){
                    break;
                }
            }
            hintBoxColumn.add(i, count);
        }
        return hintBoxColumn;
    }
}
