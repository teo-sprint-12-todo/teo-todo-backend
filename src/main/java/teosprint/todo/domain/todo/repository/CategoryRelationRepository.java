package teosprint.todo.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teosprint.todo.domain.todo.data.entity.Category;
import teosprint.todo.domain.todo.data.entity.CategoryRelation;
import teosprint.todo.domain.todo.data.entity.GoalRelation;

public interface CategoryRelationRepository extends JpaRepository<CategoryRelation, Integer> {
    CategoryRelation save(CategoryRelation categoryRelation);
}
