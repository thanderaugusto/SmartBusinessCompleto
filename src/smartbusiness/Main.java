
package smartbusiness;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import smartbusiness.modelo.ComprasDAO;
import smartbusiness.modelo.ComprasItensDAO;
import smartbusiness.modelo.FornecedoresDAO;
import smartbusiness.modelo.FornecedoresEnderecosDAO;
import smartbusiness.negocio.Compras;
import smartbusiness.negocio.ComprasItens;
import smartbusiness.negocio.Fornecedores;
import smartbusiness.negocio.FornecedoresEnderecos;

public class Main {

    public static void main(String[] args) throws SQLException, ParseException {
//ArrayList<ComprasItens> test = ComprasItensDAO.retrieveByProduto();
// 
//for(ComprasItens c:test){
//System.out.println(c+"\n");
//}

//ArrayList<Fornecedores> test = FornecedoresDAO.retrieveByEstado("mg");
// 
//for(Fornecedores c:test){
//System.out.println(c+"\n");
//}


   
    


//    LocalDate dataInserir = LocalDate.of(2017, 10, 30);
//    java.sql.Date dataSQL = java.sql.Date.valueOf(dataInserir);
//    System.out.println(dataSQL);
    
    

//    ArrayList<Compras> test = ComprasDAO.retriaveByData(java.sql.Date.valueOf(LocalDate.of(2016, 1, 1)), 
//                                                        java.sql.Date.valueOf(LocalDate.of(2016, 12, 1)));
//    for(Compras c:test){
//           System.out.println(c+"\n");
//       }


// LocalDate dataInserir = LocalDate.of(2017, 11, 06);;
//java.sql.Date dataSQL = java.sql.Date.valueOf(dataInserir);
////    System.out.println(dataSQL);
//   
//    
//
//    
//    Compras c2 = new Compras(8888, new Fornecedores("blabla", "0000"), dataSQL, new ComprasItens(2, 8, 200));
//        ComprasDAO.create(c2);

        System.out.println(ComprasDAO.retrieveAll());
}}
