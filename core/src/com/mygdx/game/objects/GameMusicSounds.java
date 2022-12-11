package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameMusicSounds {

    public static final GameMusicSounds INSTANCE = new GameMusicSounds();

    public static Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu.mp3"));

    public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/game.mp3"));

    public static Sound soundClick = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
    public static Sound soundFinish = Gdx.audio.newSound(Gdx.files.internal("sounds/finish.wav"));
    public static Sound soundMenu = Gdx.audio.newSound(Gdx.files.internal("sounds/menu.wav"));




}
