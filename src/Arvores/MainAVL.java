package Arvores;

public class Main {
    public static void main(String[] args) {

        int[] vetor = Arquivos.lerArquivo("L:\\Trabalho\\Trabalho\\src\\ArquivoDados\\dados5.txt");
        int[] vetor1 = Arquivos.lerArquivo("L:\\Trabalho\\Trabalho\\src\\ArquivoDados\\dados100.txt");
        int[] vetor2 = Arquivos.lerArquivo("L:\\Trabalho\\Trabalho\\src\\ArquivoDados\\dados1000.txt");
        int[] vetor3 = Arquivos.lerArquivo("L:\\Trabalho\\Trabalho\\src\\ArquivoDados\\dados10_mil.txt");
        int[] vetor4 = Arquivos.lerArquivo("L:\\Trabalho\\Trabalho\\src\\ArquivoDados\\dados50_mil.txt");
        int[] vetor5 = Arquivos.lerArquivo("L:\\Trabalho\\Trabalho\\src\\ArquivoDados\\dados100_mil.txt");
        int[] vetor6 = Arquivos.lerArquivo("L:\\Trabalho\\Trabalho\\src\\ArquivoDados\\dados500_mil.txt");

        AVLNo arvore = new AVLNo(vetor.clone());
        AVLNo arvore1 = new AVLNo(vetor1.clone());
        AVLNo arvore2 = new AVLNo(vetor2.clone());
        AVLNo arvore3 = new AVLNo(vetor3.clone());
        AVLNo arvore4 = new AVLNo(vetor4.clone());
        AVLNo arvore5 = new AVLNo(vetor5.clone());
        AVLNo arvore6 = new AVLNo(vetor6.clone());

        double tempoInicial = System.currentTimeMillis();

        System.out.println("Impressão em ordem dos dados: ");
        AVLNo.imp_Ordem(arvore5);

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