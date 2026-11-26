# ПР4 Багатопотокова обробка замовлень

Maven проект з трьох модулів JPMS:

* `orders` (модуль `ua.dut.orders`): товари `Electronics`, `Clothing` (Lombok builder), `Order<T extends Product>`,
  `OrderProcessor<T extends Product>`, генерація даних через JavaFaker. Пакет `internal` не експортується.
* `storage` (модуль `ua.dut.storage`): потокобезпечне сховище `OrderStorage<T extends Product>`.
* `threads` (модуль `ua.dut.threads`): пул потоків, обробка замовлень лямбдами, головний клас `App`.

```
mvn package
java -p threads/target/lib:threads/target/threads-1.0-SNAPSHOT.jar -m ua.dut.threads/ua.dut.threads.App
```
