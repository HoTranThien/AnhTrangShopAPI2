package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer>{
    boolean existsByName(String name);
}
