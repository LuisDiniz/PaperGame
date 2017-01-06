package br.cefetmg.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class Game extends ApplicationAdapter {
    
    private final static int OBJETIVO_POS_X = 500;
    private final static int OBJETIVO_POS_Y = 12;
    private final static int VELOCIDADE_CAMERA_X = 5;
    private final static int VELOCIDADE_HEROI_X = 1;
    private final static int VELOCIDADE_HEROI_Y = 1;

    private SpriteBatch batch;
    
//    private Princesa princesa;
//    private Heroi heroi;
    
    //private Pedra pedra;
    private Texture mapa;
    private Texture heroi;
    private Texture princesa;
    private Texture spriteSheet;
    private Texture texturaPlayer;
    private Texture texturaPrincesa;
    private Texture setas;
    private OrthographicCamera camera;    
    private boolean comecoFase = true;
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
        // Inicializa a câmera com o tamnho da tela
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //camera = new OrthographicCamera(1000,500);                      
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();   
    }

    @Override
    public void render () {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        batch.draw(mapa, 0, 0);        
        batch.draw(heroi, 2900, 0);
        batch.draw(princesa, 0, 0);
        
        // Desenha a camera 
        batch.setProjectionMatrix(camera.combined); 
        
        update();
        // Realiza a animação inicial do jogo apenas se estiver no começo da fase
        if(comecoFase)
            animacaoInicial();       
        
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
        
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {;
//            camera.position.x = camera.position.x - 5;
//        }        
        camera.update();
        System.out.println("Camera.x:"+camera.position.x);
    }
        
    public void moverCamera(final int destinoX, final int destinoY){
        
        moverCamera = new Timer.Task(){ 
                        @Override
                         public void run() {
                             camera.position.x = camera.position.x + VELOCIDADE_CAMERA_X;
                         }
        };        
        // ??????
        Timer.schedule(moverCamera, 3f, 0.05f, (int) Math.floor((destinoX - (camera.viewportWidth/2) - camera.position.x + 109)/VELOCIDADE_CAMERA_X - 2));// ??? ???
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

    private void animacaoInicial() {
        //mostrarObjetivo(OBJETIVO_POS_X, OBJETIVO_POS_Y);
        // Mudar animação para mover a camera para um x,y específico ??
        moverCamera(2900, 0);
        System.out.println("Camera.x:"+camera.position.x);
        System.out.println("Largura Camera:"+camera.viewportWidth/2f);
        comecoFase = false;
    }
        
}
