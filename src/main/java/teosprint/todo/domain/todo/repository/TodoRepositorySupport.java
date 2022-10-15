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
    public void updateTodo(Integer id, String text, Integer importance, LocalDate endDate) {
        QTodo t = QTodo.todo;

        jpaQueryFactory.update(t)
                .set(t.text, text)
                .set(t.importance, importance)
                .set(t.endDate, endDate)
                .where(t.id.eq(id))
                .execute();
    }

    // 카테고리 업데이트
    @Transactional
    public void updateCategoryRelation(Integer todoId, Category category) {
        QCategoryRelation cr = QCategoryRelation.categoryRelation;

        jpaQueryFactory.update(cr)
                .set(cr.category, category)
                .where(cr.todo.id.eq(todoId))
                .execute();
    }

    // 목표 업데이트
    @Transactional
    public void updateGoalRelation(Integer todoId, Goal goal) {
        QGoalRelation gr = QGoalRelation.goalRelation;

        jpaQueryFactory.update(gr)
                .set(gr.goal, goal)
                .where(gr.todo.id.eq(todoId))
                .execute();
    }
}
