import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
    }

    @Test
    public void shouldAddItem() {
        OrderItem item = new OrderItem("Product A", 10, 2);

        order.addItem(item);

        assertEquals(1, order.getItemCount());
    }

    @Test
    public void shouldRemoveItem() {
        Order order = new Order();
        OrderItem item1 = new OrderItem("Product A", 10, 2);
        OrderItem item2 = new OrderItem("Product B", 15, 1);

        order.addItem(item1);
        order.addItem(item2);
        assertEquals(2, order.getItemCount());
        order.removeItem(item1);
        assertEquals(1, order.getItemCount());
    }

    @Test
    public void shouldThrowExceptionWhenRemoveNotPresentItem() {
        OrderItem item1 = new OrderItem("Product A", 10, 2);

        assertThrows(IllegalArgumentException.class, () -> order.removeItem(item1));
    }

    @Test
    void shouldPrintOrderAndNotThrowException() {
        OrderItem item1 = new OrderItem("ProductA", 2, 10.0);
        OrderItem item2 = new OrderItem("ProductB", 1, 5.0);

        order.addItem(item1);
        order.addItem(item2);

        assertDoesNotThrow(() -> order.printOrder(), "Printing the order should not throw an exception");
    }

    @Test
    public void shouldReturnTotalPrice() {
        OrderItem item1 = new OrderItem("Product A", 10, 2);
        OrderItem item2 = new OrderItem("Product B", 15, 1);
        order.addItem(item1);
        order.addItem(item2);

        double totalPrice = order.getTotalPrice();

        assertEquals(35.0, totalPrice);
    }

    @Test
    public void shouldReturnItems() {
        OrderItem item1 = new OrderItem("Product A", 10, 2);
        OrderItem item2 = new OrderItem("Product B", 15, 1);
        order.addItem(item1);
        order.addItem(item2);

        List<OrderItem> items = order.getItems();

        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }
    @Test
    void shouldClearOrder() {
        OrderItem item1 = new OrderItem("ProductA", 2, 10.0);
        OrderItem item2 = new OrderItem("ProductB", 1, 5.0);
        order.addItem(item1);
        order.addItem(item2);

        order.clearOrder();

        List<OrderItem> items = order.getItems();
        assertTrue(items.isEmpty(), "Order should be empty after calling clearOrder");
        assertEquals(0, order.getItemCount(), "Item count should be 0 after clearing the order");
        assertEquals(0.0, order.getTotalPrice(), "Total price should be 0.0 after clearing the order");
    }

    @Test
    void shouldReturnTotalQuantityWithMultipleItems() {
        OrderItem item1 = new OrderItem("ProductA", 2, 10.0);
        OrderItem item2 = new OrderItem("ProductB", 1, 5.0);
        order.addItem(item1);
        order.addItem(item2);

        assertEquals(3, order.getTotalQuantity(), "Total quantity should be calculated correctly");
    }

    @Test
    void shouldReturnNullForNonexistentItemByName() {
        // Create an order with some items
        Order order = new Order();
        OrderItem item1 = new OrderItem("ProductA", 2, 10.0);
        OrderItem item2 = new OrderItem("ProductB", 1, 5.0);
        order.addItem(item1);
        order.addItem(item2);

        OrderItem result = order.getItemByName("NonexistentProduct");

        assertNull(result, "getItemByName should return null for nonexistent item");
    }

    @Test
    void shouldUpdateItemQuantity() {
        OrderItem item = new OrderItem("ProductA", 2, 10.0);
        order.addItem(item);

        order.updateItemQuantity("ProductA", 5);

        assertEquals(5, item.getQuantity(), "Item quantity should be updated");
    }
}