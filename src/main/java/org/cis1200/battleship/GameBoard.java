package org.cis1200.battleship;

/*
 * My Battleship game
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class instantiates a Battleship object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 */

public class GameBoard extends JPanel {

    private Battleship bship; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 700;
    public static final int BOARD_HEIGHT = 700;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        bship = new Battleship(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                bship.playTurn(p.x / 100, p.y / 100);

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        bship.reset();
        status.setText("Number of ships Sunk: 0  Number of ships left: " +
                bship.getNumShips());
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Saves the board state to a file.
     */
    public void save() {
        bship.saveBoardStateToFile(bship.getBoard(), "battleship_state.csv", false);
        repaint();
    }

    public void load() {
        bship.readFromFile("battleship_state.csv");
        updateStatus();
        repaint();
    }


     /* Updates the JLabel to reflect the current state of the game. */

    private void updateStatus() {
        boolean winner = bship.checkWinner();
        if (winner) {
            status.setText("You won!");
        } else {
            status.setText("Number of ships sunk: " + bship.getShipsSunk() +
                    "  Number of ships left: " + (bship.getNumShips()
                    - bship.getShipsSunk()));
        }
    }


    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.drawLine(100, 0, 100, 1000);
        g.drawLine(200, 0, 200, 1000);
        g.drawLine(300, 0, 300, 1000);
        g.drawLine(400, 0, 400, 1000);
        g.drawLine(500, 0, 500, 1000);
        g.drawLine(600, 0, 600, 1000);
        g.drawLine(0, 100, 1000, 100);
        g.drawLine(0, 200, 1000, 200);
        g.drawLine(0, 300, 1000, 300);
        g.drawLine(0, 400, 1000, 400);
        g.drawLine(0, 500, 1000, 500);
        g.drawLine(0, 600, 1000, 600);


        // Draws Black Squares and Blue Squares depending on whether water or a ship was
        //clicked
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                int state = bship.getCell(j, i);
                if (state == -1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(100 * j, 100 * i, 100, 100);
                } else if (state == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(100 * j, 100 * i, 100, 100);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
