package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.math.MatrixUtils;
import ru.geekbrains.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds;
    private Rect glBounds;
    private Rect worldBounds;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 tourch;

    @Override
    public void show() {

        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        batch.getProjectionMatrix().idt(); /*возвращает матрицу, idt - заполняет единичками*/
        screenBounds = new Rect();
        glBounds = new Rect(0, 0, 0.5f, 0.5f);
        worldBounds = new Rect();
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        tourch = new Vector2();

    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);

    }

    public void resize(Rect worldBounds) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tourch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDown(tourch, pointer, button);
        return false;
    }

    public boolean touchDown(Vector2 tourch, int pointer, int button) {
        System.out.println("tourchDown tourchX = " + tourch.x + " tourchDown tourchy = " + tourch.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        tourch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchUp(tourch, pointer, button);
        return false;
    }
    public boolean touchUp(Vector2 tourch, int pointer, int button) {
        System.out.println("touchDragged tourchX = " + tourch.x + " touchDragged tourchy = " + tourch.y);
        return false;
    }



    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        tourch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDragged(tourch, pointer);
        return false;
    }
    public boolean touchDragged(Vector2 tourch, int pointer) {
        System.out.println("touchDragged tourchX = " + tourch.x + " touchDragged tourchy = " + tourch.y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}