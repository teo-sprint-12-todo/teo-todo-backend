package teosprint.todo.domain.todo.data.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryListRes {
    private Integer id;
    private String name;

    public CategoryListRes() {
    }

    public CategoryListRes(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
