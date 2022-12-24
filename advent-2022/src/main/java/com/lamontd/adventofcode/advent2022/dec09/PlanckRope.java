package com.lamontd.adventofcode.advent2022.dec09;

import com.lamontd.adventofcode.utils.coord.Coordinate;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.TreeSet;

public class PlanckRope {
    private String designator;
    private final Coordinate headPosition;
    private final Coordinate tailPosition;

    private final PlanckRope leadingRope;

    private PlanckRope trailingRope = null;

    private final Set<Coordinate> tailVisits = new TreeSet<>();

    public PlanckRope() {
        this.headPosition = new Coordinate();
        this.tailPosition = new Coordinate();
        this.leadingRope = null;
        addTailVisit();
    }

    public PlanckRope(Coordinate head, Coordinate tail) {
        this.headPosition = head;
        this.tailPosition = tail;
        this.leadingRope = null;
        addTailVisit();
    }

    public PlanckRope(String designator, PlanckRope leadingRope) {
        this.designator = designator;
        this.headPosition = leadingRope.getTailPosition();
        this.tailPosition = Coordinate.builder().x(headPosition.getX()).y(headPosition.getY()).build();
        this.leadingRope = leadingRope;
        leadingRope.setTrailingRope(this);
    }

    public Coordinate getHeadPosition() {
        return headPosition;
    }

    public Coordinate getTailPosition() {
        return tailPosition;
    }

    public PlanckRope getLeadingRope() {
        return leadingRope;
    }

    public void setTrailingRope(PlanckRope trailingRope) {
        this.trailingRope = trailingRope;
    }

    public PlanckRope getTrailingRope() {
        return trailingRope;
    }

    public Set<Coordinate> getTailVisits() {
        return tailVisits;
    }

    public int countUniqueTailVisits() {
        return tailVisits.size();
    }

    public void moveUp(int distance) {
        for (int move = 0; move < distance; move++) {
            headPosition.addY(1);
//            if (!endsAreTouching()) {
//                if (tailPosition.getX() != headPosition.getX()) {
//                    tailPosition.setX(headPosition.getX());
//                }
//                tailPosition.addY(1);
//            }
//            addTailVisit();
            adjustTail();
        }
    }

    public void moveDown(int distance) {
        for (int move = 0; move < distance; move++) {
            headPosition.subtractY(1);
//            if (!endsAreTouching()) {
//                if (tailPosition.getX() != headPosition.getX()) {
//                    tailPosition.setX(headPosition.getX());
//                }
//                tailPosition.subtractY(1);
//            }
//            addTailVisit();
            adjustTail();
        }
    }

    public void moveLeft(int distance) {
        for (int move = 0; move < distance; move++) {
            headPosition.subtractX(1);
//            if (!endsAreTouching()) {
//                if (tailPosition.getY() != headPosition.getY()) {
//                    tailPosition.setY(headPosition.getY());
//                }
//                tailPosition.subtractX(1);
//            }
//            addTailVisit();
            adjustTail();
        }
    }

    public void moveRight(int distance) {
        for (int move = 0; move < distance; move++) {
            headPosition.addX(1);
//            if (!endsAreTouching()) {
//                if (tailPosition.getY() != headPosition.getY()) {
//                    tailPosition.setY(headPosition.getY());
//                }
//                tailPosition.addX(1);
//            }
//            addTailVisit();
            adjustTail();
        }
    }

    protected void adjustTail() {
        if (!endsAreTouching()) {
            if (headPosition.getX() == tailPosition.getX()) {
                if (headPosition.getY() > tailPosition.getY()) {
                    tailPosition.addY(1);
                } else {
                    tailPosition.subtractY(1);
                }
            } else if (headPosition.getY() == tailPosition.getY()) {
                if (headPosition.getX() > tailPosition.getX()) {
                    tailPosition.addX(1);
                } else {
                    tailPosition.subtractX(1);
                }
            } else {
                if (headPosition.getX() > tailPosition.getX()) {
                    tailPosition.addX(1);
                } else {
                    tailPosition.subtractX(1);
                }
                if (headPosition.getY() > tailPosition.getY()) {
                    tailPosition.addY(1);
                } else {
                    tailPosition.subtractY(1);
                }
            }
        }
        addTailVisit();
        assert endsAreTouching();
        if (trailingRope != null) {
            trailingRope.adjustTail();
        }
    }

    private void addTailVisit() {
        tailVisits.add(Coordinate.builder().x(tailPosition.getX()).y(tailPosition.getY()).build());
    }

    public boolean endsOverlap() {
        return headPosition.equals(tailPosition);
    }

    public boolean endsAreTouching() {
        if (endsOverlap()) {
            return true;
        }
        if (headPosition.getX() == tailPosition.getX()) {
            return Math.abs(headPosition.getY() - tailPosition.getY()) == 1;
        }
        if (headPosition.getY() == tailPosition.getY()) {
            return Math.abs(headPosition.getX() - tailPosition.getX()) == 1;
        }
        return Math.abs(headPosition.getX() - tailPosition.getX()) == 1
                && Math.abs(headPosition.getY() - tailPosition.getY()) == 1;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Planck Rope ");
        if (StringUtils.isNotEmpty(designator)) {
            sb.append(designator).append(" ");
        }
        sb.append("Head: ").append(headPosition).append(", Tail: ").append(tailPosition).append(" - ").append(countUniqueTailVisits()).append(" unique places");
        return sb.toString();
    }
}
