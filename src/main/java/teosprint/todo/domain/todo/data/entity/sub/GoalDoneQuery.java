package teosprint.todo.domain.todo.data.entity.sub;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Subselect("select goal_id as id, count(goal_id) as done from todo where is_done = true group by goal_id")
@Synchronize("todo")
public class GoalDoneQuery {
    @Id
    private Integer id;

    @Column
    private Integer done;
}