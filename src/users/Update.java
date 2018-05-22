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
public class Update {
    
    Connection conn=null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Statement stm = null;
    
    public void UpdateStudent(String Id, String Name, String Email, String Username, String PhoneNumber, String Password, String Role)
    {
        DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
        conn = Connect.Connectdb();
        try{

            String sql="UPDATE profilemaster SET Name= "+Name+",Email="+Email+",Username="+Username+",PhoneNumber="+PhoneNumber+",Password="+Password+",Role="+Role+" WHERE Id="+Id+"";
            pst = conn.prepareStatement(sql);
            pst.execute();

        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
