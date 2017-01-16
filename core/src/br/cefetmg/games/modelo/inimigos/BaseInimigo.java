package br.cefetmg.games.modelo.inimigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class BaseInimigo {
    protected float x,y;
    protected Rectangle hitbox;
    protected Texture textura;
    public Sprite sprite;

    public abstract void update();
    public abstract void render(SpriteBatch batch);
}
