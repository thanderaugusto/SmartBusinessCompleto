

package smartbusiness.negocio;

import java.util.ArrayList;


public class Fornecedor {
    private int pk_fornecedor;
    private String nome;
    private String cpf;
    private ArrayList<FornecedorEndereco> enderecos; 

    
    
    public Fornecedor() {
    }

    public Fornecedor(String nome, String cpf, FornecedorEndereco enderecos) {
        this.nome = nome;
        this.cpf = cpf;
        getEnderecos().add(enderecos);
    }
    
    

    public Fornecedor(int pk_fornecedor, String nome, String cpf) {
        this.pk_fornecedor = pk_fornecedor;
        this.nome = nome;
        this.cpf = cpf;
    }

    public Fornecedor(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
    
    
    public ArrayList<FornecedorEndereco> getEnderecos() {
        if(enderecos == null){
            enderecos = new ArrayList<>();
        }
        return enderecos;
    }

    public void setEnderecos(ArrayList<FornecedorEndereco> enderecos) {
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
        return "pk_fornecedor = " + pk_fornecedor + 
               ", nome = " + nome + 
               ", cpf = " + cpf +
               ", enderecos = " + getEnderecos() +"\n\n";
    }

    
    
    
}
