package Arvores;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Arquivos {
    public static int[] lerArquivo(String url) {
        Path caminho = Paths.get(url); //Caminho do arquivo
        String leitura = "";

        try {
            byte[] texto = Files.readAllBytes(caminho); //Retorna um array de bytes que serão armazenados no array texto
            leitura = new String(texto); // o construtor da classe String aceita um parametro de array do tipo byte

        } catch (Exception e) {
            return null;
        }

        ArrayList<Integer> protoVetor = new ArrayList<>();

        String num = "";

        for (int i = 0; i < leitura.length(); i++) {
            if ((leitura.charAt(i) != '[')
                    && (leitura.charAt(i) != ']')
                    && (leitura.charAt(i) != ',')
                    && (leitura.charAt(i) != ' ')) {
                num += leitura.charAt(i);
            } else {
                if (num != "") {
                    int numero = Integer.parseInt(num);
                    protoVetor.add(numero);
                    num = "";
                }
            }
        }

        int[] vetor = new int[protoVetor.size()];

        for (int i = 0; i < protoVetor.size(); i++) {
            vetor[i] = protoVetor.get(i);
        }

        return vetor;
    }


}


