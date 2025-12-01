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

        // __________________________________________________________________

        StringBuilder raftersCalc = new StringBuilder();
        for (int i = 0; i < rafterAmount; i++) {

            double x = (i == rafterAmount - 1) ? width - woodWidth : i * raftersMargin;

            raftersCalc.append(
                    String.format(
                            "<rect x=\"%s\" y=\"0\" width=\"%s\" height=\"%s\" />\n",
                            x, woodWidth, length
                    )
            );
        }

        // __________________________________________________________________

        StringBuilder polesCalc = new StringBuilder();
        for (int i = 0; i < polesAmount; i++) {
            int x = i * poleSpacing;

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
                        <svg class="svg-render" width="855" height="690" viewBox="0 0 855 690"
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
                            <line class="arrow-visuals" x1="40" y1="10" x2="40" y2="610"
                                  stroke="#000000" marker-start="url(#beginArrow)" marker-end="url(#endArrow)" />
                        
                            <line class="arrow-visuals" x1="75" y1="650" x2="%s" y2="650"
                                  stroke="#000000" marker-start="url(#beginArrow)" marker-end="url(#endArrow)" />
                        
                            <!-- Text -->
                            <text style="text-anchor: middle" transform="translate(30,300) rotate(-90)">%s cm</text>
                            <text style="text-anchor: middle" x="%s" y="%s">%s cm</text>
                        
                            <svg x="75" y="10" width="780" height="600" viewBox="0 0 %s %s" preserveAspectRatio="xMinYMin">
                        
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
                width,                                                                          // arrow x2
                length, width / 2, (length + 70), width,                                        // tekst labels
                width + margin, length + margin,                                                // inner viewbox
                width, length,                                                                  // outer frame
                overhang, woodWidth, width,                                                     // top beam
                length - overhang, woodWidth, width,                                            // bottom beam
                raftersCalc.toString(),                                                         // alle spær
                (width - woodWidth), length, woodWidth,                                         // sidste spær
                raftersMargin, overhang, length, (length - overhang + woodWidth),               // kryds 1
                raftersMargin, (length - overhang + woodWidth), length, overhang,               // kryds 2
                polesCalc.toString()                                                            // stolper

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