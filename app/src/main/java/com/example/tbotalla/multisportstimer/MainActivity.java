package com.example.tbotalla.multisportstimer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int MILLIS_PER_SECOND = 1000;
    private static final int SECONDS_TO_COUNTDOWN = 180;
    private static final int SECONDS_TO_BREAK = 60;
    private TextView countdownDisplay;
    private TextView infoDisplay;
    private CountDownTimer timer;
    private CountDownTimer restTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownDisplay = (TextView) findViewById(R.id.LblTimeDisplayBox);
        infoDisplay = (TextView) findViewById(R.id.LblInfoDisplayBox);
        Button startButton = (Button) findViewById(R.id.BtnStart);
        Button stopButton = (Button) findViewById(R.id.BtnStop);
        Button setUpButton = (Button) findViewById(R.id.BtnSetup);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    infoDisplay.setText(R.string.work);
                    showTimer((SECONDS_TO_COUNTDOWN * MILLIS_PER_SECOND));
                } catch (NumberFormatException e) {
                    // method ignores invalid (non-integer) input and waits
                    // for something it can use
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                try {
                    infoDisplay.setText(R.string.waiting);
                    timer.cancel();
                    restTimer.cancel();
                } catch (NumberFormatException e) {
                    // method ignores invalid (non-integer) input and waits
                    // for something it can use
                }
            }
        });

        setUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    showSetup(view);
                } catch (NumberFormatException e) {
                    // method ignores invalid (non-integer) input and waits
                    // for something it can use
                }
            }
        });

    }

    public void showSetup(View view) {
        Intent i = new Intent(this, Ajustes.class );
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showTimer(int countdownMillis) {
        if(timer != null) { timer.cancel(); }
        if(restTimer != null) { restTimer.cancel(); }
        timer = new CountDownTimer(countdownMillis, MILLIS_PER_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                //int hours = (int)(millisUntilFinished) / 3600;
                int minutes = (int)((millisUntilFinished/1000) % 3600) / 60;
                int seconds = (int)(millisUntilFinished/1000) % 60;
                countdownDisplay.setText(""+minutes+":"+seconds);
            }
            @Override
            public void onFinish() {
                if(restTimer != null) { restTimer.cancel(); }
                infoDisplay.setText(R.string.rest);
                restTimer = new CountDownTimer(SECONDS_TO_BREAK * MILLIS_PER_SECOND, MILLIS_PER_SECOND) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //int hours = (int)(millisUntilFinished) / 3600;
                        int minutes = (int)((millisUntilFinished/1000) % 3600) / 60;
                        int seconds = (int)(millisUntilFinished/1000) % 60;
                        countdownDisplay.setText(""+minutes+":"+seconds);
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        }.start();
    }


}
