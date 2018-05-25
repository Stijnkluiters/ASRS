import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class TSPAlgorithmTester {

    private FarthestInsertion farthestInsertion;

    private BruteForce bruteForce;

    private NearestNeighbour nearestNeighbour;

    private RandomAlgorithm randomAlgorithm;

    private Route route;

    private PdfPTable table;
    private Document document;

    public static void main(String[] args) {
        TSPAlgorithmTester tester = new TSPAlgorithmTester();
        int amountProducts = 5000;

        tester.createPdf(amountProducts);

        for (int i = 1; i < amountProducts; i++) {
            tester.testAlgorithms(i);
        }
        tester.addTable();

    }

    private void addTable() {
        try {
           if(this.document.add(table)) {
                System.out.println("Adding table succesfully succedded");
           } else {
               System.out.println("Adding table FAILED!");
           }


            this.document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public TSPAlgorithmTester() {
        this.route = generateRoute();
    }

    public void testAlgorithms(int countProducts) {
        ArrayList<Product> products = this.getRandomProducts(countProducts + 1);
        // Bruteforce
        long bruteForceRunningTime = 0;
        if( countProducts < 11) {
            this.route = this.generateRoute();
            this.bruteForce = new BruteForce(this.route);
            this.bruteForce.execute(products);
            bruteForceRunningTime = this.route.getRunningTime();
        }
        // FarestInsertion

        long faresstInsertionRunningTime = 0;
        if(countProducts < 400) {
            this.route = this.generateRoute();
            this.farthestInsertion = new FarthestInsertion(this.route);
            this.farthestInsertion.execute(products);
            faresstInsertionRunningTime = this.route.getRunningTime();
        }
        // NearestNeighbourRunningTime
        this.route = this.generateRoute();
        this.nearestNeighbour = new NearestNeighbour(this.route);
        this.nearestNeighbour.execute(products);
        long nearestNeighbourRunningTime = this.route.getRunningTime();
        // RandomAlgorithmRunningTime
        this.route = this.generateRoute();
        this.randomAlgorithm = new RandomAlgorithm(this.route);
        this.randomAlgorithm.execute(products);
        long randomAlgorithmRunningTime = this.route.getRunningTime();

        System.out.println(countProducts + " " + bruteForceRunningTime + " " + faresstInsertionRunningTime + " " + nearestNeighbourRunningTime + " " + randomAlgorithmRunningTime);
        this.addrow(
                countProducts,
                bruteForceRunningTime,
                faresstInsertionRunningTime,
                nearestNeighbourRunningTime,
                randomAlgorithmRunningTime
        );
    }

    public Route generateRoute() {
        return new Route(new Drawpanel());
    }

    private void addrow(int countProducts, long bruteforce, long farestInsertion, long nearest, long random) {


        String countProductsS = Integer.toString(countProducts);
        String bruteForceS = Long.toString(bruteforce);
        String farestInsertionS = Long.toString(farestInsertion);
        String nearestS = Long.toString(nearest);
        String randomS = Long.toString(random);


        this.table.addCell(countProductsS);
        this.table.addCell(bruteForceS);
        this.table.addCell(farestInsertionS);
        this.table.addCell(nearestS);
        this.table.addCell(randomS);

    }


    public void createPdf(int countProducts) {
        try {

            int amountOfCells = (countProducts * 5) + 5;

            File f = null;
            boolean bool = false;
            // returns pathnames for files and directory
            String filePath = "C:/Users/stijn/Dropbox/Windesheim/TSPDashboard/result";
            f = new File(filePath);

            // create
            bool = f.mkdir();

            // print

            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath + "/result.pdf"));
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Resultaat van de algoritmes:", font);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);




            table = new PdfPTable(5);
            table.addCell("Amount of products:");
            table.addCell("Bruteforce:");
            table.addCell("Farest:");
            table.addCell("Nearest:");
            table.addCell("Random:");


        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (DocumentException ex) {
            System.out.println(ex.getCause());
        }

    }

    private ArrayList<Product> getRandomProducts(int productCount) {
        // n & amount is the amount of products on the shelfs
        Random random = new Random();
        ArrayList<Product> order = new ArrayList<>();

        for (int i = 1; i < productCount; i++) {
            int x = 0;
            int y = 0;
            int unique = 0;
            if (i != 1) {
                x = random.nextInt(productCount);
                y = random.nextInt(productCount);
            }
            Product p = new Product(x, y);
            p.name = ("Package " + i);
            p.setColor(null);

            // unique points in the algorithm, no points can be placed at the same position, ( NOT ON X AND Y )
            if (!order.isEmpty()) {
                // this funtionality is used for unique points on the screen,
                // no points can be placed at the same position
                for (Product o : order) {
                    // if the package position is NOT the same as the new order position we can add a new order
                    if (p.getX() != o.getX() || p.getY() != o.getY()) {
                        unique++;
                    }
                }
                if (unique == order.size()) {
                    order.add(p);
                }
            } else {
                order.add(p);
            }
        }

        return order;
    }

    public RandomAlgorithm getRandomAlgorithm() {
        return randomAlgorithm;
    }

    public void setRandomAlgorithm(RandomAlgorithm randomAlgorithm) {
        this.randomAlgorithm = randomAlgorithm;
    }

    public NearestNeighbour getNearestNeighbour() {
        return nearestNeighbour;
    }

    public void setNearestNeighbour(NearestNeighbour nearestNeighbour) {
        this.nearestNeighbour = nearestNeighbour;
    }

    public BruteForce getBruteForce() {
        return bruteForce;
    }

    public void setBruteForce(BruteForce bruteForce) {
        this.bruteForce = bruteForce;
    }

    public FarthestInsertion getFarthestInsertion() {
        return farthestInsertion;
    }

    public void setFarthestInsertion(FarthestInsertion farthestInsertion) {
        this.farthestInsertion = farthestInsertion;
    }
}
