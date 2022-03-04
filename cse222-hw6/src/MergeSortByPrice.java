import java.util.ArrayList;

public class MergeSortByPrice {

    public static void sort(ArrayList<Product> table,int type){
        if(table.size() > 1){
            int halfSize = table.size() / 2;
            ArrayList<Product> left = new ArrayList<>();
            ArrayList<Product> right = new ArrayList<>();
            for(int i=0;i<halfSize;++i)
                left.add(table.get(i));
            for(int i=halfSize;i<table.size();++i)
                right.add(table.get(i));

            sort(left,type);
            sort(right,type);

            merge(table,left,right,type);
        }
    }

    private static void merge(ArrayList<Product> output, ArrayList<Product> left, ArrayList<Product> right,int type){
        int i=0;
        int j=0;
        int k=0;

        while(i < left.size() && j < right.size()){

            if(left.get(i).compareToPrice(right.get(j),type) < 0)
                output.set(k++,left.get(i++));
            else
                output.set(k++,right.get(j++));
        }
        while (i < left.size())
            output.set(k++,left.get(i++));
        while (j < right.size())
            output.set(k++,right.get(j++));

    }

}
