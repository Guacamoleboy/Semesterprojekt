package dk.project.mapper;

import dk.project.db.Database;
import dk.project.entity.Customer;
import dk.project.exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    public Customer newCustomer(Customer customer) throws DatabaseException {

        String sql = "INSERT INTO customers (firstname, lastname, email, phone, street, city, zipcode, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getStreet());
            stmt.setString(6, customer.getCity());
            stmt.setString(7, customer.getZipcode());
            stmt.setString(8, customer.getCountry());

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    customer.setId(rs.getInt("id"));

                }

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved oprettelse af kunde", e);

        }

        return null;

    }

    // _____________________________________________________________________

    public void deleteCustomer(int id) {

        String sql = "DELETE FROM customers WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows == 0) {

                throw new SQLException("Ingen kunde fundet med ID: " + id);

            }
        } catch (SQLException e) {

            throw new RuntimeException("Fejl ved sletning af kunde med ID " + id, e);

        }
    }

    // _____________________________________________________________________

    public void updateCustomer(Customer customer) throws DatabaseException {
        String sql = "UPDATE customers SET firstname = ?, lastname = ?, email = ?, phone = ?, street = ?, city = ?, zipcode = ?, country = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getStreet());
            stmt.setString(6, customer.getCity());
            stmt.setString(7, customer.getZipcode());
            stmt.setString(8, customer.getCountry());
            stmt.setInt(9, customer.getId());

            int rows = stmt.executeUpdate();

            if (rows == 0) {

                throw new SQLException("Materiale ikke fundet med ID: " + customer.getId());

            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved opdatering af kunde", e);
        }
    }

    // _____________________________________________________________________

    public List<Customer> getAllCustomers() throws DatabaseException {

        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY id";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(toCustomer(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af kunder", e);

        }

        return customers;

    }

    // _____________________________________________________________________

    public Customer getCustomerByID(int id) throws DatabaseException {

        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return toCustomer(rs);

                }

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af kunde", e);

        }

        return null;
    }

    // _____________________________________________________________________

    public List<Customer> getCustomerByName(String name) throws DatabaseException {

        List<Customer> customers = new ArrayList<>();
        //TODO: Skal laves

        return customers;
    }

    // _____________________________________________________________________

    private Customer toCustomer(ResultSet rs) throws SQLException {

        return new Customer(
                rs.getInt("id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("zipcode"),
                rs.getString("country"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
