package com.company.elementosDoSistema;

import com.company.engine.Tabuleiro;

import java.util.LinkedList;

public abstract class Fantasma {

    protected String nome;
    protected String cor;

    protected int nroVerticeAtual;


    protected boolean isMorto;

    public Fantasma(String nome, String cor, Tabuleiro t, int nroVerticeAtual) {
        this.nome = nome;
        this.cor = cor;
        this.nroVerticeAtual = nroVerticeAtual;
        isMorto = false;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public abstract void calculaMenorCaminho(Tabuleiro t, PacMan pc);

    public abstract LinkedList<Integer> getMenorCaminho();

    public int getNroVerticeAtual() {
        return nroVerticeAtual;
    }

    public void setNroVerticeAtual(int nroVerticeAtual) {
        this.nroVerticeAtual = nroVerticeAtual;
    }

    public boolean isMorto() {
        return isMorto;
    }

    public void setMorto() {
        isMorto = true;
    }
}
