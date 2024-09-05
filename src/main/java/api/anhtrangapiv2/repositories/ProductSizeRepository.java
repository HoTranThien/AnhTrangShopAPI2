package api.anhtrangapiv2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.ProductSize;
import api.anhtrangapiv2.models.Size;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer>{
    List<Size> findByProductId(int id);
    boolean existsBySizeId(int id);
}
