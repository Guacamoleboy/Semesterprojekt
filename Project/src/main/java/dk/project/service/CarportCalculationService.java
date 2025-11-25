package dk.project.service;

import dk.project.DTO.MaterialUsage;
import dk.project.config.MaterialConfig;
import dk.project.entity.AdminMenu.Material;
import dk.project.mapper.AdminMenu.MaterialMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarportCalculationService {

    // Attributes
    private final MaterialMapper materialMapper = new MaterialMapper();
    private final CarportCalculator carportCalculator = new CarportCalculator();

    // ___________________________________________________________________________________

    public List<MaterialUsage> calculate(double lengthCm, double widthCm, double heightCm, boolean hasToolShed, String roofType) throws Exception {

        List<MaterialUsage> results = new ArrayList<>();

        // Stolper
        int posts = carportCalculator.calculatePosts(lengthCm);
        Material postMat = materialMapper.getMaterialByID(MaterialConfig.ID_STOLPE_300);
        results.add(new MaterialUsage(postMat, posts));

        // Remme
        int rems = carportCalculator.calculateRems(lengthCm);
        Material remMat = materialMapper.getMaterialByID(MaterialConfig.ID_SPAER_600_REM);
        results.add(new MaterialUsage(remMat, rems));

        // Spær
        int rafters = carportCalculator.calculateRafters(lengthCm);
        Material rafterMat = materialMapper.getMaterialByID(MaterialConfig.ID_SPAER_600_RAFTER);
        results.add(new MaterialUsage(rafterMat, rafters));

        // Sternbredderne (Over og under)
        int[] under = carportCalculator.calculateUnderStern(lengthCm);
        if (under[0] > 0) {
            Material underMat = materialMapper.getMaterialByID(MaterialConfig.ID_UNDERSTERN_360);
            results.add(new MaterialUsage(underMat, under[0]));
        }

        if (under[1] > 0) {
            Material underMat = materialMapper.getMaterialByID(MaterialConfig.ID_UNDERSTERN_540);
            results.add(new MaterialUsage(underMat, under[1]));
        }


        int[] over = carportCalculator.calculateOverStern(lengthCm);
        if (over[0] > 0) {
            Material overMat = materialMapper.getMaterialByID(MaterialConfig.ID_OVERSTERN_360);
            results.add(new MaterialUsage(overMat, over[0]));
        }

        if (over[1] > 0) {
            Material overMat = materialMapper.getMaterialByID(MaterialConfig.ID_OVERSTERN_540);
            results.add(new MaterialUsage(overMat, over[1]));
        }


        int[] water = carportCalculator.calculateWaterboard(lengthCm);
        if (water[0] > 0) {
            Material waterMat = materialMapper.getMaterialByID(MaterialConfig.ID_VANDBRAEDT_360);
            results.add(new MaterialUsage(waterMat, water[0]));
        }

        if (water[1] > 0) {
            Material waterMat = materialMapper.getMaterialByID(MaterialConfig.ID_VANDBRAEDT_540);
            results.add(new MaterialUsage(waterMat, water[1]));
        }

        // Tag
        int roofPlates = carportCalculator.calculateRoofPlate600(lengthCm, widthCm);
        Material roofPlatesMat = materialMapper.getMaterialByID(MaterialConfig.ID_PLASTMO_600);
        results.add(new MaterialUsage(roofPlatesMat, roofPlates));




        return results;
    }

}