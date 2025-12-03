// Package
package dk.project.service;

// Imports
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarportCalculatorTest {

    // Attributes
    CarportCalculator calculator = new CarportCalculator();

    // __________________________________________________________

    @Test
    void calculatePosts() {
        int poles = calculator.calculatePosts(780);
        assertEquals(8, poles);
    }

    // __________________________________________________________

    @Test
    void calculateRafters() {
        int rafters = calculator.calculateRafters(600);
        assertEquals(10, rafters);
    }

    // __________________________________________________________

    @Test
    void calculateRems() {
        int rems = calculator.calculateRems(600);
        assertEquals(2, rems);
    }

    // __________________________________________________________

    @Test
    void calculateUniversalMounts() {
        int mounts = calculator.calculateUniversalMounts(10);
        assertEquals(20, mounts);
    }

    // __________________________________________________________

    @Test
    void calculateScrewsForUniversalMounts() {
        int screws = calculator.calculateScrewsForUniversalMounts(10);
        assertEquals(120, screws);
    }

    // __________________________________________________________

    @Test
    void calculateBraceBandScrews() {
        int screws = calculator.calculateBraceBandScrews(10);
        assertEquals(15, screws);
    }

    // __________________________________________________________

    @Test
    void calculateBraceBandRolls() {
        int rolls = calculator.calculateBraceBandRolls(400, 300);
        assertEquals(1, rolls);
    }

    // __________________________________________________________

    @Test
    void calculateBolts() {
        int bolts = calculator.calculateBolts(6);
        assertEquals(12, bolts);
    }

    // __________________________________________________________

    @Test
    void calculateWashers() {
        int washers = calculator.calculateWashers(6);
        assertEquals(12, washers);
    }

    // __________________________________________________________

    @Test
    void calculateRoofScrews() {
        int roofScrews = calculator.calculateRoofScrews(5);
        assertEquals(250, roofScrews);
    }

    // __________________________________________________________

    @Test
    void calculateScrewPacks() {
        int packs = calculator.calculateScrewPacks(250, 200);
        assertEquals(2, packs);
    }

} // CarportCalculatorTest end