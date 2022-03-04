import java.util.Scanner;

public class Customer extends SystemUser{
    private String realName;
    private String realSurname;
    private String address;
    private String phoneInfo;
    private int customerID;
    private final String[] previousOrders;
    private int prevOrderNum;

    Customer(String username,String password,String realname,String realsurname,int customerID){
        super(username,password);
        setRealName(realname);
        setRealSurname(realsurname);
        setCustomerID(customerID);
        previousOrders = new String[10];
        prevOrderNum = 0;
    }
    Customer(String username,String password,String realname,String realsurname,int customerID,String prevOrder){
        this(username,password,realname,realsurname,customerID);
        previousOrders[prevOrderNum] = prevOrder;
        prevOrderNum++;
    }

    // setters
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public void setRealSurname(String realSurname) {
        this.realSurname = realSurname;
    }
    public void setCustomerID(int customerID) { this.customerID = customerID; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneInfo(String phoneInfo) { this.phoneInfo = phoneInfo; }

    /**
     * editing customer previous order lis
     * @param product the productID that will be added
     * @param model the modelID of the product that will be added
     * @param color the colorID of the product that will be added
     * @param num the number of the product that will be added
     */
    public void editPrevOrders(int product,int model,int color,int num){
        previousOrders[prevOrderNum] =  (product) + "," +
                                        (model) + "," +
                                        (color) + "," +
                                        (num);
        prevOrderNum++;
    }

    /**
     * printing previous Orders.
     */
    public void showPrevOrders(){
        System.out.println("Previous Order List is in below.");
        String[] tokens;

        if(prevOrderNum != 0){
            for (int i=0;i<prevOrderNum;++i){
                System.out.println("****");
                tokens = previousOrders[i].split(",", 4);
                printProduct(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));

            }
        } else System.out.println("There are not any previous order.");
    }

    /**
     * handler private method for printing previous orders
     * @param product previous order productID
     * @param model previous order modelID
     * @param color previous order colorID
     * @param num previous order number(quantity)
     */
    private void printProduct(int product,int model,int color,int num){

        System.out.printf("Product:");
        switch (product) {
            case 0:
                System.out.println("Office Chairs ");
                break;
            case 1:
                System.out.println("Office Desks ");
                break;
            case 2:
                System.out.println("Meeting Tables ");
                break;
            case 3:
                System.out.println("Book Cases ");
                break;
            case 4:
                System.out.println("Office Cabinets ");
                break;
        }
        System.out.printf("Model %d\n",model);

        if(product != 3 && product != 4) {
            System.out.printf("Color:");
            switch (color) {
                case 0:
                    System.out.println("Red ");
                    break;
                case 1:
                    System.out.println("Orange ");
                    break;
                case 2:
                    System.out.println("Yellow ");
                    break;
                case 3:
                    System.out.println("Green ");
                    break;
                case 4:
                    System.out.println("Blue ");
                    break;
            }
        }
        System.out.printf("Number %d\n",num);
    }

    //getters
    public String getRealName() { return realName; }
    public String getRealSurname() { return realSurname; }
    public String getAddress() { return address; }
    public String getPhoneInfo() { return phoneInfo; }
    public int getCustomerID() { return customerID; }
    public int getPrevOrderNum() { return prevOrderNum; }
    public String getPreviousOrders() { return previousOrders[0]; }

    /**
     * operation menu for customer
     * @param cm the Company object which the customer belongs.
     */
    public void customerOperations(Company cm){
        String inputLine;
        int choice;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("\n1-)List Products");
            System.out.println("2-)Buy Products");
            System.out.println("3-)Show Customer Info");
            System.out.println("4-)Show Previous Orders");
            System.out.println("5-)LogOut");
            inputLine = input.nextLine();
            choice = Integer.parseInt(inputLine);
            switch (choice) {
                case 1:
                    cm.listProducts();
                    break;
                case 2:
                    cm.customerBuyProducts(getCustomerID());
                    break;
                case 3:
                    System.out.println(this);
                    break;
                case 4:
                    showPrevOrders();
                    break;
                case 5:
                    System.out.println("Logged Out.");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }while(choice != 5);

    }

    @Override
    public String toString() {
        String printStr;
        printStr = "UserName = " +"'"+getUsername() +"'\n";
        printStr += "Password = ";
        for (int i=0;i<getPassword().length();++i)
            printStr +="*";
        printStr += "\n";
        printStr += "RealName = " +"'"+getRealName() +"'\n";
        printStr += "RealSurName = " +"'"+getRealSurname() +"'\n";
        printStr += "CustomerID = " + getCustomerID() + '\n';
        return printStr;
    }
}
