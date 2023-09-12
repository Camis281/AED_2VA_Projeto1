import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Vertice  implements Serializable {

    private No_cliente cliente;
    private ArrayList<Arestas> entrada;
    private ArrayList<Arestas> saida;
    private Vertice predecessor;
    private Double distanciaMinima = Double.MAX_VALUE;

    public Vertice(No_cliente cliente) {
        super();
        this.cliente = cliente;
        this.entrada = new ArrayList<Arestas>();
        this.saida = new ArrayList<Arestas>();
    }

    public void AdicionarArestaEntrada (Arestas aresta) {
        this.entrada.add(aresta);
    }

    public void AdicionarArestaSaida (Arestas aresta) {
        this.saida.add(aresta);
    }

    public No_cliente getCliente() {
        return cliente;
    }

    public void setCliente(No_cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Arestas> getEntrada() {
        return entrada;
    }

    public void setEntrada(ArrayList<Arestas> entrada) {
        this.entrada = entrada;
    }

    public ArrayList<Arestas> getSaida() {
        return saida;
    }

    public void setSaida(ArrayList<Arestas> saida) {
        this.saida = saida;
    }

    public Double getDistanciaMinima() {
        return distanciaMinima;
    }

    public void setDistanciaMinima(Double distanciaMinima) {
        this.distanciaMinima = distanciaMinima;
    }

    public Vertice getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertice predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertice other = (Vertice) obj;
        return Objects.equals(cliente, other.cliente) && Objects.equals(entrada, other.entrada)
                && Objects.equals(saida, other.saida);
    }

    @Override
    public String toString() {
        return "Vertice cliente =" + cliente;
    }



}
