package android.juntillak.musicplayer;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SongPicker extends AppCompatActivity {

    private SongObject aquaSong;
    private SongObject oceanSong;

    public void song1 (View view){

        String songID = String.valueOf(aquaSong.songID);
        launchPlayer(songID);

    }

    public void song2 (View view){
        String songID = String.valueOf(oceanSong.songID);
        launchPlayer(songID);

    }

    public void launchPlayer(String songID){
            Intent launchSongPlayer = new Intent(this, MusicPlayer.class);
            String message = String.valueOf(songID);
            launchSongPlayer.putExtra("songMessage", message);
            startActivity(launchSongPlayer);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_picker);

        MediaMetadataRetriever songInfo = new MediaMetadataRetriever();

        int aquaID = R.raw.aqua;
        String aquaTitle= songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String aquaArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        aquaSong = new SongObject(aquaID, aquaTitle, aquaArtist);

        int oceanID = R.raw.ocean;
        String oceanTitle= songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String oceanArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        oceanSong = new SongObject(oceanID, oceanTitle, oceanArtist);
    }
}
