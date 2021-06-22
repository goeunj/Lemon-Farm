package cmpt276.as3.lemonfarm.gameLogic;

/**
 * store info about single score including:
 * a string of nicknames
 * score
 * date
 */
public class score {
    private String nickname;
    private String score;
    private String gameMode;

    public score(String nickname, String score, String mode){
        this.nickname = nickname;
        this.score = score;
        this.gameMode = mode;
    }

    public String getNickname(){
        return this.nickname;
    }
    public String getScore() {
        return this.score;
    }
    public String getGameMode(){
        return this.gameMode;
    }
}
