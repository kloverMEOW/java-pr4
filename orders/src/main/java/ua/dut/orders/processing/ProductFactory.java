package ua.dut.orders.processing;

import com.github.javafaker.Faker;
import ua.dut.orders.model.Clothing;
import ua.dut.orders.model.Electronics;
import ua.dut.orders.model.Order;
import ua.dut.orders.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

// генерація тестових даних через JavaFaker
public class ProductFactory {
    private static final String[] SIZES = {"S", "M", "L", "XL"};
    private final Faker faker;
    private final Random random;

    public ProductFactory(long seed) {
        this.random = new Random(seed);
        this.faker = new Faker(new Locale("en"), random);
    }

    public Electronics electronics() {
        return Electronics.builder()
                .id(faker.code().ean8())
                .name(faker.commerce().productName())
                .price(Double.parseDouble(faker.commerce().price(500, 40000).replace(',', '.')))
                .brand(faker.company().name())
                .warrantyMonths(faker.number().numberBetween(6, 37))
                .build();
    }

    public Clothing clothing() {
        return Clothing.builder()
                .id(faker.code().ean8())
                .name(faker.commerce().productName())
                .price(Double.parseDouble(faker.commerce().price(200, 4000).replace(',', '.')))
                .size(SIZES[random.nextInt(SIZES.length)])
                .color(faker.color().name())
                .build();
    }

    public <T extends Product> Order<T> order(int id, T product) {
        return Order.<T>builder()
                .id(id)
                .customer(faker.name().fullName())
                .product(product)
                .quantity(faker.number().numberBetween(0, 7))
                .build();
    }

    public List<Order<Electronics>> electronicsOrders(int count, int startId) {
        List<Order<Electronics>> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(order(startId + i, electronics()));
        }
        return list;
    }

    public List<Order<Clothing>> clothingOrders(int count, int startId) {
        List<Order<Clothing>> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(order(startId + i, clothing()));
        }
        return list;
    }
}
