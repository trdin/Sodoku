package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TintableRegionDrawable extends TextureRegionDrawable {
    private static final Color tmpColor = new Color();

    private final Color tintColor = new Color(Color.RED);

    public TintableRegionDrawable (TextureRegion region) {
        super.setRegion(region);
    }

    public void setTint(Color tint) {
        this.tintColor.set(tint);
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        setupTintColor(batch);
        super.draw(batch, x, y, width, height);
    }

    @Override
    public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        setupTintColor(batch);
        super.draw(batch, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }

    private void setupTintColor(Batch batch) {
        Color color = tmpColor.set(batch.getColor()).mul(tintColor);
        batch.setColor(tintColor);
    }
}
