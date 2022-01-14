package com.company.interfaceGrafica;

import com.company.elementosDoSistema.Clyde;
import com.company.elementosDoSistema.Inky;
import com.company.elementosDoSistema.PacMan;
import com.company.engine.Gerenciador;
import com.company.engine.VerticeAux;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.Random;

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

    @FXML
    Text nivelID;

    @FXML
    Text vidasID;

    @FXML
    Text pontuacaoID;


    public EventHandler<KeyEvent> movimentoPacMan = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            if (movimentoPacManPermitido(event) == true) {

                Gerenciador.t.contabilizaPontuacao(Gerenciador.t, Gerenciador.pm);
//                setPontuacaoID(pontuacaoID);
//                setNivelID(nivelID);

                System.out.println("Pontuacao:" + Gerenciador.t.getPontuacao());


            }

        }

    };

    public EventHandler<KeyEvent> getMovimentoPacMan() {
        return movimentoPacMan;
    }

    public void setPontuacaoID(Text pontuacaoID) {
        pontuacaoID.setText(null);
        pontuacaoID.setText(Integer.toString(Gerenciador.t.getPontuacao()));
    }

    public void setNivelID(Text nivelID) {
        nivelID.setText(null);
        nivelID.setText(Integer.toString(Gerenciador.t.getNivel()));
    }

    public void setVidasID(Text vidasID) {
        vidasID.setText(null);
        vidasID.setText(Integer.toString(Gerenciador.pm.getNumVidas()));

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

    public boolean movimentoFantasmaPermitido(int indiceIMatrizAux, int indiceJMatrizAux, Main.Direcao direcao) {

        VerticeAux vAtual = Gerenciador.t.getMatrizAux()[indiceIMatrizAux][indiceJMatrizAux];

        switch (direcao) {

            case NORTE:
                if (!vAtual.hasParedeNorte() && Gerenciador.t.getMatrizAux()[indiceIMatrizAux - 1][indiceJMatrizAux] != null) return true;
                else return false;

            case SUL:
                if (!vAtual.hasParedeSul() && Gerenciador.t.getMatrizAux()[indiceIMatrizAux + 1][indiceJMatrizAux] != null) return true;
                else return false;

            case LESTE:
                if (!vAtual.hasParedeLeste() && Gerenciador.t.getMatrizAux()[indiceIMatrizAux][indiceJMatrizAux + 1] != null) return true;
                else return false;

            case OESTE:
                if (!vAtual.hasParedeOeste() && Gerenciador.t.getMatrizAux()[indiceIMatrizAux][indiceJMatrizAux - 1] != null) return true;
                else return false;

            default:
                return false;

        }
    }

public void atualizaNumVerticeInky(int indiceIMatrizAux, int indiceJMatrizAux, Main.Direcao direcaoAtual, Inky inky) {

        if (movimentoFantasmaPermitido(indiceIMatrizAux, indiceJMatrizAux, direcaoAtual)) {

            switch (direcaoAtual) {
                case NORTE:
                    inky.setNroVerticeAtual(Gerenciador.t.getMatrizAux()[indiceIMatrizAux - 1][indiceJMatrizAux].getNumero());
                    break;

                case SUL:
                    inky.setNroVerticeAtual(Gerenciador.t.getMatrizAux()[indiceIMatrizAux + 1][indiceJMatrizAux].getNumero());
                    break;

                case LESTE:
                    inky.setNroVerticeAtual(Gerenciador.t.getMatrizAux()[indiceIMatrizAux][indiceJMatrizAux + 1].getNumero());
                    break;

                case OESTE:
                    inky.setNroVerticeAtual(Gerenciador.t.getMatrizAux()[indiceIMatrizAux][indiceJMatrizAux - 1].getNumero());
                    break;

                default:
                    break;
            }
        }

}

    public void atualizaNumVerticeClyde(int indiceIMatrizAux, int indiceJMatrizAux, Main.Direcao direcaoAtual, Clyde clyde) {

        if (movimentoFantasmaPermitido(indiceIMatrizAux, indiceJMatrizAux, direcaoAtual)) {

            switch (direcaoAtual) {
                case NORTE:
                    clyde.setNroVerticeAtual(Gerenciador.t.getMatrizAux()[indiceIMatrizAux - 1][indiceJMatrizAux].getNumero());
                    break;

                case SUL:
                    clyde.setNroVerticeAtual(Gerenciador.t.getMatrizAux()[indiceIMatrizAux + 1][indiceJMatrizAux].getNumero());
                    break;

                case LESTE:
                    clyde.setNroVerticeAtual(Gerenciador.t.getMatrizAux()[indiceIMatrizAux][indiceJMatrizAux + 1].getNumero());
                    break;

                case OESTE:
                    clyde.setNroVerticeAtual(Gerenciador.t.getMatrizAux()[indiceIMatrizAux][indiceJMatrizAux - 1].getNumero());
                    break;

                default:
                    break;
            }
        }

    }

 public Main.Direcao atualizaDirecaoMovimento(int indiceIMatrizAux, int indiceJMatrizAux, Main.Direcao direcaoAnterior) {
        Main.Direcao novaDirecao;
        if (movimentoFantasmaPermitido(indiceIMatrizAux, indiceJMatrizAux, direcaoAnterior)) novaDirecao = direcaoAnterior;
        else {
            Main.Direcao[] direcoes = {Main.Direcao.NORTE, Main.Direcao.SUL, Main.Direcao.LESTE, Main.Direcao.OESTE};

            Random gerador = new Random();
            do {
                int pos = gerador.nextInt(4);
                novaDirecao = direcoes[pos];
            } while(novaDirecao == direcaoAnterior);
        }

        return novaDirecao;
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
