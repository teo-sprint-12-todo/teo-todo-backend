package teosprint.todo.domain.review.data.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ReviewListRes {
    private Integer id;
    private String periodType;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Integer totalCnt;
    private Integer doneCnt;
    private Integer percent;

    public ReviewListRes() {
    }

    @Builder
    public ReviewListRes(Integer id, String periodType, String text, LocalDate startDate, LocalDate endDate, Integer totalCnt, Integer doneCnt, Integer percent) {
        this.id = id;
        this.periodType = periodType;
        this.text = text;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCnt = totalCnt;
        this.doneCnt = doneCnt;
        this.percent = percent;
    }

    public ReviewListRes(Integer id, String periodType, String text, LocalDate startDate, LocalDate endDate, Long totalCnt, Long doneCnt, Integer percent) {
        this.id = id;
        this.periodType = periodType;
        this.text = text;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCnt = totalCnt.intValue();
        this.doneCnt = doneCnt.intValue();
        this.percent = percent;
    }
}