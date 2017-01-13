package br.cefetmg.games;

import br.cefetmg.games.modelo.Princesa;
import br.cefetmg.games.modelo.Heroi;
import br.cefetmg.games.modelo.Pedra;
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
    private final static int OBJETIVO_POS_X = 100;
    private final static int OBJETIVO_POS_Y = 0;
    private final static int VELOCIDADE_CAMERA_X = 5;
    private final static int POSICAO_INICIAL_HEROI_X = 2900;
    private final static int POSICAO_INICIAL_HEROI_Y = 11;
    private final static int POSICAO_SETA_45_GRAUS_X = 75;
    private final static int POSICAO_SETA_90_GRAUS_X = 155;
    private final static int POSICAO_SETA_135_GRAUS_X = 225;
    private final static int POSICAO_SETAS_Y = 225;
    private final static int POSICAO_LABEL_PRINCESA_X = 100;
    private final static int POSICAO_LABEL_PRINCESA_Y = 300;
    // Texturas
    private Texture mapa;
    private Texture labelPrincesa;
    private Texture seta45Graus;
    private Texture seta90Graus;
    private Texture seta135Graus;
    
    
    private Texture caixa;
            
            
    // Modelos
    private Heroi heroi;
    private Princesa princesa;
    private Pedra pedra;
    // Tasks
    private Timer.Task moverCamera;
    private Timer.Task mostrarObjetivo;
    // Variáveis de Controle
    private boolean comecoFase;
    private boolean animacaoMostrarObjetivo;
    private boolean fimAnimacaoInicial;	
    private int contador;
    private int numeroRepeticoes;
        
    @Override
    public void create () {
        batch = new SpriteBatch();
        // Inicializa variáveis de controle;
        animacaoMostrarObjetivo = true;
        comecoFase = true;
        fimAnimacaoInicial = false;
        
        //fimAnimacaoInicial = true; // DEBUG
        
        // Carrega as texturas 
        mapa = new Texture("Mapa.png");
        labelPrincesa = new Texture("LabelPrincesa.png");
        caixa = new Texture("TexturaPreta.png");
        seta45Graus = new Texture("Seta45Graus.png");
        seta90Graus = new Texture("Seta90Graus.png");
        seta135Graus = new Texture("Seta135Graus.png");
        // Inicializa os objetos modelos
        heroi = new Heroi(POSICAO_INICIAL_HEROI_X, POSICAO_INICIAL_HEROI_Y);
        princesa = new Princesa(OBJETIVO_POS_X, OBJETIVO_POS_Y);
        // Inicializa a câmera com o tamanho da tela
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        
        //camera.position.set(heroi.getX() + heroi.getWidth() - (camera.viewportWidth / 2f), camera.viewportHeight / 2f, 0); // DEBUG
        
        camera.update();   
    }

    @Override
    public void render () {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        batch.draw(mapa, 0, 0);
        
        heroi.render(batch);
        princesa.render(batch);               
        batch.draw(caixa, 500, 0, 100, 100);
        
        // Desenha a camera 
        batch.setProjectionMatrix(camera.combined); 
        
        update();
        
        batch.end();
    }
	
    @Override
    public void dispose () {
        batch.dispose();
    }
    
    public void update(){
        
        // Realiza a animação inicial do jogo apenas se estiver nos começo da fase
        if (comecoFase)
            animacaoInicial();
        
        if (animacaoMostrarObjetivo){
            //batch.draw(labelPrincesa, POSICAO_LABEL_PRINCESA_X, POSICAO_LABEL_PRINCESA_Y);
            batch.draw(seta45Graus, POSICAO_SETA_45_GRAUS_X, POSICAO_SETAS_Y);
            batch.draw(seta90Graus, POSICAO_SETA_90_GRAUS_X, POSICAO_SETAS_Y);
            batch.draw(seta135Graus, POSICAO_SETA_135_GRAUS_X, POSICAO_SETAS_Y);
        }
        
        if (fimAnimacaoInicial) {
            
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (heroi.getX() + heroi.getWidth() < mapa.getWidth())
                    heroi.andarDireita();
                if (camera.position.x <= (mapa.getWidth() - camera.viewportWidth)) 
                    camera.position.x = camera.position.x + VELOCIDADE_CAMERA_X;
            }        

            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (heroi.getX() > 0)
                    heroi.andarEsquerda();
                if (camera.position.x > camera.viewportWidth/2f)  
                    camera.position.x = camera.position.x - VELOCIDADE_CAMERA_X;
            }    

            else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                heroi.pular();
            }           

            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                heroi.abaixar();
            }
            
            else {
                heroi.parado();
            }
            
            // Evento de armadilha da pedra
            if (heroi.getX() <= 1000){
                pedra = new Pedra();
                pedra.ativarArmadilha(heroi.getX(), heroi.getY(), camera.viewportWidth);   
            }
            
            if (pedra != null && pedra.isVisivel()){            
                // Desenha o Goomba
                pedra.render(batch);   
                //batch.draw(pedra.getTEXTURA_PEDRA(), pedra.getX(), pedra.getY());
                // ? Destroi ? o objeto se sua animação tiver chegado ao fim
                if (!pedra.isVisivel())
                    pedra = null;
            }

            // Definir Local ou Quando ativar a animação final da princesa
            if (heroi.getX() <= (princesa.getX() + princesa.getWidth()))
                princesa.animacaoFinal();                                               
        }
        else
            batch.draw(labelPrincesa, POSICAO_LABEL_PRINCESA_X, POSICAO_LABEL_PRINCESA_Y);
        
        camera.update();
        heroi.update();
    }
        
    public void moverCamera(final int destinoX, final int destinoY){
        
        numeroRepeticoes = (int) Math.floor((destinoX - (camera.viewportWidth/2) - camera.position.x + 109)/VELOCIDADE_CAMERA_X - 2);
        contador = 0;
        
        moverCamera = new Timer.Task(){ 
                        @Override
                         public void run() { 
                             if (contador < numeroRepeticoes){
                                camera.position.x = camera.position.x + VELOCIDADE_CAMERA_X;
                                contador++;
                             }
                             else
                                 fimAnimacaoInicial = true;
                         }
        };        
        // Arrumar cálculo da câmera
        Timer.schedule(moverCamera, 3f, 0.05f, numeroRepeticoes);
    }

    private void mostrarObjetivo(){
        //Posiciona a camera no objetivo;
        //camera.position.x = princesa.getX();
        
        // Inicializa a task mostrarObjetivo
        mostrarObjetivo = new Timer.Task(){
                            @Override
                            public void run() {
                                // Realiza o efeito das setas "piscando" na tela
                                animacaoMostrarObjetivo = !animacaoMostrarObjetivo;
                            }
        };        
        Timer.schedule(mostrarObjetivo, 0, 0.5f, 6);      
    }        

    private void animacaoInicial() {
        mostrarObjetivo();
        moverCamera(heroi.getX(), 0);
        comecoFase = false;
    }
        
}
