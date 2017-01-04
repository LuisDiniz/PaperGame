package br.cefetmg.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class Game extends ApplicationAdapter {
    
    private final static int OBJETIVO_POS_X = 500;
    private final static int OBJETIVO_POS_Y = 12;

    private SpriteBatch batch;
    //private Goomba goomba, princesa;
    //private Pedra pedra;
    private Texture mapa;
    private Texture heroi;
    private Texture princesa;
    private Texture spriteSheet;
    private Texture texturaPlayer;
    private Texture texturaPrincesa;
    private Texture setas;
    private OrthographicCamera camera;    
    private boolean comecoJogo = true;
    private boolean aparecerSetas = true;
    private Timer.Task moverCamera;
    private Timer.Task mostrarObjetivo;
	
        
    @Override
    public void create () {
        batch = new SpriteBatch();
        // Carrega as texturas 
        mapa = new Texture("Mapa.png");        
        heroi = new Texture("Heroi.png");
        princesa = new Texture("Princesa.png");        
    }

    @Override
    public void render () {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        batch.draw(mapa, 0, 0);        
        batch.draw(heroi, 900, 0);
        batch.draw(princesa, 0, 65);
        
        batch.end();
    }
	
    @Override
    public void dispose () {
        batch.dispose();
        //img.dispose();
    }
    
    public void update(){
        // Animação para mover a camera do objetivo para o a posição do jogador
        // Verifica se é o começo do jogo
//        if (comecoJogo) {
//            mostrarObjetivo(princesa.x, princesa.y);
//            moverCameraObjetivoJogador(princesa.x, princesa.y);
//        }        
        
    }
        
    public void moverCameraObjetivoJogador(final int objetivoX, final int objetivoY){
        
        moverCamera = new Timer.Task(){ 
                        @Override
                         public void run() {
                             camera.position.x = camera.position.x - 5;
                         }
        };
        
        Timer.schedule(moverCamera, 3f, 0.05f, (int) (objetivoX-(camera.viewportWidth/2))/5 - 1);
    }

    private void mostrarObjetivo(final int objetivoX, final int objetivoY) {
        //Posiciona a cameera no objetivo;
        camera.position.x = objetivoX;
        
        // Inicializa a task mostrarObjetivo
        mostrarObjetivo = new Timer.Task(){
                            @Override
                            public void run() {
                                aparecerSetas = !aparecerSetas;
                            }
        };        
        // Realiza o efeito das setas "piscando" na tela
        Timer.schedule(mostrarObjetivo, 0, 0.5f, 6);      
    }        
        
}
