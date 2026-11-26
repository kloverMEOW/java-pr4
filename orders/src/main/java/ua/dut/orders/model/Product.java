package ua.dut.orders.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Product {
    private final String id;
    private final String name;
    private final double price;

    public abstract String category();

    @Override
    public String toString() {
        return category() + " '" + name + "' " + String.format("%.2f", price) + " грн";
    }
}
