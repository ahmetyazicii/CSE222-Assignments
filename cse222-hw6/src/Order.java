public class Order implements Comparable<Order>{


    private String traderName;
    private String customerName;
    private String productID;
    private int status;

    public Order(String traderName,String customerName, String productID,int status) {

        this.traderName = traderName;
        this.customerName = customerName;
        this.productID = productID;
        this.status = status;
    }

    // getters and setters

    public String getTraderName() {
        return traderName;
    }
    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getProductID() {
        return productID;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(getStatus(),o.getStatus());
    }
}
