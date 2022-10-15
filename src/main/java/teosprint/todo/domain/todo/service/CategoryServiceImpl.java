package teosprint.todo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import teosprint.todo.domain.todo.data.dto.AddCategoryReq;
import teosprint.todo.domain.todo.data.dto.CategoryListRes;
import teosprint.todo.domain.todo.data.dto.UpdateCategoryReq;
import teosprint.todo.domain.todo.data.entity.Category;
import teosprint.todo.domain.todo.repository.CategoryRepository;
import teosprint.todo.domain.user.data.entity.User;
import teosprint.todo.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryRepositorySupport categoryRepositorySupport;

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
    }

    @Override
    public List<CategoryListRes> getAllCategory(String email) {
        User user = userRepository.findByEmail(email).get();

        return categoryRepositorySupport.getAllCategory(user.getId());
    }


}
