package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class PacMan {

    private int numVidas;

    private boolean pilulaDePoder;
    private long tempoInicial;
    private long tempoFinal;

    private Vertice atual;

    private ArrayList<Integer> verticesPercorridos;

    private int numPacDotsComidos;
    private int numFatasmasComidos;

    PacMan(Tabuleiro t, int nroVerticeAtual) {

        if (t.getArestas()[nroVerticeAtual][0].getChar() == '.') {
            t.setPontuacao(0);
            numPacDotsComidos = 1;
        }
        else if (t.getArestas()[nroVerticeAtual][0].getChar() == '*') {
            t.setPontuacao(0);
            numPacDotsComidos = 0;
        }

        //atual = t.getArestas()[nroVerticeAtual][0];
        atual = new Vertice(nroVerticeAtual, t.getArestas()[nroVerticeAtual][0].getChar());
//        atual.setNumero(nroVerticeAtual);
//        atual.setChar(t.getArestas()[nroVerticeAtual][0].getChar());


        verticesPercorridos = new ArrayList<>();
        verticesPercorridos.add(nroVerticeAtual);

        numVidas = 3;
        numFatasmasComidos = 0;

        pilulaDePoder = false;
        tempoInicial = 0;
        tempoFinal = 0;

    }
//
//    public void setNroProxVertice() {
//        if (atual.getNumero() < 288) nroProxVertice = atual.getNumero() + 1;
//    }
//
//    public int getNroProxVertice() {
//        return nroProxVertice;
//    }

    public void setNumFatasmasComidos(int numFatasmasComidos) {
        this.numFatasmasComidos = numFatasmasComidos;
    }

    public int getNumFatasmasComidos() {
        return numFatasmasComidos;
    }

    public int getNumPacDotsComidos() {
        int n = verticesPercorridos.size();
        // Desconsidera as pilulas de poder
        if (verticesPercorridos.contains(47)) n--;
        if (verticesPercorridos.contains(62)) n--;
        if (verticesPercorridos.contains(169)) n--;
        if (verticesPercorridos.contains(182)) n--;
        return n;
    }

    public boolean getPilulaDePoder() {
        return pilulaDePoder;
    }

    public void resetPilulaDePoder() {
        pilulaDePoder = false;
    }

    public Vertice getAtual() {
        return atual;
    }

    public int getPosicaoAtual() {
        return atual.getNumero();
    }

    public void setVerticeAtual(Tabuleiro t, int numVertice) {
        //atual = t.getArestas()[numVertice][0];
        atual.setNumero(numVertice);
        atual.setChar(t.getArestas()[numVertice][0].getChar());

    }

    public int getNumVidas() {
        return numVidas;
    }

    public ArrayList<Integer> getVerticesPercorridos() {
        return verticesPercorridos;
    }

    public void atualizaVerticesPercorridos(Tabuleiro t, Blinky f1, Pinky f2, Inky f3, Clyde f4) {

        if (!getVerticesPercorridos().contains(getPosicaoAtual())) getVerticesPercorridos().add(getPosicaoAtual());


        //if (getAtual().hasFrutaBonus())

        if (getAtual().getChar() == '*') {
            pilulaDePoder = true;
            t.setCorFantasmas(f1, f2, f3, f4);

            tempoInicial = System.currentTimeMillis();
            tempoFinal = tempoInicial + 5000;

        } else if (System.currentTimeMillis() > tempoFinal) {
            pilulaDePoder = false;
            t.resetCorFantasmas(f1, f2, f3, f4);
        }

    }


    public void gerenciaColisao(Tabuleiro t, PacMan pm, Blinky f1, Pinky f2, Inky f3, Clyde f4) {
        int x = 0;
        if (!f1.isMorto() && getPosicaoAtual() == f1.getNroVerticeAtual()) x = 1;
        else if (!f2.isMorto() && getPosicaoAtual() == f2.getNroVerticeAtual()) x = 2;
        else if (!f3.isMorto() && getPosicaoAtual() == f3.getNroVerticeAtual()) x = 3;
        else if (!f4.isMorto() && getPosicaoAtual() == f4.getNroVerticeAtual()) x = 4;

        if (x == 0) return; // nao houve colisao
        else { // houve colisao

            if (pilulaDePoder == false) { // Pac Man comido

                numVidas--;
                pm.setVerticeAtual(t, 0);
                f1.setNroVerticeAtual(262);
                f2.setNroVerticeAtual(285);
                f3.setNroVerticeAtual(286);
                f4.setNroVerticeAtual(287);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println();
                System.out.println();
                System.out.println("Voce Morreu!");
                System.out.println("Vidas restantes: " + pm.getNumVidas());

                t.imprimeTabuleiro(t, pm, f1, f2, f3, f4);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return; // reiniciar o jogo

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

                    switch (getNumFatasmasComidos()) {
                        case 0:
                            t.setPontuacao(t.getPontuacao() + 200);
                            setNumFatasmasComidos(1);
                            break;
                        case 1:
                            t.setPontuacao(t.getPontuacao() + 400);
                            setNumFatasmasComidos(2);
                            break;
                        case 2:
                            t.setPontuacao(t.getPontuacao() + 800);
                            setNumFatasmasComidos(3);
                            break;
                        case 3:
                            t.setPontuacao(t.getPontuacao() + 1600);
                            setNumFatasmasComidos(4);
                            break;
                        default:
                            break;
                    }

                } else {
                    pilulaDePoder = false;
                }
            }
        }
    }
}


