package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Cell extends Image {
    public static final float CELL_DIM = 5;

    public int number = 0;
    private boolean selected;

    public int row;
    public int column;

    public Cell(TextureRegion region) {
        super(region);
    }

    public void setDrawable(TextureRegion region) {
        super.setDrawable(new TextureRegionDrawable(region));
        //addAnimation(); // play animation when region changed
    }

    public void setNumber(int num, TextureRegion region) {
        number = num;
        super.setDrawable(new TextureRegionDrawable(region));

    }


}
