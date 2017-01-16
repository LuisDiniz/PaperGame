package br.cefetmg.games.modelo.inimigos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Medusa extends BaseInimigo {
    private boolean subindo = y < Gdx.graphics.getHeight()/2;

    public Medusa (float x, Texture textura) {
        this.x = x;
        this.y = Math.random() > 0.5 ? 300 : 21;
        this.textura = textura;
        hitbox = new Rectangle(x, y, 68, 72);
    }

    @Override
    public void render(SpriteBatch batch) {
        update();
        batch.draw(textura, x, y);
    }

    @Override
    public void update() {

    }

    private void makeAMove () {
        if (subindo) {

        }

    }
}
