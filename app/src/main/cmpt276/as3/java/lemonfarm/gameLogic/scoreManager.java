package cmpt276.as3.lemonfarm.gameLogic;

import java.util.ArrayList;

/**
 * store a collection of scores
 * support setting existing scores with new score values
 * removes duplicates
 *
 * singleton score manager
 */

public class scoreManager {
    //myScore stores a collection of scores
    private ArrayList<score> myScore;
    private static scoreManager scoreList;

    scoreManager(){
        myScore = new ArrayList<>();
    }

    public static scoreManager getInstance(){
        if (scoreList == null){
            scoreList = new scoreManager();
        }
        return scoreList;
    }

    //sets new score
    public void setNewScore(String nickname, String score, String gameMode){
        if (Integer.parseInt(myScore.get(0).getScore()) >= Integer.parseInt(score)){
            myScore.set(0, new score(nickname, score, gameMode));

        }else{
            int i=4;
            while (i > 0){
                if (Integer.parseInt(myScore.get(i).getScore()) >= Integer.parseInt(score) && Integer.parseInt(myScore.get(i-1).getScore()) < Integer.parseInt(score)){
                    myScore.set(i, new score(nickname, score, gameMode));
                    break;
                }
                i--;
            }
        }
    }

    public void removeDuplicates(){
        myScore.remove(9);
        myScore.remove(8);
        myScore.remove(7);
        myScore.remove(6);
        myScore.remove(5);
    }

    public ArrayList<score> getMyScore() {
        return this.myScore;
    }
}
