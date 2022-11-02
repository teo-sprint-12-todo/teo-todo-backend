package teosprint.todo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teosprint.todo.domain.todo.data.dto.req.AddGoalReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateGoalReq;
import teosprint.todo.domain.todo.data.dto.res.*;
import teosprint.todo.domain.todo.data.entity.Category;
import teosprint.todo.domain.todo.data.entity.Goal;
import teosprint.todo.domain.todo.repository.CategoryRepository;
import teosprint.todo.domain.todo.repository.GoalRepository;
import teosprint.todo.domain.todo.repository.GoalRepositorySupport;
import teosprint.todo.domain.todo.repository.TodoRepositorySupport;
import teosprint.todo.domain.user.data.entity.User;
import teosprint.todo.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService{
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final GoalRepository goalRepository;
    private final GoalRepositorySupport goalRepositorySupport;
    private final TodoRepositorySupport todoRepositorySupport;

    @Override
    public Integer addGoal(String email, AddGoalReq addGoalReq) {
        // 검증 조건 추가하기
        // User user = userRepository.findByEmail(email).get();
        Category category = categoryRepository.findById(addGoalReq.getCategoryId()).get();


        return goalRepository.save(Goal.builder()
                .name(addGoalReq.getName())
                .category(category)
                .endDate(addGoalReq.getEndDate())
                .build()).getId();
    }

    @Override
    public Integer updateGoal(String email, UpdateGoalReq updateGoalReq) {
        Category category = categoryRepository.findById(updateGoalReq.getCategoryId()).get();
        goalRepositorySupport.updateGoal(updateGoalReq.getId(), category, updateGoalReq.getName(), updateGoalReq.getEndDate());
        return updateGoalReq.getId();
    }

    @Override
    public void deleteGoal(Integer id) {
        todoRepositorySupport.removeGoalByGoalId(id);
        goalRepositorySupport.deleteById(id);
    }

    @Override
    public List<GoalListRes> getAllGoal(String email) {
        User user = userRepository.findByEmail(email).get();
        return goalRepositorySupport.getAllGoal(user.getId());
    }

    @Override
    public List<GoalStatListRes> getStatGoalList(String email, Boolean isEnd) {
        User user = userRepository.findByEmail(email).get();
        return goalRepositorySupport.getStatGoalList(user.getId(), isEnd);
    }

    @Override
    public GoalCurrStatRes getStatCurrGoal(String email, Integer goalId) {
        User user = userRepository.findByEmail(email).get();
        GoalStatListRes currGoal = goalRepositorySupport.getStatCurrGoal(user.getId(), goalId);
        List<TodoListRes> todos = todoRepositorySupport.getAllTodoByGoal(user.getId(), goalId);
        return GoalCurrStatRes.builder()
                .goalIid(currGoal.getGoalIid())
                .goalName(currGoal.getGoalName())
                .startDate(currGoal.getStartDate())
                .endDate(currGoal.getEndDate())
                .totalCnt(currGoal.getTotalCnt())
                .doneCnt(currGoal.getDoneCnt())
                .percent(currGoal.getPercent())
                .todoList(todos)
                .build();
    }
}
