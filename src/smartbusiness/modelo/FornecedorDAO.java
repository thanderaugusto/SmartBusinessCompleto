
package smartbusiness.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import smartbusiness.negocio.Fornecedor;
import smartbusiness.negocio.FornecedorEndereco;


public class FornecedorDAO {
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
        
        f.setEnderecos(FornecedorEnderecoDAO.retrieveAll(f.getPk_fornecedor()));

        
        return f;
        
    }
       
    public static ArrayList<Fornecedor> retrieveAll() throws SQLException{
        ArrayList<Fornecedor> aux = new ArrayList<>();
        Connection conn = BancoDados.createConnection();
        
        String sql = "select * from fornecedores";
        
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()){
            Fornecedor f = new Fornecedor(rs.getInt("pk_fornecedor"),
                                              rs.getString("nome"),
                                              rs.getString("cpf"));
            
            f.setEnderecos(FornecedorEnderecoDAO.retrieveAll(rs.getInt("pk_fornecedor")));
            aux.add(f);
            
        }
        return aux;
    }
    
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
    

    public static void update (Fornecedor f) throws SQLException{
        Connection conn =  BancoDados.createConnection();
        String sql = "UPDATE fornecedores SET nome=?, cpf=? where pk_fornecedor=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, f.getNome());
        stm.setString(2, f.getCpf());
        stm.setInt(3, f.getPk_fornecedor());
        
        stm.execute();
        stm.close();
      }
    
    public static void delete (int pk_fornecedor) throws SQLException{
        Connection conn = BancoDados.createConnection();
        String sql = "DELETE FROM fornecedores WHERE pk_fonecedor=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt(1, pk_fornecedor);
        stm.execute();
        stm.close();
       
    }
    
}

