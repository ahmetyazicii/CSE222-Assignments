import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class test{

    /**
     * method for printing operation menu
     */
    public static void printOperationMenu(){
        System.out.println("\nPlease select the operation:");
        System.out.println("1-Test with Small-Sized Data(10)");
        System.out.println("2-Test with Medium-Sized Data(100)");
        System.out.println("3-Test with Large-Sized Data(1000)");
        System.out.println("0-Exit");
    }

    /**
     * method for performing all operations
     * @param type The type of table( 1 for LinkedList, 2 for TreeSet, 3 for Coalesced)
     */
    public static void testMap(int type){

        Scanner input = new Scanner(System.in);
        int choose;
        boolean check = true;

        do{
            printOperationMenu();
            choose = input.nextInt();
            input.nextLine();

            switch (choose) {
                case 1 -> testKWHashTable.testAllSize(10, type);
                case 2 -> testKWHashTable.testAllSize(100, type);
                case 3 -> testKWHashTable.testAllSize(1000, type);
                case 0 -> {  // exit
                    check = false;
                    System.out.println("\n!HAVE A NICE DAY!");
                }
                default -> System.out.println("\nPlease select from menu!");
            }
        }while(check);
    }


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("\nPlease select the operation:");
        System.out.println("1-Test HashTable hashing by LinkedList");
        System.out.println("2-Test HashTable hashing by TreeSet");
        System.out.println("3-Test HashTable hashing by Coalesced technique");
        System.out.println("4-Compare Time Performances of Tables");
        System.out.println("0-Exit");


        try{
            int choose = input.nextInt();
            switch (choose) {
                case 1 -> testMap(1);
                case 2 -> testMap(2);
                case 3 -> testMap(3);
                case 4 -> testKWHashTable.comparePerformancesOfTables();
                case 0 ->  // exit
                        System.out.println("\n!HAVE A NICE DAY!");
                default -> throw new InputMismatchException("You did not select from menu.\n");
            }

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
