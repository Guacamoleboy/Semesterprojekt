// Package
package dk.project.server;

// Imports
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SvgGenerator {

    // Attributes

    // ________________________________________________________________

    public static String generateTopViewSvg(int length, int width, int rafterAmount, int polesAmount) {

        // Initial final values
        int margin = 50;
        int raftersMargin = 55;
        int overhang = 35;
        double woodWidth = 4.5;
        int pole = 10;
        int poleSpacing = 310;
        int textMarginOne = 90;
        int textMarginTwo = 10;
        int textMarginThree = 40;
        int textMarginFour = 75;
        int bottomTextX = width / 2;

        // __________________________________________________________________

        StringBuilder raftersCalc = new StringBuilder();
        for (int i = 0; i < rafterAmount; i++) {

            double x = (i == rafterAmount - 1) ? width - woodWidth : i * raftersMargin;

            // Rafters
            raftersCalc.append(
                    String.format(
                            "<rect x=\"%s\" y=\"0\" width=\"%s\" height=\"%s\" />\n",
                            x, woodWidth, length
                    )
            );

            // rafterMargin Visual Text
            if (i < 2) {

                double startX = x;
                double endX = x + raftersMargin;
                double arrowY = length + 20;

                // Arrow
                raftersCalc.append(String.format("""
                <line x1="%s" y1="%s" x2="%s" y2="%s"
                    stroke="#000" marker-start="url(#beginArrow)" marker-end="url(#endArrow)" />
                """,
                startX, arrowY, endX, arrowY
                ));

                // Text
                raftersCalc.append(String.format("""
                <text x="%s" y="%s" font-size="20" text-anchor="middle">55</text>
                """,
                (startX + endX) / 2, arrowY - 5
                ));

            }

        }

        // __________________________________________________________________

        StringBuilder polesCalc = new StringBuilder();
        for (int i = 0; i < polesAmount; i++) {
            int x = 110 + i * poleSpacing;

            polesCalc.append(String.format("""
                <rect x="%s" y="32" width="%s" height="%s" />
                <rect x="%s" y="562" width="%s" height="%s" />
                """,
                    x, pole, pole,
                    x, pole, pole
            ));
        }

        // __________________________________________________________________

        return String.format("""
                <svg class="svg-render" width="%s" height="%s" viewBox="0 0 %s %s"
                     preserveAspectRatio="xMinYMin" xmlns="http://www.w3.org/2000/svg"
                     stroke="#000000" fill="none">
            
                    <defs>
                        <marker id="beginArrow" markerWidth="12" markerHeight="12" refX="0" refY="6" orient="auto" fill="#000000">
                            <path d="M0,6 L12,0 L12,12 L0,6" />
                        </marker>
                        <marker id="endArrow" markerWidth="12" markerHeight="12" refX="12" refY="6" orient="auto" fill="#000000">
                            <path d="M0,0 L12,6 L0,12 L0,0 " />
                        </marker>
                    </defs>
            
                    <!-- Pile -->
                    <line class="arrow-visuals" x1="%s" y1="%s" x2="%s" y2="%s"
                          stroke="#000000" marker-start="url(#beginArrow)" marker-end="url(#endArrow)" />
            
                    <line class="arrow-visuals" x1="%s" y1="%s" x2="%s" y2="%s"
                          stroke="#000000" marker-start="url(#beginArrow)" marker-end="url(#endArrow)" />
            
                    <!-- Text -->
                    <text font-size="24" style="text-anchor: middle" transform="translate(30,300) rotate(-90)">%s cm</text>
                    <text font-size="24" style="text-anchor: middle" x="%s" y="%s">%s cm</text>
            
                    <svg x="%s" y="%s" width="%s" height="%s" viewBox="0 0 %s %s" preserveAspectRatio="xMinYMin">
            
                        <!-- Ramme -->
                        <rect x="0" y="0" height="%s" width="%s" stroke="#000000" fill="none" />
            
                        <!-- Remme -->
                        <rect x="0" y="%s" height="%s" width="%s" stroke="#000000" fill="none" />
                        <rect x="0" y="%s" height="%s" width="%s" stroke="#000000" fill="none" />
            
                        <!-- Spær -->
                        %s
            
                        <!-- Last Rafter -->
                        <rect x="%s" y="0" height="%s" width="%s" stroke="#000000" fill="none" />
            
                        <!-- Kryds -->
                        <line class="svg-cross" x1="%s" y1="%s" x2="%s" y2="%s" stroke="#000000" stroke-dasharray="5,5"/>
                        <line class="svg-cross" x1="%s" y1="%s" x2="%s" y2="%s" stroke="#000000" stroke-dasharray="5,5"/>
            
                        <!-- Stolper -->
                        %s
            
                    </svg>
            
                </svg>
                """,

                // OUTER SVG
                width + margin,                          // 1
                length + textMarginOne,                  // 2
                width + margin,                          // 3
                length + textMarginOne,                  // 4

                // ARROW (vertical)
                textMarginThree,                         // 5
                textMarginTwo,                           // 6
                textMarginThree,                         // 7
                length - textMarginThree,                // 8

                // ARROW (horizontal)
                textMarginFour,                          // 9
                length,                                  // 10
                width + textMarginTwo,                   // 11
                length,                                  // 12

                // TEXT LABELS
                length,                                   // 13 left text
                bottomTextX + textMarginThree,            // 14 (bottom text x)
                length + textMarginThree,                 // 15 (bottom text y)
                width,                                    // 16 text content

                // INNER SVG
                textMarginFour,                           // 17 x
                textMarginTwo,                            // 18 y
                width,                                    // 19 width
                length,                                   // 20 height
                width + margin,                           // 21 viewbox
                length + margin,                          // 22 viewbox

                // FRAME
                length,                                   // 23
                width,                                    // 24

                // TOP BEAM
                overhang, woodWidth, width,               // 25,26,27

                // BOTTOM BEAM
                length - overhang, woodWidth, width,      // 28,29,30

                // RAFTERS
                raftersCalc.toString(),                   // 31

                // LAST RAFTER
                (width - woodWidth), length, woodWidth,   // 32,33,34

                // CROSS LINES
                raftersMargin, overhang, length, (length - overhang + woodWidth),   // 35,36,37,38
                raftersMargin, (length - overhang + woodWidth), length, overhang,   // 39,40,41,42

                // POLES
                polesCalc.toString()                      // 43

        );

    }

    // ________________________________________________________________

    public static void saveSvgFile(String svgContent, String fileName) throws IOException {

        /* Path of file */
        Path svgDir = Path.of("src/main/resources/static/pdf/svg");

        /* Validation of directory (Path) */
        if (!Files.exists(svgDir)) {
            Files.createDirectories(svgDir);
        }

        Path file = svgDir.resolve(fileName);

        Files.writeString(file, svgContent);

    }

} // SvgGenerator end