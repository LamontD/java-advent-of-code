package com.lamontd.adventofcode.advent2022.dec17;

import com.lamontd.adventofcode.utils.BigCoordinate;
import com.lamontd.adventofcode.utils.Coordinate;

import java.util.List;
import java.util.stream.Collectors;

public class FallingRock {

    private final String name;

    protected final BigCoordinate bottomLeft = BigCoordinate.of(0, 0);

    private final List<Coordinate> shape;

    private FallingRock(String name,
                        List<Coordinate> shape) {
        this.name = name;
        this.shape = shape;
    }

    public void orient(long column, long row) {
        bottomLeft.setX(column);
        bottomLeft.setY(row);
    }

    public void shiftLeft() {
        bottomLeft.subtractX(1);
    }

    public void shiftRight() {
        bottomLeft.addX(1);
    }

    public void drop() {
        bottomLeft.subtractY(1);
    }

    public BigCoordinate getBottomLeft() {
        return bottomLeft;
    }

    public String getName() {
        return name;
    }

    public List<BigCoordinate> getPoints() {
        return shape.stream()
                .map(point -> BigCoordinate.of(bottomLeft.getX() + point.getX(),
                        bottomLeft.getY() + point.getY()))
                .collect(Collectors.toList());
    }

    public static FallingRock MINUS() {
        return new FallingRock("MINUS",
                List.of(Coordinate.of(0, 0), Coordinate.of(1, 0),
                        Coordinate.of(2, 0), Coordinate.of(3, 0)));
    }

    public static FallingRock PLUS() {
        return new FallingRock("PLUS",
                List.of(Coordinate.of(1, 0),
                        Coordinate.of(0, 1), Coordinate.of(1, 1), Coordinate.of(2, 1),
                        Coordinate.of(1, 2)));
    }

    public static FallingRock ELBOW() {
        return new FallingRock("ELBOW",
                List.of(Coordinate.of(0, 0), Coordinate.of(1, 0), Coordinate.of(2, 0),
                        Coordinate.of(2, 1), Coordinate.of(2, 2)));
    }

    public static FallingRock PIPE() {
        return new FallingRock("PIPE",
                List.of(Coordinate.of(0, 0), Coordinate.of(0, 1), Coordinate.of(0, 2), Coordinate.of(0, 3)));
    }

    public static FallingRock BOX() {
        return new FallingRock("BOX", List.of(Coordinate.of(0, 0), Coordinate.of(1, 0),
                Coordinate.of(0, 1), Coordinate.of(1, 1)));
    }
}
