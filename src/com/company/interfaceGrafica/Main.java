package com.company.interfaceGrafica;

import com.company.elementosDoSistema.FrutaBonus;
import com.company.engine.Gerenciador;
import com.company.engine.Tabuleiro;
import com.company.engine.VerticeAux;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.font.FontManagerNativeLibrary;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe principal para o controle da aplicacao.
 */
public class Main extends Application {

    /**
     * Possiveis direcoes de movimento do Pac Man no tabuleiro de jogo.
     * Coordenadas adotadas:
     *
     *    (0,0)  +--------------> x
     *           |                                  Sul
     *           |                                   |
     *           |                        Oeste _____|_____ Leste
     *           |                                   |
     *           |                                   |
     *          \/                                 Norte
     *           y
     *
     */
    public enum Direcao {
        NORTE,
        SUL,
        LESTE,
        OESTE
    }

    /**
     * Determinacao das direcoes iniciais dos fantasmas Inky e Clyde, os quais
     * se movimentarao aleatoriamente.
     */
    public static Direcao direcaoInky = Direcao.NORTE;
    public static Direcao direcaoClyde = Direcao.SUL;

    // Metodo main
    public static void main(String[] args) {
        Gerenciador.start();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tabuleiro.fxml"));

        AnchorPane pane;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        primaryStage.setScene(new Scene(pane));
        primaryStage.show();

        TabuleiroController tc = loader.getController();
        tc.PacManID.requestFocus();

        // Captar o comando de movimentacao do Pac Man atraves do teclado
        pane.addEventHandler(KeyEvent.KEY_PRESSED, tc.getMovimentoPacMan());

        // Time Line para lidar com os movimentos do Pac Man
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
        tl.play();

        tl.setOnFinished(event -> {

            tl.getKeyFrames().clear();

            int i , j;
            double x, y;

            // Calula o numero do vertice atual, no grafo, do Pac Man
            int numVerticePacMan = Gerenciador.pm.getPosicaoAtual();

            // Calculo da posicao (i,j) do vertice da matriz auxiliar
            i = tc.procuraIndiceIMatrizAux(numVerticePacMan);
            j = tc.procuraIndiceJMatrizAux(numVerticePacMan);

            // Calculo das coordenadas X e Y do vertice na interface grafica
            x = 20*j - tc.PacManID.getLayoutX();
            y = 20*i - tc.PacManID.getLayoutY();

            // Atualiza a posicao do Pac Man na tela de jogo.
            KeyValue keyValueX = new KeyValue(tc.PacManID.translateXProperty(), x);
            KeyValue keyValueY = new KeyValue(tc.PacManID.translateYProperty(), y);
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(100), keyValueX, keyValueY));

            // Gerenciamento de colisao entre Pac Man e fantasmas, caso houver
            Gerenciador.pm.gerenciaColisao(Gerenciador.t, Gerenciador.pm, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);

            // Atualizacao dos mostradores na Interface Grafica
            tc.setPontuacaoID(tc.pontuacaoID);
            tc.setNivelID(tc.nivelID);
            tc.setVidasID(tc.vidasID);

            tl.play();

            // Encerramento da aplicacao, caso o numero de vidas do Pac Man seja igual a zero
            if (Gerenciador.pm.getNumVidas() == 0) javafx.application.Platform.exit();

        });

        // Time Line para lidar com os movimentos dos fantasmas
        Timeline tl2 = new Timeline();
        tl2.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
        tl2.play();

        tl2.setOnFinished(event -> {

            tl2.getKeyFrames().clear();

            int i, j;
            double x, y;

            if (!Gerenciador.blinky.isMorto()) { // Fantasma Blinky vivo

                // Calculo do menor caminho entre Blinky e o Pac Man
                Gerenciador.blinky.calculaMenorCaminho(Gerenciador.t, Gerenciador.pm);

                // Atualizacao do vertice ocupado pelo Blinky
                Gerenciador.blinky.setVerticeAtual();

                // Calculo da posicao (i,j) do vertice da matriz auxiliar
                i = tc.procuraIndiceIMatrizAux(Gerenciador.blinky.getNroVerticeAtual());
                j = tc.procuraIndiceJMatrizAux(Gerenciador.blinky.getNroVerticeAtual());

                // Calculo das coordenadas X e Y do vertice na interface grafica
                x = 20*j - tc.BlinkyID.getLayoutX();
                y = 20*i - tc.BlinkyID.getLayoutY();

                // Atualiza a posicao do fantasma na tela de jogo.
                KeyValue keyValueX = new KeyValue(tc.BlinkyID.translateXProperty(), x);
                KeyValue keyValueY = new KeyValue(tc.BlinkyID.translateYProperty(), y);
                tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

            } else { // Fantasma Blinky morto

                // Fantasma Blinky volta para o seu vertice de origem
                Gerenciador.blinky.setNroVerticeAtual(287);

                // Calculo da posicao (i,j) do vertice da matriz auxiliar
                i = tc.procuraIndiceIMatrizAux(287);
                j = tc.procuraIndiceJMatrizAux(287);

                // Calculo das coordenadas X e Y do vertice na interface grafica
                x = 20*j - tc.BlinkyID.getLayoutX();
                y = 20*i - tc.BlinkyID.getLayoutY();

                // Atualiza a posicao do fantasma na tela de jogo.
                KeyValue keyValueX = new KeyValue(tc.BlinkyID.translateXProperty(), x);
                KeyValue keyValueY = new KeyValue(tc.BlinkyID.translateYProperty(), y);
                tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

                // Fantasma renasce
                Gerenciador.blinky.resetMorto();

            }

            // Gerenciamento de colisao entre Pac Man e fantasmas, caso houver
            Gerenciador.pm.gerenciaColisao(Gerenciador.t, Gerenciador.pm, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);

            if (!Gerenciador.pinky.isMorto()) { // Fantasma Pinky vivo

                // Calculo do menor caminho entre Pinky e o Pac Man
                Gerenciador.pinky.calculaMenorCaminho(Gerenciador.t, Gerenciador.pm);

                // Atualizacao do vertice ocupado pelo Blinky
                Gerenciador.pinky.setVerticeAtual();

                // Calculo da posicao (i,j) do vertice da matriz auxiliar
                i = tc.procuraIndiceIMatrizAux(Gerenciador.pinky.getNroVerticeAtual());
                j = tc.procuraIndiceJMatrizAux(Gerenciador.pinky.getNroVerticeAtual());

                // Calculo das coordenadas X e Y do vertice na interface grafica
                x = 20*j - tc.PinkyID.getLayoutX();
                y = 20*i - tc.PinkyID.getLayoutY();

                // Atualiza a posicao do fantasma na tela de jogo.
                KeyValue keyValueX = new KeyValue(tc.PinkyID.translateXProperty(), x);
                KeyValue keyValueY = new KeyValue(tc.PinkyID.translateYProperty(), y);
                tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

                Gerenciador.pm.gerenciaColisao(Gerenciador.t, Gerenciador.pm, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);

            } else { // Fantasma Pinky morto

                // Fantasma Blinky volta para o seu vertice de origem
                Gerenciador.pinky.setNroVerticeAtual(262);

                // Calculo da posicao (i,j) do vertice da matriz auxiliar
                i = tc.procuraIndiceIMatrizAux(262);
                j = tc.procuraIndiceJMatrizAux(262);

                // Calculo das coordenadas X e Y do vertice na interface grafica
                x = 20*j - tc.PinkyID.getLayoutX();
                y = 20*i - tc.PinkyID.getLayoutY();

                // Atualiza a posicao do fantasma na tela de jogo.
                KeyValue keyValueX = new KeyValue(tc.PinkyID.translateXProperty(), x);
                KeyValue keyValueY = new KeyValue(tc.PinkyID.translateYProperty(), y);
                tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

                // Fantasma renasce
                Gerenciador.pinky.resetMorto();

            }

            // Gerenciamento de colisao entre Pac Man e fantasmas, caso houver
            Gerenciador.pm.gerenciaColisao(Gerenciador.t, Gerenciador.pm, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);


            if (!Gerenciador.inky.isMorto()) { // Fantasma Inky vivo

                // Calculo posicao inicial (i,j) do vertice ocupado pelo fantasma na matriz auxiliar
                i = tc.procuraIndiceIMatrizAux(Gerenciador.inky.getNroVerticeAtual());
                j = tc.procuraIndiceJMatrizAux(Gerenciador.inky.getNroVerticeAtual());

                if (i != -1 && j != -1) { // Posicao calculada na matriz eh valida

                    // Atualizacao da direcao do movimento do fantasma, o qual se move aleatoriamente
                    direcaoInky = tc.atualizaDirecaoMovimento(i, j, direcaoInky);

                    // Atualizacao do vertice ocupado pelo Inky
                    tc.atualizaNumVerticeInky(i, j, direcaoInky, Gerenciador.inky);

                    // Calculo da posicao (i,j) do vertice da matriz auxiliar
                    i = tc.procuraIndiceIMatrizAux(Gerenciador.inky.getNroVerticeAtual());
                    j = tc.procuraIndiceJMatrizAux(Gerenciador.inky.getNroVerticeAtual());

                    // Calculo das coordenadas X e Y do vertice na interface grafica
                    x = 20*j - tc.InkyID.getLayoutX();
                    y = 20*i - tc.InkyID.getLayoutY();

                    // Atualiza a posicao do fantasma na tela de jogo.
                    KeyValue keyValueX = new KeyValue(tc.InkyID.translateXProperty(), x);
                    KeyValue keyValueY = new KeyValue(tc.InkyID.translateYProperty(), y);
                    tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

                }

            } else { // Fantasma Inky morto

                // Fantasma Inky volta para o seu vertice de origem
                Gerenciador.inky.setNroVerticeAtual(128);

                // Calculo da posicao (i,j) do vertice da matriz auxiliar
                i = tc.procuraIndiceIMatrizAux(128);
                j = tc.procuraIndiceJMatrizAux(128);

                // Calculo das coordenadas X e Y do vertice na interface grafica
                x = 20*j - tc.InkyID.getLayoutX();
                y = 20*i - tc.InkyID.getLayoutY();

                // Atualiza a posicao do fantasma na tela de jogo.
                KeyValue keyValueX = new KeyValue(tc.InkyID.translateXProperty(), x);
                KeyValue keyValueY = new KeyValue(tc.InkyID.translateYProperty(), y);
                tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

                // Fantasma renasce
                Gerenciador.inky.resetMorto();

            }

            // Gerenciamento de colisao entre Pac Man e fantasmas, caso houver
            Gerenciador.pm.gerenciaColisao(Gerenciador.t, Gerenciador.pm, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);

            if (!Gerenciador.clyde.isMorto()) { // Fantasma Clyde vivo

                // Calculo posicao inicial (i,j) do vertice ocupado pelo fantasma na matriz auxiliar
                i = tc.procuraIndiceIMatrizAux(Gerenciador.clyde.getNroVerticeAtual());
                j = tc.procuraIndiceJMatrizAux(Gerenciador.clyde.getNroVerticeAtual());

                if (i != -1 && j != -1) { // Posicao calculada na matriz eh valida

                    // Atualizacao da direcao do movimento do fantasma, o qual se move aleatoriamente
                    direcaoClyde = tc.atualizaDirecaoMovimento(i, j, direcaoClyde);

                    // Atualizacao do vertice ocupado pelo Clyde
                    tc.atualizaNumVerticeClyde(i, j, direcaoClyde, Gerenciador.clyde);

                    // Calculo da posicao (i,j) do vertice da matriz auxiliar
                    i = tc.procuraIndiceIMatrizAux(Gerenciador.clyde.getNroVerticeAtual());
                    j = tc.procuraIndiceJMatrizAux(Gerenciador.clyde.getNroVerticeAtual());

                    // Calculo das coordenadas X e Y do vertice na interface grafica
                    x = 20 * j - tc.ClydeID.getLayoutX();
                    y = 20 * i - tc.ClydeID.getLayoutY();

                    // Atualiza a posicao do fantasma na tela de jogo.
                    KeyValue keyValueX = new KeyValue(tc.ClydeID.translateXProperty(), x);
                    KeyValue keyValueY = new KeyValue(tc.ClydeID.translateYProperty(), y);
                    tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

                }

            } else { // Fantasma Clyde morto

                // Fantasma Inky volta para o seu vertice de origem
                Gerenciador.clyde.setNroVerticeAtual(135);

                // Calculo da posicao (i,j) do vertice da matriz auxiliar
                i = tc.procuraIndiceIMatrizAux(135);
                j = tc.procuraIndiceJMatrizAux(135);

                // Calculo das coordenadas X e Y do vertice na interface grafica
                x = 20*j - tc.ClydeID.getLayoutX();
                y = 20*i - tc.ClydeID.getLayoutY();

                // Atualiza a posicao do fantasma na tela de jogo.
                KeyValue keyValueX = new KeyValue(tc.ClydeID.translateXProperty(), x);
                KeyValue keyValueY = new KeyValue(tc.ClydeID.translateYProperty(), y);
                tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

                // Fantasma renasce
                Gerenciador.clyde.resetMorto();

            }

            // Gerenciamento de colisao entre Pac Man e fantasmas, caso houver
            Gerenciador.pm.gerenciaColisao(Gerenciador.t, Gerenciador.pm, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);

            tl2.play();

        });

    }

}