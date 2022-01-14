package com.company.elementosDoSistema;

import com.company.engine.Tabuleiro;
import javafx.scene.paint.Color;

import java.util.LinkedList;

/**
 * Classe herdeira da classe 'Fantasma'. Representa o fantasma vermelho, 'Blinky'.
 * */
public class Blinky extends Fantasma {

    private LinkedList<Integer> menorCaminho; // Lista encadeada que armazena a sequencia de numeros de vertices do
                                              // menor caminho entre o fantasma e o Pac Man.

    /**
     * Construtor padrao fantasma Blinky.
     * @param nome String com o nome do fantasma.
     * @param cor String com a cor do fantasma.
     * @param t Tabuleiro de jogo.
     * @param nroVerticeAtual Numero do atual vertice ocupado pelo fantasma.
     */
    public Blinky(String nome, String cor, Tabuleiro t, int nroVerticeAtual, Color codigoCorOriginal) {
        super(nome, cor, t, nroVerticeAtual,codigoCorOriginal);

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
     * Alem disso, o menor caminho pode ser alterado, dependendo da quantidade de Pac Dots ja comidos pelo Pac Man. Caso
     * o numero de Pac Dots comidos for superior a 96, o fantasma Blinky tem sua velocidade aumentada, pulando de 2 em 2 vertices.
     * Caso o numero de Pac Dots comidos for sueprior a 192, a velocidade do Blinky aumenta ainda mais, pulando, desta
     * vez, de 3 em 3 vertices.
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

    /**
     * Atualiza a posicao do fantasma no tabuleiro, fazendo ele andar para o seu proximo vertice calculado.
     */
    public void setVerticeAtual() {
        if (!isMorto()) nroVerticeAtual = menorCaminho.getFirst();
    }

    /**
     * Funcao para levar o fantasma Blinky para para o seu vertice de origem, alem de resetar suas confirguracoes.
     */
    public void restartBlinky() {
        setMorto();
        getMenorCaminho().clear();
        resetMorto();
        setNroVerticeAtual(287);
    }

}
