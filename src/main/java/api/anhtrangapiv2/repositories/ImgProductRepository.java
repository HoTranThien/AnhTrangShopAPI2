package api.anhtrangapiv2.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import api.anhtrangapiv2.models.ImgProduct;

public interface ImgProductRepository extends JpaRepository<ImgProduct,Integer>{
    public List<ImgProduct> findByProductId(int productId);
}
