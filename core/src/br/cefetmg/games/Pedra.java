package br.cefetmg.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Pedra {
    
    private static final int VELOCIDADE_PEDRA = 2;
    
    private final Texture spriteSheetPedra = new Texture("PedraSpritesheet.png");
    
    private final TextureRegion[][] quadrosAnimacao;
    private final Animation animacaoPedra;
    
    private float x;
    private float y;
    private float fimAnimacaoX;
    private boolean visivel;
    float tempoAnimacao;
    
    public Pedra(){
        this.x = 0;
        this.y = 0;
        visivel = false;
        quadrosAnimacao = TextureRegion.split(spriteSheetPedra, 459, 226);
        // Define as animações
        animacaoPedra = new Animation(0.3f, new TextureRegion[] {
          quadrosAnimacao[0][0],
          quadrosAnimacao[0][1],
          quadrosAnimacao[0][2],
          quadrosAnimacao[0][3],
        });
        animacaoPedra.setPlayMode(Animation.PlayMode.LOOP);         
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }  

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }  
    
    public void ativarArmadilha(float x, float y, float larguraCamera){
        this.x = x;//- larguraCamera / 2f;
        this.y = y;
        fimAnimacaoX = 0;//this.x - larguraCamera;
        visivel = true;
    }
    
    private void atualizarPosicaoPedra(){
        if (this.x >= fimAnimacaoX)
            this.x = this.x - VELOCIDADE_PEDRA;
        else
            visivel = false;
    }
    
    public void render(SpriteBatch batch){
        tempoAnimacao = tempoAnimacao + Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animacaoPedra.getKeyFrame(tempoAnimacao);                       
        batch.draw(currentFrame,x,y);
        atualizarPosicaoPedra();
    }
           
}
