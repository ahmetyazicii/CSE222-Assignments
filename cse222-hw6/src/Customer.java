import java.io.IOException;
import java.util.*;

public class Customer extends AbstractUser{

    static final Scanner input = new Scanner(System.in);

    private LinkedList<Order> orders;
    private ArrayList<Product> searchResults;


    Customer(int id,String password,String name){
        super(id,password,name);
        orders = new LinkedList<>();
    }

    /**
     * Menu for perform customer operations
     * @param db Database for communicate with database
     */
    @Override
    public void performTasks(DataBase db) {
        try {
            String inputLine;
            int choice;
            System.out.println("ID:" + getUserID() +" ---Name:" + getUserName()) ;
            do {
                System.out.println("\nPlease select from the Customer Menu:");
                System.out.println("1-Search Product");
                System.out.println("2-Search Products of Trader");
                System.out.println("3-Display Orders");
                System.out.println("0-Exit");
                inputLine = input.nextLine();
                choice = Integer.parseInt(inputLine);

                switch (choice) {
                    case 1 -> searchProduct(db);
                    case 2 -> searchPTrader(db);
                    case 3 -> displayOrders();
                    case 0 -> System.out.println("Returning application menu..");
                    default -> System.out.println("Please select from menu\n");
                }
            } while (choice != 0);
        }catch (NumberFormatException nfe){
            System.out.println("!You typed char instead of number!");
            nfe.printStackTrace();
        }
    }

    /**
     * Receive edited(in terms of status) order and save it.
     * @param order The order that has been created.
     */
    public void receiveEditedOrder(Order order){
        for(int i=0;i<orders.size();++i){
            if(orders.get(i).getProductID().equals(order.getProductID()))
                orders.set(i,order);
        }
    }

