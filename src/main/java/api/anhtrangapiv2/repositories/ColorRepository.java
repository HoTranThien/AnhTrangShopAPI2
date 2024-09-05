package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.anhtrangapiv2.models.Color;


public interface  ColorRepository extends JpaRepository<Color, Integer>{
    boolean existsByName(String name);
}
