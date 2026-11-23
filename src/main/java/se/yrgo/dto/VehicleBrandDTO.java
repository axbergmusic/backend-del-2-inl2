package se.yrgo.dto;

import se.yrgo.projection.VehicleBrandProjection;

public class VehicleBrandDTO {
    private String registrationNumber;
    private String brand;

    public VehicleBrandDTO() {}

    public VehicleBrandDTO(VehicleBrandProjection projection) {
        this.registrationNumber = projection.getRegistrationNumber();
        this.brand = projection.getBrand();
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBrand() {
        return brand;
    }
}
