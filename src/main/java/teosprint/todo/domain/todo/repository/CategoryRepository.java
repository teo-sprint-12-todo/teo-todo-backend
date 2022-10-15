package teosprint.todo.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import teosprint.todo.domain.todo.data.entity.Category;

import javax.transaction.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category save(Category category);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.name = :name WHERE c.id = :id")
    Integer updateCategory(Integer id, String name);
}
