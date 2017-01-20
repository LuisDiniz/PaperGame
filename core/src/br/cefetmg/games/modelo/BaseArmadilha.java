package br.cefetmg.games.modelo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class BaseArmadilha {
    
    protected Texture spriteSheet;
    protected Texture texturaHitbox;
    protected TextureRegion[][] quadrosAnimacao;
    protected Animation animacao;
            
    protected int x;
    protected int y;
    protected Rectangle hitbox;
    protected float tempoAnimacao;
    protected boolean visivel;
    protected boolean ativa;
    public boolean colidiu;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    
    public boolean getColidiu() {
        return colidiu;
    }

    public void setColidiu(boolean colidiu) {
        this.colidiu = colidiu;
    }    
    
    public void ativarArmadilha(){
        this.visivel = true;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }    
    
    public abstract void render(SpriteBatch batch, boolean debug);  
    
}
