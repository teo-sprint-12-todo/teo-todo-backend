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
        LocalDate endDate = getLastEndDate(nowDate);
        LocalDate startDate = endDate.minusDays(6);


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

    private LocalDate getLastEndDate(LocalDate nowDate) {
        LocalDate date = nowDate.minusDays(1);

        while(!date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            date = date.minusDays(1);
        }

        return date;
    }
}