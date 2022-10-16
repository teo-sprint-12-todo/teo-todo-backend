package teosprint.todo.domain.review.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teosprint.todo.domain.review.data.dto.res.ReviewListRes;
import teosprint.todo.domain.review.data.entity.QReview;
import teosprint.todo.domain.review.data.entity.Review;
import teosprint.todo.domain.todo.data.dto.res.TodoListRes;
import teosprint.todo.domain.todo.data.entity.Category;
import teosprint.todo.domain.todo.data.entity.QCategory;
import teosprint.todo.domain.todo.data.entity.QGoal;
import teosprint.todo.domain.todo.data.entity.QTodo;

import java.util.List;

@Repository
public class ReviewRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public ReviewRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Review.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ReviewListRes> getAllReviewByPeriodType(Integer userId, Integer periodType) {
        QReview r = QReview.review;
        QTodo t = QTodo.todo;

//        return jpaQueryFactory.select(Projections.constructor(TodoListRes.class, t.id, t.category.id, c.name, t.goal.id, g.name, t.importance, t.text, t.createdAt, t.endDate, t.isDone))
//                .from(t)
//                .leftJoin(c).on(t.category.id.eq(c.id))
//                .leftJoin(g).on(t.goal.id.eq(g.id))
//                .where(builder)
//                .orderBy(t.endDate.asc())
//                .fetch();

        return jpaQueryFactory.select(Projections.constructor(ReviewListRes.class))
                .from(r)
                .fetch();
    }

}
