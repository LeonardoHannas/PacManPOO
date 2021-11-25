package com.company;

import java.util.LinkedList;

public class Pinky extends Fantasma {
    private LinkedList<Integer> menorCaminho;

    Pinky(String nome, String cor, Tabuleiro t, int nroVerticeAtual) {
        super(nome, cor, t, nroVerticeAtual);

        t.getArestas()[nroVerticeAtual][0].setChar('R');

        menorCaminho = new LinkedList<>();

    }

    public LinkedList<Integer> getMenorCaminho() {
        return menorCaminho;
    }

    @Override
    public void calculaMenorCaminho(Tabuleiro t, PacMan pm) {
        menorCaminho.clear();
        int nroVerticeDestino = pm.getPosicaoAtual();
        int[] ant = t.menorCaminho(nroVerticeAtual, nroVerticeDestino);
        menorCaminho.addLast(nroVerticeDestino);
        int i = nroVerticeDestino;
        while(ant[i] != - 1) {
            menorCaminho.addFirst(ant[i]);
            nroVerticeDestino = ant[i];
            ant[i] = ant[ant[i]];
        }
        menorCaminho.removeFirst();
        //System.out.print(menorCaminho);
    }

    public void setVerticeAtual() {
        nroVerticeAtual = menorCaminho.getFirst();
    }

//    public void setNroProxVertice(Tabuleiro t, PacMan pm) {
//        calculaMenorCaminho(t, pm);
//        nroProxVertice = menorCaminho.getFirst();
//    }
//
//    public int getNroProxVertice() {
//        return nroProxVertice;
//    }
}