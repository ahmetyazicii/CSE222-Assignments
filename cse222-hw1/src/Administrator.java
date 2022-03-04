import java.util.Scanner;

public class Administrator extends SystemUser{

    /**
     * constructor with two parameter.
     * @param name name of the Administrator.
     * @param password password of the Administrator.
     */
    Administrator(String name, String password){
        super(name,password);
    }

    /**
     * adding the employee(BranchEmployee) to the system.Receiving employee information's from user(admin).
     * @param company The Company object which the employee will be added.
    */
    public void addBranchEmployee(Company company){
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter the new branch employee's username \nUsername:");
            String inputUserName = input.nextLine();
            System.out.println("Please enter the new branch employee's password \nPassword:");
            String inputPassword = input.nextLine();
            System.out.println("Please enter the new branch employee's branchID \nBranchID:");
            String strBranchID = input.nextLine();
            int branchID = Integer.parseInt(strBranchID);
            company.addEmployee(new BranchEmployee(inputUserName, inputPassword, branchID));
            System.out.println("Employee successfully added to system.");
        }
        catch (IndexOutOfBoundsException e){
            System.out.println(e);
        }
    }

    /**
     * adding branch(Branch) to the system.Receiving branchID from user(admin).
     * @param company the Company object which the branch will be added.
     */
    public void addBranches(Company company){
        try {
            System.out.println("Please enter the new branch ID\nID:");
            Scanner input = new Scanner(System.in);
            String inputLine = input.nextLine();
            int branchID = Integer.parseInt(inputLine);
            company.addBranch(new Branch(branchID));
            System.out.println("Branch successfully added to system.");
        }
        catch (IndexOutOfBoundsException e){
            System.out.println(e);
        }

    }


    /**
     * removing the employee(BranchEmployee) from the system.
     * @param company The Company object which the employee will be removed.
     */
    public void removeBranchEmployee(Company company){
        company.removeEmployee();
        System.out.println("Employee successfully removed from system.");
    }

    /**
     * removing branch(Branch) from the system.
     * @param company the Company object which the branch will be removed.
     */
    public void removeBranches(Company company){
        company.removeBranch();
        System.out.println("Branch successfully removed from system.");
    }

    /**
     * ask user(admin) to branchID then query that branch that is supply need.If yes, then print to the screen
     * @param company the Company object which the branches is stored.
     */
    public void querySupply(Company company){
        System.out.println("The list of branches.Please select branch number that you want to query supply.");
        int branchID = company.branchChoice();
        if(!company.branchSupplyNeed(branchID))
            System.out.println("There is no need to supply product.");
    }

    /**
     * operation menu for admin
     * @param cm the Company object which the admin belongs.
     */
    public void adminOperations(Company cm){
        String inputLine;
        int choice;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("\n1-)Add Branch");
            System.out.println("2-)Remove Branch");
            System.out.println("3-)Add Employee");
            System.out.println("4-)Remove Employee");
            System.out.println("5-)Query Need of Supply");
            System.out.println("6-)Print All Company Data");
            System.out.println("7-)Fill up The Branch");
            System.out.println("8-)LogOut");

            inputLine = input.nextLine();
            choice = Integer.parseInt(inputLine);
            switch (choice){
                case 1:
                    addBranches(cm);
                    break;
                case 2:
                    removeBranches(cm);
                    break;
                case 3:
                    addBranchEmployee(cm);
                    break;
                case 4:
                    removeBranchEmployee(cm);
                    break;
                case 5:
                    querySupply(cm);
                    break;
                case 6:
                    cm.printAllData();
                    break;
                case 7:
                    cm.fullTheBranch();
                    break;
                case 8:
                    System.out.println("Logged Out.\n");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }while(choice != 8);
    }

    /**
     * toString method for printing Admin name and password to the screen. The user password is hidden(with *).
     * @return the String that contains Admin info(username + password).
     */
    @Override
    public String toString() {
        String printStr;
        printStr = "UserName = " +"'"+getUsername() +"'\n";
        printStr += "Password = ";
        for (int i=0;i<getPassword().length();++i)
            printStr += "*";
        printStr += "\n";
        return printStr;
    }
}
