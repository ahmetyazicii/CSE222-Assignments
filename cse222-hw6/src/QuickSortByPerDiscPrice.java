import java.util.ArrayList;
import java.util.Collections;

public class QuickSortByPerDiscPrice {

    public static void sort(ArrayList<Product> table,int type){
        quickSort(table,0,table.size()-1,type);
    }

    private static void quickSort(ArrayList<Product> table, int first,int last,int type){
        if(first < last){
            int pivIndex = partition(table,first,last,type);
            quickSort(table,first,pivIndex-1,type);
            quickSort(table,pivIndex+1,last,type);
        }
    }

    private static int partition(ArrayList<Product> table, int first,int last,int type){
        sort3(table,first,last,type);
        Collections.swap(table,first,(first+last) / 2);
        Product pivot = table.get(first);
        int up = first;
        int down = last;

        do{
            while ((up < last) && (pivot.compareToPerDiscPrice(table.get(up),type) >= 0)){
                up++;
            }
            while ((pivot.compareToPerDiscPrice(table.get(down),type) < 0)){
                down--;
            }
            if(up < down){
                Collections.swap(table,up,down);
            }
        }while(up < down );
        Collections.swap(table,first,down);
        return down;
    }

    private static void sort3(ArrayList<Product> table,int first,int last,int type){
        int middle = (first + last) / 2;

        if(table.get(middle).compareToPerDiscPrice(table.get(first),type) < 0)
            Collections.swap(table,first,middle);
        if(table.get(last).compareToPerDiscPrice(table.get(middle),type) < 0)
            Collections.swap(table,middle,last);
        if(table.get(middle).compareToPerDiscPrice(table.get(first),type) < 0)
            Collections.swap(table,first,middle);
    }
}
