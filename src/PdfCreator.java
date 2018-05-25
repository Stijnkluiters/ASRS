
import com.itextpdf.text.*;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

public class PdfCreator {
    private ArrayList<Storage> boxes;

    public PdfCreator() {
    }

    public void CreatePdf() {
        try {
            File f = null;
            boolean bool = false;
            // returns pathnames for files and directory
            String filePath = "C:/Users/sande/IdeaProjects/ASRS/"+XmlImporter.getOrderNumber();
            f = new File(filePath);

            // create
            bool = f.mkdir();

            // print
            System.out.print("Directory created? "+bool);


            if (boxes.size() != 0) {
                int i = 1;
                for (Storage box : boxes) {

                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(filePath + "/Box" + i + ".pdf"));
                    document.open();
                    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                    System.out.println("De order datum is" +XmlImporter.getOrderDate());

                    Paragraph paragraph = new Paragraph("Pakbon van ordernummer: "+XmlImporter.getOrderNumber()+ "\n", font);
                    document.add(paragraph);

                    Paragraph paragraph3 = new Paragraph("Orderdatum is: "+XmlImporter.getOrderDate()+ "\n", font);
                    document.add(paragraph3);

                    document.add(Chunk.NEWLINE);

                    Paragraph paragraph2 = new Paragraph("Box " + i + " van de " + boxes.size()+ "\n",font);
                    document.add(paragraph2);

                    document.add(Chunk.NEWLINE);

                    document.add(Chunk.NEWLINE);

                    System.out.println("Er worden " + box.getProducts().size() + " aan de volgende doos in de PDFs");
                    PdfPTable table = new PdfPTable(2);
                    table.addCell("Product naam:");
                    table.addCell("Product hoogte:");
                    int totalHeight = 0;
                    for(Product product: box.getProducts()){
                        table.addCell(product.getName());
                        String height =""+ product.getHeight();
                        table.addCell(height);
                        totalHeight+=product.getHeight();
                    }
                    String amountOfProducts="" + box.getProducts().size();
                    table.addCell("Hoeveelheid producten is: "+amountOfProducts);
                    String totalHeightString= "" + totalHeight;
                    table.addCell("Totale hoogte is: " +totalHeightString);


                    document.add(table);
                    document.close();


                    i++;
                }
            }//end try

        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (DocumentException ex) {
            System.out.println(ex.getCause());
        }

    }

    public void setBoxes(ArrayList<Storage> boxes) {
        this.boxes = boxes;
    }

}
