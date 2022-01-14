package com.company.engine;

/**
 * Classe para representar cada vertice da matriz auxiliar 'matrizAux' da classe Tabuleiro.
 */
public class VerticeAux {

    private boolean paredeNorte; // Variavel booleana para indicar a presenca de parede entre o vertice analisado e o vertice logo acima.
    private boolean paredeSul; // Variavel booleana para indicar a presenca de parede entre o vertice analisado e o vertice logo abaixo.
    private boolean paredeLeste; // Variavel booleana para indicar a presenca de parede entre o vertice analisado e o vertice a direita.
    private boolean paredeOeste; // Variavel booleana para indicar a presenca de parede entre o vertice analisado e o vertice a esquerda.

    private double posX; // Indica a coordenada X, onde tal vertice se localiza na interface grafica do jogo.
    private double posY; // Indica a coordenada Y, onde tal vertice se localiza na interface grafica do jogo.

    private int numero; // Numero do vertice correspondente no grafo do Tabuleiro.

    /**
     * Construtor para um vertice auxilar. Nele sao inicializados os atributos com seus respectivos valores padrao.
     */
    public VerticeAux() {

        paredeNorte = true;
        paredeSul = true;
        paredeLeste = true;
        paredeOeste = true;

        posX = 0.0;
        posY = 0.0;

        numero = -1;

    }

    /**
     * Metodo responsavel por setar a variavel booleana "paredeNorte" do vertice auxiliar.
     * @param paredeNorte
     */
    public void setParedeNorte(boolean paredeNorte) { this.paredeNorte = paredeNorte; }

    /**
     * Metodo responsavel por setar a variavel booleana "paredeSul" do vertice auxiliar.
     * @param paredeSul
     */
    public void setParedeSul(boolean paredeSul) { this.paredeSul = paredeSul; }

    /**
     * Metodo responsavel por setar a variavel booleana "paredeLeste" do vertice auxiliar.
     * @param paredeLeste
     */
    public void setParedeLeste(boolean paredeLeste) { this.paredeLeste = paredeLeste; }

    /**
     * Metodo responsavel por setar a variavel booleana "paredeOeste" do vertice auxiliar.
     * @param paredeOeste
     */
    public void setParedeOeste(boolean paredeOeste) { this.paredeOeste = paredeOeste; }

    /**
     * Metodo para verificar se tal vertice possui uma parede na direcao Norte. Retorna 'true' em caso positivo,
     * e 'false', caso contratio.
     * @return boolean
     */
    public boolean hasParedeNorte() { return paredeNorte; }

    /**
     * Metodo para verificar se tal vertice possui uma parede na direcao Sul. Retorna 'true' em caso positivo,
     * e 'false', caso contratio.
     * @return boolean
     */
    public boolean hasParedeSul() { return paredeSul; }

    /**
     * Metodo para verificar se tal vertice possui uma parede na direcao Leste. Retorna 'true' em caso positivo,
     * e 'false', caso contratio.
     * @return boolean
     */
    public boolean hasParedeLeste() { return paredeLeste; }

    /**
     * Metodo para verificar se tal vertice possui uma parede na direcao Oeste. Retorna 'true' em caso positivo,
     * e 'false', caso contratio.
     * @return boolean
     */
    public boolean hasParedeOeste() { return paredeOeste; }

    /**
     * Setar a coordenada X do vertice na tela da Interface Grafica.
     * @param posX
     */
    public void setPosX(double posX) { this.posX = posX; }

    /**
     * Setar a coordenada Y do vertice na tela da Interface Grafica.
     * @param posY
     */
    public void setPosY(double posY) { this.posY = posY; }

    /**
     * Metodo para se ter acesso a coordenada X do vertice na tela da Interface Grafica.
     * @return double
     */
    public double getPosX() { return posX; }

    /**
     * Metodo para se ter acesso a coordenada Y do vertice na tela da Interface Grafica.
     * @return double
     */
    public double getPosY() { return posY; }

    /**
     * Metodo para setar o atributo inteiro "numero" do Vertice Auxiliar.
     * @param numero Numero inteiro a ser setado
     */
    public void setNumero(int numero) { this.numero = numero; }


    /**
     * Metodo para se ter acesso ao atributo inteiro "numero" armazenado em cada Vertice Auxiliar.
     * @return int
     */
    public int getNumero() { return numero; }
}
