public class Branch {

    private  int branchID;
    private final OfficeChairs chairs;
    private final OfficeDesks desks;
    private final MeetingTables tables;
    private final BookCases bCases;
    private final OfficeCabinets cabinets;

    /**
     * constructor for Branch Class
     * @param bNum branchID
     */
    Branch(int bNum){
       setBranchID(bNum);
        chairs = new OfficeChairs();
        desks = new OfficeDesks();
        tables = new MeetingTables();
        bCases = new BookCases();
        cabinets = new OfficeCabinets();
    }

    // setters
    public void setBranchID(int branchID) { this.branchID = branchID; }

    /**
     * adding products to the branch.
     * @param productName the productID of product to be added
     * @param model the modelID of product to be added
     * @param color the colorID of product to be added
     * @param num the number of product to be added
     * @throws IllegalStateException throw exception when input un excepted productID found
     */
    public void addStore(int productName, int model, int color, int num)throws IllegalStateException{

        switch (productName) {
            case 0:
                chairs.addElement(model, color, num);
                break;
            case 1:
                desks.addElement(model, color, num);
                break;
            case 2:
                tables.addElement(model, color, num);
                break;
            case 3:
                bCases.addElement(model, 0, num);
                break;
            case 4:
                cabinets.addElement(model, 0, num);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + productName);
        }
    }
    /**
     * removing products from the branch.
     * @param productName the productID of product to be removed
     * @param model the modelID of product to be removed
     * @param color the colorID of product to be removed
     * @param num the number of product to be removed
     * @throws IllegalStateException throw exception when input un excepted productID found
     */
    public void removeStore(int productName,int model,int color,int num)throws IllegalStateException{
        switch (productName) {
            case 0:
                chairs.removeElement(model, color, num);
                break;
            case 1:
                desks.removeElement(model, color, num);
                break;
            case 2:
                tables.removeElement(model, color, num);
                break;
            case 3:
                bCases.removeElement(model, 0, num);
                break;
            case 4:
                cabinets.removeElement(model, 0, num);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + productName);
        }
    }

    /**
     * adding max amount of the products to the store
     */
    public void fullTheStore(){
        chairs.fullStore();
        desks.fullStore();
        tables.fullStore();
        bCases.fullStore();
        cabinets.fullStore();
    }


    // getters
    public int getBranchID() {
        return branchID;
    }
    public int getChairsModelNum(){ return chairs.getModelNum();}
    public int getChairsColorNum(){ return chairs.getColorNum();}
    public int getDesksModelNum(){ return desks.getModelNum();}
    public int getDesksColorNum(){ return desks.getColorNum();}
    public int getTablesModelNum(){ return tables.getModelNum();}
    public int getTablesColorNum(){ return tables.getColorNum();}
    public int getbCasesModelNum(){ return bCases.getModelNum();}
    public int getCabinetsModelNum(){ return cabinets.getModelNum();}

    // getters for amounts of products
    public int getAmountChairs(int model,int color){
        return chairs.getAmount(model, color);
    }
    public int getAmountDesks(int model,int color){
        return desks.getAmount(model, color);
    }
    public int getAmountTables(int model,int color){
        return tables.getAmount(model, color);
    }
    public int getAmountbCases(int model){
        return bCases.getAmount(model, 0);
    }
    public int getAmountCabinets(int model){
        return cabinets.getAmount(model, 0);
    }

    /**
     * prints the office chairs.
     */
    public void printChairs(){ System.out.println(chairs);}
    /**
     * prints the office desks.
     */
    public void printDesks(){ System.out.println(desks);}
    /**
     * prints the meeting tables.
     */
    public void printTables(){ System.out.println(tables);}
    /**
     * prints the book cases.
     */
    public void printbCases(){ System.out.println(bCases);}
    /**
     * prints the office cabinets.
     */
    public void printCabinets(){ System.out.println(cabinets);}

    /**
     * checking that branch need any supply
     * @return if supply is needed returns true, otherwise returns false
     */
    public boolean querySupply(){
        boolean check = false;
        if(chairs.areNeedSupply())
            check = true;
        if(desks.areNeedSupply())
            check = true;
        if(tables.areNeedSupply())
            check = true;
        if(bCases.areNeedSupply())
            check = true;
        if(cabinets.areNeedSupply())
            check = true;
        return check;
    }

    /**
     * printing stock info of branch
     */
    public void stockInfo( ){
        System.out.println(chairs);
        System.out.println(desks);
        System.out.println(tables);
        System.out.println(bCases);
        System.out.println(cabinets);
    }

    @Override
    public String toString() {
        System.out.printf("BranchID = %d\n",getBranchID());
        System.out.println("The products in branch is:");
        printChairs();
        printDesks();
        printTables();
        printbCases();
        printCabinets();
        return "";
    }
}
