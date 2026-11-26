package ua.dut.storage;

import lombok.Getter;
import ua.dut.orders.model.Order;
import ua.dut.orders.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

// потокобезпечне сховище замовлень одного типу
public class OrderStorage<T extends Product> {
    @Getter
    private final String title;
    private final Map<Integer, Order<T>> processed = new ConcurrentHashMap<>();
    private final List<String> errors = Collections.synchronizedList(new ArrayList<>());

    public OrderStorage(String title) {
        this.title = title;
    }

    public void save(Order<T> order) {
        processed.put(order.getId(), order);
    }

    public void saveError(String error) {
        errors.add(error);
    }

    public List<Order<T>> find(Predicate<Order<T>> filter) {
        return processed.values().stream().filter(filter).toList();
    }

    public double totalSum() {
        return processed.values().stream().mapToDouble(Order::total).sum();
    }

    public int count() {
        return processed.size();
    }

    public List<String> getErrors() {
        return List.copyOf(errors);
    }
}
