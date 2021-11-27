package com.company.elementosDoSistema;

import com.company.enigine.Tabuleiro;

import java.util.LinkedList;

public class Inky extends Fantasma {

    public Inky(String nome, String cor, Tabuleiro t, int nroVerticeAtual) {
        super(nome, cor, t, nroVerticeAtual);
    }

    @Override
    public void calculaMenorCaminho(Tabuleiro t, PacMan pc) {
    }

    @Override
    public LinkedList<Integer> getMenorCaminho() {
        return null;
    }

}
