package Arvores;

public class ArvoreRN {
        int info;
        ArvoreRN noDireita;
        ArvoreRN noEsquerda;
        boolean cor;

        public ArvoreRN(int info) {
            this.info = info;
            this.cor = Vermelho;
            this.noEsquerda = null;
            this.noDireita = null;
        }

        public static final boolean Vermelho = true;
        public static final boolean Preto = false;


        public ArvoreRN(int[] vetor) {
            for (int i = 0; i < vetor.length; i++) {
                insere(this, vetor[i]);
            }
        }

        private boolean cor(ArvoreRN no) {//Retorna a cor do nó
            return no == null ? Preto : no.cor;//Se H for nulo, retorna preto, senão retorna a cor de H
        }

        void trocarCor(ArvoreRN raiz) {
            raiz.cor = !raiz.cor;//Troca a cor do nó
            if (raiz.noEsquerda != null) {//Se o nó da esquerda não for nulo, troca a cor dele
                raiz.noEsquerda.cor = !raiz.noEsquerda.cor;
            }
            if (raiz.noDireita != null) {
                raiz.noDireita.cor = !raiz.noDireita.cor;
            }
        }

        ArvoreRN rotacionaEsquerda(ArvoreRN raiz) {
            ArvoreRN aux = raiz.noDireita;//No auxiliar recebe o nó da direita
            raiz.noDireita = aux.noEsquerda;//O nó da direita recebe o nó da esquerda do auxiliar
            aux.noEsquerda = raiz;//O nó da esquerda do auxiliar recebe o nó
            aux.cor = raiz.cor;//A cor do auxiliar recebe a cor do nó
            raiz.cor = Vermelho;//A cor do nó recebe vermelho
            return aux;//Retorna o auxiliar
        }

        ArvoreRN rotacionaDireita(ArvoreRN raiz) {
            ArvoreRN aux = raiz.noEsquerda;//No auxiliar recebe o nó da esquerda
            raiz.noEsquerda = aux.noDireita;//O nó da esquerda recebe o nó da direita do auxiliar
            aux.noDireita = raiz;//O nó da direita do auxiliar recebe o nó
            aux.cor = raiz.cor;//A cor do auxiliar recebe a cor do nó
            raiz.cor = Vermelho;//A cor do nó recebe vermelho
            return aux;//Retorna o auxiliar
        }

        ArvoreRN move2EsqVermelho(ArvoreRN raiz) {
            trocarCor(raiz);//Troca a cor do nó
            if (cor(raiz.noDireita.noEsquerda) == Vermelho) {//Se a cor do nó da direita da esquerda for vermelho
                raiz.noDireita = rotacionaDireita(raiz.noDireita);//Rotaciona a direita
                raiz = rotacionaEsquerda(raiz);//Rotaciona a esquerda
                trocarCor(raiz);//Troca a cor do nó
            }
            return raiz;//Retorna o nó
        }

        ArvoreRN move2DirVermelho(ArvoreRN raiz) {
            trocarCor(raiz);//Troca a cor do nó
            if (cor(raiz.noEsquerda.noEsquerda) == Vermelho) {//Se a cor do nó da esquerda da esquerda for vermelho
                raiz = rotacionaDireita(raiz);//Rotaciona a direita
                trocarCor(raiz);//Troca a cor do nó
            }
            return raiz;//Retorna o nó
        }

        ArvoreRN balancear(ArvoreRN raiz) {
            if (cor(raiz.noDireita) == Vermelho) {
                raiz = rotacionaEsquerda(raiz);
            }
            if (raiz.noEsquerda != null && cor(raiz.noDireita) == Vermelho && cor(raiz.noEsquerda.noEsquerda) == Vermelho) {//Se o nó da esquerda não for nulo e a cor do nó da direita e da esquerda da esquerda for vermelho
                raiz = rotacionaDireita(raiz);//Rotaciona a direita
            }
            if (cor(raiz.noEsquerda) == Vermelho && cor(raiz.noDireita) == Vermelho) {//Se a cor do nó da esquerda e da direita for vermelho
                trocarCor(raiz);//Troca a cor do nó
            }
            return raiz;//Retorna o nó
        }

