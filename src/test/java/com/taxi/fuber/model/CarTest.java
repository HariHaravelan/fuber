package com.taxi.fuber.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    private Car pinkCarOne;
    private Car pinkCarTwo;
    private Car regularCarOne;
    private Car regularCarTwo;

    @BeforeEach
    public void setUp() {
        Location bengaluru = new Location(12.972442, 77.580643);
        Location chennai = new Location(13.0827, 80.2707);
        Location kolkata = new Location(22.5726, 88.3639);
        Location mumbai = new Location(19.0760, 72.8777);
        pinkCarOne = new Car("ABC123", Color.PINK, kolkata);
        pinkCarTwo = new Car("GHI789", Color.PINK, chennai);
        regularCarOne = new Car("DEF456", Color.OTHERS, bengaluru);
        regularCarTwo = new Car("XYZ007", Color.OTHERS, mumbai);
    }

    @Test
    public void shouldReturnCarsSortedByDistanceToGivenLocation() {
        Location mysuru = new Location(12.2958, 76.6394);
        List<Car> cars = new ArrayList<>(Arrays.asList(pinkCarOne, pinkCarTwo, regularCarOne, regularCarTwo));

        cars.sort(new CarComparator<>(mysuru));

        assertArrayEquals(new Car[]{regularCarOne, pinkCarTwo, regularCarTwo, pinkCarOne}, cars.toArray(new Car[0]));
    }

    @Test
    public void shouldReturnTrueWhenCurrentTripIsCompleted() {
        LocalDateTime tripStartTime = LocalDateTime.now();

        boolean available = pinkCarOne.isAvailable(tripStartTime);

        assertTrue(available);
    }

    @Test
    public void shouldAddAdditionalChargeWhenPinkCar() {
        double distanceToTravel = 45;

        double tripCharge = pinkCarOne.calculateTripCharge(distanceToTravel);

        assertEquals(140, tripCharge);
    }

    @Test
    public void shouldNotAddAdditionalChargeWhenNotPinkCar() {
        double distanceToTravel = 45;

        double tripCharge = regularCarOne.calculateTripCharge(distanceToTravel);

        assertEquals(135, tripCharge);
    }

    @Test
    public void shouldReturnTimeTakenWhenDistanceGiven() {
        double distanceToTravel = 45;

        double timeTaken = pinkCarOne.getTimeTakenInMinutes(distanceToTravel);

        assertEquals(45, timeTaken);
    }

    @Test
    public void shouldUpdateCurrentPointAsDropLocation() {
        Location mumbai = new Location(19.0760, 72.8777);
        Location kolkata = new Location(22.5726, 88.3639);

        pinkCarOne.updateCurrentLocation(mumbai);

        assertEquals(kolkata, pinkCarOne.getStartPoint());
        assertEquals(mumbai, pinkCarOne.getCurrentLocation());
    }
}