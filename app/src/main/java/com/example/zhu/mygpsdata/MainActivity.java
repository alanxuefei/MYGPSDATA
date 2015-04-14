package com.example.zhu.mygpsdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    static final String TEST_LOCATION = "99705";
    static final long TEST_DATE = 1419033600L;  // December 20th, 2014

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
        insertNorthPoleLocationValues(getApplicationContext());

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

    /*
    Students: You can uncomment this function once you have finished creating the
    LocationEntry part of theGPSContract as well as theGPSDbHelper.
 */
    static long insertNorthPoleLocationValues(Context context) {
        // insert our test records into the database
       GPSDbHelper dbHelper = new GPSDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues =  createNorthPoleLocationValues();

        long locationRowId;
        locationRowId = db.insert(GPSDbContract.LocationEntry.TABLE_NAME, null, testValues);
        String tmpStr10;
        tmpStr10 = Long.toString(locationRowId);
        Log.e("create",tmpStr10);
        // Verify we got a row back.
       // assertTrue("Error: Failure to insert North Pole Location Values", locationRowId != -1);



       // cursor.close();
        db.close();
        return locationRowId;
    }
    /*
    Students: You can uncomment this helper function once you have finished creating the
    LocationEntry part of theGPSContract.
 */
    static ContentValues createNorthPoleLocationValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();

        testValues.put(GPSDbContract.LocationEntry.COLUMN_LOCATION_SETTING, TEST_LOCATION);
        testValues.put(GPSDbContract.LocationEntry.COLUMN_DATETIME, getDateTime());
        testValues.put(GPSDbContract.LocationEntry.COLUMN_COORD_LAT, 64.7488);
        testValues.put(GPSDbContract.LocationEntry.COLUMN_COORD_LONG, -147.353);

        return testValues;
    }
    private static String getDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }
}
