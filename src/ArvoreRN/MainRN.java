package ArvoreRN;

import ArvoreAVL.Arquivos;

public class MainRN {
    public static void main(String[] args) {

        int[] vetor = Arquivos.lerArquivo("L:\\Trabalho\\Trabalho\\src\\ArquivoDados\\dados100_mil.txt");

        ArvoreRN arvore = new ArvoreRN(vetor.clone());

        double tempoInicial = System.currentTimeMillis();

        System.out.println("Impressão em ordem dos dados: ");
        ArvoreRN.imp_Ordem(arvore);


        System.out.println("_________________________________________");
        int[] randomNumbers = new int[50000];
        for (int i = 0; i < 50000; i++) {
            int random = (int) (Math.random() * 19999) - 9999;
            randomNumbers[i] = random;

            if (random % 3 == 0) {
                arvore.insere(arvore, randomNumbers[i]);
            } else if (random % 5 == 0) {
                arvore.remover(arvore, randomNumbers[i]);
            } else {
                int rnContagem = arvore.contagem(arvore, randomNumbers[i]);
                System.out.println("Quantas vezes o número" + randomNumbers[i] + " aparece: " + rnContagem);

            }

        }

        double tempoFinal = System.currentTimeMillis();
        double tempoTotal = tempoFinal - tempoInicial;
        //Tempo marcado
        double segundos = tempoTotal / 1000.0;
        double miliSeg = segundos * 1000;

        int hr = (int) Math.floor(segundos / 3600);
        int min = (int) Math.floor((segundos % 3600) / 60);
        int seg = (int) Math.floor((segundos % 60));

        System.out.println("Tempo de execução: ");
        System.out.printf("%02d:%02d:%02d:%03d", hr, min, seg, (int) miliSeg);

    }
}
