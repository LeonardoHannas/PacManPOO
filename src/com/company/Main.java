package com.company;

import com.company.elementosDoSistema.*;
import com.company.engine.*;
import sun.nio.ch.sctp.SctpNet;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Tabuleiro t = new Tabuleiro(288, 4);
        t.montaTabuleiro(t);

        PacMan pm = new PacMan(t, 0); // cria PacMan no vertice 4

        Blinky blinky = new Blinky("Blinky", "Vermelha", t, 262); // cria Blinky no vertice 245
        //blinky.calculaMenorCaminho(t, pm);
        Pinky pinky = new Pinky("Pinky", "Rosa", t, 285); // Cria Pinky no vertice 86
        //pinky.calculaMenorCaminho(t, pm);
        Inky inky = new Inky("Inky", "Verde", t, 286); // Cria Inky no vertice 47
        Clyde clyde = new Clyde("Clyde", "Laranja", t, 287); // Cria Clyde no vertice 98

        pm.setMorto();
        blinky.setMorto();
        pinky.setMorto();
        inky.setMorto();
        clyde.setMorto();

        System.out.println("Parte 1 - Projeto POO - Pac Man");
        System.out.println("O tabuleiro de jogo contem 288 vertices, numerados de 0 a 287.");
        System.out.println("Os vertices representados com um ponto ('.') sao os Pac Dots.");
        System.out.println("Ja os representados com um asterisco ('*') sao as Pilulas de Poder, localizadas nos vertices 47, 62, 169 e 182.");
        t.imprimeTabuleiro(t, pm, blinky, pinky, inky, clyde);

        Scanner scanner = new Scanner(System.in);
        int posPacMan, posFantasma;

        System.out.println("O Pac Man sera representado pela letra 'P' e o fantasma pela letra 'F'");
        System.out.print("Escolha um vertice (de 0 287) para posicionar o Pac Man: ");
        posPacMan = scanner.nextInt();
        System.out.print("Escolha outro vertice (tambem de 0 a 287) para posiconar o fantasma: ");
        posFantasma = scanner.nextInt();

        pm.resetMorto();
        blinky.resetMorto();

        pm.setVerticeAtual(t, posPacMan);
        blinky.setNroVerticeAtual(posFantasma);

        blinky.calculaMenorCaminho(t, pm);
        blinky.getMenorCaminho().removeLast();

        t.imprimeCaminhoTabuleiro(t, pm, blinky);







    }
}
