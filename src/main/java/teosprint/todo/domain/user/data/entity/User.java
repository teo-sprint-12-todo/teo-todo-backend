package teosprint.todo.domain.user.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Contact;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EnableJpaAuditing
@SpringBootApplication
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 200, unique = true)
    private String password;

    @Column(nullable = false)
    private Integer tier;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @CreatedDate
    @Column
    private LocalDateTime createdAt;

    @Builder
    public User(String email, String password, Integer tier, List<String> roles) {
        this.email = email;
        this.password = password;
        this.tier = tier;
        this.roles = roles;
    }
}
