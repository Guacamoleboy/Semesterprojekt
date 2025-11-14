package dk.project.mapper.AdminMenu;

import dk.project.entity.AdminMenu.Product;
import dk.project.entity.User;
import dk.project.exception.DatabaseException;
import java.util.ArrayList;
import java.util.List;
import dk.project.db.Database;
import java.sql.*;

public class ProductMapper {

    public List<Product> getProducts() throws DatabaseException {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = Database.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Product p = toProduct(rs);
                products.add(p);

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af produkter", e);
            
        }

        return products;
    }

    // _____________________________________________________________________

    public void newProduct(Product product) throws DatabaseException {

        String sql = "INSERT INTO products (title, description, size, quantity, price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getTitle());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getSize());
            stmt.setInt(4, product.getQuantity());
            stmt.setDouble(5, product.getUnitPrice());
            stmt.executeUpdate();


            try (ResultSet keys = stmt.getGeneratedKeys()) {

                if (keys.next()) {

                    product.setId(keys.getInt(1));

                }
            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved oprettelse af product", e);

        }
    }

    // ________________________________________________________________________________

    public Product getProductByID(int id) throws DatabaseException {
        String sql = "SELECT * FROM products where id = ?";

        try (Connection conn = Database.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            stmt.setInt(1, id);

            while (rs.next()) {

                return toProduct(rs);

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af produkter", e);

        }
        return null;
    }

    // ________________________________________________________________________________

    public Product toProduct(ResultSet rs) throws SQLException {

        return new Product(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("size"),
                rs.getInt("quantity"),
                rs.getDouble("price")
        );

    }


}
