import java.io.*;
import java.util.*;


public class DataBase {

    static final Scanner input = new Scanner(System.in);

    private HashMap<String,ArrayList<Product>> tempProducts;
    private final String csvFilePath;
    private final String usersFilePath;
    private final String productsFilePath;
    private final String ordersFilePath;

    /**
     * The users of the system(trader and customers) according to their name.
     */
    private final HashMap<Integer,User> users;
    /**
     * The ID of next user that will be register
     */
    private int nextUserID;

    DataBase()throws IOException{
        try {
            users = new HashMap<>();
            tempProducts = new HashMap<>();
            nextUserID = 10000000;
            csvFilePath = "e-commerce-samples.csv";
            usersFilePath = "users.txt";
            productsFilePath = "products.txt";
            ordersFilePath = "orders.txt";
            createDataFiles();
        }catch (IOException e){
            System.out.println("The IO Exception is occurred.Can not create Database");
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * The menu for Application
     */
    public void initialMenu(){

        try {
            String inputLine;
            int choice;

            do {
                System.out.println("\n---------------Welcome to the E-Shopping Application---------------\n");
                System.out.println("Please select:");
                System.out.println("1-Sign In");
                System.out.println("2-Sign Up");
                System.out.println("0-Exit");

                inputLine = input.nextLine();
                choice = Integer.parseInt(inputLine);

                switch (choice) {
                    case 1 -> signIn();
                    case 2 -> signUp();
                    case 0 -> System.out.println("Thanks for using E-Shopping Application. Have a nice day!");
                    default -> System.out.println("Please select from menu\n");
                }
            } while (choice != 0);
        }catch (NumberFormatException nfe){
            System.out.println("!You typed char instead of number!");
            nfe.printStackTrace();
        }
    }

    /**
     * Log In to the system.
     * @throws NumberFormatException  If the userID does not contain a parsable int.
     */
    private void signIn() throws NumberFormatException{
        int inputUserID;

        System.out.print("UserID:");
        inputUserID = Integer.parseInt(input.nextLine());
        System.out.print("Password:");
        String inputPassword = input.nextLine();

        if(users.containsKey(inputUserID)) {
            User user = users.get(inputUserID);
            if(user.getUserPassword().equals(inputPassword)){
                user.performTasks(this);
            }
            else
                System.out.println("Wrong PassWord.");
        }
        else
            System.out.println("Wrong ID.");
    }

    /**
     * Register user to the system.
     * Receive information's from user.
     * @throws NumberFormatException  If the String does not contain a parsable int.
     */
    private void signUp()throws NumberFormatException{
        String inputUserName;
        String inputPassword;
        int type;

        System.out.print("Please Write Your Name(Your Name Must Be Unique):");
        inputUserName = input.nextLine();
        System.out.print("Please Write Yor Password (Your password must be exactly 6 char):");
        inputPassword = input.nextLine();
        if(inputPassword.length() != 6){
            System.out.println("The password is not exactly 6 char");
            return;
        }
        if(isNotContainsUser(inputUserName)) {
            System.out.println("Your ID is:" + nextUserID);
            System.out.println("Don't forget that, users must enter to the system with their ID's\n");
            System.out.println("If you are Trader type 0,\nIf you are Customer type 1");
            type = Integer.parseInt(input.nextLine());
            if(type == 0)
                users.put(nextUserID,new Trader(nextUserID,inputPassword,inputUserName));
            else
                users.put(nextUserID,new Customer(nextUserID,inputPassword,inputUserName));
        }
        else{
            System.out.println("The name is already taken.Please type something else.");
            return;
        }
        updateUsersFile(users.get(nextUserID));
        nextUserID++;
        System.out.println("Register operation successfully completed.");
    }

    /**
     * Creating data files(users,products and orders) according to the csv file.
     * If the data files is created before, program does not open csv file again.
     * Instead of receiving data from csv file, receive data from data files.
     * @throws IOException If an I/O error occurs
     */
    public void createDataFiles() throws IOException{

        // The checkers for checks that the files is created before or not
        boolean isUsersFileExistBefore = true;
        boolean isProductFileExistBefore = true;

        System.out.println("Creating data files please wait...");

        // csv file
        File csvFile = new File(System.getProperty("user.dir"),csvFilePath);

        // users file(users.txt) to store system users
        File userFile = new File(System.getProperty("user.dir"),usersFilePath);
        if(userFile.createNewFile()){   // create file if file not exists
            System.out.println("The user file is created at specified path. The file was not exist.");
            isUsersFileExistBefore = false;
        }

        // products file(products.txt) to store system products
        File productFile = new File(System.getProperty("user.dir"),productsFilePath);
        if(productFile.createNewFile()){ // create file if file not exists
            System.out.println("The product file is created at specified path. The file was not exist.");
            isProductFileExistBefore = false;
        }

        File orderFile = new File(System.getProperty("user.dir"),ordersFilePath);
        if(orderFile.createNewFile()){ // create file if file not exists
            System.out.println("The order file is created at specified path. The file was not exist.");
            PrintWriter orderWriter = new PrintWriter(orderFile);
            orderWriter.write("product_ID;customer_name;trader_name;status\n");
            orderWriter.close();
        }


        if(isProductFileExistBefore && isUsersFileExistBefore){
            System.out.println("The data files(users and products) are created before.");
            System.out.println("If the csv file updated ,please delete data files and rerun program.");
            System.out.println("Otherwise please continue.");
            readUsersFile(userFile);
            readOrdersFile(orderFile);
        }
        else{
            readCSVFile(csvFile);
            writeProductsToFile(productFile);
            writeUsersToFile(userFile);
        }

        System.out.println("Data files successfully created.");

        tempProducts.clear();
    }

    /**
     * Reads csv file. Store the users into the users variable.And stores all the products into the allProducts variable.
     * @param csvFile The csv file
     * @throws IOException If an I/O error occurs
     */
    private void readCSVFile(File csvFile) throws IOException{
        // reader for read csv file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
        bufferedReader.readLine(); // reading first line(since first line does not include any data).

        String csvLine,userName,productID,productName,productCategory,productDescription;
        int productPrice,productDiscPrice;

        Product product;
        Trader trader;

        while((csvLine = bufferedReader.readLine()) != null){
            // reading product properties
            productID = csvLine.split(";")[0];productName = csvLine.split(";")[1];
            productCategory = csvLine.split(";")[2];productPrice = Integer.parseInt(csvLine.split(";")[3]);
            productDiscPrice = Integer.parseInt(csvLine.split(";")[4]);productDescription = csvLine.split(";")[5];
            userName = csvLine.split(";")[6];

            // saving products into hashmap
            product = new Product(productID, productName, productCategory, productPrice, productDiscPrice, productDescription, userName);
            tempProducts.putIfAbsent(userName, new ArrayList<>());
            tempProducts.get(userName).add(product);

            // check whether trader saved to the system before or not
            if(isNotContainsUser(userName)){
                StringBuilder sb = new StringBuilder(Integer.toString(nextUserID));
                sb.deleteCharAt(1);
                sb.deleteCharAt(2);
                trader = new Trader(nextUserID,sb.toString(),userName);
                users.put(nextUserID++,trader);
            }
        }
        bufferedReader.close();
    }

    /**
     * Checks that given user name is already in system or not
     * @param userName The user name that will be searched for
     * @return If user name is already in system returns false,otherwise true
     */
    private boolean isNotContainsUser(String userName){
        for(Map.Entry<Integer,User> entry : users.entrySet()){
            User user = entry.getValue();
            if(user.getUserName().equals(userName))
                return false;
        }
        return true;
    }

    /**
     * Writes the all productions to the products.txt
     * @param productFile The products.txt file
     * @throws IOException If an I/O error occurs
     */
    private void writeProductsToFile(File productFile) throws IOException{
        PrintWriter productWriter = new PrintWriter(productFile);
        productWriter.write("id;product_name;product_category_tree;price;discounted_price;description;trader\n");

        for(Map.Entry<String,ArrayList<Product>> entry : tempProducts.entrySet()){
            ArrayList<Product> product = entry.getValue();
            for(Product p : product){
                productWriter.write( p.getProductID()+";"+
                                        p.getProductName()+";"+
                                        p.getCategory()+";"+
                                        p.getPrice()+";"+
                                        p.getDiscountedPrice()+";"+
                                        p.getDescription()+";"+
                                        p.getTraderName()+"\n");
            }
        }
        productWriter.close();
    }

    /**
     * Writes the all users to the users.txt
     * @param userFile The users.txt file
     * @throws IOException If an I/O error occurs
     */
    private void writeUsersToFile(File userFile) throws  IOException{
        PrintWriter userWriter = new PrintWriter(userFile);
        userWriter.write("id;user_password;user_name;role\n");

        for(Map.Entry<Integer,User> entry : users.entrySet()){
            User user = entry.getValue();
            userWriter.write( user.getUserID()+";"+
                    user.getUserPassword()+";"+
                    user.getUserName()+";");
            if(user instanceof Trader)
                userWriter.write(0+"\n");
            else
                userWriter.write(1+"\n");
        }
        userWriter.close();
    }

    /**
     * Reads users.txt and stores users information's into the system.
     * @param usersFile The users.txt file
     * @throws IOException If an I/O error occurs
     */
    private void readUsersFile(File usersFile) throws IOException{
        // reader for read users file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(usersFile));
        bufferedReader.readLine(); // reading first line(since first line does not include any data).

        String line,userName,userPassword;
        int userID,userRole;

        while((line = bufferedReader.readLine()) != null){
            // reading product properties
            userID = Integer.parseInt(line.split(";")[0]); userPassword = line.split(";")[1];
            userName = line.split(";")[2];  userRole = Integer.parseInt(line.split(";")[3]);

            Trader trader;
            Customer customer;

            if(userRole == 0) {
                trader = new Trader(userID, userPassword, userName);
                users.put(userID, trader);
            }
            else{
                customer = new Customer(userID, userPassword, userName);
                users.put(userID, customer);
            }
            nextUserID++;
        }
        bufferedReader.close();
    }

    /**
     * Reads orders.txt and stores into the system.
     * @param ordersFile The orders.txt file
     * @throws IOException If an I/O error occurs
     */
    private void readOrdersFile(File ordersFile) throws IOException{
        // reader for read orders file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(ordersFile));
        bufferedReader.readLine(); // reading first line(since first line does not include any data).

        String line,productID,customerName,traderName;
        int status;
        Trader trader;
        Customer customer;

        while((line = bufferedReader.readLine()) != null){
            // reading product properties
            productID = line.split(";")[0]; customerName = line.split(";")[1];
            traderName = line.split(";")[2]; status = Integer.parseInt(line.split(";")[3]);

            trader = (Trader) findUser(traderName);
            customer = (Customer) findUser(customerName);

            trader.receiveOrder(new Order(traderName,customerName,productID,status));
            customer.receiveOrder(new Order(traderName,customerName,productID,status));

        }
        bufferedReader.close();

    }

    /**
     * Append the given user into the users.txt file.
     * @param user The user that will be append to the file.
     */
    private void updateUsersFile(User user){
        try {
            File userFile = new File(System.getProperty("user.dir"),usersFilePath);

            FileWriter userWriter = new FileWriter(userFile,true);
            userWriter.write( user.getUserID()+";"+
                    user.getUserPassword()+";"+
                    user.getUserName()+";");
            if(user instanceof Trader)
                userWriter.write(0+"\n");
            else
                userWriter.write(1+"\n");
            userWriter.close();
        }catch (IOException ioe){
            System.out.println("I/O Exception detected.");
            ioe.printStackTrace();
        }
    }

    /**
     * Update the products of the Trader
     * @param t The Trader
     * @param traderProducts All products of the Trader
     */
    public void updateProducts(Trader t,ArrayList<Product> traderProducts){
        try {
            updateProductFile(t.getUserName(),traderProducts);
        } catch (IOException e) {
            System.out.println("I/O Exception occurred.'products.txt' file can not updated.");
            e.printStackTrace();
        }
    }

    /**
     * Updates "products.txt" file.
     * @param traderName The name of Trader
     * @param traderProducts All products of the Trader
     * @throws IOException If an I/O error occurs
     */
    @SuppressWarnings("unchecked")
    private void updateProductFile(String traderName,ArrayList<Product> traderProducts) throws IOException{
        tempProducts.clear();
        tempProducts.put(traderName,(ArrayList<Product>) traderProducts.clone());

        File productFile = new File(System.getProperty("user.dir"),productsFilePath);

        // reader for read csv file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(productFile));
        bufferedReader.readLine(); // reading first line(since first line does not include any data).

        String csvLine,userName,productID,productName,productCategory,productDescription;
        int productPrice,productDiscPrice;

        Product product;

        while((csvLine = bufferedReader.readLine()) != null){
            // reading product properties
            productID = csvLine.split(";")[0];productName = csvLine.split(";")[1];
            productCategory = csvLine.split(";")[2];productPrice = Integer.parseInt(csvLine.split(";")[3]);
            productDiscPrice = Integer.parseInt(csvLine.split(";")[4]);productDescription = csvLine.split(";")[5];
            userName = csvLine.split(";")[6];

            // saving products into hashmap
            product = new Product(productID, productName, productCategory, productPrice, productDiscPrice, productDescription, userName);
            tempProducts.putIfAbsent(userName, new ArrayList<>());
            tempProducts.get(userName).add(product);

        }
        bufferedReader.close();
        writeProductsToFile(productFile);

    }

    /**
     * Transfer the order from Customer to Trader
     * @param order the order
     */
    public void transferOrder(Order order){
        try {
            Trader trader = (Trader) findUser(order.getTraderName());
            trader.receiveOrder(order);

            File orderFile = new File(System.getProperty("user.dir"),ordersFilePath);
            FileWriter orderWriter = new FileWriter(orderFile, true);
            orderWriter.write(order.getProductID()+";"+order.getCustomerName()+";"+order.getTraderName()+";"+order.getStatus()+"\n");

            orderWriter.close();
        } catch(IOException e){
           System.out.println("IO Exception occurred. Can not update orders file.");
           e.printStackTrace();
        }
    }

    /**
     * Transfer the edited Order from Trader to Customer
     * @param order the order
     */
    public void transferEditedOrder(Order order){
        Customer customer = (Customer) findUser(order.getCustomerName());
        customer.receiveEditedOrder(order);
    }

    /**
     * Load products of given Trader
     * @param traderName Name of Trader
     * @return Returns the products of Trader
     * @throws IOException If an I/O error occurs
     */
    public HashMap<String,ArrayList<Product>> loadProducts(String traderName) throws IOException{
        tempProducts.clear();

        if(isNotContainsUser(traderName)){
            System.out.println("The given name is not in the System");
            return null;
        }

        File productFile = new File(System.getProperty("user.dir"),productsFilePath);

        // reader for read products file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(productFile));
        bufferedReader.readLine(); // reading first line(since first line does not include any data).

        String csvLine,pTraderName,productID,productName,productCategory,productDescription;
        int productPrice,productDiscPrice;

        Product product;

        while((csvLine = bufferedReader.readLine()) != null){
            // reading product properties
            productID = csvLine.split(";")[0];productName = csvLine.split(";")[1];
            productCategory = csvLine.split(";")[2];productPrice = Integer.parseInt(csvLine.split(";")[3]);
            productDiscPrice = Integer.parseInt(csvLine.split(";")[4]);productDescription = csvLine.split(";")[5];
            pTraderName = csvLine.split(";")[6];

            if (traderName.equals(pTraderName)){
                // saving products into hashmap
                product = new Product(productID, productName, productCategory, productPrice, productDiscPrice, productDescription, pTraderName);
                tempProducts.putIfAbsent(pTraderName, new ArrayList<>());
                tempProducts.get(pTraderName).add(product);
                break;
            }
        }
        while((csvLine = bufferedReader.readLine()) != null && traderName.equals(csvLine.split(";")[6])){
            // reading product properties
            productID = csvLine.split(";")[0];productName = csvLine.split(";")[1];
            productCategory = csvLine.split(";")[2];productPrice = Integer.parseInt(csvLine.split(";")[3]);
            productDiscPrice = Integer.parseInt(csvLine.split(";")[4]);productDescription = csvLine.split(";")[5];
            pTraderName = csvLine.split(";")[6];

            // saving products into hashmap
            product = new Product(productID, productName, productCategory, productPrice, productDiscPrice, productDescription, pTraderName);
            tempProducts.putIfAbsent(pTraderName, new ArrayList<>());
            tempProducts.get(pTraderName).add(product);

        }


        bufferedReader.close();
        return tempProducts;
    }

    /**
     * Load products from file to the system according to given keyword.
     * @param keyword The search text
     * @return The products that contain the search text in their name or description should be returned in the results.
     * @throws IOException If an I/O error occurs
     */
    public ArrayList<Product> loadSearchedProducts(String keyword) throws  IOException{

        ArrayList<Product> products = new ArrayList<>();

        File productFile = new File(System.getProperty("user.dir"),productsFilePath);
        // reader for read csv file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(productFile));
        bufferedReader.readLine(); // reading first line(since first line does not include any data).

        String csvLine,userName,productID,productName,productCategory,productDescription;
        int productPrice,productDiscPrice;

        while((csvLine = bufferedReader.readLine()) != null){
            // reading product properties
            productID = csvLine.split(";")[0];productName = csvLine.split(";")[1];
            productCategory = csvLine.split(";")[2];productPrice = Integer.parseInt(csvLine.split(";")[3]);
            productDiscPrice = Integer.parseInt(csvLine.split(";")[4]);productDescription = csvLine.split(";")[5];
            userName = csvLine.split(";")[6];

            if(productName.contains(keyword) || productDescription.contains(keyword))
                products.add(new Product(productID, productName, productCategory, productPrice, productDiscPrice, productDescription, userName));

        }
        bufferedReader.close();
        return products;
    }

    /**
     * Finds user in the HashMap end returns it.
     * @param userName The name of User that will be search
     * @return If the user is in system , returns the User , null otherwise
     */
    public User findUser(String userName){
        for(Map.Entry<Integer,User> entry : users.entrySet()){
            User user = entry.getValue();
            if(user.getUserName().equals(userName))
                return user;
        }
        return null;
    }


    public void driverCode(){
        System.out.println("\n---------------Welcome to the Driver Code of E-Shopping Application---------------\n");

        System.out.println("\n----Test For Customer----");

        Customer customer = (Customer) findUser("DriverCustomer");

        System.out.println("The Id of tested customer = 10003181 ; The name of tested customer = 'DriverCustomer' ; The password of tested customer = '123456'");
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Testing Product Searching: Search keyword is 'Transtal'");
        customer.driverCodeSearch(this);
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Testing Printing All The Products of the Trader: Trader name is 'Transtal'");
        customer.driverCodeSearchTrader(this);
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Testing Printing The Orders of the Customer");
        customer.driverCodeOrders();

        System.out.println("\n----Test For Trader----");

        Trader trader = (Trader) findUser("DriverTrader");
        System.out.println("The Id of tested customer = 10003182 ; The name of tested customer = 'DriverTrader' ; The password of tested customer = '123456'");
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Testing Add Product to the Store");
        trader.driverCodeAddProduct(this);
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Testing Displaying The Orders");
        trader.driverCodeDisplayOrders();
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        System.out.println("Testing Canceling The Order");
        System.out.println("\nBefore(For Customer");
        customer.driverCodeOrders();
        System.out.println();
        trader.driverCodeCancelOrder(this);
        System.out.println("\nAfter(For Customer");
        customer.driverCodeOrders();


    }


}
