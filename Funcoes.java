public class Funcoes{
    private int lado_direito[] = new int[28], lado_esquerdo[] = new int[28], ordem[] = new int[28];

    private void definirOrdem(){
        for(int i = 0; i < 28; i++)
            this.ordem[i] = i;
    }

    private void embaralharOrdem(){
        int i, j, copia;

        for(i = 27; i > 0; i--){
            j = (int) (Math.random() * (i + 1));
            copia = this.ordem[i];
            this.ordem[i] = this.ordem[j];
            this.ordem[j] = copia;
        }
    }

    private void definirPecas(){
        int i, j = 0, k = 0;

        for(i = 0; i < 28; i++){
            this.lado_direito[i] = j;
            this.lado_esquerdo[i] = k;
            ++k;

            if(k == 7){
                ++j;
                k = j;
            }
        }
    }

    public int[] getLadoDireito(){
        this.definirPecas();
        return this.lado_direito;
    }

    public int[] getLadoEsquerdo(){
        return this.lado_esquerdo;
    }

    public int[] getOrdem(){
        this.definirOrdem();
        this.embaralharOrdem();
        return this.ordem;
    }

    public int inverterIndice(int indice_jogador){
        if(indice_jogador == 1)
            indice_jogador = 2;
        else
            indice_jogador = 1;

        return indice_jogador;
    }

    public void limparTerminal(){
        String os = System.getProperty("os.name").toLowerCase();
        String limparComando;

        if (os.contains("win")) {
            limparComando = "cmd /c cls";
        } else {
            limparComando = "clear";
        }

        try {
            new ProcessBuilder(limparComando.split(" ")).inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}