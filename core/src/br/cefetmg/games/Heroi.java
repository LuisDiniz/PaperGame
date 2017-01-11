package br.cefetmg.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Heroi {
    
    private final static int VELOCIDADE_HEROI_X = 6;
    private final static int VELOCIDADE_HEROI_Y = 5;
    
    private Sprite spriteHeroi;
    private final Texture texturaHeroi;
    private Texture spriteSheetPular;
    private Texture spriteSheetAbaixar;
    //private Texture texturaHitbox;
    
    private Animation animacaoCorrente;
    private final Animation animacaoAndarEsquerda;
    private final Animation animacaoAndarDireita;
    private final Animation animacaoPular;
    private final Animation animacaoAbaixar;
    
    private TextureRegion[][] quadrosAnimacaoPular;
    private TextureRegion[][] quadrosAnimacaoAbaixar;
    private int x,y;
    float tempoAnimacao;
    public final Rectangle hitbox;
    
    public Heroi(int x, int y){
        // Guarda a posição inicial do heroi                 
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(x+27, y+7, 56, 176);
        // Carrega as texturas e animações
        texturaHeroi = new Texture("Heroi.png");
        //texturaHitbox = new Texture ("TexturaVermelha.png");
        spriteHeroi = new Sprite(texturaHeroi);
        spriteSheetPular = new Texture("spritesheet-pular.png");
        spriteSheetAbaixar = new Texture("spritesheet-abaixar.png");
        quadrosAnimacaoPular = TextureRegion.split(spriteSheetPular, 109, 226);      
        quadrosAnimacaoAbaixar = TextureRegion.split(spriteSheetAbaixar, 109, 226);
        // Define as animações
        animacaoAndarEsquerda = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPular[0][0]
        });
        animacaoAndarEsquerda.setPlayMode(Animation.PlayMode.LOOP);
        
        animacaoAndarDireita = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPular[0][0]
        });
        animacaoAndarDireita.setPlayMode(Animation.PlayMode.LOOP);
        
        animacaoAbaixar = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoAbaixar[0][0],
            quadrosAnimacaoAbaixar[0][1]            
        });
       
        animacaoPular = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPular[0][0],
            quadrosAnimacaoPular[0][1]
        });
        
        animacaoCorrente = animacaoAndarEsquerda;
    }
    
    public void andarDireita(){
        animacaoCorrente = animacaoAndarDireita; 
        x = x + VELOCIDADE_HEROI_X;
        hitbox.x += VELOCIDADE_HEROI_X;
    }
    
    public void andarEsquerda(){
        animacaoCorrente = animacaoAndarEsquerda;
        x = x - VELOCIDADE_HEROI_X;
        hitbox.x -= VELOCIDADE_HEROI_X;
    }
    
    public void pular(){
        animacaoCorrente = animacaoPular;
        y = y + VELOCIDADE_HEROI_Y;
        hitbox.y += VELOCIDADE_HEROI_X;
    }
    
    public void abaixar(){
        animacaoCorrente = animacaoAbaixar;
    }
    
    public void parado(){
        // ?? CRIAR ANIMACAO PARADO OU SO DESENHAR A SPRITE NORMAL NESSE CASO ??
        // ?? COMO A GENTE CONTROLARIA O DESENHO DA SPIRTE/ANIMACAO ??
        animacaoCorrente = animacaoAndarEsquerda;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void render(SpriteBatch batch) {
        //batch.draw(texturaHitbox, hitbox.x, hitbox.y, hitbox.width, hitbox.height); Desenha o retangulo da Hitbox no heroi
        
        tempoAnimacao = tempoAnimacao + Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animacaoCorrente.getKeyFrame(tempoAnimacao);
        batch.draw(currentFrame,x,y);
    }

    public float getWidth() {
        TextureRegion currentFrame = (TextureRegion) animacaoCorrente.getKeyFrame(0);        
        return currentFrame.getRegionWidth();
    }
    
}
