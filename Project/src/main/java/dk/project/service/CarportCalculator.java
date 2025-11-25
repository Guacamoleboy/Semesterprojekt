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

    // _____________________________________________________________________
    // Theese can be used to the following, because they have the same size (360 & 540):
    // Sternbredderne (Over og under)
    // vandbrædt


    public int[] calculateUnderStern(double lengthCm) {
        return checkWaste((int) lengthCm, 360, 540);
    }

    public int[] calculateOverStern(double lengthCm) {
        return checkWaste((int) lengthCm, 360, 540);
    }

    public int[] calculateWaterboard(double lengthCm) {
        return checkWaste((int) lengthCm, 360, 540);
    }

    // _____________________________________________________________________

    public int calculateRoofPlate600(double lengthCm, double widthCm) {
        int across = (int) Math.ceil(widthCm / 76);
        int along = (int) (lengthCm / 600);
        return across * along;
    }

    public int calculateRoofPlate360(double lengthCm, double widthCm) {
        int across = (int) Math.ceil(widthCm / 76);
        int remainder = (int) (lengthCm % 600);

        if (remainder == 0) return 0;

        int along360 = remainder <= 360 ? 1 : 0;
        return across * along360;
    }

    // _____________________________________________________________________



    public int[] checkWaste(int targetLength, int small, int large) {

        int Small = 0;
        int Large = 0;
        int Waste = Integer.MAX_VALUE;

        int maxLarge = (targetLength / large) + 3;
        int maxSmall = (targetLength / small) + 3;

        for (int largeCount = 0; largeCount <= maxLarge; largeCount++) {
            for (int smallCount = 0; smallCount <= maxSmall; smallCount++) {

                int total = largeCount * large + smallCount * small;
                if (total < targetLength) continue;

                int waste = total - targetLength;

                if (waste < Waste) {
                    Waste = waste;
                    Small = smallCount;
                    Large = largeCount;
                }
            }
        }

        return new int[]{Small, Large};
    }


    //TODO: Make following:
    // Beslag og skruer

}