
package smartbusiness.negocio;


public class FornecedorEndereco extends Endereco{
    
    private int pk_endereco;
    private int fk_fornecedor;

    
    public FornecedorEndereco() {
    }

    public FornecedorEndereco(int pk_endereco, String logradouro, String bairro, String cidade, String estado, String pais, String cep) {
        super(pk_endereco, logradouro, bairro, cidade, estado, pais, cep);
    }
        
    public FornecedorEndereco(String logradouro, String bairro, String cidade, String estado, String pais, String cep) {
        super(logradouro, bairro, cidade, estado, pais, cep);
    }
    
    public FornecedorEndereco(int fk_fornecedor, int pk_endereco, String logradouro, String bairro, String cidade, String estado, String pais, String cep) {
        super(pk_endereco, logradouro, bairro, cidade, estado, pais, cep);
        this.fk_fornecedor = fk_fornecedor;
    }

    public int getPk_endereco() {
        return pk_endereco;
    }

    public void setPk_endereco(int pk_endereco) {
        this.pk_endereco = pk_endereco;
    }

    public int getFk_fornecedor() {
        return fk_fornecedor;
    }

    public void setFk_fornecedor(int fk_fornecedor) {
        this.fk_fornecedor = fk_fornecedor;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    

    
    
   
   

    
    
}
