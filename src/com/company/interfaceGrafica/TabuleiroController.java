package com.company.interfaceGrafica;

import com.company.elementosDoSistema.PacMan;
import com.company.engine.Gerenciador;
import com.company.engine.VerticeAux;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

import java.util.Iterator;

public class TabuleiroController {

    @FXML
    AnchorPane PacManID;

    @FXML
    AnchorPane paneID;

    @FXML
    AnchorPane BlinkyID;

    @FXML
    AnchorPane PinkyID;

    @FXML
    AnchorPane InkyID;

    @FXML
    AnchorPane ClydeID;

    public EventHandler<KeyEvent> movimentoPacMan = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            if (movimentoPacManPermitido(event) == true) {

                Gerenciador.t.contabilizaPontuacao(Gerenciador.t, Gerenciador.pm);
                System.out.println("Pontuacao:" + Gerenciador.t.getPontuacao());


            }

        }

    };

    public EventHandler<KeyEvent> getMovimentoPacMan() {
        return movimentoPacMan;
    }

    public boolean movimentoPacManPermitido(KeyEvent event) {

        int numVertice = Gerenciador.pm.getAtual().getNumero();
        VerticeAux vAux;
        int i = procuraIndiceIMatrizAux(numVertice);
        int j = procuraIndiceJMatrizAux(numVertice);
        if (i != -1 && j != -1) {
            vAux = Gerenciador.t.getMatrizAux()[i][j];
            switch (event.getCode()) {

                case UP:
                    if (!vAux.hasParedeNorte()) {
                        vAux = Gerenciador.t.getMatrizAux()[i - 1][j]; // atualiza o vAux (proximo vertice do Pac Man)
                        Gerenciador.pm.setVerticeAtual(Gerenciador.t, vAux.getNumero()); // Setar vAux como o vertice atual do pac man
                        removeItemVerticeAtual(paneID);
                        Gerenciador.pm.atualizaVerticesPercorridos(Gerenciador.t, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);
                        atualizaCorFantasmas(Gerenciador.pm, BlinkyID, PinkyID, InkyID, ClydeID);
                        Gerenciador.t.atualizaTabuleiro(Gerenciador.pm);
                        removeItemVerticeAtual(paneID);
                        return true;
                    } else return false;

                case DOWN:
                    if (!vAux.hasParedeSul()) {
                        vAux = Gerenciador.t.getMatrizAux()[i + 1][j]; // atualiza o vAux (proximo vertice do Pac Man)
                        Gerenciador.pm.setVerticeAtual(Gerenciador.t, vAux.getNumero()); // Setar vAux como o vertice atual do pac man
                        removeItemVerticeAtual(paneID);
                        Gerenciador.pm.atualizaVerticesPercorridos(Gerenciador.t, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);
                        atualizaCorFantasmas(Gerenciador.pm, BlinkyID, PinkyID, InkyID, ClydeID);
                        Gerenciador.t.atualizaTabuleiro(Gerenciador.pm);
                        removeItemVerticeAtual(paneID);
                        return true;
                    } else return false;

                case LEFT:
                    if (!vAux.hasParedeOeste()) {
                        vAux = Gerenciador.t.getMatrizAux()[i][j - 1]; // atualiza o vAux (proximo vertice do Pac Man)
                        Gerenciador.pm.setVerticeAtual(Gerenciador.t, vAux.getNumero()); // Setar vAux como o vertice atual do pac man
                        removeItemVerticeAtual(paneID);
                        Gerenciador.pm.atualizaVerticesPercorridos(Gerenciador.t, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);
                        atualizaCorFantasmas(Gerenciador.pm, BlinkyID, PinkyID, InkyID, ClydeID);
                        Gerenciador.t.atualizaTabuleiro(Gerenciador.pm);
                        removeItemVerticeAtual(paneID);
                        return true;
                    } else return false;

                case RIGHT:
                    if (!vAux.hasParedeLeste()) {
                        vAux = Gerenciador.t.getMatrizAux()[i][j + 1]; // atualiza o vAux (proximo vertice do Pac Man)
                        Gerenciador.pm.setVerticeAtual(Gerenciador.t, vAux.getNumero()); // Setar vAux como o vertice atual do pac man
                        removeItemVerticeAtual(paneID);
                        Gerenciador.pm.atualizaVerticesPercorridos(Gerenciador.t, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);
                        atualizaCorFantasmas(Gerenciador.pm, BlinkyID, PinkyID, InkyID, ClydeID);
                        Gerenciador.t.atualizaTabuleiro(Gerenciador.pm);
                        removeItemVerticeAtual(paneID);
                        return true;
                    } else return false;
                default:
                    return false;

            }
        }
        return false;
    }

    public int procuraIndiceIMatrizAux(int n) {
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 28; j++) {
                if (Gerenciador.t.getMatrizAux()[i][j] == null) continue;
                else if (Gerenciador.t.getMatrizAux()[i][j].getNumero() == n) return i;
            }
        }
        return -1;
    }

    public int procuraIndiceJMatrizAux(int n) {
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 28; j++) {
                if (Gerenciador.t.getMatrizAux()[i][j] == null) continue;
                else if (Gerenciador.t.getMatrizAux()[i][j].getNumero() == n) return j;
            }
        }
        return -1;
    }

    public void removeItemVerticeAtual(AnchorPane pane) {


        String numVerticeAtual =Integer.toString(Gerenciador.pm.getPosicaoAtual());

        String str = "pacDotID";
        String res = str + numVerticeAtual;

        Iterator i = pane.getChildren().listIterator();

        while (i.hasNext()) {

            Node node = (Node) i.next();

            if (node instanceof GridPane) {

                GridPane gPane = (GridPane) node;
                Iterator iAux = gPane.getChildren().listIterator();

                while (iAux.hasNext()) {

                    Node n = (Node) iAux.next();
                    if (n.getId() == null) continue;

                    if (Gerenciador.pm.getPosicaoAtual() == 0 && n.getId().equals(str)) {
                        iAux.remove();
                        return;

                    } else if (Gerenciador.pm.getPosicaoAtual() != 0 && n.getId().equals(res)) {
                        iAux.remove();
                        return;
                    }


                }

            }

        }

    }

    public void atualizaCorFantasmas(PacMan pm, AnchorPane BlinkyID, AnchorPane PinkyID, AnchorPane InkyID, AnchorPane ClydeID) {

        Arc f1, f2, f3, f4;

        f1 = (Arc) BlinkyID.getChildren().get(0);
        f2 = (Arc) PinkyID.getChildren().get(0);
        f3 = (Arc) InkyID.getChildren().get(0);
        f4 = (Arc) ClydeID.getChildren().get(0);

        if (pm.getPilulaDePoder() == true) {
            // Fantasmas azuis
            f1.setFill(Color.BLUE);
            f2.setFill(Color.BLUE);
            f3.setFill(Color.BLUE);
            f4.setFill(Color.BLUE);

        } else {

            // Fantasmas nas cores originais
            f1.setFill(Gerenciador.blinky.getCodigoCorOriginal());
            f2.setFill(Gerenciador.pinky.getCodigoCorOriginal());
            f3.setFill(Gerenciador.inky.getCodigoCorOriginal());
            f4.setFill(Gerenciador.clyde.getCodigoCorOriginal());

        }
    }
}

