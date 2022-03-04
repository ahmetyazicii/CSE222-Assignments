import java.util.NoSuchElementException;

public class testMapIterator {

    public static void testNextAndPrev() {
        try {
            AYHashMap<Integer, Integer> map = new AYHashMap<>();

            System.out.println("The following items inserting into the map(the keys are also the values) :");
            System.out.println("10-88-79-1-6-0-7-8-745-659-8426-974");
            map.put(10, 10);
            map.put(88, 88);
            map.put(79, 79);
            map.put(1, 1);
            map.put(6, 6);
            map.put(0, 0);
            map.put(7, 7);
            map.put(8, 8);
            map.put(745, 745);
            map.put(659, 88);
            map.put(8426, 8426);
            map.put(974, 974);

            System.out.println("The table is " + map);
            System.out.println("----------------------------------------------");
            System.out.println("Testing next() and hasNext() methods.");
            MapIteratorInterface<Integer> iterator = map.mapIterator();
            System.out.println("Printing keys with iterator");
            while (iterator.hasNext()) {
                System.out.print("The Next Key is: " + iterator.next() + "\n");
            }
            System.out.println("----------------------------------------------");
            System.out.println("Testing prev() method.");
            System.out.println("The previous Key is: " + iterator.prev());
            System.out.println("The previous Key is: " + iterator.prev());
            System.out.println("The previous Key is: " + iterator.prev());
            System.out.println("The previous Key is: " + iterator.prev());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("Exception is found.The details of exception printed");
            throw e;
        }
    }

    public static void testEmptyMapNext() {

        try {
            AYHashMap<Integer, Integer> map = new AYHashMap<>();
            MapIteratorInterface<Integer> iterator = map.mapIterator();
            System.out.print("The Next Key is: " + iterator.next() + "\n");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("Exception is found.The details of exception printed");
            throw e;
        }

    }

    public static void testEmptyMapPrev() {

        try {
            AYHashMap<Integer, Integer> map = new AYHashMap<>();
            MapIteratorInterface<Integer> iterator = map.mapIterator();
            System.out.print("The Previous Key is: " + iterator.prev() + "\n");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("Exception is found.The details of exception printed");
            throw e;
        }

    }

    public static void testPrevStart() {
        try {
            AYHashMap<Integer, Integer> map = new AYHashMap<>();
            System.out.println("The following items inserting into the map(the keys are also the values) :");
            System.out.println("10-88-79-1-6-0-7-8-745-659-8426-974");
            map.put(10, 10);
            map.put(88, 88);
            map.put(79, 79);
            map.put(1, 1);
            map.put(6, 6);
            map.put(0, 0);
            map.put(7, 7);
            map.put(8, 8);
            map.put(745, 745);
            map.put(659, 88);
            map.put(8426, 8426);
            map.put(974, 974);

            System.out.println("The table is " + map);
            System.out.println("----------------------------------------------");
            System.out.println("Testing prev() method when iterator cursor is starting position");
            MapIteratorInterface<Integer> iterator = map.mapIterator();
            System.out.println("The previous Key is: " + iterator.prev());
            System.out.println("The previous Key is: " + iterator.prev());
            System.out.println("The previous Key is: " + iterator.prev());
            System.out.println("The next Key is: " + iterator.next());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("Exception is found.The details of exception printed");
            throw e;
        }
    }

    public static void testIndexConstructorY() {

        try {
            AYHashMap<Integer, Integer> map = new AYHashMap<>();

            System.out.println("The following items inserting into the map(the keys are also the values) :");
            System.out.println("10-88-79-1-6-0-7-8-745-659-8426-974");
            map.put(10, 10);
            map.put(88, 88);
            map.put(79, 79);
            map.put(1, 1);
            map.put(6, 6);
            map.put(0, 0);
            map.put(7, 7);
            map.put(8, 8);
            map.put(745, 745);
            map.put(659, 88);
            map.put(8426, 8426);
            map.put(974, 974);

            System.out.println("The table is " + map);
            System.out.println("----------------------------------------------");
            System.out.println("Testing parameterized constructor.");
            MapIteratorInterface<Integer> iterator = map.mapIterator(7);
            System.out.println("Printing keys with iterator, starting key 7");
            while (iterator.hasNext()) {
                System.out.print("The Next Key is: " + iterator.next() + "\n");
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("Exception is found.The details of exception printed");
            throw e;
        }
    }

    public static void testIndexConstructorN() {
        try {
            AYHashMap<Integer, Integer> map = new AYHashMap<>();

            System.out.println("The following items inserting into the map(the keys are also the values) :");
            System.out.println("10-88-79-1-6-0-7-8-745-659-8426-974");
            map.put(10, 10);
            map.put(88, 88);
            map.put(79, 79);
            map.put(1, 1);
            map.put(6, 6);
            map.put(0, 0);
            map.put(7, 7);
            map.put(8, 8);
            map.put(745, 745);
            map.put(659, 88);
            map.put(8426, 8426);
            map.put(974, 974);

            System.out.println("The table is " + map);
            System.out.println("----------------------------------------------");
            System.out.println("Testing parameterized constructor.");
            MapIteratorInterface<Integer> iterator = map.mapIterator(-1);
            System.out.println("Printing keys with iterator, starting key -1(the given key is not in map)");
            while (iterator.hasNext()) {
                System.out.print("The Next Key is: " + iterator.next() + "\n");
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("Exception is found.The details of exception printed");
            throw e;
        }
    }
}
