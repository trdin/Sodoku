package com.mygdx.game.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.assets.AssetDescriptors;
import com.mygdx.assets.RegionNames;
import com.mygdx.game.Sudoku;
import com.mygdx.game.config.GameConfig;


public class IntroScreen extends ScreenAdapter {

    public static final float INTRO_DURATION_IN_SEC = 3f;   // duration of the (intro) animation

    private final Sudoku game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private TextureAtlas gameplayAtlas;

    private float duration = 0f;

    private Stage stage;

    public IntroScreen(Sudoku game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {

        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        // load assets
        assetManager.load(AssetDescriptors.UI_FONT);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.GAMEPLAY);
        assetManager.finishLoading();   // blocks until all assets are loaded

        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        Table table = new Table();
        table.defaults().pad(20);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));
        table.center();
        table.setFillParent(true);
        table.pack();
        stage.addActor(table);
       // stage.addActor(createKeyhole());
        for (int i = 0; i <= 8; i++) {
            stage.addActor(createAnimation(i));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(65 / 255f, 159 / 255f, 221 / 255f, 0f);

        duration += delta;

        // go to the MenuScreen after INTRO_DURATION_IN_SEC seconds
        if (duration > INTRO_DURATION_IN_SEC) {
            game.setScreen(new MenuScreen(game));
        }

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

    /*private Actor createKeyhole() {
        Image keyhole = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER0));
        // position the image to the center of the window
        keyhole.setPosition(viewport.getWorldWidth() / 2f - keyhole.getWidth() / 2f,
                viewport.getWorldHeight() / 2f - keyhole.getHeight() / 2f);
        return keyhole;

    }*/

    private Actor createAnimation(int num) {
        Image number = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER0));

        // set positions x, y to center the image to the center of the window
        float posX = (viewport.getWorldWidth() / 2f) - (number.getWidth() / 2f) - number.getWidth()  ;
        float posY = (viewport.getWorldHeight() / 2f) + (number.getHeight() / 2f)  ;

        if (num == 1) {
            number = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER1));

            // set positions x, y to ce0ter the image to the center of the window
            posX += number.getWidth() ;

        }else if( num == 2 ){
            number = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER2));
            posX += number.getWidth()*2;
            //number.setOrigin(Align.top);
        }else if(num == 3){
            number = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER3));
            posY -= number.getHeight();
        }else if(num == 4){
            number = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER4));
            posX += number.getWidth() ;
            posY -= number.getHeight();
        } else if (num == 5) {
            number = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER5));
            posX += number.getWidth() * 2;
            posY -= number.getHeight();
        }else if(num == 6){
            number = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER6));
            posY -= number.getHeight() *2;
        }else if(num == 7){
            number = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER7));
            posX += number.getWidth() ;
            posY -= number.getHeight()*2;
        } else if (num == 8) {
            number = new Image(gameplayAtlas.findRegion(RegionNames.NUMBER8));
            posX += number.getWidth() * 2;
            posY -= number.getHeight()*2;
        }

        number.setOrigin(Align.center);

        number.addAction(
                /* animationDuration = Actions.sequence + Actions.rotateBy + Actions.scaleTo
                                      = 1.5 + 1 + 0.5 = 3 sec */
                Actions.sequence(
                        Actions.parallel(
                                Actions.rotateBy(1080, 1.5f),   // rotate the image three times
                                Actions.moveTo(posX, posY, 1.5f)   // // move image to the center of the window
                        ),
                        Actions.rotateBy(-360, 1),  // rotate the image for 360 degrees to the left side
                        Actions.scaleTo(0, 0, 0.5f),    // "minimize"/"hide" image
                        Actions.removeActor()   // // remove image
                )
        );

        return number;
    }
}
