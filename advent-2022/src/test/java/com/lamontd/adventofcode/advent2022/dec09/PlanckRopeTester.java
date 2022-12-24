package com.lamontd.adventofcode.advent2022.dec09;

import com.lamontd.adventofcode.utils.coord.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlanckRopeTester {

    public static PlanckRope evenRope() { return new PlanckRope(); }
    public static PlanckRope headLeft() { return new PlanckRope(Coordinate.of(0, 0), Coordinate.of(1, 0)); }

    public static PlanckRope headRight() { return new PlanckRope(Coordinate.of(1, 0), Coordinate.of(0, 0)); }

    public static PlanckRope headUp() { return new PlanckRope(Coordinate.of(0, 1), Coordinate.of(0, 0));}

    public static PlanckRope headDown() { return new PlanckRope(Coordinate.of(0, 0), Coordinate.of(0, 1)); }

    public static PlanckRope headDiagUp() { return new PlanckRope(Coordinate.of(1, 1), Coordinate.of(0, 0)); }

    public static PlanckRope headDiagDown() { return new PlanckRope(Coordinate.of(0, 0), Coordinate.of(1, 1)); }

    @Test
    public void testEndsAreTouching() {
        PlanckRope rope = new PlanckRope();
        assertTrue(rope.endsAreTouching());
        rope = new PlanckRope(Coordinate.of(0, 1), Coordinate.of(0, 0));
        assertTrue(rope.endsAreTouching());
        rope = new PlanckRope(Coordinate.of(3, 3), Coordinate.of(3, 2));
        assertTrue(rope.endsAreTouching());
        rope = new PlanckRope(Coordinate.of(4, 5), Coordinate.of(4, 4));
        assertTrue(rope.endsAreTouching());
        rope = new PlanckRope(Coordinate.of(4, 3), Coordinate.of(4, 4));
        assertTrue(rope.endsAreTouching());
        rope = new PlanckRope(Coordinate.of(0, 2), Coordinate.of(0, 0));
        assertFalse(rope.endsAreTouching());
        rope = new PlanckRope(Coordinate.of(2, 2), Coordinate.of(1, 1));
        assertTrue(rope.endsAreTouching());
        rope = new PlanckRope(Coordinate.of(4, 4), Coordinate.of(5, 4));
        assertTrue(rope.endsAreTouching());
    }

    @Test
    public void testMoveUp() {
        PlanckRope rope = evenRope();
        rope.moveUp(1);
        assertEquals(Coordinate.of(0,1), rope.getHeadPosition());
        assertEquals(Coordinate.of(0, 0), rope.getTailPosition());
        rope = evenRope();
        rope.moveUp(4);
        assertEquals(Coordinate.of(0, 4), rope.getHeadPosition());
        assertEquals(Coordinate.of(0, 3), rope.getTailPosition());

        rope = headLeft();
        rope.moveUp(1);
        assertEquals(Coordinate.of(0, 1), rope.getHeadPosition());
        assertEquals(Coordinate.of(1, 0), rope.getTailPosition());
        rope = headLeft();
        rope.moveUp(4);
        assertEquals(Coordinate.of(0, 4), rope.getHeadPosition());
        assertEquals(Coordinate.of(0, 3), rope.getTailPosition());

        rope = headRight();
        rope.moveUp(1);
        assertEquals(Coordinate.of(1, 1), rope.getHeadPosition());
        assertEquals(Coordinate.of(0, 0), rope.getTailPosition());
        rope = headRight();
        rope.moveUp(4);
        assertEquals(Coordinate.of(1, 4), rope.getHeadPosition());
        assertEquals(Coordinate.of(1, 3), rope.getTailPosition());

        rope = headUp();
        rope.moveUp(1);
        assertEquals(Coordinate.of(0, 2), rope.getHeadPosition());
        assertEquals(Coordinate.of(0, 1), rope.getTailPosition());
        rope = headUp();
        rope.moveUp(4);
        assertEquals(Coordinate.of(0, 5), rope.getHeadPosition());
        assertEquals(Coordinate.of(0, 4), rope.getTailPosition());

        rope = headDown();
        rope.moveUp(1);
        assertEquals(Coordinate.of(0, 1), rope.getHeadPosition());
        assertEquals(Coordinate.of(0, 1), rope.getTailPosition());
        rope = headDown();
        rope.moveUp(4);
        assertEquals(Coordinate.of(0, 4), rope.getHeadPosition());
        assertEquals(Coordinate.of(0, 3), rope.getTailPosition());

        rope = headDiagUp();
        rope.moveUp(1);
        assertEquals(Coordinate.of(1, 2), rope.getHeadPosition());
        assertEquals(Coordinate.of(1, 1), rope.getTailPosition());
        rope = headDiagUp();
        rope.moveUp(4);
        assertEquals(Coordinate.of(1, 5), rope.getHeadPosition());
        assertEquals(Coordinate.of(1, 4), rope.getTailPosition());

        rope = headDiagDown();
        rope.moveUp(1);
        assertEquals(Coordinate.of(0, 1), rope.getHeadPosition());
        assertEquals(Coordinate.of(1, 1), rope.getTailPosition());
        rope = headDiagDown();
        rope.moveUp(4);
        assertEquals(Coordinate.of(0, 4), rope.getHeadPosition());
        assertEquals(Coordinate.of(0, 3), rope.getTailPosition());

    }

    @Test
    public void testSampleMoves() {
        PlanckRope rope = new PlanckRope();
        rope.moveRight(4);
        assertEquals(Coordinate.of(4, 0), rope.getHeadPosition());
        assertEquals(Coordinate.of(3, 0), rope.getTailPosition());

        rope.moveUp(4);
        assertEquals(Coordinate.of(4, 4), rope.getHeadPosition());
        assertEquals(Coordinate.of(4, 3), rope.getTailPosition());

        rope.moveLeft(3);
        assertEquals(Coordinate.of(1, 4), rope.getHeadPosition());
        assertEquals(Coordinate.of(2, 4), rope.getTailPosition());

        rope.moveDown(1);
        assertEquals(Coordinate.of(1, 3), rope.getHeadPosition());
        assertEquals(Coordinate.of(2, 4), rope.getTailPosition());

        rope.moveRight(4);
        assertEquals(Coordinate.of(5, 3), rope.getHeadPosition());
        assertEquals(Coordinate.of(4, 3), rope.getTailPosition());

        rope.moveDown(1);
        assertEquals(Coordinate.of(5, 2), rope.getHeadPosition());
        assertEquals(Coordinate.of(4, 3), rope.getTailPosition());

        rope.moveLeft(5);
        assertEquals(Coordinate.of(0, 2), rope.getHeadPosition());
        assertEquals(Coordinate.of(1, 2), rope.getTailPosition());

        rope.moveRight(2);
        assertEquals(Coordinate.of(2, 2), rope.getHeadPosition());
        assertEquals(Coordinate.of(1, 2), rope.getTailPosition());

        assertEquals(13, rope.getTailVisits().size());
    }
}
