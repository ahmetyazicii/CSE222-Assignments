import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Company {

    private  Administrator[] admin;
    private  BranchEmployee[] employee;
    private  Customer[] customer;
    private  Branch[] branch;
    private static int adminNum = 0;
    private static int employeeNum = 0;
    private static int customerNum = 0;
    private static int branchNum = 0;
    private static int adminLimit = 2;
    private static int employeeLimit = 6;
    private static int customerLimit = 10;
    private static int branchLimit = 5;
    private String saveFilePath;

    /**
     * constructor for Company Class
     * @throws FileNotFoundException throw exception when file not found
     */
    Company() throws FileNotFoundException{
        String abc = System.getProperty("user.dir");
        abc += "/savefile.txt";
        saveFilePath = abc;

        admin = new Administrator[adminLimit];
        employee = new BranchEmployee[employeeLimit];
        customer = new Customer[customerLimit];
        branch = new Branch[branchLimit];

        // reading from file if file is not there, throw exception
        try {
            readFromFile();
            addAdmin(new Administrator("admin","admin"));
        }
        catch (FileNotFoundException e){
            System.out.println("Company can not created.Can not read from file");
            e.printStackTrace();
            throw new FileNotFoundException("File can not opened.");
        }
    }

    /**
     * reads all the company information from file(reading from database)
     * @throws FileNotFoundException throw exception when file not found
     */
    private void readFromFile() throws FileNotFoundException {

        File file = new File(getSaveFilePath());
        Scanner saveFile = new Scanner(file);
        adminNum = 0;
        employeeNum = 0;
        customerNum = 0;
        branchNum = 0;

        if(saveFile.hasNext()) {

            // reading admin info
            int iter = Integer.parseInt(saveFile.nextLine());
            String wholeLine;
            String[] tokens;
            for (int i = 0; i < iter; i++) {
                wholeLine = saveFile.nextLine();
                tokens = wholeLine.split(";", 2);
                admin[adminNum] = new Administrator(tokens[0], tokens[1]);
                adminNum++;
            }

            // reading employee info
            iter = Integer.parseInt(saveFile.nextLine());
            for (int i = 0; i < iter; i++) {
                wholeLine = saveFile.nextLine();
                tokens = wholeLine.split(";", 3);
                employee[employeeNum] = new BranchEmployee(tokens[0], tokens[1], Integer.parseInt(tokens[2]));
                employeeNum++;
            }

            // reading customer info
            iter = Integer.parseInt(saveFile.nextLine());
            for (int i = 0; i < iter; i++) {
                wholeLine = saveFile.nextLine();
                tokens = wholeLine.split(";", 6);
                if (tokens.length == 5)
                    customer[customerNum] = new Customer(tokens[0], tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]));
                else
                    customer[customerNum] = new Customer(tokens[0], tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]), tokens[5]);
                customerNum++;
            }


            //reading branch info
            int fileBranchID;
            iter = Integer.parseInt(saveFile.nextLine());
            fileBranchID = Integer.parseInt(saveFile.nextLine());
            for (int i = 0; i < iter; i++) {

                branch[branchNum] = new Branch(fileBranchID);
                branchNum++;
                wholeLine = saveFile.nextLine();
                tokens = wholeLine.split(";", 4);
                do {
                    addToBranch(fileBranchID, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    wholeLine = saveFile.nextLine();
                    tokens = wholeLine.split(";", 4);
                } while (saveFile.hasNext() && tokens.length > 2);

                fileBranchID = Integer.parseInt(tokens[0]);
            }
        }
        saveFile.close();
    }

    /**
     * reads all the company information to file(reading from database)
     * @throws IOException throw exception when IOException found
     */
    private void writeToFile() throws IOException {
        File file = new File(getSaveFilePath());
        if(!file.exists())
            file.createNewFile();
        PrintWriter writer = new PrintWriter(file);

        // writing admin info
        writer.println(getAdminNum());
        for(int i=0;i<adminLimit;++i)
            if(admin != null && admin[i] != null)
                writer.printf("%s;%s\n",admin[i].getUsername(),admin[i].getPassword());

        // writing employee info
        writer.println(getEmployeeNum());
        for(int i=0;i<employeeLimit;++i)
            if(employee != null && employee[i] != null)
            writer.printf("%s;%s;%d\n",employee[i].getUsername(),employee[i].getPassword(),employee[i].getBranch());

        // writing customer info
        writer.println(getCustomerNum());
        for(int i=0;i<customerLimit;++i) {
            if (customer != null && customer[i] != null) {
                if(customer[i].getPrevOrderNum() == 0) {
                    writer.printf("%s;%s;%s;%s;%d\n", customer[i].getUsername(), customer[i].getPassword(),
                                                      customer[i].getRealName(), customer[i].getRealSurname(),
                                                      customer[i].getCustomerID());
                }
                else {
                    writer.printf("%s;%s;%s;%s;%d;%s\n", customer[i].getUsername(), customer[i].getPassword(),
                                                         customer[i].getRealName(), customer[i].getRealSurname(),
                                                         customer[i].getCustomerID(), customer[i].getPreviousOrders());
                }
            }
        }

        // writing branch info
        writer.println(getBranchNum());
        for(int i=0;i<branchLimit;++i)
            if(branch != null && branch[i] != null) {
                writer.println(branch[i].getBranchID());
                for(int j=0;j<branch[i].getChairsModelNum();++j) {
                   for (int k = 0; k < branch[i].getChairsColorNum(); ++k)
                       writer.printf("%d;%d;%d;%d\n",0,j, k, branch[i].getAmountChairs(j, k));
               }
                for(int j=0;j<branch[i].getDesksModelNum();++j) {
                    for (int k = 0; k < branch[i].getDesksColorNum(); ++k)
                        writer.printf("%d;%d;%d;%d\n",1, j, k, branch[i].getAmountDesks(j, k));
                }
                for(int j=0;j<branch[i].getTablesModelNum();++j) {
                    for (int k = 0; k < branch[i].getTablesColorNum(); ++k)
                        writer.printf("%d;%d;%d;%d\n", 2, j, k, branch[i].getAmountTables(j, k));
                }
                for(int j=0;j<branch[i].getbCasesModelNum();++j) {
                        writer.printf("%d;%d;%d;%d\n", 3, j, 0, branch[i].getAmountbCases(j));
                }
                for(int j=0;j<branch[i].getCabinetsModelNum();++j) {
                        writer.printf("%d;%d;%d;%d\n", 4, j, 0, branch[i].getAmountCabinets(j));
                }
            }
        writer.close(); // closing file
    }

    // getters
    public static int getAdminNum() { return adminNum; }
    public static int getCustomerNum() { return customerNum; }
    public static int getEmployeeNum() { return employeeNum; }
    public static int getBranchNum() { return branchNum; }
    public String getSaveFilePath() { return saveFilePath; }

    /**
     * creating new customerID when new customer signIN
     * @return return the new customer ID
     */
    public int createCustomerID(){
        if (customerNum == 0) return 0;
        else return customerNum +4;
    }

    /**
     * adding admin to the system.Then updating save file.
     * @param adm the Administrator object which will be added to system.
     * @throws IndexOutOfBoundsException when the limit of system is full
     */
    public void addAdmin(Administrator adm) throws IndexOutOfBoundsException {

        if(adminNum >= admin.length)
            throw new IndexOutOfBoundsException("The limit is full.\n");
        if(checkUserInSystem(adm)){
            admin[adminNum] = new Administrator(adm.getUsername(),adm.getPassword());
            adminNum++;
            try {
                writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            if(!adm.getUsername().equals("admin"))
            System.out.println("The are admin in system with same username.");
        }
    }

    /**
     * adding employee to the system.Then updating save file.
     * @param emp the BranchEmployee object which will be added to system.
     * @throws IndexOutOfBoundsException when the limit of system is full
     */
    public void addEmployee(BranchEmployee emp) throws IndexOutOfBoundsException {
        if(employeeNum >= employee.length)
            throw new IndexOutOfBoundsException("The limit is full.\n");
        if(checkUserInSystem(emp)){
            employee[employeeNum] = new BranchEmployee(emp.getUsername(),emp.getPassword(),emp.getBranch());
            employeeNum++;
            try {
                writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("The are employee in system with same username.");

    }

    /**
     * removing employee from system. The employee info will receiving from screen
     */
    public void removeEmployee() {

        System.out.println("The list of employees.Please select employee number that you want to remove.");
        for(int i=0;i<getEmployeeNum();++i)
            System.out.printf("%d- %s",i+1,employee[i]);

        Scanner input = new Scanner(System.in);
        String inputLine =input.nextLine();
        int choice = Integer.parseInt(inputLine);

        if(choice<employee.length && choice >0)
            employee[choice-1] = null;

        employeeNum--;
        try {
            writeToFile();
            readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * adding customer to the system.Then updating save file.
     * @param cus the Customer object which will be added to system.
     * @throws IndexOutOfBoundsException when the limit of system is full
     */
    public void addCustomer(Customer cus) throws IndexOutOfBoundsException{
        if(customerNum >= customer.length)
            throw new IndexOutOfBoundsException("The limit is full.\n");
        if(checkUserInSystem(cus)){
            customer[customerNum] = new Customer(cus.getUsername(),
                    cus.getPassword(),
                    cus.getRealName(),
                    cus.getRealSurname(),
                    cus.getCustomerID());
            customer[customerNum].setCustomerID(cus.getCustomerID());
            customerNum++;
            System.out.printf("Customer Added System Successfully.CustomerID is = %d\n",cus.getCustomerID());

            try {
                writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * adding branch to the system.Then updating save file.
     * @param branch the Branch object which will be added to system.
     * @throws IndexOutOfBoundsException when the limit of system is full
     */
    public void addBranch(Branch branch) throws IndexOutOfBoundsException {
        if(branchNum >= this.branch.length)
            throw new IndexOutOfBoundsException("The limit is full.\n");
        this.branch[branchNum] = new Branch(branch.getBranchID());
        branchNum++;
        try {
            writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * removing branch from system.It is also removing corresponding employee too.
     * The branch info will receiving from screen.
     */
    public void removeBranch(){

        System.out.println("The list of branches.Please select branch number that you want to remove.");
        System.out.println("Remember that, when Branch is removing the Branch Employee are removing too.");
        for(int i=0;i<getBranchNum();++i)
            System.out.printf("%d-)BranchID %d\n",i+1,branch[i].getBranchID());

        Scanner input = new Scanner(System.in);
        String inputLine =input.nextLine();
        int inputBranch = Integer.parseInt(inputLine);
        inputBranch--;

        // removing branch
        if(inputBranch<branch.length && inputBranch >=0) {
            branch[inputBranch] = null;
            branchNum--;
        }

        // removing branch employees
        for(int i=0;i<getEmployeeNum();++i) {
            if (employee[i].getBranch() == inputBranch) {
                employee[i] = null;
                employeeNum--;
            }
        }

        try {
            writeToFile();
            readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * adding products to the system.Then updating save file.
     * @param branchID  the branchID to which the product will be added
     * @param productName the productID of product to be added
     * @param model the modelID of product to be added
     * @param color the colorID of product to be added
     * @param num the number of product to be added
     */
    public void addToBranch(int branchID,int productName,int model,int color,int num){
        try{
            branch[branchID].addStore(productName,model, color, num);
            writeToFile();
        }
        catch (IllegalStateException ise){
            System.out.println(ise);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * removing products from the system.Then updating save file.
     * @param branchID  the branchID to which the product will be removed
     * @param productName the productID of product to be removed
     * @param model the modelID of product to be removed
     * @param color the colorID of product to be removed
     * @param num the number of product to be removed
     */
    public void removeFromBranch(int branchID,int productName,int model,int color,int num){
        try{
            branch[branchID].removeStore(productName,model, color, num);
            writeToFile();
        }
        catch (IllegalStateException ise){
            ise.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * full fill the branch. Take the branch info from screen
     */
    public void fullTheBranch( ){
        System.out.println("The list of branches.Please select branch number that you want to remove.");
        System.out.println("Remember that, when Branch is removing the Branch Employee are removing too.");
        for(int i=0;i<getBranchNum();++i)
            System.out.printf("%d-)BranchID %d\n",i+1,branch[i].getBranchID());
        Scanner input = new Scanner(System.in);
        String inputLine = input.nextLine();
        int choice = Integer.parseInt(inputLine);
        branch[choice-1].fullTheStore();
        try {
            writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Branch full-filled.");
    }

    /**
     * checking that user is system or not(compare usernames)
     * @param user the user that will be comparing with other users of system
     * @return if user in system returns false, otherwise returns true
     */
    public boolean checkUserInSystem(User user){
        boolean check = true;
        if(user instanceof Administrator){
            for(int i=0;i<getAdminNum();++i)
                if(user.getUsername().equals(admin[i].getUsername()))
                    check = false;
        }
        else if(user instanceof BranchEmployee){
            for(int i=0;i<getEmployeeNum();++i)
                if(user.getUsername().equals(employee[i].getUsername()))
                    check = false;
        }
        else{
            for(int i=0;i<getCustomerNum();++i)
                if(user.getUsername().equals(customer[i].getUsername()))
                    check = false;
        }
        return check;
    }

    /**
     * if the given userName and password match with system, returns that user, else returns null.
     * @param userName typed username
     * @param password typed password
     * @return corresponding Administrator object
     */
    public Administrator signInAdmin(String userName,String password){

        Administrator rtrnAdm = null;
        for(int i=0;i<getAdminNum();++i)
            if(userName.equals(admin[i].getUsername()) && password.equals(admin[i].getPassword()))
                rtrnAdm = admin[i];

        return  rtrnAdm;
    }
    /**
     * if the given userName and password match with system, returns that user, else returns null.
     * @param userName typed username
     * @param password typed password
     * @return corresponding BranchEmployee object
     */
    public BranchEmployee signInEmployee(String userName,String password){

        BranchEmployee rtrnEmp = null;
        for(int i=0;i<getEmployeeNum();++i)
            if(userName.equals(employee[i].getUsername()) && password.equals(employee[i].getPassword()))
                rtrnEmp = employee[i];

        return  rtrnEmp;
    }
    /**
     * if the given userName and password match with system, returns that user, else returns null.
     * @param userName typed username
     * @param password typed password
     * @return corresponding Customer object
     */
    public Customer signInCustomer(String userName,String password){

        Customer rtrnCus = null;
        for(int i=0;i<getCustomerNum();++i)
            if(userName.equals(customer[i].getUsername()) && password.equals(customer[i].getPassword()))
                rtrnCus = customer[i];
        return  rtrnCus;
    }

    /**
     * editing customer previous order lis
     * @param customerID the customerID of the customer which will be editing
     * @param product the productID that will be added
     * @param model the modelID of the product that will be added
     * @param color the colorID of the product that will be added
     * @param num the number of the product that will be added
     */
    public void editCustomerPreviousOrders(int customerID,int product,int model,int color,int num){
        int customerIndex = -1;
        for(int i=0;i<customerNum;++i)
            if(customerID == customer[i].getCustomerID())
                customerIndex = i;
        if(customerIndex != -1)
            customer[customerIndex].editPrevOrders(product, model, color, num);
        else
            System.out.println("Customer can not found in the system.");
    }

    /**
     * getting productID choice from user
     * @return correct productID
     */
    public int choiceProducts(){
        Scanner input = new Scanner(System.in);
        System.out.println("1-)Office Chairs");
        System.out.println("2-)Office Desks");
        System.out.println("3-)Meeting Tables");
        System.out.println("4-)Book Cases");
        System.out.println("5-)Office Cabinets");
        System.out.printf("Choice:");
        String line = input.nextLine();
        int rtrn = Integer.parseInt(line);
        if(rtrn > 0 && rtrn <6)
            return rtrn;
        else {
            System.out.println("Select from menu!!");
            return -1;
        }
    }
    /**
     * getting modelID choice from user
     * @return correct modelID
     */
    public int choiceModels(int times){
        Scanner input = new Scanner(System.in);
        for(int i=0; i<times;i++)
            System.out.printf("%d-)Model %d\n",i+1,i+1);

        System.out.printf("Choice:");
        String line = input.nextLine();
        int rtrn = Integer.parseInt(line);
        if(rtrn > 0 && rtrn < times+2)
            return rtrn;
        else {
            System.out.println("Select from menu!!");
            return -1;
        }
    }
    /**
     * getting colorID choice from user
     * @return correct colorID
     */
    public int choiceColors(int times){
        Scanner input = new Scanner(System.in);

        System.out.println("1-)Red");
        System.out.println("2-)Orange");
        System.out.println("3-)Yellow");
        System.out.println("4-)Green");
        if(times == 5)
            System.out.println("5-)Blue");
        System.out.printf("Choice:");
        String line = input.nextLine();
        int rtrn = Integer.parseInt(line);
        if(rtrn > 0 && rtrn < 6)
            return rtrn;
        else {
            System.out.println("Select from menu!!");
            return -1;
        }
    }
    /**
     * getting quantity choice from user
     * @return correct quantity
     */
    public int numChoice(){
        Scanner input = new Scanner(System.in);
        System.out.printf("Please enter the number:");
        String line = input.nextLine();
        return Integer.parseInt(line);

    }
    /**
     * getting branchID choice from user
     * @return correct branchID
     */
    public int branchChoice(){
        Scanner input = new Scanner(System.in);
        for(int i=0;i<getBranchNum();++i)
            System.out.printf("%d-)Branch %d\n",i+1,branch[i].getBranchID());
        System.out.printf("Choice:");
        String line = input.nextLine();
        int rtrn = Integer.parseInt(line);
        if(rtrn > 0 && rtrn < getBranchNum()+1)
            return rtrn-1;
        else {
            System.out.println("Select from menu!!");
            return -1;
        }
    }

    /**
     * printing stock info of the given branch
     * @param branchID branchID of branch which will be print
     */
    public void stocksInTheBranch(int branchID){
        branch[branchID].stockInfo();
    }
    /**
     * checking that branch is need to be supplied or not
     * @param branchID branchID of branch which will be query
     * @return  if need to supplied returns true,otherwise returns false
     */
    public boolean branchSupplyNeed(int branchID){
        return branch[branchID].querySupply();
    }

    /**
     * printing all the Company data
     */
    public void printAllData(){
        System.out.printf("Admins number in system is %d\n",getAdminNum());
        for(int i=0;i<getAdminNum();++i)
            System.out.println(admin[i]);

        System.out.printf("Employees number in system is %d\n",getEmployeeNum());
        for(int i=0;i<getEmployeeNum();++i)
            System.out.println(employee[i]);
        System.out.printf("Customers number in system is %d\n",getCustomerNum());
        for(int i=0;i<getCustomerNum();++i)
            System.out.println(customer[i]);
        System.out.printf("Branches number in system is %d\n",getBranchNum());
        for(int i=0;i<getBranchNum();++i)
            System.out.println(branch[i]);

    }

    /**
     * listing products that company sells
     */
    public void listProducts(){
        System.out.println("The below list is our products.You can see more information about buy menu\n");
        System.out.println("Office Chairs 7 different model option and 5 different color for each model.");
        System.out.println("Office Desks 5 different model option and 4 different color for each model.");
        System.out.println("Meeting Tables 10 different model option and 4 different color for each model.");
        System.out.println("Book Cases 12 different model option.");
        System.out.println("Office Cabinets 12 different model option.");
    }

    /**
     * method for customer buy operation
     * @param customerID customerID of customer who makes buy operation
     */
    public void customerBuyProducts(int customerID){
        System.out.println("***Customer Buy Menu***\n");
        System.out.println("Please select the product type from menu");
        int productID = choiceProducts() - 1;

        int model;
        System.out.println("Please select the product model from menu");
        if(productID == 0) model = choiceModels(7) - 1;
        else if(productID == 1) model = choiceModels(5) - 1;
        else if(productID == 2) model = choiceModels(10) - 1;
        else model = choiceModels(12) - 1;

        int color;

        if(productID == 0){
            System.out.println("Please select the product model from menu");
            color = choiceColors(5 ) -1;
        }
        else if(productID == 1 || productID == 2){
            System.out.println("Please select the product model from menu");
            color = choiceColors(5) - 1;
        }
        else color = 0;

        System.out.println("Please select the number that you want to buy from menu");
        int num = numChoice();

        System.out.println("The quantity of the product you have chosen in the branches is as follows.");
        for (int i=0;i<getBranchNum();++i){
            if(productID == 0)
                System.out.printf("In Branch %d = %d\n",branch[i].getBranchID()+1,branch[i].getAmountChairs(model,color));
            if(productID == 1)
                System.out.printf("In Branch %d = %d\n",branch[i].getBranchID()+1,branch[i].getAmountDesks(model,color));
            if(productID == 2)
                System.out.printf("In Branch %d = %d\n",branch[i].getBranchID()+1,branch[i].getAmountTables(model,color));
            if(productID == 3)
                System.out.printf("In Branch %d = %d\n",branch[i].getBranchID()+1,branch[i].getAmountbCases(model));
            if(productID == 4)
                System.out.printf("In Branch %d = %d\n",branch[i].getBranchID()+1,branch[i].getAmountCabinets(model));
        }
        System.out.println("Please select branch that you want to buy product from above list.");
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the number:");
        String line = input.nextLine();
        int branchChoice = Integer.parseInt(line);

        if( productID == 0 ){
           if(num <= branch[branchChoice-1].getAmountChairs(model,color)) {
               editCustomerPreviousOrders(customerID, 0, model, color, num);
               removeFromBranch(branchChoice - 1, 0, model, color, num);
               getAddressAndPhone(customerID);
               System.out.println("Buying operation successfully completed.Please check the previous orders.");
           }else System.out.println("Sorry,Your operation can not completed.There is not enough product in the branch.");
        }
        if( productID == 1 ){
            if(num <= branch[branchChoice-1].getAmountDesks(model,color)) {
                editCustomerPreviousOrders(customerID, 1, model, color, num);
                removeFromBranch(branchChoice - 1, 1, model, color, num);
                getAddressAndPhone(customerID);
                System.out.println("Buying operation successfully completed.Please check the previous orders.");
            }else System.out.println("Sorry,Your operation can not completed.There is not enough product in the branch.");
        }
        if( productID == 2){
            if(num <= branch[branchChoice-1].getAmountTables(model,color)) {
                editCustomerPreviousOrders(customerID, 2, model, color, num);
                removeFromBranch(branchChoice - 1, 2, model, color, num);
                getAddressAndPhone(customerID);
                System.out.println("Buying operation successfully completed.Please check the previous orders.");
            }else System.out.println("Sorry,Your operation can not completed.There is not enough product in the branch.");
        }
        if( productID == 3 ){
            if(num <= branch[branchChoice-1].getAmountbCases(model)) {
                editCustomerPreviousOrders(customerID, 3, model, 0, num);
                removeFromBranch(branchChoice - 1, 3, model, 0, num);
                getAddressAndPhone(customerID);
                System.out.println("Buying operation successfully completed.Please check the previous orders.");
            }else System.out.println("Sorry,Your operation can not completed.There is not enough product in the branch.");
        }
        if( productID == 4 ){
            if(num <= branch[branchChoice-1].getAmountCabinets(model)) {
                editCustomerPreviousOrders(customerID, 4, model, 0, num);
                removeFromBranch(branchChoice - 1, 4, model, 0, num);
                getAddressAndPhone(customerID);
                System.out.println("Buying operation successfully completed.Please check the previous orders.");
            }else System.out.println("Sorry,Your operation can not completed.There is not enough product in the branch.");
        }
    }

    /**
     * getting address and phone info from customer when buying operation completed.
     * @param customerID customerID who buy product
     */
    public void getAddressAndPhone(int customerID){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the address");
        customer[customerID].setAddress(input.nextLine());
        System.out.println("Please enter the phone number");
        customer[customerID].setPhoneInfo(input.nextLine());
    }
}

