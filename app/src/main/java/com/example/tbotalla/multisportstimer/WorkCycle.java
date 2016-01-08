package com.example.tbotalla.multisportstimer;

import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by tbotalla on 08/01/16.
 */
public class WorkCycle extends AsyncTask<Void, Integer, Boolean> {
    // TODO

    @Override
    protected Boolean doInBackground(Void... params) {

        //for(int i=1; i<=10; i++) {
            //tareaLarga();

            //publishProgress(i*10);

            //if(isCancelled())
            //    break;
        //}

        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //int progreso = values[0].intValue();

        //pbarProgreso.setProgress(progreso);
    }

    @Override
    protected void onPreExecute() {
        //pbarProgreso.setMax(100);
        //pbarProgreso.setProgress(0);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        //if(result)
        //    Toast.makeText(MainHilos.this, "Tarea finalizada!",
        //            Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled() {
        //Toast.makeText(MainHilos.this, "Tarea cancelada!",
        //        Toast.LENGTH_SHORT).show();
    }
}


