package dk.project.service;

import dk.project.DTO.MaterialUsage;
import dk.project.config.MaterialConfig;
import dk.project.entity.AdminMenu.Material;
import dk.project.mapper.AdminMenu.MaterialMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarportCalculationService {

    private final MaterialMapper materialMapper = new MaterialMapper();
    private final CarportCalculator carportCalculator = new CarportCalculator();


    public List<MaterialUsage> calculate(double lengthCm, double widthCm, double heightCm, boolean hasToolShed, String roofType) throws Exception {

        List<MaterialUsage> results = new ArrayList<>();


        // Stolper
        int posts = carportCalculator.calculatePosts(lengthCm);
        Material postMat = materialMapper.getMaterialByID(MaterialConfig.ID_STOLPE_300);
        results.add(new MaterialUsage(postMat, posts));


        // Remme
        int rems = carportCalculator.calculateRems(lengthCm);
        Material remMat = materialMapper.getMaterialByID(MaterialConfig.ID_SPAER_600);
        results.add(new MaterialUsage(remMat, rems));


        // Spær
        int rafters = carportCalculator.calculateRafters(lengthCm);
        Material rafterMat = materialMapper.getMaterialByID(MaterialConfig.ID_SPAER_600);
        results.add(new MaterialUsage(rafterMat, rafters));


        return results;
    }

}
