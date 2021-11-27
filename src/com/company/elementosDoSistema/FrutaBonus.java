package com.company.elementosDoSistema;

import com.company.enigine.Tabuleiro;

public class FrutaBonus {

    private char simbolo;
    private String nome;
    private int valor;
    //private boolean comida;
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
        //comida = false;
        nroVertice = - 1;
    }

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }

    //public boolean isComida() { return comida; }

    //public void setComida() { comida = true; }

    public void insereFrutaBonusTabuleiro(Tabuleiro t, PacMan pm) {

        nroVertice = t.procuraVerticeVazio(pm);
        //System.out.println("Nro Vertice Insere Fruta Bonus Tabuleiro: " + nroVertice);

        t.getArestas()[nroVertice][0].setFrutaBonus(); // frutaBonus = true
        //t.getArestas()[nroVertice][0].resetFrutaBonusComida(); // frutaBonusComida = false
        //t.getArestas()[nroVertice][0].setChar(simbolo);

    }
}
