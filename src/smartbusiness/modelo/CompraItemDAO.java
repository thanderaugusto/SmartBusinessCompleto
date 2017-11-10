

package smartbusiness.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import smartbusiness.negocio.CompraItem;


public class CompraItemDAO {
    
 /**
 * Realiza as resposabilidades comportamentais necessárias para a persistencia
 * de controle de itens de compras no banco de dados
 *
 * @author Thander Augusto
 */
    
    
    /** Método responsável pela inserção dos dados da compra no banco de dados
     * @param ci Objeto da classe CompraItem
     * @return ci.getPk_item() Chave primária fornecida pelo banco de dados
     * @throws SQLException lança uma exceção
     * 
     */
    public static int create(CompraItem ci) throws SQLException{
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement("INSERT INTO compras_itens(fk_compra, fk_produto, qtd, valor_unitario)\n" +
"VALUES (?, ?, ?, ?);",PreparedStatement.RETURN_GENERATED_KEYS);
        
       
        stm.setInt(1, ci.getFk_compra());
        stm.setInt(2, ci.getProdutos().getPk_produto());
        stm.setFloat(3, ci.getQtde());
        stm.setFloat(4, ci.getValorUnitario());
        
        stm.execute();
        
        ResultSet rs = stm.getGeneratedKeys();
        
        
        rs.next();
        ci.setPk_item(rs.getInt(1));
        
        
        stm.close();
       
        return ci.getPk_item();
    }
    /** Método responsável por realizar uma busca no banco de dados por uma chave primária informada
     * @param pk_item Chave primaria fornecia pelo usuario, para realizar a pesquisa no banco de dados
     * @return Objeto da classe CompraItem
     * @throws SQLException lança uma exceção
     * @throws ClassNotFoundException carrega uma classe via String 
     */
    public static CompraItem retrieve(int pk_item) throws SQLException, ClassNotFoundException{
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM compras_itens WHERE pk_item = ?");
        stm.setInt(1, pk_item);
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        rs.next();
        
        CompraItem ci =  new CompraItem(rs.getInt("pk_item"), 
                                rs.getInt("fk_compra"),
                                rs.getInt("fk_produto"),
                                rs.getFloat("qtd"), 
                                rs.getFloat("valor_unitario"));
        ci.setProdutos(ProdutoDAO.retrieve(rs.getInt("fk_produto")));
        
        return ci;
    }
    
    /** Método responsável por buscar e retornar todos os itens do CompraItem
     * @return ArrayList de CompraItem
     * @throws SQLException lança uma exceção
     * @throws ClassNotFoundException carrega uma classe via String
     */
    public static ArrayList<CompraItem> retrieveAll() throws SQLException, ClassNotFoundException{
        
        ArrayList<CompraItem> aux = new ArrayList<>();
        
        Connection conn = BancoDados.createConnection();
        
        String sql = "SELECT * FROM compras_itens";
        
        ResultSet rs = conn.createStatement().executeQuery(sql);
        
        while(rs.next()) {
            CompraItem ci = new CompraItem(rs.getInt("pk_item"), 
                                               rs.getInt("fk_compra"),
                                               rs.getInt("fk_produto"),
                                               rs.getFloat("qtd"),
                                               rs.getFloat("valor_unitario"));
            ci.setProdutos(ProdutoDAO.retrieve(rs.getInt("fk_produto")));
             aux.add(ci);
        }
        return aux;
    }
        /** Método responsável por buscar e retornar todos os itens do CompraItem
        * @param fk_compra Chave estrangeira fornecia pelo usuario, para realizar a pesquisa no banco de dados 
        * @return ArrayList de CompraItem por compra
        * @throws SQLException lança uma exceção
        * @throws ClassNotFoundException carrega uma classe via String
        */
        public static ArrayList<CompraItem> retrieveAll(int fk_compra) throws SQLException, ClassNotFoundException{
        
        ArrayList<CompraItem> aux = new ArrayList<>();
        
        Connection conn = BancoDados.createConnection();
        
        String sql = "SELECT * FROM compras_itens where fk_compra=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, fk_compra);
        
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        
        while(rs.next()) {
            CompraItem ci = new CompraItem(rs.getInt("pk_item"), 
                                               rs.getInt("fk_compra"),
                                               rs.getInt("fk_produto"),
                                               rs.getFloat("qtd"),
                                               rs.getFloat("valor_unitario"));
            ci.setProdutos(ProdutoDAO.retrieve(rs.getInt("fk_produto")));
             aux.add(ci);
        }
        
                
        return aux;
    }
        
    /** Método responsável por retornar as somas e média dos produtos de acordo com sua chave estrangeira.
    * @param fk_produto Chave estrangeira fornecia pelo usuario, para realizar a pesquisa no banco de dados
    * @return Retorna resultado do produto buscado
    * @throws SQLException lança uma exceção
    */
    public static String retrieveByProduto(int fk_produto) throws SQLException{
        
        
        Connection conn = BancoDados.createConnection();
        
        String sql = "SELECT nome, sum(ci.valor_unitario) soma_valores, avg(ci.valor_unitario) media_valor, \n" +
                                                      "sum(ci.qtd) soma_produto, avg(ci.qtd) media_produto\n" +
                                                      "FROM produtos p join compras_itens ci on\n" +
                                                      "p.pk_produto=ci.fk_produto where fk_produto =?\n" +
                                                      "GROUP BY nome";     
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt(1, fk_produto);
        
        
        stm.execute();
        ResultSet rs = stm.getResultSet();
        
       rs.next();
           String nome = rs.getString("nome");
           float somaUnitario = rs.getFloat("soma_valores");
           float mediaUnitario = rs.getFloat("media_valor");
           float somaQtdProduto = rs.getFloat("soma_produto");
           float mediaQtdProduto = rs.getFloat("media_produto");
           
           return ("\nNome: "+nome+"\n"
                    +"Soma Valores Unitario R$: "+somaUnitario+"\n"
                    +"Media Valores Unitario: "+mediaUnitario+"\n"
                    +"Soma Quantidade Produto: "+somaQtdProduto+"\n"
                    +"Media Quantidade Produto: "+mediaQtdProduto);
       
    }
    
    
     /**Método responsável por alterações de alguns dados de uma compraitem no banco de dados(Especificada pela chave primária).
     * @param ci Objeto do classe CompraItem.
     * @throws SQLException lança uma exceção
     */
    public static void update(CompraItem ci) throws SQLException {
        if (ci.getPk_item()==0){
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }
        
        String sql = "UPDATE compras SET fk_compra=?, fk_produto=?, qtd=?, valor_unitario=? WHERE pk_item=?";
        
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt(1, ci.getFk_compra());
        stm.setInt(2, ci.getFk_produto());
        stm.setFloat(3, ci.getQtde());
        stm.setFloat(4, ci.getValorUnitario());
        
        stm.execute();
        stm.close();
    }
    /* Método responsável por excluir uma compra no banco de dados (Especificada pela chave primário).
     * @param pk_item Parâmetro da classe CompraItem.
     * @throws SQLException lança uma exceção
     */ 
    public static void delete(int pk_item) throws SQLException {
        if (pk_item==0){
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }
        
        String sql = "delete from compras_itens where pk_item=?";
        
        Connection conn = BancoDados.createConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt(1, pk_item);       
        stm.execute();
        stm.close();   
        
        
    }

}
