package se.yrgo.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.yrgo.dto.CustomerDTO;
import se.yrgo.dto.VehicleBrandDTO;
import se.yrgo.dto.VehicleDTO;
import se.yrgo.entity.Customer;
import se.yrgo.entity.Vehicle;
import se.yrgo.projection.VehicleBrandProjection;
import se.yrgo.repository.CustomerRepository;
import se.yrgo.repository.VehicleRepository;

import java.util.List;

@RestController
public class CarWorkshopController {
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public CarWorkshopController(CustomerRepository customerRepository, VehicleRepository vehicleRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestParam String name, @RequestParam String phone) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhoneNumber(phone);

        Customer savedCustomer = customerRepository.save(customer);

        return ResponseEntity.ok(new CustomerDTO(savedCustomer));
    }

    @GetMapping("/customer-id")
    public ResponseEntity<Long> customerIdByName(@RequestParam String name) {
        return customerRepository.findByName(name)
                .map(customer -> ResponseEntity.ok(customer.getId()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> allCustomersAndTheirVehicles() {
        List<Customer> allCustomers = customerRepository.findAllCustomersAndTheirVehicles();
        List<CustomerDTO> allCustomersDto = allCustomers.stream()
                .map(CustomerDTO::new)
                .toList();

        return ResponseEntity.ok(allCustomersDto);
    }

    @PostMapping("/vehicles")
    public ResponseEntity<VehicleDTO> createVehicle (@RequestParam String registrationNumber,
                                                  @RequestParam String brand,
                                                  @RequestParam String model,
                                                  @RequestParam String productionYear) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setProductionYear(productionYear);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return ResponseEntity.ok(new VehicleDTO(savedVehicle));
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDTO>> allVehicles() {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        List<VehicleDTO> allVehiclesDTO = allVehicles.stream()
                .map(VehicleDTO::new)
                .toList();

        return ResponseEntity.ok(allVehiclesDTO);
    }

    @GetMapping("/vehicles-by-brand")
    public ResponseEntity<List<VehicleDTO>> vehicleByBrand(@RequestParam String brand) {
        List<Vehicle> vehiclesByBrand = vehicleRepository.findByBrand(brand);
        List<VehicleDTO> vehiclesByBrandDTO = vehiclesByBrand.stream()
                .map(VehicleDTO::new)
                .toList();

        return ResponseEntity.ok(vehiclesByBrandDTO);
    }

    @PostMapping("/addvehicletocustomer")
    @Transactional
    public ResponseEntity<CustomerDTO> addVehicleToCustomer(@RequestParam Long customerId,
                                                            @RequestParam Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        vehicle.setCustomer(customer);
        customer.getVehicles().add(vehicle);

        vehicleRepository.save(vehicle);

        return ResponseEntity.ok(new CustomerDTO(customer));
    }

    @PutMapping("/vehicles/{id}/brand")
    @Transactional
    public ResponseEntity<VehicleDTO> updateBrand(@PathVariable Long id,
                                                  @RequestParam String brand) {
        int updatedRows = vehicleRepository.updateBrandById(id, brand);

        if (updatedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        return ResponseEntity.ok(new VehicleDTO(vehicle));
    }

    @GetMapping("/vehicle-brand-info")
    public ResponseEntity<List<VehicleBrandDTO>> vehicleBrandInfo() {
        List<VehicleBrandProjection> projections = vehicleRepository.findAllVehicleBrandInfo();
        List<VehicleBrandDTO> dtos = projections.stream()
                .map(VehicleBrandDTO::new)
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
