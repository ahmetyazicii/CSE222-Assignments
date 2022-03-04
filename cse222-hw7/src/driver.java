import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class driver {


    public static void main(String[] args) {
        try{
            Scanner input = new Scanner(System.in);
            boolean check = true;
            System.out.println("Welcome to the Driver Function");
            do{
                System.out.println("\nPlease select the operation:");
                System.out.println("1-Test Part One--SkipList Implementation");
                System.out.println("2-Test Part One--AVL Tree Implementation");
                System.out.println("3-Test Part Two");
                System.out.println("4-Test Part Three");

                System.out.println("0-Exit");

                int choose = input.nextInt();
                switch (choose) {
                    case 1 -> partOne.skipListImplementation();
                    case 2 -> partOne.AVLTreeImplementation();
                    case 3 -> partTwo.testTreeTypeMethod();
                    case 4 -> partThree.compareInsertionPerformances();
                    case 0 -> check = false;

                    default -> throw new InputMismatchException("You did not select from menu.\n");
                }
            }while(check);
            System.out.println("\n!HAVE A NICE DAY!");

        }
        catch(InputMismatchException a){
            System.out.println("The exception is found.Program terminated.");
            a.printStackTrace();
        }
        catch (NoSuchElementException e){
            System.out.println("The exception is found.Program terminated.");
        }
    }

}
