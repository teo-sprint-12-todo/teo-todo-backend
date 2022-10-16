package teosprint.todo.domain.review.service;

import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import teosprint.todo.domain.review.data.dto.req.AddReviewReq;
import teosprint.todo.domain.review.data.dto.res.ReviewListRes;
import teosprint.todo.domain.review.data.entity.Review;
import teosprint.todo.domain.review.repository.ReviewRepository;
import teosprint.todo.domain.review.repository.ReviewRepositorySupport;
import teosprint.todo.domain.user.data.entity.User;
import teosprint.todo.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewRepositorySupport reviewRepositorySupport;

    @Override
    public Integer addReview(String email, AddReviewReq addReviewReq) {
        User user = userRepository.findByEmail(email).get();
        LocalDate nowDate = LocalDate.now();
        LocalDate endDate = getLastEndDate(nowDate, addReviewReq.getPeriodType());
        LocalDate startDate = getLastStartDate(endDate, addReviewReq.getPeriodType());

        if (getPossible(email, addReviewReq.getPeriodType())) return null;


        return reviewRepository.save(Review.builder()
                .user(user)
                .text(addReviewReq.getText())
                .periodType(addReviewReq.getPeriodType())
                .startDate(startDate)
                .endDate(endDate)
                .build()).getId();
    }

    @Override
    public List<ReviewListRes> getReviewList(String email, String periodType) {
        User user = userRepository.findByEmail(email).get();

        return reviewRepositorySupport.getAllReviewByPeriodType(user.getId(), periodType);
    }

    @Override
    public Boolean getPossible(String email, String periodType) {
        User user = userRepository.findByEmail(email).get();
        LocalDate nowDate = LocalDate.now();
        LocalDate endDate = getLastEndDate(nowDate, periodType);
        LocalDate startDate = getLastStartDate(endDate, periodType);

        return reviewRepositorySupport.existByUserAndNowDate(user.getId(), startDate, endDate) != null;
    }

    @Override
    public ReviewListRes getLastStat(String email, String periodType) {
        User user = userRepository.findByEmail(email).get();
        LocalDate nowDate = LocalDate.now();
        LocalDate endDate = getLastEndDate(nowDate, periodType);
        LocalDate startDate = getLastStartDate(endDate, periodType);

        // ReviewLastRes stat = reviewRepositorySupport.getLastTodoStat(user.getId(), startDate, endDate);
        Integer totalCnt = reviewRepositorySupport.getLastTotalCnt(user.getId(), startDate, endDate);
        Integer doneCnt = reviewRepositorySupport.getLastDoneCnt(user.getId(), startDate, endDate);
        Integer percent = totalCnt == 0? 0 : (doneCnt * 100) / totalCnt;

        System.out.println(reviewRepositorySupport.getLastTotalCnt(user.getId(), startDate, endDate) + " =============" + startDate + " " + endDate);
        ReviewListRes res = ReviewListRes.builder()
                .startDate(startDate)
                .endDate(endDate)
                .periodType(periodType)
                .totalCnt(totalCnt)
                .doneCnt(doneCnt)
                .percent(percent).build();

        Review currReview = reviewRepositorySupport.existByUserAndNowDate(user.getId(), startDate, endDate);
        if (currReview != null) {
            res.setId(currReview.getId());
            res.setText(currReview.getText());
        }
        else {
            res.setId(null);
            res.setText(null);
        }

        return res;
    }

    private LocalDate getLastEndDate(LocalDate nowDate, String periodType) {
        if (periodType.equals("WEEK") || periodType.equals("week")) {
            LocalDate date = nowDate.minusDays(1);

            while(!date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                date = date.minusDays(1);
            }

            return date;
        }
        else if (periodType.equals("MONTH") || periodType.equals("month")) {
            LocalDate date = nowDate.minusMonths(1);
            return LocalDate.of(date.getYear(), date.getMonth(), date.lengthOfMonth());
        }
        else {
            LocalDate date = nowDate.minusYears(1);
            return LocalDate.of(date.getYear(), 12, 31);
        }
    }

    private LocalDate getLastStartDate(LocalDate endDate, String periodType) {
        LocalDate date = endDate;

        if (periodType.equals("WEEK") || periodType.equals("week")) { date = date.minusDays(6);}
        else if (periodType.equals("MONTH") || periodType.equals("month")) { return LocalDate.of(date.getYear(), date.getMonth(), 1); }
        else { return LocalDate.of(date.getYear(), 1, 1); }

        return date;
    }
}