    
package smartbusiness.modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import smartbusiness.negocio.Compra;
import smartbusiness.negocio.CompraItem;
import smartbusiness.negocio.Fornecedor;


 /** Realiza as responsabilidas comportamentais necessárias para a persistencia
    * de controle dos dados de compra no banco de dados 
    * @author Rharison Lucas
     */

public class CompraDAO {
    
    
    /** Método responsável pela inserção dos dados da compra no banco de dados
     * @param c Objeto da classe Compra
     * @return Chave primária fornecida pelo banco de dados
     * @throws SQLException 
     * 
     */
    public static int create(Compra c) throws SQLException{
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement("INSERT INTO compras(fk_fornecedor, numero, datas) VALUES (?, ?, ?);"
                                                      ,PreparedStatement.RETURN_GENERATED_KEYS);
        
        stm.setInt(1, c.getFornecedor().getPk_fornecedor());
        stm.setInt(2, c.getNumero());
        stm.setDate(3, c.getData());
        
        stm.execute();
        
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        
        int pkCompra = rs.getInt("pk_compra");
        
        c.setPk_compra(pkCompra);
        
         for (CompraItem aux : c.getItens()) {
             
            aux.setFk_compra(pkCompra);

            CompraItemDAO.create(aux);
        }
         
        
        stm.close();
       
        return pkCompra;
    } 
    
    /** Método responsável por realizar uma busca no banco de dados por uma chave primária informada.
     * 
     * @param pk_compra Chave primária para realizar a pesquisa no banco de dados
     * @return Objeto da classe Compra
     * @throws SQLException 
     */
    public static Compra retrieve(int pk_compra) throws SQLException, ClassNotFoundException{
        
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM compras WHERE pk_compra = ?");
        
        stm.setInt(1, pk_compra);
        
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        
        rs.next();
        Compra c = new Compra(rs.getInt("pk_compra"), 
                                rs.getInt("fk_fornecedor"), 
                                rs.getInt("numero"), 
                                rs.getDate("datas"));
        
        c.setFornecedor(FornecedorDAO.retrieve(rs.getInt("fk_fornecedor")));
        c.setItens(CompraItemDAO.retrieveAll(rs.getInt("pk_compra")));
        
        return c;
    }
    
    
    /**
     * Método responsável por buscar e retornar todas as compras feitas.
     * @return ArrayList de Compra
     * @throws SQLException 
     */
    public static ArrayList<Compra> retrieveAll() throws SQLException, ClassNotFoundException{
        
        ArrayList<Compra> aux = new ArrayList<>();
        
        Connection conn = BancoDados.createConnection();
        
        String sql = "select * from compras";
              
        ResultSet rs = conn.createStatement().executeQuery(sql);
        
        while (rs.next()){
            Compra c = new Compra(rs.getInt("pk_compra"), 
                                    rs.getInt("fk_fornecedor"), 
                                    rs.getInt("numero"), 
                                    rs.getDate("datas"));   
            c.setFornecedor(FornecedorDAO.retrieve(rs.getInt("fk_fornecedor")));
            c.setItens(CompraItemDAO.retrieveAll(rs.getInt("pk_compra")));
            aux.add(c);
            
            
            
        }

        return aux;
    }
    /**
     * Método responsável por fazer um relátorio de todas as compras realizadas comparando se elas já foram totalmente pagas.
     * @return
     * @throws SQLException 
     */
    public static ArrayList<Compra> retrieveRelatorioCompras() throws SQLException{
           ArrayList<Compra> aux = new ArrayList<>();
                      Connection conn = BancoDados.createConnection();
           String sql = "select  f.nome , sum(ci.valor_unitario) valor_total, c.datas, fs.valor ,\n" +
                        "sum(ci.valor_unitario) < fs.valor  totalmente_paga  from \n" +
                        "compras c   join compras_itens ci on c.pk_compra = ci.fk_compra \n" +
                        " join fornecedores f on  c.fk_fornecedor = f.pk_fornecedor\n" +
                        " join  financeiros_saidas fs on fs.fk_compra = c.pk_compra \n" +
                        "GROUP BY f.nome ,datas, valor";
           
           ResultSet rs = conn.createStatement().executeQuery(sql);
           
           while (rs.next()) {
               
                String nome = rs.getString("nome");
                  Date data = rs.getDate("datas");
                  float valorsoma = rs.getFloat("valor_total");
                  float valor = rs.getFloat("valor");
                  boolean test = rs.getBoolean("totalmente_paga");
                  System.out.println("=============================");
               System.out.println("Nomes Fornecedores : "+ nome);
               System.out.println("datas : "+ data);
               System.out.println("Valor de Soma : "+ valorsoma );
               System.out.println("Valor : "+ valor);
               System.out.println("Totalmente Pago:  "+ test);
        }
        return aux;
      
    }
    
