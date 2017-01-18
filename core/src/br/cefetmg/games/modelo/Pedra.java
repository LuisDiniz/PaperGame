package br.cefetmg.games.modelo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Pedra extends BaseArmadilha{
    
    private static final int VELOCIDADE_PEDRA = 5;    
    private int direcao;
    
    public Pedra(int PosX, int PosY, boolean direita){
        x = PosX;
        y = PosY;
        visivel = false;
        ativa = true;
        if (direita)
           this.direcao = 1;
        else 
           this.direcao = -1;
        spriteSheet = new Texture("PedraSpritesheet3.png");
        quadrosAnimacao = TextureRegion.split(spriteSheet, 235, 240);
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
    }
    
    @Override
    public void render(SpriteBatch batch){;
        tempoAnimacao = tempoAnimacao + Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animacao.getKeyFrame(tempoAnimacao);                       
        batch.draw(currentFrame,x,y);
        atualizarPosicaoPedra();
    }
           
}
