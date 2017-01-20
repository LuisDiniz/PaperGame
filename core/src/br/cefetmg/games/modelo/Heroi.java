package br.cefetmg.games.modelo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Heroi {
    
    private final static int VELOCIDADE_HEROI_X = 5;
    private final static int VELOCIDADE_HEROI_Y = 20;
    private final int POSICAO_INICIAL_HEROI_Y;
    private int HP;
    private boolean esquerda;
    
    private Sprite spriteHeroi;
    private final Texture texturaHeroi;
    private Texture spriteSheetPularEsquerda;
    private Texture spriteSheetPularDireita;    
    private Texture spriteSheetAbaixar;
    private Texture spriteSheetEsmagado;    
    private Texture texturaHitbox;
    private Texture spriteSheetSocar;
    
    private Animation animacaoCorrente;
    private final Animation animacaoAndarEsquerda;
    private final Animation animacaoAndarDireita;
    private final Animation animacaoPularEsquerda;
    private final Animation animacaoPularDireita;
    private final Animation animacaoAbaixar;
    private final Animation animacaoEsmagado;    
    private final Animation<TextureRegion> animacaoSocar;
    
    private TextureRegion[][] quadrosAnimacaoPularEsquerda;
    private TextureRegion[][] quadrosAnimacaoPularDireita;
    private TextureRegion[][] quadrosAnimacaoAbaixar;
    private TextureRegion[][] quadrosAnimacaoEsmagado;    
    private TextureRegion[][] quadrosAnimacaoSocar;

    private int x,y, velocidadeY;
    private float tempoAnimacao;
    public Rectangle hitbox;
    
    public Heroi(int x, int y){
        // Guarda a posição inicial do heroi                 
        this.x = x;
        this.y = y;
        POSICAO_INICIAL_HEROI_Y = y;
        HP = 3;
        esquerda = true;
        hitbox = new Rectangle(x+27, y+10, 56, 175);
        // Carrega as texturas e animações
        texturaHeroi = new Texture("Heroi.png");
        texturaHitbox = new Texture ("TexturaVermelha.png");
        spriteHeroi = new Sprite(texturaHeroi);
        spriteSheetPularEsquerda = new Texture("spritesheet-pular.png");
        spriteSheetPularDireita = new Texture("spritesheet-pular-dir.png");
        spriteSheetAbaixar = new Texture("spritesheet-abaixar.png");
        spriteSheetEsmagado = new Texture("spritesheetEsmagado.png");
        quadrosAnimacaoPularEsquerda = TextureRegion.split(spriteSheetPularEsquerda, 109, 226);      
        quadrosAnimacaoPularDireita = TextureRegion.split(spriteSheetPularDireita, 109, 226);
        quadrosAnimacaoEsmagado = TextureRegion.split(spriteSheetEsmagado, 109, 226);                      
        spriteSheetSocar = new Texture(Gdx.files.internal("spritesheet-soco.png"));   
        quadrosAnimacaoAbaixar = TextureRegion.split(spriteSheetAbaixar, 109, 226);
        quadrosAnimacaoSocar = TextureRegion.split(spriteSheetSocar,spriteSheetSocar.getWidth()/2, spriteSheetSocar.getHeight()/1);
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
       
        animacaoPularEsquerda = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPularEsquerda[0][0],
            quadrosAnimacaoPularEsquerda[0][1]
        });
        animacaoSocar = new Animation<TextureRegion>(0.3f, new TextureRegion[]{
            quadrosAnimacaoSocar[0][0],
            quadrosAnimacaoSocar[0][1],
        });
        
        animacaoPularDireita = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPularDireita[0][1],            
            quadrosAnimacaoPularDireita[0][0]
        });
        
        animacaoEsmagado = new Animation(0.3f, new TextureRegion[] {
            quadrosAnimacaoPularEsquerda[0][0],
            quadrosAnimacaoEsmagado[0][1],            
            quadrosAnimacaoEsmagado[0][0],
            quadrosAnimacaoEsmagado[0][1],
            quadrosAnimacaoPularEsquerda[0][0]            
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
        if (y == POSICAO_INICIAL_HEROI_Y)
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
        if (y == POSICAO_INICIAL_HEROI_Y)
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
        animacaoCorrente = animacaoAbaixar;
        hitbox.height = 120;
    }

    public void parado(){
        // ?? CRIAR ANIMACAO PARADO OU SO DESENHAR A SPRITE NORMAL NESSE CASO ??;
        // ?? COMO A GENTE CONTROLARIA O DESENHO DA SPIRTE/ANIMACAO ??        
        if (esquerda){
            if (y == POSICAO_INICIAL_HEROI_Y)
                animacaoCorrente = animacaoAndarEsquerda;
            else
                animacaoCorrente = animacaoPularEsquerda;
        }
        else{
            if (y == POSICAO_INICIAL_HEROI_Y)
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
        if (debug)
            batch.draw(texturaHitbox, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        tempoAnimacao = tempoAnimacao + Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animacaoCorrente.getKeyFrame(tempoAnimacao);
        batch.draw(currentFrame,x,y);
    }

    public float getWidth() {
        TextureRegion currentFrame = (TextureRegion) animacaoCorrente.getKeyFrame(0);        
        return currentFrame.getRegionWidth();
    }
    
    public int getHP(){
        return HP;
    }
    
    public void perdeuVida(BaseArmadilha armadilha){
        if (armadilha instanceof Pedra)
            animacaoCorrente = animacaoEsmagado;
        HP = HP - 1;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
            
    public void socar () {
        animacaoCorrente = animacaoSocar;
    }
}
