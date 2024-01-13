package org.cis1200.battleship;


public class Ship implements Comparable<Ship> {
    private int startX;
    private int startY;
    private int length;
    private int endX;
    private int endY;
    private int numberOfHits;
    private char direc;
    private boolean isSunk;

    /**Ship Constructor */
    public Ship(int x, int y, int l, char direction, int hits, boolean sunk) {
        startX = x;
        startY = y;
        length = l;
        numberOfHits = hits;
        isSunk = sunk;
        direc = direction;

        if (direction == 'h') {
            endX = startX + length - 1;
            endY = startY;
        }
        if (direction == 'v') {
            endX = startX;
            endY = startY + length - 1;
        }
    }

    /**return start coordinates*/
    public int[] getStart() {
        int[] start = new int[2];
        start[0] = startX;
        start[1] = startY;
        return start;
    }

    /**return end coordinates*/
    public int[] getEnd() {
        int[] end = new int[2];
        end[0] = endX;
        end[1] = endY;
        return end;
    }

    /**return length*/
    public int getLength() {
        return length;
    }

    /**return direction */
    public char getDirection() {
        return direc;
    }

    /**return sunk status*/
    public boolean checkSunk() {
        return isSunk;
    }

    /**try to sink ship*/
    public void sink() {
        if (numberOfHits == length) {
            isSunk = true;
        }
    }

    /**increment the number of hits*/
    private void incHits() {
        numberOfHits++;
    }

    /**return the number of hits*/
    public int getHits() {
        return numberOfHits;
    }

    /**check if a ship occupies and is hit by a sepcific coordinate
     * and if it is hit increment the number of hits*/
    public boolean isHit(int x, int y) {
        //vertical ship
        if (startX == endX) {
            if (x != startX) {
                return false;
            }
            boolean hit = false;
            for (int j = startY; j <= endY; j++) {
                if (j == y) {
                    hit = true;
                }
            }
            if (hit) {
                incHits();
            }
            return hit;
        }
        //horizontal ship
        if (y != startY) {
            return false;
        }
        boolean hit = false;
        for (int i = startX; i <= endX; i++) {
            if (i == x) {
                hit = true;
            }
        }
        if (hit) {
            incHits();
        }
        return hit;
    }

    /**check if a new ship overlaps this ship*/
    public boolean overlaps(int newX, int newY, int newSize, char newDirection) {
        int newEndX;
        int newEndY;

        if (newDirection == 'h') {
            newEndX = newX + newSize - 1;
            newEndY = newY;
        } else {
            newEndX = newX;
            newEndY = newY + newSize - 1;
        }

        if (this.startX <= newEndX && this.endX >= newX && this.startY
                <= newEndY && this.endY >= newY) {
            return true; // Overlapping
        }

        return false;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "StarX='" + startX + '\'' +
                ", length=" + length +
                '}';
    }

    @Override
    public int compareTo(Ship o) {
        String current = "" + startX + startY + endX + endY + length;
        String other = "" + o.getStart()[0] + o.getStart()[1]
                + o.getEnd()[0] + o.getEnd()[1] + o.getLength();
        return current.compareTo(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ship ship = (Ship) o;
        return length == ship.getLength() && startX == (ship.getStart())[0] &&
                startY == (ship.getStart())[1] && endX == (ship.getEnd())[0] &&
                endY == (ship.getEnd())[1];
    }
}
