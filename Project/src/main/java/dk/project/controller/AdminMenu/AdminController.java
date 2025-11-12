package dk.project.controller.AdminMenu;

import dk.project.entity.AdminMenu.Product;
import dk.project.entity.User;
import dk.project.exception.DatabaseException;
import dk.project.mapper.ProductMapper;
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminController {

    ProductMapper productMapper = new ProductMapper();

    public static void registerRoutes(Javalin app) {

        AdminController controller = new AdminController();

        app.get("/menu", controller::displayMenu);
        app.post("/getProducts", controller::getAllProducts);
        app.post("/searchProducts", controller::searchProducts);
    }

    // _______________________________________________

    public void getAllProducts(Context ctx) throws DatabaseException {

        ctx.json(productMapper.getProducts());

    }

    // _______________________________________________

    public void searchProducts(Context ctx) throws DatabaseException {
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

    private void displayMenu(Context ctx) {
        User user = ctx.sessionAttribute("user");
        if (user == null) {
            ctx.redirect("/loginPage?error=notLoggedIn");
            return;
        }

        ctx.html(ThymeleafSetup.render("adminMenu.html", Map.of("user", user)));

    }
}
