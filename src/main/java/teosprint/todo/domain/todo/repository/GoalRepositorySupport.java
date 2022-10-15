package teosprint.todo.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teosprint.todo.domain.todo.data.dto.res.CategoryListRes;
import teosprint.todo.domain.todo.data.dto.res.GoalListRes;
import teosprint.todo.domain.todo.data.dto.res.GoalStatListRes;
import teosprint.todo.domain.todo.data.entity.*;

import org.springframework.transaction.annotation.Transactional;
import teosprint.todo.domain.todo.data.entity.sub.QGoalDoneQuery;
import teosprint.todo.domain.todo.data.entity.sub.QGoalTotalQuery;

import java.time.LocalDate;
import java.util.List;

@Repository
public class GoalRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public GoalRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Goal.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Transactional
    public void updateGoal(Integer id, Category category, String name, LocalDate endDate) {
        QGoal g = QGoal.goal;

        jpaQueryFactory.update(g)
                .set(g.category, category)
                .set(g.name, name)
                .set(g.endDate, endDate)
                .where(g.id.eq(id))
                .execute();
    }

    public List<GoalListRes> getAllGoal(Integer userId) {
        QGoal g = QGoal.goal;
        QCategory c = QCategory.category;

        return jpaQueryFactory.select(Projections.constructor(GoalListRes.class, g.id, g.category.id, g.name, g.endDate))
                .from(g)
                .join(c)
                .on(g.category.id.eq(c.id))
                .where(c.user.id.eq(userId))
                .fetch();
    }

    public List<GoalStatListRes> getStatGoalList(Integer userId, Boolean isEnd) {
        QTodo t = QTodo.todo;
        QCategory c = QCategory.category;
        QGoal g = QGoal.goal;
        QGoalTotalQuery gtq = QGoalTotalQuery.goalTotalQuery;
        QGoalDoneQuery gdq = QGoalDoneQuery.goalDoneQuery;

        BooleanBuilder builder = new BooleanBuilder();
        if (!isEnd) {
            builder.and(g.endDate.after(LocalDate.now()).or(g.endDate.eq(LocalDate.now())));
        } else {
            builder.and(g.endDate.before(LocalDate.now()));
        }

        return jpaQueryFactory.select(Projections.constructor(GoalStatListRes.class, g.id, g.name, g.createdAt, g.endDate,
                                                    gtq.total.coalesce(0), gdq.done.coalesce(0), gdq.done.multiply(100).divide(gtq.total).coalesce(0)))
                .from(g)
                .leftJoin(gtq).on(gtq.id.eq(g.id))
                .leftJoin(gdq).on(gdq.id.eq(g.id))
                .where(builder)
                .fetch();
    }

    public GoalStatListRes getStatCurrGoal(Integer userId, Integer goalId) {
        QTodo t = QTodo.todo;
        QCategory c = QCategory.category;
        QGoal g = QGoal.goal;
        QGoalTotalQuery gtq = QGoalTotalQuery.goalTotalQuery;
        QGoalDoneQuery gdq = QGoalDoneQuery.goalDoneQuery;

        return jpaQueryFactory.select(Projections.constructor(GoalStatListRes.class, g.id, g.name, g.createdAt, g.endDate,
                        gtq.total.coalesce(0), gdq.done.coalesce(0), gdq.done.multiply(100).divide(gtq.total).coalesce(0)))
                .from(g)
                .leftJoin(gtq).on(gtq.id.eq(g.id))
                .leftJoin(gdq).on(gdq.id.eq(g.id))
                .where(g.id.eq(goalId))
                .fetch().get(0);
    }
}
