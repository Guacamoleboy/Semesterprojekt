// Package
package dk.project.service;

// Imports
import dk.project.DTO.MaterialUsage;
import dk.project.config.CalculatorConfig;
import dk.project.config.MaterialConfig;
import dk.project.entity.AdminMenu.Material;
import dk.project.mapper.AdminMenu.MaterialMapper;
import java.util.ArrayList;
import java.util.List;

public class CarportCalculationService {

    // Attributes
    private final MaterialMapper materialMapper = new MaterialMapper();
    private final CarportCalculator carportCalculator = new CarportCalculator();
    private final CalculatorConfig calculatorConfig = new CalculatorConfig();

    // ___________________________________________________________________________________

    public List<MaterialUsage> calculate(double lengthCm, double widthCm, double heightCm, boolean hasToolShed, String roofType) throws Exception {

        // List of MaterialUsage
        List<MaterialUsage> results = new ArrayList<>();

        // Posts
        int posts = carportCalculator.calculatePosts(lengthCm);
        Material postMat = materialMapper.getMaterialByID(MaterialConfig.ID_STOLPE_300);
        results.add(new MaterialUsage(postMat, posts));

        // Rems
        int rems = carportCalculator.calculateRems(lengthCm);
        Material remMat = materialMapper.getMaterialByID(MaterialConfig.ID_SPAER_600_REM);
        results.add(new MaterialUsage(remMat, rems));

        // Rafters
        int rafters = carportCalculator.calculateRafters(lengthCm);
        Material rafterMat = materialMapper.getMaterialByID(MaterialConfig.ID_SPAER_600_RAFTER);
        results.add(new MaterialUsage(rafterMat, rafters));

        // Under Stern
        int[] under = carportCalculator.calculateUnderStern(lengthCm);
        if (under[0] > 0) {
            Material underMat = materialMapper.getMaterialByID(MaterialConfig.ID_UNDERSTERN_360);
            results.add(new MaterialUsage(underMat, under[0]));
        }
        if (under[1] > 0) {
            Material underMat = materialMapper.getMaterialByID(MaterialConfig.ID_UNDERSTERN_540);
            results.add(new MaterialUsage(underMat, under[1]));
        }

        // Over Stern
        int[] over = carportCalculator.calculateOverStern(lengthCm);
        if (over[0] > 0) {
            Material overMat = materialMapper.getMaterialByID(MaterialConfig.ID_OVERSTERN_360);
            results.add(new MaterialUsage(overMat, over[0]));
        }
        if (over[1] > 0) {
            Material overMat = materialMapper.getMaterialByID(MaterialConfig.ID_OVERSTERN_540);
            results.add(new MaterialUsage(overMat, over[1]));
        }

        // Waterboard
        int[] water = carportCalculator.calculateWaterboard(lengthCm);
        if (water[0] > 0) {
            Material waterMat = materialMapper.getMaterialByID(MaterialConfig.ID_VANDBRAEDT_360);
            results.add(new MaterialUsage(waterMat, water[0]));
        }
        if (water[1] > 0) {
            Material waterMat = materialMapper.getMaterialByID(MaterialConfig.ID_VANDBRAEDT_540);
            results.add(new MaterialUsage(waterMat, water[1]));
        }

        // Roof Plate
        int[] roofPlates = carportCalculator.calculateRoofPlate(lengthCm, widthCm);
        if (roofPlates[0] > 0) {
            Material roofMat = materialMapper.getMaterialByID(MaterialConfig.ID_PLASTMO_360);
            results.add(new MaterialUsage(roofMat, roofPlates[0]));
        }
        if (roofPlates[1] > 0) {
            Material roofMat = materialMapper.getMaterialByID(MaterialConfig.ID_PLASTMO_600);
            results.add(new MaterialUsage(roofMat, roofPlates[1]));
        }

        // Universal Mounts
        int universalMounts = carportCalculator.calculateUniversalMounts(rafters);
        if (universalMounts > 0) results.add(new MaterialUsage(materialMapper.getMaterialByID(MaterialConfig.ID_UNIVERSAL_RIGHT), universalMounts));

        // Screws per mount
        int screwsForMounts = carportCalculator.calculateScrewsForUniversalMounts(rafters);
        int screwPackSize = calculatorConfig.getScrewPackSize();
        int screwPacksForMounts = carportCalculator.calculateScrewPacks(screwsForMounts, screwPackSize);
        if (screwPacksForMounts > 0) {
            results.add(new MaterialUsage(materialMapper.getMaterialByID(MaterialConfig.ID_SKRUER_4_5X60_200), screwPacksForMounts));
        }

        // Brace Screws
        int braceScrews = carportCalculator.calculateBraceBandScrews(rafters);
        int braceScrewPacks = carportCalculator.calculateScrewPacks(braceScrews, screwPackSize);
        if (braceScrewPacks > 0) {
            results.add(new MaterialUsage(materialMapper.getMaterialByID(MaterialConfig.ID_SKRUER_4_5X50_300), braceScrewPacks));
        }

        // Brace Rolls
        int braceRolls = carportCalculator.calculateBraceBandRolls(lengthCm, widthCm);
        if (braceRolls > 0) results.add(new MaterialUsage(materialMapper.getMaterialByID(MaterialConfig.ID_HULBAAND_10M), braceRolls));

        // Bolts
        int bolts = carportCalculator.calculateBolts(posts);
        if (bolts > 0) results.add(new MaterialUsage(materialMapper.getMaterialByID(MaterialConfig.ID_BRAEDDEBOLT_10X120), bolts));

        // Washers
        int washers = carportCalculator.calculateWashers(posts);
        if (washers > 0) results.add(new MaterialUsage(materialMapper.getMaterialByID(MaterialConfig.ID_FIRKANTSKIVER), washers));

        // Roof Screws
        int totalRoofPlates = roofPlates[0] + roofPlates[1];
        int roofScrews = carportCalculator.calculateRoofScrews(totalRoofPlates);
        int roofScrewPacks = carportCalculator.calculateScrewPacks(roofScrews, calculatorConfig.getRoofScrewPackSize());
        if (roofScrewPacks > 0) {
            results.add(new MaterialUsage(materialMapper.getMaterialByID(MaterialConfig.ID_BESLAGSKRUER_4_0X50_250), roofScrewPacks));
        }

        // screwPacks
        int screwPacks = carportCalculator.calculateScrewPacks(roofScrews, calculatorConfig.getRoofScrewPackSize());
        if (screwPacks > 0) results.add(new MaterialUsage(materialMapper.getMaterialByID(MaterialConfig.ID_BUNDSKRUER_200), screwPacks));

        // Final
        return results;

    }

} // CarportCalculationService end