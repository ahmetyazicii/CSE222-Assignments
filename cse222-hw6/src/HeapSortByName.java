import java.util.ArrayList;
import java.util.Collections;

public class HeapSortByName {

    public static void sort(ArrayList<Product> table,int type){

        buildHeap(table,type);
        shrinkHeap(table,type);

    }

    private static void buildHeap(ArrayList<Product> table,int type){
        int n=1;


        while (n< table.size()){
            n++;
            int child = n-1;
            int parent = (child -1) / 2;
            while (parent >= 0
                    && table.get(parent).compareToName(table.get(child),type) < 0){
                Collections.swap(table,parent,child);
                child = parent;
                parent = (child-1) / 2;
            }
        }
    }

    private static void shrinkHeap(ArrayList<Product> table,int type){
        int n= table.size();

        while (n > 0){
            n--;
            Collections.swap(table,0,n);
            int parent = 0;
            while (true){
                int leftChild = 2 * parent + 1;
                if(leftChild >= n){
                    break;
                }
                int rightChild = leftChild +1;
                int maxChild = leftChild;

                if(rightChild < n && table.get(leftChild).compareToName(table.get(rightChild),type) < 0)
                    maxChild = rightChild;

                if(table.get(parent).compareToName(table.get(maxChild),type) < 0){
                    Collections.swap(table,parent,maxChild);
                    parent = maxChild;
                }
                else break;
            }
        }
    }
}

