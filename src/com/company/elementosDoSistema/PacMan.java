package com.company.elementosDoSistema;

import com.company.engine.Tabuleiro;
import com.company.engine.Vertice;

import java.util.ArrayList;

/**
 * Classe que representa o persongaem controlado pelo usuario.
 */
public class PacMan {

    private int numVidas; // Numero de vidas restantes do Pac Man.

    private boolean pilulaDePoder; // Booleano que indica se o Pac Men esta (ou nao) sob efeito da Pilula de Poder.
    private long tempoInicial; // Marca o instante de tempo no qual o Pac Man comeu a Pilula de Poder.
    private long tempoFinal;  // Marca o instante de tempo no qual a Pilula de Poder deixa de ter efeito sobre o Pac Man.

    private Vertice atual; // Vertice atual do Pac Man no tabuleiro de jogo.

    private ArrayList<Integer> verticesPercorridos; // Vetor que armazena os numeros dos vertices ja visitados pelo Pac Man.

    private int numPacDotsComidos; // Variavel que armazena o numero de Pac Dots ja comidos pelo Pac Man.
    private int numFatasmasComidos; // Variavel que armazena o numero de fantasmas ja comidos pelo Pac Man.

    private boolean isMorto; // Booleano contendo a informacao de vida do Pac Man: se vivo, 'true', senao, 'false'.
    private boolean ganhouVidaExtra; // Booleano para saber se o Pac Man ganhou sua vida extra ao atingir os 10 mil pontos.


    /**
     * Construtor padrao do Pac Man.
     * @param t Tabuleiro de jogo.
     * @param nroVerticeAtual Numero do vertice atual ocupado pelo Pac Man.
     */
    public PacMan(Tabuleiro t, int nroVerticeAtual) {

        if (t.getArestas()[nroVerticeAtual][0].getChar() == '.') {
            t.setPontuacao(0);
            numPacDotsComidos = 1;
        }
        else if (t.getArestas()[nroVerticeAtual][0].getChar() == '*') {
            t.setPontuacao(50);
            numPacDotsComidos = 0;
        }

        atual = new Vertice(nroVerticeAtual, t.getArestas()[nroVerticeAtual][0].getChar());

        verticesPercorridos = new ArrayList<>();
        verticesPercorridos.add(nroVerticeAtual);

        numVidas = 3;
        numFatasmasComidos = 0;

        pilulaDePoder = false;
        tempoInicial = 0;
        tempoFinal = 0;

        isMorto = false;
        ganhouVidaExtra = false;

    }

    public void restartPacMan(Tabuleiro t) {

        setVerticeAtual(t, 0);
        getVerticesPercorridos().clear();
        verticesPercorridos.add(getPosicaoAtual());
        resetNumFantasmasComidos();
        resetNumPacDotsComidos();

    }

    public void resetNumFantasmasComidos() {
        numFatasmasComidos = 0;
    }

    public void resetNumPacDotsComidos() {
        numPacDotsComidos = 0;
    }

    public void setGanhouVidaExtra() { ganhouVidaExtra = true; }

    public boolean getGanhouVidaExtra() { return ganhouVidaExtra; }

    /**
     * Metodo que acessa a informacao de que o Pac Man esta morto ou vivo.
     * Retorna 'true', caso o personagem esteja morto, e 'false', caso esteja vivo.
     * @return boolean
     */
    public boolean isMorto() { return isMorto; }

    /**
     * Quando o Pac Man morre, este metodo seta o atributo 'isMorto' para 'true'.
     */
    public void setMorto() { isMorto = true; }

    /**
     * Quando o Pac Man esta vivo, entao 'isMorto' recebe 'false'.
     */
    public void resetMorto() { isMorto = false; }

    /**
     * Metodo para atualizar o numero de fantasmas comidos pelo Pac Man, apos a realizacao do calculo.
     * @param numFatasmasComidos Numero de fantasmas comidos atualizado.
     */
    public void setNumFatasmasComidos(int numFatasmasComidos) {
        this.numFatasmasComidos = numFatasmasComidos;
    }

    /**
     * Retorna o numero de fantasmas comidos pelo Pac Man.
     * @return int
     */
    public int getNumFatasmasComidos() {
        return numFatasmasComidos;
    }

    /**
     * Retorna o numero de Pac Dots comidos pelo Pac Man. Ele acessa todos os vertices percorridos e disconta, caso necessario,
     * os vertices que continham Pilulas de Poder.
     * @return int
     */
    public int getNumPacDotsComidos() {
        int n = verticesPercorridos.size();
        // Desconsidera as pilulas de poder
        if (verticesPercorridos.contains(47)) n--;
        if (verticesPercorridos.contains(62)) n--;
        if (verticesPercorridos.contains(169)) n--;
        if (verticesPercorridos.contains(182)) n--;
        return n;
    }

    /**
     * Metodo que verifica se o Pac Man esta sob efeito de alguma pilula de poder.
     * @return boolean
     */
    public boolean getPilulaDePoder() {
        return pilulaDePoder;
    }

    /**
     * Retorna o vertice atual do tabuleiro, onde o Pac Man se encontra.
     * @return Vertice
     */
    public Vertice getAtual() {
        return atual;
    }

    /**
     * Retorna o numero do vertice atual do tabuleiro, onde o Pac Man se encontra.
     * @return int
     */
    public int getPosicaoAtual() {
        return atual.getNumero();
    }

