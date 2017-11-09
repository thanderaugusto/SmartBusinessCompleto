

package smartbusiness.negocio;

import java.sql.Date;
import java.util.ArrayList;


public class Compra {
    private int pk_compra;
    private Fornecedor fornecedor;
    private int fk_fornecedor;
    private int numero;
    private Date data;
    private ArrayList<CompraItem> itens;
    
    public Compra() {
    }

    public Compra(int pk_compra, int numero, Fornecedor fornecedor,  Date data, CompraItem itens) {
        this.pk_compra = pk_compra;
        this.fornecedor = fornecedor;
        this.numero = numero;
        this.data = data;
        getItens().add(itens);
    }

    public Compra(int numero, Fornecedor fornecedor,  Date data, CompraItem itens) {
        this.fornecedor = fornecedor;
        this.numero = numero;
        this.data = data;
        getItens().add(itens);
    }

    public Compra(int pk_compra, int fk_fornecedor, int numero, Date data) {
        this.pk_compra = pk_compra;
        this.fk_fornecedor = fk_fornecedor;
        this.numero = numero;
        this.data = data;
    }
    
    
    
    

    public int getPk_compra() {
        return pk_compra;
    }

    public void setPk_compra(int pk_compra) {
        this.pk_compra = pk_compra;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getFk_fornecedor() {
        return fk_fornecedor;
    }

    public void setFk_fornecedor(int fk_fornecedor) {
        this.fk_fornecedor = fk_fornecedor;
    }
    
    
    
    public ArrayList<CompraItem> getItens() {
        if (itens == null){
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(ArrayList<CompraItem> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "pk_compra: " + pk_compra + 
               ", Fornecedor: " + getFornecedor().getNome() + 
               ", Numero: " + numero + 
               ", Data: " + data +
               "\n";
    }

   
    
    
    
    
    
    
}
