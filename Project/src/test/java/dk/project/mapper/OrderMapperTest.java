// Package
package dk.project.mapper;

// Imports
import dk.project.db.Database;
import dk.project.entity.*;
import dk.project.entity.Order;
import dk.project.exception.DatabaseException;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    // Attributes
    private OrderMapper orderMapper;
    private CustomerMapper customerMapper;
    private CarportOrderMapper carportOrderMapper;

    // ______________________________________________________

    @BeforeAll
    static void beforeAll() {
        Database.setDatabaseName("fog_test");
    }

    // ______________________________________________________

    @BeforeEach
    void setUp() throws Exception {

        // Initial
        orderMapper = new OrderMapper();
        customerMapper = new CustomerMapper();
        carportOrderMapper = new CarportOrderMapper();

        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement()) {

            // Clear
            stmt.execute("TRUNCATE TABLE orders, carport_orders, carport_category, customers RESTART IDENTITY CASCADE");

            // Customers
            stmt.execute("""
            INSERT INTO customers (firstname, lastname, email, phone, street, city, zipcode, country) VALUES
            ('Jonas', 'Guacamole', 'jonas@test.com', '12345678', 'Street 1', 'City', '1000', 'Denmark')
            """);

            // Carport Category
            stmt.execute("""
            INSERT INTO carport_category (name) VALUES
            ('Standard Carport')
            """);

            // Carport Orders
            stmt.execute("""
            INSERT INTO carport_orders (customer_id, carport_category_id, width, length, height, created_at)
            VALUES (1, 1, 300, 500, 250, NOW())
            """);

        }

    }

    // ______________________________________________________

    @Test
    void testNewOrderCreatesOrder() throws DatabaseException {

        // Arrange
        Customer customer = customerMapper.getCustomerByID(1);
        CarportOrder carportOrder = carportOrderMapper.getOrderByID(1);
        Order order = new Order(0, customer, carportOrder, 10000.0, "pending", LocalDateTime.now(), null);

        // Act
        int orderId = orderMapper.newOrder(order);
        Order createdOrder = orderMapper.getById(orderId);

        // Assert
        assertNotNull(createdOrder, "Order should be created");
        assertEquals(orderId, createdOrder.getId());
        assertEquals("pending", createdOrder.getStatus());
        assertEquals(10000.0, createdOrder.getTotalPrice());
        assertNotNull(createdOrder.getCarportOrder());

    }

    // ______________________________________________________

    @Test
    void testGetStatusById() throws DatabaseException {

        // Arrange
        Customer customer = customerMapper.getCustomerByID(1);
        CarportOrder carportOrder = carportOrderMapper.getOrderByID(1);
        Order order = new Order(0, customer, carportOrder, 5000.0, "pending", LocalDateTime.now(), null);

        // Act
        int orderId = orderMapper.newOrder(order);
        String status = orderMapper.getStatusById(orderId);

        // Assert
        assertEquals("pending", status);

    }

    // ______________________________________________________

    @Test
    void testUpdateStatus() throws DatabaseException {

        // Arrange
        Customer customer = customerMapper.getCustomerByID(1);
        CarportOrder carportOrder = carportOrderMapper.getOrderByID(1);
        Order order = new Order(0, customer, carportOrder, 7000.0, "pending", LocalDateTime.now(), null);

        // Act
        int orderId = orderMapper.newOrder(order);
        orderMapper.updateStatus(orderId, "accepted");
        String updatedStatus = orderMapper.getStatusById(orderId);

        // Assert
        assertEquals("accepted", updatedStatus);

    }

    // ______________________________________________________

    @Test
    void testUpdateTotalPrice() throws DatabaseException {

        // Arrange
        Customer customer = customerMapper.getCustomerByID(1);
        CarportOrder carportOrder = carportOrderMapper.getOrderByID(1);
        Order order = new Order(0, customer, carportOrder, 7000.0, "pending", LocalDateTime.now(), null);

        // Act
        int orderId = orderMapper.newOrder(order);
        orderMapper.updateTotalPrice(orderId, 8500.0);
        Order updatedOrder = orderMapper.getById(orderId);

        // Assert
        assertEquals(8500.0, updatedOrder.getTotalPrice());

    }

}