package ua.dut.orders.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Order<T extends Product> {
    private final int id;
    private final String customer;
    private final T product;
    private final int quantity;

    public double total() {
        return product.getPrice() * quantity;
    }
}
