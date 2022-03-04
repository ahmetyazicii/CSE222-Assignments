public class OfficeCabinets implements Supply{

    private final int[][] supply;
    private final int modelNum = 12;

    OfficeCabinets(){
        supply = new int[modelNum][limitAmount];
        for (int i=0;i<modelNum;++i)
            for(int j=0;j<limitAmount;++j)
                supply[i][j] = 0;
    }

    @Override
    public void fullStore() {
        for (int i=0;i<modelNum;++i)
            for(int j=0;j<limitAmount;++j)
                supply[i][j] = 1;
    }

    public  int getModelNum() { return modelNum; }


    @Override
    public void addElement(int model, int color, int num) {
        int counter = 0;
        for (int i=0;i<limitAmount;++i)
            if(counter != num && supply[model][i] != 1) {
                supply[model][i] = 1;
                counter++;
            }
    }

    @Override
    public void removeElement(int model, int color, int num) {
        int counter = 0;
        for (int i=limitAmount-1;i>=0;--i)
            if(counter != num && supply[model][i] != 0) {
                supply[model][i] = 0;
                counter++;
            }
    }

    @Override
    public int getAmount(int model,int color) {
        int counter = 0;
        for (int i=0;i<limitAmount;++i)
            if(supply[model][i] == 1)
                counter++;
        return counter;
    }
    @Override
    public void print(int model, int color) {
        System.out.printf("Office Cabinets Model %d = %d\n",model+1,getAmount(model,color));
    }
    @Override
    public boolean isNeedSupply(int model, int color) {
        return getAmount(model, color) < lessAmount;
    }

    public boolean areNeedSupply() {
        boolean check = false;
        for (int i=0;i<modelNum;++i)
            if(isNeedSupply(i,0)){
                System.out.printf("Office Cabinets Model %d is need to be supplied.Current in store: %d\n",i+1,getAmount(i,0));
                check = true;
            }
        return check;
    }

    @Override
    public String toString() {
        for (int i=0;i<modelNum;++i)
                print(i,0);
        return "";
    }
}
