package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer>{

}
