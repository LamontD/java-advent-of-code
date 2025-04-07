package com.lamontd.adventofcode.advent2023.day03;

import com.lamontd.adventofcode.utils.SingleValueCharGrid;
import com.lamontd.adventofcode.utils.coord.Coordinate;
import lombok.Getter;

import java.util.*;

public class EngineSchematic extends SingleValueCharGrid {
    @Getter private List<SchematicNumber> schematicNumbers;
    @Getter private List<Gear> gears;
    public EngineSchematic(List<String> layout) {
        super(layout);
        buildSchematic();
    }

    public void buildSchematic() {
        schematicNumbers = findSchematicNumbers();
        gears = findGears();
    }

    private List<Gear> findGears() {
        ArrayList<Coordinate> potentialGears = new ArrayList<>();
        for (int row = 0; row < charGrid.length; row++) {
            for (int col = 0; col < charGrid[row].length; col++) {
                if ('*' == charGrid[row][col]) {
                    potentialGears.add(Coordinate.of(row, col));
                }
            }
        }

        ArrayList<Gear> gearList = new ArrayList<>();
        for (Coordinate location : potentialGears) {
            Gear thisGear = new Gear(location);
            for (Coordinate adjacentLocation : getValidFullAdjacentPoints(location)) {
                if (Character.isDigit(getGridValue(adjacentLocation))) {
                    for (SchematicNumber schematicNumber : schematicNumbers) {
                        if (schematicNumber.numberTiles.contains(adjacentLocation)) {
                            thisGear.addAdjacentNumber(schematicNumber);
                            break;
                        }
                    }
                }
            }
            gearList.add(thisGear);
        }
        return gearList.stream()
                .filter(gear -> gear.getAdjacentNumbers().size() == 2)
                .toList();
    }
    private List<SchematicNumber> findSchematicNumbers() {
        ArrayList<SchematicNumber> schematicNumbers = new ArrayList<>();

        SchematicNumber currentNumber = null;
        for (int row = 0; row < charGrid.length; row++) {
            if (currentNumber != null) {
                currentNumber.closeNumber();
                schematicNumbers.add(currentNumber);
                currentNumber = null;
            }
            for (int col = 0; col < charGrid[row].length; col++) {
                if (Character.isDigit(getGridValue(row, col))) {
                    if (currentNumber == null) {
                        currentNumber = new SchematicNumber();
                    }
                    currentNumber.addNumberTile(Coordinate.of(row, col));
                } else {
                    if (currentNumber != null) {
                        currentNumber.closeNumber();
                        schematicNumbers.add(currentNumber);
                        currentNumber = null;
                    }
                }
            }
        }
        if (currentNumber != null) {
            currentNumber.closeNumber();
            schematicNumbers.add(currentNumber);
        }
        return schematicNumbers;
    }

    public class SchematicNumber {
        @Getter private int number = 0;
        @Getter private final List<Coordinate> numberTiles = new ArrayList<>();
        @Getter private final Set<Coordinate> adjacentPoints = new HashSet<>();
        public void addNumberTile(Coordinate tile) { numberTiles.add(tile);}
        public void closeNumber() {
            final StringBuilder sb = new StringBuilder();
            adjacentPoints.clear();
            numberTiles.forEach(tile -> {
                sb.append(getGridValue(tile));
                adjacentPoints.addAll(getValidFullAdjacentPoints(tile));
            });
            numberTiles.forEach(adjacentPoints::remove);
            this.number = Integer.parseInt(sb.toString());

        }
        public boolean isPartNumber() {
            for (Coordinate adjacent : adjacentPoints) {
                char gridValue = getGridValue(adjacent);
                if (!Character.isDigit(gridValue) && '.' != gridValue) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            SchematicNumber that = (SchematicNumber) o;
            return number == that.number && Objects.equals(numberTiles, that.numberTiles);
        }

        @Override
        public int hashCode() {
            return Objects.hash(number, numberTiles);
        }

        @Override
        public String toString() {
            return "SchematicNumber " + getNumber() + " at " + numberTiles.toString() + (isPartNumber() ? " is a part number" : "");
        }

    }

    public class Gear {
        @Getter private Coordinate location;
        @Getter final private Set<SchematicNumber> adjacentNumbers = new HashSet<>();
        public Gear(Coordinate location) { this.location = location;}
        public int getGearRatio() {
            int gearMultiplier = 1;
            for (SchematicNumber schematicNumber : adjacentNumbers) {
                gearMultiplier *= schematicNumber.getNumber();
            }
            return gearMultiplier;
        }
        public void addAdjacentNumber(SchematicNumber newNumber) { adjacentNumbers.add(newNumber); }

        @Override
        public String toString() {
            return "Gear at " + location + " is adjacent to " + adjacentNumbers.size() + " numbers and has a ratio of " + getGearRatio();
        }
    }

}
