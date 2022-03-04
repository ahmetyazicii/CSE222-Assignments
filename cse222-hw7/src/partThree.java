import java.util.Random;

public class partThree {

    public static Random random = new Random();

    /**
     * Driver function for part 3 of homework. More details can be found in pdf file of homework.
     */
    @SuppressWarnings("unchecked")
    public static void compareInsertionPerformances(){

        BinarySearchTree<Integer>[] binarySearchTrees = (BinarySearchTree<Integer>[]) new BinarySearchTree[40];
        RedBlackTree<Integer>[] redBlackTrees = (RedBlackTree<Integer>[]) new RedBlackTree[40];
        SkipList<Integer>[] skipLists = (SkipList<Integer>[]) new SkipList[40];

        System.out.println("The data structures are constructing please wait...");
        bst10K(binarySearchTrees);
        bst20K(binarySearchTrees);
        bst40K(binarySearchTrees);
        bst80K(binarySearchTrees);

        rbt10K(redBlackTrees);
        rbt20K(redBlackTrees);
        rbt40K(redBlackTrees);
        rbt80K(redBlackTrees);

        sl10K(skipLists);
        sl20K(skipLists);
        sl40K(skipLists);
        sl80K(skipLists);
        System.out.println("The data structures are successfully constructed.");


        long startTime;
        long stopTime;
        double bst10, bst20, bst40,bst80;
        double rbt10, rbt20, rbt40,rbt80;
        double sl10, sl20, sl40,sl80;

        System.out.println("\nInserting random 100 number and measuring the running time");

        startTime = System.currentTimeMillis();
        extra100bst10(binarySearchTrees);
        stopTime = System.currentTimeMillis();
        bst10 = (double) (stopTime - startTime) / 1000.0;

        startTime = System.currentTimeMillis();
        extra100bst20(binarySearchTrees);
        stopTime = System.currentTimeMillis();
        bst20 = (double) (stopTime - startTime) / 1000.0;

        startTime = System.currentTimeMillis();
        extra100bst40(binarySearchTrees);
        stopTime = System.currentTimeMillis();
        bst40 = (double) (stopTime - startTime) / 1000.0;

        startTime = System.currentTimeMillis();
        extra100bst80(binarySearchTrees);
        stopTime = System.currentTimeMillis();
        bst80 = (double) (stopTime - startTime) / 1000.0;

        System.out.println("The running times for classic Binary Search Tree are");
        System.out.printf("Inserting 100 number to structure which has 10K element: %.6f\n",bst10);
        System.out.printf("Inserting 100 number to structure which has 20K element: %.6f\n",bst20);
        System.out.printf("Inserting 100 number to structure which has 40K element: %.6f\n",bst40);
        System.out.printf("Inserting 100 number to structure which has 80K element: %.6f\n",bst80);


        startTime = System.currentTimeMillis();
        extra100rbt10(redBlackTrees);
        stopTime = System.currentTimeMillis();
        rbt10 = (double) (stopTime - startTime) / 1000.0;

        startTime = System.currentTimeMillis();
        extra100rbt20(redBlackTrees);
        stopTime = System.currentTimeMillis();
        rbt20 = (double) (stopTime - startTime) / 1000.0;

        startTime = System.currentTimeMillis();
        extra100rbt40(redBlackTrees);
        stopTime = System.currentTimeMillis();
        rbt40 = (double) (stopTime - startTime) / 1000.0;

        startTime = System.currentTimeMillis();
        extra100rbt80(redBlackTrees);
        stopTime = System.currentTimeMillis();
        rbt80 = (double) (stopTime - startTime) / 1000.0;

        System.out.println("The running times for Red-Black tree are");
        System.out.printf("Inserting 100 number to structure which has 10K element: %.6f\n",rbt10);
        System.out.printf("Inserting 100 number to structure which has 20K element: %.6f\n",rbt20);
        System.out.printf("Inserting 100 number to structure which has 40K element: %.6f\n",rbt40);
        System.out.printf("Inserting 100 number to structure which has 80K element: %.6f\n",rbt80);


        startTime = System.currentTimeMillis();
        extra100sl10(skipLists);
        stopTime = System.currentTimeMillis();
        sl10 = (double) (stopTime - startTime) / 1000.0;

        startTime = System.currentTimeMillis();
        extra100sl20(skipLists);
        stopTime = System.currentTimeMillis();
        sl20 = (double) (stopTime - startTime) / 1000.0;

        startTime = System.currentTimeMillis();
        extra100sl40(skipLists);
        stopTime = System.currentTimeMillis();
        sl40 = (double) (stopTime - startTime) / 1000.0;

        startTime = System.currentTimeMillis();
        extra100sl80(skipLists);
        stopTime = System.currentTimeMillis();
        sl80 = (double) (stopTime - startTime) / 1000.0;

        System.out.println("The running times for Skip-List are");
        System.out.printf("Inserting 100 number to structure which has 10K element: %.6f\n",sl10);
        System.out.printf("Inserting 100 number to structure which has 20K element: %.6f\n",sl20);
        System.out.printf("Inserting 100 number to structure which has 40K element: %.6f\n",sl40);
        System.out.printf("Inserting 100 number to structure which has 80K element: %.6f\n",sl80);


        System.out.println("\nThe average running times and and problem sizes:");
        double bstTotal = bst10 + bst20 + bst40 + bst80;
        bstTotal /= 4;
        System.out.printf("Average running time of classic Binary Search Tree: %.6f  --  The problem size: %d\n",bstTotal,calculateProblemSizeBST(binarySearchTrees));
        double rbtTotal = rbt10 + rbt20 + rbt40 + rbt80;
        rbtTotal /= 4;
        System.out.printf("Average running time of Red-Black Tree: %.6f  --  The problem size: %d\n",rbtTotal,calculateProblemSizeRBT(redBlackTrees));
        double slTotal = sl10 + sl20 + sl40 +sl80;
        slTotal /= 4;
        System.out.printf("Average running time of Skip-List: %.6f  --  The problem size: %d\n",slTotal,calculateProblemSizeSL(skipLists));

    }
    private static int calculateProblemSizeBST(BinarySearchTree<Integer>[] binarySearchTrees){
        int sum = 0;
        for(int i=0; i<40;++i){
            sum += binarySearchTrees[i].size();
        }
        return sum;
    }
    private static int calculateProblemSizeRBT(RedBlackTree<Integer>[] redBlackTrees){
        int sum = 0;
        for(int i=0; i<40;++i){
            sum += redBlackTrees[i].size();
        }
        return sum;
    }
    private static int calculateProblemSizeSL(SkipList<Integer>[] skipLists){
        int sum = 0;
        for(int i=0; i<40;++i){
            sum += skipLists[i].size();
        }
        return sum;
    }

