/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author RiFF
 */
public class DbConnection {
    Connection conn=null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Statement stm = null;
    
    public Connection Connectdb()
    {

        String dbname, user, password, url,Driver;

        dbname="pos";//place name of dbase in the quotation marks eg trial
        user ="root"; //default name in mysql is root
        password="";//place here pword after you create one
        url="jdbc:mysql://localhost:3306/";//
        Driver="com.mysql.jdbc.Driver";
       try{

            Class.forName(Driver);//pwrd
            conn=DriverManager.getConnection(url+dbname,user,password);
          return conn;
       }catch(Exception ex){

           JOptionPane.showMessageDialog(null, "An error occured while establishing database connection\nKindly make sure your database is up and running.", "Database Connection error", JOptionPane.ERROR_MESSAGE);
           ex.printStackTrace();
           //.showMessageDialog(null, ex);
         return null;
       }        
    }
}
