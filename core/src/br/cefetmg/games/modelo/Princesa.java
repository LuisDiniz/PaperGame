/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.games;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Luis Diniz
 */
public class Princesa {
    
    private Texture texturaPrincesa;
    private Texture texturaAtual;
    private Texture texturaFinal;
    private Texture spriteSheetPrincesa;
    private TextureRegion[][] quadrosAnimacao;
    private Sprite spritePrincesa;
    private Animation animacaoFinal;
    
    private int x;
    private int y;
    public final Rectangle hitbox;
    
    public Princesa(int x, int y){
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(x+7, y+18, 98, 197);
        
        spriteSheetPrincesa = new Texture("spritesheetPrincesa.png");
        quadrosAnimacao = TextureRegion.split(spriteSheetPrincesa, 109, 226); 
        
//        animacaoFinal = new Animation(0.3f, new TextureRegion[] {
//          quadrosAnimacao[0][0],
//          quadrosAnimacao[0][1]
//        });
//        animacaoFinal.setPlayMode(Animation.PlayMode.LOOP);

        texturaPrincesa = new Texture("Princesa.png");
        texturaFinal = new Texture("PrincesaFeliz.png");
        spritePrincesa = new Sprite(texturaPrincesa);
        texturaAtual = texturaPrincesa;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texturaAtual, x, y);
    }
    
    public void animacaoFinal(){
        texturaAtual = texturaFinal;
    }

    public float getWidth() {
        return spritePrincesa.getWidth();
    }
    
}
