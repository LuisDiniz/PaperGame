package br.cefetmg.games.modelo;

import br.cefetmg.games.modelo.inimigos.BaseInimigo;
import br.cefetmg.games.utils.Collision;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Iterator;

public class Heroi {
    
    private final static int VELOCIDADE_HEROI_X = 5;
    private final static int VELOCIDADE_HEROI_Y = 20;
    private final int POSICAO_INICIAL_HEROI_Y;
    private int x;
    private int y;
    private int velocidadeY;
    public Rectangle hitbox;
    private int HP;    
    
    private Sprite spriteHeroi;
    private final Texture texturaHeroi;
    private Texture spriteSheetPularEsquerda;
    private Texture spriteSheetPularDireita;    
    private Texture spriteSheetAbaixar;
    private Texture spriteSheetAbaixarDireita;    
    private Texture texturaHitbox;
    private Texture spriteSheetSocar;
    private Texture spriteSheetSocarDireita;
    private Texture socoMagico;
    private Texture socoMagicoDireita;
    
    private Animation animacaoCorrente;
    private final Animation animacaoAndarEsquerda;
    private final Animation animacaoAndarDireita;
    private final Animation animacaoPularEsquerda;
    private final Animation animacaoPularDireita;
    private final Animation animacaoAbaixar;
    private final Animation animacaoAbaixarDireita;
    private final Animation<TextureRegion> animacaoSocar;
    private final Animation<TextureRegion> animacaoSocarDireita;
    
    private TextureRegion[][] quadrosAnimacaoPularEsquerda;
    private TextureRegion[][] quadrosAnimacaoPularDireita;
    private TextureRegion[][] quadrosAnimacaoAbaixar;
    private TextureRegion[][] quadrosAnimacaoAbaixarDireita;
    private TextureRegion[][] quadrosAnimacaoSocar;
    private TextureRegion[][] quadrosAnimacaoSocarDireita;
    
    private float tempoAnimacao;
    private boolean esquerda;
    private boolean visivel;
    private Timer.Task perdeuVidaTask;
    