    /**
     * Prints out the orders.
     */
    public void displayOrders(){
        System.out.println("There are "+ orders.size() +" orders that you placed.");
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
        }
    }

    /**
     * Display all the products of trader.The trader name is taken from user
     * @param db Database where information's received
     */
    public void searchPTrader(DataBase db){
        try {
            System.out.println("Please write the name of Trader");
            String tName = input.nextLine();

            ArrayList<Product> temp =db.loadProducts(tName).get(tName);
                if(temp == null){
                    System.out.println("Could not find the trader in system.");
                    return;
                }
            TreeSet<Product> traderProducts = new TreeSet<>(temp);

            System.out.println("The name of Trader is: " + tName + " . The products of trader are:");
            for (Product p : traderProducts) {
                System.out.println("---------------------------------------------------------------------------------------------------------------------------");
                System.out.println("ID:"+ p.getProductID());
                System.out.println("Name:" + p.getProductName());
                System.out.println("Category:" + p.getCategory());
                System.out.println("Price:" + p.getPrice());
                System.out.println("Discounted Price:" + p.getDiscountedPrice());
                System.out.println("Description:" + p.getDescription());
                System.out.println("Trader Name:" + p.getTraderName());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Search and query the products by their name.
     * @param db Database where information's received
     */
    public void searchProduct(DataBase db){
        try {
            boolean check = true;
            System.out.println("Please type a word to search");
            String word = input.nextLine();
            searchResults = db.loadSearchedProducts(word);
            if(searchResults.size() == 0){
                System.out.println("Could not find any result");
                return;
            }
            HeapSortByName.sort(searchResults,1);
            System.out.println("\nThe search results for keyword: '" + word + "' are showing in decreasing order:");
            printSearchProducts(searchResults);
            int choose;
            do{
                sortMenu();
                choose = Integer.parseInt(input.nextLine());

                switch (choose) {
                    case 0 -> {
                        HeapSortByName.sort(searchResults, 1);
                        System.out.println("\nThe search results for keyword: '" + word + "' are showing in decreasing order in terms of product name:");
                        printSearchProducts(searchResults);
                    }
                    case 1 -> {
                        HeapSortByName.sort(searchResults, -1);
                        System.out.println("\nThe search results for keyword: '" + word + "' are showing in increasing order in terms of product name:");
                        printSearchProducts(searchResults);
                    }
                    case 2 -> {
                        MergeSortByPrice.sort(searchResults, 1);
                        System.out.println("\nThe search results for keyword: '" + word + "' are showing in low to high in terms of price:");
                        printSearchProducts(searchResults);
                    }
                    case 3 -> {
                        MergeSortByPrice.sort(searchResults, -1);
                        System.out.println("\nThe search results for keyword: '" + word + "' are showing in high to low in terms of price:");
                        printSearchProducts(searchResults);
                    }
                    case 4 -> {
                        QuickSortByPerDiscPrice.sort(searchResults, 1);
                        System.out.println("\nThe search results for keyword: '" + word + "' are showing in low to high in terms of percentage of discount:");
                        printSearchProducts(searchResults);
                    }
                    case 5 -> {
                        QuickSortByPerDiscPrice.sort(searchResults, -1);
                        System.out.println("\nThe search results for keyword: '" + word + "' are showing in high to low percentage of discount:");
                        printSearchProducts(searchResults);
                    }
                    case 6 -> upperThreshold();
                    case 7 -> lowerThreshold();
                    case 8 -> priceRange();
                    case 9 -> System.out.println("This operation does not implemented yet.Please wait the next update.Thanks for your understanding.");
                    case 10 -> {
                        buyProduct(db);
                        check = false;
                    }
                    case -1 -> check = false;
                    default ->  System.out.println("You did not select from menu");
                }


            }while(check);

        } catch (IOException e){
            System.out.println("Can not receive products from file.");
            e.printStackTrace();
        }
        catch (NumberFormatException nfe){
            System.out.println("You typed char instead of number.");
        }
    }

    /**
     * Print out the result of search
     */
    private void printSearchProducts(ArrayList<Product> products){

        for (Product p : products) {
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            System.out.println("ID:"+ p.getProductID());
            System.out.println("Name:" + p.getProductName());
            System.out.println("Category:" + p.getCategory());
            System.out.println("Price:" + p.getPrice());
            System.out.println("Discounted Price:" + p.getDiscountedPrice());
            System.out.println("Description:" + p.getDescription());
            System.out.println("Trader Name:" + p.getTraderName());
        }
    }

    /**
     * Print the menu for sort type
     */
    private void sortMenu(){
        System.out.println("\nSort by:");
        System.out.println("0-Name:Decreasing Order");
        System.out.println("1-Name:Increasing  Order");
        System.out.println("2-Price:Low to High");
        System.out.println("3-Price:High to Low");
        System.out.println("4-The Percentage of the Discount:Low to High");
        System.out.println("5-The Percentage of the Discount:High to Low");
        System.out.println("6-Determine Upper Threshold");
        System.out.println("7-Determine Lower Threshold");
        System.out.println("8-Determine Price Range");
        System.out.println("9-Filter by Category");
        System.out.println("10-Buy Product");
        System.out.println("-1-Terminate Search");

    }

    /**
     * Prints the search results with respect to given upper threshold for price.
     */
    private void upperThreshold(){
        try {
            ArrayList<Product> temp = new ArrayList<>();
            System.out.println("Please type Upper Threshold");
            int threshold = Integer.parseInt(input.nextLine());
            for (Product p : searchResults) {
                if(p.getPrice() <= threshold)
                    temp.add(p);
            }
            MergeSortByPrice.sort(temp,-1);
            System.out.println("\nThe search results are showing in high to low in terms of upper threshold(" + threshold+ "):");
            printSearchProducts(temp);

        }catch (NumberFormatException nfe){
            System.out.println("You typed char instead of number.");
        }
    }
    /**
     * Prints the search results with respect to given lower threshold for price.
     */
    private void lowerThreshold(){
        try {
            ArrayList<Product> temp = new ArrayList<>();
            System.out.println("Please type Lower Threshold");
            int threshold = Integer.parseInt(input.nextLine());
            for (Product p : searchResults) {
                if(p.getPrice() >= threshold)
                    temp.add(p);
            }
            MergeSortByPrice.sort(temp,1);
            System.out.println("\nThe search results are showing in low to high in terms of lower threshold(" + threshold+ "):");
            printSearchProducts(temp);

        }catch (NumberFormatException nfe){
            System.out.println("You typed char instead of number.");
        }
    }
    /**
     * Prints the search results with respect to given upper and lower thresholds for price.
     */
    private void priceRange(){
        try {
            ArrayList<Product> temp = new ArrayList<>();
            System.out.println("Please type Lower Threshold");
            int lowerThreshold = Integer.parseInt(input.nextLine());
            System.out.println("Please type Upper Threshold");
            int upperThreshold = Integer.parseInt(input.nextLine());
            for (Product p : searchResults) {
                if(p.getPrice() >= lowerThreshold && p.getPrice() <= upperThreshold)
                    temp.add(p);
            }
            MergeSortByPrice.sort(temp,-1);
            System.out.println("\nThe search results are showing from lower threshold( " + lowerThreshold+ ") to upper threshold(" + upperThreshold+ "):");
            printSearchProducts(temp);

        }catch (NumberFormatException nfe){
            System.out.println("You typed char instead of number.");
        }

    }

    /**
     * Buy product with typing productID and save it to the orders file
     * @param db Database where orders will be transferred
     */
    public void buyProduct(DataBase db){
        System.out.println("Please type the ID of product you want to buy.");
        String pID = input.nextLine();
        for (Product p : searchResults) {
            if(p.getProductID().equals(pID)){
                orders.add(new Order(p.getTraderName(),getUserName(),p.getProductID(),0));
                createOrder(orders.getLast(),db);
                System.out.println("The order has been successfully created.");
                return;
            }
        }
        System.out.println("The order could not be created successfully.Product could not find.");
    }

    /**
     * Transfers order to the database
     * @param o The order that created
     * @param db Database where orders will be transferred
     */
    public void createOrder(Order o,DataBase db){
        db.transferOrder(o);
    }

    /**
     * Receive order from database and save it to the order list.
     * @param order The order that will be saved
     */
    public void receiveOrder(Order order){
        orders.offer(order);
    }


    public void driverCodeSearch(DataBase db){
        try {
            searchResults = db.loadSearchedProducts("Transtal");
            HeapSortByName.sort(searchResults,1);
            System.out.println("\nPrinting search results in decreasing order of product name");
            printSearchProducts(searchResults);
            System.out.println("****************************************************************************************");
            HeapSortByName.sort(searchResults,-1);
            System.out.println("\nPrinting search results in increasing order of product name");
            printSearchProducts(searchResults);
            System.out.println("****************************************************************************************");
            MergeSortByPrice.sort(searchResults, 1);
            System.out.println("\nPrinting search results in low to high order of product price");
            printSearchProducts(searchResults);
            System.out.println("****************************************************************************************");
            MergeSortByPrice.sort(searchResults, -1);
            System.out.println("\nPrinting search results in high to low order of product price");
            printSearchProducts(searchResults);
            System.out.println("****************************************************************************************");
            QuickSortByPerDiscPrice.sort(searchResults, 1);
            System.out.println("\nPrinting search results in low to high order of product percentage of the discount");
            printSearchProducts(searchResults);
            System.out.println("****************************************************************************************");
            QuickSortByPerDiscPrice.sort(searchResults, -1);
            System.out.println("\nPrinting search results in high to low order of product percentage of the discount");
            printSearchProducts(searchResults);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void driverCodeSearchTrader(DataBase db){
        try {

            ArrayList<Product> temp =db.loadProducts("Transtal").get("Transtal");
            if(temp == null){
                System.out.println("Could not find the trader in system.");
                return;
            }
            TreeSet<Product> traderProducts = new TreeSet<>(temp);

            System.out.println("The name of Trader is: 'Transtal'. The products of trader are:");
            for (Product p : traderProducts) {
                System.out.println("Name-----Category-----Price-----DiscountedPrice-----Description");
                System.out.println(p.getProductName() + "--" + p.getCategory() + "--" + p.getPrice() + "--" + p.getDiscountedPrice() + "--" + p.getDescription());
                System.out.println("---------------------------------------------------------------------------------------------------------------------------");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void driverCodeOrders(){
        displayOrders();
    }
}
