package cmpt276.as3.lemonfarm.gameUI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import cmpt276.as3.lemonfarm.R;
import cmpt276.as3.lemonfarm.gameLogic.option;
import cmpt276.as3.lemonfarm.gameLogic.optionManager;
import cmpt276.as3.lemonfarm.music.song;

/**
 * Has 3 buttons and 1 image
 *      Start button  -> goes to game page
 *      Option button -> goes to option page
 *      Help button   -> goes to help page
 */

public class menuPage extends AppCompatActivity {
    private optionManager manager = optionManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        setButtons();
        setImageBubbleAnimation();
    }

    @Override
    protected void onResume(){
        super.onResume();
        startService(new Intent(menuPage.this, song.class).setAction("PLAY"));
    }

    @Override
    protected void onPause(){
        super.onPause();
        startService(new Intent(menuPage.this, song.class).setAction("PAUSE"));
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        startService(new Intent(menuPage.this, song.class).setAction("STOP"));
    }

    private void setButtons() {
        final SoundPool sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1f, 1f, 0, 0, 1);
            }
        });

        Button start = findViewById(R.id.startGame);
        Button options = findViewById(R.id.option);
        Button scores = findViewById(R.id.scores);
        Button help = findViewById(R.id.help);

        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        start.startAnimation(bounce);
        options.startAnimation(bounce);
        scores.startAnimation(bounce);
        help.startAnimation(bounce);

        //starts game
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.load(menuPage.this, R.raw.button, 0);
                Intent intent = new Intent(menuPage.this, gamePage.class);
                startActivity(intent);
            }
        });

        //goes to options page
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.load(menuPage.this, R.raw.button, 0);
                startActivity(new Intent(menuPage.this, optionsPage.class));
            }
        });

        //goes to scores page
        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.load(menuPage.this, R.raw.button, 0);
                startActivity(new Intent(menuPage.this, scorePage.class));
            }
        });

        //goes to help page
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.load(menuPage.this, R.raw.button, 0);
                startActivity(new Intent(menuPage.this, helpPage.class));

            }
        });
    }

    public void setImageBubbleAnimation(){
        ImageView imageView = findViewById(R.id.talkBubble);
        Animation zoom = AnimationUtils.loadAnimation(menuPage.this, R.anim.zoom);
        imageView.startAnimation(zoom);
    }
}