    public Heroi(int x, int y){
        // Guarda a posição inicial do heroi                 
        this.x = x;
        this.y = y;
        POSICAO_INICIAL_HEROI_Y = y;
        HP = 3;
        esquerda = true;
        visivel = true;
        hitbox = new Rectangle(x+27, y+10, 56, 175);
        // Carrega as texturas e animações
        texturaHeroi = new Texture("Heroi.png");
        texturaHitbox = new Texture ("TexturaVermelha.png");
        spriteHeroi = new Sprite(texturaHeroi);
        spriteSheetPularEsquerda = new Texture("spritesheet-pular.png");
        spriteSheetPularDireita = new Texture("spritesheet-pular-dir.png");
        spriteSheetAbaixar = new Texture("spritesheet-abaixar.png");
        spriteSheetAbaixarDireita = new Texture("spritesheet-abaixar-dir.png");
        spriteSheetSocar = new Texture("Soco.png");
        spriteSheetSocarDireita = new Texture("Soco-dir.png");
        socoMagico = new Texture (Gdx.files.internal("SocoMagico.png"));
        socoMagicoDireita = new Texture (Gdx.files.internal("SocoMagico-dir.png"));
        quadrosAnimacaoPularEsquerda = TextureRegion.split(spriteSheetPularEsquerda, 109, 226);
        quadrosAnimacaoPularDireita = TextureRegion.split(spriteSheetPularDireita, 109, 226);
        quadrosAnimacaoAbaixar = TextureRegion.split(spriteSheetAbaixar, 109, 226);
        quadrosAnimacaoAbaixarDireita = TextureRegion.split(spriteSheetAbaixarDireita, 109, 226);
        quadrosAnimacaoSocar = TextureRegion.split(spriteSheetSocar,spriteSheetSocar.getWidth(), spriteSheetSocar.getHeight());
        quadrosAnimacaoSocarDireita = TextureRegion.split(spriteSheetSocarDireita,spriteSheetSocarDireita.getWidth(), spriteSheetSocarDireita.getHeight());
        // Define as animações
        animacaoAndarEsquerda = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPularEsquerda[0][0]
        });
        animacaoAndarEsquerda.setPlayMode(Animation.PlayMode.LOOP);
        
        animacaoAndarDireita = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPularDireita[0][1]
        });
        animacaoAndarDireita.setPlayMode(Animation.PlayMode.LOOP);
        
        animacaoAbaixar = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoAbaixar[0][0],
            quadrosAnimacaoAbaixar[0][1]            
        });
        
        animacaoAbaixarDireita = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoAbaixarDireita[0][1],
            quadrosAnimacaoAbaixarDireita[0][0]            
        });
       
        animacaoPularEsquerda = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPularEsquerda[0][0],
            quadrosAnimacaoPularEsquerda[0][1]
        });

        animacaoSocar = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoSocar[0][0]
        });
        
        animacaoSocarDireita = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoSocarDireita[0][0]
        });
        
        animacaoPularDireita = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPularDireita[0][1],            
            quadrosAnimacaoPularDireita[0][0]
        });        
        
        animacaoCorrente = animacaoAndarEsquerda;
    }
    
    public void update (){
        hitbox.x = x+27;
        hitbox.y = y+10;
        y = y + velocidadeY;
        if (velocidadeY < 0 && hitbox.y <= Chao.getFloorHeightBelowCharacter(hitbox.x, hitbox.width)){
            velocidadeY = 0;
            hitbox.y = Chao.getFloorHeightBelowCharacter(hitbox.x, hitbox.width);
            y = Chao.getFloorHeightBelowCharacter(hitbox.x, hitbox.width)-10;
        }
        else if (hitbox.y > Chao.getFloorHeightBelowCharacter(hitbox.x, hitbox.width)){
            velocidadeY = velocidadeY - 1;
        }
    }
    
    public boolean andarDireita(){
        if (hitbox.y == Chao.getFloorHeightBelowCharacter(hitbox.x, hitbox.width))
            animacaoCorrente = animacaoAndarDireita;
        else
            animacaoCorrente = animacaoPularDireita;        
        esquerda = false;
        float deltaAltura = Chao.getFloorHeight(hitbox.x+hitbox.width+5) - hitbox.y;
        if ( deltaAltura <= 0) {
            x = x + VELOCIDADE_HEROI_X;
            return true;
        }
        return false;
    }
    
    public boolean andarEsquerda(){
        if (hitbox.y == Chao.getFloorHeightBelowCharacter(hitbox.x, hitbox.width))
            animacaoCorrente = animacaoAndarEsquerda;
        else
            animacaoCorrente = animacaoPularEsquerda;
        esquerda = true;
        float deltaAltura = Chao.getFloorHeight(hitbox.x-5) - hitbox.y;
        if ( deltaAltura <= 0) {
            x = x - VELOCIDADE_HEROI_X;
            return true;
        }
        return false;
    }
    
    public void pular(){
        // Verifica qual o sentido o personagem está andando para ajustar a animação
        if (esquerda)
            animacaoCorrente = animacaoPularEsquerda;
        else
            animacaoCorrente = animacaoPularDireita;
        if (hitbox.y == Chao.getFloorHeightBelowCharacter(hitbox.x, hitbox.width))
            velocidadeY = VELOCIDADE_HEROI_Y;
    }
    
    public void abaixar(){
        if (esquerda)
            animacaoCorrente = animacaoAbaixar;
        else
            animacaoCorrente = animacaoAbaixarDireita;        
        
        hitbox.height = 120;
    }

    public void parado(){
        // ?? CRIAR ANIMACAO PARADO OU SO DESENHAR A SPRITE NORMAL NESSE CASO ??;
        // ?? COMO A GENTE CONTROLARIA O DESENHO DA SPIRTE/ANIMACAO ??        
        if (esquerda){
            if (hitbox.y == Chao.getFloorHeightBelowCharacter(hitbox.x, hitbox.width))
                animacaoCorrente = animacaoAndarEsquerda;
            else
                animacaoCorrente = animacaoPularEsquerda;
        }
        else{
            if (hitbox.y == Chao.getFloorHeightBelowCharacter(hitbox.x, hitbox.width))
                animacaoCorrente = animacaoAndarDireita;
            else
                animacaoCorrente = animacaoPularDireita;            
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void render(SpriteBatch batch, boolean debug) {
        //Desenha o retangulo da Hitbox no heroi
        if(debug)
            batch.draw(texturaHitbox, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        tempoAnimacao = tempoAnimacao + Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animacaoCorrente.getKeyFrame(tempoAnimacao);
        if(visivel)
            batch.draw(currentFrame,x,y);
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            if (esquerda)
                batch.draw(socoMagico, x - 150, y);
            else
                batch.draw(socoMagicoDireita, x, y);
        }
    }

    public float getWidth() {
        TextureRegion currentFrame = (TextureRegion) animacaoCorrente.getKeyFrame(0);        
        return currentFrame.getRegionWidth();
    }
    
    public int getHP(){
        return HP;
    }

    public void perdeuVida(){
        HP = HP - 1;
        animacaoPerdeuVida();
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void socar (Heroi heroi, ArrayList<BaseInimigo> inimigos) {
        Rectangle alcanceDoSoco;
        if (esquerda) {
            animacaoCorrente = animacaoSocar;
            alcanceDoSoco = new Rectangle(x-150, y, 150, 175);
        }
        else {
            animacaoCorrente = animacaoSocarDireita;
            alcanceDoSoco = new Rectangle(x+109, y, 150, 175);
        }

        for(Iterator<BaseInimigo> i = inimigos.iterator(); i.hasNext(); ) {
            BaseInimigo temp = i.next();
            if (Collision.rectsOverlap(alcanceDoSoco, temp.hitbox))
            {
                temp.dispose();
                i.remove();
            }
        }
    }
    
    public void animacaoPerdeuVida(){
        // Inicializa a task mostrarObjetivo
        perdeuVidaTask = new Timer.Task(){
                            @Override
                            public void run() {
                                visivel = !visivel;
                            }
        };        
        Timer.schedule(perdeuVidaTask, 0, 0.3f, 5);  
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