    /**
     * Atualiza o vertice atual do Pac Man.
     * @param t Tabuleiro de jogo.
     * @param numVertice Numero do novo vertice ocupado pelo Pac Man.
     */
    public void setVerticeAtual(Tabuleiro t, int numVertice) {
        atual.setNumero(numVertice);
        atual.setChar(t.getArestas()[numVertice][0].getChar());

    }

    /**
     * Retorna a quantidade de vidas restantes do Pac Man.
     * @return int
     */
    public int getNumVidas() {
        return numVidas;
    }

    /**
     * Metodo para setar a quantidade total de vidas restantes do Pac Man.
     * @param numVidas Numero de vidas a ser setado.
     */
    public void setNumVidas(int numVidas) { this.numVidas = numVidas; }

    /**
     * Acessa e retorna o ArrayList com os numeros dos vertices percorridos pelo Pac Man.
     * @return ArrayList
     */
    public ArrayList<Integer> getVerticesPercorridos() {
        return verticesPercorridos;
    }

    /**
     * Atualiza o ArrayList de vertices percorridos pelo Pac Man, adicionando-se a posicao atual do personagem, caso tal
     * posicao ainda nao pertenca a este ArrayList.
     * Alem disso, tal metodo verifica se o vertice atual do Pac Man possui uma Pilula de Poder. Em caso afirmativo, a
     * contagem do tempo eh disparada. Caso o instante atual seja superior ao limite de tempo da Pilula, o efeito da Pilula
     * eh acabado. Enquanto a Pilula permanece ativada, os fantasmas tem suas cores alteradas para o 'Azul'.
     * @param t Tabuleiro de jogo.
     * @param f1 Fantasma Blinky.
     * @param f2 Fantasma Pinky.
     * @param f3 Fantasma Inky.
     * @param f4 Fantasma Clyde.
     */
    public void atualizaVerticesPercorridos(Tabuleiro t, Blinky f1, Pinky f2, Inky f3, Clyde f4) {

        if (!getVerticesPercorridos().contains(getPosicaoAtual())) getVerticesPercorridos().add(getPosicaoAtual());

        if (getAtual().getChar() == '*') { // Pilula de Poder

            pilulaDePoder = true;
            t.setCorFantasmas(f1, f2, f3, f4);

            tempoInicial = System.currentTimeMillis();

            switch (t.getNivel()) {

                case 1:
                    tempoFinal = tempoInicial + 5000;
                    break;
                case 2:
                    tempoFinal = tempoInicial + 3000;
                    break;
                case 3:
                    tempoFinal = tempoInicial + 2000;
                    break;
                case 4:
                    tempoFinal = tempoInicial + 1000;
                    break;
                default:
                    tempoFinal = tempoInicial + 500;
                    break;
            }

        } else if (System.currentTimeMillis() > tempoFinal) {
            pilulaDePoder = false;
            t.resetCorFantasmas(f1, f2, f3, f4);
        }

    }

    /**
     * Caso o Pac Man ocupe o mesmo vertice que algum dos fantasmas, este metodo vai gerenciar este tipo de colisao.
     * Caso o Pac Man esteja sobre o efeito de uma Pilula de Poder, o fantasma morre e ha a atualizacao de pontos. Caso
     * contrario, o Pac Man morre e perde uma vida.
     * @param t Tabuleiro de jogo.
     * @param pm Pac Man.
     * @param f1 Fantasma Blinky.
     * @param f2 Fantasma Pinky.
     * @param f3 Fantasma Inky.
     * @param f4 Fantasma Clyde.
     */
    public void gerenciaColisao(Tabuleiro t, PacMan pm, Blinky f1, Pinky f2, Inky f3, Clyde f4) {
        int x = 0;
        if (!f1.isMorto() && getPosicaoAtual() == f1.getNroVerticeAtual()) x = 1;
        else if (!f2.isMorto() && getPosicaoAtual() == f2.getNroVerticeAtual()) x = 2;
        else if (!f3.isMorto() && getPosicaoAtual() == f3.getNroVerticeAtual()) x = 3;
        else if (!f4.isMorto() && getPosicaoAtual() == f4.getNroVerticeAtual()) x = 4;

        if (x == 0) return; // nao houve colisao
        else { // houve colisao

            if (!pilulaDePoder) { // Pac Man comido

                numVidas--;
                pm.setVerticeAtual(t, 0);
                f1.setNroVerticeAtual(287);
                f2.setNroVerticeAtual(262);
                f3.setNroVerticeAtual(128);
                f4.setNroVerticeAtual(135);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } else { // houve colisao e pilula de poder == true

                long hora = System.currentTimeMillis();

                if (hora < tempoFinal) {

                    switch (x) {
                        case 1: // Fantasma vermelho comido
                            f1.setMorto();
                            break;
                        case 2: // Fantasma rosa comido
                            f2.setMorto();
                            break;
                        case 3: // Fantasma verde comido
                            f3.setMorto();
                            break;
                        case 4: // Fantasma laranja comido
                            f4.setMorto();
                            break;
                    }

                    if (getNumFatasmasComidos() == 0) {
                        t.setPontuacao(t.getPontuacao() + 200);
                        setNumFatasmasComidos(1);
                    } else {
                        t.setPontuacao((int) (t.getPontuacao() + 200 * Math.pow(2, getNumFatasmasComidos())));
                        setNumFatasmasComidos(getNumFatasmasComidos() + 1);
                    }

                } else {
                    pilulaDePoder = false;
                }
            }
        }
    }
}


