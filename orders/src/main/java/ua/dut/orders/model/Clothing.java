package ua.dut.orders.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Clothing extends Product {
    private final String size;
    private final String color;

    @Override
    public String category() {
        return "Одяг";
    }

    @Override
    public String toString() {
        return super.toString() + ", розмір " + size + ", колір " + color;
    }
}
