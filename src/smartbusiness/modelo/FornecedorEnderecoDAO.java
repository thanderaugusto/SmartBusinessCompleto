
package smartbusiness.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import smartbusiness.negocio.FornecedorEndereco;


public class FornecedorEnderecoDAO {

    public static int create(FornecedorEndereco fe) throws SQLException{ 
            
        
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement("INSERT INTO fornecedores_enderecos"
                + "(fk_fornecedor, logradouro, bairro, cidade, "
                + "estado, pais, cep) VALUES (?, ?, ?, ?, ?, ?, ?);"
                ,PreparedStatement.RETURN_GENERATED_KEYS);
        
       
        stm.setInt(1, fe.getFk_fornecedor());
        stm.setString(2, fe.getLogradouro());
        stm.setString(3, fe.getBairro());
        stm.setString(4, fe.getCidade());
        stm.setString(5, fe.getEstado());
        stm.setString(6, fe.getPais());
        stm.setString(7, fe.getCep());
        
        stm.execute();
        
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        fe.setPk_endereco(1);
        
        stm.close();
                
        return fe.getPk_endereco();
    
        
    }
    
    public static FornecedorEndereco retrieve (int pk_endereco) throws SQLException{
        Connection conn = BancoDados.createConnection();
         PreparedStatement stm = conn.prepareStatement("SELECT * FROM fornecedores_enderecos WHERE pk_endereco = ?");
        
        stm.setInt(1, pk_endereco);
        
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        
        rs.next();
        return new FornecedorEndereco(rs.getInt("pk_endereco"), rs.getInt("fk_fornecedor"), rs.getString("logradouro"),
        rs.getString("bairro"), rs.getString("cidade"), rs.getString("estado"), rs.getString("pais"), rs.getString("cep"));
           
     
       }
     public static ArrayList<FornecedorEndereco> retrieveAll(int fk_fornecedor) throws SQLException{
         
        ArrayList<FornecedorEndereco> aux= new ArrayList<>();
         
        Connection conn = BancoDados.createConnection();
         
        String sql = "SELECT * FROM fornecedores_enderecos where fk_fornecedor = ?";
         
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, fk_fornecedor);
        
        stm.execute();

        ResultSet rs = stm.getResultSet();
         
         while (rs.next()){
             FornecedorEndereco fe = new FornecedorEndereco(rs.getInt("pk_endereco"),
                                                                  rs.getInt("fk_fornecedor"),
                                                                  rs.getString("logradouro"),
                                                                  rs.getString("bairro"),
                                                                  rs.getString("cidade"),
                                                                  rs.getString("estado"),
                                                                  rs.getString("pais"),
                                                                  rs.getString("cep")); 
             
             aux.add(fe);
             
         }
         
        return aux;
               
    }

    public static void update(FornecedorEndereco fe) throws SQLException{
        if (fe.getPk_endereco()==0){
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }
        
        String sql = "UPDATE fornecedores_enderecos SET fk_fornecedor=?, logradouro=?, bairro=?, cidade=?, estado=?, pais=?, cep=? WHERE pk_endereco=?";
        
        Connection conn = BancoDados.createConnection();
        
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt (1, fe.getFk_fornecedor());
        stm.setString(2, fe.getLogradouro());
        stm.setString(3, fe.getBairro());
        stm.setString(4, fe.getCidade());
        stm.setString(5, fe.getEstado());
        stm.setString(6, fe.getPais());
        stm.setString(7, fe.getCep());
        
        stm.execute();
        stm.close();
        
        

    }
    
    public static void delete (int pk_endereco) throws SQLException{
        if (pk_endereco==0){
           throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }
        
        String sql = "DELETE FROM forncedores_enderecos WHERE pk_forncedores=?";
        
        Connection conn = BancoDados.createConnection();
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, pk_endereco);
            stm.execute();
            stm.close();
        }
                   
    }

    
  
}
