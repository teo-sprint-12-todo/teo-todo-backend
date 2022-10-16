package teosprint.todo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teosprint.todo.domain.todo.data.dto.req.AddCategoryReq;
import teosprint.todo.domain.todo.data.dto.res.CategoryListRes;
import teosprint.todo.domain.todo.data.dto.req.UpdateCategoryReq;
import teosprint.todo.domain.todo.data.entity.Category;
import teosprint.todo.domain.todo.data.repository.CategoryRepository;
import teosprint.todo.domain.todo.data.repository.CategoryRepositorySupport;
import teosprint.todo.domain.todo.data.repository.GoalRepositorySupport;
import teosprint.todo.domain.todo.data.repository.TodoRepositorySupport;
import teosprint.todo.domain.user.data.entity.User;
import teosprint.todo.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryRepositorySupport categoryRepositorySupport;
    private final TodoRepositorySupport todoRepositorySupport;
    private final GoalRepositorySupport goalRepositorySupport;

    @Override
    public Integer addCategory(String email, AddCategoryReq addCategoryReq) {
        User user = userRepository.findByEmail(email).get();

        return categoryRepository.save(Category.builder()
                .user(user)
                .name(addCategoryReq.getName())
                .build()).getId();
    }

    @Override
    public Integer updateCategory(String email, UpdateCategoryReq updateCategoryReq) {
        // 검증 프로세스 추가하기
        // User user = userRepository.findByEmail(email).get();

        return categoryRepository.updateCategory(updateCategoryReq.getId(), updateCategoryReq.getName());
    }

    @Override
    public void deleteCategory(Integer id) {
        todoRepositorySupport.removeCategoryByCategoryId(id);
        goalRepositorySupport.deleteByCategoryId(id);
        categoryRepositorySupport.deleteById(id);
    }

    @Override
    public List<CategoryListRes> getAllCategory(String email) {
        User user = userRepository.findByEmail(email).get();

        return categoryRepositorySupport.getAllCategory(user.getId());
    }


}
