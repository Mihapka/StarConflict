package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {

    protected final List<T> activeObjects = new ArrayList<T>();

    protected final List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    /*создаем объект*/
    public  T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            /*добавляем объект из массива запасных объектов в массив активных
             * и одновременно удаляем его из запасных*/
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        System.out.println(getClass().getName()
                + "activ/free: " + activeObjects.size()
                + "/" + freeObjects.size());
        return object;
    }

    public void updateActiveSprites(float dalta) {

        for (T item : activeObjects) {
            if (!item.isDestroyed()) {
                item.update(dalta);
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {

        for (T item : activeObjects) {
            if (!item.isDestroyed()) {
                item.draw(batch);
            }
        }
    }

    public void freeAllDestroyedActiveSprites() {

        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if (sprite.isDestroyed()) {
                free(sprite);
                i--;
                sprite.flushDestroy();
            }

        }
    }

    public void freeAllActiveSprites() {

        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

    private void free(T object) {

        if (activeObjects.remove(object)) {
            freeObjects.add(object);
            System.out.println(getClass().getName()
                    + "activ/free: " + activeObjects.size()
                    + "/" + freeObjects.size());
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose() {

        activeObjects.clear();
        freeObjects.clear();
    }
}

