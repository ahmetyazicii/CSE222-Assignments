import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
public class testBSTHeapTree{

    // method for printing operation menu
    public static void printOperationMenu(){
        System.out.println("\nPlease select the operation:");
        System.out.println("0-Insert an element to the BST(as in report)");
        System.out.println("1-Remove item from BST");
        System.out.println("2-Find the number of occurrences");
        System.out.println("3-Find the mode");
        System.out.println("4-Fill the new BSTHeapTree with 3000 random numbers ");
        System.out.println("5-Display the BSTHeapTree");
        System.out.println("-1-Exit");
    }


    // method for performing all operations
    public static void test(){

        Scanner input = new Scanner(System.in);
        int choose;

        boolean check = true;

        BSTHeapTree<Integer> tree = new BSTHeapTree<>();


        do{
            printOperationMenu();
            choose = input.nextInt();
            input.nextLine();

            switch(choose ){
                case 0:
                    System.out.println("The Tree is filling with following numbers:");
                    System.out.println("“37-23-16-19-10-10-9-9-93-31-15-29-13-13-13-13-124-52-98-51-51-51-38-87-80-80-43-43-43-60-57-54-39-39'");
                    tree.add(37);
                    tree.add(23);
                    tree.add(16);
                    tree.add(19);
                    tree.add(10);
                    tree.add(10);
                    tree.add(9);
                    tree.add(9);
                    tree.add(9);
                    tree.add(3);
                    tree.add(31);
                    tree.add(15);
                    tree.add(13);
                    tree.add(29);
                    tree.add(13);
                    tree.add(13);
                    tree.add(124);
                    tree.add(52);
                    tree.add(98);
                    tree.add(51);
                    tree.add(51);
                    tree.add(51);
                    tree.add(38);
                    tree.add(87);
                    tree.add(80);
                    tree.add(43);
                    tree.add(43);
                    tree.add(43);
                    tree.add(60);
                    tree.add(57);
                    tree.add(54);
                    tree.add(39);
                    tree.add(39);
                    System.out.println("The number 80 is inserting to the Tree.The number of occurrences of the item after insertion is " + tree.add(80));
                    System.out.println("The number 13 is inserting to the Tree.The number of occurrences of the item after insertion is " + tree.add(13));
                    System.out.println("\nThe Tree is:");
                    System.out.println(tree);

                    break;

                case 1:
                    System.out.println("\nThe Tree is BEFORE remove:");
                    System.out.println(tree);
                    System.out.println("The number 51 is removing from the Tree.The number of occurrences of the item after removal is " + tree.remove(51));
                    System.out.println("The number 1 is removing from the Tree.The number of occurrences of the item after removal is " + tree.remove(1));
                    System.out.println("The number 38 is removing from the Tree.The number of occurrences of the item after removal is " + tree.remove(38));
                    System.out.println("\nThe Tree is AFTER remove:");
                    System.out.println(tree);
                    break;
                case 2:
                    System.out.println("The Tree is in below");
                    System.out.println(tree);
                    System.out.println("Finding occurrence of 51 ");
                    System.out.println("The occurrence of 51 is:" + tree.find(51));
                    System.out.println("Finding occurrence of 124 ");
                    System.out.println("The occurrence of 124 is:" + tree.find(124));
                    System.out.println("Finding occurrence of 1 ");
                    System.out.println("The occurrence of 1 is:" + tree.find(1));
                    break;
                case 3:
                    System.out.println("The Tree is in below");
                    System.out.println(tree);
                    System.out.print("The mode of Tree is:");
                    tree.find_mode();
                    System.out.println();
                    break;
                case 4:
                    Random rand = new Random();
                    BSTHeapTree<Integer> tree2 = new BSTHeapTree<>();

                    for(int i=0;i<3000;++i){
                        tree2.add(rand.nextInt(5000));
                    }
                    System.out.println(tree2);
                    break;
                case 5:
                    System.out.println(tree);
                    break;

                case -1:  // exit
                    check = false;
                    System.out.println("\n!HAVE A NICE DAY!");
                    break;
                default:
                    System.out.println("\nPlease select from menu!");
                    break;
            }
        }while(check);
    }

    public static void main(String[] args) {

        System.out.println("Welcome to the testBSTHeapTree program.");

        test();
    }
}
