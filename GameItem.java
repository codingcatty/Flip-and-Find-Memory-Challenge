/*
 * Assignment: Assignment 5 - GUI Game
 * Project: Flip & Find - Memory Challenge
 * Author: Dhanashri Ghumate
 * Description: Represents an item used in the memory game.
 */

public abstract class GameItem {

    private String symbol;

    public GameItem(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract void displayItem();
}
