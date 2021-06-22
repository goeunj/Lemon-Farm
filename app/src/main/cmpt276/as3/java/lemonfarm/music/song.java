package lemonfarm.music;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.Objects;
import cmpt276.as3.lemonfarm.R;

/**
 * Referred to numerous different stackOverflow questions
 * while writing this class. Hence not original code,
 * but referred to too many to cite all.
 *
 * Music service to play background cmpt276.as3.lemonfarm.music
 */

public class song extends Service {
    MediaPlayer music;

    @Nullable
    @Override
    public android.os.IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        music = MediaPlayer.create(getApplicationContext(), R.raw.song);
        music.setLooping(true);
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int onStartCommand(Intent intent, int flag, int startId){
        if (Objects.equals(intent.getAction(), "PLAY")){
            music.start();
        }

        if (Objects.equals(intent.getAction(), "PAUSE")){
            music.pause();
        }

        if (Objects.equals(intent.getAction(), "STOP")){
            music.pause();
            music.seekTo(0);
        }
        return 1;
    }

    @Override
    public void onDestroy() {
        music.stop();
        music.release();
        music = null;
    }
}
