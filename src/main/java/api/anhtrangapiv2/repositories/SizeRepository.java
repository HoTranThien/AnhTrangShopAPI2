package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.Size;

public interface SizeRepository extends JpaRepository<Size, Integer>{
    boolean existsByName(String name);
}
