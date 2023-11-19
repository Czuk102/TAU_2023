public class OrderItem {
    private String name;
    private int quantity;
    private double price;

    public OrderItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubTotal() {
        return price * quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductName(String name) {
        this.name = name;
    }
}