
package smartbusiness.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import smartbusiness.negocio.Fornecedores;
import smartbusiness.negocio.FornecedoresEnderecos;


public class FornecedoresDAO {
    public static int create (Fornecedores f) throws SQLException {
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

        for (FornecedoresEnderecos aux : f.getEnderecos()) {
            
            aux.setFk_fornecedor(pkfor);
            FornecedoresEnderecosDAO.create(aux);
        }

        return pkfor;

    }
    
    public static Fornecedores retrieve (int pk_fornecedor) throws SQLException{
       Connection conn = BancoDados.createConnection();
       String sql = "select * from fornecedores where pk_fornecedor = ?";
       
       PreparedStatement stm = conn.prepareStatement(sql);
       stm.setInt(1, pk_fornecedor);
       stm.execute();
       
       ResultSet rs = stm.getResultSet();
       rs.next();
       
        
        Fornecedores f = new Fornecedores(rs.getInt("pk_fornecedor"),
                                rs.getString("nome"),
                                rs.getString("cpf"));
        
        f.setEnderecos(FornecedoresEnderecosDAO.retrieveAll(pk_fornecedor));

        
        return f;
        
    }
       
    public static ArrayList<Fornecedores> retrieveAll() throws SQLException{
        ArrayList<Fornecedores> aux = new ArrayList<>();
        Connection conn = BancoDados.createConnection();
        
        String sql = "select * from fornecedores";
        
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()){
            Fornecedores f = new Fornecedores(rs.getInt("pk_fornecedor"),
                                              rs.getString("nome"),
                                              rs.getString("cpf"));
            
            f.setEnderecos(FornecedoresEnderecosDAO.retrieveAll(f.getPk_fornecedor()));
            aux.add(f);
            
        }
        return aux;
    }
    
    public static ArrayList<Fornecedores> retrieveByCidades(String cidade ) throws SQLException{
        ArrayList<Fornecedores> aux = new ArrayList<>();
        Connection conn = BancoDados.createConnection();  
        String sql = "SELECT f.* FROM fornecedores AS f LEFT JOIN fornecedores_enderecos AS fe ON f.pk_fornecedor = fe.fk_fornecedor WHERE fe.cidade=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, cidade);
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        while(rs.next()){
           Fornecedores f = new Fornecedores(rs.getInt("pk_fornecedor"),
                                             rs.getString("nome"),
                                             rs.getString("cpf"));
                                             
           
           aux.add(f);
        }
        return aux ;
    }
    
    public static ArrayList<Fornecedores> retrieveByEstado(String estado) throws SQLException{
        ArrayList<Fornecedores> aux = new ArrayList<>();
        Connection conn = BancoDados.createConnection();  
        String sql = "SELECT f.* FROM fornecedores AS f LEFT JOIN fornecedores_enderecos AS fe ON f.pk_fornecedor = fe.fk_fornecedor WHERE fe.estado=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, estado);
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        while(rs.next()){
           Fornecedores f = new Fornecedores(rs.getInt("pk_fornecedor"),
                                             rs.getString("nome"),
                                             rs.getString("cpf"));
           aux.add(f);
        }
        return aux;
    }
    

    public static void update (Fornecedores f) throws SQLException{
        Connection conn =  BancoDados.createConnection();
        String sql = "UPDATE fornecedor SET nome=?, cpf=? where pk_fornecedor=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setString(1, f.getNome());
        stm.setString(2, f.getCpf());
        stm.setInt(3, f.getPk_fornecedor());
        
        stm.execute();
        stm.close();
      }
    
    public static void delete (Fornecedores f) throws SQLException{
        Connection conn = BancoDados.createConnection();
        String sql = "DELETE FROM fornecedores WHERE pk_fonecedor=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt(1, f.getPk_fornecedor());
        stm.execute();
        stm.close();
       
    }
    
}

