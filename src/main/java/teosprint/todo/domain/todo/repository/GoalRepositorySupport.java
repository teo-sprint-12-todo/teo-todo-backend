package teosprint.todo.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teosprint.todo.domain.todo.data.dto.res.CategoryListRes;
import teosprint.todo.domain.todo.data.dto.res.GoalListRes;
import teosprint.todo.domain.todo.data.entity.Category;
import teosprint.todo.domain.todo.data.entity.Goal;

import org.springframework.transaction.annotation.Transactional;
import teosprint.todo.domain.todo.data.entity.QCategory;
import teosprint.todo.domain.todo.data.entity.QGoal;

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
}
