

package smartbusiness.negocio;


public class CompraItem {
    private int pk_item;
    private int fk_compra;
    private Produto produtos ;
    private int fk_produto;
    private float qtde;
    private float valorUnitario;

    public CompraItem() {
    }

    public CompraItem(Produto produtos, float qtde, float valorUnitario) {
        this.produtos = produtos;
        this.qtde = qtde;
        this.valorUnitario = valorUnitario;
    }

    
    public CompraItem(int pk_item, int fk_compra, Produto produtos, float qtde, float valorUnitario) {
        this.pk_item = pk_item;
        this.fk_compra = fk_compra;
        this.produtos = produtos;
        this.qtde = qtde;
        this.valorUnitario = valorUnitario;
    }

    public CompraItem(int pk_item, int fk_compra, int fk_produto, float qtde, float valorUnitario) {
        this.pk_item = pk_item;
        this.fk_compra = fk_compra;
        this.fk_produto = fk_produto;
        this.qtde = qtde;
        this.valorUnitario = valorUnitario;
    }
    
    

    public CompraItem(int fk_compra, int fk_produto, float qtde, float valorUnitario) {
        this.fk_compra = fk_compra;
        this.fk_produto = fk_produto;
        this.qtde = qtde;
        this.valorUnitario = valorUnitario;
    }

    public int getPk_item() {
        return pk_item;
    }

    public void setPk_item(int pk_item) {
        this.pk_item = pk_item;
    }

    public int getFk_compra() {
        return fk_compra;
    }

    public void setFk_compra(int fk_compra) {
        this.fk_compra = fk_compra;
    }

    public int getFk_produto() {
        return fk_produto;
    }

    public void setFk_produto(int fk_produto) {
        this.fk_produto = fk_produto;
    }
    

    public Produto getProdutos() {
        return produtos;
    }

    public void setProdutos(Produto produtos) {
        this.produtos = produtos;
    }

    

    public float getQtde() {
        return qtde;
    }

    public void setQtde(float qtde) {
        this.qtde = qtde;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return "pk_item: " + pk_item + 
                ", fk_compra: " + fk_compra + 
                ", Produtos: " + getProdutos().getNome() + 
                ", Quantidade: " + qtde + 
                ", Valor Unitário: " + valorUnitario +"\n\n";
    }

   

    
    
    
}
