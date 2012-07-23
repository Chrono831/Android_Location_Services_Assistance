package edu.uchicago.cs.dylanphall;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.util.ArrayDeque;

public class MockLocationPaths {
    //This is a testing class, so I'm not going to do accurate speed / distance to lat / long conversion
    //Instead I'm going to use the following approximation:
    //Assume roughly at equator and
    // 1 degree lat = 110km
    // 1 degree long = 110km
    // and, accurately:
    // 1 degree = 60 minutes = 3600 seconds
    // thus
    // 1 meter 36 / 1100 ~= .032727 degrees
    // also, this class will use the android.Location.FORMAT_DEGREES notation:
    // DD.DDDDD

    public static final String DEFAULT_PROVIDER = LocationManager.PASSIVE_PROVIDER;
    public static final long DEFAULT_STEPS = 100;
    public static final float DEFAULT_SPEED = (float) 5.0;
    public static final double DEGREES_PER_METER = 0.032727;

    protected ArrayDeque<Location> path;
    protected Location location;
    protected float speed; // meters per second, same unit as android.Location
    protected boolean clockwise_rotation; // CW (clockwise) == true, CCW(counterclockwise) == false
    protected long steps;// 1 step == 1 second
    protected double heading; //degrees 0.0 - 359.9

    public enum Path {
        STRAIGHT, CIRCLE, SQUARE
    }

    public enum CardinalHeading {
        NORTH (0.0),
        EAST (90.0),
        SOUTH (180.0),
        WEST (270.0);

        private final double _heading; //in degrees

        private CardinalHeading(double heading) { _heading = heading; }

        public double heading() { return _heading; }
    }

    public MockLocationPaths() {
        path = new ArrayDeque<Location>();
        location = new Location(DEFAULT_PROVIDER);
        clockwise_rotation = true;
        setHeading(CardinalHeading.EAST.heading());
        //setPath(Path.STRAIGHT);
        setSpeed(DEFAULT_SPEED);
        setSteps(DEFAULT_STEPS);
        makeStraightPath();

    }

    public MockLocationPaths(Location start_point, long _steps, double _heading, float _speed) {
        location = start_point;
        setSteps(_steps);
        setHeading(_heading);
        setSpeed(_speed);
    }

    public void setPath(Path _path) {
        switch (_path) {
            case STRAIGHT:
                makeStraightPath();
                break;
            case CIRCLE:
                //makeCircularPath();
                break;
            case SQUARE:
                //makeSquarePath();
                break;
        }
    }

    private double calculateLatitude(Location l) {
        double new_latitude = l.getLatitude();
        new_latitude += Math.sin(getHeading()) * getSpeed() * DEGREES_PER_METER;
        return new_latitude;
    }

    private double calculateLongitude(Location l) {
        double new_longitude = l.getLongitude();
        new_longitude += Math.cos(getHeading()) * getSpeed() * DEGREES_PER_METER;
        return new_longitude;
    }

    public boolean hasMoreSteps() {
        return !path.isEmpty();
    }

    public Location getNextStep() {
        return path.pollLast();
    }

    private void makeStraightPath() {
        Location start = location;
        Location current = start;
        Location previous = current;
        Log.w("location", "MLP - step : " + steps);
        for(int i = 0; i < steps; i++) {
            double new_latitude = calculateLatitude(previous);
            double new_longitude = calculateLongitude(previous);
            // add speed to current in direction of travel
            // add to front of queue
            // replace previous with current
            //--------
            // when reading output, read from Last / Back / End of Queue FIFO
            current = new LocationBuilder(previous).latitude(new_latitude).longitude(new_longitude).buildLocation();
            path.addFirst(current);
            previous = current;
        }
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = Math.abs(speed);
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading % 360.0;
    }

    public long getSteps() { return path.size(); }

    public void setSteps(long steps) {
        if (steps < 1) {
            this.steps = 1;
        } else {
            this.steps = steps;
        }
    }
}