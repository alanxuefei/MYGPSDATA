package com.example.zhu.mygpsdata;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Zhu on 16/04/2015.
 */
public class GPSLocationService extends Service{
    protected  LocationManager locationManager;

    static private String TAG="GPSListener";
    private void makeUseOfNewLocation(Location location){
        String longitude = "Longitude: " + location.getLongitude();
        Log.v(TAG, longitude);
        String latitude = "Latitude: " + location.getLatitude();
        Log.v(TAG, latitude);
        double l1=location.getLongitude();
        double l2=location.getLatitude();
        insertNorthPoleLocationValues(getApplicationContext(),l2,l1);
}



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v(TAG,"start service");


        // Acquire a reference to the system Location Manager
         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                makeUseOfNewLocation(location);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

// Register the listener with the Location Manager to receive location updates
       locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
Students: You can uncomment this function once you have finished creating the
LocationEntry part of theGPSContract as well as theGPSDbHelper.
*/
    static long insertNorthPoleLocationValues(Context context,double Dlongitude, double DLatitude) {
        // insert our test records into the database
        GPSDbHelper dbHelper = new GPSDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues =  createNorthPoleLocationValues(Dlongitude,DLatitude);

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
    static ContentValues createNorthPoleLocationValues(double Dlongitude, double DLatitude) {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();

        testValues.put(GPSDbContract.LocationEntry.COLUMN_LOCATION_SETTING,"99705");
        testValues.put(GPSDbContract.LocationEntry.COLUMN_DATETIME, getDateTime());
        testValues.put(GPSDbContract.LocationEntry.COLUMN_COORD_LAT,  DLatitude);
        testValues.put(GPSDbContract.LocationEntry.COLUMN_COORD_LONG,  Dlongitude);

        return testValues;
    }
    private static String getDateTime() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }
}
