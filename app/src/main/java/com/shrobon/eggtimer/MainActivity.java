package com.shrobon.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    Button controller ;
    CountDownTimer countDownTimer;

    public void resetTimer()
    {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controller.setText("Go!");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft){
        int minutes= (int) secondsLeft / 60;
        int seconds= secondsLeft - minutes * 60;

        String secondsString = Integer.toString(seconds);
        if(seconds <=9)
        {
            secondsString ="0"+secondsString;
        }
        timerTextView.setText(Integer.toString(minutes)+":"+secondsString);
    }


    public void controlTimer(View v)
    {
        if(counterIsActive == false)
        {
            counterIsActive = true;
            //timerSeekBar.setVisibility(View.GONE); // set slider off when countdown starts
            timerSeekBar.setEnabled(false);
            controller.setText("Stop");
            //Log.i("Button: ","Timer button Pressed");
            countDownTimer= new CountDownTimer(timerSeekBar.getProgress()*1000+100,1000){ //nasty fix

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int)millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    timerTextView.setText("0:00");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);//this here will refer to countdown timer and not application context
                    mplayer.start();
                    Log.i("finished","Timer done");
                }
            }.start();

        }else{
            resetTimer();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = (Button)findViewById(R.id.controllerButton);
        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30); //initially to 30 seconds


        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
