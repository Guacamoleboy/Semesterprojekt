// Package
package dk.project.mapper;

// Imports
import dk.project.db.Database;
import dk.project.entity.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    // Attributes
    private CustomerMapper customerMapper;

    // _______________________________________________________

    @BeforeAll
    static void beforeAll() {
        Database.setDatabaseName("fog_test");
    }

    // _______________________________________________________

    @BeforeEach
    void setUp() throws Exception {

        // Initial
        customerMapper = new CustomerMapper();

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {

            // Clear
            stmt.execute("TRUNCATE TABLE customers RESTART IDENTITY CASCADE");

            // Query
            stmt.execute("""
                INSERT INTO customers (firstname, lastname, email, phone, street, city, zipcode, country) VALUES
                ('Jonas', 'Guacamole', 'jonas@test.com', '12345678', 'Street 1', 'City', '1000', 'Denmark'),
                ('Andreas', 'Rovelt', 'andreas@test.com', '87654321', 'Street 2', 'City', '2000', 'Denmark')
            """);

        }

    }

    // _______________________________________________________

    @Test
    void getAllCustomers() throws Exception {

        // Act
        List<Customer> customers = customerMapper.getAllCustomers();

        // Assert
        assertNotNull(customers);
        assertEquals(2, customers.size());
        assertEquals("Jonas", customers.get(0).getFirstName());
        assertEquals("Andreas", customers.get(1).getFirstName());

    }

    // _______________________________________________________

    @Test
    void getCustomerByID() throws Exception {

        // Arrange
        int existingId = 1;
        int nonExistingId = 99;

        // Act
        Customer customer = customerMapper.getCustomerByID(existingId);
        Customer missing = customerMapper.getCustomerByID(nonExistingId);

        // Assert
        assertNotNull(customer);
        assertEquals("Jonas", customer.getFirstName());
        assertEquals("Guacamole", customer.getLastName());
        assertNull(missing);

    }

    // _______________________________________________________

    @Test
    void getCustomerByEmail() throws Exception {

        // Arrange
        String existingEmail = "andreas@test.com";
        String missingEmail = "doesnotexist@test.com";

        // Act
        Customer customer = customerMapper.getCustomerByEmail(existingEmail);
        Customer missing = customerMapper.getCustomerByEmail(missingEmail);

        // Assert
        assertNotNull(customer);
        assertEquals("Andreas", customer.getFirstName());
        assertEquals("Rovelt", customer.getLastName());
        assertNull(missing);

    }

    // _______________________________________________________

    @Test
    void updateCustomer() throws Exception {

        // Arrange
        Customer customer = customerMapper.getCustomerByID(1);
        customer.setFirstName("JonasUpdated");

        // Act
        customerMapper.updateCustomer(customer);
        Customer updated = customerMapper.getCustomerByID(1);

        // Assert
        assertEquals("JonasUpdated", updated.getFirstName());

    }

    // _______________________________________________________

    @Test
    void deleteCustomer() throws Exception {

        // Arrange
        int existingId = 2;

        // Act
        customerMapper.deleteCustomer(existingId);

        // Assert
        assertNull(customerMapper.getCustomerByID(existingId));

    }

}