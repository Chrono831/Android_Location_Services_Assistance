package edu.uchicago.cs.dylanphall;

import android.location.Location;
import android.os.Bundle;

public class LocationBuilder {
    private Location _location;

    public LocationBuilder(String _provider) {
        this._location = new Location(_provider);
    }

    public LocationBuilder(Location _location) {
        this._location = new Location(_location);
    }

    public Location buildLocation() {
         return new Location(_location);
    }

    public LocationBuilder accuracy(float _accuracy) {
        this._location.setAccuracy(_accuracy);
        return this;
    }

    public LocationBuilder altitude(double _altitude) {
        this._location.setAltitude(_altitude);
        return this;
    }

    public LocationBuilder bearing(float _bearing) {
        this._location.setBearing(_bearing);
        return this;
    }

    public LocationBuilder extras(Bundle _extras) {
        this._location.setExtras(_extras);
        return this;
    }

    public LocationBuilder latitude(double _latitude) {
        this._location.setLatitude(_latitude);
        return this;
    }

    public LocationBuilder longitude(double _longitude) {
        this._location.setLongitude(_longitude);
        return this;
    }

    public LocationBuilder provider(String _provider) {
        this._location.setProvider(_provider);
        return this;
    }

    public LocationBuilder speed(float _speed) {
        this._location.setSpeed(_speed);
        return this;
    }

    public LocationBuilder time(long _time) {
        this._location.setTime(_time);
        return this;
    }

}
