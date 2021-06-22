package cmpt276.as3.lemonfarm.gameLogic;

import java.util.ArrayList;

/**
 * A manager for option class
 * Lets activities access user options choices
 */

public class optionManager {
    private ArrayList<option> myOption = new ArrayList<>();
    private static optionManager chosenOption;

    private optionManager(){
        myOption.add(0, new option("4", "6", "6"));
    }

    public static optionManager getInstance(){
        if (chosenOption == null){
            chosenOption = new optionManager();
        }
        return chosenOption;
    }
    public ArrayList<option> getMyOption(){
        return this.myOption;
    }
}

