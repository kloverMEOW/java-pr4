module ua.dut.orders {
    requires static lombok;
    requires javafaker;
    // snakeyaml всередині javafaker використовує java.sql.Date, без цього падає NoClassDefFoundError
    requires java.sql;

    exports ua.dut.orders.model;
    exports ua.dut.orders.processing;
    // пакет internal не експортується, ззовні його не видно
}
