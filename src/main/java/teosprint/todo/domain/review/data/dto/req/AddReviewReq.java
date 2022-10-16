package teosprint.todo.domain.review.data.dto.req;

import lombok.Getter;

@Getter
public class AddReviewReq {
    private String periodType; // "WEEK", "MONTH", "YEAR"
    private String text;
}
