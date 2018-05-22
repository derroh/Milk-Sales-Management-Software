/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sellclothes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
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
 * @author Derrick Abucheri
 */
public class ProfitLossAnalysis {
    
    Connection conn =  null;
    PreparedStatement pst = null;
    Statement stm = null;
    ResultSet rs = null;
    
    public void generateReceipt(String path, String timeStamp)
    {
        try{
          Document document = new Document();  
          FileOutputStream file =  new FileOutputStream(path);
          PdfWriter.getInstance(document, file);
          Font norm = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
          Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);                                              
          document.open();
          
          Paragraph titl = new Paragraph("POINT OF SALE SYSTEM(CLOTHES)", FontFactory.getFont(FontFactory.HELVETICA, 20, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
          titl.setAlignment(Element.ALIGN_CENTER);
          document.add(titl);
         
          Paragraph tit2 = new Paragraph("PROFIT/LOSS ANALYSIS FOR "+timeStamp+"", FontFactory.getFont(FontFactory.HELVETICA, 14, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
          tit2.setAlignment(Element.ALIGN_CENTER);
          document.add(tit2);
          
          long time = System.currentTimeMillis();
          java.sql.Timestamp timestamp = new java.sql.Timestamp(time);

          
          document.add(new Paragraph("\n"));
          Paragraph tit3 = new Paragraph("Printed on "+ timestamp, FontFactory.getFont(FontFactory.HELVETICA, 14, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
          tit3.setAlignment(Element.ALIGN_CENTER);
          document.add(tit3);
          document.add(new Paragraph("\n"));
          
          PdfPTable table = new PdfPTable(6);
          table.setWidthPercentage(100);            
                                                       
          PdfPCell cell;
          cell = new PdfPCell(new Phrase("Name", boldFont));
          table.addCell(cell);
          cell = new PdfPCell(new Phrase("Category", boldFont));
          table.addCell(cell);
          cell = new PdfPCell(new Phrase("Buying Price", boldFont));
          table.addCell(cell);
          cell = new PdfPCell(new Phrase("Selling Price", boldFont));
          table.addCell(cell);
          cell = new PdfPCell(new Phrase("Quantity Sold", boldFont));
          table.addCell(cell);
          cell = new PdfPCell(new Phrase("Profit Made", boldFont));
          table.addCell(cell);


          float[] columnWidths = new float[] {35f, 20f, 10f, 10f, 15f, 20f};
          table.setPaddingTop(TOP_ALIGNMENT);
          table.setWidths(columnWidths);
          

          try{
              DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
              conn = Connect.Connectdb();
                String getCat_one = 
                "select `products`.`Name`, `products`.`Category`,\n" +
                "       `products`.`BuyingPrice`,\n" +
                "       `products`.`Price` as SellingPrice,\n" +
                "       `item_sales`.`QuantitySold`, \n" +
                "        (`products`.`Price` * `item_sales`.`QuantitySold`) -\n" +
                "       (`products`.`BuyingPrice` * `item_sales`.`QuantitySold`) as Profit\n" +
                "  from (`products` `products`\n" +
                "  inner join `item_sales` `item_sales`\n" +
                "       on (`item_sales`.`ProductId` = `products`.`ProductId`))\n" +
                " where\n" +
                "       (`item_sales`.`Date` = '"+timeStamp+"')\n" +
                "	GROUP BY `products`.`Name`";
               
                stm = conn.createStatement();
                rs = stm.executeQuery(getCat_one);

                while(rs.next())
                {
                    String Name = rs.getString("Name");
                    String Category = rs.getString("Category");
                    String BuyingPrice = rs.getString("BuyingPrice");
                    String SellingPrice = rs.getString("SellingPrice");
                    String QuantitySold = rs.getString("QuantitySold");
                    String Profit = rs.getString("Profit");
                    
                    cell = new PdfPCell(new Phrase(Name, norm));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(Category, norm));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(BuyingPrice, norm));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(SellingPrice, norm));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(QuantitySold, norm));
                    table.addCell(cell);                    
                    cell = new PdfPCell(new Phrase(Profit, norm));
                    table.addCell(cell);
                }
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
    
 }

