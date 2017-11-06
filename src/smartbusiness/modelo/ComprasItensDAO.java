

package smartbusiness.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import smartbusiness.negocio.ComprasItens;


public class ComprasItensDAO {
    
 /**
 * Realiza as resposabilidades comportamentais necessárias para a persistencia
 * de controle de itens de compras no banco de dados
 *
 * @author Thander Augusto
 */
    
    
    /** Método para inserção de dados no banco de dados do Compras Itens*/
    public static int create(ComprasItens ci) throws SQLException{
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement("INSERT INTO compras_itens(fk_compra, fk_produto, qtd, valor_unitario)\n" +
"VALUES (?, ?, ?, ?);",PreparedStatement.RETURN_GENERATED_KEYS);
        
       
        stm.setInt(1, ci.getFk_compra());
        stm.setInt(2, ci.getFk_produto());
        stm.setFloat(3, ci.getQtde());
        stm.setFloat(4, ci.getValorUnitario());
        
        stm.execute();
        
        ResultSet rs = stm.getGeneratedKeys();
        
        
        rs.next();
        ci.setPk_item(rs.getInt(1));
        
        
        stm.close();
       
        return ci.getPk_item();
    }
    /** Método para retornar dados de um id específico do banco de dados*/
    public static ComprasItens retrieve(int pk_item) throws SQLException{
        
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM compras_itens WHERE pk_item = ?");
        
        stm.setInt(1, pk_item);
        
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        
        rs.next();
        return new ComprasItens(rs.getInt("pk_item"), rs.getInt("fk_compra"), rs.getInt("fk_produto"), rs.getFloat("qtd"), rs.getFloat("valor_unitario"));
    }
    
    /** Método para retornar dados de todos itens do banco de dados*/
    public static ArrayList<ComprasItens> retrieveAll() throws SQLException{
        
        ArrayList<ComprasItens> aux = new ArrayList<>();
        
        Connection conn = BancoDados.createConnection();
        
        String sql = "SELECT * FROM compras_itens";
        
        ResultSet rs = conn.createStatement().executeQuery(sql);
        
        while(rs.next()) {
            ComprasItens ci = new ComprasItens(rs.getInt("pk_item"), 
                                               rs.getInt("fk_compra"),
                                               rs.getInt("fk_produto"),
                                               rs.getFloat("qtd"),
                                               rs.getFloat("valor_unitario"));
             aux.add(ci);
        }
                
        return aux;
    }
     /** Método para retornar Relatorio de acordo com o produto e a compra do banco de dado*/
    public static ArrayList<ComprasItens> retrieveByProduto() throws SQLException{
        
        ArrayList<ComprasItens> aux = new ArrayList<>();
        
        Connection conn = BancoDados.createConnection();
        
        String sql = "SELECT nome, sum(ci.valor_unitario) soma_valores, avg(ci.valor_unitario) media_valor, \n" +
                                                      "sum(ci.qtd) soma_produto, avg(ci.qtd) media_produto\n" +
                                                      "FROM produtos p join compras_itens ci on\n" +
                                                      "p.pk_produto=ci.fk_produto\n" +
                                                      "GROUP BY nome";     
        
        ResultSet rs = conn.createStatement().executeQuery(sql);
        
        while (rs.next()) {
           String nome = rs.getString("nome");
           float somaUnitario = rs.getFloat("soma_valores");
           float mediaUnitario = rs.getFloat("media_valor");
           float somaQtdProduto = rs.getFloat("soma_produto");
           float mediaQtdProduto = rs.getFloat("media_produto");
           System.out.println("\n=============================");
            System.out.println("\nNome: "+nome+"\n"
                    +"Soma Valores Unitario R$: "+somaUnitario+"\n"
                    +"Media Valores Unitario: "+mediaUnitario+"\n"
                    +"Soma Quantidade Produto: "+somaQtdProduto+"\n"
                    +"Media Quantidade Produto: "+mediaQtdProduto);
            
        }
        
        return aux;
        
    }
    
    
     /** Método para alterações de algum dado do banco de dados*/
    public static void update(ComprasItens ci) throws SQLException {
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
    
    public static void delete(ComprasItens ci) throws SQLException {
        if (ci.getPk_item()==0){
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }
        
        String sql = "delete from compras_itens where pk_item=?";
        
        Connection conn = BancoDados.createConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt(1, ci.getPk_item());       
        stm.execute();
        stm.close();   
        
        
    }
}
