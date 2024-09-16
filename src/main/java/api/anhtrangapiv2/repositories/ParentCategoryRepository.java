package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.ParentCategory;

public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Integer>{
    boolean existsByName(String name);

}
