package se.yrgo.dto;

import se.yrgo.entity.Vehicle;

public class VehicleDTO {

    private long id;
    private String registrationNumber;
    private String brand;
    private String model;
    private String productionYear;

    public VehicleDTO() {}

    public VehicleDTO(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.registrationNumber = vehicle.getRegistrationNumber();
        this.brand = vehicle.getBrand();
        this.model = vehicle.getModel();
        this.productionYear = vehicle.getProductionYear();
    }

    public long getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getProductionYear() {
        return productionYear;
    }
}