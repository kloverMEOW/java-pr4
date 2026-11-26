package ua.dut.orders.processing;

import ua.dut.orders.internal.Validator;
import ua.dut.orders.model.Order;
import ua.dut.orders.model.Product;

// обробник працює тільки з нащадками Product
public class OrderProcessor<T extends Product> {
    private final String name;

    public OrderProcessor(String name) {
        this.name = name;
    }

    public String process(Order<T> order) throws OrderProcessingException {
        Validator.check(order);
        return String.format("[%s] %s: замовлення #%d %s x%d = %.2f грн (%s)",
                Thread.currentThread().getName(), name, order.getId(),
                order.getProduct().getName(), order.getQuantity(), order.total(), order.getCustomer());
    }
}
