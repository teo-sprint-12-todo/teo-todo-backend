package teosprint.todo.domain.todo.data.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class TodoListRes {
    private Integer id;
    private Integer categoryId;
    private String categoryName;
    private Integer goalId;
    private String goalName;
    private Integer importance;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public TodoListRes() {
    }

    public TodoListRes(Integer id, Integer categoryId, String categoryName, Integer goalId, String goalName, Integer importance, String text, LocalDateTime startDate, LocalDate endDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.goalId = goalId;
        this.goalName = goalName;
        this.importance = importance;
        this.text = text;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
