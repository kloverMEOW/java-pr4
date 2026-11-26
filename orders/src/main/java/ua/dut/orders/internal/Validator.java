package ua.dut.orders.internal;

import ua.dut.orders.model.Order;
import ua.dut.orders.processing.OrderProcessingException;

// внутрішній клас модуля, пакет не експортується
public final class Validator {
    private Validator() {
    }

    public static void check(Order<?> order) throws OrderProcessingException {
        if (order.getQuantity() <= 0) {
            throw new OrderProcessingException("кількість має бути більше 0", order);
        }
        if (order.getQuantity() > 5) {
            throw new OrderProcessingException("не можна більше 5 одиниць в одному замовленні", order);
        }
    }
}
