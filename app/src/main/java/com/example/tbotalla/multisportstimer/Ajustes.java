package com.example.tbotalla.multisportstimer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by tbotalla on 05/01/16.
 */
public class Ajustes extends Activity {

    private Spinner cmbRoundNumber;
    private Spinner cmbRoundTime;
    private Spinner cmbRestTime;
    private Button  btnApplyChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustes);

        this.setViewReferences();
        this.setAdapters();




    }

    // Asocia los atributos usados por la clase con sus vistas correspondientes
    private void setViewReferences(){
        cmbRoundNumber = (Spinner)findViewById(R.id.CmbRoundNumber);
        cmbRoundTime = (Spinner)findViewById(R.id.CmbRoundTime);
        cmbRestTime = (Spinner)findViewById(R.id.CmbRestTime);
        btnApplyChanges = (Button)findViewById(R.id.BtnApplyChanges);


    }

    // Setea y crea los adaptadores de los spinners con los datos a mostrar
    private void setAdapters(){
        // Creacion del adaptador para el spinner de rounds
        ArrayAdapter<CharSequence> roundNumberAdapter = ArrayAdapter.createFromResource(this,
                R.array.numbers_array, android.R.layout.simple_spinner_item);
        roundNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbRoundNumber.setAdapter(roundNumberAdapter);


        // Creacion del adaptador para el spinner del tiempo de los rounds
        ArrayAdapter<CharSequence> roundTimeAdapter = ArrayAdapter.createFromResource(this,
                R.array.round_times_array, android.R.layout.simple_spinner_item);
        roundTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbRoundTime.setAdapter(roundTimeAdapter);


        // Creacion del adaptador para el spinner del tiempo de descanso entre los rounds
        ArrayAdapter<CharSequence> restTimeAdapter = ArrayAdapter.createFromResource(this,
                R.array.rest_times_array, android.R.layout.simple_spinner_item);
        restTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbRestTime.setAdapter(restTimeAdapter);

    }
}
