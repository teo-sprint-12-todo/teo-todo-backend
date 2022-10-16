package teosprint.todo.domain.todo.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import teosprint.todo.domain.user.data.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EnableJpaAuditing
@SpringBootApplication
@EntityListeners(AuditingEntityListener.class)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Goal goal;

    @Column(nullable = false, length = 200)
    private String text;

    @Column(nullable = false)
    private Integer importance;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean isDone;

    @CreatedDate
    @Column
    private LocalDateTime createdAt;

    @Builder
    public Todo(User user, Category category, Goal goal, String text, Integer importance, LocalDate endDate, Boolean isDone) {
        this.user = user;
        this.category = category;
        this.goal = goal;
        this.text = text;
        this.importance = importance;
        this.endDate = endDate;
        this.isDone = isDone;
    }
}
