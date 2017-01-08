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
    private SpriteBatch batch;
    private OrthographicCamera camera;      
    // Constantes
    private final static int OBJETIVO_POS_X = 500;
    private final static int OBJETIVO_POS_Y = 12;
    private final static int VELOCIDADE_CAMERA_X = 5;
    private final static int POSICAO_INICIAL_HEROI_X = 2900;
    private final static int POSICAO_INICIAL_HEROI_Y = 11; 
    // Texturas
    private Texture mapa;
    private Texture princesa;
    private Texture setas;
    // Modelos
    private Heroi heroi;
    //private Princesa princesa;
    // Tasks
    private Timer.Task moverCamera;
    private Timer.Task mostrarObjetivo;
    // Variáveis de Controle
    private boolean comecoFase = true;
    private boolean aparecerSetas = true;
	
        
    @Override
    public void create () {
        batch = new SpriteBatch();
        // Carrega as texturas 
        mapa = new Texture("Mapa.png");
        heroi = new Heroi(POSICAO_INICIAL_HEROI_X, POSICAO_INICIAL_HEROI_Y);
        //heroi = new Texture("Heroi.png");
        princesa = new Texture("Princesa.png");
        // Inicializa a câmera com o tamanho da tela
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //camera = new OrthographicCamera(1000,500);                      
        //camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.position.set(mapa.getWidth() - (camera.viewportWidth / 2f), camera.viewportHeight / 2f, 0);
        //camera.position.x = heroi.getX() - (camera.viewportWidth / 2f);        
        camera.update();   
    }

    @Override
    public void render () {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        batch.draw(mapa, 0, 0);
        heroi.render(batch);
        batch.draw(princesa, 0, 0);
        
        // Desenha a camera 
        batch.setProjectionMatrix(camera.combined); 
        
        update();
        // Realiza a animação inicial do jogo apenas se estiver no começo da fase
//        if(comecoFase)
//            animacaoInicial();       
        
        batch.end();
    }
	
    @Override
    public void dispose () {
        batch.dispose();
        //img.dispose();
    }
    
    public void update(){
        
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (heroi.getX() + heroi.getWidth() < mapa.getWidth())
                heroi.andarDireita();
            if (camera.position.x <= (mapa.getWidth() - camera.viewportWidth)) 
                camera.position.x = camera.position.x + VELOCIDADE_CAMERA_X;
        }        
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (heroi.getX() > 0)
                heroi.andarEsquerda();
            if (camera.position.x > camera.viewportWidth/2f)  
                camera.position.x = camera.position.x - VELOCIDADE_CAMERA_X;
        }    
        
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            heroi.pular();
        }     
        
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            // chama método para andar com o héroi
        } 
        
        camera.update();
        
    }
        
    public void moverCamera(final int destinoX, final int destinoY){
        
        moverCamera = new Timer.Task(){ 
                        @Override
                         public void run() {
                             camera.position.x = camera.position.x + VELOCIDADE_CAMERA_X;
                         }
        };        
        // Arrumar cálculo da câmera
        Timer.schedule(moverCamera, 3f, 0.05f, (int) Math.floor((destinoX - (camera.viewportWidth/2) - camera.position.x + 109)/VELOCIDADE_CAMERA_X - 2));
    }

    private void mostrarObjetivo(final int objetivoX, final int objetivoY) {
        //Posiciona a cameera no objetivo;
        camera.position.x = heroi.getX();
        
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
        moverCamera(heroi.getX(), 0);
        comecoFase = false;
    }
        
}
