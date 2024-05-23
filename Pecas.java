import java.util.ArrayList;

public class Pecas {
    private ArrayList <Integer> lado_direito, lado_esquerdo;

    public Pecas(){
        this.lado_direito = new ArrayList<Integer>();
        this.lado_esquerdo = new ArrayList<Integer>();
    }

    public void definirPecas(int lado_direito[], int lado_esquerdo[], int ordem[], int indice){
        int i, max;

        if(indice == 14)
            max = 28;
        else
            max = indice + 7;

        for(i = indice; i < max; i++){
            this.lado_direito.add(lado_direito[ordem[i]]);
            this.lado_esquerdo.add(lado_esquerdo[ordem[i]]);
        }
    }

    public int retornarMaiorDupla(){
        int i, maior_dupla = -1;

        for(i = 0; i < 7; i++){
            if(this.lado_direito.get(i) == this.lado_esquerdo.get(i) && this.lado_direito.get(i) > maior_dupla)
                maior_dupla = this.lado_direito.get(i);
        }

        return maior_dupla;
    }

    public int retornarSomaPontos(){
        int i, soma_de_pontos = 0; 

        for(i = 0; i < this.lado_direito.size(); i++)
            soma_de_pontos += this.lado_direito.get(i) + this.lado_esquerdo.get(i);
        
        return soma_de_pontos;
    }

    public int[] retornarMaiorPeca(){
        int i, maior_soma = 0, maior_lado = 0, menor_lado = 0, lado_direito, lado_esquerdo, soma;

        for(i = 0; i < this.lado_direito.size(); i++){
            lado_direito = this.lado_direito.get(i);
            lado_esquerdo = this.lado_esquerdo.get(i);
            soma = lado_direito + lado_esquerdo;

            if(soma > maior_soma){
                maior_soma = soma;
                maior_lado = lado_esquerdo;
                menor_lado = lado_direito;
            }

            if(soma == maior_soma && lado_esquerdo > maior_lado){
                maior_lado = lado_esquerdo;
                menor_lado = lado_direito;
            }
        }

        int maior_peca[] = {maior_soma, maior_lado, menor_lado};
        return maior_peca;
    }

    public void apagarPrimeiraPeca(int lado_direito, int lado_esquerdo){
        for(int i = 0; i < 7; i++){

            if(lado_direito == this.lado_direito.get(i) && lado_esquerdo == this.lado_esquerdo.get(i)){
                this.lado_direito.remove(i);
                this.lado_esquerdo.remove(i);
                break;
            }
        }
    }

    public void imprimirPecas(){
        int i, posicao = 1;

        for(i = 0; i < this.lado_direito.size(); i++){
            System.out.printf("%d.[%d|%d] ", posicao, this.lado_direito.get(i), this.lado_esquerdo.get(i));
            ++posicao;
        }
    }

    public int quantidadeDePecas(){
        return this.lado_direito.size();
    }

    public int retornarLadoDireitoPeca(int i){
        return this.lado_direito.get(i);
    }

    public int retornarLadoEsquerdoPeca(int i){
        return this.lado_esquerdo.get(i);
    }

    public void apagarPeca(int indice_peca){
        this.lado_direito.remove(indice_peca);
        this.lado_esquerdo.remove(indice_peca);
    }

    public boolean verificarLiberacaoDaCompra(int mesa_direita, int mesa_esquerda){
        boolean compra_liberada = true;

        for(int i = 0; i < this.lado_direito.size(); i++){
            int lado_direito = this.lado_direito.get(i), lado_esquerdo = this.lado_esquerdo.get(i);
            
            if(lado_direito == mesa_direita || lado_direito == mesa_esquerda || lado_esquerdo == mesa_direita || lado_esquerdo == mesa_esquerda){
                compra_liberada = false;
                break;
            }
        }

        return compra_liberada;
    }

    public void adicionarPeca(int lado_direito, int lado_esquerdo){
        this.lado_direito.add(lado_direito);
        this.lado_esquerdo.add(lado_esquerdo);
    }

    public int[] ladoDireitoDasPecas(){
        int i, tam = this.lado_direito.size(), vetor_lado_direito[] = new int[tam];

        for(i = 0; i < tam; i++)
            vetor_lado_direito[i] = this.lado_direito.get(i);
        
        return vetor_lado_direito;
    }

    public int[] ladoEsquerdoDasPecas(){
        int i, tam = this.lado_esquerdo.size(), vetor_lado_esquerdo[] = new int[tam];

        for(i = 0; i < tam; i++)
            vetor_lado_esquerdo[i] = this.lado_esquerdo.get(i);
        
        return vetor_lado_esquerdo;
    }

}