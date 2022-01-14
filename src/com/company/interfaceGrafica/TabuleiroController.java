package com.company.interfaceGrafica;

import com.company.elementosDoSistema.Clyde;
import com.company.elementosDoSistema.Inky;
import com.company.elementosDoSistema.PacMan;
import com.company.engine.Gerenciador;
import com.company.engine.VerticeAux;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.Random;

/**
 * Classe para controlar as acoes que ocorrem no tabuleiro de jogo
 */
public class TabuleiroController {

    // Definicao das Tags utilizadas para a elaboracao da interface grafica

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


    /**
     * Metodo para captar os comandos provenientes do teclado dados ao Pac Man.
     */
    public EventHandler<KeyEvent> movimentoPacMan = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            if (movimentoPacManPermitido(event) == true) {

                Gerenciador.t.contabilizaPontuacao(Gerenciador.t, Gerenciador.pm);

            }

        }

    };

    /**
     * Metodo para se ter acesso aos movimentos dados pelo usuario ao Pac Man
     * @return EventHandler<KeyEvent>
     */
    public EventHandler<KeyEvent> getMovimentoPacMan() {
        return movimentoPacMan;
    }

    /**
     * Atualiza a pontuacao na Interface Grafica.
     * @param pontuacaoID ID da pontuacao
     */
    public void setPontuacaoID(Text pontuacaoID) {
        pontuacaoID.setText(null);
        pontuacaoID.setText(Integer.toString(Gerenciador.t.getPontuacao()));
    }

    /**
     * Atualiza o nivel de jogo na Interface Grafica
     * @param nivelID ID do nivel
     */
    public void setNivelID(Text nivelID) {
        nivelID.setText(null);
        nivelID.setText(Integer.toString(Gerenciador.t.getNivel()));
    }

    /**
     * Atualiza a quantidade de vidas do Pac Man na Interface Grafica
     * @param vidasID ID do numero de vidas
     */
    public void setVidasID(Text vidasID) {
        vidasID.setText(null);
        vidasID.setText(Integer.toString(Gerenciador.pm.getNumVidas()));

    }

    /**
     * Analisa se o comando de movimento do Pac Man eh um comando valido. Em caso positivo, faz as devidas atualzacoes e
     * retorna 'true'. Em caso negativo, retorna 'false'
     * @param event Evento do teclado
     * @return boolean
     */
    public boolean movimentoPacManPermitido(KeyEvent event) {

        int numVertice = Gerenciador.pm.getAtual().getNumero();
        VerticeAux vAux;
        int i = procuraIndiceIMatrizAux(numVertice);
        int j = procuraIndiceJMatrizAux(numVertice);
        if (i != -1 && j != -1) {
            vAux = Gerenciador.t.getMatrizAux()[i][j];
            switch (event.getCode()) {

                case UP: // Direcao Sul
                    if (!vAux.hasParedeNorte()) {
                        vAux = Gerenciador.t.getMatrizAux()[i - 1][j]; // Atualiza o vAux (proximo vertice do Pac Man)
                        Gerenciador.pm.setVerticeAtual(Gerenciador.t, vAux.getNumero()); // Setar vAux como o vertice atual do Pac Man
                        removeItemVerticeAtual(paneID); // Remove o PacDot ou a Pilula de Poder do vertice consumidos pelo Pac Man
                        Gerenciador.pm.atualizaVerticesPercorridos(Gerenciador.t, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);
                        atualizaCorFantasmas(Gerenciador.pm, BlinkyID, PinkyID, InkyID, ClydeID); // Atualiza a cor dos fantasmas
                        Gerenciador.t.atualizaTabuleiro(Gerenciador.pm); // Atualiza o tabuleiro de jogo
                        removeItemVerticeAtual(paneID); // Remove o PacDot ou a Pilula de Poder do vertice consumidos pelo Pac Man
                        return true;
                    } else return false;

                case DOWN: // Direcao Norte
                    if (!vAux.hasParedeSul()) {
                        vAux = Gerenciador.t.getMatrizAux()[i + 1][j];
                        Gerenciador.pm.setVerticeAtual(Gerenciador.t, vAux.getNumero());
                        removeItemVerticeAtual(paneID);
                        Gerenciador.pm.atualizaVerticesPercorridos(Gerenciador.t, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);
                        atualizaCorFantasmas(Gerenciador.pm, BlinkyID, PinkyID, InkyID, ClydeID);
                        Gerenciador.t.atualizaTabuleiro(Gerenciador.pm);
                        removeItemVerticeAtual(paneID);
                        return true;
                    } else return false;

                case LEFT: // Direcao Oeste
                    if (!vAux.hasParedeOeste()) {
                        vAux = Gerenciador.t.getMatrizAux()[i][j - 1];
                        Gerenciador.pm.setVerticeAtual(Gerenciador.t, vAux.getNumero());
                        removeItemVerticeAtual(paneID);
                        Gerenciador.pm.atualizaVerticesPercorridos(Gerenciador.t, Gerenciador.blinky, Gerenciador.pinky, Gerenciador.inky, Gerenciador.clyde);
                        atualizaCorFantasmas(Gerenciador.pm, BlinkyID, PinkyID, InkyID, ClydeID);
                        Gerenciador.t.atualizaTabuleiro(Gerenciador.pm);
                        removeItemVerticeAtual(paneID);
                        return true;
                    } else return false;

                case RIGHT: // Direcao Leste
                    if (!vAux.hasParedeLeste()) {
                        vAux = Gerenciador.t.getMatrizAux()[i][j + 1];
                        Gerenciador.pm.setVerticeAtual(Gerenciador.t, vAux.getNumero());
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

    /**
     * Analisa se o movimento do fantasma eh um movimento valido. Retorna 'true' no caso de movimento valido e 'false',
     * caso contratio.
     * @param indiceIMatrizAux Indice i do vertice ocupado pelo fantasma na matriz auxiliar.
     * @param indiceJMatrizAux Indice j do vertice ocupado pelo fantasma na matriz auxiliar.
     * @param direcao Direcao Norte, Sul, Leste ou Oeste
     * @return boolean
     */
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

    /**
     * Atualiza o numero do vertice do fantasma Inky, apos um movimento permitido.
     * @param indiceIMatrizAux Indice i do vertice ocupado pelo fantasma na matriz auxiliar.
     * @param indiceJMatrizAux Indice j do vertice ocupado pelo fantasma na matriz auxiliar.
     * @param direcaoAtual Direcao Norte, Sul, Leste ou Oeste.
     * @param inky Fantasma Inky.
     */
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

    /**
     * Atualiza o numero do vertice do fantasma Clyde, apos um movimento permitido.
     * @param indiceIMatrizAux Indice i do vertice ocupado pelo fantasma na matriz auxiliar.
     * @param indiceJMatrizAux Indice j do vertice ocupado pelo fantasma na matriz auxiliar.
     * @param direcaoAtual Direcao Norte, Sul, Leste ou Oeste.
     * @param clyde Fantasma Clyde.
     */
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

    /**
     * Funcao usada nos fantasmas Inky e Clyde. Tal funcao tenta manter a direcao de movimento anterior nos fantasmas.
     * Caso nao seja posivel, uma nova direcao e sorteada aleatoriamente. Essa nova direcao eh retornada por este metodo.
     * @param indiceIMatrizAux Indice i do vertice ocupado pelo fantasma na matriz auxiliar.
     * @param indiceJMatrizAux Indice j do vertice ocupado pelo fantasma na matriz auxiliar.
     * @param direcaoAnterior Direcao Norte, Sul, Leste ou Oeste.
     * @return Direcao.
     */
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


    /**
     * Metodo para procurar o indice da linha ocupado na matriz auxiliar por um vertice que contenha o numero passado como parametro.
     * Tal funcao retorna a linha "i" caso tal numero tenha sido encontrado. No caso contratio, retorna -1.
     * @param n Inteiro a ser procurado nos vertices auxiliares da matri auxiliar.
     * @return int
     */
    public int procuraIndiceIMatrizAux(int n) {
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 28; j++) {
                if (Gerenciador.t.getMatrizAux()[i][j] == null) continue;
                else if (Gerenciador.t.getMatrizAux()[i][j].getNumero() == n) return i;
            }
        }
        return -1;
    }

    /**
     * Metodo para procurar o indice da coluna ocupado na matriz auxiliar por um vertice que contenha o numero passado como parametro.
     * Tal funcao retorna a coluna "j" caso tal numero tenha sido encontrado. No caso contratio, retorna -1.
     * @param n Inteiro a ser procurado nos vertices auxiliares da matri auxiliar.
     * @return int
     */
    public int procuraIndiceJMatrizAux(int n) {
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 28; j++) {
                if (Gerenciador.t.getMatrizAux()[i][j] == null) continue;
                else if (Gerenciador.t.getMatrizAux()[i][j].getNumero() == n) return j;
            }
        }
        return -1;
    }

    /**
     * Metodo para remover um PacDot ou uma Pilula de Poder do vertice, apos a passagem do Pac Man por tal vertice
     * @param pane Pane do tabuleiro de jogo.
     */
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

    /**
     * Metodo para atualizar a cor dos fantasmas na tela de jogo. Quando o Pac Man esta sob efeito da Pilula de Poder,
     * todos os fantasmas tem suas cores alteradas para Azul. A partir do momento em que tal efeito acaba, as cores
     * sao restauradas aos seus respectivos valores originais.
     * @param pm Pac Man
     * @param BlinkyID ID do fantasma Blinky na Interface Grafica
     * @param PinkyID ID do fantasma Pinky na Interface Grafica
     * @param InkyID ID do fantasma Inky na Interface Grafica
     * @param ClydeID ID do fantasma Clyde na Interface Grafica
     */
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