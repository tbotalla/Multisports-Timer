package com.example.tbotalla.multisportstimer;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MILLIS_PER_SECOND = 1000;
    private static final int DEFAULT_SECONDS_TO_COUNTDOWN = 10;
    private static final int DEFAULT_SECONDS_TO_REST = 5;
    private static final int DEFAULT_ROUND_AMOUNT = 3;

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
    private int actualRound;


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
        this.secsToCountdown = DEFAULT_SECONDS_TO_COUNTDOWN;
        this.secsToRest = DEFAULT_SECONDS_TO_REST;
        this.roundAmount = DEFAULT_ROUND_AMOUNT;
        this.actualRound = 0;
    }


    public void setViewReferences(){
        this.countdownDisplay = (TextView) findViewById(R.id.LblTimeDisplayBox);
        this.infoDisplay = (TextView) findViewById(R.id.LblInfoDisplayBox);
        this.startButton = (Button) findViewById(R.id.BtnStart);
        this.stopButton = (Button) findViewById(R.id.BtnStop);
        this.setUpButton = (Button) findViewById(R.id.BtnSetup);
    }


    public void setListeners(){
        setStartButtonListener();
        setStopButtonListener();
        setSetUpButtonListener();
    }


    public void setStartButtonListener(){
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cancelBothTimers();
                resetRoundNumber();
                startWorkCycle();
            }
        });
    }


    public void setStopButtonListener(){
        stopButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                try {
                    infoDisplay.setText(R.string.waiting);
                    cancelBothTimers();
                    resetRoundNumber();
                } catch (NumberFormatException e) {
                    // method ignores invalid (non-integer) input and waits
                    // for something it can use
                }
            }
        });
    }


    public void setSetUpButtonListener(){
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
    // y tiempo de descanso.
    public void startWorkCycle(){
        // El metodo es llamado cada vez que termina el timer de descanso, y ahi se chequea el
        // numero de rounds
        if(actualRound < roundAmount){
            showTimer((secsToCountdown * MILLIS_PER_SECOND));
            this.actualRound++;

            // TODO --> PROVISORIO PARA DEBUG
            Context context = getApplicationContext();
            CharSequence text = "Round #: "+actualRound;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            // TODO --> aca se deberia lanzar un sonido de comienzo
        } else {
            infoDisplay.setText(R.string.waiting);
            resetRoundNumber(); // Reseteo el numero de round para poder comenzar otro ciclo
        }
    }

    public void showSetup(View view) {
        Intent i = new Intent(this, Setup.class );
        startActivity(i);
    }


    public void showTimer(int countdownMillis) {
        // TODO --> lanzar sonido de inicio de entrenamiento
        this.cancelBothTimers();
        infoDisplay.setText(R.string.work);
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
                // TODO --> lanzar sonido de inicio de descanso
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
                        startWorkCycle(); // inicia nuevamente el ciclo hasta que se cumplan los rounds
                    }
                }.start();
            }
        }.start();
    }


    // Cancela el timer del round y el del descanso
    public void cancelBothTimers() {
        // Necesario chequear que los timers sean no nulos para cancelarlos
        if(timer != null) { timer.cancel(); }
        if(restTimer != null) { restTimer.cancel(); }
    }


    public void cancelRestTimer() {
        if(restTimer != null) { restTimer.cancel(); }
    }


    public boolean areTimersCancelled(){
        return (timer == null && restTimer == null);
    }


    public void resetRoundNumber(){
        actualRound = 0;
    }

}
