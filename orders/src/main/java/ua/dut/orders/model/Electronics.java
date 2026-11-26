package ua.dut.orders.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Electronics extends Product {
    private final String brand;
    private final int warrantyMonths;

    @Override
    public String category() {
        return "Електроніка";
    }

    @Override
    public String toString() {
        return super.toString() + ", " + brand + ", гарантія " + warrantyMonths + " міс.";
    }
}
