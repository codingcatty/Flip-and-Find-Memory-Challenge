/*
 * Assignment: Assignment 5 - GUI Game
 * Project: Flip & Find - Memory Challenge
 * Author: Dhanashri Ghumate
 * Description: Starts the memory game application.
 */

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MemoryGame();
        });
    }
}