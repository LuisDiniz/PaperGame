package br.cefetmg.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Heroi {
    
    private final static int VELOCIDADE_HEROI_X = 6;
    private final static int VELOCIDADE_HEROI_Y = 5;
    
    private Sprite spriteHeroi;
    private Texture texturaHeroi;
    private Texture spriteSheet;
    
    private Animation animacaoCorrente;
    private Animation animacaoAndarEsquerda;
    private Animation animacaoAndarDireita;
    private Animation animacaoPular;
    private Animation animacaoAbaixar;
    
    private TextureRegion[][] quadrosAnimacao;
    private int x,y;
    float tempoAnimacao;
    
    public Heroi(int x, int y){
        texturaHeroi = new Texture("Heroi.png");
        spriteHeroi = new Sprite(texturaHeroi);
        spriteSheet = new Texture("spritesheet-pulo.png");
        quadrosAnimacao = TextureRegion.split(spriteSheet, 109, 226);      
        
        animacaoAndarEsquerda = new Animation(0.3f, new TextureRegion[] {
          quadrosAnimacao[0][0] // 1ª linha, 1ª coluna
          //quadrosAnimacao[0][1] // idem, 2ª coluna
        });
        animacaoAndarEsquerda.setPlayMode(Animation.PlayMode.LOOP);
        
        animacaoAndarDireita = new Animation(0.3f, new TextureRegion[] {
          quadrosAnimacao[0][0] // 1ª linha, 1ª coluna
          //quadrosAnimacao[0][1] // idem, 2ª coluna
        });
        animacaoAndarDireita.setPlayMode(Animation.PlayMode.LOOP);
        
        animacaoAbaixar = new Animation(0.3f, new TextureRegion[] {
          quadrosAnimacao[0][0] // 1ª linha, 1ª coluna
          //quadrosAnimacao[0][1] // idem, 2ª coluna
        });
        
                // Define as animações
        animacaoPular = new Animation(0.3f, new TextureRegion[] {
          quadrosAnimacao[0][0],
          quadrosAnimacao[0][1]
        });
        
        animacaoCorrente = animacaoAndarEsquerda;
                
        this.x = x;
        this.y = y;
    }
    
    public void andarDireita(){
        animacaoCorrente = animacaoAndarDireita; 
        x = x + VELOCIDADE_HEROI_X;
    }
    
    public void andarEsquerda(){
        animacaoCorrente = animacaoAndarEsquerda;
        x = x - VELOCIDADE_HEROI_X;
    }
    
    public void pular(){
        animacaoCorrente = animacaoPular;
        y = y + VELOCIDADE_HEROI_Y;
    }
    
    public void abaixar(){
        
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void render(SpriteBatch batch) {
        tempoAnimacao = tempoAnimacao + Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animacaoCorrente.getKeyFrame(tempoAnimacao);
        batch.draw(currentFrame,x,y);
    }

    public float getWidth() {
        return spriteHeroi.getWidth();
    }
    
}
