package Arvores;

public class AVLNo {
    int info;
    int alt;
    AVLNo noDireita;
    AVLNo noEsquerda;

    public AVLNo(int info) {
        this.info = info;
        this.alt = 0;
        this.noDireita = null;
        this.noEsquerda = null;
    }

    public AVLNo(int[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            inserir(this, vetor[i]);
        }
    }
    static void imp_Ordem(AVLNo no) {
        if (no != null) {
            imp_Ordem(no.noEsquerda); //Caminhar subarvore esquerda
            System.out.println(no.info); //Visitar o nó
            imp_Ordem(no.noDireita); //Caminhar subarvore direita
        }
    }

    int getAltura(AVLNo no) {
        if (no != null) {
            return no.alt;
        }
        return -1;
    }

    int getMax(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    int getFatorBalanceamento(AVLNo no) {
        if (no != null) {
            return Math.abs(getAltura(no.noEsquerda) - getAltura(no.noDireita)); //Retorna o valor absoluto da diferença entre as alturas
        }
        return 0;
    }

    int contagem(AVLNo no, int valor) {
        int count = 0;
        if (no != null) {
            if (no.info == valor) {
                count++;
            }
            count += contagem(no.noEsquerda, valor);
            count += contagem(no.noDireita, valor);
        }
        return count;
    }


    AVLNo rotacaoLL(AVLNo raiz) {
        AVLNo no = raiz.noEsquerda;//No auxiliar
        raiz.noEsquerda = no.noDireita;
        no.noDireita = raiz;
        raiz.alt = getMax(getAltura(raiz.noEsquerda), getAltura(raiz.noDireita)) + 1;

        no.alt = getMax(getAltura(no.noEsquerda), raiz.alt) + 1;
        return no; //Raiz passa a ser quem estiver no nó

    }


    AVLNo rotacaoRR(AVLNo raiz) {
        AVLNo no = raiz.noDireita;
        raiz.noDireita = no.noEsquerda;
        no.noEsquerda = raiz;
        raiz.alt = getMax(getAltura(raiz.noEsquerda), getAltura(raiz.noDireita)) + 1;
        no.alt = getMax(getAltura(no.noDireita), raiz.alt) + 1;
        return no;
    }

    AVLNo rotacaoLR(AVLNo raiz) {
        raiz.noEsquerda = rotacaoRR(raiz.noEsquerda);
        raiz = rotacaoLL(raiz);
        return raiz;
    }

    AVLNo rotacaoRL(AVLNo raiz) {
        raiz.noDireita = rotacaoLL(raiz.noDireita);
        raiz = rotacaoRR(raiz);
        return raiz;
    }

    AVLNo inserir(AVLNo raizAtual, int valor) {
        if (raizAtual == null) { //Se a raiz for nula
            raizAtual = new AVLNo(valor);
            return raizAtual;
        } else { //Atual recebe o conteudo da raiz
            if (valor < raizAtual.info) { //Valor que o campo de informação do nó raizAtual
                raizAtual.noEsquerda = inserir(raizAtual.noEsquerda, valor);
                if (getFatorBalanceamento(raizAtual) >= 2) {
                    if (valor < raizAtual.noEsquerda.info) {
                        raizAtual = rotacaoLL(raizAtual);
                    } else {
                        raizAtual = rotacaoLR(raizAtual);
                    }
                }
            } else if (valor > raizAtual.info) {
                raizAtual.noDireita = inserir(raizAtual.noDireita, valor);
                if (getFatorBalanceamento(raizAtual) >= 2) {
                    if (valor > raizAtual.noDireita.info) {//Se o valor é maior do que o conteudo do nó da direita raizAtual
                        raizAtual = rotacaoRR(raizAtual);
                    } else {
                        raizAtual = rotacaoRL(raizAtual);
                    }
                }
            } else {
                System.out.println("Valor duplicado");
            }
        }
        raizAtual.alt =
                getMax(getAltura(raizAtual.noEsquerda), getAltura(raizAtual.noDireita)) + 1;
        return raizAtual;
    }

    AVLNo procuraMenor(AVLNo raizAtual) { //Dado um dos nós da árvore
        AVLNo no1 = raizAtual;
        AVLNo no2 = raizAtual.noEsquerda;
        while (no2 != null) {
            no1 = no2;
            no2 = no2.noEsquerda; //Andando cada vez mais para a esquerda
        }
        return no1; //Retorna o nó mais a esquerda
    }

    AVLNo remove(AVLNo raizAtual, int valor) {
        if (raizAtual == null) {//Valor não existe
            return null;
        } else {
            if (valor < raizAtual.info) { //Se o valor é menor do que a informação
                raizAtual.noEsquerda = remove(raizAtual.noEsquerda, valor);// tenta remover o nó da esquerda
                if (getFatorBalanceamento(raizAtual) >= 2) {//Se a inserção for maior ou igual a 2, é necessário balancear
                    if (getAltura(raizAtual.noDireita.noEsquerda) <= getAltura(raizAtual.noDireita.noDireita)) { //Comparando as alturas
                        raizAtual = rotacaoRR(raizAtual);
                    } else {
                        raizAtual = rotacaoRL(raizAtual);
                    }
                }
            } else if (valor > raizAtual.info) {
                raizAtual.noDireita = remove(raizAtual.noDireita, valor);// Tenta remover o nó da direita
                if (getFatorBalanceamento(raizAtual) >= 2) {//Se a inserção for maior ou igual a 2, é necessário balancear
                    if (getAltura(raizAtual.noEsquerda.noDireita) <= getAltura(raizAtual.noEsquerda.noEsquerda)) {
                        raizAtual = rotacaoLL(raizAtual);
                    } else {
                        raizAtual = rotacaoLR(raizAtual);
                    }
                }
            } else { //Se valor == raizAtual.info
                if (raizAtual.noDireita == null || raizAtual.noEsquerda == null) {
                    if (raizAtual.noEsquerda != null) { //Se o nó da esquerda não for nulo
                        raizAtual = raizAtual.noEsquerda; //Raiz recebe o filho da esquerda
                    } else {
                        raizAtual = raizAtual.noDireita; //Raiz recebe o filho da direita
                    }
                } else {//Nó tem dois filhos
                    AVLNo temp = procuraMenor(raizAtual.noDireita);//procurar o menor valor na subarvore da direita
                    raizAtual.info = temp.info; //Compila as informações para o nó raiz
                    raizAtual = remove(raizAtual.noDireita, raizAtual.info);//Remove o nó que foi compilado

                    if (getFatorBalanceamento(raizAtual) >= 2) {
                        if (getAltura(raizAtual.noDireita.noEsquerda) <= getAltura(raizAtual.noDireita.noDireita)) {
                            raizAtual = rotacaoRR(raizAtual);
                        } else {
                            raizAtual = rotacaoRL(raizAtual);
                        }
                    }
                }
            }
        }
        if (raizAtual != null) {
            raizAtual.alt = getMax(getAltura(raizAtual.noEsquerda), getAltura(raizAtual.noDireita)) + 1; //Recalcula a altura raizAtual
        }
        return raizAtual;
    }


}

