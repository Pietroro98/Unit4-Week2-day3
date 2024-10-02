
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Product product1 = new Product(1L, "Il Signore Degli Anelli", "Books", 150.0);
        Product product2 = new Product(2L, "Baby Toy", "Baby", 10.99);
        Product product3 = new Product(3L, "Advanced Java", "Books", 120.0);
        Product product4 = new Product(4L, "Boy's T-shirt", "Boys", 30.0);
        Product product5 = new Product(5L, "Boy's Shoes", "Boys", 70.0);
        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5);


        Customer customer1 = new Customer(1L, "Mario ROssi", 2);
        Customer customer2 = new Customer(2L, "Giuseppe verdi", 2);

        Order order1 = new Order(1L, "shipped", LocalDate.of(2021, 2, 6), LocalDate.of(2021, 2, 20), Arrays.asList(product1, product4), customer1);
        Order order2 = new Order(2L, "delivered", LocalDate.of(2021, 3, 16), LocalDate.of(2021, 3, 19), Arrays.asList(product2, product5), customer2);
        Order order3 = new Order(3L, "pending", LocalDate.of(2021, 2, 22), LocalDate.of(2021, 2, 12), Arrays.asList(product3, product4), customer1);
        List<Order> orders = Arrays.asList(order1, order2, order3);

        System.out.println("**********ESERCIZIO1**********");

        Supplier<List<Product>> booksOver100Supplier = () -> products.stream()
                .filter(product -> "Books".equals(product.getCategory()) && product.getPrice() > 100)
                .collect(Collectors.toList());
        List<Product> booksOver100 = booksOver100Supplier.get();
        System.out.println("Lista prodotti di categoria 'Books' con un prezzo > 100: " + booksOver100);

        System.out.println("**********ESERCIZIO2**********");

        Supplier<List<Order>> ordersWithBabyProductsSupplier = () -> orders.stream()
                .filter(order -> order.getProducts().stream().anyMatch(product -> "Baby".equals(product.getCategory())))
                .collect(Collectors.toList());
        List<Order> ordersWithBabyProducts = ordersWithBabyProductsSupplier.get();
        System.out.println("Lista ordini con prodotti della categoria 'Baby': " + ordersWithBabyProducts);

        System.out.println("**********ESERCIZIO3**********");

        Supplier<List<Product>> discountedBoysProductsSupplier = () -> products.stream()
                .filter(product -> "Boys".equals(product.getCategory()))
                .peek(product -> product.setPrice(product.getPrice() * 0.9))
                .collect(Collectors.toList());
        List<Product> discountedBoysProducts = discountedBoysProductsSupplier.get();
        System.out.println("Prodotti 'Boys' con sconto del 10%: " + discountedBoysProducts);

        System.out.println("**********ESERCIZIO4**********");
        Supplier<List<Product>> tier2CustomerProductsSupplier = () -> {
            LocalDate startDate = LocalDate.of(2021, 2, 1);
            LocalDate endDate = LocalDate.of(2021, 4, 1);
            return orders.stream()
                    .filter(order -> order.getCustomer().getTier() == 2 &&
                            (order.getOrderDate().isAfter(startDate) || order.getOrderDate().isEqual(startDate)) &&
                            (order.getOrderDate().isBefore(endDate) || order.getOrderDate().isEqual(endDate)))
                    .flatMap(order -> order.getProducts().stream())
                    .collect(Collectors.toList());
        };

        List<Product> tier2CustomerProducts = tier2CustomerProductsSupplier.get();
        System.out.println("Prodotti ordinati da clienti di livello tier 2 tra 01-Feb-2021 e 01-Apr-2021: " + tier2CustomerProducts);
    }

    }



