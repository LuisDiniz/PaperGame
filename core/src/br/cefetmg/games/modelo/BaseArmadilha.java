package br.cefetmg.games.modelo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class BaseArmadilha {
    
    protected Texture spriteSheet;
    protected TextureRegion[][] quadrosAnimacao;
    protected Animation animacao;
    
    protected int x;
    protected int y;
    protected float tempoAnimacao;
    protected boolean visivel;
    protected boolean ativa;

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
    
    public void ativarArmadilha(){
        this.visivel = true;
    }
    
    public void render(SpriteBatch batch, boolean debug){
        this.tempoAnimacao = this.tempoAnimacao + Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) this.animacao.getKeyFrame(this.tempoAnimacao);                       
        batch.draw(currentFrame, this.x, this.y);
    }    
    
}
