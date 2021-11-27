package com.company.elementosDoSistema;

import com.company.engine.Tabuleiro;

public class FrutaBonus {

    private char simbolo;
    private String nome;
    private int valor;
    private int nroVertice;

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

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }

    public void insereFrutaBonusTabuleiro(Tabuleiro t, PacMan pm) {

        nroVertice = t.procuraVerticeVazio(pm);

        t.getArestas()[nroVertice][0].setFrutaBonus(); // frutaBonus = true

    }
}
