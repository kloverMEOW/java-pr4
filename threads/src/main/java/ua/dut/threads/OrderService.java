package ua.dut.threads;

import ua.dut.orders.model.Order;
import ua.dut.orders.model.Product;
import ua.dut.orders.processing.OrderHandler;
import ua.dut.orders.processing.OrderProcessingException;
import ua.dut.orders.processing.OrderProcessor;
import ua.dut.storage.OrderStorage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.ArrayList;

public class OrderService {
    private final ExecutorService executor;
    private final AtomicInteger ok = new AtomicInteger();
    private final AtomicInteger failed = new AtomicInteger();

    public OrderService(ExecutorService executor) {
        this.executor = executor;
    }

    // generic метод: однаково працює і з Electronics, і з Clothing
    public <T extends Product> List<Future<?>> submitAll(List<Order<T>> orders,
                                                         OrderProcessor<T> processor,
                                                         OrderStorage<T> storage,
                                                         Consumer<String> log) {
        // посилання на метод обробки замовлення
        OrderHandler<T> handler = processor::process;
        List<Future<?>> futures = new ArrayList<>();
        orders.forEach(order -> futures.add(executor.submit(() -> handle(order, handler, storage, log))));
        return futures;
    }

    private <T extends Product> void handle(Order<T> order, OrderHandler<T> handler,
                                            OrderStorage<T> storage, Consumer<String> log) {
        try {
            String line = handler.handle(order);
            storage.save(order);
            ok.incrementAndGet();
            log.accept(line);
        } catch (OrderProcessingException e) {
            failed.incrementAndGet();
            String msg = "помилка в замовленні #" + e.getOrder().getId() + ": " + e.getMessage();
            storage.saveError(msg);
            log.accept("[" + Thread.currentThread().getName() + "] " + msg);
        }
        sleepQuietly(50);
    }

    private static void sleepQuietly(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    public int okCount() {
        return ok.get();
    }

    public int failedCount() {
        return failed.get();
    }
}
