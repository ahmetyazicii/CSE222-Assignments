import java.util.ArrayList;
import java.util.NoSuchElementException;

public class testKWHashTable {

   /**
    * Testing tables all methods
    * @param size The size of items that will be inserted to the table.
    * @param type The type of table( 1 for LinkedList, 2 for TreeSet, 3 for Coalesced)
    */
   public static void testAllSize(int size,int type){

      try {
         int testItem = 123456;

         KWHashMap<Integer, Integer> map;
         if (type == 1)
            map = new HashTableLL<>(size);
         else if (type == 2)
            map = new HashTableTS<>(size);
         else
            map = new HashTableCoalesced<>(size);


         System.out.print("The condition of Map :");
         if (map.isEmpty())
            System.out.print(" Map is empty\n");
         else
            System.out.print(" Map is not empty\n");
         System.out.println("TEST-PUT-METHOD");
         System.out.println("------------------------------------------------------------------");

         map.put(testItem, testItem);
         for (int i = 0; i < size - 1; ++i) {
            Integer nextInt = (int) (i * 87 * Math.random());
            map.put(nextInt, nextInt);
         }

         System.out.println(size + " element inserted to the map.Now the map is printing...");
         System.out.print("The condition of Map :");
         if (map.isEmpty())
            System.out.print(" Map is empty\n");
         else
            System.out.print(" Map is not empty\n");
         System.out.println();
         System.out.println(map);
         System.out.println("TEST-GET-METHOD");
         System.out.println("------------------------------------------------------------------");
         System.out.println("Now Testing Get Method(The " + testItem + "  is always in map)");

         System.out.println();
         System.out.println("GET-Key: " + testItem + " Value: " + map.get(testItem));
         System.out.println("GET-Key: 50" + " Value: " + map.get(50));
         System.out.println("GET-Key: 150" + " Value: " + map.get(150));
         System.out.println("GET-Key: 600" + " Value: " + map.get(600));
         System.out.println("GET-Key: -1" + " Value: " + map.get(-1));


         System.out.println("\nTEST-PUT(SET)-METHOD");
         System.out.println("------------------------------------------------------------------");
         System.out.println("Now Testing Put Method(The " + testItem + "  is always in map)");
         System.out.println("PUT-Key: " + testItem + " New Value: -" + testItem + " OldValue: " + map.put(testItem, -testItem));


         System.out.println("\nTEST-REMOVE-METHOD");
         System.out.println("------------------------------------------------------------------");
         System.out.println("Now Testing Remove Method(The " + testItem + "  is in map)");
         System.out.println("Map BEFORE REMOVE operation\n");
         System.out.println(map);
         System.out.println("REMOVE-Key: " + testItem + " OldValue: " + map.remove(testItem));
         System.out.println();
         System.out.println("Map AFTER REMOVE operation\n");
         System.out.println(map);


         System.out.println("Map BEFORE REMOVE operation\n");
         System.out.println(map);
         System.out.println("REMOVE-Key: " + -1 + " OldValue: " + map.remove(testItem));
         System.out.println();
         System.out.println("Map AFTER REMOVE operation\n");
         System.out.println(map);
         System.out.println("------------------------------------------------------------------");

      }
      catch (NoSuchElementException e){
         e.printStackTrace();
         System.out.println("Exception is found.The details of exception printed");
         throw e;
      }
   }


