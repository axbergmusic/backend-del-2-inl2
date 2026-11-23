package se.yrgo.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.yrgo.entity.Vehicle;
import se.yrgo.projection.VehicleBrandProjection;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByBrand(String brand);

    @Query("SELECT v FROM Vehicle v WHERE v.customer.id = :customerId")
    List<Vehicle> findVehiclesByCustomerId(@Param("customerId") Long customerId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Vehicle v SET v.brand = :brand WHERE v.id = :id")
    int updateBrandById(@Param("id") Long id, @Param("brand") String brand);

    @Query("SELECT v.registrationNumber AS registrationNumber, v.brand AS brand FROM Vehicle v ")
    List<VehicleBrandProjection> findAllVehicleBrandInfo();
}
