package com.company.elementosDoSistema;

import com.company.engine.Tabuleiro;

import java.util.LinkedList;

/**
 * Classe herdeira da classe 'Fantasma'. Representa o fantasma vermelho, 'Pinky'.
 * */
public class Pinky extends Fantasma {

    private LinkedList<Integer> menorCaminho; // Lista encadeada que armazena a sequencia de numeros de vertices do
                                              // menor caminho entre o fantasma e o Pac Man.

    /**
     * Construtor padrao fantasma Pinky.
     * @param nome String com o nome do fantasma.
     * @param cor String com a cor do fantasma.
     * @param t Tabuleiro de jogo.
     * @param nroVerticeAtual Numero do atual vertice ocupado pelo fantasma.
     */
    public Pinky(String nome, String cor, Tabuleiro t, int nroVerticeAtual) {
        super(nome, cor, t, nroVerticeAtual);
        menorCaminho = new LinkedList<>();

    }

    /**
     * Metodo que retorna o menor caminho calculado entre o fantasma e o Pac Man.
     * @return LinkedList
     */
    public LinkedList<Integer> getMenorCaminho() {
        return menorCaminho;
    }

    /**
     * Calcula o menor camingo entre o fantasma e o Pac Man, seguindo-se o Algoritmo de Dijkstra.
     * @param t Tabuleiro de jogo.
     * @param pm Pac Man.
     */
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
    }

    /**
     * Atualiza a posicao do fantasma no tabuleiro, fazendo ele andar para o seu proximo vertice calculado.
     */
    public void setVerticeAtual() {
        nroVerticeAtual = menorCaminho.getFirst();
    }

}
