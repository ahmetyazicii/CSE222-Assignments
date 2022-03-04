import java.io.IOException;
import java.util.*;

public class Trader extends AbstractUser{

    static final Scanner input = new Scanner(System.in);

    private ArrayList<Product> products;
    private final PriorityQueue<Order> orders;

    Trader(int id,String password,String name) {
        super(id, password, name);
        orders = new PriorityQueue<>();
        products = new ArrayList<>();

    }

    /**
     * Add new product.
     * @param db Database where the product is sent
     */
    public void addProduct(DataBase db){
        try {
            String line;
            String pName, pCategory, pDescription;
            int pPrice, pDiscPrice;
            System.out.println("Please write the name of product:");
            pName = input.nextLine();
            System.out.println("Please write the hierarchical category of product(use >> between categories)");
            pCategory = input.nextLine();
            System.out.println("Please write the price of the product");
            line = input.nextLine();
            pPrice = Integer.parseInt(line);
            System.out.println("Please write the discounted price of the product");
            line = input.nextLine();
            pDiscPrice = Integer.parseInt(line);
            System.out.println("Please write the description of the product");
            pDescription = input.nextLine();

            products.add(new Product(generateRandomProductID(), pName, pCategory, pPrice, pDiscPrice, pDescription,getUserName()));
            db.updateProducts(this,products);

        }
        catch (NumberFormatException nfe){
            System.out.println("You have typed char instead of number");
        }
    }

    /**
     * Removes product.Firstly prints all the products of the user.Then perform removes operation.Lastly update the products file
     * @param db Database where products received
     */
    public void removeProduct(DataBase db){
        loadProductsT(db);
        displayProducts(db);
        System.out.println("Please type the ID of product you want to remove.");

        if(removeProduct(input.nextLine())) {
            System.out.println("Product successfully removed.");
            db.updateProducts(this,products);
        }
        else
            System.out.println("Product could not removed.Please try again properly.");
    }

