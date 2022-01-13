package com.company.elementosDoSistema;

import com.company.engine.Tabuleiro;
import javafx.scene.paint.Color;

import java.util.LinkedList;

/**
 * Classe abstrata que representa os quatro fantasmas do jogo.
 * */
public abstract class Fantasma {

    protected String nome; // Nome do fantasma.
    protected String cor; // Cor do fantasma.
    protected Color codigoCorOriginal;
    protected int nroVerticeAtual; // Numero do vertice do tabuleiro ocupado pelo fantasma.

    protected boolean isMorto; // Booleano contendoa informacao de vida do fantasma: se vivo, 'true', senao, 'false'.


    /**
     * Construtor de um objeto da classe 'Fantasma'.
     * @param nome String com o nome do fantasma.
     * @param cor String com a cor do fantasma.
     * @param t Tabuleiro de jogo.
     * @param nroVerticeAtual Numero do atual vertice ocupado pelo fantasma.
     */
    public Fantasma(String nome, String cor, Tabuleiro t, int nroVerticeAtual, Color codigoCorOriginal) {
        this.nome = nome;
        this.cor = cor;
        this.nroVerticeAtual = nroVerticeAtual;
        isMorto = false;
        this.codigoCorOriginal = codigoCorOriginal;
    }

    /**
     * Metdo responsavel por setar a cor de um fantasma.
     * @param cor Cor a ser colocada no fantasma.
     */
    public void setCor(String cor) {
        this.cor = cor;
    }

    public Color getCodigoCorOriginal() {
        return this.codigoCorOriginal;
    }

    /**
     * Metodo abstrato para o calculo do menor caminho entre o fantasma e o Pac Man.
     * @param t Tabuleiro de jogo.
     * @param pm Pac Man.
     */
    public abstract void calculaMenorCaminho(Tabuleiro t, PacMan pm);

    /**
     * Metodo abstrato para acessar a LinkedList que contem a sequencia dos numeros dos vertices a serem percorridos
     * durante o caminho do fantasma ate o Pac Man.
     * @return LinkedList
     */
    public abstract LinkedList<Integer> getMenorCaminho();

    /**
     * Metodo para acessar o numero do atual vertice ocupado pelo fantasma.
     * @return int
     */
    public int getNroVerticeAtual() {
        return nroVerticeAtual;
    }

    /**
     * Metodo para atualizar o numero de vertice atual do fantasma.
     * @param nroVerticeAtual Numero do vertice atual.
     */
    public void setNroVerticeAtual(int nroVerticeAtual) {
        this.nroVerticeAtual = nroVerticeAtual;
    }

    /**
     * Metodo que acessa a informacao de que o fantasma esta morto ou vivo.
     * Retorna 'true', caso o personagem esteja morto, e 'false', caso esteja vivo.
     * @return boolean
     */
    public boolean isMorto() {
        return isMorto;
    }

    /**
     * Quando o fantasma morre, este metodo seta o atributo 'isMorto' para 'true'.
     */
    public void setMorto() {
        isMorto = true;
    }

    /**
     * Quando o fantasma esta vivo, entao 'isMorto' recebe 'false'.
     */
    public void resetMorto() { isMorto = false; }
}
