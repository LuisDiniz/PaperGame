package br.cefetmg.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Pedra {
    
    private static final int VELOCIDADE_PEDRA = 2;
    
    private final Texture TEXTURA_PEDRA = new Texture("Pedra.png");
    private final Texture TEXTURA_PEDRA_SPIRTESHEET = new Texture("PedraSpritesheet.png");
    
    public TextureRegion[][] quadrosAnimacao;
    private Sprite spritePedra;
    public Animation animacaoPedra;
    
    private float x;
    private float y;
    private float fimAnimacaoX;
    private boolean visivel;
    float tempoAnimacao;
    
    public Pedra(){
        this.x = 0;
        this.y = 0;
        visivel = false;
        spritePedra = new Sprite(TEXTURA_PEDRA);
        quadrosAnimacao = TextureRegion.split(TEXTURA_PEDRA_SPIRTESHEET, 459, 226);
        // Define as animações
        animacaoPedra = new Animation(0.3f, new TextureRegion[] {
          quadrosAnimacao[0][0], // 1ª linha, 1ª coluna
          quadrosAnimacao[0][1], // idem, 2ª coluna
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
    
    public void atualizarPosicaoPedra(){
        if (this.x >= fimAnimacaoX)
            this.x = this.x - VELOCIDADE_PEDRA;
        else
            visivel = false;
    }
    
    public void render(SpriteBatch batch){
        tempoAnimacao = tempoAnimacao + Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animacaoPedra.getKeyFrame(tempoAnimacao);
        batch.draw(currentFrame,x,y);// O Sprite "se desenha"
        atualizarPosicaoPedra();
    }
           
}
