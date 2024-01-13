package org.cis1200.battleship;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.TreeSet;

/**
 * My Battleship Game
 */

public class Battleship {
    private int[][] board;
    private boolean gameOver;
    private int shipsSunk;
    private int numberOfShips;
    private TreeSet<Ship> ships;

    /**
     * Constructor sets up game state.
     */
    public Battleship() {
        reset();
    }

    /**
     * playTurn allows players to play a turn. If a player clicks on a square that is already
     * cliked on or the game is over, nothing happens. If a player clicks on an unlciked square
     * then the collection of ship objects is iterated through and checked to see if any
     * ship occupies that coordinate. If a ship occupies that coordinate that cell is changed
     * to -1. If a ship does not occupy that coordinate then the cell is changed to 1 which
     * means there is water there.
     *
     * @param c column to play in
     * @param r row to play in
     */

    public void playTurn(int c, int r) {
        //if its already clicked or the game is over do nothing
        if (board[r][c] != 0 || gameOver) {
            return;
        }
        //iterate through the collection and check if any are hit
        for (Ship aShip: ships) {
            if (aShip.isHit(c, r)) {
                //hit ship
                board[r][c] = 1;
                aShip.sink();
                if (aShip.checkSunk()) {
                    shipsSunk++;
                }
                break;
            } else { //water
                board[r][c] = -1;
            }
        }

    }

    /**
     * checkWinner checks whether the number of ships sunk equals the number of ships
     * on the board.
     *
     * @return false if nobody has won yet and true if they have*/

    public boolean checkWinner() {
        if (shipsSunk == numberOfShips) {
            gameOver = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nShips sunk "  + shipsSunk + "  Ships left " +
                        (numberOfShips - shipsSunk) + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 6) {
                    System.out.print(" | ");
                }
            }
            if (i < 6) {
                System.out.println("\n---------");
            }
        }
    }

    /** get the number of ships sunk */
    public int getShipsSunk() {
        return shipsSunk;
    }

    /** get the number of ships on the board */
    public int getNumShips() {
        return numberOfShips;
    }

    /**
     * reset (re-)sets the game state to start a new game. It randomly places a random number
     * of ships between 1 and 3 of random length between 2 and 4.
     */
    public void reset() {
        board = new int[7][7];
        shipsSunk = 0;
        gameOver = false;
        ships = new TreeSet<>();
        char direction;

        // Create between 1 and 3 ships
        numberOfShips = (int) (Math.random() * 3) + 1;

        for (int i = 0; i < numberOfShips; i++) {
            // Make it a random size between 2 and 4 length
            int size = (int) (Math.random() * 3) + 2;
            int rand = (int) (Math.random() * 2) + 1;
            if (rand == 1) {
                direction = 'h';
            } else {
                direction = 'v';
            }

            boolean positionOccupied = true;
            int x = -1;
            int y = -1;

            while (positionOccupied) {
                positionOccupied = false;
                //make sure the ship coordinates are within the grid
                if (direction == 'h') {
                    x = (int) (Math.random() * (7 - size));
                    y = (int) (Math.random() * 7);
                } else {
                    x = (int) (Math.random() * 7);
                    y = (int) (Math.random() * (7 - size));
                }

                //check if the chosen positions overlap with existing ships
                for (Ship ship : ships) {
                    if (ship.overlaps(x, y, size, direction)) {
                        positionOccupied = true;
                        break;
                    }
                }
            }

            //create ship object and add it to the set of ships
            Ship ship = new Ship(x, y, size, direction, 0, false);
            ships.add(ship);
        }
    }


    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = unlciked, 1 = ship, 2 = water
     */
    public int getCell(int c, int r) {
        return board[r][c];
    }

    /**returns a copy of the array of the board*/
    public int[][] getBoard() {
        int [][] copy = new int[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    /**Helper method for testing to remove all the randomly placed ships*/
    public void removeShips() {
        ships.clear();
    }

    /**Helper method for testing to add a specific ship*/
    public void addShip(int x, int y, int l, char d, int h, boolean s) {
        Ship ship = new Ship(x, y, l, d, h, s);
        ships.add(ship);
    }

    /**saveBoardStateToFile is used for file IO and saves the state of the board along with
     important information about each ship like where it starts, its length, its
     direction, if it is sunk or not, and how many hits it has. */
    public void saveBoardStateToFile(
            int[][] boardState,  String filePath,
            boolean append
    ) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;


        try {
            bw = new BufferedWriter(new FileWriter(file, append));

            //first write the 2D array to the file
            for (int r = 0; r < 7; r++) {
                if (r > 0) {
                    bw.newLine();
                }
                for (int c = 0; c < 7; c++) {
                    bw.write((String.valueOf(boardState[r][c])));
                    if (c < 6) {
                        bw.write(",");
                    }
                }
            }

            bw.newLine();

            //write num ships
            bw.write(String.valueOf(numberOfShips));
            bw.newLine();
            //write num ships sunk
            bw.write(String.valueOf(shipsSunk));
            bw.newLine();
            //write the information about each ship
            int counter = 1;
            for (Ship s: ships) {
                bw.write(String.valueOf(s.getStart()[0])); //x start
                bw.write(",");
                bw.write(String.valueOf(s.getStart()[1])); //y start
                bw.write(",");
                bw.write(String.valueOf(s.getLength())); //length
                bw.write(",");
                bw.write(String.valueOf(s.getDirection())); //direction
                bw.write(",");
                bw.write(String.valueOf(s.getHits())); //numHits
                bw.write(",");
                bw.write(String.valueOf(s.checkSunk())); //sunk
                if (counter <= numberOfShips) {
                    bw.newLine();
                }
            }

            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.out.println("Caught exception");
        }

    }


    /**readFromFile is used for file IO and reads the state of the board along with
     important information about each ship from the file creating new ship objects and
     updating the board, the numberOfShips, the shipsSunk. */
    public void readFromFile(String filePath) {
        int[][] boardState = new int[7][7];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while (row < 7) {
                line = br.readLine();
                String[] values = line.split(",");
                for (int col = 0; col < 7 && col < values.length; col++) {
                    boardState[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
            board = boardState;

            //after the boardState comes the number of ships
            ships.clear();
            line = br.readLine();
            numberOfShips = Integer.parseInt(line);
            //get the number of ships sunk
            line = br.readLine();
            shipsSunk = Integer.parseInt(line);
            //next comes the ship objects to create with their information
            while ((line = br.readLine()) != null) {
                String[] shipInfo = line.split(",");
                Ship newShip = new Ship(Integer.parseInt(shipInfo[0]),
                        Integer.parseInt(shipInfo[1]), Integer.parseInt(shipInfo[2]),
                        shipInfo[3].charAt(0), Integer.parseInt(shipInfo[4]),
                        Boolean.parseBoolean(shipInfo[5]));
                ships.add(newShip);
            }
        } catch (IOException e) {
            System.out.println("Caught exception");
        }
    }



    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Battleship b = new Battleship();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                b.playTurn(i, j);
                b.printGameState();
            }
        }
        System.out.println();
        System.out.println();
    }
}
