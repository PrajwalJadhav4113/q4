interface OrderRepository {
    void save(Order order);
}

class DatabaseSaver implements OrderRepository {
    @Override
    public void save(Order order) {
        System.out.println("DIP: Low-level DatabaseSaver saved order " + order.id);
    }
}

interface IPriceCalculator {
    double calculate(double basePrice);
}

class StandardPriceCalculator implements IPriceCalculator {
    @Override
    public double calculate(double basePrice) {
        return basePrice * 1.05;
    }
}

class Order {
    public int id;
    public double basePrice;

    public Order(int id, double price) {
        this.id = id;
        this.basePrice = price;
    }
}

class OrderProcessor {
    private final OrderRepository repository;
    private final IPriceCalculator calculator;

    public OrderProcessor(OrderRepository repository, IPriceCalculator calculator) {
        this.repository = repository;
        this.calculator = calculator;
    }

    public void processOrder(Order order) {
        double finalPrice = calculator.calculate(order.basePrice);
        System.out.println("SRP: Calculated final price: " + finalPrice);
        repository.save(order);
    }
}

public class SOLIDTest {
    public static void main(String[] args) {
        OrderRepository saver = new DatabaseSaver();
        IPriceCalculator calculator = new StandardPriceCalculator();
        OrderProcessor processor = new OrderProcessor(saver, calculator);

        processor.processOrder(new Order(101, 500.00));
    }
}
