package com.lamontd.adventofcode.advent2020.dec12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipNavigator {
    private static final Logger logger = LoggerFactory.getLogger(ShipNavigator.class);
    private int heading = 0;
    private Coordinate location = new Coordinate();

    public ShipNavigator() {}

    public ShipNavigator(int initialHeading) {
        this.heading = initialHeading;
    }

    public ShipNavigator(Coordinate initialLocation) {
        this.location = initialLocation;
    }

    public ShipNavigator(Coordinate initialLocation, int initialHeading) {
        this.heading = initialHeading;
        this.location = initialLocation;
    }

    public void navigate(String action) {
        char navgationAction = action.charAt(0);
        int amplitude = Integer.parseInt(action.substring(1));
        switch (navgationAction) {
            case 'N':
                moveNorth(amplitude);
                break;
            case 'S':
                moveSouth(amplitude);
                break;
            case 'E':
                moveEast(amplitude);
                break;
            case 'W':
                moveWest(amplitude);
                break;
            case 'L':
                turnLeft(amplitude);
                break;
            case 'R':
                turnRight(amplitude);
                break;
            case 'F':
                moveForward(amplitude);
                break;
            default:
                throw new IllegalArgumentException("Unknown action: '" + action + '"');
        }
    }

    public void moveNorth(int distance) {
        location.moveNorth(distance);
        logger.debug("Moved ship North " + distance + " units");
    }

    public void moveSouth(int distance) {
        location.moveSouth(distance);
        logger.debug("Moved ship South " + distance + " units");
    }

    public void moveEast(int distance) {
        location.moveEast(distance);
        logger.debug("Moved ship East " + distance + " units");
    }

    public void moveWest(int distance) {
        location.moveWest(distance);
        logger.debug("Moved ship west " + distance + " units");
    }

    public void turnRight(int degrees) {
        this.heading += degrees;
        this.heading %= 360;
        logger.debug("Turning Right " + degrees + " degrees to heading " + this.heading);
    }

    public void turnLeft(int degrees) {
        this.heading -= degrees;
        if (heading < 0)
            this.heading += 360;
        logger.debug("Turning Left " + degrees + " degrees to heading " + this.heading);

    }

    public void moveForward(int distance) {
        logger.debug("Moving " + distance + " units along heading " + this.heading);
        switch(this.heading) {
            case 0:
                location.moveNorth(distance);
                break;
            case 90:
                location.moveEast(distance);
                break;
            case 180:
                location.moveSouth(distance);
                break;
            case 270:
                location.moveWest(distance);
                break;
            default:
                throw new IllegalArgumentException("I don't know how to move along heading " + this.heading);
        }
    }

    public int getHeading() {
        return heading;
    }

    public Coordinate getLocation() {
        return location;
    }

    public int getManhattanDistanceTraveled() {
        return location.getManhattanDistance();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ship Navigator currently at location ").append(this.location).append(" with heading ").append(this.heading);
        return sb.toString();
    }
}
