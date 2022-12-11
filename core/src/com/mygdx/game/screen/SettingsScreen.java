package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.assets.AssetDescriptors;
import com.mygdx.assets.RegionNames;
import com.mygdx.game.Sudoku;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.objects.GameMusicSounds;
import com.sun.tools.javac.comp.Check;

public class SettingsScreen extends ScreenAdapter {

    private final Sudoku game;
    private final AssetManager assetManager;
    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private Viewport viewport;
    private Stage stage;

    private Skin skin;
    private TextureAtlas gameplayAtlas;

    private BitmapFont font;

    public SettingsScreen(Sudoku game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());


        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);
        font = assetManager.get(AssetDescriptors.UI_FONT);


        stage.addActor(createUi());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Actor createUi() {
        Table table = new Table();
        table.defaults().pad(100, 200, 100, 200);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Table buttonTable = new Table();
        buttonTable.defaults().padLeft(30).padRight(30);
        TextureRegion menuBackgroundRegion = gameplayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        buttonTable.setBackground(new TextureRegionDrawable(menuBackgroundRegion));

        buttonTable.add(new Label("Settings", skin)).padBottom(30).colspan(10).row();


        buttonTable.add(new Label("Difficulty", skin)).padBottom(20).colspan(5);
        final TextField textfield = new TextField("", skin);
        textfield.setMessageText("pick between 0 and 3");

        textfield.setText(GameManager.INSTANCE.getDiff() + "");
        textfield.setAlignment(Align.center);

        buttonTable.add(textfield).padBottom(20).colspan(5).row();

        //buttonTable.add(new Label("Colors", skin)).padBottom(10).colspan(5);
        final CheckBox colors = new CheckBox(" Colors", skin);
        int colorVar = GameManager.INSTANCE.getColors();

        colors.setChecked(colorVar == 1);
        buttonTable.add(colors).padBottom(10).colspan(5);

        final CheckBox music = new CheckBox(" Music", skin);
        int musicVar = GameManager.INSTANCE.getMusic();

        music.setChecked(musicVar == 1);
        buttonTable.add(music).padBottom(10).colspan(5).row();

        final CheckBox sound= new CheckBox(" Sound", skin);
        int soundVar = GameManager.INSTANCE.getSound();

        sound.setChecked(soundVar == 1);
        buttonTable.add(sound).padBottom(10).colspan(10).row();

        TextButton saveScore = new TextButton("Save Settings", skin);
        saveScore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                log.debug("save");
                GameMusicSounds.INSTANCE.playSoundMenu();
                GameManager.INSTANCE.setColors(colors.isChecked());
                GameManager.INSTANCE.setMusic(music.isChecked());
                if(!music.isChecked()){
                    GameMusicSounds.musicMenu.stop();
                }else{
                    GameMusicSounds.INSTANCE.playMusicMenu();
                }
                GameManager.INSTANCE.setSound(sound.isChecked());
                String text = textfield.getText();
                int diff = 0;
                if(isNumeric(text)){
                    diff = Integer.parseInt(text);
                    if(diff < 0){
                        diff = 0;
                    }else if(diff > 3 ){
                        diff = 3;
                    }
                    GameManager.INSTANCE.setDiff(diff);
                }else{
                    textfield.setText("");
                    textfield.setMessageText("must be a number between 0 and 3");
                }
            }
        });

        buttonTable.add(saveScore).padBottom(15).colspan(10).row();

        final TextButton backButton = new TextButton("Back to Menu", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMusicSounds.INSTANCE.playSoundMenu();
                game.setScreen(new MenuScreen(game));
            }
        });

        buttonTable.add(backButton).padTop(30).colspan(10).row();



        // buttonTable.add(introButton).padBottom(15).expandX().fillX().row();
        // buttonTable.add(playButton).padBottom(15).expandX().fill().row();
        /*buttonTable.add(leaderboardButton).padBottom(15).fillX().row();
        buttonTable.add(settingsButton).padBottom(15).fillX().row();
        buttonTable.add(quitButton).fillX();*/

        buttonTable.center();

        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}