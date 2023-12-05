package ArvoreAVL;

import ArvoreRN.ArvoreRN;

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

    int contagem(AVLNo raiz, int valor) {
        int count = 1;
        if(busca(raiz, valor) == true){
            count += 1;
        }
        return count;
    }

    boolean busca(AVLNo raiz, int valor) {
        AVLNo atual = raiz;
        while (atual != null) {
            if (valor == atual.info) {
                return true;
            }
            if (valor < atual.info) {
                atual = atual.noEsquerda;
            } else {
                atual = atual.noDireita;
            }
        }
        return false;
    }

    AVLNo rotacaoLL(AVLNo raiz) {
    if (raiz == null || raiz.noEsquerda == null) {
        return raiz;
    }
    AVLNo no = raiz.noEsquerda;
    raiz.noEsquerda = no.noDireita;
    no.noDireita = raiz;
    raiz.alt = getMax(getAltura(raiz.noEsquerda), getAltura(raiz.noDireita)) + 1;
    no.alt = getMax(getAltura(no.noEsquerda), raiz.alt) + 1;
    return no;
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

    AVLNo remover(AVLNo raiz, int valor){
        if(raiz == null){
            return raiz;
        }
        if(valor < raiz.info){
            raiz.noEsquerda = remover(raiz.noEsquerda, valor);
        }else if(valor > raiz.info){
            raiz.noDireita = remover(raiz.noDireita, valor);
        }else{
            if((raiz.noEsquerda == null) || (raiz.noDireita == null)){
                AVLNo temp = null;
                if(temp == raiz.noEsquerda){
                    temp = raiz.noDireita;
                }else{
                    temp = raiz.noEsquerda;
                }
                if(temp == null){
                    temp = raiz;
                    raiz = null;
                }else{
                    raiz = temp;
                }
            }else{
                AVLNo temp = procuraMenor(raiz.noDireita);
                raiz.info = temp.info;
                raiz.noDireita = remover(raiz.noDireita, temp.info);
            }
        }
        if(raiz == null){
            return raiz;
        }
        raiz.alt = getMax(getAltura(raiz.noEsquerda), getAltura(raiz.noDireita)) + 1;
        int fatorBalanceamento = getFatorBalanceamento(raiz);
        if(fatorBalanceamento > 1 && getFatorBalanceamento(raiz.noEsquerda) >= 0){
            return rotacaoLL(raiz);
        }
        if(fatorBalanceamento > 1 && getFatorBalanceamento(raiz.noEsquerda) < 0){
            return rotacaoLR(raiz);
        }
        if(fatorBalanceamento < -1 && getFatorBalanceamento(raiz.noDireita) <= 0){
            return rotacaoRR(raiz);
        }
        if(fatorBalanceamento < -1 && getFatorBalanceamento(raiz.noDireita) > 0){
            return rotacaoRL(raiz);
        }
        return raiz;
    }


}

