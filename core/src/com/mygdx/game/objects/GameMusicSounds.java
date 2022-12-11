package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.common.GameManager;

public class GameMusicSounds {

    public static final GameMusicSounds INSTANCE = new GameMusicSounds();

    public static Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu.mp3"));

    public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/game.mp3"));

    public static Sound soundClick = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
    public static Sound soundFinish = Gdx.audio.newSound(Gdx.files.internal("sounds/finish.wav"));
    public static Sound soundMenu = Gdx.audio.newSound(Gdx.files.internal("sounds/menu.wav"));

    public void playMusicMenu(){
        if(GameManager.INSTANCE.getMusic() == 1 ){
            if(!musicMenu.isPlaying()){
                musicMenu.play();
                musicMenu.setLooping(true);
                musicMenu.setVolume(0.2f);
            }
        }
    }

    public void playMusicGame(){
        if(GameManager.INSTANCE.getMusic() == 1 ){
            if(!gameMusic.isPlaying()){
                gameMusic.play();
                gameMusic.setLooping(true);
                gameMusic.setVolume(0.5f);
            }
        }
    }

    public void playSoundClick(){
        if(GameManager.INSTANCE.getSound() == 1 ){
            soundClick.play();
        }
    }
    public void playSoundFinish(){
        if(GameManager.INSTANCE.getSound() == 1 ){
            soundFinish.play();
        }
    }
    public void playSoundMenu(){
        if(GameManager.INSTANCE.getSound() == 1 ){
            soundMenu.play();
        }
    }

}
