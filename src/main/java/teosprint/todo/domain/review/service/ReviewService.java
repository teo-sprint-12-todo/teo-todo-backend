package teosprint.todo.domain.review.service;

import teosprint.todo.domain.review.data.dto.req.AddReviewReq;
import teosprint.todo.domain.review.data.dto.res.ReviewListRes;
import teosprint.todo.domain.todo.data.dto.req.AddCategoryReq;

import java.util.*;

public interface ReviewService {
    public Integer addReview(String email, AddReviewReq addReviewReq);
    public List<ReviewListRes> getReviewList(String email, String periodType);
    public Boolean getPossible(String email, String periodType);
}
