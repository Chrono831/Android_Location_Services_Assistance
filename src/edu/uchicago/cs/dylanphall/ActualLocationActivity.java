package edu.uchicago.cs.dylanphall;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created with IntelliJ IDEA.
 * User: dylan
 * Date: 7/22/12
 * Time: 7:58 PM
 */
public class ActualLocationActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private static final String network_provider = LocationManager.NETWORK_PROVIDER;
    private static final String gps_provider = LocationManager.GPS_PROVIDER;

    private LocationManager network_location_manager;
    private LocationManager gps_location_manager;

    private TextView network_latitude, network_longitude, gps_latitude, gps_longitude;

    private SimpleLocationListener location_listener;
    ToggleButton network, gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Build XML view
        setContentView(R.layout.actual_location);
        linkTextViewsFromXml();
        linkToggleButtonsFromXml();
        //Listen to locations
        establishLocationListener();
        defineLocationManagers();
        //Ensure start not listening
        stopListeningToAll();

        listenToToggleButtons();        //toggleButtonCheckedChangedListener();

    }

    private void establishLocationListener() {
        location_listener = new ActualLocationListener("location");
    }

    private void linkToggleButtonsFromXml() {
        network = (ToggleButton) findViewById(R.id.network_toggle);
        gps = (ToggleButton) findViewById(R.id.gps_toggle);
    }

    private void linkTextViewsFromXml() {
        network_latitude = (TextView) findViewById(R.id.network_latitude);
        network_longitude = (TextView) findViewById(R.id.network_longitude);
        gps_latitude = (TextView) findViewById(R.id.gps_latitude);
        gps_longitude = (TextView) findViewById(R.id.gps_longitude);
    }

    private void defineLocationManagers() {
        network_location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        gps_location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    public void stopListeningToAll() {
        stopNetworkListening();
        stopGpsListening();
    }

    public void startListeningToAll() {
        startNetworkListening();
        startGpsListening();
    }

    public void stopNetworkListening() {
        Log.i("location", "Not Listening to Actual Network Updates");
        network_location_manager.removeUpdates(location_listener);
    }

    public void stopGpsListening() {
        Log.i("location", "Not Listening to Actual GPS Updates");
        gps_location_manager.removeUpdates(location_listener);
    }

    private void startNetworkListening() {
        try {
            Log.i("location", "Listening to Actual Network Updates");
            network_location_manager.requestLocationUpdates(network_provider, 0, 0, location_listener);
        } catch (Exception e) {
            Log.e("location", "Error starting to listen to Network Location : " + e);
        }
    }

    private void startGpsListening() {
        try {
            Log.i("location", "Listening to Actual GPS Updates");
            gps_location_manager.requestLocationUpdates(gps_provider, 0, 0, location_listener);
        } catch (Exception e) {
            Log.e("location", "Error starting to listen to GPS Location : " + e);
        }
    }

    private void updateNetworkCoordinates(Location location) {
        Double lat = location.getLatitude();
        Double lon = location.getLongitude();
        network_latitude.setText(lat.toString());
        network_longitude.setText(lon.toString());
    }

    private void updateGpsCoordinates(Location location) {
        Double lat = location.getLatitude();
        Double lon = location.getLongitude();
        gps_latitude.setText(lat.toString());
        gps_longitude.setText(lon.toString());
    }

    private void listenToToggleButtons() {
        network.setOnCheckedChangeListener(this);
        gps.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int button = buttonView.getId();
        if (button == R.id.network_toggle) {
            if (isChecked) {
                startNetworkListening();
            } else {
                stopNetworkListening();
            }
        } else if (button == R.id.gps_toggle) {
            if (isChecked) {
                startGpsListening();
            } else {
                stopGpsListening();
            }
        }
    }

    private class ActualLocationListener extends SimpleLocationListener {

        public ActualLocationListener(String tag) {
            super(tag);
        }

        @Override
        protected void gpsLocationUpdated(Location location) {
            updateGpsCoordinates(location);
        }

        @Override
        protected void networkLocationUpdated(Location location) {
            updateNetworkCoordinates(location);
        }
    }

}
