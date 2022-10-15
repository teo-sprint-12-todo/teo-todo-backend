package teosprint.todo.domain.todo.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
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
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate endDate;

    @CreatedDate
    @Column
    private LocalDateTime createdAt;

    @Builder
    public Goal(Category category, String name, LocalDate endDate) {
        this.category = category;
        this.name = name;
        this.endDate = endDate;
    }
}
