package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import api.anhtrangapiv2.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    boolean existsByParentCategoryId(int id);
    boolean existsByChildrenCategoryId(int id);
    boolean existsByCollectionId(int id);

    // @Query("SELECT p FROM Product p WHERE p.id = :id")
    // //@EntityGraph(attributePaths = {"parentCategory.name","childrenCategory.name"})
    // Product findProductById2(@Param("id") int id);
    // @Query(value = "SELECT * FROM product WHERE id = :id",nativeQuery = true)
    // Product findProductById3(@Param("id") int id);

    //boolean existsByParent_category(Parent_category pc);
    //boolean existsByChildrenCategory(ChildrenCategory cc);
}
