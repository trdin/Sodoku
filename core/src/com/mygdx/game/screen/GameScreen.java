package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.assets.AssetDescriptors;
import com.mygdx.assets.RegionNames;
import com.mygdx.game.Sudoku;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.objects.Cell;

public class GameScreen extends ScreenAdapter {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final Sudoku game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Viewport hudViewport;

    private Stage gameplayStage;
    private Stage hudStage;

    private Skin skin;
    private TextureAtlas gameplayAtlas;

    private Cell selectedCell;


    //private CellState move = GameManager.INSTANCE.getInitMove();
    private Image infoImage;

    public GameScreen(Sudoku game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);

        gameplayStage = new Stage(viewport, game.getBatch());
        hudStage = new Stage(hudViewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        gameplayStage.addActor(createGrid(9, 5));
       //hudStage.addActor(createInfo());
        hudStage.addActor(createBackButton());

        Gdx.input.setInputProcessor(new InputMultiplexer(gameplayStage, hudStage));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(195 / 255f, 195 / 255f, 195 / 255f, 0f);

        if (selectedCell != null) {
            log.debug(selectedCell.row + " " + selectedCell.column);
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
                setNumber(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2) ) {
                setNumber(2);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3)) {
                setNumber(3);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_4) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
                setNumber(4);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)|| Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {
                setNumber(5);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_6) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {
                setNumber(6);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_7)|| Gdx.input.isKeyPressed(Input.Keys.NUMPAD_7)) {
                setNumber(7);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_8)|| Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {
                setNumber(8);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_9)|| Gdx.input.isKeyPressed(Input.Keys.NUMPAD_9)) {
                setNumber(9);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                setNumber(selectedCell.number);
                selectedCell = null;
            }
        }
        // update
        gameplayStage.act(delta);
        hudStage.act(delta);

        // draw
        gameplayStage.draw();
        hudStage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameplayStage.dispose();
        hudStage.dispose();
    }

    private Actor createGrid(int size, final float cellSize) {
        final Table table = new Table();
        table.setDebug(false);   // turn on all debug lines (table, cell, and widget)

        final Table grid = new Table();
        grid.defaults().size(cellSize);   // all cells will be the same size
        grid.setDebug(false);

        final TextureRegion region0 = gameplayAtlas.findRegion(RegionNames.EMPTY);


        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                final Cell cell = new Cell(region0);
                cell.column = column;
                cell.row = row;
                cell.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        final Cell clickedCell = (Cell) event.getTarget(); // it will be an image for sure :-)
                        if(selectedCell !=null){
                            setNumber(selectedCell.number);
                        }
                        selectedCell = clickedCell;
                        selectedCell.setDrawable(gameplayAtlas.findRegion(RegionNames.SELECTED));
                        log.debug("clicked " + selectedCell.row + " " + selectedCell.column);
                    }
                });
                grid.add(cell);
            }
            grid.row();
        }

        table.add(grid).row();
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void setNumber(int num) {

        switch (num) {
            case 1:
                selectedCell.setNumber(1, gameplayAtlas.findRegion(RegionNames.NUMBER1B));
                break;
            case 2:
                selectedCell.setNumber(2, gameplayAtlas.findRegion(RegionNames.NUMBER2B));
                break;
            case 3:
                selectedCell.setNumber(3, gameplayAtlas.findRegion(RegionNames.NUMBER3B));
                break;
            case 4:
                selectedCell.setNumber(4, gameplayAtlas.findRegion(RegionNames.NUMBER4B));
                break;
            case 5:
                selectedCell.setNumber(5, gameplayAtlas.findRegion(RegionNames.NUMBER5B));
                break;
            case 6:
                selectedCell.setNumber(6, gameplayAtlas.findRegion(RegionNames.NUMBER6B));
                break;
            case 7:
                selectedCell.setNumber(7, gameplayAtlas.findRegion(RegionNames.NUMBER7B));
                break;
            case 8:
                selectedCell.setNumber(8, gameplayAtlas.findRegion(RegionNames.NUMBER8B));
                break;
            case 9:
                selectedCell.setNumber(9, gameplayAtlas.findRegion(RegionNames.NUMBER9B));
                break;
            default:
                selectedCell.setNumber(0, gameplayAtlas.findRegion(RegionNames.EMPTY));
        }
        selectedCell = null;

    }

    /*private Actor createGrid(int rows, int columns, final float cellSize) {
        final Table table = new Table();
        table.setDebug(false);   // turn on all debug lines (table, cell, and widget)

        final Table grid = new Table();
        grid.defaults().size(cellSize);   // all cells will be the same size
        grid.setDebug(false);

        final TextureRegion emptyRegion = gameplayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        final TextureRegion xRegion = gameplayAtlas.findRegion(RegionNames.NUMBER0);
        final TextureRegion oRegion = gameplayAtlas.findRegion(RegionNames.NUMBER1);

        if (move == CellState.X) {
            infoImage = new Image(xRegion);
        } else if (move == CellState.O) {
            infoImage = new Image(oRegion);
        }

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                final CellActor cell = new CellActor(emptyRegion);
                cell.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        final CellActor clickedCell = (CellActor) event.getTarget(); // it will be an image for sure :-)
                        if (clickedCell.isEmpty()) {
                            switch (move) {
                                case X:
                                    clickedCell.setState(move);
                                    clickedCell.setDrawable(xRegion);
                                    infoImage.setDrawable(new TextureRegionDrawable(oRegion));
                                    move = CellState.O;
                                    break;
                                case O:
                                    clickedCell.setState(move);
                                    clickedCell.setDrawable(oRegion);
                                    infoImage.setDrawable(new TextureRegionDrawable(xRegion));
                                    move = CellState.X;
                                    break;
                            }
                        }
                        log.debug("clicked");
                    }
                });
                grid.add(cell);
            }
            grid.row();
        }

        table.add(grid).row();
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }*/

    private Actor createBackButton() {
        final TextButton backButton = new TextButton("Back", skin);
        backButton.setWidth(100);
        backButton.setPosition(GameConfig.HUD_WIDTH / 2f - backButton.getWidth() / 2f, 20f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        return backButton;
    }

    private Actor createInfo() {
        final Table table = new Table();
        table.add(new Label("Turn: ", skin));
        table.add(infoImage).size(30).row();
        table.center();
        table.pack();
        table.setPosition(
                GameConfig.HUD_WIDTH / 2f - table.getWidth() / 2f,
                GameConfig.HUD_HEIGHT - table.getHeight() - 20f
        );
        return table;
    }
}
