package space.khsv.quizapp;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button buttonNext = findViewById(R.id.button_start);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameLevels.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnToggleMusic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    stopMusic();
                } else {
                    startMusic();
                }
                isPlaying = !isPlaying;
            }
        });
    }
        private void startMusic() {
            startService(new Intent(this, MusicService.class));
        }

        private void stopMusic() {
            stopService(new Intent(this, MusicService.class));
        }



}


