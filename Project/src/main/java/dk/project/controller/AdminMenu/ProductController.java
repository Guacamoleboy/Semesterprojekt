package dk.project.controller.AdminMenu;

import dk.project.entity.AdminMenu.Product;
import dk.project.exception.DatabaseException;
import dk.project.mapper.AdminMenu.ProductMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {

    private static final ProductController controller = new ProductController();
    private final ProductMapper productMapper = new ProductMapper();

    // _______________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/getProducts", controller::getAllProducts);
        app.post("/searchProducts", controller::searchProducts);
        app.post("/updateProduct", controller::updateProduct);
        app.post("/addProduct", controller::addProduct);
        app.post("/deleteProduct", controller::deleteProduct);

    }

    // _______________________________________________

    private void getAllProducts(Context ctx) throws DatabaseException {

        ctx.json(productMapper.getProducts());

    }

    // _______________________________________________

    private void searchProducts(Context ctx) throws DatabaseException {
        Product search = ctx.bodyAsClass(Product.class);

        List<Product> result = productMapper.getProducts().stream()

                .filter(p -> search.getId() <= 0 || String.valueOf(p.getId()).contains(String.valueOf(search.getId())))


                .filter(p -> search.getTitle() == null || search.getTitle().isEmpty()
                        || p.getTitle().toLowerCase().contains(search.getTitle().toLowerCase()))


                .filter(p -> search.getSize() == null || search.getSize().isEmpty()
                        || p.getSize().toLowerCase().contains(search.getSize().toLowerCase()))

                .collect(Collectors.toList());

        ctx.json(result);
    }

    // _______________________________________________

    private void addProduct(Context ctx) throws DatabaseException {

        String title = ctx.formParam("title");
        String description = ctx.formParam("description");
        String size = ctx.formParam("size");
        String stringUnitPrice = ctx.formParam("price");

        if (stringUnitPrice == null){

            //TODO: Lav notification!
            return;

        }

        try {
            double unitPrice = Double.parseDouble(stringUnitPrice);
            Product product = new Product();
            product.setTitle(title);
            product.setDescription(description);
            product.setSize(size);
            product.setUnitPrice(unitPrice);

            productMapper.newProduct(product);
            ctx.status(200);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    // _______________________________________________

    private void updateProduct(Context ctx) throws DatabaseException {

        String id = ctx.formParam("id");
        String title = ctx.formParam("title");
        String size = ctx.formParam("size");
        String stringUnitPrice = ctx.formParam("unitPrice");

        if (id == null || stringUnitPrice == null){

            //TODO: Lav notification!
            return;

        }

        try {

            int productID = Integer.parseInt(id);
            double unitPrice = Double.parseDouble(stringUnitPrice);
            Product product = productMapper.getProductByID(productID);
            System.out.println(productID + " " + title + " " +size+ " " +unitPrice);

            /*

            product.setTitle(title);
            product.setSize(size);
            product.setUnitPrice(unitPrice);

            //TODO: Vi skal lige have lavet denne metode!
            productMapper.updateProduct(product);

            */

            ctx.status(200);

        } catch (NumberFormatException e) {

            ctx.status(400).result("Skal være et tal!");

        } catch (Exception e) {

            ctx.status(500).result("Fejl sletning af produkt!");

        }

    }

    // _______________________________________________

    private void deleteProduct(Context ctx) {

        String id = ctx.formParam("id");
        try {

            if (id == null) {

                return;

            }

            int productID = Integer.parseInt(id);

            System.out.println(productID);
            //TODO: Skal lige koble backend på!

        } catch (NumberFormatException e) {

            ctx.status(400).result("Skal være et tal!");

        } catch (Exception e) {

            ctx.status(500).result("Fejl sletning af produkt!");

        }

    }

}
