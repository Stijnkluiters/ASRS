import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class ProductListPanel extends JPanel {
    private JList productList;
    private ArrayList<Product> products;
    private JTable positionTable;
    private JPanel colorPanel;
    private JTextField nameValue;
    private Product selectedProduct;

    public ProductListPanel() {

    }

    public void setProductList(JList productList) {
        this.productList = productList;
        this.initProducts();
        this.initListeners();
    }
    public void handleUpdateName() {
        selectedProduct.name = this.nameValue.getText();
        System.out.println("aangepast");
    }

    public void initListeners(){
        this.productList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                selectedProduct = (Product)productList.getModel().getElementAt(productList.locationToIndex(e.getPoint()));

                // handeling the click from here
                // we need to add the location to the labels and the name to the textfield.
                nameValue.setText(selectedProduct.name);
                colorPanel.setBackground(selectedProduct.getColor());

                DefaultTableModel dm = new DefaultTableModel(0, 0);

                ArrayList<String> stringArrayList = new ArrayList<>();
                stringArrayList.add("X");
                stringArrayList.add("Y");

                dm.setColumnIdentifiers(stringArrayList.toArray());
                positionTable.setModel(dm);
                Vector<Object> data = new Vector<Object>();
                data.add(selectedProduct.getY() + 1);
                data.add(selectedProduct.getX() + 1);
                dm.addRow(data);

            }


        });
    }

    public void setColorPanelBackground(Product product) {
        colorPanel.setBackground(product.getColor());
    }

    private void initProducts() {
        try {
            DefaultListModel<Product> listModel = new DefaultListModel<>();
            for (Product product : products) {
                listModel.addElement(product);
            }
            this.productList.setModel(listModel);
            this.productList.setSelectionBackground(Color.CYAN);
            this.productList.setSelectionForeground(Color.BLACK);
            this.productList.setBackground(Color.WHITE);
            this.productList.setForeground(Color.ORANGE);

        }catch (NullPointerException exception) {
            System.out.println("Er zijn geen producten gevonden? ");
            System.out.println(exception.getMessage());
        }
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


    public void setNameValue(JTextField nameValue) {
        this.nameValue = nameValue;
    }

    public void setColorPanel(JPanel colorPanel) {
        this.colorPanel = colorPanel;
    }

    public void setPositionTable(JTable positionTable) {
        this.positionTable = positionTable;
    }

    public void clearGui() {
        colorPanel.setBackground(Color.WHITE);
        nameValue.setText("");
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }
}
