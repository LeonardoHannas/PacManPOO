package com.company.engine;

import com.company.elementosDoSistema.PacMan;
import com.company.elementosDoSistema.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Classe que representa o tabuleiro de jogo. Foi utilizado o conceito de grafo para a construcao desta classe.
 *
 */
public class Tabuleiro {

    private boolean ehDigrafo; // Booleano que indica que o grafo eh bidirecional.
    private int nroTotalVertices; // Numero total de vertices do tabuleiro.
    private int grauMax; // Numero maximo de ligacoes com outros vertices, partindo-se de um determinado vertice.
    private int[] grau; // vetor que indica quantas arestas cada vertice ja possui.
    private Vertice[][] arestas; // Matriz de vertices do grafo. A ligacao entre 2 vertices determina uma aresta.

    private VerticeAux[][] matrizAux;

    private int pontuacao; // Variavel que armazena a pontuacao atual do PacMan.
    private int nivel; // Variavel para armazenar o atual nivel de jogo.

    /**
     * Construtor padrao do tabuleiro de jogo. Nele, sao inicializados os atributos da classe, como a inicializacao do
     * grafo de jogo, que contem cada um dos 288 vertices.
     * @param nroTotalVertices numero total de vertices do tabuleiro.
     * @param grauMax Numero maximo de ligacoes com outros vertices, partindo-se de um determinado vertice.
     */
    public Tabuleiro(int nroTotalVertices, int grauMax) {

        nivel = 1;
        grauMax = 4;
        pontuacao = 0;
        ehDigrafo = false;
        this.nroTotalVertices = nroTotalVertices;

        this.grauMax = grauMax;
        grau = new int[nroTotalVertices];
        for (int i = 0; i < nroTotalVertices; i++) grau[i] = 0;

        arestas = new Vertice[nroTotalVertices][grauMax];
        for (int i = 0; i < nroTotalVertices; i++) {
            arestas[i][0] = new Vertice(i, '.');
            for (int j = 1; j < grauMax; j++) {
                arestas[i][j] = new Vertice(-1, '.');
            }
        }

        for (int i = 0; i < arestas.length; i++) {

            switch (i) {
                case 47:
                case 62:
                case 169:
                case 182:
                    arestas[i][0].setChar('*');
                    break;
                default:
                    arestas[i][0].setChar('.');
                    break;
            }
        }

        matrizAux = new VerticeAux[31][28];

        carregarMatrizAux("src/com/company/engine/tabuleiro.txt");
    }

    /**
     * Funcao para reiniciar as confuguracoes do tabuleiro, apos o aumento do nivel do jogo.
     */
    public void restartTabuleiro() {

        for (int i = 0; i < nroTotalVertices; i++) {

            arestas[i][0].setNumero(i);
            arestas[i][0].resetComido();
            arestas[i][0].resetFrutaBonus();
            arestas[i][0].resetFrutaBonusComida();

        }

        for (int i = 0; i < arestas.length; i++) {

            switch (i) {
                case 47:
                case 62:
                case 169:
                case 182:
                    arestas[i][0].setChar('*');
                    break;
                default:
                    arestas[i][0].setChar('.');
                    break;
            }
        }

    }

    /**
     * Metodo que da acesso a matriz auxiliar "matrizAux", carregada na Memoria RAM do computador, apos a leitura
     * do arquivo "tabuleiro.txt".
     * @return VerticeAux[][]
     */
    public VerticeAux[][] getMatrizAux() {
        return matrizAux;
    }

    /**
     * Atualiza as paredes do vertice da posicao (i,j) na matriz auxiliar, bem como faz atualizacao nas paredes
     * dos vertices adjacentes.
     * @param i Numero da linha na matriz auxiliar
     * @param j Numero da coluna na matriz auxiliar.
     */
    private void atualizaParedesMatrixAux(int i, int j) {

        VerticeAux vAux = matrizAux[i][j];

        if (vAux != null) {

            if (i >= 1) {
                VerticeAux vAux1 = matrizAux[i - 1][j];
                if (vAux1 != null) {
                    vAux.setParedeNorte(false);
                    vAux1.setParedeSul(false);
                }
            }

            if (j >= 1) {
                VerticeAux vAux2 = matrizAux[i][j - 1];
                if (vAux2 != null) {
                    vAux.setParedeOeste(false);
                    vAux2.setParedeLeste(false);
                }
            }
        }
    }

