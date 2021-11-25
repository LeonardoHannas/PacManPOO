package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class Blinky extends Fantasma {

    private LinkedList<Integer> menorCaminho;


    private int nivelVelocidade;


    Blinky(String nome, String cor, Tabuleiro t, int nroVerticeAtual) {
        super(nome, cor, t, nroVerticeAtual);

        //Vertice atual = new Vertice(nroVerticeAtual, 'B');
        //t.getArestas()[nroVerticeAtual][0].setChar('V');

        menorCaminho = new LinkedList<>();
        nivelVelocidade = 1;

    }


    public LinkedList<Integer> getMenorCaminho() {
        return menorCaminho;
    }

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

        //System.out.println("Caminho NIVEL 1: " + menorCaminho);
        //System.out.println("Tamanho Caminho 1: " + menorCaminho.size());


        //t.setNivel(2);


        LinkedList<Integer> aux = new LinkedList<>();
        int j;

        if (t.getNivel() == 2) {

            for (j = 0; j < menorCaminho.size(); j++) if (j % 2 != 0) aux.add(menorCaminho.get(j));

            if (!aux.contains(pm.getPosicaoAtual())) aux.add(pm.getPosicaoAtual());
            menorCaminho = aux;
            //System.out.println("Caminho NIVEL 2: " + menorCaminho);
            //System.out.println("Tamanho Caminho 2: " + menorCaminho.size());


        } else if (t.getNivel() == 3) {

            for (j = 0; j < menorCaminho.size(); j++) if ((j - 2) % 3 == 0) aux.add(menorCaminho.get(j));

            if (!aux.contains(pm.getPosicaoAtual())) aux.add(pm.getPosicaoAtual());
            menorCaminho = aux;
//            System.out.println("Caminho NIVEL 3: " + menorCaminho);
//            System.out.println("Tamanho Caminho 3: " + menorCaminho.size());

        }


    }

    public void setVerticeAtual() {
        nroVerticeAtual = menorCaminho.getFirst();
    }





}
