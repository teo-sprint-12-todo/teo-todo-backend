package teosprint.todo.domain.review.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

import teosprint.todo.domain.review.data.dto.res.ReviewLastRes;
import teosprint.todo.domain.review.data.dto.res.ReviewListRes;
import teosprint.todo.domain.review.data.entity.QReview;
import teosprint.todo.domain.review.data.entity.Review;
import teosprint.todo.domain.todo.data.entity.QTodo;
import teosprint.todo.domain.todo.data.entity.Todo;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.all;
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
                                                                                                .where(t.endDate.between(r.startDate, r.endDate).and(t.user.id.eq(userId))),
                                                                                        "totalCnt"),
                                                                                ExpressionUtils.as(
                                                                                        JPAExpressions.select(count(t.id))
                                                                                                .from(t)
                                                                                                .where(t.endDate.between(r.startDate, r.endDate).and(t.isDone.eq(true).and(t.user.id.eq(userId)))),
                                                                                        "doneCnt"),
                                                                                r.id))
                .from(r)
                .where(r.periodType.eq(periodType).and(r.user.id.eq(userId)))
                .fetch();

        // 임시 순회
        for (ReviewListRes review: res) {
            if (review.getTotalCnt() == 0) review.setPercent(0);
            else review.setPercent( review.getDoneCnt() * 100 / review.getTotalCnt() );
        }

        return  res;
    }

    public Review existByUserAndNowDate(Integer userId, LocalDate startDate, LocalDate endDate) {
        QReview r = QReview.review;

        List<Review> res =jpaQueryFactory.select(r)
                .from(r)
                .where(r.startDate.eq(startDate).and(r.endDate.eq(endDate)).and(r.user.id.eq(userId)))
                .fetch();

        return res.size() == 0 ? null : res.get(0);
    }

    public ReviewLastRes getLastTodoStat(Integer userId, LocalDate startDate, LocalDate endDate) {
        QTodo t = QTodo.todo;

        ReviewLastRes res = jpaQueryFactory.select(Projections.constructor(ReviewLastRes.class,
                ExpressionUtils.as(
                        JPAExpressions.select(count(t.id))
                                .from(t)
                                .where(t.endDate.between(startDate, endDate).and(t.user.id.eq(userId))),
                        "totalCnt"),
                ExpressionUtils.as(
                        JPAExpressions.select(count(t.id))
                                .from(t)
                                .where(t.endDate.between(startDate, endDate).and(t.isDone.eq(true).and(t.user.id.eq(userId)))),
                        "doneCnt"),
                t.id
                ))
                .from(t)
                .fetch()
                .get(0);

        if (res.getTotalCnt() != 0) res.setPercent( (res.getDoneCnt() * 100) / res.getTotalCnt());
        else res.setPercent(0);

        return res;
    }

    public Integer getLastTotalCnt(Integer userId, LocalDate startDate, LocalDate endDate) {
        QTodo t = QTodo.todo;

        List<Todo> ts = jpaQueryFactory.select(t)
                .from(t)
                .where(t.user.id.eq(userId))
                .fetch();

        int res = 0;
        for (Todo tss: ts) {
            if ((tss.getEndDate().isBefore(endDate) || tss.getEndDate().equals(endDate)) && (tss.getEndDate().isAfter(startDate) || tss.getEndDate().equals(startDate))) {
                res++;
            }
        }

//        Integer res = jpaQueryFactory.select(count(t.id))
//                .from(t)
//                .where(t.endDate.after(endDate).and(t.user.id.eq(userId)))
//                .fetch().get(0).intValue();
        return res;
    }

    public Integer getLastDoneCnt(Integer userId, LocalDate startDate, LocalDate endDate) {
        QTodo t = QTodo.todo;

        List<Todo> ts = jpaQueryFactory.select(t)
                .from(t)
                .where(t.user.id.eq(userId).and(t.isDone.eq(true)))
                .fetch();

        int res = 0;
        for (Todo tss: ts) {
            if ((tss.getEndDate().isBefore(endDate) || tss.getEndDate().equals(endDate)) && (tss.getEndDate().isAfter(startDate) || tss.getEndDate().equals(startDate))) {
                res++;
            }
        }

        return res;
    }
}
