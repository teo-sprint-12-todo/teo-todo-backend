package teosprint.todo.domain.todo.data.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import teosprint.todo.domain.todo.data.entity.Category;
import teosprint.todo.domain.todo.data.entity.Goal;

import javax.transaction.Transactional;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    Goal save(Goal goal);
}
