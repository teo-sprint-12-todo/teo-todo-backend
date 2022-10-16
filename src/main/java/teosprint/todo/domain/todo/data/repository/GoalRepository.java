package teosprint.todo.domain.todo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teosprint.todo.domain.todo.data.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    Goal save(Goal goal);
}
