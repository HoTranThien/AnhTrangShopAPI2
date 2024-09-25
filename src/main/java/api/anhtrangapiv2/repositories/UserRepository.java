package api.anhtrangapiv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import api.anhtrangapiv2.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
