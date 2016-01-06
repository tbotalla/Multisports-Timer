package com.example.tbotalla.multisportstimer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by tbotalla on 05/01/16.
 */
public class Ajustes extends Activity {

    private Spinner cmbRoundNumber;
    private Spinner cmbRoundAmount;
    private Spinner cmbRoundTime;
    private Spinner cmbRestTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustes);

        // Creacion del adaptador para el spinner de rounds
        ArrayAdapter<CharSequence> roundNumberAdapter = ArrayAdapter.createFromResource(this,
                R.array.numbers_array, android.R.layout.simple_spinner_item);

        cmbRoundNumber = (Spinner)findViewById(R.id.CmbRoundNumber);

        roundNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cmbRoundNumber.setAdapter(roundNumberAdapter);



    }
}
