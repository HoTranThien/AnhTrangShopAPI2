package api.anhtrangapiv2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.Order;

public interface  OrderRepository extends JpaRepository<Order, Integer>{
    boolean existsByDeliveryId(int id);
    List<Order> findByUserId(int id);
}
