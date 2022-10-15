package teosprint.todo.domain.todo.data.dto.req;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class AddGoalReq {
    private Integer categoryId;
    private String name;
    private LocalDate endDate;
}
