package com.example.zhu.mygpsdata;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    static private  int lock=1;

    static final String TEST_LOCATION = "99705";
    static final long TEST_DATE = 1419033600L;  // December 20th, 2014
    private String provider;
    private LocationManager locationManager;
    private Criteria criteria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //long locationRowId = insertLocation();
        return true;
    }

    public void selfDestruct(View view) {


        // Kabloey
        Log.e("create","insert");
        Button p1_button = (Button)findViewById(R.id.button);
        Intent Myservice_intent = new Intent(getApplicationContext(), GPSLocationService.class);
        if (lock == 0) {

            // Update the widgets via the service
            getApplicationContext().startService(Myservice_intent);
            lock = 1;
            p1_button.setText("Stop GPS data");
        }
        else{
            getApplicationContext().stopService(Myservice_intent);
            lock = 0;
            p1_button.setText("Star GPS data");

        }

        //insertNorthPoleLocationValues(getApplicationContext());

    }

    public void selfDestruct2(View view) {
        // Kabloey
        // First, check if the location with this city name exists in the db
        Cursor locationCursor = getApplicationContext().getContentResolver().query(
                GPSDbContract.LocationEntry.CONTENT_URI,
                new String[]{GPSDbContract.LocationEntry._ID},
                GPSDbContract.LocationEntry.COLUMN_LOCATION_SETTING + " = ?",
                new String[]{"test"},
                null);
        Log.e("create","read");
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(locationCursor));
        //insertNorthPoleLocationValues(getApplicationContext());

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



}
