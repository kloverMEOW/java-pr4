package ua.dut.orders.processing;

import lombok.Getter;
import ua.dut.orders.model.Order;

// виняток зберігає замовлення, на якому сталася помилка
@Getter
public class OrderProcessingException extends Exception {
    private final transient Order<?> order;

    public OrderProcessingException(String message, Order<?> order) {
        super(message);
        this.order = order;
    }
}