    /**
     * Helper method for remove operation
     * @param pName Name of product
     * @return True if remove operation successfully performed, false otherwise
     */
    private boolean removeProduct(String pName){
        for(int i=0;i<products.size();++i){
            if(products.get(i).getProductName().equals(pName)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Edits product.Firstly prints all the products of the user.Then perform edit operation.Lastly update the products file
     * @param db Database where products received
     */
    public void editProduct(DataBase db){
        loadProductsT(db);
        displayProducts(db);
        System.out.println("Please type the ID of product you want to edit.");

        if(editProduct(input.nextLine())) {
            System.out.println("Product successfully edited.");
            db.updateProducts(this,products);
        }
        else
            System.out.println("Product could not edited.Please try again properly.");
    }

    /**
     * Helper method for edit operation
     * @param pName Name of product
     * @return True if edit operation successfully performed, false otherwise
     */
    private boolean editProduct(String pName){
        for(int i=0;i<products.size();++i){
            if(products.get(i).getProductName().equals(pName)){
                performEditOperation(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method for edit operation
     * @param index The index of product that will be edited
     */
    private void performEditOperation(int index){
        try {
            System.out.println("Choose which property want to edit");
            System.out.println("1-Name");
            System.out.println("2-Category");
            System.out.println("3-Price");
            System.out.println("4-Discounted Price");
            System.out.println("5-Description");

            int choose = Integer.parseInt(input.nextLine());
            System.out.println("Type new value of property");
            switch (choose) {
                case 1 -> products.get(index).setProductName(input.nextLine());
                case 2 -> products.get(index).setCategory(input.nextLine());
                case 3 -> products.get(index).setPrice(Integer.parseInt(input.nextLine()));
                case 4->  products.get(index).setDiscountedPrice(Integer.parseInt(input.nextLine()));
                case 5->  products.get(index).setDescription(input.nextLine());
                default -> System.out.println("You did not select from menu");
            }
        }
        catch (NumberFormatException nfe){
            System.out.println("You have typed char instead of number");
        }
    }

    /**
     * Prints all the products of trader.If products not receive from database yet, receives them
     * @param db Database where products received
     */
    public void displayProducts(DataBase db){
        if(products.size() == 0)
            loadProductsT(db);

        if(products.size() == 0){
            System.out.println("Not found any Product");
            return;
        }
        for (Product p : products) {
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            System.out.println("ID:"+ p.getProductID());
            System.out.println("Name:" + p.getProductName());
            System.out.println("Category:" + p.getCategory());
            System.out.println("Price:" + p.getPrice());
            System.out.println("Discounted Price:" + p.getDiscountedPrice());
            System.out.println("Description:" + p.getDescription());
        }
    }

    /**
     * Creating random product ID
     * @return Randomly created product ID
     */
    private String generateRandomProductID(){
        String containingChars = "ABCDEFGHIJKLMNOPQRSTUVWYZ" +"0123456789";
        StringBuilder sb = new StringBuilder(16);
        for(int i=0;i<16;++i){
            int randomChar = (int) (containingChars.length() * Math.random());
            sb.append(containingChars.charAt(randomChar));
        }
        return sb.toString();
    }

    /**
     * Receive order and save it.
     * @param order The order that has been created.
     */
    public void receiveOrder(Order order){
        orders.offer(order);
    }

    /**
     * Edit order(in terms of status) and send it to the DataBase
     * @param order The order that edited
     * @param db The DataBase
     */
    public void createEditedOrder(Order order,DataBase db){
        db.transferEditedOrder(order);
    }

    /**
     * Displays and meet or cancel the orders.
     * @param db Database where orders received
     */
    public void displayOrders(DataBase db){
        System.out.println("There are "+ orders.size() +" orders waiting for your confirmation");
        if(orders.size() !=0) {
            System.out.println("ProductID-----TraderName-----Status");
            for (Order order : orders) {
                System.out.print(order.getProductID() + " | " + order.getTraderName() + " | ");
                if (order.getStatus() == 1)
                    System.out.print("ACCEPTED\n");
                else if (order.getStatus() == 2)
                    System.out.print("CANCELED\n");
                else
                    System.out.print("WAITING\n");
            }
            System.out.println("-----------------------------------------------");
            if (orders.peek() != null) {
                Order order = orders.peek();
                System.out.println("The order that have priority:");
                System.out.println("ProductID:" + order.getProductID() + "--CustomerName:" + order.getCustomerName());
                System.out.println("Type MEET for meet the order , CANCEL for cancel order or EXIT for exit");
                String inputStr = input.nextLine();
                switch (inputStr) {
                    case "MEET" -> {
                        order.setStatus(1);
                        createEditedOrder(order, db);
                    }
                    case "CANCEL" -> {
                        order.setStatus(2);
                        createEditedOrder(order, db);
                    }
                    case "EXIT" -> System.out.println("Returning Trader menu");
                    default -> System.out.println("You did not typed MEET or CANCEL");
                }
                orders.poll();
            }
        }
    }

    /**
     * Loads the products from database
     * @param db Database where products received
     */
    public void loadProductsT(DataBase db){
        try{
            ArrayList<Product> temp = db.loadProducts(getUserName()).get(getUserName());
            if(temp != null)
                products = temp;
        }catch (IOException e){
            System.out.println("I/O Exception occurred. The products could not loaded. ");
        }
    }

    /**
     * Menu for perform trader operations
     * @param db Database for communicate with database
     */
    @Override
    public void performTasks(DataBase db) {
        try {
            String inputLine;
            int choice;
            System.out.println("ID:" + getUserID() +" ---Name:" + getUserName()) ;
            do {
                System.out.println("\nPlease select from the Trader Menu:");
                System.out.println("1-Add Product");
                System.out.println("2-Remove Product");
                System.out.println("3-Edit Product");
                System.out.println("4-Display and Edit Orders");
                System.out.println("5-Display Products");
                System.out.println("0-Exit");
                inputLine = input.nextLine();
                choice = Integer.parseInt(inputLine);

                switch (choice) {
                    case 1 -> addProduct(db);
                    case 2 -> removeProduct(db);
                    case 3 -> editProduct(db);
                    case 4 -> displayOrders(db);
                    case 5 -> displayProducts(db);
                    case 0 -> System.out.println("Updating data files.\nReturning application menu..");
                    default -> System.out.println("Please select from menu\n");
                }
            } while (choice != 0);
        }catch (NumberFormatException nfe){
            System.out.println("!You typed char instead of number!");
            nfe.printStackTrace();
        }
    }

    public void driverCodeAddProduct(DataBase db){
        System.out.println("Before:");
        displayProducts(db);
        products.add(new Product(generateRandomProductID(), "Test", "Test", 2, 1, "The product for test the system",getUserName()));
        db.updateProducts(this,products);
        System.out.println("After:");
        displayProducts(db);
    }
    public void driverCodeDisplayOrders(){
        for (Order order : orders) {
            System.out.print(order.getProductID() + " | " + order.getTraderName() + " | ");
            if (order.getStatus() == 1)
                System.out.print("ACCEPTED\n");
            else if (order.getStatus() == 2)
                System.out.print("CANCELED\n");
            else
                System.out.print("WAITING\n");
        }
    }
    public void driverCodeCancelOrder(DataBase db){
        if(orders.peek() != null) {
            Order order = orders.peek();
            System.out.println("The order that have priority:");
            System.out.println("ProductID:" + order.getProductID() + "--CustomerName:" + order.getCustomerName());

            order.setStatus(2);
            createEditedOrder(order, db);
            System.out.println("The Order successfully canceled");
        }
    }

}
