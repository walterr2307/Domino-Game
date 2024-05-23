import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class Arquivos {
    
    public void apagar() throws IOException{
        FileWriter arq_lado_direito = new FileWriter("Lado_Direito.txt");
        FileWriter arq_lado_esquerdo = new FileWriter("Lado_Esquerdo.txt");
        FileWriter arq_compra = new FileWriter("Salvar_Compra.txt");
        FileWriter arq_j1 = new FileWriter("Salvar_J1.txt");
        FileWriter arq_j2 = new FileWriter("Salvar_J2.txt");
        FileWriter arq_variaveis = new FileWriter("Salvar_Variaveis.txt");

        arq_lado_direito.close();
        arq_lado_esquerdo.close();
        arq_compra.close();
        arq_j1.close();
        arq_j2.close();
        arq_variaveis.close();
    }

    public boolean verificarCarregarJogo() throws IOException{
        File arq = new File("Salvar_Variaveis.txt");
        boolean carregar_jogo;

        if(arq.length() == 0)
            carregar_jogo = false;
        else
            carregar_jogo = true;
        
        return carregar_jogo;
    }

    public void salvarPecas(Pecas pecas, String caminho) throws IOException{
        FileWriter arq_salvar = new FileWriter(caminho);
        int qtd_pecas = pecas.quantidadeDePecas();
        int vetor_lado_direito[] = pecas.ladoDireitoDasPecas();
        int vetor_lado_esquerdo[] = pecas.ladoEsquerdoDasPecas();

        for(int i = 0; i < qtd_pecas; i++)
            arq_salvar.write(vetor_lado_direito[i] + "\n" + vetor_lado_esquerdo[i] + "\n");
        
        arq_salvar.close();
    }

    public void salvarVariaveis(int vetor_variaveis[])  throws IOException{
        FileWriter arq_salvar = new FileWriter("Salvar_Variaveis.txt");

        for(int i = 0; i < 4; i++)
            arq_salvar.write(vetor_variaveis[i] + "\n");

        arq_salvar.close();
    }

    public void gravarPeca(int lado1, int lado2, int tipo_impressao) throws IOException{
        FileWriter arq_lado_direito = new FileWriter("Lado_Direito.txt", true);
        FileWriter arq_lado_esquerdo = new FileWriter("Lado_Esquerdo.txt", true);

        if(tipo_impressao == 0 || tipo_impressao == 1)
            arq_lado_direito.write("[" + lado2 + "|" + lado1 + "]");
        if(tipo_impressao == 0 || tipo_impressao == 2)
            arq_lado_esquerdo.write("[" + lado1 + "|" + lado2 + "]");

        arq_lado_direito.close();
        arq_lado_esquerdo.close();
    }

    public void ImprimirMesa() throws IOException{
        File arq_lado_direito = new File("Lado_Direito.txt"), arq_lado_esquerdo = new File("Lado_Esquerdo.txt");
        Scanner scanner_direito = new Scanner(arq_lado_direito), scanner_esquerdo = new Scanner(arq_lado_esquerdo);

        System.out.print("\nLado Direito:\n");
        while(scanner_direito.hasNextLine())
            System.out.println(scanner_direito.nextLine());

        System.out.print("\nLado Esquerdo:\n");
        while(scanner_esquerdo.hasNextLine())
            System.out.println(scanner_esquerdo.nextLine());

        scanner_direito.close();
        scanner_esquerdo.close();
    }


    public void carregasPecas(Pecas pecas, String caminho) throws IOException{
        FileReader arq_salvar = new FileReader(caminho);
        BufferedReader arq_carregar = new BufferedReader(arq_salvar);
        int lado_direito, lado_esquerdo;
        String linha;

        try {
            while ((linha = arq_carregar.readLine()) != null) {
                lado_direito = Integer.parseInt(linha);
                linha = arq_carregar.readLine();
                lado_esquerdo = Integer.parseInt(linha);
                pecas.adicionarPeca(lado_direito, lado_esquerdo);
            }
        } finally {
            arq_carregar.close(); 
            arq_salvar.close(); 
        }
    }

    public int[] carregarVariaveis() throws IOException{
        FileReader arq_salvar = new FileReader("Salvar_Variaveis.txt");
        BufferedReader arq_carregar = new BufferedReader(arq_salvar);
        int i = 0, vetor_variaveis[] = new int[4];
        String linha;

        try {
            while ((linha = arq_carregar.readLine()) != null){
                vetor_variaveis[i] = Integer.parseInt(linha);
                ++i;
            }
        } finally {
            arq_carregar.close(); 
            arq_salvar.close(); 
        }

        return vetor_variaveis;
    }

}