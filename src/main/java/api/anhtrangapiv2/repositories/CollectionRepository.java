package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Integer>{
    boolean existsByName(String name);
}
