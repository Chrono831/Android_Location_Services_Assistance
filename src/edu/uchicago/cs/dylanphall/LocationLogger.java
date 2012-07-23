package edu.uchicago.cs.dylanphall;

import android.location.Location;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: dylan
 * Date: 7/22/12
 * Time: 7:13 PM
 */
public class LocationLogger {
    Location location;
    String tag;

    public LocationLogger(String tag, Location location) {
        this.tag = tag;
        this.location = location;
    }

    public void log(String tag, Location location) {
        this.tag = tag;
        log(location);
    }

    public void log(Location location) {
        this.location = location;
        logWithoutUpdate();
    }

    public void logWithoutUpdate() {
        makeEntry();
    }

    public void logAdvancedFeatureStatus(Location location) {
        Log.i(tag, "Accuracy  : " + location.hasAccuracy());
        Log.i(tag, "Altitude  : " + location.hasAltitude());
        Log.i(tag, "Bearing   : " + location.hasBearing());
        Log.i(tag, "Speed     : " + location.hasSpeed());

    }

    protected void makeEntry() {
        Log.i(tag, "Provider  : " + location.getProvider());
        Log.i(tag, "Latitude  : " + location.getLatitude());
        Log.i(tag, "Longitude : " + location.getLongitude());
        Log.i(tag, "Accuracy  : " + location.getAccuracy());
        Log.i(tag, "Altitude  : " + location.getAltitude());
        Log.i(tag, "Bearing   : " + location.getBearing());
        Log.i(tag, "Speed     : " + location.getSpeed());
        Log.i(tag, "Time      : " + location.getTime());
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
