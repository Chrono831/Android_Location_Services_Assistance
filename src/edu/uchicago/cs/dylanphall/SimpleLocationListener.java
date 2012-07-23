package edu.uchicago.cs.dylanphall;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Dylan Hall
 * Email: dylan.p.hall@gmail.com
 * Date: 7/22/12
 * Time: 7:44 PM
 *
 * Purpose: To demonstrate a simple Location listener
 */
public class SimpleLocationListener implements LocationListener {
    private static final String network_provider = LocationManager.NETWORK_PROVIDER;
    private static final String gps_provider = LocationManager.GPS_PROVIDER;
    private static final String passive_provider = LocationManager.PASSIVE_PROVIDER;

    private String _tag;
    private LocationLogger _logger;

    public SimpleLocationListener(String tag) {
        _tag = tag;
        _logger = new LocationLogger(tag, null);
    }

    @Override
    public void onLocationChanged(Location location) {
        String provider = location.getProvider();
        if (provider.equalsIgnoreCase(network_provider)) {
            Log.i(_tag, "Network provider updated location");
            networkLocationUpdated(location);
        } else if (provider.equalsIgnoreCase(gps_provider)) {
            Log.i(_tag, "GPS provider updated location");
            gpsLocationUpdated(location);
        } else if (provider.equalsIgnoreCase(passive_provider)) {
            Log.i(_tag, "Passive provider updated location");
            passiveLocationUpdated(location);
        } else {
            Log.e(_tag, "Unknown Provider");
        }
        _logger.log(location);
    }


    protected void passiveLocationUpdated(Location location) {
        Log.i(_tag, "Actions to take on location update.");
    }

    protected void gpsLocationUpdated(Location location) {
        Log.i(_tag, "Actions to take on GPS location update.");
    }

    protected void networkLocationUpdated(Location location) {
        Log.i(_tag, "Actions to take on Network location update.");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(_tag, provider + " Status : " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(_tag, provider + " Enabled.");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(_tag, provider + " Disabled.");
    }
}
