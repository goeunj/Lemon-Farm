package cmpt276.as3.lemonfarm.gameLogic;

/**
 * Option class that sets the number of
 * rows and columns wanted in the game board view
 *
 * Also sets the number of lemons the user chooses to find
 */

public class option{
    private String boardRow;
    private String boardColumn;
    private String numbOfLemon;

    public option(String row, String column, String numb){
        this.boardRow = row;
        this.boardColumn = column;
        this.numbOfLemon = numb;
    }

    public String getBoardRow(){
        return this.boardRow;
    }

    public String getBoardColumn(){
        return this.boardColumn;
    }

    public String getNumbOfLemon(){
        return this.numbOfLemon;
    }

}

