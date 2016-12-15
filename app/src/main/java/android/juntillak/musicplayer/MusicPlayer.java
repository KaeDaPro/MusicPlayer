package android.juntillak.musicplayer;

import android.content.Intent;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MusicPlayer extends AppCompatActivity {

    public int seekTime;

    private MediaPlayer song1;
    private Handler myHandler = new Handler();
    private SeekBar mySongBarVar;


    public String songTitle;
    public String songArtist;

    public TextView SongTitleViewVar;
    public TextView SongArtistViewVar;

    public TextView currentTimeViewVar;
    public TextView currentFinalTimeViewVar;


    public double currentTimeMS;
    public double finalTimeMS;

    public int endMinutes;
    public int endSeconds;
    public int currentMinutes;
    public int currentSeconds;

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
        Toast.makeText(MusicPlayer.this, "Forward 5 Seconds", Toast.LENGTH_SHORT).show();

    }

    public void RewindSong (View view){
        song1.seekTo( (int) (currentTimeMS - 5000) );
        Toast.makeText(MusicPlayer.this, "Back 5 Seconds", Toast.LENGTH_SHORT).show();


    }




    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            currentTimeMS = song1.getCurrentPosition();
            int currentMinutes = (int) currentTimeMS/1000/60;
            int currentSeconds = (int) (currentTimeMS/1000) % 60;
            currentTimeViewVar.setText(currentMinutes + " min, " + currentSeconds + "sec ");

            mySongBarVar.setProgress((int) currentTimeMS);

            myHandler.postDelayed(this, 100);
        }

    };

    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        Intent thisIntent = getIntent();
        String songID = thisIntent.getStringExtra("songMessage");
        MediaMetadataRetriever songInfo = new MediaMetadataRetriever();
        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + songID);
        songInfo.setDataSource(this, mediaPath);
        songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        SongTitleViewVar = (TextView) findViewById(R.id.songTitleView);
        SongArtistViewVar = (TextView) findViewById(R.id.songArtistView);
        SongTitleViewVar.setText(songTitle);
        SongArtistViewVar.setText(songArtist);



        song1 = MediaPlayer.create(this, Integer.parseInt(songID));
        playButtonVar = (Button) findViewById(R.id.view9);
        pauseButtonVar = (Button) findViewById(R.id.view10);
        ForwardButton = (Button) findViewById(R.id.view12);
        RewindButton = (Button) findViewById(R.id.view11);

        finalTimeMS = song1.getDuration();
        currentTimeMS = song1.getCurrentPosition();
        currentTimeViewVar = (TextView) findViewById(R.id.currentTime);
        currentFinalTimeViewVar = (TextView) findViewById(R.id.finalTime);
        endMinutes = (int) (finalTimeMS / 1000 / 60);
        endSeconds = ((int) (finalTimeMS / 1000)) %60;
        currentMinutes =(int) (currentTimeMS/1000/60);
        currentSeconds = ((int)(currentTimeMS/1000)) %60;
        currentFinalTimeViewVar.setText(endMinutes + "min" + endSeconds + "sec");
        currentTimeViewVar.setText(currentMinutes + "min, " + currentSeconds + "sec");

        pauseButtonVar.setEnabled(true);
        playButtonVar.setEnabled(true);
        myHandler.postDelayed(UpdateSongTime, 100);
        myHandler.postDelayed(this.UpdateSongTime, 100);

        seekTime = 0;
        mySongBarVar = (SeekBar) findViewById(R.id.seekBar);
        mySongBarVar.setMax((int) finalTimeMS);

        mySongBarVar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                seekTime=progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                song1.seekTo(seekTime);
                currentTimeMS = seekTime;
            }
        });
    }
}