    int contagem(ArvoreRN no, int valor) {
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

    boolean busca(ArvoreRN raiz, int valor) {
        ArvoreRN atual = raiz;
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


    ArvoreRN insereNo(ArvoreRN raiz, int valor) {
            if (raiz == null) {//Árvore vazia
                return new ArvoreRN(valor);
            }
            if (valor < raiz.info) {//Se o valor for menor que o nó
                raiz.noEsquerda = insereNo(raiz.noEsquerda, valor);//O nó da esquerda recebe o valor
            } else if (valor > raiz.info) {//Se o valor for maior que o nó
                raiz.noDireita = insereNo(raiz.noDireita, valor);//O nó da direita recebe o valor
            } else {//Se o valor for igual ao nó
                raiz.info = valor;//O nó recebe o valor
            }
            if (cor(raiz.noDireita) == Vermelho && cor(raiz.noEsquerda) == Preto) {//Se a cor do nó da direita for vermelho e a cor do nó da esquerda for preto
                raiz = rotacionaEsquerda(raiz);//Rotaciona a esquerda
            }
            if (cor(raiz.noEsquerda) == Vermelho && cor(raiz.noEsquerda.noEsquerda) == Vermelho) {//Se a cor do nó da esquerda for vermelho e a cor do nó da esquerda da esquerda for vermelho
                raiz = rotacionaDireita(raiz);//Rotaciona a direita
            }
            if (cor(raiz.noEsquerda) == Vermelho && cor(raiz.noDireita) == Vermelho) {//Se a cor do nó da esquerda e da direita for vermelho
                trocarCor(raiz);//Troca a cor do nó
            }
            return balancear(raiz);//Retorna o nó balanceado
        }

        public boolean insere(ArvoreRN raiz, int valor) {
            if (busca(raiz, valor) == true) {//Se o valor já existir na árvore
                return false;//Retorna falso
            }
            raiz = insereNo(raiz, valor);//Insere o valor na árvore
            raiz.cor = Preto;//A raiz recebe preto
            return true;//Retorna verdadeiro
        }

        ArvoreRN removerMenor(ArvoreRN raiz) {
            if (raiz.noEsquerda == null) {//Se o nó da esquerda for nulo
                return null;//Retorna nulo
            }
            if (cor(raiz.noEsquerda) == Preto && cor(raiz.noEsquerda.noEsquerda) == Preto) {//Se a cor do nó da esquerda e da esquerda da esquerda for preto
                raiz = move2EsqVermelho(raiz);//Move para a esquerda
            }
            raiz.noEsquerda = removerMenor(raiz.noEsquerda);//O nó da esquerda recebe o nó da esquerda
            return balancear(raiz);//Retorna o nó balanceado
        }

        ArvoreRN procurarMenor(ArvoreRN atual) {
            ArvoreRN no1 = atual;
            ArvoreRN no2 = atual.noEsquerda;
            while (no2 != null) {
                no1 = no2;
                no2 = no2.noEsquerda;
            }
            return no1;
        }

        ArvoreRN removerNo(ArvoreRN raiz, int valor) {
            if (valor < raiz.info) {//Se o valor for menor que o nó
                if (cor(raiz.noEsquerda) == Preto && cor(raiz.noEsquerda.noEsquerda) == Preto) {//Se a cor do nó da esquerda e da esquerda da esquerda for preto
                    raiz = move2EsqVermelho(raiz);//Move para a esquerda
                }
                raiz.noEsquerda = removerNo(raiz.noEsquerda, valor);//O nó da esquerda recebe o nó da esquerda
            } else {
                if (cor(raiz.noEsquerda) == Vermelho) {//Se a cor do nó da esquerda for vermelho
                    raiz = rotacionaDireita(raiz);//Rotaciona a direita
                }
                if (valor == raiz.info && (raiz.noDireita == null)) {//Se o valor for igual ao nó e o nó da direita for nulo
                    return null;//Retorna nulo
                }
                if (cor(raiz.noDireita) == Preto && cor(raiz.noDireita.noEsquerda) == Preto) {//Se a cor do nó da direita e da esquerda da direita for preto
                    raiz = move2DirVermelho(raiz);//Move para a direita
                }
                if (valor == raiz.info) {//Se o valor for igual ao nó
                    ArvoreRN temp = procurarMenor(raiz.noDireita);//Procura o menor valor na subárvore da direita
                    raiz.info = temp.info;//O nó recebe o menor valor
                    raiz.noDireita = removerMenor(raiz.noDireita);//O nó da direita recebe o nó da direita
                } else {
                    raiz.noDireita = removerNo(raiz.noDireita, valor);//O nó da direita recebe o nó da direita
                }
            }
            return balancear(raiz);//Retorna o nó balanceado
        }

        public boolean remover(ArvoreRN raiz, int valor) {
            if (busca(raiz, valor) == false) {//Se o valor não existir na árvore
                return false;//Retorna falso
            }
            if (cor(raiz.noEsquerda) == Preto && cor(raiz.noDireita) == Preto) {//Se a cor do nó da esquerda e da direita for preto
                raiz.cor = Vermelho;//A raiz recebe vermelho
            }
            raiz = removerNo(raiz, valor);//Remove o valor da árvore
            if (raiz != null) {//Se a raiz não for nula
                raiz.cor = Preto;//A raiz recebe preto
            }
            return true;//Retorna verdadeiro
        }

        static void imp_Ordem(ArvoreRN raiz) {
            if (raiz != null) {
                imp_Ordem(raiz.noEsquerda);
                if (raiz.cor == Vermelho) {
                    System.out.println("VERMELHO) " + raiz.info);
                } else {
                    System.out.println("PRETO) " + raiz.info);
                }
                imp_Ordem(raiz.noDireita);
            }
        }

    }
