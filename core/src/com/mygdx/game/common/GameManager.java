package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;



import com.mygdx.game.Sudoku;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String DIFF_KEY = "diff";
    private static final String COLOR_KEY = "color";

    private static final String MUSIC_KEY = "music";
    private static final String SOUND_KEY = "sound";


    private static int diff;

    private static int colors;

    private static int music;

    private static int sound;

    private final Preferences PREFS;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(Sudoku.class.getSimpleName());
        String diffStr = PREFS.getString(DIFF_KEY, "0");
        diff = Integer.parseInt(diffStr);
        String colorStr = PREFS.getString(COLOR_KEY, "1");
        colors = Integer.parseInt(colorStr);
        String musicStr = PREFS.getString(MUSIC_KEY, "1");
        music = Integer.parseInt(musicStr);
        String soundStr = PREFS.getString(SOUND_KEY, "1");
        sound = Integer.parseInt(soundStr);
    }

    public int getDiff() {
        return diff;
    }


    public int getColors() {
        return colors;
    }

    public int getMusic() {
        return music;
    }
    public int getSound() {
        return sound;
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

    public void setMusic(boolean setMusic) {
        if(setMusic){
            music = 1;
        }else{
            music = 0;
        }

        PREFS.putString(MUSIC_KEY, Integer.toString(music));
        PREFS.flush();
    }
    public void setSound(boolean setSound) {
        if(setSound){
            sound = 1;
        }else{
            sound = 0;
        }

        PREFS.putString(SOUND_KEY, Integer.toString(sound));
        PREFS.flush();
    }
}
