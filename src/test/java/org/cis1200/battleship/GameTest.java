package org.cis1200.battleship;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameTest {
    //battleship tests
    @Test
    public void testPlayTurnHit() {
        Battleship battleship = new Battleship();
        battleship.removeShips();
        battleship.addShip(0, 0, 3, 'h', 0, false);
        battleship.playTurn(0, 0);
        assertEquals(1, battleship.getCell(0, 0));
    }

    @Test
    public void testPlayTurnWater() {
        Battleship battleship = new Battleship();
        battleship.removeShips();
        battleship.addShip(0, 0, 3, 'h', 0, false);
        battleship.playTurn(6, 0);
        assertEquals(-1, battleship.getCell(6, 0));
    }

    @Test
    public void testPlayTurnSinkingShip() {
        Battleship battleship = new Battleship();
        battleship.removeShips();
        battleship.addShip(0, 0, 3, 'h', 0, false);
        battleship.playTurn(0, 0);
        battleship.playTurn(1, 0);
        battleship.playTurn(2, 0);
        assertEquals(1, battleship.getCell(0, 0));
        assertEquals(1, battleship.getCell(0, 0));
        assertEquals(1, battleship.getCell(0, 0));
        assertEquals(1, battleship.getShipsSunk());
    }

    @Test
    public void testReset() {
        Battleship battleship = new Battleship();
        battleship.playTurn(1, 1);
        battleship.reset();
        assertEquals(0, battleship.getCell(1, 1));
    }

    @Test
    public void testCheckWinnerOnReset() {
        Battleship battleship = new Battleship();
        assertFalse(battleship.checkWinner());
        battleship.reset();
        assertFalse(battleship.checkWinner());
    }

    //ship tests
    @Test
    public void testIsHitVertical() {
        Ship ship = new Ship(2, 3, 2, 'v', 0, false);

        assertTrue(ship.isHit(2, 3));
        assertTrue(ship.isHit(2, 4));
        assertFalse(ship.isHit(3, 7));
        assertFalse(ship.isHit(4, 5));
        assertEquals(2, ship.getHits());
        ship.sink();
        assertTrue(ship.checkSunk());
    }

    @Test
    public void testIsHitHorizontal() {
        Ship ship = new Ship(5, 2, 4, 'h', 0, false);

        assertTrue(ship.isHit(5, 2));
        assertTrue(ship.isHit(6, 2));
        assertTrue(ship.isHit(8, 2));
        assertFalse(ship.isHit(5, 3));
        assertFalse(ship.isHit(8, 3));
        assertFalse(ship.isHit(7, 1));
        assertEquals(3, ship.getHits());
        ship.sink();
        assertFalse(ship.checkSunk());
    }

    @Test
    public void testIsHitInvalidCoordinates() {
        Ship ship = new Ship(3, 4, 3, 'v', 0, false);

        assertFalse(ship.isHit(10, 10));
        assertFalse(ship.isHit(-1, -1));
        assertEquals(0, ship.getHits());
        ship.sink();
        assertFalse(ship.checkSunk());
    }

    @Test
    public void testOverlapsMiddle() {
        Ship ship = new Ship(3, 4, 3, 'v', 0, false);
        assertTrue(ship.overlaps(2, 4, 4, 'h'));
    }

    @Test
    public void testOverlapsBeginning() {
        Ship ship = new Ship(4, 1, 3, 'h', 0, false);
        assertTrue(ship.overlaps(4, 1, 2, 'v'));
    }

    @Test
    public void testOverlapsEnd() {
        Ship ship = new Ship(4, 1, 3, 'h', 0, false);
        assertTrue(ship.overlaps(6, 1, 2, 'v'));
    }

    @Test
    public void testDoesNotOverlap() {
        Ship ship = new Ship(4, 1, 3, 'h', 0, false);
        assertFalse(ship.overlaps(2, 4, 4, 'h'));
    }

    @Test
    public void testGivenHitShip() {
        Ship ship = new Ship(0, 0, 3, 'h', 2, false);
        ship.isHit(2,0);
        ship.sink();
        assertTrue(ship.checkSunk());
    }

    //file IO
    @Test
    public void testSaveFile() {
        int[][] boardState = {
                {1, 1, -1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1}
        };

        String filePath = "test_board_state.csv";
        Battleship battleship = new Battleship();
        battleship.saveBoardStateToFile(boardState,filePath, false);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = br.readLine();
            assertEquals("1,1,-1,0,1,0,1", line) ;
        } catch (IOException e) {
            System.out.println("Caught");
        }
    }

    @Test
    public void testLoadFromFile() {
        int[][] boardState = {
                {1, 1, 1, 0, -1, 0, -1},
                {0, -1, 0, -1, 0, -1, 0},
                {-1, 0, -1, 0, -1, 0, -1},
                {0, -1, 0, -1, 0, -1, 0},
                {-1, 0, -1, 0, -1, 0, -1},
                {0, -1, 0, -1, 0, -1, 0},
                {-1, 0, -1, 0, -1, 0, -1}
        };

        String filePath = "test_board_state.csv";
        Battleship battleship = new Battleship();
        battleship.saveBoardStateToFile(boardState,filePath, false);
        battleship.readFromFile(filePath);
        assertArrayEquals(battleship.getBoard(), boardState);
    }
}
