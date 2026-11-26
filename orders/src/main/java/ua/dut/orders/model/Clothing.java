package ua.dut.orders.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Clothing extends Product {
    private final String size;
    private final String color;

    @Builder
    public Clothing(String id, String name, double price, String size, String color) {
        super(id, name, price);
        this.size = size;
        this.color = color;
    }

    @Override
    public String category() {
        return "Одяг";
    }

    @Override
    public String toString() {
        return super.toString() + ", розмір " + size + ", колір " + color;
    }
}
