package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.assets.AssetDescriptors;
import com.mygdx.assets.RegionNames;
import com.mygdx.game.Sudoku;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.objects.GameMusicSounds;
import com.mygdx.game.objects.Scores;
import com.mygdx.game.objects.leaderBoard;

public class LeaderboardScreen extends ScreenAdapter {

    private final Sudoku game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;


    public LeaderboardScreen(Sudoku game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

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
        table.defaults().pad(80);

        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));



        /*checkBoxX = new CheckBox(CellState.X.name(), uiSkin);
        checkBoxO = new CheckBox(CellState.O.name(), uiSkin);

        checkBoxX.addListener(listener);
        checkBoxO.addListener(listener);

        checkBoxGroup = new ButtonGroup<>(checkBoxX, checkBoxO);
        checkBoxGroup.setChecked(GameManager.INSTANCE.getInitMove().name());*/

        TextButton backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMusicSounds.soundMenu.play(1.0f);
                game.setScreen(new MenuScreen(game));
            }
        });

        Table contentTable = new Table(uiSkin);

        TextureRegion menuBackground = gameplayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        contentTable.setBackground(new TextureRegionDrawable(menuBackground));

        Label title = new Label("Leader Board", uiSkin);
        title.setAlignment(Align.center);
        contentTable.add(title).padBottom(10).fillX().colspan(10).row();
        contentTable.add(new Label("Place", uiSkin)).padBottom(10).colspan(2);
        contentTable.add(new Label("Name", uiSkin)).padBottom(10).colspan(4);
        contentTable.add(new Label("Score", uiSkin)).padBottom(10).colspan(4).row();

        leaderBoard.intializeObj();

        for (int i = 0; i < leaderBoard.scoreObj.scores.size(); i++) {
            contentTable.add(new Label((i+1) +".", uiSkin)).padBottom(10).colspan(2);
            contentTable.add(new Label(leaderBoard.scoreObj.names.get(i), uiSkin)).padBottom(10).colspan(4);
            contentTable.add(new Label(leaderBoard.scoreObj.scores.get(i).toString(), uiSkin)).padBottom(10).colspan(4).row();
        }

        contentTable.add(backButton).width(300).padTop(50).colspan(10).center().row();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }
}