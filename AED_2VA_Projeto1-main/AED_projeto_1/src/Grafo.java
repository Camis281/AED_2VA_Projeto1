import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class Grafo implements Serializable{

    private ArrayList<Vertice> vertices;
    private ArrayList<Arestas> arestas;

    public Grafo() {
        this.vertices = new ArrayList<Vertice>();
        this.arestas = new ArrayList<Arestas>();
    }

    public boolean adicionarVertice(No_cliente cliente) {
        //distancia = peso
        Vertice novoVertice = new Vertice(cliente);
        vertices.add(novoVertice);

        if (BuscarVertice(cliente) != null) {
            return true;
        } else {
            return false;
        }

    }

    public boolean adicionarAresta(Double distancia, No_cliente clienteInicio, No_cliente clienteFim) {
        //distancia = peso
        Vertice inicio = this.BuscarVertice(clienteInicio);
        Vertice fim = this.BuscarVertice(clienteFim);

        if (inicio != null && fim != null) {
            Arestas aresta = new Arestas(distancia, inicio, fim);
            inicio.AdicionarArestaSaida(aresta);
            fim.AdicionarArestaEntrada(aresta);
            this.arestas.add(aresta);
        }

        if (BuscarAresta(clienteInicio, clienteFim) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Vertice BuscarVertice(No_cliente cliente) {

        Vertice auxVertice = null;

        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getCliente().equals(cliente)) {
                auxVertice = this.vertices.get(i);
                break;
            }
        }
        return auxVertice;
    }

    public ArrayList<Arestas> BuscarAresta(No_cliente origem, No_cliente destino) {
        Vertice verticeOrigem = BuscarVertice(origem);
        Vertice verticeDestino = BuscarVertice(destino);

        ArrayList<Arestas> arestasEncontradas = null;

        if (verticeOrigem != null && verticeDestino != null) {
            arestasEncontradas = new ArrayList<>();
            for (Arestas aresta : arestas) {
                if (aresta.getInicio().equals(verticeOrigem) && aresta.getFim().equals(verticeDestino)) {
                    arestasEncontradas.add(aresta);
                }
            }
        }

        return arestasEncontradas;
    }

    public No_cliente BuscarCliente(String nome) {
        No_cliente auxCliente = null;

        for (Vertice vertice : vertices) {
            if (vertice.getCliente().getNome().equalsIgnoreCase(nome)) {
                auxCliente = vertice.getCliente();
            }
        }
        return auxCliente;
    }


    public void RemoverAresta(No_cliente clienteOrigem, No_cliente clienteDestino) {
        Vertice origem = BuscarVertice(clienteOrigem);
        Vertice destino = BuscarVertice(clienteDestino);

        if (origem != null && destino != null) {
            ArrayList<Arestas> arestasEncontradas = BuscarAresta(clienteOrigem, clienteDestino);

            if (!arestasEncontradas.isEmpty()) {
                for (Arestas aresta : arestasEncontradas) {
                    aresta.getInicio().getSaida().remove(aresta);
                    aresta.getFim().getEntrada().remove(aresta);
                    arestas.remove(aresta);

                    System.out.println();
                    System.out.println("Aresta de " + aresta.getInicio().getCliente().getNome() + " para " + aresta.getFim().getCliente().getNome() + " removida");
                    System.out.println();
                }

                System.out.println();
                System.out.println("Lista de arestas após a remoção:");
                for (Arestas aresta : arestas) {
                    System.out.println(aresta.getInicio().getCliente().getNome() + " -> " + aresta.getFim().getCliente().getNome() + " Distancia " + aresta.getDistancia());
                }

            } else {
                System.out.println("Aresta não encontrada!!!");
            }
        } else {
            System.out.println("Vértice de origem ou destino não encontrado.");
        }
    }
    public void BuscaEmLargura() {
        ArrayList<Vertice> marcados = new ArrayList<Vertice>();
        ArrayList<Vertice> fila = new ArrayList<Vertice>();
        Vertice atual = this.vertices.get(0);
        marcados.add(atual);
        System.out.println(atual.getCliente());
        fila.add(atual);
        while (!fila.isEmpty()) {
            Vertice visitado = fila.get(0);
            for (int i = 0; i < visitado.getSaida().size(); i++) {
                Vertice proximo = visitado.getSaida().get(i).getFim();
                if (!marcados.contains(proximo)) {
                    marcados.add(proximo);
                    System.out.println(proximo.getCliente());
                    fila.add(proximo);
                }
            }
            fila.remove(0);
        }
    }


    public void Dijkstra(No_cliente origem, No_cliente destino) {
        for (Vertice vertice : vertices) {
            vertice.setDistanciaMinima(Double.MAX_VALUE);
            vertice.setPredecessor(null);
        }

        Vertice verticeOrigem = BuscarVertice(origem);
        Vertice verticeDestino = BuscarVertice(destino);

        if (verticeOrigem == null || verticeDestino == null) {
            System.out.println();
            System.out.println("Vértice de origem ou destino não encontrado.");
            return;
        }

        verticeOrigem.setDistanciaMinima(0.0);

        PriorityQueue<Vertice> filaPrioridade = new PriorityQueue<>(Comparator.comparing(Vertice::getDistanciaMinima));
        filaPrioridade.add(verticeOrigem);

        while (!filaPrioridade.isEmpty()) {
            Vertice atual = filaPrioridade.poll();

            for (Arestas aresta : atual.getSaida()) {
                Vertice vizinho = aresta.getFim();
                double novaDistancia = atual.getDistanciaMinima() + aresta.getDistancia();

                if (novaDistancia < vizinho.getDistanciaMinima()) {
                    vizinho.setDistanciaMinima(novaDistancia);
                    vizinho.setPredecessor(atual);
                    filaPrioridade.add(vizinho);
                }
            }
        }

        if (verticeDestino.getDistanciaMinima() == Double.MAX_VALUE) {
            System.out.println();
            System.out.println("Não há caminho de " + origem.getNome() + " para " + destino.getNome());
            System.out.println();
        } else {
            ArrayList<Vertice> caminho = new ArrayList<>();
            caminho.add(verticeDestino);
            Vertice predecessor = verticeDestino.getPredecessor();

            while (predecessor != null) {
                caminho.add(predecessor);
                predecessor = predecessor.getPredecessor();
            }

            Collections.reverse(caminho);

            System.out.println();
            System.out.println("Caminho de " + origem.getNome() + " para " + destino.getNome() + ":");
            for (int i = 0; i < caminho.size() - 1; i++) {
                System.out.print(caminho.get(i).getCliente().getNome() + "/" + caminho.get(i).getCliente().getBairro() + " -> ");
            }
            System.out.println(caminho.get(caminho.size() - 1).getCliente().getNome()+ "/" + caminho.get(caminho.size() - 1).getCliente().getBairro());

            System.out.println("Distância total percorrida: " + verticeDestino.getDistanciaMinima());
        }
    }

    public void ImprimirListaArestas() {
        if (!arestas.isEmpty()) {
            System.out.println();
            System.out.println("Lista de arestas: ");
            for (Arestas a : arestas) {
                System.out.println(a.getInicio().getCliente().getNome() + " -> " + a.getFim().getCliente().getNome() + " Distancia: " + a.getDistancia());
            }
        } else {
            System.out.println("Não tem arestas ");
            System.out.println();
        }
    }

    public void ImprimirListaVertices() {
        if (!vertices.isEmpty()) {
            System.out.println("Lista de vertices: ");
            for (Vertice v : vertices) {
                System.out.println(v.getCliente());
            }
        } else {
            System.out.println("Não tem vertices ");
            System.out.println();
        }
    }

    public void iniciar() {
        int op;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\n---------------GRAFO---------------");
            System.out.printf("\n\t0 - Sair\n\t1 - Adicionar vertice\n\t2 - Adicionar aresta\n\t3 - Buscar vertice\n\t4 - Buscar aresta\n\t5 - Caminho do vertice a ate b\n\t6 - Remover aresta\n\t7 - Busca em Largura\n\t8 - Imprimir vertices\n\t9 - Imprimir arestas\n\t10 - Limpar grafo \n>");
            try {
                op = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Erro: Insira um valor numérico válido.");
                scanner.nextLine();
                op = -1;
            }
            String nome, bairro, origem, destino;
            boolean ver;
            No_cliente auxCliente;
            Vertice auxVertice;
            Arestas auxAresta;

            switch (op) {
                case 1:
                    System.out.println("----------Adicionar Vertice----------");
                    System.out.print("Digite o nome: ");
                    nome = scanner.nextLine();
                    System.out.print("Digite o Bairro: ");
                    bairro = scanner.nextLine();
                    if (nome != null && bairro != null) {
                        ver = adicionarVertice(new No_cliente(nome, bairro));
                        if (ver) {
                            System.out.println("Vertice criado com sucesso!!!");
                        } else {
                            System.out.println("Falha ao criar vertice");
                        }
                    }
                    break;
                case 2:
                    System.out.println("----------Adicionar Aresta----------");
                    System.out.print("Qual nome do cliente do vertice origem? ");
                    origem = scanner.nextLine();
                    System.out.print("Qual nome do cliente do vertice Destino? ");
                    destino = scanner.nextLine();
                    System.out.print("Qual o peso da aresta? ");
                    Double distancia = scanner.nextDouble();
                    if (BuscarCliente(origem) != null && BuscarCliente(destino) != null) {
                        ver = adicionarAresta(distancia, BuscarCliente(origem), BuscarCliente(destino));
                        if (ver) {
                            System.out.println("Aresta criada com sucesso!!!");
                        } else {
                            System.out.println("Erro ao criar aresta!!!");
                        }
                    } else {
                        System.out.println("O vertice origem ou destino não existe");
                    }
                    break;
                case 3:
                    System.out.println("----------Buscar vertice----------");
                    System.out.print("Qual nome do cliente do vertice que deseja buscar? ");
                    nome = scanner.nextLine();
                    auxVertice = BuscarVertice(BuscarCliente(nome));
                    if (auxVertice != null) {
                        System.out.printf("\n\t" + auxVertice.getCliente());
                    } else {
                        System.out.println("Vertice não encontrado ou não existe!!!");
                    }
                    break;
                case 4:
                    System.out.println("----------Buscar Aresta----------");
                    System.out.print("Qual nome do cliente do vertice origem? ");
                    origem = scanner.nextLine();
                    System.out.print("Qual nome do cliente do vertice destino? ");
                    destino = scanner.nextLine();
                    ArrayList<Arestas> auxListaAresta = BuscarAresta(BuscarCliente(origem), BuscarCliente(destino));
                    if (auxListaAresta != null) {
                        for (Arestas a : auxListaAresta) {
                            System.out.printf("\n\tCliente Origem: " + a.getInicio().getCliente() + "\n\tCliente Destino: " + a.getFim().getCliente() + "\n\tDistancia (peso) : " + a.getDistancia());
                        }
                    } else {
                        System.out.println("Aresta não encontrada ou não existe!!!");
                    }
                    break;
                case 5:
                    System.out.println("----------Caminho de a ate b----------");
                    System.out.print("Qual nome do cliente do vertice origem? ");
                    origem = scanner.nextLine();
                    System.out.print("Qual nome do cliente do vertice destino? ");
                    destino = scanner.nextLine();
                    Dijkstra(BuscarCliente(origem), BuscarCliente(destino));
                    break;
                case 6:
                    System.out.println("----------Remover Aresta----------");
                    System.out.print("Qual nome do cliente do vertice origem? ");
                    origem = scanner.nextLine();
                    System.out.print("Qual nome do cliente do vertice destino? ");
                    destino = scanner.nextLine();
                    RemoverAresta(BuscarCliente(origem), BuscarCliente(destino));
                    break;
                case 7:
                    System.out.println("----------Busca em largura----------");
                    System.out.println();
                    BuscaEmLargura();
                    break;

                case 8:
                    System.out.println("----------Imprimir Vertices----------");
                    System.out.println();
                    ImprimirListaVertices();
                    break;
                case 9:
                    System.out.println("----------Imprimir Arestas----------");
                    System.out.println();
                    ImprimirListaArestas();
                    break;
                case 10:
                    System.out.println("----------Limpar Grafo----------");
                    System.out.print("\nTem certeza que deseja limpar o grafo? Essa operação limpa todas as listas é arquivos de forma IRREVERSVEL\n\tDeseja continuar? \n\t1 - SIM\n\t2 - NÃO\n\t>");
                    op = scanner.nextInt();
                    if(op == 1){
                        System.out.print("\nLimpando Arquivos....\n\n\n\n");
                        limparGrafo();
                        System.out.println("Arquivos Limpos!!!");
                    }
                    else{
                        System.out.println("Operação cancelada!!!");
                    }
                    break;
                default:
                    if (op != 0) {
                        System.out.println("Opção invalida!");
                    }
                    break;
            }
        } while (op != 0);
    }

    public void salvarArquivoEm(String nomeArquivo) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            outputStream.writeObject(this); // Grava o objeto Grafo atual
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarArquivoDe(String nomeArquivo) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Object obj = inputStream.readObject();
            if (obj instanceof Grafo) {
                Grafo grafoCarregado = (Grafo) obj;
                this.vertices = grafoCarregado.getVertices();
                this.arestas = grafoCarregado.getArestas();
            } else {
                System.out.println("Arquivo não contém um objeto Grafo.");
            }
        } catch (EOFException e) {
            System.out.println("Arquivo vazio.");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void limparArray() {
        this.vertices.clear();
        this.arestas.clear();
    }


    public void limparArquivo() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("dados_grafo.ser"))) {
            outputStream.writeObject(null); // Escreve um objeto nulo para limpar o arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void limparGrafo() {
        limparArray();
        limparArquivo();
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Arestas> getArestas() {
        return arestas;
    }

    public void setArestas(ArrayList<Arestas> arestas) {
        this.arestas = arestas;
    }

}