    /**
     * Faz a leitura do arquivo "tabuleiro..txt" e faz as insercoes das devidas paredes entre os vertices,
     * a fum de que se possa amalisar se o movimento dada pelo ususario ao Pac Man eh permitido.
     * @param path Localizacao do arquivo "tabuleiro.txt" a ser lido por este metodo.
     */
    public void carregarMatrizAux(String path) {

        try {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int i = 0, j = 0;

            int numVerticeAux = 0;
            while (line != null) {

                for (j = 0; j < 28; j++) {

                    switch (line.charAt(j)) {

                        case '+':
                        case '|':
                        case '-':
                        case ' ':
                        case '\n':
                            matrizAux[i][j] = null;
                            break;
                        default:
                            matrizAux[i][j] = new VerticeAux();
                            atualizaParedesMatrixAux(i, j);
                            matrizAux[i][j].setPosX(20*i);
                            matrizAux[i][j].setPosY(20*j);
                            matrizAux[i][j].setNumero(numVerticeAux);
                            numVerticeAux++;
                            break;
                    }
                }
                line = br.readLine();
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Retorna o atual nivel do jogo.
     * @return int
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Atualiza o atual nivel de jogo.
     * @param nivel: numero inteiro contendo o atual nivel a ser setado.
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Aumenta o nivel de jogo, assim que o Pac Man come a quantidade minima de Paa Dots e de Pilulas de Poder.
     * @param t Tabuleiro de jogo
     * @param pm Pac Man
     * @param f1 Fantasma Blinky
     * @param f2 Fantasma Pinky
     * @param f3 Fantasma Inky
     * @param f4 Fantasma Clyde
     */
    public void nivelUp(Tabuleiro t, PacMan pm, Blinky f1, Pinky f2, Inky f3, Clyde f4) {

           restartTabuleiro();
           pm.restartPacMan(t);
           f1.restartBlinky();
           f2.restartPinky();
           f3.restartInky();
           f4.restartClyde();
           setNivel(getNivel() + 1);

    }

    /**
     * Checa se as condicoes minimas para evoluir de nivel foram satisfeitas. Retorna 'true', caso tais condicoes
     * forem satisfeitas, e 'false', caso contrario.
     * @param pm Pac Man
     * @return boolean
     */
    public boolean checkNivelUp(PacMan pm) {

        if (pm.getNumPacDotsComidos() == 240 && getArestas()[47][0].isComido() && getArestas()[62][0].isComido() &&
                getArestas()[169][0].isComido() && getArestas()[182][0].isComido()) return true;
        else return false;
    }

    /**
     * Retorna a atual pontuacao do Pac Man.
     * @return int
     */
    public int getPontuacao() {
        return pontuacao;
    }

    /**
     * Metodo para atualizar a pontuacao do Pac Man.
     * @param x: nova potuacao a ser setada.
     */
    public void setPontuacao(int x) {
        pontuacao = x;
    }

    /**
     * Retorna a matriz de nome "arestas", que contem os vertices do grafo do tabuleiro.
     * @return Vertice[][]
     */
    public Vertice[][] getArestas() {
        return arestas;
    }

    /**
     * Verifica se o grafo eh uni ou bidirecional. Esse metodo eh utilizado na insercao de uma nova aresta no grafo.
     * @return boolean
     */
    public boolean getEhDigrafo() {
        return this.ehDigrafo;
    }

    /**
     * Executa a insercao de uma nova aresta no grafo.
     * @param nroVerticeOrigem numero do vertice de origem.
     * @param nroVerticeDestino numero do vertice de destino.
     * @param ehDigrafo variavel booleana para controlar a insercao recursiva de uma aresta.
     */
    public void insereAresta(int nroVerticeOrigem, int nroVerticeDestino, boolean ehDigrafo)  {

        if (nroVerticeOrigem < 0 || nroVerticeOrigem >= nroTotalVertices) return;
        if (nroVerticeDestino < 0 || nroVerticeDestino >= nroTotalVertices) return;

        arestas[nroVerticeOrigem][grau[nroVerticeOrigem]].setNumero(nroVerticeDestino);
        grau[nroVerticeOrigem]++;
        if (!ehDigrafo) insereAresta(nroVerticeDestino, nroVerticeOrigem, true);

    }

    /**
     * Calcula o menor caminho entre 2 vertices do grafo. A sequencia de vertices a serem percorridos eh armazenada
     * num vetor de numeros inteiros Tais numeros representam o numero do cada vertice do tabuleiro.
     * @param nroVerticeOrigem Numero do vertice de origem.
     * @param nroVerticeDestino Numero do vertice de origem.
     * @return int[]
     */
    public int[] menorCaminho(int nroVerticeOrigem, int nroVerticeDestino) {
        int[] visitado = new int[nroTotalVertices];
        int[] ant = new int[nroTotalVertices];
        float[] dist = new float[nroTotalVertices];

        int i, cont, u, ind;
        cont = nroTotalVertices;

        for (i = 0; i < nroTotalVertices; i++) {
            ant[i] = - 1;
            dist[i] = - 1;
            visitado[i] = 0;
        }

        dist[nroVerticeOrigem] = 0;

        while (cont > 0) {
            u = procuraMenorDistancia(dist, visitado, nroTotalVertices);
            if (u == - 1) break;
            visitado[u] = 1;
            cont--;

            for (i = 0; i < this.grau[u]; i++) {
                ind = this.arestas[u][i].getNumero();
                if (dist[ind] < 0) {
                    dist[ind] = dist[u] + 1;
                    ant[ind] = u;
                } else {
                    if (dist[ind] > dist[u] + 1) {
                        dist[ind] = dist[u] + 1;
                        ant[ind] = u;
                    }
                }
            }
        }

        return ant;

    }

    /**
     * Funcao auxiliar utilizada no calculo do menor caminho entre dois vertices.
     * @param dist vetor de distancias.
     * @param visitado vetor que controla os vertices visitados para se fazer o calculo.
     * @param nroTotalVertices numero total de vertices do grafo.
     * @return int
     */
    public int procuraMenorDistancia(float[] dist, int[] visitado, int nroTotalVertices) {
        int i, menor = - 1, primeiro = 1;
        for (i = 0; i < nroTotalVertices; i++) {
            if (dist[i] >= 0 && visitado[i] == 0) {
                if (primeiro != 0) {
                    menor = i;
                    primeiro = 0;
                } else {
                    if (dist[menor] > dist[i]) menor = i;
                }
            }
        }
        return menor;
    }

    /**
     * Faz todas as ligacoes entre os vertices para se montar o tabuleiro. Cada ligacao entre vertices representa uma
     * aresta, a qual eh sempre bidirecional.
     * @param t Tabuleiro de jogo.
     */
    public void montaTabuleiro(Tabuleiro t)  {
        int i;

        // Linha 1
        for (i = 0; i < 11; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 12; i < 23; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Linha 2
        for (i = 42; i < 67; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Linha 3
        for (i = 80; i < 85; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 86; i < 89; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 90; i < 93; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 94; i < 99; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Linha 4
        for (i = 109; i < 118; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Linha 5
        for (i = 128; i < 131; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 132; i < 135; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Linha 6
        for (i = 145; i < 154; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Linha 7
        for (i = 164; i < 175; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 176; i < 187; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Linha 8
        for (i = 200; i < 202; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 203; i < 218; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 219; i < 221; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Linha 9
        for (i = 234; i < 239; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 240; i < 243; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 244; i < 247; i++) t.insereAresta(i, i+1, t.getEhDigrafo());
        for (i = 248; i < 253; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Linha 10
        for (i = 262; i < 287; i++) t.insereAresta(i, i+1, t.getEhDigrafo());

        // Coluna 1
        t.insereAresta(0, 24, t.getEhDigrafo());
        for (i = 24; i <= 36; i += 6) t.insereAresta(i, i+6, t.getEhDigrafo());
        t.insereAresta(42,68, t.getEhDigrafo());
        for (i = 68; i <= 74; i += 6) t.insereAresta(i, i+6, t.getEhDigrafo());
        t.insereAresta(164,188, t.getEhDigrafo());
        for (i = 188; i <= 194; i += 6) t.insereAresta(i, i+6, t.getEhDigrafo());
        t.insereAresta(234, 254, t.getEhDigrafo());
        for (i = 254; i <= 258; i += 4) t.insereAresta(i, i+4, t.getEhDigrafo());

        // Coluna 2
        t.insereAresta(202, 222, t.getEhDigrafo());
        t.insereAresta(222, 228, t.getEhDigrafo());
        t.insereAresta(228, 236, t.getEhDigrafo());


        // Coluna 3
        t.insereAresta(5, 25, t.getEhDigrafo());
        t.insereAresta(25, 31, t.getEhDigrafo());
        t.insereAresta(31, 37, t.getEhDigrafo());
        t.insereAresta(37, 47, t.getEhDigrafo());
        t.insereAresta(47, 69, t.getEhDigrafo());
        t.insereAresta(69, 75, t.getEhDigrafo());
        t.insereAresta(75, 85, t.getEhDigrafo());
        t.insereAresta(85, 100, t.getEhDigrafo());
        for (i = 100; i <= 104; i += 4) t.insereAresta(i, i + 4, t.getEhDigrafo());
        t.insereAresta(108, 120, t.getEhDigrafo());
        for (i = 120; i <= 124; i += 4) t.insereAresta(i, i + 4, t.getEhDigrafo());
        t.insereAresta(128, 136, t.getEhDigrafo());
        for (i = 136; i <= 140; i += 4) t.insereAresta(i, i + 4, t.getEhDigrafo());
        t.insereAresta(144, 156, t.getEhDigrafo());
        t.insereAresta(156, 160, t.getEhDigrafo());
        t.insereAresta(160, 169, t.getEhDigrafo());
        t.insereAresta(169, 189, t.getEhDigrafo());
        t.insereAresta(189, 195, t.getEhDigrafo());
        t.insereAresta(195, 203, t.getEhDigrafo());
        t.insereAresta(203, 223, t.getEhDigrafo());
        t.insereAresta(223, 229, t.getEhDigrafo());
        t.insereAresta(229, 239, t.getEhDigrafo());

        // Coluna 4
        t.insereAresta(50, 70, t.getEhDigrafo());
        t.insereAresta(70, 76, t.getEhDigrafo());
        t.insereAresta(76, 86, t.getEhDigrafo());
        t.insereAresta(109, 121, t.getEhDigrafo());
        t.insereAresta(121, 125, t.getEhDigrafo());
        t.insereAresta(125, 131, t.getEhDigrafo());
        t.insereAresta(131, 137, t.getEhDigrafo());
        t.insereAresta(137, 141, t.getEhDigrafo());
        t.insereAresta(141, 145, t.getEhDigrafo());
        t.insereAresta(145, 157, t.getEhDigrafo());
        t.insereAresta(157, 161, t.getEhDigrafo());
        t.insereAresta(161, 172, t.getEhDigrafo());
        t.insereAresta(206, 224, t.getEhDigrafo());
        t.insereAresta(224, 230, t.getEhDigrafo());
        t.insereAresta(230, 240, t.getEhDigrafo());

        // Coluna 5
        t.insereAresta(11, 26, t.getEhDigrafo());
        t.insereAresta(26, 32, t.getEhDigrafo());
        t.insereAresta(32, 38, t.getEhDigrafo());
        t.insereAresta(38, 53, t.getEhDigrafo());
        t.insereAresta(89, 101, t.getEhDigrafo());
        t.insereAresta(101, 105, t.getEhDigrafo());
        t.insereAresta(105, 112, t.getEhDigrafo());
        t.insereAresta(175, 190, t.getEhDigrafo());
        t.insereAresta(190, 196, t.getEhDigrafo());
        t.insereAresta(196, 209, t.getEhDigrafo());
        t.insereAresta(243, 255, t.getEhDigrafo());
        t.insereAresta(255, 259, t.getEhDigrafo());
        t.insereAresta(259, 273, t.getEhDigrafo());

        // Coluna 6
        t.insereAresta(12, 27, t.getEhDigrafo());
        t.insereAresta(27, 33, t.getEhDigrafo());
        t.insereAresta(33, 39, t.getEhDigrafo());
        t.insereAresta(39, 56, t.getEhDigrafo());

        t.insereAresta(90, 102, t.getEhDigrafo());
        t.insereAresta(102, 106, t.getEhDigrafo());
        t.insereAresta(106, 115, t.getEhDigrafo());

        t.insereAresta(176, 191, t.getEhDigrafo());
        t.insereAresta(191, 197, t.getEhDigrafo());
        t.insereAresta(197, 212, t.getEhDigrafo());

        t.insereAresta(244, 256, t.getEhDigrafo());
        t.insereAresta(256, 260, t.getEhDigrafo());
        t.insereAresta(260, 276, t.getEhDigrafo());

        // Coluna 7
        t.insereAresta(59, 71, t.getEhDigrafo());
        t.insereAresta(71, 77, t.getEhDigrafo());
        t.insereAresta(77, 93, t.getEhDigrafo());

        t.insereAresta(118, 122, t.getEhDigrafo());
        t.insereAresta(122, 126, t.getEhDigrafo());
        t.insereAresta(126, 132, t.getEhDigrafo());

        t.insereAresta(132, 138, t.getEhDigrafo());
        t.insereAresta(138, 142, t.getEhDigrafo());
        t.insereAresta(142, 154, t.getEhDigrafo());
        t.insereAresta(154, 158, t.getEhDigrafo());
        t.insereAresta(158, 162, t.getEhDigrafo());
        t.insereAresta(162, 179, t.getEhDigrafo());

        t.insereAresta(215, 225, t.getEhDigrafo());
        t.insereAresta(225, 231, t.getEhDigrafo());
        t.insereAresta(231, 247, t.getEhDigrafo());

        // Coluna 8
        t.insereAresta(18, 28, t.getEhDigrafo());
        t.insereAresta(28, 34, t.getEhDigrafo());
        t.insereAresta(34, 40, t.getEhDigrafo());
        t.insereAresta(40, 62, t.getEhDigrafo());

        t.insereAresta(62, 72, t.getEhDigrafo());
        t.insereAresta(72, 78, t.getEhDigrafo());
        t.insereAresta(78, 94, t.getEhDigrafo());

        t.insereAresta(94, 103, t.getEhDigrafo());
        t.insereAresta(103, 107, t.getEhDigrafo());
        t.insereAresta(107, 119, t.getEhDigrafo());
        t.insereAresta(119, 123, t.getEhDigrafo());
        t.insereAresta(123, 127, t.getEhDigrafo());
        t.insereAresta(127, 135, t.getEhDigrafo());

        t.insereAresta(135, 139, t.getEhDigrafo());
        t.insereAresta(139, 143, t.getEhDigrafo());
        t.insereAresta(143, 155, t.getEhDigrafo());
        t.insereAresta(155, 159, t.getEhDigrafo());
        t.insereAresta(159, 163, t.getEhDigrafo());
        t.insereAresta(163, 182, t.getEhDigrafo());

        t.insereAresta(182, 192, t.getEhDigrafo());
        t.insereAresta(192, 198, t.getEhDigrafo());
        t.insereAresta(198, 218, t.getEhDigrafo());

        t.insereAresta(218, 226, t.getEhDigrafo());
        t.insereAresta(226, 232, t.getEhDigrafo());
        t.insereAresta(232, 248, t.getEhDigrafo());

        // Coluna 9
        t.insereAresta(219, 227, t.getEhDigrafo());
        t.insereAresta(227, 233, t.getEhDigrafo());
        t.insereAresta(233, 251, t.getEhDigrafo());

        // Coluna 10
        t.insereAresta(23, 29, t.getEhDigrafo());
        t.insereAresta(29, 35, t.getEhDigrafo());
        t.insereAresta(35, 41, t.getEhDigrafo());
        t.insereAresta(41, 67, t.getEhDigrafo());

        t.insereAresta(67, 73, t.getEhDigrafo());
        t.insereAresta(73, 79, t.getEhDigrafo());
        t.insereAresta(79, 99, t.getEhDigrafo());

        t.insereAresta(187, 193, t.getEhDigrafo());
        t.insereAresta(193, 199, t.getEhDigrafo());
        t.insereAresta(199, 221, t.getEhDigrafo());

        t.insereAresta(253, 257, t.getEhDigrafo());
        t.insereAresta(257, 261, t.getEhDigrafo());
        t.insereAresta(261, 287, t.getEhDigrafo());
    }

    /**
     * Imprime o tabuleiro de jogo. Para isso, percorre toda a matriz de vertices de nome "arestas". Eh feita uma
     * verificacao do status de cada vertice para saber qual caractere sera impresso na sua posicao. A seguir, eh feita
     * a verificacao de quais persongens estao vivos. Todos que estiverem vivos possuem seus respectivos caracteres
     * printados em suas posicoes.
     * @param t Tabuleiro de jogo.
     * @param pm Pac Man.
     * @param f1 Fantasma Blinky.
     * @param f2 Fantasma Pinky.
     * @param f3 Fantasma Inky.
     * @param f4 Fantasma Clyde.
     */
    public void imprimeTabuleiro(Tabuleiro t, PacMan pm, Blinky f1, Pinky f2, Inky f3, Clyde f4) {

        for (int i = 0; i < t.arestas.length; i++) {
            if (!arestas[i][0].isComido()) { // Vertice nao comido
                switch (i) {
                    case 47:
                    case 62:
                    case 169:
                    case 182:
                        arestas[i][0].setChar('*'); // Pilulas de Poder
                        break;
                    default:
                        arestas[i][0].setChar('.'); // Pac Dots
                        break;

                }
            } else { // Vertice ja comido
                if (!arestas[i][0].hasFrutaBonus()) arestas[i][0].setChar(' '); // Nao tem fruta bonus e esta comido
                else { // Possui fruta bonus
                    if (arestas[i][0].isFrutaBonusComida()) arestas[i][0].setChar(' ');
                    else arestas[i][0].setChar('!');
                }
            }
        }

        int posPacMan, posBlinky, posPinky, posInky, posClyde;

        if (pm.getPilulaDePoder()) {

            if (!f1.isMorto()) {
                posBlinky = f1.getNroVerticeAtual();
                t.getArestas()[posBlinky][0].setChar('A');
            }

            if (!f2.isMorto()) {
                posPinky = f2.getNroVerticeAtual();
                t.getArestas()[posPinky][0].setChar('B');
            }

            if (!f3.isMorto()) {
                posInky = f3.getNroVerticeAtual();
                t.getArestas()[posInky][0].setChar('C');
            }

            if (!f4.isMorto()) {
                posClyde = f4.getNroVerticeAtual();
                t.getArestas()[posClyde][0].setChar('D');
            }
            if (!pm.isMorto()) {
                posPacMan = pm.getPosicaoAtual();
                t.getArestas()[posPacMan][0].setChar('P');
            }


        } else {

            if (!pm.isMorto()) {
                posPacMan = pm.getPosicaoAtual();
                t.getArestas()[posPacMan][0].setChar('P');
            }

            if (!f1.isMorto()) {
                posBlinky = f1.getNroVerticeAtual();
                t.getArestas()[posBlinky][0].setChar('A');
            }

            if (!f2.isMorto()) {
                posPinky = f2.getNroVerticeAtual();
                t.getArestas()[posPinky][0].setChar('B');
            }

            if (!f3.isMorto()) {
                posInky = f3.getNroVerticeAtual();
                t.getArestas()[posInky][0].setChar('C');
            }

            if (!f4.isMorto()) {
                posClyde = f4.getNroVerticeAtual();
                t.getArestas()[posClyde][0].setChar('D');
            }
        }


        System.out.println("+-------------------------+ +-------------------------+");
        System.out.println("| " + arestas[0][0].getChar() + " " + arestas[1][0].getChar() + " " + arestas[2][0].getChar() + " " + arestas[3][0].getChar() + " " + arestas[4][0].getChar() + " " + arestas[5][0].getChar() + " " + arestas[6][0].getChar() + " " + arestas[7][0].getChar() + " " + arestas[8][0].getChar() + " " + arestas[9][0].getChar() + " " + arestas[10][0].getChar() + " " + arestas[11][0].getChar() + " | " + "| " + arestas[12][0].getChar() + " " + arestas[13][0].getChar() + " " + arestas[14][0].getChar() + " " + arestas[15][0].getChar() + " " + arestas[16][0].getChar() + " " + arestas[17][0].getChar() + " " + arestas[18][0].getChar() + " " + arestas[19][0].getChar() + " " + arestas[20][0].getChar() + " " + arestas[21][0].getChar() + " " + arestas[22][0].getChar() + " " + arestas[23][0].getChar() + " |");
        System.out.println("| " + arestas[24][0].getChar() + " +-----+ " + arestas[25][0].getChar() + " +-------+ " + arestas[26][0].getChar() + " | | " + arestas[27][0].getChar() + " +-------+ " + arestas[28][0].getChar() + " +-----+ " + arestas[29][0].getChar() + " |");
        System.out.println("| " + arestas[30][0].getChar() + " |     | " + arestas[31][0].getChar() + " |       | " + arestas[32][0].getChar() + " | | " + arestas[33][0].getChar() + " |       | " + arestas[34][0].getChar() + " |     | " + arestas[35][0].getChar() + " |");
        System.out.println("| " + arestas[36][0].getChar() + " +-----+ " + arestas[37][0].getChar() + " +-------+ " + arestas[38][0].getChar() + " +-+ " + arestas[39][0].getChar() + " +-------+ " + arestas[40][0].getChar() + " +-----+ " + arestas[41][0].getChar() + " |");
        System.out.println("| " + arestas[42][0].getChar() + " " + arestas[43][0].getChar() + " " + arestas[44][0].getChar() + " " + arestas[45][0].getChar() + " " + arestas[46][0].getChar() + " " + arestas[47][0].getChar() + " " + arestas[48][0].getChar() + " " + arestas[49][0].getChar() + " " + arestas[50][0].getChar() + " " + arestas[51][0].getChar() + " " + arestas[52][0].getChar() + " " + arestas[53][0].getChar() + " " + arestas[54][0].getChar() + " " + arestas[55][0].getChar() + " " + arestas[56][0].getChar() + " " + arestas[57][0].getChar() + " " + arestas[58][0].getChar() + " " + arestas[59][0].getChar() + " " + arestas[60][0].getChar() + " " + arestas[61][0].getChar() + " " + arestas[62][0].getChar() + " " + arestas[63][0].getChar() + " " + arestas[64][0].getChar() + " " + arestas[65][0].getChar() + " " + arestas[66][0].getChar() + " " + arestas[67][0].getChar() + " |");
        System.out.println("| " + arestas[68][0].getChar() + " +-----+ " + arestas[69][0].getChar() + " +-+ " + arestas[70][0].getChar() + " +-------------+ " + arestas[71][0].getChar() + " +-+ " + arestas[72][0].getChar() + " +-----+ " + arestas[73][0].getChar() + " |");
        System.out.println("| " + arestas[74][0].getChar() + " +-----+ " + arestas[75][0].getChar() + " | | " + arestas[76][0].getChar() + " +-----+ +-----+ " + arestas[77][0].getChar() + " | | " + arestas[78][0].getChar() + " +-----+ " + arestas[79][0].getChar() + " |");
        System.out.println("| " + arestas[80][0].getChar() + " " + arestas[81][0].getChar() + " " + arestas[82][0].getChar() + " " + arestas[83][0].getChar() + " " + arestas[84][0].getChar() + " " + arestas[85][0].getChar() + " | | " + arestas[86][0].getChar() + " " + arestas[87][0].getChar() + " " + arestas[88][0].getChar() + " " + arestas[89][0].getChar() + " | | " + arestas[90][0].getChar() + " " + arestas[91][0].getChar() + " " + arestas[92][0].getChar() + " " + arestas[93][0].getChar() + " | | " + arestas[94][0].getChar() + " " + arestas[95][0].getChar() + " " + arestas[96][0].getChar() + " " + arestas[97][0].getChar() + " " + arestas[98][0].getChar() + " " + arestas[99][0].getChar() + " |");
        System.out.println("+---------+ " + arestas[100][0].getChar() + " | +-----+ " + arestas[101][0].getChar() + " | | " + arestas[102][0].getChar() + " +-----+ | " + arestas[103][0].getChar() + " +---------+");
        System.out.println("          | " + arestas[104][0].getChar() + " | +-----+ " + arestas[105][0].getChar() + " +-+ " + arestas[106][0].getChar() + " +-----+ | " + arestas[107][0].getChar() + " |          ");
        System.out.println("          | " + arestas[108][0].getChar() + " | | " + arestas[109][0].getChar() + " " + arestas[110][0].getChar() + " " + arestas[111][0].getChar() + " " + arestas[112][0].getChar() + " " + arestas[113][0].getChar() + " " + arestas[114][0].getChar() + " " + arestas[115][0].getChar() + " " + arestas[116][0].getChar() + " " + arestas[117][0].getChar() + " " + arestas[118][0].getChar() + " | | " + arestas[119][0].getChar() + " |          ");
        System.out.println("          | " + arestas[120][0].getChar() + " | | " + arestas[121][0].getChar() + " +-------------+ " + arestas[122][0].getChar() + " | | " + arestas[123][0].getChar() + " |          ");
        System.out.println("          | " + arestas[124][0].getChar() + " +-+ " + arestas[125][0].getChar() + " |             | " + arestas[126][0].getChar() + " +-+ " + arestas[127][0].getChar() + " |          ");
        System.out.println("          | " + arestas[128][0].getChar() + " " + arestas[129][0].getChar() + " " + arestas[130][0].getChar() + " " + arestas[131][0].getChar() + " |             | " + arestas[132][0].getChar() + " " + arestas[133][0].getChar() + " " + arestas[134][0].getChar() + " " + arestas[135][0].getChar() + " |          ");
        System.out.println("          | " + arestas[136][0].getChar() + " +-+ " + arestas[137][0].getChar() + " |             | " + arestas[138][0].getChar() + " +-+ " + arestas[139][0].getChar() + " |          ");
        System.out.println("          | " + arestas[140][0].getChar() + " | | " + arestas[141][0].getChar() + " +-------------+ " + arestas[142][0].getChar() + " | | " + arestas[143][0].getChar() + " |          ");
        System.out.println("          | " + arestas[144][0].getChar() + " | | " + arestas[145][0].getChar() + " " + arestas[146][0].getChar() + " " + arestas[147][0].getChar() + " " + arestas[148][0].getChar() + " " + arestas[149][0].getChar() + " " + arestas[150][0].getChar() + " " + arestas[151][0].getChar() + " " + arestas[152][0].getChar() + " " + arestas[153][0].getChar() + " " + arestas[154][0].getChar() + " | | " + arestas[155][0].getChar() + " |          ");
        System.out.println("          | " + arestas[156][0].getChar() + " | | " + arestas[157][0].getChar() + " +-------------+ " + arestas[158][0].getChar() + " | | " + arestas[159][0].getChar() + " |          ");
        System.out.println("+---------+ " + arestas[160][0].getChar() + " +-+ " + arestas[161][0].getChar() + " +-----+ +-----+ " + arestas[162][0].getChar() + " +-+ " + arestas[163][0].getChar() + " +---------+");
        System.out.println("| " + arestas[164][0].getChar() + " " + arestas[165][0].getChar() + " " + arestas[166][0].getChar() + " " + arestas[167][0].getChar() + " " + arestas[168][0].getChar() + " " + arestas[169][0].getChar() + " " + arestas[170][0].getChar() + " " + arestas[171][0].getChar() + " " + arestas[172][0].getChar() + " " + arestas[173][0].getChar() + " " + arestas[174][0].getChar() + " " + arestas[175][0].getChar() + " | | " + arestas[176][0].getChar() + " " + arestas[177][0].getChar() + " " + arestas[178][0].getChar() + " " + arestas[179][0].getChar() + " " + arestas[180][0].getChar() + " " + arestas[181][0].getChar() + " " + arestas[182][0].getChar() + " " + arestas[183][0].getChar() + " " + arestas[184][0].getChar() + " " + arestas[185][0].getChar() + " " + arestas[186][0].getChar() + " " + arestas[187][0].getChar() + " |");
        System.out.println("| " + arestas[188][0].getChar() + " +-----+ " + arestas[189][0].getChar() + " +-------+ " + arestas[190][0].getChar() + " | | " + arestas[191][0].getChar() + " +-------+ " + arestas[192][0].getChar() + " +-----+ " + arestas[193][0].getChar() + " |");
        System.out.println("| " + arestas[194][0].getChar() + " +---+ | " + arestas[195][0].getChar() + " +-------+ " + arestas[196][0].getChar() + " +-+ " + arestas[197][0].getChar() + " +-------+ " + arestas[198][0].getChar() + " | +---+ " + arestas[199][0].getChar() + " |");
        System.out.println("| " + arestas[200][0].getChar() + " " + arestas[201][0].getChar() + " " + arestas[202][0].getChar() + " | | " + arestas[203][0].getChar() + " " + arestas[204][0].getChar() + " " + arestas[205][0].getChar() + " " + arestas[206][0].getChar() + " " + arestas[207][0].getChar() + " " + arestas[208][0].getChar() + " " + arestas[209][0].getChar() + " " + arestas[210][0].getChar() + " " + arestas[211][0].getChar() + " " + arestas[212][0].getChar() + " " + arestas[213][0].getChar() + " " + arestas[214][0].getChar() + " " + arestas[215][0].getChar() + " " + arestas[216][0].getChar() + " " + arestas[217][0].getChar() + " " + arestas[218][0].getChar() + " | | " + arestas[219][0].getChar() + " " + arestas[220][0].getChar() + " " + arestas[221][0].getChar() + " |");
        System.out.println("+---+ " + arestas[222][0].getChar() + " | | " + arestas[223][0].getChar() + " +-+ " + arestas[224][0].getChar() + " +-------------+ " + arestas[225][0].getChar() + " +-+ " + arestas[226][0].getChar() + " | | " + arestas[227][0].getChar() + " +---+");
        System.out.println("+---+ " + arestas[228][0].getChar() + " +-+ " + arestas[229][0].getChar() + " | | " + arestas[230][0].getChar() + " +-----+ +-----+ " + arestas[231][0].getChar() + " | | " + arestas[232][0].getChar() + " +-+ " + arestas[233][0].getChar() + " +---+");
        System.out.println("| " + arestas[234][0].getChar() + " " + arestas[235][0].getChar() + " " + arestas[236][0].getChar() + " " + arestas[237][0].getChar() + " " + arestas[238][0].getChar() + " " + arestas[239][0].getChar() + " | | " + arestas[240][0].getChar() + " " + arestas[241][0].getChar() + " " + arestas[242][0].getChar() + " " + arestas[243][0].getChar() + " | | " + arestas[244][0].getChar() + " " + arestas[245][0].getChar() + " " + arestas[246][0].getChar() + " " + arestas[247][0].getChar() + " | | " + arestas[248][0].getChar() + " " + arestas[249][0].getChar() + " " + arestas[250][0].getChar() + " " + arestas[251][0].getChar() + " " + arestas[252][0].getChar() + " " + arestas[253][0].getChar() + " |");
        System.out.println("| " + arestas[254][0].getChar() + " +---------+ +-----+ " + arestas[255][0].getChar() + " | | " + arestas[256][0].getChar() + " +-----+ +---------+ " + arestas[257][0].getChar() + " |");
        System.out.println("| " + arestas[258][0].getChar() + " +-----------------+ " + arestas[259][0].getChar() + " +-+ " + arestas[260][0].getChar() + " +-----------------+ " + arestas[261][0].getChar() + " |");
        System.out.println("| " + arestas[262][0].getChar() + " " + arestas[263][0].getChar() + " " + arestas[264][0].getChar() + " " + arestas[265][0].getChar() + " " + arestas[266][0].getChar() + " " + arestas[267][0].getChar() + " " + arestas[268][0].getChar() + " " + arestas[269][0].getChar() + " " + arestas[270][0].getChar() + " " + arestas[271][0].getChar() + " " + arestas[272][0].getChar() + " " + arestas[273][0].getChar() + " " + arestas[274][0].getChar() + " " + arestas[275][0].getChar() + " " + arestas[276][0].getChar() + " " + arestas[277][0].getChar() + " " + arestas[278][0].getChar() + " " + arestas[279][0].getChar() + " " + arestas[280][0].getChar() + " " + arestas[281][0].getChar() + " " + arestas[282][0].getChar() + " " + arestas[283][0].getChar() + " " + arestas[284][0].getChar() + " " + arestas[285][0].getChar() + " " + arestas[286][0].getChar() + " " + arestas[287][0].getChar() + " |");
        System.out.println("+-----------------------------------------------------+");

    }

    /**
     * Atualiza os vertices percorridos pelo Pac Man, setando a variavel booleana de cada vertice visitado para
     * "comido = true".
     * @param pm Pac Man
     */
    public void atualizaTabuleiro(PacMan pm) {

        for (int i = 0; i < pm.getVerticesPercorridos().size(); i++)
            arestas[pm.getVerticesPercorridos().get(i)][0].setComido(); // vertices por onde o pac man passou recebem comido = true

        for (int i = 0; i < arestas.length; i++) {
            if (!arestas[i][0].isComido()) { // Vertice nao comido
                switch (i) {
                    case 47:
                    case 62:
                    case 169:
                    case 182:
                        arestas[i][0].setChar('*'); // Pilulas de Poder
                        break;
                    default:
                        arestas[i][0].setChar('.'); // Pac Dots
                        break;

                }
            } else { // Vertice ja comido
                if (!arestas[i][0].hasFrutaBonus()) arestas[i][0].setChar(' '); // Nao tem fruta bonus e esta comido
                else { // Possui fruta bonus
                    if (arestas[i][0].isFrutaBonusComida()) arestas[i][0].setChar(' ');
                    else arestas[i][0].setChar('!');
                }
            }
        }

    }

    /**
     * Apos cada movimento do Pac Man, esta funcoa eh utilizada para se atualizar sua pontuacao. Esta funcao ja verifica
     * se o vertice atual possui ou nao fruta bonus, bem como pilula de poder, alem de fazer os devidos calculos.
     * @param t Tabuleiro de jogo.
     * @param pm Pac Man.
     */
    public void contabilizaPontuacao(Tabuleiro t, PacMan pm) {

        int pont = t.getPontuacao();

        if (pont >= 10000 && !pm.getGanhouVidaExtra()){
            pm.setNumVidas(pm.getNumVidas() + 1); // Ganha 1 vida extra
            pm.setGanhouVidaExtra();
        }

        if (arestas[pm.getPosicaoAtual()][0].hasFrutaBonus()) {

            if (t.getNivel() == 1) pont += 100;
            else if (t.getNivel() == 2) pont += 300;
            else if (t.getNivel() == 3) pont += 500;
            t.getArestas()[pm.getAtual().getNumero()][0].resetFrutaBonus(); // fruta bonus = false
            t.getArestas()[pm.getAtual().getNumero()][0].setFrutaBonusComida(); // frutaBonusComida = true

        } else {
            if (!pm.getAtual().isComido() && pm.getAtual().getChar() == '*') pont += 50;
            else if (!pm.getAtual().isComido() && pm.getAtual().getChar() == '.') pont += 10;
        }

        t.setPontuacao(pont);

    }

    /**
     * Atualiza a cor de todos os fantasmas para "Azul", quando o Pac Man esta sob efeito da Pilula de Poder.
     * @param f1 Fantasma Blinky.
     * @param f2 Fantasma Pinky.
     * @param f3 Fantasma Inky.
     * @param f4 Fantasma Clyde.
     */
    public void setCorFantasmas(Blinky f1, Pinky f2, Inky f3, Clyde f4) {
        f1.setCor("Azul");
        f2.setCor("Azul");
        f3.setCor("Azul");
        f4.setCor("Azul");

    }

    /**
     * Quando o efeito da pilula de poder sobre o Pac Man acaba, os fantasmas tem suas cores voltadas para o original.
     * @param f1 Fantasma Blinky.
     * @param f2 Fantasma Pinky.
     * @param f3 Fantasma Inky.
     * @param f4 Fantasma Clyde.
     */
    public void resetCorFantasmas(Blinky f1, Pinky f2, Inky f3, Clyde f4) {
        f1.setCor("Vermelha");
        f2.setCor("Rosa");
        f3.setCor("Verde");
        f4.setCor("Laranja");
    }

    /**
     * Metodo utilizada para procurar um vertice disponivel no tabuleiro a fim de se posicionar uma Fruta Bonus.
     * Para isso, este metodo acessa o vetor de vertices percorridos do Pac Man e sorteia aleatoriamente um deles para
     * receber a Fruta Bonus.
     * @param pm Pac Man
     * @return int
     */
    public int procuraVerticeVazio(PacMan pm) {

        int numVertice = - 1;

        do {
            Random random = new Random();
            int posicao = random.nextInt(pm.getVerticesPercorridos().size() - 1);
            if (arestas[pm.getVerticesPercorridos().get(posicao)][0].getChar() == ' '
                    && !arestas[pm.getVerticesPercorridos().get(posicao)][0].hasFrutaBonus()
                    && pm.getVerticesPercorridos().contains(arestas[pm.getVerticesPercorridos().get(posicao)][0].getNumero()))
                numVertice = arestas[pm.getVerticesPercorridos().get(posicao)][0].getNumero();

        } while (numVertice == - 1);

        return numVertice;

    }

}
