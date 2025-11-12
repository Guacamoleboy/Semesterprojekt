package dk.project.mapper;

import dk.project.entity.AdminMenu.Product;
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
