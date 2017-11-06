

package smartbusiness.negocio;

import java.sql.Date;
import java.util.ArrayList;


public class Compras {
    private int pk_compra;
    private Fornecedores fornecedor;
    private int fk_fornecedor;
    private int numero;
    private Date data;
    private ArrayList<ComprasItens> itens;
    
    public Compras() {
    }

    public Compras(int pk_compra, int numero, Fornecedores fornecedor,  Date data, ComprasItens itens) {
        this.pk_compra = pk_compra;
        this.fornecedor = fornecedor;
        this.numero = numero;
        this.data = data;
        getItens().add(itens);
    }

    public Compras(int numero, Fornecedores fornecedor,  Date data, ComprasItens itens) {
        this.fornecedor = fornecedor;
        this.numero = numero;
        this.data = data;
        getItens().add(itens);
    }

    public Compras(int pk_compra, int fk_fornecedor, int numero, Date data) {
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

    public Fornecedores getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedores fornecedor) {
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

    public ArrayList<ComprasItens> getItens() {
        if (itens == null){
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(ArrayList<ComprasItens> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "Compras{" + "pk_compra=" + pk_compra + ", fornecedor=" + fornecedor + ", numero=" + numero + ", data=" + data + ", itens=" + itens + '}';
    }
    
    
    
    
}
