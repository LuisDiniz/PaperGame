package br.cefetmg.games.modelo.inimigos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Medusa extends BaseInimigo {
    private boolean subindo;
    private float velocidadeY;
    private final static float VELOCIDADE_MAX_Y = 4;
    private final static float VELOCIDADE_X = 1.5f;

    public Medusa (float x, Texture textura) {
        this.x = x;
        this.y = Math.random() > 0.5 ? 300 : 21;
        subindo = y < Gdx.graphics.getHeight()/2;
        velocidadeY = 0;
        this.textura = textura;
        hitbox = new Rectangle(x, y, 68, 72);
    }

    @Override
    public void render(SpriteBatch batch) {
//        update();
        batch.draw(textura, x, y);
    }

    @Override
    public void update() {
        makeAMove();
        hitbox.setPosition(x, y);
    }

    @Override
    public void dispose() {
        //tocar um som
//        //mostrar uma animação
    }

    private void makeAMove () {
        y += velocidadeY;
        x += VELOCIDADE_X;
        if (subindo) {
            if (y <= 200 && velocidadeY < VELOCIDADE_MAX_Y)
                velocidadeY += 1;
            if (y >= 350 && velocidadeY > 0)
                velocidadeY -= 1;
            if (velocidadeY == 0)
                subindo = false;
        }
        //descendo
        else {
            if (y >= 200 && velocidadeY > (-VELOCIDADE_MAX_Y))
                velocidadeY -= 1;
            if (y<=50 && velocidadeY < 0)
                velocidadeY += 1;
            if (velocidadeY == 0)
                subindo = true;
        }

    }
    //Acho que nao e' necessario
//    public void collisionWithPlayer () {
//        //tocar um som
//        //mostrar uma animação
//        this.textura.dispose();
//    }
}
