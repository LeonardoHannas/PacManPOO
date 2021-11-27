package com.company.elementosDoSistema;

import com.company.enigine.Tabuleiro;

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
      //  nroProxVertice = -1;
        //atual.setNumero(nroVerticeInicial);
    }

    //public abstract void calculaMenorCaminho();


    public void setCor(String cor) {
        this.cor = cor;
    }

    public abstract void calculaMenorCaminho(Tabuleiro t, PacMan pc);

    public abstract LinkedList<Integer> getMenorCaminho();

//    public abstract void setNroProxVertice(Tabuleiro t, PacMan pm);
//
//    public abstract int getNroProxVertice();

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
