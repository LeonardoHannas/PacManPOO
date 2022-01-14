package com.company.engine;

/**
 * Classe que representa a minima unidade do grafo (ou do tabuleiro de jogo).
 */
public class Vertice {

    private int numero; // Inteiro indicando o seu numero dentro da estrutura do grafo.
    private char c; // Char, o qual cada vertice armazena.
    private boolean comido; // Variavel booleana que indica se o vertice ja foi (ou nao) comido pelo Pac Man.

    private boolean frutaBonus; // Booleano que indica se o vertice possui uma Fruta Bonus.
    private boolean frutaBonusComida; // Se o vertice possui (ou ja possuiu) uma Fruta Bonus, este Booleano indica se tal fruta ja foi (ou nao) comida.


    /**
     * Construtor de um objeto da classe Vertice.
     * @param numero Numero o qual o vertice contruido tera.
     * @param c Char a ser armazenado pelo vertice.
     */
    public Vertice(int numero, char c) {
        this.numero = numero;
        this.c = c;
        comido = false;

        frutaBonus = false;
        frutaBonusComida = false;

    }



    /**
     * Verifica se o vertice em questao possui Fruta Bonus. Caso positivo, retorna 'true', senao, retorna 'false'.
     * @return boolean
     */
    public boolean hasFrutaBonus() {
        return frutaBonus;
    }

    /**
     * Dado um vertice que recebera uma Fruta Bonus, este metodo eh respnsavel por setar o booleano correspondente
     * para 'true'.
     */
    public void setFrutaBonus() { frutaBonus = true; }

    /**
     * Quando um vertice deixa de ter uma Fruta Bonus, sua variavel booleana recebe 'false'.
     */
    public void resetFrutaBonus() { frutaBonus = false; }

    /**
     * Verifica se a Fruta Bonus recebida por um vertice ja foi comida pelo Pac Man. Se sim, retona 'true'. Caso
     * contrario, retorna 'false'.
     * @return boolean
     */
    public boolean isFrutaBonusComida() {
        return frutaBonusComida;
    }

    /**
     * Quando o Pac Man come a Fruta Bonus de um vertice, entao a variavel 'frutaBonusComida' recebe 'true'.
     */
    public void setFrutaBonusComida() {
        frutaBonusComida = true;
    }

    public void resetFrutaBonusComida() {
        frutaBonusComida = false;
    }

    /**
     * Funcao que retorna o numero do vertice em questao.
     * @return int
     */
    public int getNumero() { return this.numero; }

    /**
     * Retorna o char armazenado pelo vertice.
     * @return char
     */
    public char getChar() {
        return this.c;
    }

    /**
     * Funcao responsavel por setar o numero de um vertice.
     * @param numero numero que o vertice recebera.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Funcao responsavel por setar o char aramzenado por um vertice.
     * @param c char a ser armazenado no vertice.
     */
    public void setChar(char c) {
        this.c = c;
    }

    /**
     * Quando o Pac Man visita um vertice que contem um Pac Dot ('.'), entao este Pac Dot foi comido.
     * Logo, a variavel booleana do vertice 'comido' recebe 'true'.
     */
    public void setComido() {
        comido = true;
    }

    public void resetComido() {
        comido = false;
    }

    /**
     * Verifica se o Pac Dot de um vertice ja foi comido. Retorna 'true', caso positivo, e 'false', caso negativo.
     * @return boolean
     */
    public boolean isComido() {
        return comido;
    }
}
