// Package
package dk.project.controller.Modtag;

// Imports
import dk.project.entity.CarportCategory;
import dk.project.entity.CarportOrder;
import dk.project.entity.Customer;
import dk.project.entity.Order;
import dk.project.mapper.CarportCategoryMapper;
import dk.project.mapper.CarportOrderMapper;
import dk.project.mapper.CustomerMapper;
import dk.project.mapper.OrderMapper;
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;
import java.util.Map;

public class ModtagController {

    // Attributes

    // __________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/beregn/modtag", ctx -> ctx.html(ThymeleafSetup.render("modtag.html", null)));

        // _____________________________________________________________________

        app.post("/generate-offer", ctx -> {

            // TODO | change from ChatGPT to our actual solution
            // TODO | Works now, but I don't fully understand it.
            // TODO | - Jonas

            try {

                var json = ctx.bodyAsClass(Map.class);

                CustomerMapper customerMapper = new CustomerMapper();
                String email = (String) json.get("email");
                Customer customer = customerMapper.getCustomerByEmail(email);

                if (customer == null) {
                    customer = new Customer();
                    customer.setFirstName((String) json.get("firstname"));
                    customer.setLastName((String) json.get("lastname"));
                    customer.setEmail(email);
                    customer.setPhone((String) json.get("phone"));
                    customerMapper.newCustomer(customer);
                }

                CarportCategoryMapper categoryMapper = new CarportCategoryMapper();
                CarportCategory category = categoryMapper.getFirstCategory();
                CarportOrder carportOrder = new CarportOrder(
                        0, customer, category,
                        ((Number) json.get("width")).doubleValue(),
                        ((Number) json.get("length")).doubleValue(),
                        ((Number) json.get("height")).doubleValue(),
                        ((Number) json.get("angle")).doubleValue(),
                        (String) json.get("roof"),
                        (Boolean) json.get("hasToolShed"),
                        ((Number) json.get("toolShedWidth")).doubleValue(),
                        ((Number) json.get("toolShedLength")).doubleValue(),
                        (Boolean) json.get("hasTrapez"),
                        java.time.LocalDateTime.now()
                );

                CarportOrderMapper carportOrderMapper = new CarportOrderMapper();
                carportOrderMapper.newOrder(carportOrder);

                Order order = new Order(
                        0, customer, carportOrder, 0.0, "pending",
                        java.time.LocalDateTime.now(), null
                );

                OrderMapper orderMapper = new OrderMapper();
                int orderId = orderMapper.newOrder(order);
                ctx.json(Map.of("success", true, "orderId", orderId));
            } catch (Exception e) {
                e.printStackTrace();
                ctx.json(Map.of("success", false, "error", e.getMessage()));
            }
        });

    }

} // ModtagController end