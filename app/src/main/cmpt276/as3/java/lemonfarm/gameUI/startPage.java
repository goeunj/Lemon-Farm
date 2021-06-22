package lemonfarm.gameUI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import cmpt276.as3.lemonfarm.R;
import cmpt276.as3.lemonfarm.music.song;

/**
 * Has a rotating loading image for set duration
 * When duration is finished, leads to welcome page
 */

public class startPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setText();
        setImageAnimation();
        startApp();
    }

    @Override
    protected void onResume(){
        super.onResume();
        startService(new Intent(startPage.this, song.class).setAction("PLAY"));
    }

    private void setText() {
        TextView text = findViewById(R.id.loading);
        text.setText(getString(R.string.loading));

        Animation blink = AnimationUtils.loadAnimation(this, R.anim.blink);
        text.setAnimation(blink);
    }

    private void setImageAnimation() {
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final ImageView image = findViewById(R.id.image_me);

        image.startAnimation(fade);
    }

    private void startApp(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(startPage.this, welcomeScreen.class));
                finish();
            }
        }, 4000);
    }
}
