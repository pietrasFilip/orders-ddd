package com.app.application.service.orders;

import com.app.application.dto.customer.GetCustomerDto;
import com.app.application.dto.email.CreateEmailDto;
import com.app.application.dto.email.GetEmailDto;
import com.app.application.dto.order.AddOrderDto;
import com.app.application.dto.order.CreateOrderDto;
import com.app.application.dto.order.GetOrderDto;
import com.app.application.dto.pdf.CreatePdfForEmailDto;
import com.app.application.dto.product.GetProductDto;
import com.app.application.validator.CreateOrderDtoValidator;
import com.app.domain.customer_management.model.Customer;
import com.app.domain.customer_management.model.repository.CustomerRepositoryDb;
import com.app.domain.orders_management.model.Order;
import com.app.domain.product_management.model.Product;
import com.app.domain.product_management.model.product_category.Category;
import com.app.domain.product_management.model.repository.ProductRepositoryDb;
import com.app.infrastructure.persistence.entity.OrderEntity;
import com.app.infrastructure.persistence.provider.OrdersProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

import static com.app.domain.orders_management.model.OrderMapper.*;
import static com.app.domain.policy.abstract_factory.validator.DataValidator.validateDateTime;
import static com.app.domain.product_management.model.ProductMapper.toCategory;
import static com.app.domain.product_management.model.ProductMapper.toPrice;
import static java.util.stream.Collectors.*;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final List<Order> orders;
    private final CustomerRepositoryDb customerRepository;
    private final ProductRepositoryDb productRepository;
    private final CreateOrderDtoValidator createOrderDtoValidator;
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final Logger logger = LogManager.getRootLogger();

    public OrdersServiceImpl(OrdersProvider ordersProvider, CustomerRepositoryDb customerRepository,
                             ProductRepositoryDb productRepository, CreateOrderDtoValidator createOrderDtoValidator,
                             ApplicationEventPublisher applicationEventPublisher) {
        this.orders = new ArrayList<>(ordersProvider.provide());
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.createOrderDtoValidator = createOrderDtoValidator;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     *
     * @param from Start of time interval.
     * @param to   End of time interval.
     * @return Average price of all orders from list matching given time interval.
     */
    @Override
    public BigDecimal getAverageOrdersPrice(ZonedDateTime from, ZonedDateTime to) {
        if (from.isAfter(to)) {
            logger.error("Wrong date range given, from {} to {}", from, to);
            throw new IllegalArgumentException("Wrong date range");
        }

        if (numberOfProductsInOrdersInGivenTime(from, to) == 0) {
            return BigDecimal.valueOf(0);
        }

        return orders
                .stream()
                .filter(order -> order.hasDateBetween(from, to))
                .map(Order::totalPrice)
                .reduce(BigDecimal::add)
                .orElseThrow()
                .divide(BigDecimal.valueOf(numberOfProductsInOrdersInGivenTime(from, to)), 3,
                        RoundingMode.HALF_UP);
    }

    private int numberOfProductsInOrdersInGivenTime(ZonedDateTime from, ZonedDateTime to) {
        return orders
                .stream()
                .filter(order -> order.hasDateBetween(from, to))
                .map(toQuantity)
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     *
     * @return Map of most expensive products from category.
     */
    @Override
    public Map<Category, List<GetProductDto>> getMaxPriceProductFromCategory() {
        return orders
                .stream()
                .map(toProduct)
                .toList()
                .stream()
                .collect(groupingBy(toCategory,
                        collectingAndThen(
                                groupingBy(toPrice),
                                groupedByPrice -> groupedByPrice
                                        .entrySet()
                                        .stream()
                                        .max(Map.Entry.comparingByKey())
                                        .map(val -> val
                                                .getValue()
                                                .stream()
                                                .map(Product::toGetProductDto)
                                                .toList())
                                        .orElseThrow()
                        )));

    }

    /**
     *
     * @param email Email used to identify a customer.
     * @return All products purchased by chosen customer.
     */
    @Override
    public Map<GetProductDto, Integer> getProductsPurchasedByCustomer(String email) {
        var customer = customersWithMails().get(email);

        if (customer == null) {
            logger.error("No order associated with this email");
            throw new IllegalStateException("No orders found");
        }

        return orders
                .stream()
                .filter(order -> order.containsEmail(email))
                .map(Order::toNumberOfProductsInOrderEntry)
                .collect(toMap(key -> key
                                .getKey()
                                .toGetProductDto(),
                        Map.Entry::getValue,
                        Integer::sum));
    }

    private Map<String, Customer> customersWithMails() {
        return orders
                .stream()
                .map(Order::toCustomerMailEntry)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (m1, m2) -> m1));
    }

    /**
     *
     * @param createEmailDto dto that contains data necessary for sending mail
     * @return response message
     */
    @Override
    public GetEmailDto sendPdfEmailWithProductsPurchasedByCustomer(CreateEmailDto createEmailDto) {
        var pdfContent = new ArrayList<String>();
        getProductsPurchasedByCustomer(createEmailDto.email())
                .forEach((productDto, quantity) -> pdfContent
                        .add(("PRODUCT %s, QUANTITY %s").formatted(productDto.name(), quantity))
                );
        applicationEventPublisher.publishEvent(new CreatePdfForEmailDto(createEmailDto, pdfContent));
        return new GetEmailDto("Email sent");
    }

    /**
     *
     * @param min Boolean value. When true - get the oldest order date. When false - get the latest order date.
     * @return  The oldest or the latest order date.
     */
    @Override
    public List<LocalDate> getDateOfMinAndMaxNumberOfOrders(boolean min) {
        return getMinMaxOrdersDate(min);
    }

    private List<LocalDate> getMinMaxOrdersDate(boolean min) {
        var ordersDateStream = getNumberOfOrdersInDate()
                .entrySet()
                .stream();
        return min ?
                ordersDateStream.
                        min(Map.Entry.comparingByKey()).
                        map(Map.Entry::getValue).orElseThrow() :
                ordersDateStream.
                        max(Map.Entry.comparingByKey()).
                        map(Map.Entry::getValue).orElseThrow();
    }

    /**
     *
     * @return  Map with number of orders made each day.
     */
    private Map<Integer, List<LocalDate>> getNumberOfOrdersInDate() {
        return orders
                .stream()
                .collect(groupingBy(toOrderLocalDate))
                .entrySet()
                .stream()
                .collect(toMap(key -> key.getValue().size(),
                        val -> new ArrayList<>(List.of(val.getKey())),(v1, v2) -> {
                            v1.addAll(v2);
                            return v1;
                        }));
    }

    /**
     *
     * @return  List of customers who spent the most money on orders.
     */
    @Override
    public List<GetCustomerDto> getCustomerWithMostExpensiveOrders() {
        return customersWithOrdersTotalPrice()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .map(entry -> entry
                        .getValue()
                        .stream()
                        .map(Customer::toGetCustomerDto)
                        .toList())
                .orElseThrow(() -> new IllegalStateException("No customers found"));
    }

    /**
     *
     * @return  Map with orders total price and customers who spent specified amount of money.
     */
    private Map<BigDecimal, List<Customer>> customersWithOrdersTotalPrice() {
        return orders
                .stream()
                .collect(groupingBy(toCustomer))
                .entrySet()
                .stream()
                .collect(toMap(
                        key -> key.getValue()
                                .stream()
                                .map(Order::totalPrice)
                                .reduce(BigDecimal::add)
                                .orElseThrow(),
                        value -> new ArrayList<>(List.of(value.getKey())),
                        (v1, v2) -> {
                            v1.addAll(v2);
                            return v1;
                        }
                ));
    }

    /**
     *
     * @return Map of orders and their price after discount
     */
    @Override
    public Map<GetOrderDto, BigDecimal> getOrdersPriceAfterDiscount() {
        return orders
                .stream()
                .collect(toMap(
                        order -> order.toGetOrderDto(),
                        Order::totalPriceWithDiscount,
                        BigDecimal::add
                ));
    }

    /**
     *
     * @param quantity  Number of orders.
     * @return  Set of customers who have exactly or more orders.
     */
    @Override
    public Set<GetCustomerDto> getCustomersThatOrderedExactlyOrMoreThan(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Product quantity is negative number");
        }

        return orders
                .stream()
                .filter(order -> toQuantity.apply(order) >= quantity)
                .map(toCustomer
                        .andThen(Customer::toGetCustomerDto))
                .collect(toSet());
    }

    /**
     *
     * @return  Set of categories from which products were old the most.
     */
    @Override
    public Set<Category> getMostBoughtCategory() {
        return getNumberOfSalesInCategory()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    private Map<Integer, Set<Category>> getNumberOfSalesInCategory() {
        return orders
                .stream()
                .collect(groupingBy(orderToCategory))
                .entrySet()
                .stream()
                .collect(toMap(key -> key.getValue().size(),
                        val -> new HashSet<>(Set.of(val.getKey())),(v1, v2) -> {
                            v1.addAll(v2);
                            return v1;
                        }));
    }

    /**
     *
     * @return  Map of number of sold products each month.
     */
    @Override
    public Map<String, Integer> getMonthsWithNumberOfBoughtProductsSortedDescending() {
        return orders
                .stream()
                .collect(groupingBy(toOrderMonthName,
                        collectingAndThen(
                                groupingBy(toQuantity),
                                groupedByQuantity -> groupedByQuantity
                                        .keySet()
                                        .stream()
                                        .reduce(0, Integer::sum)
                        )))
                .entrySet()
                .stream()
                .sorted((n1, n2) -> Integer.compare(n2.getValue(), n1.getValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (x1, x2) -> x1, LinkedHashMap::new));
    }

    /**
     *
     * @return  Most popular categories in each month
     */
    @Override
    public Map<String, Set<Category>> getMonthsWithMostPopularCategory() {
        return orders
                .stream()
                .collect(groupingBy(toOrderMonthName,
                        collectingAndThen(
                                groupingBy(orderToCategory),
                                groupedByCategory -> groupedByCategory
                                        .entrySet()
                                        .stream()
                                        .collect(groupingBy(
                                                e -> e.getValue().size(),
                                                mapping(Map.Entry::getKey, toSet())
                                        ))
                                        .entrySet()
                                        .stream()
                                        .max(Map.Entry.comparingByKey())
                                        .map(Map.Entry::getValue)
                                        .orElseThrow()
                        )));
    }

    /**
     *
     * @param createOrderDto - data from frontend form
     * @return GetOrderDto type with data about order
     */
    @Override
    public GetOrderDto addOrder(CreateOrderDto createOrderDto) {
        createOrderDtoValidator.validate(createOrderDto);
        var customerEmail = createOrderDto.email();
        var customerEntity = createOrderDto.toCustomerEntity();

        var timestamp = ZonedDateTime.now();
        var validatedTimestamp = validateDateTime(timestamp).toInstant().toString();

        if (customerRepository.findByEmail(customerEmail).isEmpty()) {
            customerRepository.save(customerEntity);
        }

        var customerFromDb = customerRepository
                .findByEmail(customerEmail)
                .orElseThrow();

        if (customerFromDb.isNotTheSameAs(customerEntity)) {
            logger.error("Another name, surname or age is associated with this email");
            throw new IllegalArgumentException("Another data is associated with given email");
        }

        var product = productRepository
                .findById(createOrderDto.productId())
                .orElseThrow(() -> {
                    logger.error("No product with id {} found in database", createOrderDto.productId());
                    return new IllegalStateException("This product does not exist");
                });

        var orderToSave = OrderEntity
                .builder()
                .customer(customerFromDb)
                .product(product)
                .quantity(createOrderDto.quantity())
                .orderDate(validatedTimestamp)
                .build();

        applicationEventPublisher.publishEvent(new AddOrderDto(orderToSave));
        orders.add(orderToSave.toDomain());

        return orderToSave.toGetOrderDto();
    }
}
