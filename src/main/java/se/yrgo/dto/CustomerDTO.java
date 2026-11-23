package se.yrgo.dto;

import se.yrgo.entity.Customer;

import java.util.List;

public class CustomerDTO {

    private long id;
    private String name;
    private String phoneNumber;
    private List<VehicleDTO> vehicles;

    public CustomerDTO() {}

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.phoneNumber = customer.getPhoneNumber();
        this.vehicles = customer.getVehicles().stream()
                .map(se.yrgo.dto.VehicleDTO::new)
                .toList();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<se.yrgo.dto.VehicleDTO> getVehicles() {
        return vehicles;
    }
}