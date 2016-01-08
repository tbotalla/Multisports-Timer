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
    private static final int DEFAULT_SECONDS_TO_COUNTDOWN = 10;
    private static final int DEFAULT_SECONDS_TO_REST = 10;
    private static final int DEFAULT_ROUND_AMOUNT = 5;

    private TextView countdownDisplay;
    private TextView infoDisplay;
    private CountDownTimer timer;
    private CountDownTimer restTimer;
    private Button startButton;
    private Button stopButton;
    private Button setUpButton;

    private int secsToCountdown;
    private int secsToRest;
    private int roundAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setDefaultValues();
        this.setViewReferences();
        this.setListeners();

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

    public void setDefaultValues(){
        this.secsToCountdown = this.DEFAULT_SECONDS_TO_COUNTDOWN;
        this.secsToRest = this.DEFAULT_SECONDS_TO_REST;
        this.roundAmount = this.DEFAULT_ROUND_AMOUNT;
    }

    public void setViewReferences(){
        this.countdownDisplay = (TextView) findViewById(R.id.LblTimeDisplayBox);
        this.infoDisplay = (TextView) findViewById(R.id.LblInfoDisplayBox);
        this.startButton = (Button) findViewById(R.id.BtnStart);
        this.stopButton = (Button) findViewById(R.id.BtnStop);
        this.setUpButton = (Button) findViewById(R.id.BtnSetup);
    }

    public void setListeners(){
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    startWorkCycle();
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
                    // Necesario chequear que los timers sean no nulos para cancelarlos
                    if(timer != null) { timer.cancel(); }
                    if(restTimer != null) { restTimer.cancel(); }
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

    // Determina la logica del ciclo de timers incluyendo la cantidad de rounds, tiempo de round,
    // y tiempo de descanso
    private void startWorkCycle(){
        infoDisplay.setText(R.string.work);
        // TODO, revisar funcionamiento de esto
        for(int i = 1 ; i <= this.roundAmount ; i++){
            showTimer((secsToCountdown * MILLIS_PER_SECOND));
            //wait((secsToCountdown + secsToRest) * MILLIS_PER_SECOND)
        }

        //this.cancelBothTimers(); // una vez terminado el ciclo de trabajo, cancelo los timers
    }

    public void showSetup(View view) {
        Intent i = new Intent(this, Ajustes.class );
        startActivity(i);
    }


    private void showTimer(int countdownMillis) {
        this.cancelBothTimers();
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
                cancelRestTimer();
                infoDisplay.setText(R.string.rest);
                restTimer = new CountDownTimer(secsToRest * MILLIS_PER_SECOND, MILLIS_PER_SECOND) {
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


    // Cancela el timer del round y el del descanso
    private void cancelBothTimers() {
        if(timer != null) { timer.cancel(); }
        if(restTimer != null) { restTimer.cancel(); }
    }

    private void cancelRestTimer() {
        if(restTimer != null) { restTimer.cancel(); }
    }


}
