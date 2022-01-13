package com.company.interfaceGrafica;

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
import java.util.Iterator;

public class Main extends Application {

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
        pane.addEventHandler(KeyEvent.KEY_PRESSED, tc.getMovimentoPacMan());

        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
        tl.play();

        tl.setOnFinished(event -> {

            tl.getKeyFrames().clear();
            int numVerticePacMan = Gerenciador.pm.getPosicaoAtual();

            int i = tc.procuraIndiceIMatrizAux(numVerticePacMan);
            int j = tc.procuraIndiceJMatrizAux(numVerticePacMan);

            double x = 20*j - tc.PacManID.getLayoutX();
            double y = 20*i - tc.PacManID.getLayoutY();

            System.out.println("Pilula de Poder: " + Gerenciador.pm.getPilulaDePoder());


            KeyValue keyValueX = new KeyValue(tc.PacManID.translateXProperty(), x);
            KeyValue keyValueY = new KeyValue(tc.PacManID.translateYProperty(), y);

            tl.getKeyFrames().add(new KeyFrame(Duration.millis(100), keyValueX, keyValueY));



            tl.play();

        });

        Timeline tl2 = new Timeline();
        tl2.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
        tl2.play();

        tl2.setOnFinished(event -> {

            tl2.getKeyFrames().clear();

            if (!Gerenciador.blinky.isMorto()) {

                int i, j;
                double x, y;

                Gerenciador.blinky.calculaMenorCaminho(Gerenciador.t, Gerenciador.pm);
                Gerenciador.blinky.setVerticeAtual();
                i = tc.procuraIndiceIMatrizAux(Gerenciador.blinky.getNroVerticeAtual());
                j = tc.procuraIndiceJMatrizAux(Gerenciador.blinky.getNroVerticeAtual());

                x = 20*j - tc.BlinkyID.getLayoutX();
                y = 20*i - tc.BlinkyID.getLayoutY();

                KeyValue keyValueX = new KeyValue(tc.BlinkyID.translateXProperty(), x);
                KeyValue keyValueY = new KeyValue(tc.BlinkyID.translateYProperty(), y);

                tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

                Gerenciador.pm.gerenciaColisao(Gerenciador.t, Gerenciador.pm, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);

            }

            if (!Gerenciador.pinky.isMorto()) {

                int i, j;
                double x, y;

                Gerenciador.pinky.calculaMenorCaminho(Gerenciador.t, Gerenciador.pm);
                Gerenciador.pinky.setVerticeAtual();

                i = tc.procuraIndiceIMatrizAux(Gerenciador.pinky.getNroVerticeAtual());
                j = tc.procuraIndiceJMatrizAux(Gerenciador.pinky.getNroVerticeAtual());

                x = 20*j - tc.PinkyID.getLayoutX();
                y = 20*i - tc.PinkyID.getLayoutY();

                KeyValue keyValueX = new KeyValue(tc.PinkyID.translateXProperty(), x);
                KeyValue keyValueY = new KeyValue(tc.PinkyID.translateYProperty(), y);

                tl2.getKeyFrames().add(new KeyFrame(Duration.millis(300), keyValueX, keyValueY));

                Gerenciador.pm.gerenciaColisao(Gerenciador.t, Gerenciador.pm, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);

            }

            tl2.play();

        });

    }
}












//        PacManID.getOnKeyPressed();



// A TimeLine eh unica. ela sera usada para armazenar a sequencia de eventos
// para cada entidade (pac man, cada fantasma) serao necessarias 2 keyvalues (1 pra movimentar em X e a outra pra movimentar em Y)
// No scene builder, o eixo eh:
//   (0,0)  +----------> x
//          |
//          |
//          |
//          |
//          \/
//          y
//


//        Circle circle = new Circle();
//
//        Iterator i = pane.getChildren().listIterator();
//
//        while (i.hasNext()) {
//            Node node = (Node) i.next();
//            if (node instanceof GridPane) {
//
//                GridPane gPane = (GridPane) node;
//                Iterator iAux = gPane.getChildren().listIterator();
//
//                while (iAux.hasNext()) {
//                    Node n = (Node) iAux.next();
//                    if (n instanceof Circle) {
//                        circle = (Circle) n;
//                    }
//                }
//
//            }
//        }
//
//        circle.setFill(Color.RED);
//
//        System.out.println(circle.getCenterX());
//
//        primaryStage.setScene(new Scene(pane));
//        primaryStage.show();
//
//        KeyValue keyValueX = new KeyValue(circle.translateXProperty(), 200);
//        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), keyValueX);
//
//        Timeline timeline = new Timeline();
//        timeline.getKeyFrames().add(keyFrame);
//        timeline.play();
//
//        final Circle c = circle;
//
//        timeline.setOnFinished(e -> {
//            timeline.getKeyFrames().clear();
//            KeyValue keyX = new KeyValue(c.translateXProperty(), 50);
//            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), keyX));
//            timeline.play();
//        });