/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author RiFF
 */
public class Create {
    
    Connection conn=null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Statement stm = null;
    
    public void CreateUser(String Name, String Email, String Username, String PhoneNumber, String Password, String Role)
    {
        DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
        conn = Connect.Connectdb();
        try{
            String user = "INSERT INTO `profilemaster`(`Name`, `Email`, `Username`, `PhoneNumber`, `Password`, `Role`) VALUES (?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(user);
            pst.setString(1, Name);
            pst.setString(2, Email);
            pst.setString(3, Username);
            pst.setString(4, PhoneNumber);
            pst.setString(5, Password);
            pst.setString(6, Role);
            pst.execute(); 
            JOptionPane.showMessageDialog(null, "User has been successfully added");
                     
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }    
    }
}
