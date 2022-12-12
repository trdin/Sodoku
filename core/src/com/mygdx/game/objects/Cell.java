package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.common.GameManager;

public class Cell extends Image {
    public static final float CELL_DIM = 5;

    public int number = 0;
    public boolean selected;
    public boolean preset = false;
    public int row;
    public int column;

    public Cell(TextureRegion region) {
        super(region);
    }

    public void setDrawable(TextureRegion region) {

        TintableRegionDrawable tint = new TintableRegionDrawable(region);
        tint.setTint(Color.RED);
        super.setDrawable(tint);
        //addAnimation(); // play animation when region changed
    }

    public void setNumber(int num, TextureRegion region) {
        number = num;
        TintableRegionDrawable tint = new TintableRegionDrawable(region);
        if(preset && GameManager.INSTANCE.getColors() == 1){
            tint.setTint(Color.FOREST);
        }else if(selected){
            tint.setTint(Color.RED);
        }else {
            tint.setTint(Color.BLACK);
        }
        if(!preset && !selected && number !=0){
            addAnimation();
        }
        super.setDrawable(tint);
    }

    private void addAnimation() {
        setOrigin(Align.center);
        addAction(
                Actions.sequence(
                        Actions.scaleTo(0.2f, 0.2f, 0.15f),
                        /*Actions.parallel(
                                Actions.rotateBy(720, 0.25f),

                        ),*/
                        Actions.scaleTo(1, 1, 0.15f)
                )
        );
    }

}

