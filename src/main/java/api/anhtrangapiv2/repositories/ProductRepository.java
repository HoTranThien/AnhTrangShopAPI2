package api.anhtrangapiv2.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.anhtrangapiv2.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    boolean existsByParentCategoryId(int id);
    boolean existsByChildrenCategoryId(int id);
    boolean existsByCollectionId(int id);
    Page<Product> findByCollectionId(int collectionId,PageRequest pageRequest);
    Page<Product> findByChildrenCategoryId(int id,PageRequest pageRequest);
    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE p.isnew = true")
    Page<Product> findByIsnew(PageRequest pageRequest);
    @Query("SELECT p FROM Product p WHERE p.sale_cost > 0")
    Page<Product> findBySale_cost(PageRequest pageRequest);
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:key%")
    Page<Product> findByQuery(@Param("key") String key,PageRequest pageRequest);
}
