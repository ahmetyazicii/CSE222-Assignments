import java.io.IOException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        try {
            Scanner input = new Scanner(System.in);

            DataBase db = new DataBase();
            System.out.println("\nPlease select:");
            System.out.println("1-Driver Code");
            System.out.println("2-Interactive Menu");

            switch (Integer.parseInt(input.nextLine())) {
                case 1 -> db.driverCode();
                case 2 -> db.initialMenu();

                default -> System.out.println("You did not select from menu");
            }
        }catch (NumberFormatException nfe){
            System.out.println("You typed char instead of number.");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
