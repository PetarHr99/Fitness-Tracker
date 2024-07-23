package bg.softuni.finalproject.repo;

import bg.softuni.finalproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsername(String username);

    Optional<User> findByEmail(String email);
}
