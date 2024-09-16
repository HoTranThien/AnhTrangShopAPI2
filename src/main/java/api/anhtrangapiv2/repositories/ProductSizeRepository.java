package api.anhtrangapiv2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.ProductSize;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer>{
    List<ProductSize> findByProductId(int id);
    ProductSize findByProductIdAndSizeId(int productId, int sizeId);
    boolean existsBySizeId(int id);
    boolean existsByProductIdAndSizeId(int productId, int sizeId);
}
