package fr.theome.bingo.utils;

public enum WinCondition {

    ROWS(),ITEMS();

    @Override
    public String toString() {
        if (this.equals(WinCondition.ROWS)) return "lignes";
        else return "items";
    }
}
