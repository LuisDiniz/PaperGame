package br.cefetmg.games.modelo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Pedra extends BaseArmadilha{
    
    private static final int VELOCIDADE_PEDRA = 5;    
    private int direcao;
    
    public Pedra(int PosX, int PosY, boolean esquerda){
        x = PosX;
        y = PosY;
        visivel = false;
        ativa = true;
        colidiu = false;
        if (esquerda)
           this.direcao = -1;
        else 
           this.direcao = 1;
        hitbox = new Rectangle(x, y, 175, 175);
        spriteSheet = new Texture("spritesheetPedra3.png");
        texturaHitbox = new Texture ("TexturaVermelha.png");
        quadrosAnimacao = TextureRegion.split(spriteSheet, 173, 175);
        // Define as animações
        animacao = new Animation(0.3f, new TextureRegion[] {
          quadrosAnimacao[0][0],
          quadrosAnimacao[0][1],
          quadrosAnimacao[0][2],
          quadrosAnimacao[0][3],
        });
        animacao.setPlayMode(Animation.PlayMode.LOOP);         
    } 
    
    private void atualizarPosicaoPedra(){
        x = x - (this.VELOCIDADE_PEDRA * direcao);
        hitbox.x = hitbox.x - (this.VELOCIDADE_PEDRA * direcao);
    }
    
    @Override
    public void render(SpriteBatch batch, boolean debug){
        //Desenha o retangulo da Hitbox no heroi
        if (debug)
            batch.draw(texturaHitbox, hitbox.x, hitbox.y, hitbox.width, hitbox.height);                 
        tempoAnimacao = tempoAnimacao + Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animacao.getKeyFrame(tempoAnimacao);                       
        batch.draw(currentFrame,x,y);
        atualizarPosicaoPedra();
    }
               
}
