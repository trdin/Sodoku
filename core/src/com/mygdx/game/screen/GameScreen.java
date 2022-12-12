package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.assets.AssetDescriptors;
import com.mygdx.assets.RegionNames;
import com.mygdx.game.Sudoku;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.objects.Board;
import com.mygdx.game.objects.Cell;
import com.mygdx.game.objects.Data;
import com.mygdx.game.objects.GameMusicSounds;

public class GameScreen extends ScreenAdapter {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final Sudoku game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Viewport hudViewport;

    private Stage gameplayStage;
    private Stage finishStage;
    private Stage hudStage;

    private Skin skin;
    private TextureAtlas gameplayAtlas;
    private BitmapFont font;

    private Cell selectedCell;

    private Board board;

    private int size = 9;

    private int score = 0;
    private long lastScoreTime = 0;

    final long SCORE_DEDUCT_TIME= 1000;

    private Label scoreLabel;
    private Label endScore;


    //private CellState move = GameManager.INSTANCE.getInitMove();
    private Image infoImage;

    public GameScreen(Sudoku game) {
        score = 1000;
        //log.debug(GameManager.INSTANCE.getDiff());
        switch(GameManager.INSTANCE.getDiff()) {
            case 0:
                score = 50;
                break;
            case 1:
                score = 500;
                break;
            case 2:
                score = 1500;
                break;
            case 3:
                score = 2000;
                break;
            default:
                score = 2000;
        }
        lastScoreTime = TimeUtils.millis();
        this.game = game;
        assetManager = game.getAssetManager();
        board = new Board(Data.getBoard(), new Cell[size][size]);
        //log.debug("create");

        //board.board = Data.solved.clone();
    }

    @Override
    public void show() {

        //board = new Board(Data.solved.clone(), new Cell[size][size]);



        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);

        gameplayStage = new Stage(viewport, game.getBatch());
        hudStage = new Stage(hudViewport, game.getBatch());
        finishStage = new Stage(hudViewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);
        font = assetManager.get(AssetDescriptors.UI_FONT);

        scoreLabel = new Label("Score: " + score , skin);
        scoreLabel.setAlignment(Align.center);
       // scoreLabel.setPosition(GameConfig.HUD_WIDTH / 2f +50f, 20f);

        gameplayStage.addActor(createGrid(9, 5));
        hudStage.addActor(createScore());
        //hudStage.addActor(scoreLabel);
        hudStage.addActor(createBackButton());

        finishStage.addActor(setFinishScreen());

        Gdx.input.setInputProcessor(new InputMultiplexer(gameplayStage, hudStage, finishStage));

