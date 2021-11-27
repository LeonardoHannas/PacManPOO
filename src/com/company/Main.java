
package com.company;


import com.company.elementosDoSistema.*;
import com.company.enigine.*;

public class Main {

    public static void main(String[] args) {

        Tabuleiro t = new Tabuleiro(288, 4);

        t.montaTabuleiro(t);

        //t.imprimeTabuleiro(); // imprime tabuleiro sem personagens

        PacMan pm = new PacMan(t, 0); // cria PacMan no vertice 4

        //t.contabilizaPontuacao(t, pm);
//        System.out.println( "Pontuacao antes: " + t.getPontuacao());

        Blinky blinky = new Blinky("Blinky", "Vermelha", t, 262); // cria Blinky no vertice 245
        //blinky.calculaMenorCaminho(t, pm);

        Pinky pinky = new Pinky("Pinky", "Rosa", t, 285); // Cria Pinky no vertice 86
        //pinky.calculaMenorCaminho(t, pm);

        Inky inky = new Inky("Inky", "Verde", t, 286); // Cria Inky no vertice 47

        Clyde clyde = new Clyde("Clyde", "Laranja", t, 287); // Cria Clyde no vertice 98



        /* --------------------------------------------------------------------------------------------------------------- */

        int i = 0;
        boolean flag1 = true;
        boolean flag2 = true;

        while (pm.getNumVidas() > 0) {

            if (pm.getVerticesPercorridos().size() == 288) {
                t.setNivel(t.getNivel() + 1);
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pm.setVerticeAtual(t, i);

            pm.atualizaVerticesPercorridos(t, blinky, pinky, inky, clyde);
            t.contabilizaPontuacao(t, pm);
            t.imprimeTabuleiro(t, pm, blinky, pinky, inky, clyde);
            t.atualizaTabuleiro(pm);

            if (pm.getNumPacDotsComidos() == 10 || pm.getNumPacDotsComidos() == 20) { // setar Fruta Bonus.
                FrutaBonus fb = new FrutaBonus(t);
                fb.insereFrutaBonusTabuleiro(t, pm);

//                for (int j = 0; j < t.getArestas().length; j++)
//                    System.out.println("Vertice: " + j + " | FrutaBonus: " + t.getArestas()[j][0].hasFrutaBonus());


            }

            System.out.println("Pontuacao: " + t.getPontuacao());
            System.out.println("Qtd PacDots comidos: " + pm.getNumPacDotsComidos());
            System.out.println("Num Vidas: " + pm.getNumVidas());
            System.out.println("Pilula de Poder: " + pm.getPilulaDePoder());

            pm.gerenciaColisao(t, pm, blinky, pinky, inky, clyde);

            if (pm.getNumVidas() == 2 && flag1 == true) {
                i = 0;
                flag1 = false;
            }
            if (pm.getNumVidas() == 1 && flag2 == true) {
                i = 0;
                flag2 = false;
            }

            if (!blinky.isMorto()) {
                blinky.calculaMenorCaminho(t, pm);
                blinky.setVerticeAtual();
                pm.gerenciaColisao(t, pm, blinky, pinky, inky, clyde);

            }

            if (!pinky.isMorto()) {
                pinky.calculaMenorCaminho(t, pm);
                pinky.setVerticeAtual();
                pm.gerenciaColisao(t, pm, blinky, pinky, inky, clyde);

            }

            pm.gerenciaColisao(t, pm, blinky, pinky, inky, clyde);

            System.out.println();
            System.out.println();
            i++;



        }

        /* --------------------------------------------------------------------------------------------------------------- */

    }
}
