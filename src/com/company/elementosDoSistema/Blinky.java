package com.company.elementosDoSistema;

import com.company.engine.Tabuleiro;

import java.util.LinkedList;

public class Blinky extends Fantasma {

    private LinkedList<Integer> menorCaminho;


    public Blinky(String nome, String cor, Tabuleiro t, int nroVerticeAtual) {
        super(nome, cor, t, nroVerticeAtual);

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

        LinkedList<Integer> aux = new LinkedList<>();
        int j;

        if (pm.getNumPacDotsComidos() >= 96 && pm.getNumPacDotsComidos() < 192) {

            for (j = 0; j < menorCaminho.size(); j++) if (j % 2 != 0) aux.add(menorCaminho.get(j));

            if (!aux.contains(pm.getPosicaoAtual())) aux.add(pm.getPosicaoAtual());

            menorCaminho = aux;

        } else if (pm.getNumPacDotsComidos() >= 192) {

            for (j = 0; j < menorCaminho.size(); j++) if ((j - 2) % 3 == 0) aux.add(menorCaminho.get(j));

            if (!aux.contains(pm.getPosicaoAtual())) aux.add(pm.getPosicaoAtual());

            menorCaminho = aux;

        }

    }

    public void setVerticeAtual() {
        nroVerticeAtual = menorCaminho.getFirst();
    }





}
