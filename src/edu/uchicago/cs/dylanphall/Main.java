package edu.uchicago.cs.dylanphall;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main extends Activity implements LocationListener {

    LocationManager network_location_manager;
    LocationManager gps_location_manager;
    LocationManager test_location_manager;
    //LocationListener network_location_listener, gps_location_listener;
    String test_location_provider;
    //Location network_location, gps_location, current_location, previous_location, initial_location;

    TextView network_latitude, network_longitude, gps_latitude, gps_longitude;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        network_location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        gps_location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        test_location_provider = LocationManager.GPS_PROVIDER;

        network_latitude = (TextView) findViewById(R.id.network_latitude);
        network_longitude = (TextView) findViewById(R.id.network_longitude);
        gps_latitude = (TextView) findViewById(R.id.gps_latitude);
        gps_longitude = (TextView) findViewById(R.id.gps_longitude);

        //network_location_manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        //gps_location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        Log.i(this.getPackageName(), "initializing test_location_manager");
        test_location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        test_location_manager.addTestProvider(test_location_provider, false, false,  false, false, false, true, true, 0, 5);
        test_location_manager.setTestProviderEnabled(test_location_provider, true);

        Log.i(this.getPackageName(), "finished test_location_manager");

        MockLocationPaths test = new MockLocationPaths();
        Log.i(this.getPackageName(), "test path created, steps = " + test.getSteps());
        while (test.hasMoreSteps()) {
            Long milliseconds = new Long(500);
            AsyncTask<Long, Void, Boolean> p = new Pause().execute(milliseconds);
             //TODO make it wait correctly
            try {
                if (p.get()) {
                    Location l = test.getNextStep();
                    Log.i(this.getPackageName(), "lat : " + l.getLatitude() + "| lon : " + l.getLongitude());
                    test_location_manager.setTestProviderLocation(test_location_provider, l);
                    this.onLocationChanged(l); //Force it to be called
                    //this.logLocationStatus(l);
                    //this.updateGpsLocation(l);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ExecutionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

        //runTest();


    }

    @Override
    protected void onPause() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void runTest() {
        test_location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        test_location_manager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

        MockLocationPaths test = new MockLocationPaths();

        while (test.hasMoreSteps()) {

            test_location_manager.setTestProviderLocation(LocationManager.GPS_PROVIDER, test.getNextStep() );
        }



    }

    public void logLocationStatus(Location location) {
        Log.i(this.getPackageName(), "Provider  : " + location.getProvider());
        Log.i(this.getPackageName(), "Latitude  : " + location.getLatitude());
        Log.i(this.getPackageName(), "Longitude : " + location.getLongitude());
        Log.i(this.getPackageName(), "Accuracy  : " + location.getAccuracy());
        Log.i(this.getPackageName(), "Altitude  : " + location.getAltitude());
        Log.i(this.getPackageName(), "Bearing   : " + location.getBearing());
        Log.i(this.getPackageName(), "Speed     : " + location.getSpeed());
        Log.i(this.getPackageName(), "Time      : " + location.getTime());
    }

    public void updateNetworkLocation(Location network_location) {
        //Log.i(this.getPackageName(), "in updateNetworkLocation()");
        Double lat =  network_location.getLatitude();
        Double longi = network_location.getLongitude();
        network_latitude.setText(lat.toString());
        network_longitude.setText(longi.toString());
    }

    public void updateGpsLocation(Location gps_location) {
        //Log.i(this.getPackageName(), "in updateGpsLocation()");
        Double lat = gps_location.getLatitude();
        Double longi = gps_location.getLongitude();
        gps_latitude.setText(lat.toString());
        gps_longitude.setText(longi.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        this.logLocationStatus(location);
        if (location.getProvider().equalsIgnoreCase(LocationManager.NETWORK_PROVIDER)) {
            this.updateNetworkLocation(location);
        } else if (location.getProvider().equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
            Log.i(this.getPackageName(), "gps provider updated location");
            this.updateGpsLocation(location);
        } else {
            Log.i(this.getPackageName(), "passive provider updated location");
            this.updateGpsLocation(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(this.getPackageName(), provider + " status : " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(this.getPackageName(), provider + " Enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(this.getPackageName(), provider + " Disabled");
    }

    private class Pause extends AsyncTask<Long, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Long... params) {
           try {
               long sleep_time = params[0];
               Log.i(getPackageName(), "Sleeping for " + sleep_time + "ms");
               Thread.sleep(sleep_time);
               return true;
           } catch (InterruptedException e) {
               Log.e("dph", "Exception while sleeping :" + e);
           }
            return false;
        }
    }

}

