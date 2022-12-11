package com.mygdx.game.objects;

import com.badlogic.gdx.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.screen.GameScreen;
import com.sun.tools.javac.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class leaderBoard {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);
    public static Map<String, Integer> score = new HashMap<String, Integer>();
    final static String filename = "./data/score.json";

    public static Scores scoreObj = new Scores();

   //  public static Vector<Pair<String,Integer>> v = new Vector<Pair<String,Integer>>(5);

    // private lateinit var gson: Gson

  /*  public static getScore(){
        intializeObj();
        return scoreObj;

    }
*/
    public static void addScore(String user, int score) {
        intializeObj();

        if(scoreObj.scores.size()<1){
            scoreObj.scores.add(score);
            scoreObj.names.add(user);
        }
        log.debug(score + " ");
        for (int i = 0; i < scoreObj.scores.size(); i++)
        {
            if(scoreObj.scores.get(i) < score){
                log.debug("smth");
                for(int j = scoreObj.scores.size()-1; j > i; j--){
                    scoreObj.scores.set(j, scoreObj.scores.get(j - 1));
                    scoreObj.names.set(j, scoreObj.names.get(j - 1));
                }
                scoreObj.scores.set(i,score);
                scoreObj.names.set(i, user);
                break;
            }
        }
/*
        for (int i = 0; i < v.size(); i++)
        {
            log.debug(v.get(i).snd + " " );
        }


        log.debug("inside");

        if(v.size()<5){
            v.add( new Pair<>(user, score));
        }

        Collections.sort(v, new ComparatorDesc());

        for (int i = 0; i < v.size(); i++)
        {
            if(v.get(i).snd < score){

                for(int j = 4; j < i; j++){
                    v.set(j, v.get(j - 1));
                }
                v.set(i, new Pair<>(user, score));
                break;
            }
        }

        for (Integer i = 0; i < v.size(); i++)
        {
            log.debug(v.get(i).fst + " "+v.get(i).snd );
        }*/

        //scoreObj.scores.add(score);
        //scoreObj.names.add(user);
       saveObj();

    }

   /* public static void intialize() {
        try {
            String path = "absolute path to your file";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            Gson gson = new Gson();
            Vector<Pair<String,Integer>> json = gson.fromJson(bufferedReader, Vector.class);

            log.debug(json.toString());


            for (Integer i = 0; i < json.size(); i++)
            {
                v.add(gson.fromJson(String.valueOf(json.get(i)), Pair.class));
                log.debug(v.get(i).toString());
                log.debug(v.get(i).fst.toString());
            }

            v = json;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void save() {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(v, writer);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug(e.toString());
        }
    }*/

    public static void saveObj() {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(scoreObj, writer);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug(e.toString());
        }
    }

    public static void intializeObj() {
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
