package dk.project.service;

import dk.project.config.CalculatorConfig;

public class CarportCalculator {

    // Attributes
    private final CalculatorConfig calculatorConfig = new CalculatorConfig();

    // _____________________________________________________________________

    public int calculatePosts(double length) {
        int postsAlong = (int) (2 * (2 + length / calculatorConfig.getPoleSpacingCm()));
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

    public int[] calculateRoofPlate(double lengthCm, double widthCm) {
        return checkWaste(lengthCm, widthCm, 360, 600);
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

    public int[] checkWaste(double targetLengthCm, double targetWidthCm, int smallCm, int largeCm) {

        int columns = (int) Math.ceil(targetWidthCm / 109.0);

        int smallPerColumn = (int) Math.ceil(targetLengthCm / smallCm);
        int largePerColumn = (int) Math.ceil(targetLengthCm / largeCm);

        int bestSmall = 0;
        int bestLarge = 0;
        int bestWaste = Integer.MAX_VALUE;

        for (int largeColumns = 0; largeColumns <= columns; largeColumns++) {
            for (int smallColumns = 0; smallColumns <= columns; smallColumns++) {

                if (largeColumns + smallColumns != columns) continue;

                int totalLarge = largeColumns * largePerColumn;
                int totalSmall = smallColumns * smallPerColumn;

                int wasteLarge = largeColumns * ((largePerColumn * largeCm) - (int) targetLengthCm);
                int wasteSmall = smallColumns * ((smallPerColumn * smallCm) - (int) targetLengthCm);

                int totalWaste = wasteLarge + wasteSmall;

                if (totalWaste < bestWaste) {
                    bestWaste = totalWaste;
                    bestSmall = totalSmall;
                    bestLarge = totalLarge;
                }
            }
        }

        return new int[]{bestSmall, bestLarge};
    }





    //TODO: Make following:
    // Beslag og skruer

}