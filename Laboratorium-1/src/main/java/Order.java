import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void removeItem(OrderItem item) {
        if (items.contains(item)) {
            items.remove(item);
        } else {
            throw new IllegalArgumentException("Item not found");
        }
    }

    public double getTotalPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubTotal();
        }
        return total;
    }

    public void printOrder() {
        for (OrderItem item : items) {
            System.out.println(item.getProductName() + " " + item.getQuantity() + " " + item.getPrice() + " " + item.getSubTotal());
        }
        System.out.println("Total: " + getTotalPrice());
    }

    public int getItemCount() {
        return items.size();
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void clearOrder() {
        items.clear();
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (OrderItem item : items) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }

    public OrderItem getItemByName(String productName) {
        for (OrderItem item : items) {
            if (item.getProductName().equals(productName)) {
                return item;
            }
        }
        return null;
    }
    public void updateItemQuantity(String productName, int newQuantity) {
        OrderItem item = getItemByName(productName);
        if (item != null) {
            item.setQuantity(newQuantity);
        } else {
            throw new IllegalArgumentException("Item not found");
        }
    }
}