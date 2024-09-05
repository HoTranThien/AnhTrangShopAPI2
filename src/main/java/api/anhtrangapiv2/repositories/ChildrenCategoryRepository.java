package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.ChildrenCategory;

public interface ChildrenCategoryRepository extends JpaRepository<ChildrenCategory, Integer>{
    boolean existsByName(String name);
}
