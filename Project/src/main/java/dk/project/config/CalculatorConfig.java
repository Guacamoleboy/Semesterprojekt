// Package
package dk.project.config;

public class CalculatorConfig {

    // Attributes
    private final int poleSpacingCm = 340;
    private final int rafterSpacingCm = 60;
    private final int remLength = 600;
    private final int screwsPerUniversalMount = 6;
    private final int universalMountsPerRafter = 2;
    private final int boltsPerPost = 2;
    private final int washersPerPost = 2;
    private final int screwsPerRoofPlate = 50;
    private final int roofScrewPackSize = 200;
    private final double braceBandScrewsPerRafter = 1.5;
    private final double braceBandRollLengthM = 10.0;
    private final int screwPackSize = 200;

    // _______________________________________________

    public int getScrewPackSize() {
        return screwPackSize;
    }

    // _______________________________________________

    public int getPoleSpacingCm() {
        return poleSpacingCm;
    }

    // _______________________________________________

    public int getRafterSpacingCm() {
        return rafterSpacingCm;
    }

    // _______________________________________________

    public int getRemLength() {
        return remLength;
    }

    // _______________________________________________

    public int getScrewsPerUniversalMount() {
        return screwsPerUniversalMount;
    }

    // _______________________________________________

    public int getUniversalMountsPerRafter() {
        return universalMountsPerRafter;
    }

    // _______________________________________________

    public double getBraceBandScrewsPerRafter() {
        return braceBandScrewsPerRafter;
    }

    // _______________________________________________

    public double getBraceBandRollLengthM() {
        return braceBandRollLengthM;
    }

    // _______________________________________________

    public int getScrewsPerRoofPlate() {
        return screwsPerRoofPlate;
    }

    // _______________________________________________

    public int getRoofScrewPackSize() {
        return roofScrewPackSize;
    }

    // _______________________________________________

    public int getBoltsPerPost() {
        return boltsPerPost;
    }

    // _______________________________________________

    public int getWashersPerPost() {
        return washersPerPost;
    }

} // CalculatorConfig end