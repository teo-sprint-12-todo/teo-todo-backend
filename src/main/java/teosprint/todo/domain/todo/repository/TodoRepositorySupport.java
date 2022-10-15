package teosprint.todo.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teosprint.todo.domain.todo.data.entity.*;
import teosprint.todo.domain.user.data.entity.User;

import java.time.LocalDate;

@Repository
public class TodoRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public TodoRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Todo.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // 할 일 업데이트
    /**
     * this.user = user;
     *         this.text = text;
     *         this.importance = importance;
     *         this.endDate = endDate;
     *         */
    @Transactional
    public void updateTodo(Integer id, String text, Integer importance, LocalDate endDate, Category category, Goal goal) {
        QTodo t = QTodo.todo;

        jpaQueryFactory.update(t)
                .set(t.text, text)
                .set(t.importance, importance)
                .set(t.endDate, endDate)
                .set(t.category, category)
                .set(t.goal, goal)
                .where(t.id.eq(id))
                .execute();
    }

    @Transactional
    public void checkTodo(Integer id, Boolean toThis) {
        QTodo t = QTodo.todo;

        jpaQueryFactory.update(t)
                .set(t.isDone, toThis)
                .where(t.id.eq(id))
                .execute();
    }
}
