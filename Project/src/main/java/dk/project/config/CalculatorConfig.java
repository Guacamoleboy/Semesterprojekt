package dk.project.config;

public class CalculatorConfig {

    // stolper hver anden meter (Angivet i vores PDF)
    private final int poleSpacingCm = 200;
    // max 60 cm mellem spær (Angivet i vores PDF)
    private final int rafterSpacingCm = 60;
    // Rem længde (600 Angivet i vores PDF)
    private final int remLength = 600;

    // _______________________________________________

    public int getPoleSpacingCm() {
        return poleSpacingCm;
    }

    // _______________________________________________

    public int getRafterSpacingCm() {
        return rafterSpacingCm;
    }

    public int getRemLength() {
        return remLength;
    }
}
