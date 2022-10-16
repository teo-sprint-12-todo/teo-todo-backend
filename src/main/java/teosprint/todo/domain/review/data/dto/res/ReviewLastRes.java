package teosprint.todo.domain.review.data.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
public class ReviewLastRes {
    private Integer totalCnt;
    private Integer doneCnt;
    private Integer percent;

    public ReviewLastRes() {
    }

    public ReviewLastRes(Integer totalCnt, Integer doneCnt, Integer percent) {
        this.totalCnt = totalCnt;
        this.doneCnt = doneCnt;
        this.percent = percent;
    }

    public ReviewLastRes(Long totalCnt, Long doneCnt, Integer percent) {
        this.totalCnt = totalCnt.intValue();
        this.doneCnt = doneCnt.intValue();
        this.percent = percent;
    }
}