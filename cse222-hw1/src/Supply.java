public interface Supply {
    int limitAmount = 10;
    int lessAmount = 3;

    void addElement(int model,int color,int num);
    void removeElement(int model,int color,int num);
    int getAmount(int model,int color);
    boolean isNeedSupply(int model,int color);
    void print(int model,int color);
    void fullStore();
}
