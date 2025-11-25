package dk.project.config;

public class CalculatorConfig {

    // Attributes
    private final int poleSpacingCm = 200;
    private final int rafterSpacingCm = 60;
    private final int remLength = 600;

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

}