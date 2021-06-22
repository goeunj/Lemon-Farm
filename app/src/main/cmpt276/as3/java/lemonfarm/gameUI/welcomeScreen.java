package lemonfarm.gameUI;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import cmpt276.as3.lemonfarm.R;
import lemonfarm.music.song;

/**
 * Has title of game
 * Has image
 * Has menu button that goes to menu page
 */

public class welcomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        setMenuButton();
        setWelcomeMessage();
    }

    @Override
    protected void onResume(){
        super.onResume();
        startService(new Intent(welcomeScreen.this, song.class).setAction("PLAY"));
    }

    @Override
    protected void onPause(){
        super.onPause();
        startService(new Intent(welcomeScreen.this, song.class).setAction("PAUSE"));
    }

    private void setWelcomeMessage() {
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce2);
        TextView welcome = findViewById(R.id.welcome);

        welcome.startAnimation(bounce);
    }

    private void setMenuButton() {
        Animation blink = AnimationUtils.loadAnimation(this, R.anim.blink);
        Button setMenuButton = findViewById(R.id.button);
        setMenuButton.startAnimation(blink);

        setMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer sound = MediaPlayer.create(welcomeScreen.this, R.raw.button);
                sound.start();
                startActivity(new Intent(welcomeScreen.this, menuPage.class));
                finish();
            }
        });
    }
}
