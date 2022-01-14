package com.company.elementosDoSistema;

import com.company.engine.Tabuleiro;
import javafx.scene.paint.Color;

import java.util.LinkedList;

/**
 * Classe herdeira da classe 'Fantasma'. Representa o fantasma vermelho, 'Inky'.
 * */
public class Inky extends Fantasma {

    /**
     * Construtor padrao fantasma Inky.
     * @param nome String com o nome do fantasma.
     * @param cor String com a cor do fantasma.
     * @param t Tabuleiro de jogo.
     * @param nroVerticeAtual Numero do atual vertice ocupado pelo fantasma.
     */
    public Inky(String nome, String cor, Tabuleiro t, int nroVerticeAtual, Color codigoCorOriginal) {
        super(nome, cor, t, nroVerticeAtual, codigoCorOriginal);
    }

    @Override
    public void calculaMenorCaminho(Tabuleiro t, PacMan pm) { }

    @Override
    public LinkedList<Integer> getMenorCaminho() {
        return null;
    }

    public void restartInky() { setNroVerticeAtual(128); }

}
