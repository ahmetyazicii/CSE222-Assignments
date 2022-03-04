public class OfficeDesks implements Supply{

    private final int[][][] supply;
    private final int modelNum = 5;
    private final int colorNum = 4;

    OfficeDesks(){
        supply = new int[modelNum][colorNum][limitAmount];
        for (int i=0;i<modelNum;++i)
            for(int j=0;j<colorNum;++j)
                for(int k=0;k<limitAmount;++k)
                    supply[i][j][k] = 0;
    }

    @Override
    public void fullStore() {
        for (int i=0;i<modelNum;++i)
            for(int j=0;j<colorNum;++j)
                for(int k=0;k<limitAmount;++k)
                    supply[i][j][k] = 1;
    }

    @Override
    public void addElement(int model, int color, int num) {
        int counter = 0;
        for (int i=0;i<limitAmount;++i)
            if(counter != num && supply[model][color][i] != 1) {
                supply[model][color][i] = 1;
                counter++;
            }
    }

    @Override
    public void removeElement(int model, int color, int num) {
        int counter = 0;
        for (int i=limitAmount-1;i>=0;--i)
            if(counter != num && supply[model][color][i] != 0) {
                supply[model][color][i] = 0;
                counter++;
            }
    }

    @Override
    public int getAmount(int model,int color) {
        int counter = 0;
        for (int i=0;i<limitAmount;++i)
            if(supply[model][color][i] == 1)
                counter++;
        return counter;
    }
    @Override
    public void print(int model, int color) {
        System.out.printf("Office Desks Model %d Color %d = %d\n",model+1,color+1,getAmount(model,color));
    }
    @Override
    public boolean isNeedSupply(int model, int color) {
        return getAmount(model, color) < lessAmount;
    }

    public  int getColorNum() { return colorNum; }
    public  int getModelNum() { return modelNum; }

    public boolean areNeedSupply() {
        boolean check = false;
        for (int i=0;i<modelNum;++i)
            for (int j=0;j<colorNum;++j)
                if(isNeedSupply(i,j)) {
                    System.out.printf("Office Desks Model %d Color %s is need to be supplied.Current in store: %d\n",i+1,printColor(j),getAmount(i,j));
                    check = true;
                }
        return check;
    }

    public String printColor(int color){
        switch (color) {
            case 0:
                return "Red";
            case 1:
                return "Orange";
            case 2:
                return "Yellow";
            case 3:
                return "Green";
            case 4:
                return "Blue";
            default:
                throw new IllegalStateException("Unexpected value: " + color);
        }

    }
    @Override
    public String toString() {
        for (int i=0;i<modelNum;++i)
            for(int j=0;j<colorNum;++j)
                print(i,j);
        return "";
    }
}
