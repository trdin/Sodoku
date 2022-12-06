package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;



import com.mygdx.game.Sudoku;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String INIT_MOVE_KEY = "initMove";

    private final Preferences PREFS;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(Sudoku.class.getSimpleName());
        //String moveName = PREFS.getString(INIT_MOVE_KEY, CellState.X.name());
        //initMove = CellState.valueOf(moveName);
    }

   /* public CellState getInitMove() {
        return initMove;
    }

    public void setInitMove(CellState move) {
        initMove = move;

        PREFS.putString(INIT_MOVE_KEY, move.name());
        PREFS.flush();
    }*/
}
