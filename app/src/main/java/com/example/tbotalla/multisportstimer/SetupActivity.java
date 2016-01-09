package com.example.tbotalla.multisportstimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by tbotalla on 05/01/16.
 */
public class SetupActivity extends AppCompatActivity {

    private Spinner cmbRoundNumber;
    private Spinner cmbRoundTime;
    private Spinner cmbRestTime;
    private Button  btnApplyChanges;
    private Button btnBack;
    private TextView lblRoundNumberSelected;
    private TextView lblRoundTimeSelected;
    private TextView lblRestTimeSelected;

    private int secsToCountdown;
    private int secsToRest;
    private int roundAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        this.getExtras();
        this.setViewReferences();
        this.setAdapters();
        this.setSpinnersBehavior();
        this.setListeners();
    }


    // Recibe los parametros de la activity principal
    private void getExtras() {
        secsToCountdown = getIntent().getExtras().getInt("secsToCountdown");
        secsToRest = getIntent().getExtras().getInt("secsToRest");
        roundAmount = getIntent().getExtras().getInt("roundAmount");
    }


    // Setea los listeners para los botones
    private void setListeners() {
        btnApplyChanges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(SetupActivity.this, MainActivity.class);
                i.putExtra("secsToCountdown", secsToCountdown);
                i.putExtra("secsToRest", secsToRest);
                i.putExtra("roundAmount", roundAmount);
                startActivity(i);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(SetupActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }


    // Asocia los atributos usados por la clase con sus vistas correspondientes
    private void setViewReferences(){
        cmbRoundNumber = (Spinner)findViewById(R.id.CmbRoundNumber);
        cmbRoundTime = (Spinner)findViewById(R.id.CmbRoundTime);
        cmbRestTime = (Spinner)findViewById(R.id.CmbRestTime);
        btnApplyChanges = (Button)findViewById(R.id.BtnApplyChanges);
        btnBack = (Button)findViewById(R.id.BtnBack);

        lblRoundNumberSelected = (TextView)findViewById(R.id.LblRoundNumberSelected);
        lblRoundTimeSelected = (TextView)findViewById(R.id.LblRoundTimeSelected);
        lblRestTimeSelected = (TextView)findViewById(R.id.LblRestTimeSelected);
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


    /* Setea el comportamiento al seleccionar las opciones en los distintos spinners y almacenar
    * dichas selecciones para luego ser usadas */
    private void setSpinnersBehavior(){
        // Obtiene el valor seleccionado del spinner de numero de rounds
        cmbRoundNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v,
                                       int position, long id) {
                roundAmount = Integer.parseInt((String) parent.getItemAtPosition(position));

                lblRoundNumberSelected.setText("Seleccionado: " + (String) parent.getItemAtPosition(position));
                // TODO --> PARA DEBUG SOLAMENTE
//                Context context = getApplicationContext();
//                String text = String.valueOf(roundAmount);
//                int duration = Toast.LENGTH_SHORT;
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //lblMensaje.setText("");
            }
        });


        // Obtiene el valor seleccionado del spinner de round time
        cmbRoundTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position,
                                       long id) {
                secsToCountdown = parseSeconds((String) parent.getItemAtPosition(position));
                lblRoundTimeSelected.setText("Seleccionado: " + (String) parent.getItemAtPosition(position));

                // TODO --> PARA DEBUG SOLAMENTE
//                Context context = getApplicationContext();
//                String text = String.valueOf(secsToCountdown);
//                int duration = Toast.LENGTH_SHORT;
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //lblMensaje.setText("");
            }
        });


        // Obtiene el valor seleccionado del spinner de rest time
        cmbRestTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v,
                                       int position, long id) {
                secsToRest = parseSeconds((String) parent.getItemAtPosition(position));
                lblRestTimeSelected.setText("Seleccionado: " + (String) parent.getItemAtPosition(position));

                // TODO --> PARA DEBUG SOLAMENTE
//                Context context = getApplicationContext();
//                String text = String.valueOf(secsToRest);
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //lblMensaje.setText("");
            }
        });
    }


    // Recibe el tiempo en formato MM:SS y lo pasa a segundos
    private int parseSeconds(String tiempo) {
        String delims = "[:]+"; // expresion regular para delimitar
        String[] tokens = tiempo.split(delims);
        int minutes = Integer.parseInt(tokens[0]);
        int seconds = Integer.parseInt(tokens[1]);

        return ((minutes * 60) + seconds);
    }
}
