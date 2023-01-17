package seleniumbasics;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Vehicle {
    @Test
    public void TC_001_verifyVehicleQuality() {
        System.out.println("Vehicle quality is good");
    }
    @Test
    public void TC_002_verifyVehiclePrice() {
        System.out.println("Vehicle price is low");
    }
}
