package teosprint.todo.domain.review.service;

import teosprint.todo.domain.todo.data.dto.req.AddCategoryReq;

public interface ReviewService {
    public Integer addReview(String email, AddCategoryReq addCategoryReq);
}
