package edu.uchicago.cs.dylanphall;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Dylan Hall
 * Date: 7/22/12
 * Time: 7:13 PM
 */
public class LocationLogger {
    private Location _location;
    private String _tag;

    public LocationLogger(String tag, Location location) {
        _tag = tag;
        _location = location;
    }

    public LocationLogger(String tag) {
        _tag = tag;
        _location = new Location(LocationManager.PASSIVE_PROVIDER);
    }

    public void log(String tag, Location location) {
        _tag = tag;
        log(location);
    }

    public void log(Location location) {
        _location = location;
        logWithoutUpdate();
    }

    public void logWithoutUpdate() {
        makeEntry();
    }

    public void logAdvancedFeatureStatus(Location location) {
        Log.i(_tag, "Accuracy  : " + location.hasAccuracy());
        Log.i(_tag, "Altitude  : " + location.hasAltitude());
        Log.i(_tag, "Bearing   : " + location.hasBearing());
        Log.i(_tag, "Speed     : " + location.hasSpeed());

    }

    protected void makeEntry() {
        Log.i(_tag, "Provider  : " + _location.getProvider());
        Log.i(_tag, "Latitude  : " + _location.getLatitude());
        Log.i(_tag, "Longitude : " + _location.getLongitude());
        Log.i(_tag, "Accuracy  : " + _location.getAccuracy());
        Log.i(_tag, "Altitude  : " + _location.getAltitude());
        Log.i(_tag, "Bearing   : " + _location.getBearing());
        Log.i(_tag, "Speed     : " + _location.getSpeed());
        Log.i(_tag, "Time      : " + _location.getTime());
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        _location = location;
    }

    public String getTag() {
        return _tag;
    }

    public void setTag(String tag) {
        _tag = tag;
    }
}
