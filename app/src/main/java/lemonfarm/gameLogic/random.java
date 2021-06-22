package cmpt276.as3.lemonfarm.gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * creates an array of random numbers
 * created to set random buttons to be lemons
 */

public class random {
    private optionManager manager = optionManager.getInstance();
    private ArrayList<Integer> randomArray;
    private static random numberArray;
    private static Random number = new Random();

    private random(){
        randomArray = new ArrayList<>();
    }

    public static random getInstance(){
        if (numberArray == null){
            numberArray = new random();
        }
        return numberArray;
    }

    public ArrayList<Integer> getRandom(){
        return this.randomArray;
    }

    public void setRandomArray() {
        final int column = Integer.parseInt(manager.getMyOption().get(0).getBoardColumn());
        final int row = Integer.parseInt(manager.getMyOption().get(0).getBoardRow());
        final int numb = Integer.parseInt(manager.getMyOption().get(0).getNumbOfLemon());

        randomArray.clear();
        int index = 0;
        while (index <numb){
            int randomIndex = number.nextInt(row*column);
            if (!randomArray.contains(randomIndex)) {
                randomArray.add(randomIndex);
                index++;
            }
        }
        Collections.sort(randomArray);
    }
}
