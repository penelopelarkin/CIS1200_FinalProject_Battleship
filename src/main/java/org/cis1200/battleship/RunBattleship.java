package org.cis1200.battleship;

/*
 * My battleship game
 */

import javax.swing.*;
import java.awt.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 */
public class RunBattleship implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Battleship");
        frame.setLocation(700, 700);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final org.cis1200.battleship.GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        //add save button to save game
        final JButton save = new JButton("Save");
        save.addActionListener(e -> board.save());
        control_panel.add(save);

        //add load button to load in a game
        final JButton load = new JButton("Load in Game");
        load.addActionListener(e -> board.load());
        control_panel.add(load);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //add instructions popup
        Object[] options = { "OK" };
        JOptionPane.showOptionDialog(frame, "Welcome to Battleship! The computer\n" +
                        "will randomly generate between one and three ships\n" +
                        "of random lengths between two and four and place them\n" +
                        "randomly on the board. Ships can only be oriented\n" +
                        "vertically or horizontally. Your job is to use the\n" +
                        "mouse to click on squares in the grid and sink\n" +
                        "those ships! Once you click on a square, if it\n" +
                        "turns blue that means you hit water, but if it\n" +
                        "turns black that means that you hit a ship!\n" +
                        "There is a counter at the bottom to tell you\n" +
                        "how many ships you have sunk. You win the game\n" +
                        "by sinking all of the ships. If you would like to\n" +
                        "save a game board click the \"Save\" button and to load\n" +
                        "in a previous game click the \"Load In Game\" button.\n" +
                        "Good luck!",
                "Battleship Game Instructions",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);


    }
}