package cmpt276.as3.lemonfarm.gameUI;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import cmpt276.as3.lemonfarm.R;
import cmpt276.as3.lemonfarm.gameLogic.option;
import cmpt276.as3.lemonfarm.gameLogic.optionManager;
import cmpt276.as3.lemonfarm.music.song;

/**
 * Has options for board view/size
 * Has options for number of lemons to find in the board
 *
 * Has set button which sets the optionManager with
 * choices the user chose
 */

public class optionsPage extends AppCompatActivity {
    private optionManager manager = optionManager.getInstance();
    String row, column, numb;
    int sizeSelected, numbSelected;

    private RadioGroup radioSize, radioNumb;
    private RadioButton gridSize, lemonNumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_page);

        setOption();
    }

    @Override
    protected void onResume(){
        super.onResume();
        startService(new Intent(optionsPage.this, song.class).setAction("PLAY"));
    }

    @Override
    protected void onPause(){
        super.onPause();
        startService(new Intent(optionsPage.this, song.class).setAction("PAUSE"));
    }

    @SuppressLint("SetTextI18n")
    private void setOption() {
        Button setOption = findViewById(R.id.setOption);

        radioNumb = findViewById(R.id.radioNumb);
        radioSize = findViewById(R.id.radioSize);

        setOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer sound = MediaPlayer.create(optionsPage.this, R.raw.button);
                sound.start();

                sizeSelected = radioSize.getCheckedRadioButtonId();
                numbSelected = radioNumb.getCheckedRadioButtonId();

                if(radioSize.getCheckedRadioButtonId() == -1 || radioNumb.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "GRID SIZE & NUMBER OF LEMONS\n MUST BE CHOSEN TO PLAY GAME", Toast.LENGTH_LONG).show();
                }else{
                    gridSize = findViewById(sizeSelected);
                    lemonNumb = findViewById(numbSelected);

                    switch (lemonNumb.getId()){
                        case R.id.numb1:
                            numb = "6";
                            break;
                        case R.id.numb2:
                            numb = "10";
                            break;
                        case R.id.numb3:
                            numb = "15";
                            break;
                        case R.id.numb4:
                            numb = "20";
                            break;
                    }

                    switch (gridSize.getId()){
                        case R.id.size1:
                            row = "4";
                            column = "6";
                            break;
                        case R.id.size2:
                            row = "5";
                            column = "10";
                            break;
                        case R.id.size3:
                            row = "6";
                            column = "15";
                            break;
                    }

                    manager.getMyOption().clear();
                    manager.getMyOption().add(new option(row, column, numb));

                    startActivity(new Intent(optionsPage.this, menuPage.class));
                }

            }
        });
    }
}


