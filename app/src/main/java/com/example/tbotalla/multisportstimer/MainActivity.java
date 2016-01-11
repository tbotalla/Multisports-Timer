package com.example.tbotalla.multisportstimer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
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

    private SoundManager soundManager;
    private int startSound;
    private int endSound;
    private int warningSound;

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
        this.loadSounds();
        this.setListeners();
        this.getExtras();
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
        this.actualRound = 1;
    }


    public void setViewReferences(){
        this.countdownDisplay = (TextView) findViewById(R.id.LblTimeDisplayBox);
        this.infoDisplay = (TextView) findViewById(R.id.LblInfoDisplayBox);
        this.startButton = (Button) findViewById(R.id.BtnStart);
        this.stopButton = (Button) findViewById(R.id.BtnStop);
        this.setUpButton = (Button) findViewById(R.id.BtnSetup);
    }


    private void loadSounds() {
        this.soundManager = new SoundManager(getApplicationContext());
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.startSound = soundManager.load(R.raw.ringring);
        // TODO
        //this.stopSound = ;
        //this.warningSound = ;
    }


    public void setListeners(){
        setStartButtonListener();
        setStopButtonListener();
        setSetUpButtonListener();
    }


    private void getExtras() {
        if(getIntent().getExtras() != null){
            this.secsToCountdown = getIntent().getExtras().getInt("secsToCountdown");
            this.secsToRest = getIntent().getExtras().getInt("secsToRest");
            this.roundAmount = getIntent().getExtras().getInt("roundAmount");
        }
    }


    // Setea y configura el comportamiento al presionar el boton START
    public void setStartButtonListener(){
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cancelBothTimers();
                resetRoundNumber();
                getExtras();
                showInfoToast();
                playStartSound();
                startWorkCycle();
            }
        });
    }


    // Setea y configura el comportamiento al presionar el boton STOP
    public void setStopButtonListener(){
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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


    // Setea y configura el comportamiento al presionar el boton SETUP
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


    /* Reproduce el sonido de inicio del round */
    private void playStartSound() {
        soundManager.play(startSound);
    }


    // TODO
    /* Reproduce el sonido de fin del round */
    private void playEndSound() {
        soundManager.play(endSound);
    }


    // TODO
    /* Reproduce el sonido de aviso de fin del round */
    private void playWarningSound() {
        soundManager.play(warningSound);
    }


    private void showInfoToast() {
        // TODO --> PROVISORIO PARA DEBUG
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        CharSequence text1 = "Round #: "+actualRound;

        Toast toast1 = Toast.makeText(context, text1, duration);
        toast1.show();

        CharSequence text2 = "Round time: "+secsToCountdown;

        Toast toast2 = Toast.makeText(context, text2, duration);
        toast2.show();

        CharSequence text = "Rest time: "+secsToRest;

        Toast toast3 = Toast.makeText(context, text, duration);
        toast3.show();
    }


    // Determina la logica del ciclo de timers incluyendo la cantidad de rounds, tiempo de round,
    // y tiempo de descanso.
    public void startWorkCycle(){
        // El metodo es llamado cada vez que termina el timer de descanso, y ahi se chequea el
        // numero de rounds
        if(actualRound <= roundAmount){
            showTimer((secsToCountdown * MILLIS_PER_SECOND));

            // TODO --> PROVISORIO PARA DEBUG
            Context context = getApplicationContext();
            CharSequence text = "Round #: "+actualRound;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            // TODO --> aca se deberia lanzar un sonido de comienzo
            this.actualRound++;
        } else {
            infoDisplay.setText(R.string.waiting);
            resetRoundNumber(); // Reseteo el numero de round para poder comenzar otro ciclo
        }
    }

    public void showSetup(View view) {
        Intent i = new Intent(this, SetupActivity.class);
        i.putExtra("secsToCountdown", secsToCountdown);
        i.putExtra("secsToRest", secsToRest);
        i.putExtra("roundAmount", roundAmount);

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
        actualRound = 1;
    }

}
