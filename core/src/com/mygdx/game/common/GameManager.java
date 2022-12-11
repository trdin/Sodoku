package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;



import com.mygdx.game.Sudoku;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String DIFF_KEY = "diff";
    private static final String COLOR_KEY = "color";


    private static int diff;

    private static int colors;

    private final Preferences PREFS;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(Sudoku.class.getSimpleName());
        String diffStr = PREFS.getString(DIFF_KEY, "0");
        diff = Integer.parseInt(diffStr);
        String colorStr = PREFS.getString(COLOR_KEY, "1");
        colors = Integer.parseInt(colorStr);
    }

    public int getDiff() {
        return diff;
    }

    public int getColors() {
        return colors;
    }

    public void setDiff(int setDiff) {
        diff = setDiff;

        PREFS.putString(DIFF_KEY, Integer.toString(diff));
        PREFS.flush();
    }

    public void setColors(boolean setColor) {
        if(setColor){
            colors = 1;
        }else{
            colors = 0;
        }

        PREFS.putString(COLOR_KEY, Integer.toString(colors));
        PREFS.flush();
    }
}
