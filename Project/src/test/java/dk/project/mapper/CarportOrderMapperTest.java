// Package
package dk.project.mapper;

// Imports
import dk.project.db.Database;
import dk.project.entity.CarportOrder;
import dk.project.entity.CarportCategory;
import dk.project.entity.Customer;
import dk.project.exception.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CarportOrderMapperTest {

    // Attributes
    private CarportOrderMapper carportOrderMapper;

    // ____________________________________________________________

    @BeforeAll
    static void beforeAll() {
        Database.setDatabaseName("fog_test");
    }

    // ____________________________________________________________

    @BeforeEach
    void setUp() throws Exception {

        // Initial
        carportOrderMapper = new CarportOrderMapper();

        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement()) {

            // Clear
            stmt.execute("TRUNCATE TABLE carport_orders RESTART IDENTITY CASCADE");
            stmt.execute("TRUNCATE TABLE customers RESTART IDENTITY CASCADE");
            stmt.execute("TRUNCATE TABLE carport_category RESTART IDENTITY CASCADE");

            // Customers
            stmt.execute("""
                INSERT INTO customers (firstname, lastname, email, phone) VALUES
                ('Mads', 'Kristensen', 'mads.kristensen@example.com', '+4522334455'),
                ('Sara', 'Lund', 'sara.lund@example.com', '+4520118899')
            """);

            // Carport Category
            stmt.execute("""
                INSERT INTO carport_category (name) VALUES
                ('Fladt tag'),
                ('Høj rejsning')
            """);

        }

    }

    // ____________________________________________________________

    @Test
    void newOrderAndGetById() throws DatabaseException {

        // Arrange
        Customer customer = new Customer();
        customer.setId(1);

        CarportCategory carportCategory = new CarportCategory();
        carportCategory.setId(1);

        CarportOrder order = new CarportOrder();
        order.setCustomer(customer);
        order.setCategory(carportCategory);
        order.setWidth(500.0);
        order.setLength(600.0);
        order.setHeight(250.0);
        order.setAngle(15.0);
        order.setRoof("Fladt tag");
        order.setHasToolShed(false);
        order.setToolShedWidth(null);
        order.setToolShedLength(null);
        order.setHasTrapez(false);
        order.setCreatedAt(LocalDateTime.now());

        // Act
        carportOrderMapper.newOrder(order);
        CarportOrder carportOrder = carportOrderMapper.getOrderByID(order.getId());

        // Assert
        assertNotNull(carportOrder);
        assertEquals(1, carportOrder.getCustomer().getId());
        assertEquals(1, carportOrder.getCategory().getId());
        assertEquals(500.0, carportOrder.getWidth());
        assertEquals("Fladt tag", carportOrder.getRoof());

    }

    // ____________________________________________________________
    @Test
    void getAllOrders() throws DatabaseException {

        // Arrange
        Customer customer = new Customer();
        customer.setId(1);

        CarportCategory category = new CarportCategory();
        category.setId(1);

        CarportOrder order1 = new CarportOrder();
        order1.setCustomer(customer);
        order1.setCategory(category);
        order1.setWidth(400);
        order1.setLength(500);
        carportOrderMapper.newOrder(order1);

        CarportOrder order2 = new CarportOrder();
        order2.setCustomer(customer);
        order2.setCategory(category);
        order2.setWidth(450);
        order2.setLength(550);
        carportOrderMapper.newOrder(order2);

        // Act
        List<CarportOrder> list = carportOrderMapper.getAllOrders();

        // Assert
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(400, list.get(0).getWidth());
        assertEquals(450, list.get(1).getWidth());

    }

    // ____________________________________________________________

    @Test
    void deleteOrder() throws DatabaseException {

        // Arrange
        Customer customer = new Customer();
        customer.setId(1);

        CarportCategory category = new CarportCategory();
        category.setId(1);

        CarportOrder order = new CarportOrder();
        order.setCustomer(customer);
        order.setCategory(category);
        carportOrderMapper.newOrder(order);

        // Act
        carportOrderMapper.deleteOrder(order.getId());

        // Assert
        assertNull(carportOrderMapper.getOrderByID(order.getId()));

    }

}