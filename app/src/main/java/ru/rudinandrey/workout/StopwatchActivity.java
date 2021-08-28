package ru.rudinandrey.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStart(View view) {
        if(wasRunning) {
            running = true;
        }
    }

    public void onClickStop(View view) {
        wasRunning = running;
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.stopwatch_counter);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i( "RUN", "run");
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if(running) {
                    seconds++;
                    Log.i("SECONDS", String.format("%d", seconds));
                }
                handler.postDelayed(this, 1000);
            }
        });

    }
}