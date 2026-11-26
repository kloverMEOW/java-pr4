package ua.dut.orders.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Electronics extends Product {
    private final String brand;
    private final int warrantyMonths;

    // @Builder на конструкторі, щоб у білдері були і поля з Product
    @Builder
    public Electronics(String id, String name, double price, String brand, int warrantyMonths) {
        super(id, name, price);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String category() {
        return "Електроніка";
    }

    @Override
    public String toString() {
        return super.toString() + ", " + brand + ", гарантія " + warrantyMonths + " міс.";
    }
}
