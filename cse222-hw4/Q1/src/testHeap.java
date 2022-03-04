import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
public class testHeap{

	// method for printing operation menu
	public static void printOperationMenu(){
		System.out.println("\nPlease select the operation:");
		System.out.println("0-Fill the Heap(as in report)");
		System.out.println("1-Search for an element");
		System.out.println("2-Merge with another heap");
		System.out.println("3-Removing ith largest element from Heap");
		System.out.println("4-Set Method of Iterator");
		System.out.println("5-Remove from Heap");
		System.out.println("6-Fill the Heap(manually)");
		System.out.println("7-Display the Heap");
		System.out.println("-1-Exit");
	}


	// method for performing all operations
	public static void test(){

		Scanner input = new Scanner(System.in);
		int choose;

		boolean check = true;

		Heap<Integer> heap = new Heap<>();
		Heap<Integer> heap2 = new Heap<>();

		heap2.insert(14);
		heap2.insert(240);
		heap2.insert(14);
		heap2.insert(74);



		do{
			printOperationMenu();
			choose = input.nextInt();
			input.nextLine();
			
			switch(choose ){
				case 0:
					System.out.println("The Heap is filling with following numbers:");
					System.out.println("'86-78-4-5-7-8-8-9-754'");
					heap.insert(86);
					heap.insert(78);
					heap.insert(4);
					heap.insert(5);
					heap.insert(7);
					heap.insert(8);
					heap.insert(8);
					heap.insert(9);
					heap.insert(754);
					heap.show();
					break;

				case 1:
					System.out.println("The Heap is in below");
					heap.show();
					System.out.println("Searching 3 item:");
					System.out.print("Searching 754- ");
					if(heap.search(754))
						System.out.print("Item is in Heap.\n");
					else
						System.out.print("Item is not in Heap.\n");
					System.out.print("Searching 9- ");
					if(heap.search(9))
						System.out.print("Item is in Heap.\n");
					else
						System.out.print("Item is not in Heap.\n");
					System.out.print("Searching 44- ");
					if(heap.search(44))
						System.out.print("Item is in Heap.\n");
					else
						System.out.print("Item is not in Heap.\n");

					break;
				case 2:
					System.out.println("The Heap1 is in below");
					heap.show();
					System.out.println("The Heap2 is in below");
					heap2.show();
					System.out.println("The Merged Heap is in below");
					heap.merge(heap2);
					heap.show();

					break;
				case 3:
					System.out.println("The Heap is in below");
					heap.show();
					System.out.println("Removing 3 item");
					System.out.println("Removing first largest item");
					heap.removeILargest(1);
					heap.show();
					System.out.println("Removing size/2th largest item");
					heap.removeILargest(heap.size()/2);
					heap.show();
					System.out.println("Removing sizeth largest item");
					heap.removeILargest(heap.size());
					heap.show();
					break;
				case 4:
					try {
						HeapIterator<Integer> iterator = heap.iterator();

						System.out.println("The Heap is in below");
						heap.show();
						System.out.println("Setting 1 after first iteration");
						System.out.println("Last Iterated Element :" + iterator.next());
						iterator.set(1);
						heap.show();
						System.out.println("Last Iterated Element :" + iterator.next());
						System.out.println("Last Iterated Element :" + iterator.next());
						System.out.println("Setting 2 after third iteration");
						iterator.set(2);
						heap.show();
					}
					catch (NoSuchElementException nexc){
						System.out.println(nexc +"!EXCEPTION!-No element found.\n");
					}

					break;

				case 5:
					System.out.println("BEFORE remove:");
					heap.show();
					heap.remove();
					System.out.println("AFTER remove:");
					heap.show();

					break;
				case 6:
					addInteger(heap);
					heap.show();
					break;
				case 7:
					heap.show();
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

	@SuppressWarnings("unchecked")
	public static void addInteger(Heap c){
		String inputline = null;
		int inputint;
		Scanner input = new Scanner(System.in);

		System.out.println("Please fill the heap with integer numbers one by one(type HALT for halt)");
		do{
			try{
				inputline= input.nextLine();

				inputint = Integer.parseInt(inputline);

				c.insert(inputint);
			}
			catch (NumberFormatException nexc){
				System.out.println("Please type integer numbers.\n");
			}
		}while(!Objects.equals(inputline, "HALT"));
	}

	public static void main(String[] args) {

		System.out.println("Welcome to the testHeap program.Remember that the tested Heap is maxHeap.");

		test();
	}
}
