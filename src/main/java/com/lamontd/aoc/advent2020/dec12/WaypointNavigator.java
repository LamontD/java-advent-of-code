package com.lamontd.aoc.advent2020.dec12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaypointNavigator extends ShipNavigator {
    private static final Logger logger = LoggerFactory.getLogger(WaypointNavigator.class);

    private Coordinate relativeWaypoint;

    public WaypointNavigator(Coordinate relativeWaypoint) {
        super();
        this.relativeWaypoint = relativeWaypoint;
    }

    @Override
    public void moveNorth(int distance) {
        this.relativeWaypoint.moveNorth(distance);
        logger.debug("Moved Waypoint North " + distance + " units");
    }

    @Override
    public void moveSouth(int distance) {
        this.relativeWaypoint.moveSouth(distance);
        logger.debug("Moved Waypoint South " + distance + " units");
    }

    @Override
    public void moveEast(int distance) {
        this.relativeWaypoint.moveEast(distance);
        logger.debug("Moved Waypoint East " + distance + " units");
    }

    @Override
    public void moveWest(int distance) {
        this.relativeWaypoint.moveWest(distance);
        logger.debug("Moved Waypoint West " + distance + " units");
    }

    @Override
    public void turnRight(int degrees) {
        int transformationDegrees = degrees;
        transformationDegrees %= 360;
        while (transformationDegrees < 0)
            transformationDegrees += 360;
        turnWaypoint(transformationDegrees);
        logger.debug("Turned waypoint right by " + degrees + " degrees");
    }

    @Override
    public void turnLeft(int degrees) {
        int transformationDegrees = -degrees;
        while (transformationDegrees < 0)
            transformationDegrees += 360;
        transformationDegrees %= 360;
        turnWaypoint(transformationDegrees);
        logger.debug("Turned waypoint Left by " + degrees + " degrees");
    }

    private void turnWaypoint(int transformationDegrees) {
        Coordinate.Builder newWaypoint = new Coordinate.Builder().east(relativeWaypoint.getEast()).north(relativeWaypoint.getNorth());
        switch(transformationDegrees) {
            case 0:
                break;
            case 90:
                newWaypoint.south(relativeWaypoint.getEast()).east(relativeWaypoint.getNorth());
                break;
            case 180:
                newWaypoint.south(relativeWaypoint.getNorth()).west(relativeWaypoint.getEast());
                break;
            case 270:
                newWaypoint.north(relativeWaypoint.getEast()).west(relativeWaypoint.getNorth());
                break;
            default:
                throw new IllegalArgumentException("Can't transform waypoint by " + transformationDegrees + " degrees");
        }
        this.relativeWaypoint = newWaypoint.build();
        logger.debug("Actually turned waypoint by " + transformationDegrees + " degrees");
    }

    @Override
    public void moveForward(int distance) {
        super.moveEast(this.relativeWaypoint.getEast() * distance);
        super.moveNorth(this.relativeWaypoint.getNorth() * distance);
        logger.debug("Moved To Waypoint " + distance + " times");
    }

    public Coordinate getRelativeWaypoint() { return this.relativeWaypoint; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WaypointNavigator: Ship currently at location ").append(getLocation()).append(" on heading ").append(getHeading()).append(" WAYPOINT at ").append(this.relativeWaypoint);
        return sb.toString();
    }
}
