package api.anhtrangapiv2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.ProductColor;

public interface ProductColorRepository extends JpaRepository<ProductColor, Integer>{
    List<ProductColor> findByProductId(int id);
    boolean existsByColorId(int id);
}