    private static void bst10K(BinarySearchTree<Integer>[] binarySearchTrees){
        for(int i=0;i<10;++i){
            binarySearchTrees[i] = new BinarySearchTree<>();
            for(int j=0;j<10000;++j){
                binarySearchTrees[i].add(random.nextInt());
            }
        }
    }
    private static void rbt10K(RedBlackTree<Integer>[] redBlackTrees){
        for(int i=0;i<10;++i){
            redBlackTrees[i] = new RedBlackTree<>();
            for(int j=0;j<10000;++j){
                redBlackTrees[i].add(random.nextInt());
            }
        }
    }
    private static void sl10K(SkipList<Integer>[] SkipList){
        for(int i=0;i<10;++i){
            SkipList[i] = new SkipList<>();
            for(int j=0;j<10000;++j){
                SkipList[i].insert(random.nextInt());
            }
        }
    }

    private static void bst20K(BinarySearchTree<Integer>[] binarySearchTrees){
        for(int i=10;i<20;++i){
            binarySearchTrees[i] = new BinarySearchTree<>();
            for(int j=0;j<20000;++j){
                binarySearchTrees[i].add(random.nextInt());
            }
        }
    }
    private static void rbt20K(RedBlackTree<Integer>[] redBlackTrees){
        for(int i=10;i<20;++i){
            redBlackTrees[i] = new RedBlackTree<>();
            for(int j=0;j<20000;++j){
                redBlackTrees[i].add(random.nextInt());
            }
        }
    }
    private static void sl20K(SkipList<Integer>[] SkipList){
        for(int i=10;i<20;++i){
            SkipList[i] = new SkipList<>();
            for(int j=0;j<20000;++j){
                SkipList[i].insert(random.nextInt());
            }
        }
    }