    public static ArrayList<Compra> retrieveByFornecedores(int fk_fornecedor) throws SQLException, ClassNotFoundException{
           ArrayList<Compra> aux = new ArrayList<>();
           
           Connection conn = BancoDados.createConnection();
           
           String sql = "select * from compras where fk_fornecedor = ?";
           
           PreparedStatement stm = conn.prepareStatement(sql);
           
           stm.setInt(1, fk_fornecedor);
           stm.execute();
              
           ResultSet rs = stm.getResultSet();
           
           while (rs.next()) {
               Compra c = new Compra(rs.getInt("pk_compra"), 
                                       rs.getInt("fk_fornecedor"), 
                                       rs.getInt("numero"),
                                       rs.getDate("datas"));
               c.setFornecedor(FornecedorDAO.retrieve(rs.getInt("fk_fornecedor")));
               c.setItens(CompraItemDAO.retrieveAll(rs.getInt("pk_compra")));
               aux.add(c);
               
        }
        return aux;
    }
    
    /**
     * Método responsável por buscar e retornar as compras realizadas dentro de um intervalo de datas.
     * @param dataInicial Data inicial para realizar a busca 
     * do tipo LocalDate. Para definir a data utilize LocalDate.of(aaaa,MM,dd).
     * @param dataFinal Data final para realizar a busca 
     * do tipo LocalDate. Para definir a data utilize LocalDate.of(aaaa,MM,dd)
     * @return ArrayList de Compra
     * @throws SQLException 
     */
    public static ArrayList<Compra> retriaveByData(Date dataInicial, Date dataFinal) throws SQLException{
        if (dataInicial == null || dataFinal == null){
            System.out.println("Digite as datas corretamente!");
        }
               
        ArrayList<Compra> aux = new ArrayList<>();
        
        Connection conn = BancoDados.createConnection();
        String sql = "select * from compras where datas between ? and ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setDate(1, dataInicial);
        stm.setDate(2, dataFinal);
        
        
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()){
            Compra c = new Compra(rs.getInt("pk_compra"), 
                                    rs.getInt("fk_fornecedor"), 
                                    rs.getInt("numero"), 
                                    rs.getDate("datas"));
            aux.add(c);
        }
        
        return aux;
    }
    
    
    /**
     * Método responsável por atualizar os dados de uma compra no banco de dados(Especificada pela chave primária).
     * @param c Objeto do classe Compra.
     * @throws SQLException 
     */
     public static void update(Compra c) throws SQLException{
        if (c.getPk_compra()==0){
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }
        
        String sql = "UPDATE compras SET fk_fornecedor=?, numero=?, datas=? WHERE pk_compra=?";
        
        Connection conn = BancoDados.createConnection();
        
        
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(4, c.getPk_compra());
        stm.setInt(1, c.getFk_fornecedor());
        stm.setInt(2, c.getNumero());
        stm.setDate(3, c.getData());
        
        stm.execute();
        stm.close();
        
        
        for (CompraItem aux : c.getItens()) {
            CompraItemDAO.update(aux);
        }
    }
    /**
     * Método responsável por excluir uma compra no banco de dados (Especificada pela chave primária).
     * @param c Objeto da classe Compra.
     * @throws SQLException 
     */ 
    public static void delete(int pk_compra) throws SQLException{
        if (pk_compra==0){
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }

        String sql = "delete from compras where pk_compra=?";
        
        Connection conn = BancoDados.createConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt(1, pk_compra);       
        stm.execute();
        stm.close();        
    }
    
    
}