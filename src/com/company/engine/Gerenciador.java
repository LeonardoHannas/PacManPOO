package com.company.engine;

import com.company.elementosDoSistema.*;
import javafx.scene.paint.Color;

public class Gerenciador {

    public static Tabuleiro t;
    public static PacMan pm;
    public static Blinky blinky;
    public static Pinky pinky;
    public static Inky inky;
    public static Clyde clyde;
    public static FrutaBonus fb;

    public static void start() {

        t = new Tabuleiro(288, 4);
        t.montaTabuleiro(t);

        pm = new PacMan(t, 0); // cria PacMan no vertice 4

        blinky = new Blinky("Blinky", "Vermelha", t, 287, Color.rgb(255, 23, 19)); // cria Blinky no vertice 287

        pinky = new Pinky("Pinky", "Rosa", t, 262, Color.rgb(255, 20, 233)); // Cria Pinky no vertice 262

        inky = new Inky("Inky", "Verde", t, 128, Color.rgb(23, 255, 20)); // Cria Inky no vertice 128

        clyde = new Clyde("Clyde", "Laranja", t, 135, Color.rgb(255, 145, 20)); // Cria Clyde no vertice 135

    }
}
