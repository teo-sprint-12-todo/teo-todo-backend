package teosprint.todo.domain.review.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import teosprint.todo.domain.review.data.dto.res.ReviewListRes;
import teosprint.todo.domain.review.data.entity.QReview;
import teosprint.todo.domain.review.data.entity.Review;
import teosprint.todo.domain.todo.data.entity.QTodo;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.count;

@Repository
public class ReviewRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public ReviewRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Review.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ReviewListRes> getAllReviewByPeriodType(Integer userId, String periodType) {
        QReview r = QReview.review;
        QTodo t = QTodo.todo;

        List<ReviewListRes> res = jpaQueryFactory.select(Projections.constructor(ReviewListRes.class, r.id, r.periodType, r.text, r.startDate, r.endDate,
                                                                                ExpressionUtils.as(
                                                                                        JPAExpressions.select(count(t.id))
                                                                                                .from(t)
                                                                                                .where(t.endDate.between(r.startDate, r.endDate)),
                                                                                        "totalCnt"),
                                                                                ExpressionUtils.as(
                                                                                        JPAExpressions.select(count(t.id))
                                                                                                .from(t)
                                                                                                .where(t.endDate.between(r.startDate, r.endDate).and(t.isDone.eq(true))),
                                                                                        "doneCnt"),
                                                                                r.id))
                .from(r)
                .where(r.periodType.eq(periodType))
                .fetch();

        // 임시 순회
        for (ReviewListRes review: res) {
            review.setPercent( review.getDoneCnt() * 100 / review.getTotalCnt() );
        }

        return  res;
    }

    public Boolean existByUserAndNowDate(Integer userId, LocalDate startDate, LocalDate endDate) {
        QReview r = QReview.review;

        List<Review> res =jpaQueryFactory.select(r)
                .from(r)
                .where(r.startDate.eq(startDate).and(r.endDate.eq(endDate)).and(r.user.id.eq(userId)))
                .fetch();

        return res.size() != 0;
    }
}
