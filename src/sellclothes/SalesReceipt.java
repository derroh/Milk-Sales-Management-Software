/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sellclothes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static java.awt.Component.TOP_ALIGNMENT;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author HiGHROLLER
 */
public class SalesReceipt 
{
    Connection conn =  null;
    PreparedStatement pst = null;
    Statement stm = null;
    ResultSet rs = null;
    
    public void generateReceipt(String path, String ReceiptNumber)
    {
        
         try{
          Document document = new Document();  
          FileOutputStream file =  new FileOutputStream(path);
          PdfWriter.getInstance(document, file);
          Font norm = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
          Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);                                              
          document.open();
          
          Paragraph titl = new Paragraph("CLOTHE SALE MANAGEMENT SYSTEM", FontFactory.getFont(FontFactory.HELVETICA, 20, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
          titl.setAlignment(Element.ALIGN_CENTER);
          document.add(titl);
         
          Paragraph tit2 = new Paragraph("CUSTOMER RECEIPT", FontFactory.getFont(FontFactory.HELVETICA, 14, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
          tit2.setAlignment(Element.ALIGN_CENTER);
          document.add(tit2);
          
          Paragraph tit4 = new Paragraph("RECEIPT NO: " , FontFactory.getFont(FontFactory.HELVETICA, 14, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
          tit2.setAlignment(Element.ALIGN_CENTER);
          document.add(tit4);
          
          long time = System.currentTimeMillis();
          java.sql.Timestamp timestamp = new java.sql.Timestamp(time);

          
          document.add(new Paragraph("\n"));
          Paragraph tit3 = new Paragraph("Printed on "+ timestamp, FontFactory.getFont(FontFactory.HELVETICA, 14, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
          tit3.setAlignment(Element.ALIGN_CENTER);
          document.add(tit3);
          document.add(new Paragraph("\n"));
          
          PdfPTable table = new PdfPTable(5);
          table.setWidthPercentage(100);            
                                                       
          PdfPCell cell;
          cell = new PdfPCell(new Phrase("Product Id", boldFont));
          table.addCell(cell);
          cell = new PdfPCell(new Phrase("Name", boldFont));
          table.addCell(cell);
          cell = new PdfPCell(new Phrase("Quantity", boldFont));
          table.addCell(cell);
          cell = new PdfPCell(new Phrase("Price", boldFont));
          table.addCell(cell);
          cell = new PdfPCell(new Phrase("Total Amount", boldFont));
          table.addCell(cell);


          float[] columnWidths = new float[] {20f, 31f, 10f, 10f, 15f};
          table.setPaddingTop(TOP_ALIGNMENT);
          table.setWidths(columnWidths);
          

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
                    cell = new PdfPCell(new Phrase(rs.getString("ProductId"), norm));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(GetProductName(rs.getString("ProductId")), norm));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(quan, norm));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(price, norm));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(totalamt, norm));
                    table.addCell(cell);
                }
            /////////////////////////////////////////
            PdfPCell cell1;
            cell1 = new PdfPCell(new Phrase("Total", boldFont));
            table.addCell(cell1);
            cell1 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell1);
            cell1 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell1);
            cell1 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell1);
            cell1 = new PdfPCell(new Phrase(GetCartAmount(), boldFont));
            table.addCell(cell1);
            /////////////////////////////////////////
            double total = 0;
            double cart = Integer.parseInt(GetCartAmount());
            double tax = TaxIsht(cart);
            double discountAmt = Double.parseDouble(GetDiscountAmount(ReceiptNumber));
            
            
            PdfPCell cell3;
            cell3 = new PdfPCell(new Phrase("Tax", boldFont));
            table.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(String.valueOf(tax), boldFont));
            table.addCell(cell3);
            ///////////////////////////////////////// String TaxIsht(int taxm)
            PdfPCell cell2;
            cell2 = new PdfPCell(new Phrase("Discount/Bonus", boldFont));
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(GetDiscountAmount(ReceiptNumber), boldFont));
            table.addCell(cell2);
            ///////////////////////////////////////// String TaxIsht(int taxm)
            
            PdfPCell cell4;
            cell4 = new PdfPCell(new Phrase("Amount to pay", boldFont));
            table.addCell(cell4);
            cell4 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell4);
            cell4 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell4);
            cell4 = new PdfPCell(new Phrase(" ", boldFont));
            table.addCell(cell4);
                    
            total = cart + tax -  discountAmt;       
                    
            cell4 = new PdfPCell(new Phrase(String.valueOf(total), boldFont));
            table.addCell(cell4);
          }catch(Exception e)
          {
           e.printStackTrace();
          }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { pst.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
          
          document.add(table);
          document.close();

        }catch(Exception e)
        {
            JOptionPane.showMessageDialog( null,e.toString(),"Error", JOptionPane.ERROR_MESSAGE);

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
}
