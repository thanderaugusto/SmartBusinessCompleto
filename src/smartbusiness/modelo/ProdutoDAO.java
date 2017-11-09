/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartbusiness.modelo;


import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import smartbusiness.negocio.Produto;

public class ProdutoDAO {

    public static int create(Produto p) throws SQLException {
        Connection conn = BancoDados.createConnection();

        PreparedStatement stm
                = conn.prepareStatement("insert into produtos (nome, estoque_minimo,qtd_estoque) values (?,?,?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);

        stm.setString(1, p.getNome());
        stm.setInt(2, p.getEstoque_minimo());
        stm.setInt(3, p.getQtd_estoque());
        stm.execute();

        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        p.setPk_produto(rs.getInt("pk_produto"));

        stm.close();

        return p.getPk_produto();
    }

    public static Produto retrieve(int pk) throws SQLException, ClassNotFoundException {
        Connection conn = BancoDados.createConnection();

        PreparedStatement stm = conn.prepareStatement("select * from produtos where pk_produto = ?");

        stm.setInt(1, pk);
        stm.execute();

        ResultSet rs = stm.getResultSet();
        rs.next();

        return new Produto(rs.getInt("pk_produto"),
                rs.getString("nome"),
                rs.getInt("estoque_minimo"),
                rs.getInt("qtd_estoque"));

    }

    public static ArrayList<Produto> retrieveAll() throws SQLException, ClassNotFoundException {
        Connection conn = BancoDados.createConnection();

        ArrayList<Produto> aux = new ArrayList<>();

        ResultSet rs = conn.createStatement().executeQuery("select * from produtos");

        while (rs.next()) {

            aux.add(new Produto(rs.getInt("pk_produto"),
                    rs.getString("nome"),
                    rs.getInt("estoque_minimo"),
                    rs.getInt("qtd_estoque"))
            );

        }
        return aux;

    }

    public static String retrieveRelatorioEstoque() throws SQLException, ClassNotFoundException {
        String relatorio = "Nome do produto, QTD_Estoque, Estoque_mínimo, Estoque dinâmico\n";
        Connection conn = BancoDados.createConnection();

        ResultSet rsP = conn.createStatement().executeQuery("select * from produtos");
        PreparedStatement stmC = conn.prepareStatement("select * from compras_itens where fk_produto=?");
        PreparedStatement stmV = conn.prepareStatement("select * from vendas_itens where fk_produto=?");
        ResultSet rs;

        while (rsP.next()) {
            int compras = 0, vendas = 0;
            relatorio += rsP.getString("nome") + ", ";
            relatorio += rsP.getString("qtd_estoque") + ", ";
            relatorio += rsP.getString("estoque_minimo") + ", ";

            stmC.setInt(1, rsP.getInt("pk_produto"));
            stmC.execute();
            rs = stmC.getResultSet();
            while (rs.next()) {
                compras += rs.getInt("qtd");
            }

            stmV.setInt(1, rsP.getInt("pk_produto"));
            stmV.execute();
            rs = stmV.getResultSet();
            while (rs.next()) {
                vendas += rs.getInt("qtd");
            }

            relatorio += compras - vendas + "\n";
        }
        stmC.close();
        stmV.close();
        return relatorio;
    }

    public static String retrieveRelatorioLucroBruto(Date dataInicial, Date dataFinal) throws SQLException {
        String relatorio = "Período apurado: " + new java.sql.Date(dataInicial.getTime())
                + " a " + new java.sql.Date(dataFinal.getTime()) + "\n";
        relatorio += "Nome do produto, Total Compras R$,Total Vendas R$, Lucro Bruto R$\n";
        Connection conn = BancoDados.createConnection();

        ResultSet rsP = conn.createStatement().executeQuery("select * from produtos");

        PreparedStatement stmC
                = conn.prepareStatement("select ci.fk_produto, ci.qtd, ci.valor_unitario\n"
                        + "from compras com inner join compras_itens ci\n"
                        + "on com.pk_compra = ci.fk_compra and com.datas between ? and ?");
        stmC.setDate(1, new java.sql.Date(dataInicial.getTime()));
        stmC.setDate(2, new java.sql.Date(dataFinal.getTime()));

        PreparedStatement stmV
                = conn.prepareStatement("select vi.fk_produto, vi.qtd, vi.valor_unitario\n"
                        + "from vendas v inner join vendas_itens vi\n"
                        + "on v.pk_venda = vi.fk_venda and v.data between ? and ?");
        stmV.setDate(1, new java.sql.Date(dataInicial.getTime()));
        stmV.setDate(2, new java.sql.Date(dataFinal.getTime()));
        

        ResultSet rsCV;

        while (rsP.next()) {
            double compras = 0, vendas = 0;
            relatorio += rsP.getString("nome") + ": ";

            //calculando o valor das compras do produto atual
            stmC.execute();
            rsCV = stmC.getResultSet();
            while (rsCV.next()) {
                if (rsP.getInt("pk_produto") == rsCV.getInt("fk_produto")) {
                    compras += rsCV.getInt("qtd") * rsCV.getDouble("valor_unitario");
                }
            }
            relatorio += compras + ", ";

            //calculando o valor das vendas do produto atual
            stmV.execute();
            rsCV = stmV.getResultSet();
            while (rsCV.next()) {
                if (rsP.getInt("pk_produto") == rsCV.getInt("fk_produto")) {
                    vendas += rsCV.getInt("qtd") * rsCV.getDouble("valor_unitario");
                }
            }
            relatorio += vendas + ", ";

            relatorio += vendas - compras + "\n";
        }
        return relatorio;
    }

    public static void update(Produto p) throws SQLException {
        if (p.getPk_produto() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }

        Connection conn = BancoDados.createConnection();
        PreparedStatement stm = conn.prepareStatement("update produtos set nome=?,estoque_minimo=?,qtd_estoque=? where pk_produto = ?");

        stm.setString(1, p.getNome());
        stm.setInt(2, p.getEstoque_minimo());
        stm.setInt(3, p.getQtd_estoque());
        stm.setInt(4, p.getPk_produto());
        stm.execute();

        stm.close();

    }

    public static void delete(Produto p) throws SQLException {
        if (p.getPk_produto() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }
        Connection conn = BancoDados.createConnection();
        PreparedStatement stm = conn.prepareStatement("delete from produtos where pk_produto=?");
        stm.setInt(1, p.getPk_produto());
        stm.execute();
        stm.close();
    }
}
