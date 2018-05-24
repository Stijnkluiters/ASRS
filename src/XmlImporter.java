import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.w3c.dom.Element;

public class XmlImporter extends JFrame implements ActionListener {

    private JFileChooser fc;
    private JTextField tf;
    private JButton b;
    private JButton b1;
    private Socket s;
    private DataOutputStream dout;
    private DataInputStream din;
    int i;
    private FileInputStream in;
    private File fileToBeSent;
    private JLabel fileNameLabel;

    private ArrayList<Product> products = new ArrayList<>();
    private boolean uploaded = false;
    //    private ProductListPanel productListPanel;
    private JList productList;
    private Customer customer = new Customer();
    private String orderDate;
    private static String orderNumber;

    public XmlImporter() {
        super("XML importeren");
    }

    public void handleMouseClick(MouseEvent e) {

        tf = new JTextField();
        tf.setBounds(20, 50, 190, 30);
        add(tf);

        b = new JButton("Browse");
        b.setBounds(250, 50, 80, 30);
        add(b);
        b.addActionListener(this);
        b1 = new JButton("Upload");
        b1.setBounds(250, 100, 80, 30);
        add(b1);
        b1.addActionListener(this);
        fc = new JFileChooser();
        setLayout(null);
        setSize(400, 300);
        setVisible(true);
        try {
            s = new Socket("localhost", 10);
            dout = new DataOutputStream(s.getOutputStream());
            din = new DataInputStream(s.getInputStream());
        } catch (Exception ex) {
        }
    }

    public void setLabels(String name) {
        tf.setText(name);
        fileNameLabel.setText(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == b) {
                int x = fc.showOpenDialog(null);
                if (x == JFileChooser.APPROVE_OPTION) {
                    fileToBeSent = fc.getSelectedFile();
                    setLabels(fileToBeSent.getName());
                    b1.setEnabled(true);
                } else {
                    fileToBeSent = null;
                    tf.setText(null);
                    b1.setEnabled(false);
                }
            }
            if (e.getSource() == b1) {
                this.parseToXml();
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
//                productListPanel.setProducts(products);
//                productListPanel.setProductList(productList);
            }
        } catch (Exception ex) {
        }
    }

    private void parseToXml() {
        try {
            Database database = new Database();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileToBeSent);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("bestelling");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element bestellingElement = (Element) nNode;
                    orderNumber = bestellingElement.getElementsByTagName("ordernummer").item(0).getTextContent();
                    System.out.println("ordernummer: " + orderNumber);
                    if (database.checkOrderInDatabase(bestellingElement.getElementsByTagName("ordernummer").item(0).getTextContent())) {
                        //if order is in the database just get the products
                        // we can asume here that the customer exists.. but we still need to create the customer object.
                        customer = database.getCustomerThroughOrderId(orderNumber);


                        NodeList artikelen = bestellingElement.getElementsByTagName("artikelnr");

                        ArrayList<String> TemporaryList = new ArrayList<>();
                        for (int i = 0; i < artikelen.getLength(); i++) {
                            TemporaryList.add(artikelen.item(i).getTextContent());
                        }
                        addProductToList(TemporaryList);
                    } else {
                        //If order is not in the database check person
                        /*
                         * nog een laag dieper naar het klanten object in XML
                         */
                        NodeList klant = doc.getElementsByTagName("klant");
                        Element klantElement = (Element) nNode;
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            String name = klantElement.getElementsByTagName("voornaam").item(0).getTextContent();
                            String lastname = klantElement.getElementsByTagName("achternaam").item(0).getTextContent();
                            String adress = klantElement.getElementsByTagName("adres").item(0).getTextContent();
                            String zipcode = klantElement.getElementsByTagName("postcode").item(0).getTextContent();
                            String city = klantElement.getElementsByTagName("plaats").item(0).getTextContent();
                            String datum = bestellingElement.getElementsByTagName("datum").item(0).getTextContent();

                            this.customer.setFirstName(name);
                            this.customer.setLastName(lastname);
                            this.customer.setAdres(adress);
                            this.customer.setZipcode(zipcode);
                            this.customer.setCity(city);
                            this.orderDate = datum;


                            System.out.println("voornaam: " + name);
                            System.out.println("achternaam: " + lastname);
                            System.out.println("adres: " + adress);
                            System.out.println("postcode: " + zipcode);
                            System.out.println("plaats: " + city);

                            if (database.checkPersonInDatabase(klantElement.getElementsByTagName("voornaam").item(0).getTextContent(), klantElement.getElementsByTagName("achternaam").item(0).getTextContent())) {
//                          // if Person is in de database add order
                                database.addOrder(bestellingElement.getElementsByTagName("ordernummer").item(0).getTextContent(), name, lastname, datum);
                                //get products from the order
                                NodeList artikelen = bestellingElement.getElementsByTagName("artikelnr");
                                ArrayList<String> TemporaryList = new ArrayList<>();
                                for (int i = 0; i < artikelen.getLength(); i++) {

                                    database.addOrderList(artikelen.item(i).getTextContent(), bestellingElement.getElementsByTagName("ordernummer").item(0).getTextContent());
                                    TemporaryList.add(artikelen.item(i).getTextContent());
                                }
                                addProductToList(TemporaryList);
                            } else {
                                //If the person isnt in the database add person to the database
                                database.addPersonToDatabase(name, lastname, adress, zipcode, city);
                                //Once the person is added add the order
                                database.addOrder(bestellingElement.getElementsByTagName("ordernummer").item(0).getTextContent(), name, lastname, datum);
                                //once the order is added retrieve the products
                                NodeList artikelen = bestellingElement.getElementsByTagName("artikelnr");
                                ArrayList<String> TemporaryList = new ArrayList<>();
                                for (int i = 0; i < artikelen.getLength(); i++) {

                                    database.addOrderList(artikelen.item(i).getTextContent(), bestellingElement.getElementsByTagName("ordernummer").item(0).getTextContent());
                                    TemporaryList.add(artikelen.item(i).getTextContent());
                                }
                                addProductToList(TemporaryList);

                            }
                        }

                    }


                    System.out.println("Datum : " + bestellingElement.getElementsByTagName("datum").item(0).getTextContent());

//                    NodeList artikelen = bestellingElement.getElementsByTagName("artikelnr");
//                    ArrayList<String> TemporaryList = new ArrayList<>();
//                    for (int i = 0; i < artikelen.getLength(); i++) {
//                       TemporaryList.add(artikelen.item(i).getTextContent());
//                    }
//                    addProductToList(TemporaryList);

                }
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace() + e.getMessage());
        }
        this.uploaded = true;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProductToList(ArrayList<String> TemporaryList) {
        Database database = new Database();

        products = database.getProductData(TemporaryList);

    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setfileNameLabel(JLabel fileNameLabel) {
        this.fileNameLabel = fileNameLabel;
    }

    public boolean isUploaded() {
        return uploaded;
    }


    public String getOrderDate() {
        return orderDate;
    }

    public static String getOrderNumber() {
        return orderNumber;
    }

}
