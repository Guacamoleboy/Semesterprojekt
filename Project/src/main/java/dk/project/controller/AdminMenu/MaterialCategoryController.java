// Package
package dk.project.controller.AdminMenu;

// Imports
import dk.project.mapper.AdminMenu.MaterialCategoryMapper;
import io.javalin.Javalin;
import dk.project.entity.AdminMenu.MaterialCategory;
import dk.project.exception.DatabaseException;
import io.javalin.http.Context;
import java.util.List;

public class MaterialCategoryController {

    // Attributes
    private final MaterialCategoryMapper categoryMapper = new MaterialCategoryMapper();
    private static final MaterialCategoryController controller = new MaterialCategoryController();

    // _______________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/getCategories", controller::getCategories);

    }

    // _______________________________________________

    private void getCategories(Context ctx) throws DatabaseException {

        List<MaterialCategory> categories = categoryMapper.getCategories();
        ctx.json(categories);

    }

}