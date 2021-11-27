package com.company.engine;

public class Vertice {

    private int numero;
    private char c;
    private boolean comido;

    private boolean frutaBonus;
    private boolean frutaBonusComida;



    
    public Vertice(int numero, char c) {
        this.numero = numero;
        this.c = c;
        comido = false;

        frutaBonus = false;
        frutaBonusComida = false;


    }

    public boolean hasFrutaBonus() {
        return frutaBonus;
    }
    public void setFrutaBonus() { frutaBonus = true; }
    public void resetFrutaBonus() { frutaBonus = false; }

    public boolean isFrutaBonusComida() {
        return frutaBonusComida;
    }
    public void setFrutaBonusComida() {
        frutaBonusComida = true;
    }

    public int getNumero() { return this.numero; }

    public char getChar() {
        return this.c;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setChar(char c) {
        this.c = c;
    }


    public void setComido() {
        comido = true;
    }

    public boolean isComido() {
        return comido;
    }
}
