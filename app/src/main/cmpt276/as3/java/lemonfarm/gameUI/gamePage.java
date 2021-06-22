package lemonfarm.gameUI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.as3.lemonfarm.R;
import cmpt276.as3.lemonfarm.gameLogic.gameManager;
import cmpt276.as3.lemonfarm.gameLogic.optionManager;
import cmpt276.as3.lemonfarm.gameLogic.random;
import cmpt276.as3.lemonfarm.music.song;
import cmpt276.as3.lemonfarm.music.winSong;

/**
 * Sets grid view
 *      sets the grid with the correct number of
 *      buttons according to optionManager
 * Sets random lemons
 *      creates new set (array list) of random lemon index
 * Sets lemon view
 *      when button clicked, either sets button image to lemon if random index or
 *      sets button text to correct number of not-yet-found lemons in its row and column
 * Sets row and column found
 *      initializes two array lists with the size of the board's row and column respectively
 * Update hint box
 *      each time lemon button is found, for previous hint box/buttons clicked
 *      that is in the row and column of the lemon button is updated with new number
 *      (decrements hint box/button text by one)
 * Sets win message
 *      has congratulatory message
 *      plays new song for set duration
 *      lemons fall from top of the screen
 * Increment methods
 *      increments count and hintBoxCount to keep track of how many lemons were found
 *      and how many hint boxes were used
 */

public class gamePage extends AppCompatActivity {
    private optionManager manager = optionManager.getInstance();
    private random randomManager = random.getInstance();
    private gameManager gameLogic = gameManager.getInstance();
    private int count, columnVal, rowVal, hintBoxCount;
    private int hintRow, hintColumn;
    MediaPlayer lostSong;
    Chronometer time;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        time = findViewById(R.id.time);
        time.setBase(SystemClock.elapsedRealtime());
        time.start();

