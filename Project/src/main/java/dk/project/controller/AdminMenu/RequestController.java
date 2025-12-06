// Package
package dk.project.controller.AdminMenu;

// Imports
import dk.project.entity.CarportOrder;
import dk.project.exception.DatabaseException;
import dk.project.mapper.CarportOrderMapper;
import dk.project.mapper.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestController {

    // Attributes
    private final CarportOrderMapper carportOrderMapper = new CarportOrderMapper();
    private static final RequestController controller = new RequestController();

    // _________________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/getAllRequests", controller::getAllRequests);
        app.post("/searchRequest", controller::searchRequest);

        // ________________________________________________________________________

        app.post("/searchRequest/status", ctx -> {
            int orderId = Integer.parseInt(ctx.formParam("order_id"));
            String status = new OrderMapper().getStatusById(orderId);
            ctx.json(Map.of("order_id", orderId, "status", status));
        });

    }

    // _________________________________________________

    private void getAllRequests(Context ctx) throws DatabaseException {

        List<CarportOrder> orders = carportOrderMapper.getAllOrders();

        ctx.json(orders);

    }

    // _________________________________________________

    private void searchRequest(Context ctx) throws DatabaseException {

        String orderIdParam = ctx.formParam("order_id");
        String name = ctx.formParam("firstname");
        String phone = ctx.formParam("phone");
        String email = ctx.formParam("email");

        Integer orderId = null;
        try {
            if (orderIdParam != null && !orderIdParam.isEmpty()) {
                orderId = Integer.valueOf(orderIdParam.trim());
            }
        } catch (NumberFormatException e) {
            ctx.status(400);
            return;
        }

        List<CarportOrder> result = new ArrayList<>();
        List<CarportOrder> orders = carportOrderMapper.getAllOrders();

        if (orderId != null) {
            for (CarportOrder order : orders) {
                if (order.getId() == orderId) {
                    result.add(order);
                    break;
                }
            }
        } else {
            for (CarportOrder order : orders) {
                boolean matchesName = (name == null || name.isEmpty() || order.getCustomer().getFirstName().equalsIgnoreCase(name));
                boolean matchesPhone = (phone == null || phone.isEmpty() || order.getCustomer().getPhone().equals(phone));
                boolean matchesEmail = (email == null || email.isEmpty() || order.getCustomer().getEmail().equalsIgnoreCase(email));

                if (matchesName && matchesPhone && matchesEmail) {
                    result.add(order);
                }
            }
        }

        ctx.json(result);
    }
}