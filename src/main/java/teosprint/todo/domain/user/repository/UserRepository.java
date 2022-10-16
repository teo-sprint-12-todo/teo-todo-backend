package teosprint.todo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teosprint.todo.domain.user.data.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User account);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}