        setGrid();
        setRandomLemons();
        lemonView();
        setRowColumnFound();
    }

    @Override
    protected void onResume(){
        super.onResume();
        startService(new Intent(gamePage.this, song.class).setAction("PLAY"));
    }

    @Override
    protected void onPause(){
        super.onPause();
        startService(new Intent(gamePage.this, song.class).setAction("PAUSE"));
        startService(new Intent(gamePage.this, winSong.class).setAction("PAUSE"));
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setGrid() {
        int id = 0;
        final int column = Integer.parseInt(manager.getMyOption().get(0).getBoardColumn());
        final int row = Integer.parseInt(manager.getMyOption().get(0).getBoardRow());

        GridLayout lemonFarm = findViewById(R.id.lemonFarm);
        lemonFarm.setColumnCount(column);
        lemonFarm.setRowCount(row);

        for (int i = 0; i < row; i++) {
            GridLayout.Spec rowSpec = GridLayout.spec(i, 1, 0);
            for (int j = 0; j < column; j++) {
                GridLayout.Spec columnSpec = GridLayout.spec(j, 1, 0);

                final Button button = new Button(getApplicationContext());
                button.setBackgroundResource(android.R.color.holo_orange_light);
                button.setId(id);
                button.setText("");

                GridLayout.LayoutParams buttonParams = new GridLayout.LayoutParams();
                buttonParams.height = getResources().getDisplayMetrics().heightPixels / 9;
                buttonParams.width = getResources().getDisplayMetrics().widthPixels / 17;
                buttonParams.columnSpec = columnSpec;
                buttonParams.rowSpec = rowSpec;
                buttonParams.setMargins(2, 1, 2, 1);

                lemonFarm.addView(button, buttonParams);
                id++;
            }
        }
    }

    public void incrementHintBox() {
        this.hintBoxCount = this.hintBoxCount + 1;
    }

    public void incrementCount() {
        this.count = this.count + 1;
    }

    private void setRandomLemons() {
        randomManager.setRandomArray();
    }

    public void setRowColumnFound() {
        gameLogic.setRowColumnFound();
    }

    public void setRowColumnVal(Button button) {
        gameLogic.setRowColumnVal(button);
        this.rowVal = gameLogic.getRowVal();
        this.columnVal = gameLogic.getColumnVal();
    }

    @SuppressLint("ResourceType")
    private void setHintVal(Button button) {
        gameLogic.setHintVal(button);
        this.hintRow = gameLogic.getHintRow();
        this.hintColumn = gameLogic.getHintColumn();
    }

    public void updateHintBox(Button randomButton) {
        final int column = Integer.parseInt(manager.getMyOption().get(0).getBoardColumn());
        final int row = Integer.parseInt(manager.getMyOption().get(0).getBoardRow());
        int j = this.hintColumn;

        for (int i = this.hintRow * column; i < column * (this.hintRow + 1); i++) {
            Button rowButton = findViewById(i);
            if (rowButton.isSelected() && randomButton.getId() != rowButton.getId()) {
                 rowButton.setText(String.valueOf(Integer.parseInt((String) rowButton.getText())-1));
            }
        }

        while (j <= column * (row - 1) + this.hintColumn) {
            Button columnButton = findViewById(j);
            if (columnButton.isSelected() && randomButton.getId() != columnButton.getId()) {
                    columnButton.setText(String.valueOf(Integer.parseInt((String) columnButton.getText())-1));
            }
            j = j + column;
        }
    }

    public void updateRestOfButtons(){
        final int column = Integer.parseInt(manager.getMyOption().get(0).getBoardColumn());
        final int row = Integer.parseInt(manager.getMyOption().get(0).getBoardRow());
        for (int i=0; i< row*column; i++){
            Button button = findViewById(i);
            if (!button.isSelected()){
                button.setClickable(false);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void lemonView() {
        final int column = Integer.parseInt(manager.getMyOption().get(0).getBoardColumn());
        final int row = Integer.parseInt(manager.getMyOption().get(0).getBoardRow());
        final int numb = Integer.parseInt(manager.getMyOption().get(0).getNumbOfLemon());
        final TextView score = findViewById(R.id.score);
        final TextView hintBoxUsed = findViewById(R.id.hintBoxes);

        score.setText(this.count + " " + getString(R.string.score) + " " + numb);
        hintBoxUsed.setText(this.hintBoxCount + " " + getString(R.string.hintBox) + " " + ((row * column) - numb));

        //soundPool code snippet found on stackOverflow -> button sound does not interfere with background music
        final SoundPool sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1f, 1f, 0, 0, 1);
            }
        });

        for (int i = 0; i < row*column; i++) {
            final Button button = findViewById(i);

            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    sound.load(gamePage.this, R.raw.button, 0);

                    setHintVal(button);
                    setRowColumnVal(button);

                    if (randomManager.getRandom().contains(button.getId())) {
                        button.setBackgroundResource(R.drawable.found);

                        updateHintBox(button);
                        incrementCount();

                    } else if (!randomManager.getRandom().contains(button.getId())) {
                        button.setText(String.valueOf(columnVal + rowVal));
                        button.setSelected(true);

                        incrementHintBox();
                    }
                    score.setText(count + " " + getString(R.string.score) + " " + numb);
                    hintBoxUsed.setText(hintBoxCount + " " + getString(R.string.hintBox) + " " + ((row * column) - numb));

                    if (count == numb && hintBoxCount < ((row*column)-numb)){
                        updateRestOfButtons();
                        time.stop();
                        setWinMessage(row, column, numb);
                    }else  if (count < numb && hintBoxCount == ((row*column)-numb)){
                        updateRestOfButtons();
                        time.stop();
                        setLoseMessage();
                    }

                    button.setClickable(false);
                }
            });
        }
    }

    public void setLoseMessage(){
        final TextView loseMessage = findViewById(R.id.loseMessage);
        loseMessage.setVisibility(View.VISIBLE);

        final Button agreeToLost = findViewById(R.id.agreeToLosing);
        agreeToLost.setVisibility(View.VISIBLE);

        final GridLayout lemonGrid = findViewById(R.id.lemonFarm);
        lemonGrid.setVisibility(View.INVISIBLE);

        Animation blink = new AlphaAnimation(0.0f, 1.0f);
        blink.setDuration(700);
        blink.setRepeatMode(Animation.REVERSE);
        blink.setRepeatCount(5);
        loseMessage.startAnimation(blink);

        lostSong = MediaPlayer.create(getApplicationContext(), R.raw.lost);
        lostSong.seekTo(0);
        lostSong.start();

        startService(new Intent(gamePage.this, song.class).setAction("PAUSE"));
        Toast.makeText(getApplicationContext(), R.string.agreeButtonToast, Toast.LENGTH_LONG).show();

        agreeToLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lostSong.pause();
                startService(new Intent(gamePage.this, song.class).setAction("PLAY"));
                loseMessage.setVisibility(View.INVISIBLE);
                agreeToLost.setVisibility(View.INVISIBLE);
                startActivity(new Intent(gamePage.this, menuPage.class));
            }
        });
    }

    public void setWinMessage(final int row, final int column, final int numb){
        final int score = (int)(SystemClock.elapsedRealtime() - time.getBase());

        final TextView winMessage = findViewById(R.id.winMessage);
        winMessage.setVisibility(View.VISIBLE);

        final GridLayout lemonGrid = findViewById(R.id.lemonFarm);
        lemonGrid.setVisibility(View.INVISIBLE);

        final Button addButton = findViewById(R.id.add);
        final Button cancelButton = findViewById(R.id.cancel);
        final EditText nickname = findViewById(R.id.nickname);
        addButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        nickname.setVisibility(View.VISIBLE);

        Animation blink = new AlphaAnimation(0.0f, 1.0f);
        blink.setDuration(700);
        blink.setRepeatMode(Animation.REVERSE);
        blink.setRepeatCount(5);
        winMessage.startAnimation(blink);

        final ImageView lemon1 = findViewById(R.id.fallingLemon1);
        final ImageView lemon2 = findViewById(R.id.fallingLemon2);
        final ImageView lemon3 = findViewById(R.id.fallingLemon3);
        final ImageView lemon4 = findViewById(R.id.fallingLemon4);
        final ImageView lemon5 = findViewById(R.id.fallingLemon5);
        final ImageView lemon6 = findViewById(R.id.fallingLemon6);
        final ImageView lemon7 = findViewById(R.id.fallingLemon7);
        final ImageView lemon8 = findViewById(R.id.fallingLemon8);
        final ImageView lemon9 = findViewById(R.id.fallingLemon9);
        final ImageView lemon10 = findViewById(R.id.fallingLemon10);
        final ImageView lemon11 = findViewById(R.id.fallingLemon11);

        lemon1.setVisibility(View.VISIBLE);
        lemon2.setVisibility(View.VISIBLE);
        lemon3.setVisibility(View.VISIBLE);
        lemon4.setVisibility(View.VISIBLE);
        lemon5.setVisibility(View.VISIBLE);
        lemon6.setVisibility(View.VISIBLE);
        lemon7.setVisibility(View.VISIBLE);
        lemon8.setVisibility(View.VISIBLE);
        lemon9.setVisibility(View.VISIBLE);
        lemon10.setVisibility(View.VISIBLE);
        lemon11.setVisibility(View.VISIBLE);

        float bottom = getResources().getDisplayMetrics().heightPixels;

        lemon1.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(4098);
        lemon2.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(6356);
        lemon3.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(3754);
        lemon4.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(5456);
        lemon5.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(4756);
        lemon6.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(3463);
        lemon7.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(6098);
        lemon8.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(5356);
        lemon9.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(4754);
        lemon10.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(6456);
        lemon11.animate().translationY(bottom).setInterpolator(new AccelerateInterpolator()).setInterpolator(new BounceInterpolator()).setDuration(4756);

        startService(new Intent(gamePage.this, song.class).setAction("PAUSE"));
        Toast.makeText(getApplicationContext(), R.string.agreeButtonToast, Toast.LENGTH_LONG).show();

        startService(new Intent(gamePage.this, winSong.class).setAction("PLAY"));

        if (addButton.isPressed() || cancelButton.isPressed()){
            startService(new Intent(gamePage.this, winSong.class).setAction("PAUSE"));
            startService(new Intent(gamePage.this, song.class).setAction("PLAY"));

            winMessage.setVisibility(View.INVISIBLE);
            nickname.setVisibility(View.INVISIBLE);

            lemon1.setVisibility(View.INVISIBLE);
            lemon2.setVisibility(View.INVISIBLE);
            lemon3.setVisibility(View.INVISIBLE);
            lemon4.setVisibility(View.INVISIBLE);
            lemon5.setVisibility(View.INVISIBLE);
            lemon6.setVisibility(View.INVISIBLE);
            lemon7.setVisibility(View.INVISIBLE);
            lemon8.setVisibility(View.INVISIBLE);
            lemon9.setVisibility(View.INVISIBLE);
            lemon10.setVisibility(View.INVISIBLE);
            lemon11.setVisibility(View.INVISIBLE);
        }

        setAddButton(addButton, row, column, numb, nickname, score);
        setCancelButton(cancelButton);
    }

    public void setAddButton(final Button addButton, final int row, final int column, final int numb, final EditText nickname, final int score){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.INVISIBLE);
                if (nickname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.nicknameMessage, Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(gamePage.this, scorePage.class);
                    intent.putExtra("nickname", nickname.getText().toString());
                    intent.putExtra("score", score);
                    intent.putExtra("gameMode", row + " rows " + column + " columns; " + numb + " lemons");

                    startActivity(intent);
                }
            }
        });
    }

    public void setCancelButton(final Button cancelButton){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButton.setVisibility(View.INVISIBLE);
                startActivity(new Intent(gamePage.this, menuPage.class));
            }
        });
    }
}

