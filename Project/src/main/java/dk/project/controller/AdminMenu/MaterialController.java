package dk.project.controller.AdminMenu;

import dk.project.DTO.MaterialDTO;
import dk.project.entity.AdminMenu.MaterialCategory;
import dk.project.entity.AdminMenu.Material;
import dk.project.exception.DatabaseException;
import dk.project.mapper.AdminMenu.MaterialCategoryMapper;
import dk.project.mapper.AdminMenu.MaterialMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class MaterialController {

    private static final MaterialController controller = new MaterialController();
    private final MaterialMapper materialMapper = new MaterialMapper();
    private final MaterialCategoryMapper categoryMapper = new MaterialCategoryMapper();

    // _______________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/getMaterials", controller::getAllMaterials);
        app.post("/searchMaterials", controller::searchMaterial);
        app.post("/updateMaterial", controller::updateMaterial);
        app.post("/addMaterial", controller::addMaterial);
        app.post("/deleteMaterial", controller::deleteMaterial);

    }

    // _______________________________________________

    private void getAllMaterials(Context ctx) throws DatabaseException {

        List<Material> materials = materialMapper.getMaterials();
        List<MaterialCategory> categories = categoryMapper.getCategories();

        List<MaterialDTO> DTOMaterialList = new ArrayList<>();

        for (Material material : materials) {
            String categoryName = categories.stream()
            .filter(c -> c.getId() == material.getCategory_id())
            .map(MaterialCategory::getName)
            .findFirst()
            .orElse("Ukendt");

            DTOMaterialList.add(new MaterialDTO(material, categoryName));
        }

        ctx.json(DTOMaterialList);

    }

    // _______________________________________________

    private void searchMaterial(Context ctx) throws DatabaseException {

        String idParam = ctx.formParam("serialnumber");
        String name = ctx.formParam("name");
        String categoryIdParam = ctx.formParam("category_id");

        Integer id = null;
        try {

            if (idParam != null && !idParam.isEmpty()) {
                id = Integer.valueOf(idParam.trim());
            }

        } catch (NumberFormatException e) {

            //TODO: Notification - Forkert input (Skal være tal)!!
            ctx.status(400);

        }

        List<MaterialDTO> result = new ArrayList<>();
        List<MaterialCategory> categories = categoryMapper.getCategories();

        if (id != null) {

            Material m = materialMapper.getMaterialByID(id);
            if (m != null) {

                String categoryName = getMaterialCategoryName(m, categories);
                result.add(new MaterialDTO(m, categoryName));

            }

        } else {

            List<Material> materials = materialMapper.getMaterials();
            Integer categoryId = null;

            try {

                if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
                    categoryId = Integer.valueOf(categoryIdParam.trim());
                }

            } catch (NumberFormatException ignored) {}

            for (Material m : materials) {

                boolean matchesName = (name == null || name.isEmpty() || m.getName().toLowerCase().contains(name.toLowerCase()));
                boolean matchesCategory = (categoryId == null || m.getCategory_id() == categoryId);

                if (matchesName && matchesCategory) {
                    String categoryName = getMaterialCategoryName(m, categories);
                    result.add(new MaterialDTO(m, categoryName));
                }

            }
        }

        ctx.json(result);
    }

    // _______________________________________________

    private void addMaterial(Context ctx) throws DatabaseException {

        String name = ctx.formParam("name");
        String description = ctx.formParam("description");
        String unit = ctx.formParam("unit");
        String stringLength = ctx.formParam("length");
        String stringWidth = ctx.formParam("width");
        String stringHeight = ctx.formParam("height");
        String stringPrice = ctx.formParam("price");
        String stringCategoryId = ctx.formParam("category_id");

        if (name == null || name.isEmpty() ||
        description == null || description.isEmpty() ||
        unit == null || unit.isEmpty() ||
        stringPrice == null || stringPrice.isEmpty() ||
        stringCategoryId == null || stringCategoryId.isEmpty()) {

            //TODO: Notification!!
            ctx.status(400);
            return;
        }

        try {

            Integer length = (stringLength == null || stringLength.isEmpty()) ? null : Integer.parseInt(stringLength);
            Integer width = (stringWidth == null || stringWidth.isEmpty()) ? null : Integer.parseInt(stringWidth);
            Integer height = (stringHeight == null || stringHeight.isEmpty()) ? null : Integer.parseInt(stringHeight);
            double price = Double.parseDouble(stringPrice);
            int categoryId = Integer.parseInt(stringCategoryId);

            Material material = new Material(categoryId, name, description, unit, length, width, height, price);
            materialMapper.newMaterial(material);

            ctx.status(200).json(material);

        } catch (NumberFormatException e) {

            //TODO: Notification - Forkert input (Skal være tal)!!
            ctx.status(400);
        }
    }

    // _______________________________________________

    private void updateMaterial(Context ctx) throws DatabaseException {

        String idParam = ctx.formParam("id");
        String name = ctx.formParam("name");
        String description = ctx.formParam("description");
        String unit = ctx.formParam("unit");
        String stringLength = ctx.formParam("length");
        String stringWidth = ctx.formParam("width");
        String stringHeight = ctx.formParam("height");
        String stringPrice = ctx.formParam("price");
        String stringCategoryId = ctx.formParam("category_id");

        if (idParam == null || idParam.isEmpty()) {

            //TODO: Notification - ID er påkrævet!
            ctx.status(400);
            return;

        }

        try {

            int id = Integer.parseInt(idParam);
            Integer length = (stringLength == null || stringLength.isEmpty()) ? null : Integer.parseInt(stringLength);
            Integer width = (stringWidth == null || stringWidth.isEmpty()) ? null : Integer.parseInt(stringWidth);
            Integer height = (stringHeight == null || stringHeight.isEmpty()) ? null : Integer.parseInt(stringHeight);
            double price = Double.parseDouble(stringPrice);
            int categoryId = Integer.parseInt(stringCategoryId);

            Material material = materialMapper.getMaterialByID(id);

            if (material == null) {

                //TODO: Notification - Materiale ikke fundet!
                ctx.status(404);
                return;

            }

            material.setName(name);
            material.setDescription(description);
            material.setUnit(unit);
            material.setLength(length);
            material.setWidth(width);
            material.setHeight(height);
            material.setPrice(price);
            material.setCategory_id(categoryId);

            materialMapper.updateMaterial(material);
            ctx.status(200).json(material);

        } catch (NumberFormatException e) {

            //TODO: Notification - Skal være et tal (Forkert input!)!
            ctx.status(400);

        }
    }

    // _______________________________________________

    private void deleteMaterial(Context ctx) {

        String idParam = ctx.formParam("id");

        try {

            if (idParam == null || idParam.isEmpty()) {

                //TODO: Notification - ID er påkrævet!
                ctx.status(400);
                return;

            }

            int id = Integer.parseInt(idParam);

            materialMapper.deleteMaterial(id);

        } catch (NumberFormatException e) {

            //TODO: Notification - Forkert input - Skal være et tal!
            ctx.status(400);

        }
    }

    // _______________________________________________


    private String getMaterialCategoryName(Material material, List<MaterialCategory> categories) {

        return categories.stream()
        .filter(c -> c.getId() == material.getCategory_id())
        .map(MaterialCategory::getName)
        .findFirst()
        .orElse("Ukendt");

    }
    
}