    private static void bst40K(BinarySearchTree<Integer>[] binarySearchTrees){
        for(int i=20;i<30;++i){
            binarySearchTrees[i] = new BinarySearchTree<>();
            for(int j=0;j<40000;++j){
                binarySearchTrees[i].add(random.nextInt());
            }
        }
    }
    private static void rbt40K(RedBlackTree<Integer>[] redBlackTrees){
        for(int i=20;i<30;++i){
            redBlackTrees[i] = new RedBlackTree<>();
            for(int j=0;j<40000;++j){
                redBlackTrees[i].add(random.nextInt());
            }
        }
    }
    private static void sl40K(SkipList<Integer>[] SkipList){
        for(int i=20;i<30;++i){
            SkipList[i] = new SkipList<>();
            for(int j=0;j<40000;++j){
                SkipList[i].insert(random.nextInt());
            }
        }
    }

    private static void bst80K(BinarySearchTree<Integer>[] binarySearchTrees){
        for(int i=30;i<40;++i){
            binarySearchTrees[i] = new BinarySearchTree<>();
            for(int j=0;j<80000;++j){
                binarySearchTrees[i].add(random.nextInt());
            }
        }
    }
    private static void rbt80K(RedBlackTree<Integer>[] redBlackTrees){
        for(int i=30;i<40;++i){
            redBlackTrees[i] = new RedBlackTree<>();
            for(int j=0;j<80000;++j){
                redBlackTrees[i].add(random.nextInt());
            }
        }
    }
    private static void sl80K(SkipList<Integer>[] SkipList){
        for(int i=30;i<40;++i){
            SkipList[i] = new SkipList<>();
            for(int j=0;j<80000;++j){
                SkipList[i].insert(random.nextInt());
            }
        }
    }

    private static void extra100bst10(BinarySearchTree<Integer>[] binarySearchTrees){
        for(int i=0;i<10;++i){
            for(int j=0;j<100;++j){
                binarySearchTrees[i].add(random.nextInt());
            }
        }
    }
    private static void extra100rbt10(RedBlackTree<Integer>[] redBlackTrees){
        for(int i=0;i<10;++i){
            for(int j=0;j<100;++j){
                redBlackTrees[i].add(random.nextInt());
            }
        }
    }
    private static void extra100sl10(SkipList<Integer>[] skipLists){
        for(int i=0;i<10;++i){
            for(int j=0;j<100;++j){
                skipLists[i].insert(random.nextInt());
            }
        }
    }

    private static void extra100bst20(BinarySearchTree<Integer>[] binarySearchTrees){
        for(int i=10;i<20;++i){
            for(int j=0;j<100;++j){
                binarySearchTrees[i].add(random.nextInt());
            }
        }
    }
    private static void extra100rbt20(RedBlackTree<Integer>[] redBlackTrees){
        for(int i=10;i<20;++i){
            for(int j=0;j<100;++j){
                redBlackTrees[i].add(random.nextInt());
            }
        }
    }
    private static void extra100sl20(SkipList<Integer>[] skipLists){
        for(int i=10;i<20;++i){
            for(int j=0;j<100;++j){
                skipLists[i].insert(random.nextInt());
            }
        }
    }

    private static void extra100bst40(BinarySearchTree<Integer>[] binarySearchTrees){
        for(int i=20;i<30;++i){
            for(int j=0;j<100;++j){
                binarySearchTrees[i].add(random.nextInt());
            }
        }
    }
    private static void extra100rbt40(RedBlackTree<Integer>[] redBlackTrees){
        for(int i=20;i<30;++i){
            for(int j=0;j<100;++j){
                redBlackTrees[i].add(random.nextInt());
            }
        }
    }
    private static void extra100sl40(SkipList<Integer>[] skipLists){
        for(int i=20;i<30;++i){
            for(int j=0;j<100;++j){
                skipLists[i].insert(random.nextInt());
            }
        }
    }

    private static void extra100bst80(BinarySearchTree<Integer>[] binarySearchTrees){
        for(int i=30;i<40;++i){
            for(int j=0;j<100;++j){
                binarySearchTrees[i].add(random.nextInt());
            }
        }
    }
    private static void extra100rbt80(RedBlackTree<Integer>[] redBlackTrees){
        for(int i=30;i<40;++i){
            for(int j=0;j<100;++j){
                redBlackTrees[i].add(random.nextInt());
            }
        }
    }
    private static void extra100sl80(SkipList<Integer>[] skipLists){
        for(int i=30;i<40;++i){
            for(int j=0;j<100;++j){
                skipLists[i].insert(random.nextInt());
            }
        }
    }

}
