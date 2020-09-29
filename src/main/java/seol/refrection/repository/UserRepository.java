package seol.refrection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seol.refrection.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