//
//                switch(event.getCode()) {
//
//        case UP:
//            if (t.getMatrizAux()[(int) x/20][(int) y/20 - 1] != null && !atualPacMan.hasParedeNorte()) {
//                PacManID.setCenterY(yCentro - 20);
//                atualPacMan = t.getMatrizAux()[(int) x/20][(int) y/20 - 1];
//                break;
//            }
//
//        case DOWN:
//            if (t.getMatrizAux()[(int) x/20][(int) y/20 + 1] != null && !atualPacMan.hasParedeSul()) {
//                PacManID.setCenterY(yCentro + 20);
//                atualPacMan = t.getMatrizAux()[(int) x/20][(int) y/20 + 1];
//                break;
//            }
//
//        case LEFT:
//            if (t.getMatrizAux()[(int) x/20 - 1][(int) y/20] != null && !atualPacMan.hasParedeOeste()) {
//                PacManID.setCenterX(xCentro - 20);
//                atualPacMan = t.getMatrizAux()[(int) x/20 - 1][(int) y/20];
//                break;
//            }
//        case RIGHT:
//            if (t.getMatrizAux()[(int) x/20 + 1][(int) y/20] != null && !atualPacMan.hasParedeLeste()) {
//                PacManID.setCenterX(xCentro + 20);
//                atualPacMan = t.getMatrizAux()[(int) x/20 + 1][(int) y/20];
//                break;
//            }
//
//    }


//    Arc PacManID = null;
//
//    Iterator i = pane.getChildren().listIterator();
//
//        while (i.hasNext()) {
//        Node node = (Node) i.next();
//        if (node instanceof GridPane) {
//
//            GridPane gPane = (GridPane) node;
//            Iterator iAux = gPane.getChildren().listIterator();
//
//            while (iAux.hasNext()) {
//                Node n = (Node) iAux.next();
//                if (n instanceof Arc) {
//                    PacManID = (Arc) n;
//                }
//            }
//
//        }
//
//        PacManID.requestFocus();
//
//        double x, y, xCentro, yCentro;
//        xCentro = PacManID.getCenterX();
//        yCentro = PacManID.getCenterY();
//        x = xCentro - 10;
//        y = yCentro - 10;
//
//        VerticeAux atualPacMan = t.getMatrizAux()[(int) x/20][(int) y/20];
//
//        EventHandler<KeyEvent> keyEvent = new EventHandler<KeyEvent>() {
//            @Override
//
//        };
//
//        pane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent);
