public class Product implements Comparable<Product>{
    private String productID;
    private String productName;
    private String category;
    private int price;
    private int discountedPrice;
    private String description;
    private String traderName;


    Product(String productID, String productName, String category, int price, int discountedPrice, String description, String traderName){

        setProductID(productID);
        setProductName(productName);
        setCategory(category);
        setPrice(price);
        setDiscountedPrice(discountedPrice);
        setDescription(description);
        setTraderName(traderName);
    }


    // Setters and getters
    public String getProductID() {
        return productID;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getDiscountedPrice() {
        return discountedPrice;
    }
    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTraderName() {
        return traderName;
    }
    public void setTraderName(String traderID) {
        this.traderName = traderID;
    }


    public int compareToName(Product other,int type){
        if(type > 0)
            return this.getProductName().compareTo(other.getProductName());
        else
            return -(this.getProductName().compareTo(other.getProductName()));
    }
    public int compareToPrice(Product other,int type){
        if(type > 0)
            return Integer.compare(getPrice(),other.getPrice());
        else
            return -(Integer.compare(getPrice(),other.getPrice()));
    }
    public int compareToPerDiscPrice(Product other,int type){
        if(type > 0)
            return Integer.compare(percentageOfDiscount(),other.percentageOfDiscount());
        else
            return -(Integer.compare(percentageOfDiscount(),other.percentageOfDiscount()));
    }
    public int percentageOfDiscount(){
        double result = getPrice() - getDiscountedPrice();
        result /= getPrice();
        result *= 100;

        return (int)(result);
    }

    @Override
    public int compareTo(Product o) {
        return this.getProductName().compareTo(o.getProductName());
    }
}


