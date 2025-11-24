package dk.project.service;

import dk.project.config.CalculatorConfig;

public class CarportCalculator {

    // Attributes
    private final CalculatorConfig calculatorConfig = new CalculatorConfig();

    // _____________________________________________________________________

    public int calculatePosts(double length) {
        int postsAlong = (int) (2 * (2 + length / 340));
        return postsAlong * 2;
    }

    // _____________________________________________________________________

    public int calculateRafters(double length) {
        return (int) Math.ceil(length / calculatorConfig.getRafterSpacingCm());
    }

    // _____________________________________________________________________

    public int calculateRems(double length) {
        int perRem = (int) Math.ceil(length / calculatorConfig.getRemLength());
        return perRem * 2;
    }

    //TODO: Make following:
    // Sternbredderne (Over og under)
    // vandbrædt
    // Tagplader
    // Beslag og skruer

}