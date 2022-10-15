package teosprint.todo.domain.todo.data.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class GoalCurrStatRes {
    private Integer goalIid;
    private String goalName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Integer totalCnt;
    private Integer doneCnt;
    private Integer percent;
    private List<TodoListRes> todoList;

    public GoalCurrStatRes() {
    }

    @Builder
    public GoalCurrStatRes(Integer goalIid, String goalName, LocalDateTime startDate, LocalDate endDate, Integer totalCnt, Integer doneCnt, Integer percent, List<TodoListRes> todoList) {
        this.goalIid = goalIid;
        this.goalName = goalName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCnt = totalCnt;
        this.doneCnt = doneCnt;
        this.percent = percent;
        this.todoList = todoList;
    }
}
