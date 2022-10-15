package teosprint.todo.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teosprint.todo.domain.todo.data.entity.Goal;
import teosprint.todo.domain.todo.data.entity.GoalRelation;

public interface GoalRelationRepository extends JpaRepository<GoalRelation, Integer> {
    GoalRelation save(GoalRelation goalRelation);
}
