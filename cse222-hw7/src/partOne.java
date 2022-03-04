import java.util.Iterator;
import java.util.NoSuchElementException;

public class partOne {

    /**
     * Driver function of NavigableSet interface using skip-list
     */
    public static void skipListImplementation(){

        NavigableSetSL<Integer> ns = new NavigableSetSLImp<>();

        System.out.println("-----------------------------------------");
        System.out.println("Testing descendingIterator() method on empty set.");
        try{
            Iterator<Integer> it = ns.descendingIterator();
            System.out.println(it.next());
        }catch (NoSuchElementException nse){
            System.out.println("Exception found-The set is empty");
            System.out.println("The exception printStackTree is not printing.");
        }



        System.out.println("-----------------------------------------");
        System.out.println("Testing insert() method.");
        System.out.println("Inserting following items: 6-6-1-3-4-2-5-(-7)");
        ns.insert(6);
        ns.insert(6);
        ns.insert(1);
        ns.insert(3);
        ns.insert(4);
        ns.insert(2);
        ns.insert(5);
        ns.insert(-7);
        System.out.println("The set:");
        System.out.println(ns);
        System.out.println("-----------------------------------------");
        System.out.println("Testing delete() method.");
        System.out.println("Deleting following items: 5(in set), 87(not in set)");
        ns.delete(5);
        ns.delete(87);
        System.out.println("The set:");
        System.out.println(ns);

        System.out.println("-----------------------------------------");
        System.out.println("Testing descendingIterator() method.");
        System.out.println("Printing out the entire set:");
        System.out.println("Printing set with toString() method");
        System.out.println(ns);
        System.out.println("Printing set with descendingIterator() method");
        Iterator<Integer> it = ns.descendingIterator();

        while (it.hasNext()) {
            Integer item = it.next();
            System.out.println(item);
        }
    }

    /**
     * Driver function of NavigableSet interface using Red-Black Tree
     */
    public static void AVLTreeImplementation() {

        NavigableSetAVL<Integer> ns = new NavigableSetAVLImp<>();

        System.out.println("-----------------------------------------");
        System.out.println("Testing iterator() method on empty set.");
        try{
            Iterator<Integer> it = ns.iterator();
            System.out.println(it.next());
        }catch (NoSuchElementException nse){
            System.out.println("Exception found-The set is empty");
            System.out.println("The exception printStackTree is not printing.");
        }

        System.out.println("-----------------------------------------");
        System.out.println("Testing insert() method.");
        System.out.println("Inserting following items: 6-6-1-3-4-2-5-(-7)");
        ns.insert(6);
        ns.insert(6);
        ns.insert(1);
        ns.insert(3);
        ns.insert(4);
        ns.insert(2);
        ns.insert(5);
        ns.insert(-7);
        System.out.println("The set:");
        System.out.println(ns);


        System.out.println("-----------------------------------------");
        System.out.println("Testing iterator() method.");
        System.out.println("Printing out the entire set:");
        System.out.println("Printing set with toString() method");
        System.out.println(ns);
        System.out.println("Printing set with iterator() method");
        Iterator<Integer> iterator = ns.iterator();

        while (iterator.hasNext()) {
            Integer item = iterator.next();
            System.out.println(item);
        }


        System.out.println("-----------------------------------------");
        System.out.println("Testing tailSet() method.");
        System.out.println("toElement is 4 , inclusive is true");
        NavigableSetAVL<Integer> tst = ns.tailSet(4, true);
        System.out.println(tst);
        System.out.println("toElement is 4 , inclusive is false");
        NavigableSetAVL<Integer> tsf = ns.tailSet(4, false);
        System.out.println(tsf);

        System.out.println("-----------------------------------------");
        System.out.println("Testing headSet() method.");
        System.out.println("toElement is 4 , inclusive is true");
        NavigableSetAVL<Integer> hst = ns.headSet(4, true);
        System.out.println(hst);
        System.out.println("toElement is 4 , inclusive is false");
        NavigableSetAVL<Integer> hsf = ns.headSet(4, false);
        System.out.println(hsf);

    }
}
