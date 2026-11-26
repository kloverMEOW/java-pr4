package ua.dut.threads;

import ua.dut.orders.model.Clothing;
import ua.dut.orders.model.Electronics;
import ua.dut.orders.model.Order;
import ua.dut.orders.processing.OrderProcessor;
import ua.dut.orders.processing.ProductFactory;
import ua.dut.storage.OrderStorage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {
    public static void main(String[] args) throws Exception {
        ProductFactory factory = new ProductFactory(42);

        List<Order<Electronics>> electronicsOrders = factory.electronicsOrders(5, 1);
        List<Order<Clothing>> clothingOrders = factory.clothingOrders(5, 101);

        System.out.println("Згенеровані товари:");
        electronicsOrders.stream().map(Order::getProduct).forEach(System.out::println);
        clothingOrders.stream().map(Order::getProduct).forEach(System.out::println);
        System.out.println();

        OrderStorage<Electronics> electronicsStorage = new OrderStorage<>("електроніка");
        OrderStorage<Clothing> clothingStorage = new OrderStorage<>("одяг");

        OrderService service = new OrderService(Executors.newFixedThreadPool(3));
        List<Future<?>> all = new java.util.ArrayList<>();
        all.addAll(service.submitAll(electronicsOrders, new OrderProcessor<>("Electronics"),
                electronicsStorage, System.out::println));
        all.addAll(service.submitAll(clothingOrders, new OrderProcessor<>("Clothing"),
                clothingStorage, System.out::println));
        for (Future<?> f : all) {
            f.get();
        }
        service.shutdown();

        System.out.println();
        System.out.println("Успішно: " + service.okCount() + ", з помилками: " + service.failedCount());
        printStorage(electronicsStorage);
        printStorage(clothingStorage);

        System.out.println("Замовлення електроніки дорожчі за 20000 грн:");
        electronicsStorage.find(o -> o.total() > 20000)
                .forEach(o -> System.out.printf("  #%d %s %.2f%n", o.getId(), o.getCustomer(), o.total()));
    }

    private static void printStorage(OrderStorage<?> storage) {
        System.out.printf("Сховище '%s': %d замовлень на суму %.2f грн, помилок %d%n",
                storage.getTitle(), storage.count(), storage.totalSum(), storage.getErrors().size());
    }
}
