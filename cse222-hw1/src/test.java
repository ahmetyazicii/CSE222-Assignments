import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {

    public static void main(String[] args){

       try {
            String inputLine;
            int choice;
            Scanner input = new Scanner(System.in);
            Company cm = new Company();
            System.out.println("*********Welcome To The Automation System*********\n");
            do {
                initialMenu();
                System.out.printf("Choice: ");
                inputLine = input.nextLine();
                choice = Integer.parseInt(inputLine);

                switch (choice) {
                    case 1:
                        LogInAsAdmin(cm);
                        break;
                    case 2:
                        LogInAsEmployee(cm);
                        break;
                    case 3:
                        LogInAsCustomer(cm);
                        break;
                    case 4:
                        System.out.println("Thanks for using Automation System.");
                        break;
                    default:
                        System.out.println("Please Select From Menu\n");
                        break;
                }
            }while(choice != 4);

       }
       catch (FileNotFoundException fntfe){
            System.out.println(fntfe);
       }
    }

    public static void initialMenu(){
        System.out.println("Please select from menu:");
        System.out.println("1-)Login as Administrator");
        System.out.println("2-)Login as BranchEmployee");
        System.out.println("3-)Login as Customer");
        System.out.println("4-Exit");
    }

    public static String getInputUsername(){
        System.out.printf("UserName:");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
    public static String getInputPassword(){
        System.out.printf("Password:");
        Scanner input = new Scanner(System.in);
        return input.nextLine();

    }
    public static String getRealName(){
        System.out.printf("RealName:");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
    public static String getRealSurName(){
        System.out.printf("RealSurName:");
        Scanner input = new Scanner(System.in);
        return input.nextLine();

    }

    public static void LogInAsAdmin(Company cm){
        System.out.println("\n***Admin Login Interface***\n");

        System.out.println("Please enter the Username");
        String inputUsername = getInputUsername();
        String inputPassword = getInputPassword();
        Administrator admin = cm.signInAdmin(inputUsername,inputPassword);

        if(admin != null){
            admin.adminOperations(cm);
        }
        else System.out.println("\nUsername or password is wrong.\n");

    }
    public static void LogInAsEmployee(Company cm){
        System.out.println("\n***Employee Login Interface***\n");
        System.out.println("Please enter the Username");
        String inputUsername = getInputUsername();
        String inputPassword = getInputPassword();
        BranchEmployee employee = cm.signInEmployee(inputUsername,inputPassword);

        if(employee != null) {
            employee.employeeOperations(cm);
        } else System.out.println("\nUsername or password is wrong.\n");
    }
    public static void LogInAsCustomer(Company cm){
        System.out.println("\n***Customer Login Interface***\n");

        System.out.println("1-)LogIn");
        System.out.println("2-)SignIn");
        Scanner input = new Scanner(System.in);
        String inputLine = input.nextLine();
        int choice = Integer.parseInt(inputLine);

        if(choice == 1){
            System.out.println("Please enter the Username or E-Mail");
            String inputUsername = getInputUsername();
            String inputPassword = getInputPassword();

            Customer customer = cm.signInCustomer(inputUsername,inputPassword);
            if(customer != null){
                customer.customerOperations(cm);
            }
            else System.out.println("\nUsername or password is wrong.\n");
        }
        if(choice == 2){
            String inputUsername = getInputUsername();
            String inputPassword = getInputPassword();
            String inputRealName = getRealName();
            String inputRealSurName = getRealSurName();

            cm.addCustomer(new Customer(inputUsername,inputPassword,inputRealName,inputRealSurName,cm.createCustomerID()));


            LogInAsCustomer(cm);
        }

    }

}
