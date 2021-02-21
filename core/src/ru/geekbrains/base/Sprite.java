package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.math.Rect;

public class Sprite extends Rect {

    protected float angle;
    protected float scale =2f;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite(TextureRegion region) {
        if (region==null){
            throw new RuntimeException("Region is NULL");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void setHightProportions(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle);
    }

    public void update(float delta) {

    }

    public void resize(Rect worldBounds) {

    }

    public boolean touchUp(Vector2 tourch, int pointer, int button) {
        System.out.println("tourchUp tourchX = " + tourch.x + " tourchUp tourchy = " + tourch.y);
        return false;
    }

    public boolean touchDown(Vector2 tourch, int pointer, int button) {
        System.out.println("tourchUp tourchX = " + tourch.x + " tourchUp tourchy = " + tourch.y);
        return false;
    }

    public boolean touchDragged(Vector2 tourch, int pointer, int button) {
        System.out.println("tourchUp tourchX = " + tourch.x + " tourchUp tourchy = " + tourch.y);
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