        GameMusicSounds.INSTANCE.playMusicGame();
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
            //log.debug(selectedCell.row + " " + selectedCell.column);
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
                setNumber(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2)) {
                setNumber(2);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3)) {
                setNumber(3);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_4) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
                setNumber(4);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_5) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {
                setNumber(5);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_6) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {
                setNumber(6);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_7) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_7)) {
                setNumber(7);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_8) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {
                setNumber(8);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_9) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_9)) {
                setNumber(9);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                setNumber(selectedCell.number);
                selectedCell = null;
            }
        }

        if (TimeUtils.millis() - lastScoreTime > SCORE_DEDUCT_TIME && !board.valid && score > 0 ){
            lastScoreTime = TimeUtils.millis();
            score -= 1;
            scoreLabel.setText("score: "+ score);
        }


        // update
        if (!board.valid) {
            gameplayStage.act(delta);
        } else {
            finishStage.act(delta);
        }
        hudStage.act(delta);

        // draw
        if (!board.valid) {
            gameplayStage.draw();
        } else {
            endScore.setText("your score: " + this.score);
            finishStage.draw();
        }
        hudStage.draw();

        //log.debug(scoreLabel.getText().toString());
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
        table.defaults().pad(5, 9, 0, 10);
        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));
        final Table grid = new Table();
        grid.defaults().size(cellSize);   // all cells will be the same size
        grid.setDebug(false);

        final TextureRegion region0 = gameplayAtlas.findRegion(RegionNames.EMPTY);
        /*int[][] data = Data.simple;
        Cell[][] cells = new Cell[size][size];*/
        //board.board = Data.solved;

        TextureRegion menuBackgroundRegion = gameplayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        grid.setBackground(new TextureRegionDrawable(menuBackgroundRegion));

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                final Cell cell = new Cell(region0);
                cell.column = column;
                cell.row = row;

                cell.preset = (board.board[row][column] != 0);

                setNumberCell(board.board[row][column], cell);

                if (cell.number == 0) {
                    cell.addListener(new ClickListener() {
                        public void clicked(InputEvent event, float x, float y) {
                            GameMusicSounds.INSTANCE.playSoundClick();
                            final Cell clickedCell = (Cell) event.getTarget(); // it will be an image for sure :-)
                            clickedCell.selected = true;
                            if (selectedCell != null) {
                                selectedCell.selected = false;
                                setNumber(selectedCell.number);
                            }
                            selectedCell = clickedCell;
                            if (selectedCell.number == 0) {
                                selectedCell.setDrawable(gameplayAtlas.findRegion(RegionNames.SELECTED));
                            } else {
                                setNumberCell(selectedCell.number, selectedCell);
                            }
                        }
                    });
                }
                board.cellsBoard[row][column] = cell;
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

    private Actor setFinishScreen() {


        final Table table = new Table();
        table.setDebug(false);
        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));
        table.defaults().pad(100, 200, 0, 200);// turn on all debug lines (table, cell, and widget)
        //table.defaults().pad(17);
        final Table menu = new Table();
        menu.setDebug(false);
        menu.defaults().pad(30, 50, 0, 50);
        TextureRegion menuBackgroundRegion = gameplayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        menu.setBackground(new TextureRegionDrawable(menuBackgroundRegion));

        Label finish = new Label("Congratulations you have completed the game ", skin);
        finish.setAlignment(Align.center);
        menu.add(finish).padBottom(5).expandX().fill().row();

        endScore = new Label("your score: " + this.score, skin);
        endScore.setAlignment(Align.center);
        menu.add(endScore).padBottom(5).expandX().fill().row();


        /*TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().scale(0.01f);*/
        /*TextField.TextFieldStyle textstyle = new TextField.TextFieldStyle();
        textstyle.font = font;*/

        final TextField textfield = new TextField("", skin);

        textfield.setMessageText("Username: click to add");
        textfield.setAlignment(Align.center);

        menu.add(textfield).padBottom(15).expandX().fill().row();


        final TextButton saveScore = new TextButton("Save your score", skin);
        saveScore.setWidth(100);
        saveScore.setColor(Color.RED);
        saveScore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String test = textfield.getText();
                GameMusicSounds.INSTANCE.playSoundMenu();
                if (test == null || test.equals("") || test.equals(" ")) {
                    textfield.setMessageText("Cant save score without username");
                } else {
                    log.debug(test);
                    GameManager.INSTANCE.addScore(test, score);
                    GameMusicSounds.gameMusic.stop();
                    game.setScreen(new MenuScreen(game));
                }
            }
        });

        menu.add(saveScore).padBottom(15).expandX().fill().row();
        menu.top();
        table.add(menu);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;

    }


    private void setNumber(int num) {
        GameMusicSounds.INSTANCE.playSoundFinish();
        selectedCell.selected = false;
        board.board[selectedCell.row][selectedCell.column] = num;
        switch (num) {
            case 1:
                selectedCell.setNumber(1, gameplayAtlas.findRegion(RegionNames.NUMBER1W));
                break;
            case 2:
                selectedCell.setNumber(2, gameplayAtlas.findRegion(RegionNames.NUMBER2W));
                break;
            case 3:
                selectedCell.setNumber(3, gameplayAtlas.findRegion(RegionNames.NUMBER3W));
                break;
            case 4:
                selectedCell.setNumber(4, gameplayAtlas.findRegion(RegionNames.NUMBER4W));
                break;
            case 5:
                selectedCell.setNumber(5, gameplayAtlas.findRegion(RegionNames.NUMBER5W));
                break;
            case 6:
                selectedCell.setNumber(6, gameplayAtlas.findRegion(RegionNames.NUMBER6W));
                break;
            case 7:
                selectedCell.setNumber(7, gameplayAtlas.findRegion(RegionNames.NUMBER7W));
                break;
            case 8:
                selectedCell.setNumber(8, gameplayAtlas.findRegion(RegionNames.NUMBER8W));
                break;
            case 9:
                selectedCell.setNumber(9, gameplayAtlas.findRegion(RegionNames.NUMBER9W));
                break;
            default:
                selectedCell.setNumber(0, gameplayAtlas.findRegion(RegionNames.EMPTY));
        }
        selectedCell = null;
        board.check();

    }

    private void setNumberCell(int num, Cell cell) {

        switch (num) {
            case 1:
                cell.setNumber(1, gameplayAtlas.findRegion(RegionNames.NUMBER1W));
                break;
            case 2:
                cell.setNumber(2, gameplayAtlas.findRegion(RegionNames.NUMBER2W));
                break;
            case 3:
                cell.setNumber(3, gameplayAtlas.findRegion(RegionNames.NUMBER3W));
                break;
            case 4:
                cell.setNumber(4, gameplayAtlas.findRegion(RegionNames.NUMBER4W));
                break;
            case 5:
                cell.setNumber(5, gameplayAtlas.findRegion(RegionNames.NUMBER5W));
                break;
            case 6:
                cell.setNumber(6, gameplayAtlas.findRegion(RegionNames.NUMBER6W));
                break;
            case 7:
                cell.setNumber(7, gameplayAtlas.findRegion(RegionNames.NUMBER7W));
                break;
            case 8:
                cell.setNumber(8, gameplayAtlas.findRegion(RegionNames.NUMBER8W));
                break;
            case 9:
                cell.setNumber(9, gameplayAtlas.findRegion(RegionNames.NUMBER9W));
                break;
            default:
                cell.setNumber(0, gameplayAtlas.findRegion(RegionNames.EMPTY));
        }

    }


    private Actor createBackButton() {
        final TextButton backButton = new TextButton("Back to Menu", skin);
        backButton.setWidth(100);
        backButton.setPosition(GameConfig.HUD_WIDTH / 2f - backButton.getWidth() / 2f, 20f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMusicSounds.gameMusic.stop();
                GameMusicSounds.INSTANCE.playSoundMenu();
                game.setScreen(new MenuScreen(game));
            }
        });
        return backButton;
    }


    private Actor createScore() {
        final Table table = new Table();
        table.setDebug(false);


        final Table board = new Table();
        TextureRegion menuBackgroundRegion = gameplayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        board.setBackground(new TextureRegionDrawable(menuBackgroundRegion));

        log.debug(scoreLabel.getText().toString());
        board.add(scoreLabel).padBottom(5).expandX().fill().row();



        board.center();
        //table.defaults().pad(0,0, GameConfig.HUD_HEIGHT-200f, GameConfig.HUD_WIDTH -200f );
        table.defaults().width(150).height(50);
        table.add(board);

        table.center();
        table.pack();
        table.setPosition(
                0 ,
                GameConfig.HUD_HEIGHT - table.getHeight()
        );
        return table;
    }
}
