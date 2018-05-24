import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public Database(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            st = con.createStatement();
        }catch(SQLException ex){
            System.out.println("SQL ERROR: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void addOrder(String orderid, String name, String lastname, String dateFormat){
        try{
            String query="INSERT INTO `order` (`Order_date`, `PersonID`) VALUES ('"+ dateFormat + "', '"+getPersonId(name,lastname) +"')";
            System.out.println(query);
            st.executeUpdate(query);
        }catch(SQLException ex){
            System.out.println(ex.getErrorCode() + " " + ex.getMessage());

        }
    }

    public void addOrderList(String item, String order){
        int itemint=Integer.parseInt(item);
        try{
            String query="INSERT INTO `orderlist` (`OrderID`, `ProductID`, `Quantity`) VALUES ('"+order+"', '"+itemint+ "', '1');";
            System.out.println(query);
            st.executeUpdate(query);
        }catch(SQLException ex){
            System.out.println(ex.getErrorCode() + " " + ex.getMessage());

        }
    }


    public void addPersonToDatabase(String name, String lastname, String adress, String zipcode, String city){
        try{
            String query = "INSERT INTO `person` (`PersonID`, `Firstname`, `Lastname`, `Adress`, `Zipcode`, `City`) VALUES (NULL, '"+name+"', '"+lastname+"', '"+adress+"', '"+zipcode+"', '"+city+"');  ";
            System.out.println(query);
            st.executeUpdate(query);
        }catch(SQLException ex){
            System.out.println(ex.getErrorCode() + " " + ex.getMessage());

        }
    }

    public int getPersonId(String name, String lastname){
        try{
            String query ="select PersonID from `person` where Firstname = "+"'"+name+"'" +" AND Lastname = "+"'"+lastname+ "'";
            System.out.println(query);
            rs = st.executeQuery(query);
            String PersonIDs = new String();
            while(rs.next()) {
                PersonIDs = rs.getString("PersonID");
            }

            int PersonID = Integer.parseInt(PersonIDs);
            return PersonID;

        }catch(SQLException ex){
            System.out.println(ex.getErrorCode() + " " + ex.getMessage());

        }
        return 0;
    }

    public boolean checkPersonInDatabase(String name, String lastname){
        try{
            String query ="select PersonID from `person` where Firstname = "+"'"+name+"'" +" AND Lastname = "+"'"+lastname+ "'";
            System.out.println(query);
            rs = st.executeQuery(query);
            while(rs.next()){
                String personID = rs.getString("PersonID");
                System.out.println("PersonID " + personID);
                if(personID!=null){
                    return true;
                }
            }

        } catch(SQLException ex){
            System.out.println(ex.getErrorCode() + " " + ex.getMessage());

        }
        System.out.println("Not in database");
        return false;
    }


    public boolean checkOrderInDatabase(String order){
        int xmlOrderID = Integer.parseInt(order);
        try{
            String query ="select PersonID from `order` where OrderID = "+ xmlOrderID;
            System.out.println(query);
            rs = st.executeQuery(query);
            while(rs.next()){
                String personID = rs.getString("PersonID");
                System.out.println("PersoonID "+personID);
                if(personID!=null){
                    System.out.println(personID);
                    return true;
                }
            }

        } catch(SQLException ex){
            System.out.println(ex.getErrorCode() + " " + ex.getMessage());

        }
        System.out.println("Order not in database");
        return false;
    }


    public ArrayList<Product> getProductData(ArrayList<String> tempList){

        StringBuilder builder = new StringBuilder();
        System.out.println(tempList);
        //Convert arrayList to a string for the where in clause
        builder.append("(");
        for(String id : tempList) {
            builder.append(id);
            builder.append(" ,");
        }

        builder.deleteCharAt(builder.length()-1);
        builder.append(")");

        //System.out.println(builder);

        ArrayList<Product> products = new ArrayList<>();
        try{
            //Where in clause to only have 1 query instead if multiple ones
            String query ="select * from product where ProductID IN "+builder.toString();
            rs = st.executeQuery(query);
            System.out.println(query);
            System.out.println("Product was found in the database.");
            while(rs.next()){
                String name = rs.getString("Name");
                String heightS = rs.getString("Height");
                String xS = rs.getString("LocationX");
                String yS = rs.getString("LocationY");
                String colorS = rs.getString("RGB");

                System.out.println("Name: " + name + " Height: " + heightS + " X: " + xS + " Y:" + yS + " Color: " );

                int height = Integer.parseInt(heightS);
                int x = Integer.parseInt(xS);
                int y = Integer.parseInt(yS);

                //Create a color from the String
               String[] splittedColor = colorS.split(",");
//               System.out.println(splittedColor);

                Color color = new Color(Integer.parseInt(splittedColor[0]),Integer.parseInt(splittedColor[1]),Integer.parseInt(splittedColor[2]));
                Product product = new Product(name,height,x,y,color);
                products.add(product);
            }

        }catch(SQLException ex){
            System.out.println(ex.getErrorCode());

        }

        return products;

    }

    public Customer getCustomerThroughOrderId(String orderNumber) {
        int xmlOrderID = Integer.parseInt(orderNumber);
        try{
            String query = "SELECT Firstname, Lastname, Adress, Zipcode, City From `person` WHERE `PersonID` IN (SELECT PersonID FROM `order` WHERE `Orderid` = '" + xmlOrderID + "')";
            System.out.println(query);
            rs = st.executeQuery(query);

            Customer customer = new Customer();

            while(rs.next()){
                String firstname = rs.getString("Firstname");
                String lastname = rs.getString("Lastname");
                String adres = rs.getString("Adress");
                String zipcode = rs.getString("Zipcode");
                String city = rs.getString("City");
                customer.setFirstName(firstname);
                customer.setLastName(lastname);
                customer.setAdres(adres);
                customer.setZipcode(zipcode);
                customer.setCity(city);
            }

            return customer;
        } catch(SQLException ex){
            System.out.println(ex.getErrorCode() + " " + ex.getMessage());

        }
        System.out.println("Order not in database");
        return null;
    }

}
