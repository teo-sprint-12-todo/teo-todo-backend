package teosprint.todo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import teosprint.todo.domain.user.data.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User account);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}