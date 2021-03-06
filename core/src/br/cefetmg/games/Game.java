package br.cefetmg.games;

import br.cefetmg.games.modelo.BaseArmadilha;
import br.cefetmg.games.modelo.Princesa;
import br.cefetmg.games.modelo.Heroi;
import br.cefetmg.games.modelo.Pedra;
import br.cefetmg.games.modelo.Chao;
import br.cefetmg.games.modelo.inimigos.BaseInimigo;
import br.cefetmg.games.modelo.inimigos.Medusa;
import br.cefetmg.games.utils.Collision;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;      
    // Constantes
    private final static int OBJETIVO_POS_X = 100;
    private final static int OBJETIVO_POS_Y = 0;
    private final static int VELOCIDADE_CAMERA_X = 5;
    private final static int POSICAO_INICIAL_HEROI_X = 3800;
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
    private Texture tutorial;
    private Texture espinhos;
    private Texture texturaMedusa;
    private Texture caixa;
    private Texture gameOverScreen;
    private Texture victoryScreen;
            
            
    // Modelos
    private Heroi heroi;
    private Princesa princesa;
    private Pedra pedra;
    private ArrayList<BaseInimigo> inimigos;
    private Chao chao;
    //Sons e Musica
    private Music musicaVitoria;
    private Music musicaDerrota;
    // Tasks
    private Timer.Task moverCamera;
    private Timer.Task mostrarObjetivo;
    private Timer.Task fimAnimacao;
    // Variáveis de Controle
    private boolean comecoFase;
    private boolean animacaoMostrarObjetivo;
    private boolean fimAnimacaoInicial;	
    private float distanciaHeroiTela;
    private int limiteCameraEsquerda;
    private int limiteCameraDireita;
    private List<BaseArmadilha> armadilhas;
    private boolean isAgachado;
    private int gameState;
    private long deltaTempoVitoria;
    private long tempoVitoria=0;
    private boolean primeiraFase;
    // DEBUG
    private boolean debug = false;
    private BitmapFont font;
    private float posicaoFontX;

    @Override
    public void create () {
        batch = new SpriteBatch();
        // Inicializa variáveis de controle;
        animacaoMostrarObjetivo = true;
        comecoFase = true;
        gameState = 0;
        primeiraFase = true;
        if (debug)
            fimAnimacaoInicial = true;
        else
            fimAnimacaoInicial = false;
        // Carrega as texturas 
        mapa = new Texture("Mapa.png");
        labelPrincesa = new Texture("LabelPrincesa.png");
        caixa = new Texture("TexturaPreta.png");
        seta45Graus = new Texture("Seta45Graus.png");
        seta90Graus = new Texture("Seta90Graus.png");
        seta135Graus = new Texture("Seta135Graus.png");
        texturaMedusa = new Texture("Medusa.png");
        tutorial = new Texture("Tutorial_Temp.png");
        espinhos = new Texture("Espinhos.png");
        gameOverScreen = new Texture(Gdx.files.internal("GameOverScreen.png"));
        victoryScreen = new Texture(Gdx.files.internal("VictoryScreen.png"));
        //Carrega os sons e musicas
        musicaVitoria = Gdx.audio.newMusic(Gdx.files.internal("Vitoria.mp3"));
        musicaDerrota = Gdx.audio.newMusic(Gdx.files.internal("Derrota.mp3"));
        // Inicializa os objetos modelos
        heroi = new Heroi(POSICAO_INICIAL_HEROI_X, POSICAO_INICIAL_HEROI_Y);
        princesa = new Princesa(OBJETIVO_POS_X, OBJETIVO_POS_Y);
        chao = new Chao();
        // Inicializa a câmera com o tamanho da tela
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        if (debug)
            camera.position.set(mapa.getWidth() - camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        else
            camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        distanciaHeroiTela = mapa.getWidth() - POSICAO_INICIAL_HEROI_X;
        if (debug){
            font = new BitmapFont();
            font.setColor(Color.BLACK);
            posicaoFontX = camera.position.x + 350;
        }
        inicializarArrayInimigos();
        // Cria array com as armadilhas
        inicializarArrayArmadilhas();
    }

    @Override
    public void render () {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        batch.draw(mapa, 0, 0);
        
        heroi.render(batch, debug);
        princesa.render(batch);               
        desenharObjetos();
        if (!inimigos.isEmpty())
            for (BaseInimigo inimigo: inimigos) {
                inimigo.render(batch);
            }
        
        if (debug)
            desenharVariaveisDebug();
        
        // Desenha a camera 
        batch.setProjectionMatrix(camera.combined); 

        if (gameState == 0)
            update();
        else if (gameState == 1)
            vencerJogo();
        else if (gameState == 2)
            perderJogo();
        
        batch.end();
    }
	
    @Override
    public void dispose () {
        batch.dispose();
        musicaVitoria.dispose();
    }
    
    public void update(){

        if (!debug){
            // Realiza a animação inicial do jogo apenas se estiver nos começo da fase
            if (comecoFase)
                animacaoInicial();

            if (animacaoMostrarObjetivo){
                //batch.draw(labelPrincesa, POSICAO_LABEL_PRINCESA_X, POSICAO_LABEL_PRINCESA_Y);
                batch.draw(seta45Graus, POSICAO_SETA_45_GRAUS_X, POSICAO_SETAS_Y);
                batch.draw(seta90Graus, POSICAO_SETA_90_GRAUS_X, POSICAO_SETAS_Y);
                batch.draw(seta135Graus, POSICAO_SETA_135_GRAUS_X, POSICAO_SETAS_Y);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            if (moverCamera != null)
                moverCamera.cancel();
            camera.position.x = mapa.getWidth() - camera.viewportWidth / 2f;
            fimAnimacaoInicial = true;
        }

        if (fimAnimacaoInicial) {
            isAgachado = false;

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                heroi.pular();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                heroi.socar(heroi,inimigos);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                boolean andou = false;
                 // Movimenta a câmera
                if ((heroi.getX() < limiteCameraDireita - distanciaHeroiTela) &&
                    (heroi.getX() > limiteCameraEsquerda + distanciaHeroiTela) &&
                    (camera.position.x <= (mapa.getWidth() - camera.viewportWidth/2f)))
                    if (limiteCameraDireita < mapa.getWidth())
                        camera.position.x = camera.position.x + VELOCIDADE_CAMERA_X;
                    else
                        camera.position.x = mapa.getWidth() - camera.viewportWidth / 2f;
                // Movimenta o Heroi
                if (heroi.getX() + heroi.getWidth() < mapa.getWidth())
                    andou = heroi.andarDireita();
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                boolean andou = false;
                // Movimenta a câmera
                if ((heroi.getX() < limiteCameraDireita - distanciaHeroiTela) &&
                    (heroi.getX() > limiteCameraEsquerda + distanciaHeroiTela) &&
                    (camera.position.x > camera.viewportWidth / 2f))
                    if (limiteCameraEsquerda > 0)
                        camera.position.x = camera.position.x - VELOCIDADE_CAMERA_X;
                    else
                        camera.position.x = camera.viewportWidth / 2f;
                // Movimenta o Heroi
                if (heroi.getX() > 0)
                    andou = heroi.andarEsquerda();
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                heroi.abaixar();
                isAgachado = true;
            }
            else if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
                heroi.parado();
            }

            limiteCameraEsquerda = (int) (camera.position.x - camera.viewportWidth / 2f);
            limiteCameraDireita = (int) (camera.position.x + camera.viewportWidth / 2f);

            verificarDisparoArmadilha();

            //Spawn de inimigos
            if (inimigos.size() < 1)
                inimigos.add( new Medusa(camera.position.x - camera.viewportWidth/2, texturaMedusa));

            //Deteccao de colisoes
            if (!isAgachado){
                heroi.hitbox.height = 175;
            }

            // Definir Local ou Quando ativar a animação final da princesa
            if (heroi.getX() <= (princesa.getX() + princesa.getWidth())) {
                princesa.animacaoFinal();
                gameState = 1;
                musicaVitoria.play();
            }
            if (heroi.getHP() <= 0) {
                gameState = 2;
                musicaDerrota.play();
            }
            
            verificarColisaoEspinho();
        }
        else{
            batch.draw(labelPrincesa, POSICAO_LABEL_PRINCESA_X, POSICAO_LABEL_PRINCESA_Y);
            batch.draw(tutorial, camera.viewportWidth / 2f, camera.viewportHeight / 4f);
        }


        if (debug)
            posicaoFontX = camera.position.x + 350;

        camera.update();
        heroi.update();
        updateInimigos();
    }
        
    public void moverCamera(final int destinoX, final int destinoY){
        
        float delay = 3f;
        float velocidadeAnimacaoMoverCamera = 0.05f;
        int numRepeticoes = (int) Math.floor((destinoX - (camera.viewportWidth/2) - camera.position.x + 109)/VELOCIDADE_CAMERA_X - 2);
                
        moverCamera = new Timer.Task(){ 
                        @Override
                        public void run() { 
                            camera.position.x = camera.position.x + VELOCIDADE_CAMERA_X;
                        }
        };
        
        fimAnimacao = new Timer.Task(){ 
                        @Override
                        public void run() { 
                            fimAnimacaoInicial = true;
                        }
        }; 
        // Arrumar cálculo da câmera
        Timer.schedule(moverCamera, delay, velocidadeAnimacaoMoverCamera, (int) Math.floor((destinoX - (camera.viewportWidth/2) - camera.position.x + 109)/VELOCIDADE_CAMERA_X - 2));
        Timer.schedule(fimAnimacao, delay+(velocidadeAnimacaoMoverCamera*numRepeticoes));
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

    private void inicializarArrayArmadilhas() {
        armadilhas = new ArrayList<BaseArmadilha>();
        armadilhas.add(new Pedra(700, 12, true));
        //armadilhas.append(new Pedra(1000,0));
    }

    private void verificarDisparoArmadilha() {
        for (BaseArmadilha armadilha: armadilhas){
            if (armadilha.isAtiva()){
                if ( (armadilha.getX() >= limiteCameraEsquerda) && (!armadilha.isVisivel()) ){
                    armadilha.ativarArmadilha();
                }
                if (armadilha.isVisivel()){
                    if ((armadilha.getX() >= limiteCameraEsquerda) && (armadilha.getX() <= limiteCameraDireita)){
                        armadilha.render(batch,debug);
                        if (verificarColisao(armadilha)){
                            heroi.perdeuVida();
                            armadilha.setColidiu(true);
                        }
                    }
                    else{
                        armadilha.setVisivel(false);
                        armadilha.setAtiva(false);
                    }
                }
            }
        }
    }

    private void inicializarArrayInimigos () {
        inimigos = new ArrayList<BaseInimigo>();
    }

    private void updateInimigos() {
        for(Iterator<BaseInimigo> i = inimigos.iterator(); i.hasNext(); ) {
            BaseInimigo temp = i.next();
            temp.update();
            if (Collision.rectsOverlap(heroi.hitbox, temp.hitbox))
            {
                //diminuir a vida do cabra
                heroi.perdeuVida();
                temp.dispose();
                i.remove();
            }
            if (temp.hitbox.x >= limiteCameraDireita)
                i.remove();
        }
    }

    private void desenharVariaveisDebug() {
        font.draw(batch, "Camera.x: "+camera.position.x, posicaoFontX, 300);
        font.draw(batch, "Limite Esquerda: "+limiteCameraEsquerda, posicaoFontX, 320);
        font.draw(batch, "Limite Direito: "+limiteCameraDireita, posicaoFontX, 340);
        font.draw(batch, "Heroi.x: "+heroi.getX(), posicaoFontX, 360);
        font.draw(batch, "HP: "+heroi.getHP(), posicaoFontX, 380);
    }

    private boolean verificarColisao(BaseArmadilha armadilha) {
        Collision verificaColisao = new Collision();
        if (verificaColisao.rectsOverlap(heroi.getHitbox(),armadilha.getHitbox()) && (!armadilha.getColidiu()))
            return true;
        else
            return false;
    }

    private void desenharObjetos() {
        chao.addNovaCaixa(500);
        batch.draw(caixa, 500, 0, 100, 100);
        desenharCaixasComEspinhos(3150,6);                 
    }
    
    private void desenharCaixasComEspinhos(int inicio, int quantidade){
        for (int i = 0; i < quantidade;i++){
            batch.draw(caixa, inicio, 0, 100, 100);
            chao.addNovaCaixa(inicio);            
            batch.draw(espinhos, inicio - 150, 21);
            chao.addNovoEspinho(inicio - 150);
            batch.draw(caixa, inicio - 250, 0, 100, 100);
            chao.addNovaCaixa(inicio - 250);
            inicio = inicio - 250;
        }
    }

    private void vencerJogo () {
        if (tempoVitoria == 0)
            tempoVitoria = System.currentTimeMillis();
        deltaTempoVitoria = System.currentTimeMillis() - tempoVitoria;
        if (deltaTempoVitoria >= 2500) {
            batch.draw(victoryScreen,limiteCameraEsquerda,0);
        }

    }

    private void perderJogo () {
        if (tempoVitoria == 0)
            tempoVitoria = System.currentTimeMillis();
        deltaTempoVitoria = System.currentTimeMillis() - tempoVitoria;
        if (deltaTempoVitoria >= 2500) {
            batch.draw(gameOverScreen, limiteCameraEsquerda, 0);
        }
    }

    private void verificarColisaoEspinho() {
        int deslocamentoCamera = 0;
        if ((heroi.getY() <= 40) && (Chao.getFloorHeightBelowCharacter(heroi.getHitbox().x, heroi.getHitbox().width) == 40)){
            heroi.perdeuVida();
            // Volta o heroi para cima da última caixa
            while(Chao.getFloorHeightBelowCharacter(heroi.getHitbox().x, heroi.getHitbox().width) != 100){
                heroi.setX(heroi.getX() + 1);
                heroi.hitbox.x = heroi.hitbox.x + 1;
                deslocamentoCamera++;
            }
            heroi.setX(heroi.getX() + 75);
            heroi.setY(heroi.getY() + 100);
            heroi.hitbox.y = heroi.hitbox.y + 60;
            camera.position.x = camera.position.x + (deslocamentoCamera + 75);
        }
    }
        
}
