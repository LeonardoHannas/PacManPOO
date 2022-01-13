package com.company.engine;

public class VerticeAux {

    private boolean paredeNorte;
    private boolean paredeSul;
    private boolean paredeLeste;
    private boolean paredeOeste;

    private double posX;
    private double posY;

    private int numero;

    public VerticeAux() {

        paredeNorte = true;
        paredeSul = true;
        paredeLeste = true;
        paredeOeste = true;

        posX = 0.0;
        posY = 0.0;

        numero = -1;

    }


    public void setParedeNorte(boolean paredeNorte) { this.paredeNorte = paredeNorte; }

    public void setParedeSul(boolean paredeSul) { this.paredeSul = paredeSul; }

    public void setParedeLeste(boolean paredeLeste) { this.paredeLeste = paredeLeste; }

    public void setParedeOeste(boolean paredeOeste) { this.paredeOeste = paredeOeste; }

    public boolean hasParedeNorte() { return paredeNorte; }

    public boolean hasParedeSul() { return paredeSul; }

    public boolean hasParedeLeste() { return paredeLeste; }

    public boolean hasParedeOeste() { return paredeOeste; }

    public void setPosX(double posX) { this.posX = posX; }

    public void setPosY(double posY) { this.posY = posY; }

    public double getPosX() { return posX; }

    public double getPosY() { return posY; }

    public void setNumero(int numero) { this.numero = numero; }

    public int getNumero() { return numero; }
}
