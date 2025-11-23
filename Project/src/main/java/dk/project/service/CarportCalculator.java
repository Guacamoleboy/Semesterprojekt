package dk.project.service;

import dk.project.config.CalculatorConfig;

public class CarportCalculator {

    private final CalculatorConfig config = new CalculatorConfig();

    // _____________________________________________________________________

    public int calculatePosts(double length) {
        int postsAlong = (int) (2 * (2 + length / 340));
        return postsAlong * 2;
    }

    // _____________________________________________________________________

    public int calculateRafters(double length) {
        return (int) Math.ceil(length / config.getRafterSpacingCm());
    }

    // _____________________________________________________________________

    public int calculateRems(double length) {
        int perRem = (int) Math.ceil(length / config.getRemLength());
        return perRem * 2;
    }

    //TODO: Vi skal lave følgende:
    // Sternbredderne (Over og under)
    // vandbrædt
    // Tagplader
    // Beslag og skruer
}
