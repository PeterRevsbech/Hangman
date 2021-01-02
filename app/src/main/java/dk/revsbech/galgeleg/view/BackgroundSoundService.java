package dk.revsbech.galgeleg.view;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;

import dk.revsbech.galgeleg.R;

public class BackgroundSoundService extends Service {
    public static final String ACTION_STOP = "stop_music_service";
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {

        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.sang1);
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(100, 100);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction()!=null && intent.getAction().equals(ACTION_STOP)){
            mediaPlayer.stop();
        }
        mediaPlayer.start();
        return startId;
    }
    public void onStart(Intent intent, int startId) {
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    @Override
    public void onLowMemory() {
    }
}