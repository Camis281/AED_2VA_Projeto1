import java.io.Serializable;
import java.util.Objects;
public class No_cliente  implements Serializable {

    public String nome;
    public String bairro;

    public No_cliente(String nome, String bairro) {
        this.nome = nome;
        this.bairro = bairro;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }


    @Override
    public String toString() {
        return "Cliente: nome = " + nome + ", bairro = " + bairro ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bairro, nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        No_cliente other = (No_cliente) obj;
        return Objects.equals(bairro, other.bairro) && Objects.equals(nome, other.nome);
    }


}
