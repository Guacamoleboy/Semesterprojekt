package dk.project.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarportCalculatorTest {

    CarportCalculator calculator = new CarportCalculator();

    @Test
    void calculatePosts() {
        int poles = calculator.calculatePosts(600);
        assertEquals(14, poles);
    }

    @Test
    void calculateRafters() {
        int rafters = calculator.calculateRafters(600);
        assertEquals(10, rafters);
    }

    @Test
    void calculateRems() {
        int rems = calculator.calculateRems(600);
        assertEquals(2, rems);
    }
}