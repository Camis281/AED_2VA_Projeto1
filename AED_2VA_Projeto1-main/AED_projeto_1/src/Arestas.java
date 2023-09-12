import java.io.Serializable;
import java.util.Objects;

public class Arestas  implements Serializable {
    private Double distancia;//peso
    private Vertice inicio;
    private Vertice fim;

    public Arestas(Double distancia, Vertice inicio, Vertice fim) {
        super();
        this.distancia = distancia;
        this.inicio = inicio;
        this.fim = fim;
    }
    public Double getDistancia() {
        return distancia;
    }
    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }
    public Vertice getInicio() {
        return inicio;
    }
    public void setInicio(Vertice inicio) {
        this.inicio = inicio;
    }
    public Vertice getFim() {
        return fim;
    }
    public void setFim(Vertice fim) {
        this.fim = fim;
    }
    @Override
    public int hashCode() {
        return Objects.hash(distancia);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Arestas other = (Arestas) obj;
        return Objects.equals(distancia, other.distancia) && Objects.equals(fim, other.fim)
                && Objects.equals(inicio, other.inicio);
    }
    @Override
    public String toString() {
        return "Arestas [distancia=" + distancia + ", inicio=" + inicio + ", fim=" + fim + "]";
    }

}
