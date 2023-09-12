public class Main {
    public static void main(String[] args) {

        Grafo grafo = new Grafo();
        grafo.carregarArquivoDe("dados_grafo.ser");

        grafo.iniciar();

        grafo.salvarArquivoEm("dados_grafo.ser");
    }
}