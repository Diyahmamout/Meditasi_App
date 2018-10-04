package com.bekkostudio.meditasidanretret.Timer.NianFo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.R;

public class NianFoAutomaticActivity extends AppCompatActivity implements View.OnClickListener {
    Button pauseButton, finishEarlyButton;
    TextView countSiklusLabel;

    //Sound on click BGM image
    MediaPlayer backgroundSound;

    int jumlahSiklus;
    int musicId;
    int counterSiklus = 1;
    // pause button text status
    boolean isButtonPause = true;
    // record start and total meditation time in millisecond
    long startTime, totalDurasi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nian_fo_automatic);

        startTime = System.currentTimeMillis();

        pauseButton = findViewById(R.id.pauseButton);
        finishEarlyButton = findViewById(R.id.finishEarlyButton);
        countSiklusLabel = findViewById(R.id.countSiklusLabel);

        jumlahSiklus = Integer.valueOf(getIntent().getStringExtra(NianFoHomeActivity.EXTRA_SIKLUS));
        musicId = getIntent().getIntExtra(NianFoHomeActivity.EXTRA_MUSIC_ID, 0);

        countSiklusLabel.setText(String.valueOf(counterSiklus));

        pauseButton.setOnClickListener(this);
        finishEarlyButton.setOnClickListener(this);

        backgroundSound = MediaPlayer.create(this, musicId);
        backgroundSound.start();
        backgroundSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                counterSiklus++;

                if (counterSiklus > jumlahSiklus) {
                    endMeditation();
                    return;
                }

                if (backgroundSound.isPlaying()){
                    backgroundSound.pause();
                    backgroundSound.seekTo(0);
                }
                countSiklusLabel.setText(String.valueOf(counterSiklus));
                backgroundSound.start();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.pauseButton) {
            if (isButtonPause) {
                totalDurasi += System.currentTimeMillis() - startTime;
                isButtonPause = false;
                pauseButton.setText("Start");
                if (backgroundSound.isPlaying()){
                    backgroundSound.pause();
                }
            }
            else {
                startTime = System.currentTimeMillis();
                isButtonPause = true;
                pauseButton.setText("Pause");
                backgroundSound.start();
            }
        }
        else if(v.getId() == R.id.finishEarlyButton) {
            if (backgroundSound.isPlaying()){
                backgroundSound.stop();
            }
            endMeditation();
        }
    }

    private void endMeditation() {
        totalDurasi += System.currentTimeMillis() - startTime;
        countSiklusLabel.setText("DONE");
        pauseButton.setEnabled(false);
        finishEarlyButton.setEnabled(false);

        if (backgroundSound!=null){
            backgroundSound.stop();
            backgroundSound.release();
            backgroundSound = null;
        }

        Intent intent = new Intent(this, NianFoResultActivity.class);
        intent.putExtra(NianFoResultActivity.EXTRA_SIKLUS, jumlahSiklus);
        intent.putExtra(NianFoResultActivity.EXTRA_DURASI, totalDurasi);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backgroundSound!=null){
            backgroundSound.stop();
            backgroundSound.release();
            backgroundSound = null;
        }
    }
}
