package se.yrgo.entity;

import jakarta.persistence.*;

@Entity
public class Vehicle {

    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private long id;
    private String registrationNumber;
    private String brand;
    private String model;
    private String productionYear;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Vehicle() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