   /**
    * Comparing all the methods of three tables in terms of time performance.
    */
   public static void comparePerformancesOfTables(){

      try {
         long startTime;
         long stopTime;
         double mapLLPut, mapLLGet, mapLLRemove;
         double mapTSPut, mapTSGet, mapTSRemove;
         double mapCPut, mapCGet, mapCRemove;


         System.out.println("*The three different HashTables will be comparing each other in terms of their performances");
         System.out.println("*All three table will be inserted with same items with same amount.");
         System.out.println("*The inserted items same randoms number , total 70 items");
         System.out.println("*There is one plus item for each table for get and remove operations,since the items are random, need specific item");
         System.out.println("*The initial capacities of each table is 25");

         HashTableLL<Integer, Integer> mapLL = new HashTableLL<>(25);
         HashTableTS<Integer, Integer> mapTS = new HashTableTS<>(25);
         HashTableCoalesced<Integer, Integer> mapC = new HashTableCoalesced<>(25);
         mapLL.put(-1, -1);
         mapTS.put(-1, -1);
         mapC.put(-1, -1);

         ArrayList<Integer> randomNum = new ArrayList<>();

         for (int i = 0; i < 70; ++i) {
            int nextInt = (int) (76 * Math.random());
            randomNum.add(i * nextInt);
         }

         startTime = System.currentTimeMillis();
         for (int i = 0; i < 70; ++i) {
            mapLL.put(randomNum.get(i), randomNum.get(i));
         }
         stopTime = System.currentTimeMillis();
         mapLLPut = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         for (int i = 0; i < 70; ++i) {
            mapTS.put(randomNum.get(i), randomNum.get(i));
         }
         stopTime = System.currentTimeMillis();
         mapTSPut = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         for (int i = 0; i < 70; ++i) {
            mapC.put(randomNum.get(i), randomNum.get(i));
         }
         stopTime = System.currentTimeMillis();
         mapCPut = (double) (stopTime - startTime) / 1000.0;


         System.out.println("LinkedList");
         System.out.println(mapLL);
         System.out.println("----------------------------------------------");
         System.out.println("TreeSet");
         System.out.println(mapTS);
         System.out.println("----------------------------------------------");
         System.out.println("Coalesced");
         System.out.println(mapC);
         System.out.println("----------------------------------------------");


         System.out.println("The PUT method performances of tables in terms of time(in seconds) is:");
         System.out.println("LinkedList= " + mapLLPut);
         System.out.println("TreeSet= " + mapTSPut);
         System.out.println("Coalesced= " + mapCPut);


         System.out.println("------------------------------------------");

         startTime = System.currentTimeMillis();
         System.out.println("Element: " + mapLL.get(-1));
         stopTime = System.currentTimeMillis();
         mapLLGet = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         System.out.println("Element: " + mapTS.get(-1));
         stopTime = System.currentTimeMillis();
         mapTSGet = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         System.out.println("Element: " + mapC.get(-1));
         stopTime = System.currentTimeMillis();
         mapCGet = (double) (stopTime - startTime) / 1000.0;

         System.out.println("\nThe GET method for CONTAINing element(-1) performances of tables in terms of time(in seconds) is:");
         System.out.println("LinkedList= " + mapLLGet);
         System.out.println("TreeSet= " + mapTSGet);
         System.out.println("Coalesced= " + mapCGet);
         System.out.println();

         startTime = System.currentTimeMillis();
         System.out.println("Element: " + mapLL.get(-2));
         stopTime = System.currentTimeMillis();
         mapLLGet = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         System.out.println("Element: " + mapTS.get(-2));
         stopTime = System.currentTimeMillis();
         mapTSGet = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         System.out.println("Element: " + mapC.get(-2));
         stopTime = System.currentTimeMillis();
         mapCGet = (double) (stopTime - startTime) / 1000.0;

         System.out.println("\nThe GET method for NON-CONTAINing(-2) element performances of tables in terms of time(in seconds) is:");
         System.out.println("LinkedList= " + mapLLGet);
         System.out.println("TreeSet= " + mapTSGet);
         System.out.println("Coalesced= " + mapCGet);
         System.out.println();

         System.out.println("------------------------------------------");

         startTime = System.currentTimeMillis();
         System.out.println("RemovedElement: " + mapLL.remove(-1));
         stopTime = System.currentTimeMillis();
         mapLLRemove = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         System.out.println("RemovedElement: " + mapTS.remove(-1));
         stopTime = System.currentTimeMillis();
         mapTSRemove = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         System.out.println("RemovedElement: " + mapC.remove(-1));
         stopTime = System.currentTimeMillis();
         mapCRemove = (double) (stopTime - startTime) / 1000.0;

         System.out.println("\nThe REMOVE method for CONTAINing element(-1) performances of tables in terms of time(in seconds) is:");
         System.out.println("LinkedList= " + mapLLRemove);
         System.out.println("TreeSet= " + mapTSRemove);
         System.out.println("Coalesced= " + mapCRemove);
         System.out.println();

         startTime = System.currentTimeMillis();
         System.out.println("RemovedElement: " + mapLL.remove(-2));
         stopTime = System.currentTimeMillis();
         mapLLRemove = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         System.out.println("RemovedElement: " + mapTS.remove(-2));
         stopTime = System.currentTimeMillis();
         mapTSRemove = (double) (stopTime - startTime) / 1000.0;

         startTime = System.currentTimeMillis();
         System.out.println("RemovedElement: " + mapC.remove(-2));
         stopTime = System.currentTimeMillis();
         mapCRemove = (double) (stopTime - startTime) / 1000.0;

         System.out.println("\nThe REMOVE method for NON-CONTAINing(-2) element performances of tables in terms of time(in seconds) is:");
         System.out.println("LinkedList= " + mapLLRemove);
         System.out.println("TreeSet= " + mapTSRemove);
         System.out.println("Coalesced= " + mapCRemove);
         System.out.println();
      }
      catch (NoSuchElementException e){
         e.printStackTrace();
         System.out.println("Exception is found.The details of exception printed");
         throw e;
      }

   }

}
