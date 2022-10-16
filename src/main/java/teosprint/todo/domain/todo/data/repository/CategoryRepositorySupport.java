package teosprint.todo.domain.todo.data.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teosprint.todo.domain.todo.data.dto.res.CategoryListRes;
import teosprint.todo.domain.todo.data.entity.Category;
import teosprint.todo.domain.todo.data.entity.QCategory;

import java.util.*;

@Repository
public class CategoryRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CategoryRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Category.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<CategoryListRes> getAllCategory(Integer userId) {
        QCategory c = QCategory.category;

        return jpaQueryFactory.select(Projections.constructor(CategoryListRes.class, c.id, c.name))
                .from(c)
                .where(c.user.id.eq(userId))
                .fetch();
    }
}
