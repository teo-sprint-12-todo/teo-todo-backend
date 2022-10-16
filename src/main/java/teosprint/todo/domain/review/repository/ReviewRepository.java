package teosprint.todo.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teosprint.todo.domain.review.data.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review save(Review review);
}
