package edu.uchicago.cs.dylanphall;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: dylan
 * Date: 7/22/12
 * Time: 8:01 PM
 */
public class MockLocationActivity extends Activity implements View.OnClickListener {
    private String test_location_provider = LocationManager.GPS_PROVIDER;
    private LocationManager test_location_manager;

    private TextView test_latitude, test_longitude;
    private SimpleLocationListener location_listener;

    Button send_mock_locations;

    MockLocationPaths path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //link XML views
        setContentView(R.layout.mock_location);
        linkTextViewsFromXml();
        linkButtonFromXml();
        //Establish the test location manager
        defineTestLocationManager();
        enableMockListening();
        //listen to the button to send the signal
        listenToSendMockButton();

    }


    private void updateTestCoordinates(Location location) {
        Double lat = location.getLatitude();
        Double lon = location.getLongitude();
        test_latitude.setText(lat.toString());
        test_longitude.setText(lon.toString());
    }

    private void sendMockLocations() {
        Location location = new LocationBuilder(test_location_provider).latitude(100.0).longitude(50.0).buildLocation();
        LocationLogger logger = new LocationLogger("location");
        //path = new MockLocationPaths(location, 100, 0, 10);
        path = new MockLocationPaths();
        location_listener = new MockLocationListener("location");

        Log.i("location", "Path created with " + path.getSteps() + " step");
        while (path.hasMoreSteps()) {
            try {
                Location step = path.getNextStep();
                logger.log(step);
                test_location_manager.setTestProviderLocation(test_location_provider, step);
                location_listener.onLocationChanged(step); //Force it to be called
            } catch (Exception e) {
                Log.e("location", "Error stepping through mock locations : " + e);
            }
        }
    }

    private void linkButtonFromXml() {
        send_mock_locations = (Button) findViewById(R.id.send_mock);
    }

    private void listenToSendMockButton() {
        send_mock_locations.setOnClickListener(this);
    }

    private void linkTextViewsFromXml() {
        test_latitude = (TextView) findViewById(R.id.gps_latitude);
        test_longitude = (TextView) findViewById(R.id.gps_longitude);
    }

    private void defineTestLocationManager() {
        test_location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        test_location_manager.addTestProvider(test_location_provider, false, false, false, false, false, true, true, 0, 5);
    }

    public void disableMockListening() {
        try {
            Log.i("location", "Not Listening to Mock GPS Updates");
            test_location_manager.setTestProviderEnabled(test_location_provider, false);
        } catch (Exception e) {
            Log.e("location", "Error disabling listening to Mock GPS Location : " + e);
        }
    }

    private void enableMockListening() {
        try {
            Log.i("location", "Listening to Mock GPS Updates");
            test_location_manager.setTestProviderEnabled(test_location_provider, true);
        } catch (Exception e) {
            Log.e("location", "Error enabling listening to Mock GPS Location : " + e);
        }
    }

    @Override
    public void onClick(View v) {
        int button = v.getId();
        Log.i("location", "Button selected : " + button);
        switch (button) {
            case R.id.send_mock:
                Log.i("location", "Send Mock button selected");
                sendMockLocations();
            default:
                Log.e("location", "Illegal button selected");
        }
    }


    private class MockLocationListener extends SimpleLocationListener {
        public MockLocationListener(String tag) {
            super(tag);
        }

        @Override
        protected void passiveLocationUpdated(Location location) {
            super.passiveLocationUpdated(location);
            updateTestCoordinates(location);
        }

        @Override
        protected void gpsLocationUpdated(Location location) {
            super.gpsLocationUpdated(location);
            updateTestCoordinates(location);
        }
    }

}
