
package smartbusiness.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import smartbusiness.negocio.Fornecedor;
import smartbusiness.negocio.FornecedorEndereco;

/** Realiza as responsabilidas comportamentais necessárias para a persistencia
    * de controle dos dados de Fornecedores no banco de dados 
    * @author Joao Vitor
     */

public class FornecedorDAO {
     /**
     * Metodo responsavel para inserção de dados de novos fornecedores no BD
     * @param f objeto da classe Fornecedores    
     * @return Chave primária do fonecedor no BD
     * @throws SQLException 
     */
    public static int create (Fornecedor f) throws SQLException {
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement("INSERT INTO fornecedores(nome, cpf) VALUES(?,?)"
                                                     ,PreparedStatement.RETURN_GENERATED_KEYS);
                
        stm.setString(1, f.getNome());
        stm.setString(2, f.getCpf());
        stm.execute();
        
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        
        int pkfor = rs.getInt("pk_fornecedor");
        f.setPk_fornecedor(pkfor);
        for (FornecedorEndereco aux : f.getEnderecos()) {
            aux.setFk_fornecedor(pkfor);
            FornecedorEnderecoDAO.create(aux);
        }
        return pkfor;
    }
    
    /**
     * Metodo responsavel por fazer a busca de um item na tabela 'fornecedores' no BD
     * @param pk_fornecedor Chave primaria fornecia pelo usuario, na qual fara a busca no BD
     * @return Objeto Fornecedor
     * @throws SQLException 
     */
    public static Fornecedor retrieve (int pk_fornecedor) throws SQLException{
       Connection conn = BancoDados.createConnection();
       String sql = "select * from fornecedores where pk_fornecedor = ?";
       
       PreparedStatement stm = conn.prepareStatement(sql);
       stm.setInt(1, pk_fornecedor);
       stm.execute();
       
       ResultSet rs = stm.getResultSet();
       rs.next();
        Fornecedor f = new Fornecedor(rs.getInt("pk_fornecedor"),
                                rs.getString("nome"),
                                rs.getString("cpf"));
        
        f.setEnderecos(FornecedorEnderecoDAO.retrieveAll(pk_fornecedor));
        return f;
    }
     
    /**
     * Metodo reponsavel por retornar todos os fornecedores 
     * @return ArrayList dos Fornecedores
     * @throws SQLException 
     */
    public static ArrayList<Fornecedor> retrieveAll() throws SQLException{
        ArrayList<Fornecedor> aux = new ArrayList<>();
        Connection conn = BancoDados.createConnection();
        
        String sql = "select * from fornecedores";
        
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()){
            Fornecedor f = new Fornecedor(rs.getInt("pk_fornecedor"),
                                              rs.getString("nome"),
                                              rs.getString("cpf"));
            
            f.setEnderecos(FornecedorEnderecoDAO.retrieveAll(f.getPk_fornecedor()));
            aux.add(f);
            
        }
        return aux;
    }
        
    /**
     * Metodo responsavel por realizar um busca de fornecedores no BD realizando filtros de cidades
     * @param nomeCidade Uma String fornecedia pelo usario, deve ser fornecida uma cidade para fazer o filtro da busca
     * @return Objeto da classe Compra
     * @throws SQLException 
     */
    public static ArrayList<Fornecedor> retrieveByCidades(String nomeCidade ) throws SQLException{
        ArrayList<Fornecedor> aux = new ArrayList<>();
        Connection conn = BancoDados.createConnection();  
        String sql = "SELECT f.* FROM fornecedores AS f LEFT JOIN fornecedores_enderecos AS fe ON f.pk_fornecedor = fe.fk_fornecedor WHERE fe.cidade=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, nomeCidade);
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        while(rs.next()){
           Fornecedor f = new Fornecedor(rs.getInt("pk_fornecedor"),
                                             rs.getString("nome"),
                                             rs.getString("cpf"));
                                             
           
           aux.add(f);
        }
        return aux ;
    }
    
    /**
     * Metodo responsavel por realizar um busca de fornecedores no BD realizando filtros de estado
     * @param nomeEstado Uma String fornecedia pelo usario, deve ser fornecida uma estado para fazer o filtro da busca
     * @return Array de Fornecedores
     * @throws SQLException 
     */ 
    public static ArrayList<Fornecedor> retrieveByEstado(String nomeEstado) throws SQLException{
        ArrayList<Fornecedor> aux = new ArrayList<>();
        Connection conn = BancoDados.createConnection();  
        String sql = "SELECT f.* FROM fornecedores AS f LEFT JOIN fornecedores_enderecos AS fe ON f.pk_fornecedor = fe.fk_fornecedor WHERE fe.estado=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, nomeEstado);
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        while(rs.next()){
           Fornecedor f = new Fornecedor(rs.getInt("pk_fornecedor"),
                                             rs.getString("nome"),
                                             rs.getString("cpf"));
           aux.add(f);
        }
        return aux;
    }
    
     /**
     * Metodo responsavel por atualizar os dados da tabela 'fonecedores' no BD
     * @param f Objeto da classe Fonecedores
     * @throws SQLException 
     */
    public static void update (Fornecedor f) throws SQLException{
        Connection conn =  BancoDados.createConnection();
        String sql = "UPDATE fornecedores SET nome=?, cpf=? where pk_fornecedor=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, f.getNome());
        stm.setString(2, f.getCpf());
        stm.setInt(3, f.getPk_fornecedor());
        
        stm.execute();
        stm.close();
        
        for (FornecedorEndereco aux : f.getEnderecos()) {
            FornecedorEnderecoDAO.update(aux);
        }
        
      }
    
    
    /**
     * Metodo responsavel por excuir um fornecedor do BD
     * @param pk_fornecedor Chave Primária da classe Fornecedor
     * @throws SQLException 
     */
    public static void delete (int pk_fornecedor) throws SQLException{
        Connection conn = BancoDados.createConnection();
        String sql = "DELETE FROM fornecedores WHERE pk_fornecedor=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt(1, pk_fornecedor);
        stm.execute();
        stm.close();
       
    }
}