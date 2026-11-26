package ua.dut.orders.processing;

import ua.dut.orders.model.Order;
import ua.dut.orders.model.Product;

// функціональний інтерфейс для обробки, сюди передається посилання на метод process
@FunctionalInterface
public interface OrderHandler<T extends Product> {
    String handle(Order<T> order) throws OrderProcessingException;
}
