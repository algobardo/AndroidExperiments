package org.esprtimeouts.app;

import android.os.AsyncTask;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class SyncActivity extends ActionBarActivity {

    private LogPrinter logger = new LogPrinter(Log.VERBOSE,"MessageQueue-Log");

    /*A simple aysnc task*/
    private class WaitTask extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String... p) {
            System.out.println("Running AsyncTask");System.out.flush();
            Random rand = new Random();
            //SystemClock.sleep(rand.nextInt(5000) + 2000);
            return null;
        }


        protected void onPostExecute(Long result) {
            System.out.println("Ending AsyncTask");System.out.flush();
            setStatus(getString(R.string.hello_world));
            System.out.println("Ended AsyncTask");System.out.flush();
        }
    }

    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        Looper.getMainLooper().setMessageLogging(logger);


        statusTextView = ((((TextView) findViewById(R.id.status_text))));
        if(statusTextView == null) throw new IllegalArgumentException();

    }


    public void onRequestButtonClick(@SuppressWarnings("unused") View view) {
        System.out.println("Inst AsyncTask");System.out.flush();
        WaitTask wt = new WaitTask();
        System.out.println("Launching AsyncTask");System.out.flush();
        wt.execute();
        //wt.onPostExecute(1L);
        System.out.println("Launched AsyncTask");System.out.flush();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sync, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setStatus(String text) {
        statusTextView.setText(text);
    }

}
