package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    boolean existsByParentCategoryId(int id);
    boolean existsByChildrenCategoryId(int id);
    boolean existsByCollectionId(int id);
    //boolean existsByParent_category(Parent_category pc);
    //boolean existsByChildrenCategory(ChildrenCategory cc);
}
