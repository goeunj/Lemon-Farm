package lemonfarm.gameUI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import cmpt276.as3.lemonfarm.R;
import cmpt276.as3.lemonfarm.music.song;

/**
 * Has description on game
 *      How to play/win the game
 *      How to change board view/size
 *      How to change number of lemons to find
 *      Goal of the game
 * Citations
 *      About the author link to cmpt276 home page
 *      cmpt276.as3.lemonfarm.music/image citations
 *      yea
 */

public class helpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        getUrl();
    }

    @Override
    protected void onResume(){
        super.onResume();
        startService(new Intent(helpPage.this, song.class).setAction("PLAY"));
    }

    @Override
    protected void onPause(){
        super.onPause();
        startService(new Intent(helpPage.this, song.class).setAction("PAUSE"));
    }

    private void getUrl() {
        TextView url = findViewById(R.id.url);
        TextView song = findViewById(R.id.song);
        TextView loseSong = findViewById(R.id.loseSong);
        TextView winSong = findViewById(R.id.winSong);
        TextView buttonSound = findViewById(R.id.buttonSound);

        if (url != null){
            url.setMovementMethod(LinkMovementMethod.getInstance());
            song.setMovementMethod(LinkMovementMethod.getInstance());
            loseSong.setMovementMethod(LinkMovementMethod.getInstance());
            winSong.setMovementMethod(LinkMovementMethod.getInstance());
            buttonSound.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}

