package com.company.elementosDoSistema;

import com.company.engine.Tabuleiro;

/**
 * Classe representativa das Frutas Bonus, as quais aparecem quando o Pac Man come 70 ou 170 Pac Dots.
 */
public class FrutaBonus {

    private char simbolo; // Char que armazena o simbolo da Fruta Bonus no tabuleiro de jogo.
    private String nome; // Nome da Fruta Bonus.
    private int valor; // Valor (em pontos) da Fruta.
    private int nroVertice; // Numero do vertice o qual a Fruta Bonus ocupara.

    /**
     * Construtor da Fruta Bonus. Seu nome e seu valor dependem do nivel de jogo.
     * @param t Tabuleiro de jogo.
     */
    public FrutaBonus(Tabuleiro t) {

        if (t.getNivel() == 1) {
            nome = "Cereja";
            valor = 100;
        } else if (t.getNivel() == 2) {
            nome = "Morango";
            valor = 300;
        } else if (t.getNivel() == 3) {
            nome = "Laranja";
            valor = 500;
        }

        simbolo = '!';
        nroVertice = - 1;
    }

    /**
     * Retorna o nome da Fruta Bonus em questao.
     * @return String.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o valor (em pontos) da Fruta Bonus analisada.
     * @return int
     */
    public int getValor() {
        return valor;
    }

    /**
     * Funcao que insere uma Fruta Bonus no tabuleiro de jogo. Para isso, um vertice vazio eh procurado e, em seguida,
     * a insercao no tabuleiro eh feita.
     * @param t Tabuleiro de jogo.
     * @param pm Pac Man.
     */
    public void insereFrutaBonusTabuleiro(Tabuleiro t, PacMan pm) {

        nroVertice = t.procuraVerticeVazio(pm);

        t.getArestas()[nroVertice][0].setFrutaBonus(); // frutaBonus = true

    }

    public int getNroVerticeFrutaBonus() {
        return this.nroVertice;
    }

}
