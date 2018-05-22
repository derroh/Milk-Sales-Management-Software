/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sellclothes;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Derrick Abucheri
 */
public class PrintReceipt extends javax.swing.JFrame {
    Connection conn =  null;
    PreparedStatement pst = null;
    Statement stm = null;
    ResultSet rs = null;
    String ItemSoldBy =  null;
    /**
     * Creates new form PrintReceipt
     */
    public PrintReceipt() {
        initComponents();
    }
    public PrintReceipt(String receipt, String Soldby) {
        initComponents();
        ItemSoldBy = Soldby;
        iconMethod();
        PrintReceipt(receipt);
    }
    public void iconMethod()
    {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
    }
    
     private void PrintReceipt(String ReceiptNumber)
    {
        txt_receipt.setText("          POINT OF SALE SYSTEM(CLOTHES)");
        txt_receipt.append("\n                Sales Receipt");
        txt_receipt.append("\n            Receipt Number " +ReceiptNumber);
        txt_receipt.append("\n------------------------------------------");
        txt_receipt.append("\nITEM                   QTY  PRICE    TOTAL");
        txt_receipt.append("\n------------------------------------------");
        try{
              DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
              conn = Connect.Connectdb();
                String getCat_one = "SELECT * FROM `customer_basket` WHERE 1";
                stm = conn.createStatement();
                rs = stm.executeQuery(getCat_one);

                while(rs.next())
                {
                    String quan = rs.getString("Quantity");
                    String price = rs.getString("Price");
                    String totalamt = rs.getString("TotalAmount");
                    String prodId = rs.getString("ProductId");
                    
                    txt_receipt.append("\n" + GetProductName(prodId)+ "      "+quan+"  * "+totalamt+"           " +price+"");
           
                }
            txt_receipt.append("\n------------------------------------------");
             txt_receipt.append("\nSubtotal                             " +GetCartAmount());
            double total = 0;
            double cart = Integer.parseInt(GetCartAmount());
            double tax = TaxIsht(cart);
            double discountAmt = Double.parseDouble(GetDiscountAmount(ReceiptNumber));
            total = cart + tax -  discountAmt; 
            txt_receipt.append("\nTax                                " +String.valueOf(tax));
            txt_receipt.append("\nBonus                               " +GetDiscountAmount(ReceiptNumber));
            txt_receipt.append("\nPayment                           " +String.valueOf(total));    
            txt_receipt.append("\n------------------------------------------");
           //ItemSoldBy
            txt_receipt.append("\n-------- Served by "+ItemSoldBy+ " --------- ");    
           
        }catch(Exception e)
          {
           e.printStackTrace();
          }
    }
    public String GetProductName(String ProductId)
    {
        String Name ="";
        DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
        conn = Connect.Connectdb();
        try
        {
            String sql = "SELECT  `Name` FROM `products` WHERE `ProductId` = '"+ProductId+"'";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            
            while (rs.next())
            {
                Name = rs.getString("Name");
            }
                     
        }catch(Exception e)
        {
            e.printStackTrace();
        } 
        return Name;
    }
    public String GetCartAmount()
    {
        String Name ="";
        DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
        conn = Connect.Connectdb();
        try
        {
            String sql = "SELECT SUM(TotalAmount) as Total FROM `customer_basket` ";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            
            while (rs.next())
            {
                Name = rs.getString("Total");
            }
                     
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } 
        return Name;
    }
     public String GetDiscountAmount(String ReceiptNumber)
    {
        String Name ="";
        DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
        conn = Connect.Connectdb();
        try
        {
            String sql = "SELECT * FROM `discounts` WHERE `Receipt` ='"+ReceiptNumber+"'";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            
            while (rs.next())
            {
                Name = rs.getString("Amount");
            }
                     
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } 
        return Name;
    }
    private double TaxIsht(double taxm)
    {
        int tax = getTaxAmount();

        double vat= tax * 0.01 * taxm;
        
        return vat;
    }
     public int getTaxAmount()
    {
        int amt = 0;
        DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
        conn = Connect.Connectdb();
        try{

            String sql ="SELECT * FROM `tax_settings`";
            pst = conn.prepareStatement(sql);
            rs= pst.executeQuery();

            if(rs.next())
            {
                amt = rs.getInt("Amount");  
            }

        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return amt;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_receipt = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/attatchments/Print_24px.png"))); // NOI18N
        jButton1.setText("PRINT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_receipt.setEditable(false);
        txt_receipt.setColumns(20);
        txt_receipt.setRows(5);
        jScrollPane1.setViewportView(txt_receipt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(406, 573));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            boolean complete = txt_receipt.print();

            if(complete == true)
            {
                JOptionPane.showMessageDialog(null, "Print Success");

            }else if(complete == false)
            {
                JOptionPane.showMessageDialog(null, "Print Failed");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrintReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrintReceipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txt_receipt;
    // End of variables declaration//GEN-END:variables
}
