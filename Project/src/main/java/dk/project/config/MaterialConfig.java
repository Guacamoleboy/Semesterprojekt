// Package
package dk.project.config;

// Imports
import java.util.List;
import java.util.Map;

public class MaterialConfig {

    // TODO | Jonas Comments - 24/11
    // TODO | Vi skal vel ikke skrive ID på (hardcoded) men have det loadet via getId fx.
    // TODO | Hvad siger du? Antager hardcoded er midlertidigt - bevidst.

    // Attributes
    public static final int ID_UNDERSTERN_360 = 1;        // Brædt 360
    public static final int ID_UNDERSTERN_540 = 2;        // Brædt 540
    public static final int ID_OVERSTERN_360 = 3;         // Overstern 360
    public static final int ID_OVERSTERN_540 = 4;         // Overstern 540
    public static final int ID_LAEGTE_420 = 5;            // Lægte 420
    public static final int ID_REGLAR_270 = 6;            // Reglar 270
    public static final int ID_REGLAR_240 = 7;            // Reglar 240
    public static final int ID_SPAER_600_REM = 8;         // Spær 600 (remme i sider)
    public static final int ID_SPAER_480 = 9;             // Spær 480
    public static final int ID_SPAER_600_RAFTER = 10;     // Spær 600 (15 stk spær)
    public static final int ID_STOLPE_300 = 11;           // Stolpe 300
    public static final int ID_BRAEDT_210 = 12;           // Brædt 210 (beklædning skur)
    public static final int ID_VANDBRAEDT_540 = 13;       // Vandbrædt 540
    public static final int ID_VANDBRAEDT_360 = 14;       // Vandbrædt 360
    public static final int ID_PLASTMO_600 = 15;          // Plastmo Ecolite 600
    public static final int ID_PLASTMO_360 = 16;          // Plastmo Ecolite 360
    public static final int ID_BUNDSKRUER_200 = 17;       // Bundskruer 200 (pakke)
    public static final int ID_HULBAAND_10M = 18;         // Hulbånd 1x20mm (rulle)
    public static final int ID_UNIVERSAL_RIGHT = 19;      // Universal skruer 190mm højre
    public static final int ID_UNIVERSAL_LEFT = 20;       // Universal skruer 190mm venstre
    public static final int ID_SKRUER_4_5X60_200 = 21;    // Skruer 4,5x60mm 200stk
    public static final int ID_BESLAGSKRUER_4_0X50_250 = 22; // Beslagskruer 4,0x50mm 250stk
    public static final int ID_BRAEDDEBOLT_10X120 = 23;   // Bræddebolt 10x120mm
    public static final int ID_FIRKANTSKIVER = 24;        // Firkantskiver 40x40x11mm
    public static final int ID_SKRUER_4_5X70_400 = 25;    // Skruer 4,5x70mm 400stk
    public static final int ID_SKRUER_4_5X50_300 = 26;    // Skruer 4,5x50mm 300stk
    public static final int ID_STALDDOERSGREB = 27;       // Stalddørsgreb 50x75
    public static final int ID_HAENGSEL_390 = 28;         // Hængsel 390mm
    public static final int ID_VINKELBESLAG_35 = 29;      // Vinkelbeslag 35


    public Map<String, Integer> getConfig(String type) {
        switch (type) {
            case "Fladt tag" -> {
                return Map.of(
                        "underStern360", ID_UNDERSTERN_360,
                        "underStern540", ID_UNDERSTERN_540,
                        "overStern360", ID_OVERSTERN_360,
                        "overStern540", ID_OVERSTERN_360,
                        "spær", ID_SPAER_600_REM,
                        "rafter", ID_SPAER_600_RAFTER,
                        "stolpe", ID_STOLPE_300,
                        "vandbrædt360", ID_VANDBRAEDT_360,
                        "vandbrædt540", ID_VANDBRAEDT_540,
                        "tag", ID_PLASTMO_600
                );
            }

            case "Høj rejsning" -> {
                return Map.of(
                        "underStern360", ID_UNDERSTERN_360,
                        "underStern540", ID_UNDERSTERN_540,
                        "overStern360", ID_OVERSTERN_360,
                        "overStern540", ID_OVERSTERN_360,
                        "spær", ID_SPAER_600_REM,
                        "rafter", ID_SPAER_600_RAFTER,
                        "stolpe", ID_STOLPE_300,
                        "vandbrædt360", ID_VANDBRAEDT_360,
                        "vandbrædt540", ID_VANDBRAEDT_540,
                        "tag", ID_PLASTMO_360
                );
            }
        }
        return Map.of();
    }



}
