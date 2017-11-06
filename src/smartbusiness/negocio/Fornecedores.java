

package smartbusiness.negocio;

import java.util.ArrayList;


public class Fornecedores {
    private int pk_fornecedor;
    private String nome;
    private String cpf;
    private ArrayList<FornecedoresEnderecos> enderecos; 

    
    
    public Fornecedores() {
    }

    public Fornecedores(String nome, String cpf, FornecedoresEnderecos enderecos) {
        this.nome = nome;
        this.cpf = cpf;
        getEnderecos().add(enderecos);
    }
    
    

    public Fornecedores(int pk_fornecedor, String nome, String cpf) {
        this.pk_fornecedor = pk_fornecedor;
        this.nome = nome;
        this.cpf = cpf;
    }

    public Fornecedores(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
    
    
    public ArrayList<FornecedoresEnderecos> getEnderecos() {
        if(enderecos == null){
            enderecos = new ArrayList<>();
        }
        return enderecos;
    }

    public void setEnderecos(ArrayList<FornecedoresEnderecos> enderecos) {
        this.enderecos = enderecos;
    }
    
    

    public int getPk_fornecedor() {
        return pk_fornecedor;
    }

    public void setPk_fornecedor(int pk_fornecedor) {
        this.pk_fornecedor = pk_fornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Fornecedores{" + "pk_fornecedor=" + pk_fornecedor + ", nome=" + nome + ", cpf=" + cpf + '}';
    }
    
    
}
