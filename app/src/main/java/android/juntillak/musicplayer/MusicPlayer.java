package android.juntillak.musicplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayer extends AppCompatActivity {

    private MediaPlayer song1;
    private Handler myHandler = new Handler();

    public double currentTimeMS;
    public double finalTimeMS;

    public double endMinutes;
    public double endSeconds;
    public double currentMinutes;
    public double currentSeconds;

    public Button playButtonVar;
    public Button pauseButtonVar;
    public Button RewindButton;
    public Button ForwardButton;

    public void playSong(View view) {
        song1.start();
        Toast.makeText(MusicPlayer.this, "Play song", Toast.LENGTH_SHORT).show();

    }

    public void pauseSong (View view) {

        song1.pause();
        Toast.makeText(MusicPlayer.this, "Pause song", Toast.LENGTH_SHORT).show();
    }

    public void ForwardSong (View view){
        song1.seekTo( (int) (currentTimeMS + 5000) );

    }

    public void RewindSong (View view){
        song1.seekTo( (int) (currentTimeMS - 5000) );

    }


    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {

        }
    };

    @Override
protected void onCreate(Bundle savedInstanceState) {
        song1 = MediaPlayer.create(this, R.raw.garbage);
        playButtonVar = (Button) findViewById(R.id.view9);
        pauseButtonVar = (Button) findViewById(R.id.view10);
        ForwardButton = (Button) findViewById(R.id.view12);
        RewindButton = (Button) findViewById(R.id.view11);
        finalTimeMS = song1.getDuration();
        currentTimeMS = song1.getCurrentPosition();
        endMinutes = (int) (finalTimeMS / 1000 / 60);
        endSeconds = ((int) (finalTimeMS / 1000)) %60;
        currentMinutes =(int) (currentTimeMS/1000/60);
        currentSeconds = ((int)(currentTimeMS/1000)) %60;
        pauseButtonVar.setEnabled(true);
        playButtonVar.setEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        myHandler.postDelayed(UpdateSongTime, 100);
        myHandler.postDelayed(this.UpdateSongTime, 100);
    }
}
