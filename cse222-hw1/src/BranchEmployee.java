import java.util.Scanner;

public class BranchEmployee extends SystemUser{

    private final int branch;

    BranchEmployee(String name, String password,int branch){
        super(name,password);
        this.branch = branch;
    }

    /**
     * getters for branch value
     * @return returning branch value
     */
    public int getBranch() { return branch; }

    /**
     * removing product from database(or company)
     * @param cm the Company object which the products are stored.
     * @param productID productID of product which will be removed.
     * @param model  model of product which will be removed.
     * @param color  color of product which will be removed.
     * @param num remove num.
     */
    public void removeProduct(Company cm, int productID, int model, int color, int num){
        cm.removeFromBranch(getBranch(),productID,model,color,num);
        System.out.println("Product successfully removed from store");
    }

    /**
     * adding product to database(or company).Receiving product details from user(employee).
     * @param cm the Company object which the products are stored.
     */
    public void addProduct(Company cm){
        System.out.println("Please select product from menu to add branch store");
        System.out.println("Remember that each employee can add products to their branches.");
        int productID = cm.choiceProducts() - 1;
        int model;

        if(productID == 0)
            model = cm.choiceModels(7) - 1;
        else if(productID == 1)
            model = cm.choiceModels(5) - 1;
        else if(productID == 2)
            model = cm.choiceModels(10) - 1;
        else
            model = cm.choiceModels(12) - 1;

        int color;

        if(productID == 0)
            color = cm.choiceColors(5 ) -1;
        else if(productID == 1 || productID == 2)
            color = cm.choiceColors(5) - 1;
        else
            color = 0;
        int num = cm.numChoice();
        cm.addToBranch(getBranch(),productID,model,color,num);
    }


    /**
     * making sell from store. first getting customerID then getting product info which will be removed. Then adding
     * product info to customers previous orders.Then removing product from branch.
     * @param cm the Company object which the customer and products are stored.
     */
    public void makeSalesFromStore(Company cm){
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the customer ID.");
        String line = input.nextLine();
        int saleCustomerID = Integer.parseInt(line);

        int saleProduct;
        int saleModel;
        int saleColor;

        saleProduct = cm.choiceProducts() - 1;

        if(saleProduct == 0)
            saleModel = cm.choiceModels(7) - 1;
        else if(saleProduct == 1)
            saleModel = cm.choiceModels(5) - 1;
        else if(saleProduct == 2)
            saleModel = cm.choiceModels(10) - 1;
        else
            saleModel = cm.choiceModels(12) - 1;


        if(saleProduct == 0)
            saleColor = cm.choiceColors(5) - 1;
        else if(saleProduct == 1 || saleProduct == 2)
            saleColor = cm.choiceColors(4) - 1;
        else
            saleColor = 0;
        int saleNum = cm.numChoice();

        cm.editCustomerPreviousOrders(saleCustomerID,saleProduct,saleModel,saleColor,saleNum);
        removeProduct(cm,saleProduct,saleModel,saleColor,saleNum);

    }

    /**
     * inquiring the products in the branch.
     * @param cm the Company object which the branches and products are stored.
     */
    public void inquireStore(Company cm){
        cm.stocksInTheBranch(getBranch());
    }

    /**
     * informing manager when there are products to need to be supplied
     * @param cm the Company object which the products are stored.
     */
    public void informManager(Company cm){
        if(cm.branchSupplyNeed(getBranch()))
            System.out.println("Manager informed about above list.");
        else
            System.out.println("There is no need to supply product.");
    }

    /**
     * operation menu for employee
     * @param cm the Company object which the employee belongs.
     */
    public void employeeOperations(Company cm){
        String inputLine;
        int choice;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Remember that each employee only manage their branches.");
            System.out.printf("Your branchID is = %d\n",getBranch());
            System.out.println("\n1-)Inquire About Stock");
            System.out.println("2-)Inform Manager About Stock");
            System.out.println("3-)Add Products");
            System.out.println("4-)Make Sales");
            System.out.println("5-)Print Employee Info");
            System.out.println("6-)LogOut");

            inputLine = input.nextLine();
            choice = Integer.parseInt(inputLine);
            switch (choice) {
                case 1:
                    inquireStore(cm);
                    break;
                case 2:
                    informManager(cm);
                    break;
                case 3:
                    addProduct(cm);
                    break;
                case 4:
                    makeSalesFromStore(cm);
                    break;
                case 5:
                    System.out.println(this);
                    break;
                case 6:
                    System.out.println("Logged Out.\n");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }while(choice != 6);
    }

    @Override
    public String toString() {
        String printStr;
        printStr = "UserName = " +"'"+getUsername() +"'\n";
        printStr += "Password = ";
        for (int i=0;i<getPassword().length();++i)
            printStr +="*";
        printStr += "\n";
        printStr += "BranchID = " + getBranch() + '\n';
        return printStr;
    }
}
