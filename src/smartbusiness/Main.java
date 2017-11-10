
package smartbusiness;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import smartbusiness.modelo.CompraDAO;
import smartbusiness.modelo.CompraItemDAO;
import smartbusiness.modelo.FornecedorDAO;
import smartbusiness.modelo.FornecedorEnderecoDAO;
import smartbusiness.negocio.Compra;
import smartbusiness.negocio.CompraItem;
import smartbusiness.negocio.Fornecedor;
import smartbusiness.negocio.FornecedorEndereco;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//ArrayList<Compra> test = CompraDAO.retrieveAll();
// 
//for(Compra c:test){
//System.out.println(c);
//}
//ArrayList<Fornecedores> test = FornecedorDAO.retrieveAll();
// 
//for(Fornecedor c:test){
//System.out.println(c);
//}
//
//        System.out.println(CompraDAO.retrieveByFornecedores(2));

   
    


//    LocalDate dataInserir = LocalDate.of(2017, 10, 30);
//    java.sql.Date dataSQL = java.sql.Date.valueOf(dataInserir);
//    System.out.println(dataSQL);
    
    

//    ArrayList<Compras> test = CompraDAO.retriaveByData(java.sql.Date.valueOf(LocalDate.of(2016, 1, 1)), 
//                                                        java.sql.Date.valueOf(LocalDate.of(2016, 12, 1)));
//    for(Compra c:test){
//           System.out.println(c+"\n");
//       }


 LocalDate dataInserir = LocalDate.of(2017, 11, 06);
java.sql.Date dataSQL = java.sql.Date.valueOf(dataInserir);
//    System.out.println(dataSQL);
   
//    
//
//


//Fornecedores f = new Fornecedor("TI ", "12555");
//FornecedoresDAO.create(f);
//
//    Compra c2 = new Compra(66, f, dataSQL, new CompraItem(1,1, 8, 200));
//        CompraDAO.create(c2);

//            FornecedorDAO.update(new Fornecedor());
//            Fornecedor d = FornecedorDAO.retrieve(4);
//            d.setNome("Ti Info");
//            d.setCpf("2522");
//            FornecedorDAO.update(d);
//            System.out.println(FornecedorDAO.retrieve(4));
            
            
//           Fornecedor d = new Fornecedor();
//           d.setPk_fornecedor(4);
//           FornecedorDAO.delete(d);
  
//            CompraDAO.update(new Compra());
//            Compra d = CompraDAO.retrieve(11);
//            CompraDAO.update(d);

//        System.out.println(CompraDAO.retrieveAll());


        System.out.println(CompraItemDAO.retrieveByProduto(2));
    }}