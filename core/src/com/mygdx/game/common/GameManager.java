package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


import com.badlogic.gdx.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.Sudoku;
import com.mygdx.game.objects.Scores;
import com.mygdx.game.screen.GameScreen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    final static String filename = "./data/score.json";

    public static Scores scoreObj = new Scores();

    private GameManager() {
        //intializeObj();
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

    public void addScore(String user, int score) {
        intializeObj();

        if (scoreObj.scores.size() < 1) {
            scoreObj.scores.add(score);
            scoreObj.names.add(user);
        }
        log.debug(score + " ");
        for (int i = 0; i < scoreObj.scores.size(); i++) {
            if (scoreObj.scores.get(i) < score) {
                log.debug("smth");
                for (int j = scoreObj.scores.size() - 1; j > i; j--) {
                    scoreObj.scores.set(j, scoreObj.scores.get(j - 1));
                    scoreObj.names.set(j, scoreObj.names.get(j - 1));
                }
                scoreObj.scores.set(i, score);
                scoreObj.names.set(i, user);
                break;
            }
        }
        saveObj();
    }

    public void saveObj() {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(scoreObj, writer);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug(e.toString());
        }
    }

    public void intializeObj() {
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            Gson gson = new Gson();
            Scores json = gson.fromJson(bufferedReader, Scores.class);

            //log.debug(json.toString());


            scoreObj =json;

            for (Integer i = 0; i < scoreObj.scores.size(); i++)
            {
                log.debug(scoreObj.names.get(i) + " " + scoreObj.scores.get(i));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
