import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {
        try{
            Scanner input = new Scanner(System.in);
            boolean check = true;
            do{
            System.out.println("\nPlease select the operation:");
            System.out.println("1-Test Next and Prev Methods");
            System.out.println("2-Test Next method on empty Map");
            System.out.println("3-Test Prev method on empty Map");
            System.out.println("4-Test Prev method when cursor is in beginning position");
            System.out.println("5-Start Iterator from given key(key is in Map)");
            System.out.println("6-Start Iterator from given key(key is not in Map)");
            System.out.println("0-Exit");

                int choose = input.nextInt();
                switch (choose) {
                    case 1 -> testMapIterator.testNextAndPrev();
                    case 2 -> testMapIterator.testEmptyMapNext();
                    case 3 -> testMapIterator.testEmptyMapPrev();
                    case 4 -> testMapIterator.testPrevStart();
                    case 5 -> testMapIterator.testIndexConstructorY();
                    case 6 -> testMapIterator.testIndexConstructorN();
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
