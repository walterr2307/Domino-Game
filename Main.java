import java.util.Scanner;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        Funcoes funcoes = new Funcoes();
        Pecas j1 = new Pecas(), j2 = new Pecas(), compra = new Pecas(), jog;
        Arquivos arquivos = new Arquivos();

        int indice_jogador, indice_adversario, mesa_direita, mesa_esquerda, qtd_pecas, qtd_pecas_adversario, qtd_pecas_compra;
        int maior_dupla[] = new int[2], soma_de_pontos[]  = new int[2], maior_peca1[]  = new int[3], maior_peca2[] = new int[3];
        int maior_soma[] = new int[2], maior_lado[] = new int[2], menor_lado[] = new int[2], opcao_menu;
        int opcao_de_jogada, pos_peca, indice_peca, lado_direito_peca, lado_esquerdo_peca, empate = 0;
        boolean jogada_valida = true, compra_liberada, novo_jogo = true, carregar_jogo;
        char lado_da_mesa;
        
        System.out.println(" =============== DOMINÓ ===============");

        do{ //"do" para o menu
            System.out.print("1 - Novo Jogo\n2 - Carregar Jogo\n3 - Regras\n0 - Sair\n\n");
            System.out.print("Digite a opção desejada: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, digite um número inteiro válido: ");
                scanner.next(); 
            }
    
            opcao_menu = scanner.nextInt();

            switch(opcao_menu){
                case 1:
                    novo_jogo = true;
                    break;

                case 2:
                    novo_jogo = false;
                    carregar_jogo = arquivos.verificarCarregarJogo();

                    if(carregar_jogo == false){
                        System.out.print("\n\tNão há arquivos salvos!\n\n");
                        opcao_menu = 4;
                    }
                    break;

                case 3:
                    System.out.println("\n1 - O jogo comeca com um conjunto de 28 pecas unicas e diferentes, variando de[0|0] ate [6|6].\n\n2 - Cada jogador comeca comprando 7 pecas.\n\n3 - O jogador inicial e o que possui a dupla mais alta. Se nenhum jogador possuir uma dupla, o que tiver a maior soma inicia. Em caso de empate na soma, o jogador com \na peca de maior numero em sua composicao comeca.\n\n4 - Em cada vez, o jogador deve realizar uma das tres acoes: jogar uma peca de sua mao, comprar pecas da mesa ate que seja possivel jogar ou passar a vez, se todas as \npecas da mesa estiverem esgotadas.\n\n5 - As regras para jogar uma peca da mao para a mesa sao as seguintes: e permitido jogar pecas que tenham um lado com o mesmo numero do lado esquerdo da peca mais a \nesquerda jogada ou um lado com o mesmo numero do lado direito da peca mais a direita. Se ambas as possibilidades forem o mesmo número, o jogador pode escolher em que \nlado quer jogar a peca.\n\n6 -Apos a jogada, a peca jogada deve se conectar a peca que estava em mesa pelo lado com o numero em comum, formando assim uma sequencia linear de pecas.\n");
                    break;

                case 0:
                    System.exit(0);
            }   
        }while(opcao_menu < 0 || opcao_menu > 2); //"while" para o menu

        if(novo_jogo == true){ //Algoritmo para novo jogo
            arquivos.apagar();

            int lado_direito[] = funcoes.getLadoDireito(), lado_esquerdo[] = funcoes.getLadoEsquerdo(), ordem[] = funcoes.getOrdem();
            j1.definirPecas(lado_direito, lado_esquerdo, ordem, 0);
            j2.definirPecas(lado_direito, lado_esquerdo, ordem, 7);
            compra.definirPecas(lado_direito, lado_esquerdo, ordem, 14);

            maior_dupla[0] = j1.retornarMaiorDupla();
            maior_dupla[1] = j2.retornarMaiorDupla();
            soma_de_pontos[0] = j1.retornarSomaPontos();
            soma_de_pontos[1] = j2.retornarSomaPontos();
            maior_peca1 = j1.retornarMaiorPeca();
            maior_peca2 = j2.retornarMaiorPeca();
            maior_soma[0] = maior_peca1[0];
            maior_soma[1] = maior_peca2[0];
            maior_lado[0] = maior_peca1[1];
            maior_lado[1] = maior_peca2[1];
            menor_lado[0] = maior_peca1[2];
            menor_lado[1] = maior_peca2[2];

            //Definindo o índice do jogador inicial
            if(maior_dupla[0] != maior_dupla[1]){
                if(maior_dupla[0] > maior_dupla[1])
                    indice_jogador = 1;
                else
                    indice_jogador = 2;
            }
            else if(soma_de_pontos[0] != soma_de_pontos[1]){
                if(soma_de_pontos[0] > soma_de_pontos[1])
                    indice_jogador = 1;
                else
                    indice_jogador = 2;
            }
            else{
                if(maior_soma[0] > maior_soma[1])
                    indice_jogador = 1;
                else if(maior_soma[0] == maior_soma[1] && maior_lado[0] > maior_lado[1])
                    indice_jogador = 1;
                else
                    indice_jogador = 2;
            }

            //Definindo o jogador inicial
            if(indice_jogador == 1)
                jog = j1;
            else
                jog = j2;

            //Definindo a peça inicial
            if(maior_dupla[0] != maior_dupla[1]){
                mesa_direita = maior_dupla[indice_jogador - 1];
                mesa_esquerda = mesa_direita;
            }
            else{
                mesa_direita = menor_lado[indice_jogador - 1];
                mesa_esquerda = maior_lado[indice_jogador - 1];
            }

            arquivos.gravarPeca(mesa_direita, mesa_esquerda, 0);
            jog.apagarPrimeiraPeca(mesa_direita, mesa_esquerda);
        } // Fim do algoritmo para um novo jogo

        else{ //Algoritmo para carregar o jogo
            int variaveis_carregadas [] = arquivos.carregarVariaveis();
            indice_jogador = variaveis_carregadas[0];
            mesa_direita = variaveis_carregadas[1];
            mesa_esquerda = variaveis_carregadas[2];
            empate = variaveis_carregadas[3];
            arquivos.carregasPecas(j1, "Salvar_J1.txt");
            arquivos.carregasPecas(j2, "Salvar_J2.txt");
            arquivos.carregasPecas(compra, "Salvar_Compra.txt");
            jogada_valida = true;
        } //Fim do algoritmo para carregar o jogo
        
        do{ //"do" para a intercalação das jogadas
            funcoes.limparTerminal();
            arquivos.ImprimirMesa();
            indice_jogador = funcoes.inverterIndice(indice_jogador);

            if(indice_jogador == 1){
                jog = j1;
                indice_adversario = 2;
                qtd_pecas_adversario = j2.quantidadeDePecas();
            }
            else{
                jog = j2;
                indice_adversario = 1;
                qtd_pecas_adversario = j1.quantidadeDePecas();
            }

            qtd_pecas = jog.quantidadeDePecas();
            qtd_pecas_compra = compra.quantidadeDePecas();

            System.out.printf("\n\tJogador %d:\n\t", indice_jogador);
            jog.imprimirPecas();
            System.out.printf("\n\tQtd. peças do J%d: %d\tQtd.peças na Compra: %d\n\n", indice_adversario, qtd_pecas_adversario, qtd_pecas_compra);

            do{ //"do" para verificar a validez a jogada
                 if(jogada_valida == false)
                    System.out.print("\n\t\tJogada inválida!\n\n");

                do{ //"do" para selecionar a opção de jogada
                    System.out.print("\tDigite 1 para selecionar a peça, 2 para comprar, 3 para pular e 0 para salvar e sair: ");

                    while (!scanner.hasNextInt()) {
                        System.out.print("\tPor favor, digite um número inteiro válido: ");
                        scanner.next(); 
                    }

                    opcao_de_jogada = scanner.nextInt();

                }while(opcao_de_jogada < 0 || opcao_de_jogada > 3); //"while" para selecionar a opção de jogada

                switch(opcao_de_jogada){
                    case 1:
                        System.out.print("\tInsira a posiçao da peça: ");

                        while (!scanner.hasNextInt()) {
                            System.out.print("\tPor favor, digite um número inteiro válido: ");
                            scanner.next(); 
                        }

                        pos_peca = scanner.nextInt();
                        indice_peca = pos_peca - 1;

                        System.out.println("\tSelecione D para o lado direito e E para o lado esquerdo: ");
                        lado_da_mesa = scanner.next().charAt(0);
                        
                        lado_direito_peca = jog.retornarLadoDireitoPeca(indice_peca);
                        lado_esquerdo_peca = jog.retornarLadoEsquerdoPeca(indice_peca);

                        if(lado_da_mesa == 'D')
                            lado_da_mesa = 'd';
                        if(lado_da_mesa == 'E')
                            lado_da_mesa = 'e';
                        
                        if(lado_direito_peca == mesa_direita && lado_da_mesa == 'd'){
                            jogada_valida = true;
                            mesa_direita = lado_esquerdo_peca;
                            arquivos.gravarPeca(lado_esquerdo_peca, lado_direito_peca, 1);
                            jog.apagarPeca(indice_peca);
                        }
                        else if(lado_esquerdo_peca == mesa_direita && lado_da_mesa == 'd'){
                            jogada_valida = true;
                            mesa_direita = lado_direito_peca;
                            arquivos.gravarPeca(lado_direito_peca, lado_esquerdo_peca, 1);
                            jog.apagarPeca(indice_peca);
                        }
                        else if(lado_direito_peca == mesa_esquerda && lado_da_mesa == 'e'){
                            jogada_valida = true;
                            mesa_esquerda = lado_esquerdo_peca;
                            arquivos.gravarPeca(lado_direito_peca, lado_esquerdo_peca, 2);
                            jog.apagarPeca(indice_peca);
                        }
                        else if(lado_esquerdo_peca == mesa_esquerda && lado_da_mesa == 'e'){
                            jogada_valida = true;
                            mesa_esquerda = lado_direito_peca;
                            arquivos.gravarPeca(lado_esquerdo_peca, lado_direito_peca, 2);
                            jog.apagarPeca(indice_peca);
                        }
                        else
                            jogada_valida = false;
                            
                        break; 

                    case 2:
                        compra_liberada = jog.verificarLiberacaoDaCompra(mesa_direita, mesa_esquerda);

                        if(compra_liberada == true && qtd_pecas_compra > 0){
                            jogada_valida = true;
                            jog.adicionarPeca(compra.retornarLadoDireitoPeca(0), compra.retornarLadoEsquerdoPeca(0));
                            compra.apagarPeca(0);
                            indice_jogador = funcoes.inverterIndice(indice_jogador);
                        }
                        else
                            jogada_valida = false;

                        break; 

                    case 3:
                        compra_liberada = jog.verificarLiberacaoDaCompra(mesa_direita, mesa_esquerda);

                        if(compra_liberada == true && qtd_pecas_compra == 0)
                            jogada_valida = true;
                        else
                            jogada_valida = false;
                        
                        break;

                    default:
                        indice_jogador = funcoes.inverterIndice(indice_jogador);
                        int vetor_variaveis[] = {indice_jogador, mesa_direita, mesa_esquerda, empate};
                        arquivos.salvarVariaveis(vetor_variaveis);
                        arquivos.salvarPecas(j1, "Salvar_J1.txt");
                        arquivos.salvarPecas(j2, "Salvar_J2.txt");
                        arquivos.salvarPecas(compra, "Salvar_Compra.txt");
                        System.exit(0);
                } //Fim do "Switch Case"

            }while(jogada_valida == false); //"while" para verificar a validez a jogada

            if(opcao_de_jogada == 3)
                ++empate;
            else
                empate = 0;
        
            qtd_pecas = jog.quantidadeDePecas();
        }while(empate < 2 && qtd_pecas > 0); //"while" para a intercalação das jogadas

        if(empate == 2){ //Algoritmo para o desempate
            soma_de_pontos[0] = j1.retornarSomaPontos();
            soma_de_pontos[1] = j2.retornarSomaPontos();
            maior_peca1 = j1.retornarMaiorPeca();
            maior_peca2 = j2.retornarMaiorPeca();
            maior_soma[0] = maior_peca1[0];
            maior_soma[1] = maior_peca2[0];
            maior_lado[0] = maior_peca1[1];
            maior_lado[1] = maior_peca2[1];

            if(soma_de_pontos[0] != soma_de_pontos[1]){
                if(soma_de_pontos[0] > soma_de_pontos[1])
                    indice_jogador = 2;
                else
                    indice_jogador = 1;
            }
            else{
                if(maior_soma[0] > maior_soma[1])
                    indice_jogador = 2;
                else if(maior_soma[0] == maior_soma[1] && maior_lado[0] > maior_lado[1])
                    indice_jogador = 2;
                else
                    indice_jogador = 1;
            }
        } //Fim do algoritmo para o desempate

        scanner.close();
        arquivos.apagar();
        System.out.printf("\n\n\t\tJogador %d venceu!!!\n", indice_jogador);
    }
}