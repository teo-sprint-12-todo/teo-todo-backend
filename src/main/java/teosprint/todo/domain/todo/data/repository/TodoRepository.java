package teosprint.todo.domain.todo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teosprint.todo.domain.todo.data.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Todo save(Todo todo);

}
