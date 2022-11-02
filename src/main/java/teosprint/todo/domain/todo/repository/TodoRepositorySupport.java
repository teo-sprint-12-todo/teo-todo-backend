package teosprint.todo.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teosprint.todo.domain.todo.data.dto.res.TodoListRes;
import teosprint.todo.domain.todo.data.entity.*;

import java.util.*;

import java.time.LocalDate;

@Repository
public class TodoRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public TodoRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Todo.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // 할 일 업데이트
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

    // 정렬 기준, 카테고리
    public List<TodoListRes> getMainTodoList(Integer userId, Integer categoryId) {
        QTodo t = QTodo.todo;
        QCategory c = QCategory.category;
        QGoal g = QGoal.goal;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(t.user.id.eq(userId));
        builder.and(t.endDate.after(LocalDate.now()).or(t.endDate.eq(LocalDate.now())));
        if (categoryId != -1) {
            builder.and(t.category.id.eq(categoryId));
        }

        return jpaQueryFactory.select(Projections.constructor(TodoListRes.class, t.id, t.category.id, c.name, t.goal.id, g.name, t.importance, t.text, t.createdAt, t.endDate, t.isDone))
                .from(t)
                .leftJoin(c).on(t.category.id.eq(c.id))
                .leftJoin(g).on(t.goal.id.eq(g.id))
                .where(builder)
                .orderBy(t.createdAt.desc())
                .fetch();

    }


    public List<TodoListRes> getTodoListOfMonth(Integer userId, Integer year, Integer month) {
        QTodo t = QTodo.todo;
        QCategory c = QCategory.category;
        QGoal g = QGoal.goal;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(t.user.id.eq(userId));
        builder.and(t.endDate.year().eq(year));
        builder.and(t.endDate.month().eq(month));

        return jpaQueryFactory.select(Projections.constructor(TodoListRes.class, t.id, t.category.id, c.name, t.goal.id, g.name, t.importance, t.text, t.createdAt, t.endDate, t.isDone))
                .from(t)
                .leftJoin(c).on(t.category.id.eq(c.id))
                .leftJoin(g).on(t.goal.id.eq(g.id))
                .where(builder)
                .orderBy(t.endDate.asc())
                .fetch();

    }

    public List<TodoListRes> getTodoListOfDay(Integer userId, Integer year, Integer month, Integer day) {
        QTodo t = QTodo.todo;
        QCategory c = QCategory.category;
        QGoal g = QGoal.goal;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(t.user.id.eq(userId));
        builder.and(t.endDate.year().eq(year));
        builder.and(t.endDate.month().eq(month));
        builder.and(t.endDate.dayOfMonth().eq(day));

        return jpaQueryFactory.select(Projections.constructor(TodoListRes.class, t.id, t.category.id, c.name, t.goal.id, g.name, t.importance, t.text, t.createdAt, t.endDate, t.isDone))
                .from(t)
                .leftJoin(c).on(t.category.id.eq(c.id))
                .leftJoin(g).on(t.goal.id.eq(g.id))
                .where(builder)
                .orderBy(t.endDate.asc())
                .fetch();

    }


    public List<TodoListRes> getAllTodoByGoal(Integer userId, Integer goalId) {
        QTodo t = QTodo.todo;
        QCategory c = QCategory.category;
        QGoal g = QGoal.goal;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(t.user.id.eq(userId));
        builder.and(t.goal.id.eq(goalId));

        return jpaQueryFactory.select(Projections.constructor(TodoListRes.class, t.id, t.category.id, c.name, t.goal.id, g.name, t.importance, t.text, t.createdAt, t.endDate, t.isDone))
                .from(t)
                .leftJoin(c).on(t.category.id.eq(c.id))
                .leftJoin(g).on(t.goal.id.eq(g.id))
                .where(builder)
                .orderBy(t.endDate.asc())
                .fetch();

    }

    @Transactional
    public void deleteById(Integer todoId) {
        QTodo t = QTodo.todo;

        jpaQueryFactory.delete(t)
                .where(t.id.eq(todoId))
                .execute();
    }

    @Transactional
    public void removeGoalByGoalId(Integer goalId) {
        QTodo t = QTodo.todo;

        jpaQueryFactory.update(t)
                .setNull(t.goal)
                .where(t.goal.id.eq(goalId))
                .execute();
    }

    @Transactional
    public void removeCategoryByCategoryId(Integer categoryId) {
        QTodo t = QTodo.todo;

        jpaQueryFactory.update(t)
                .setNull(t.category)
                .setNull(t.goal)
                .where(t.category.id.eq(categoryId))
                .execute();
    }
}
