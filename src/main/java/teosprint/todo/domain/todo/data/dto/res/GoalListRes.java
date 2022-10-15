package teosprint.todo.domain.todo.data.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
public class GoalListRes {
    private Integer id;
    private Integer categoryId;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public GoalListRes() {
    }

    public GoalListRes(Integer id, Integer categoryId, String name, LocalDate endDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.endDate = endDate;
    }
}
