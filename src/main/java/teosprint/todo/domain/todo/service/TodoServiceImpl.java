package teosprint.todo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teosprint.todo.domain.todo.data.dto.req.AddTodoReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateTodoReq;
import teosprint.todo.domain.todo.data.dto.res.TodoListRes;
import teosprint.todo.domain.todo.data.entity.*;
import teosprint.todo.domain.todo.repository.CategoryRepository;
import teosprint.todo.domain.todo.repository.GoalRepository;
import teosprint.todo.domain.todo.repository.TodoRepository;
import teosprint.todo.domain.todo.repository.TodoRepositorySupport;
import teosprint.todo.domain.user.data.entity.User;
import teosprint.todo.domain.user.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;
    private final GoalRepository goalRepository;
    private final TodoRepositorySupport todoRepositorySupport;

    @Override
    public Integer addTodo(String email, AddTodoReq addTodoReq) {
        User user = userRepository.findByEmail(email).get();
        Category category = addTodoReq.getCategoryId() == -1? null : categoryRepository.findById(addTodoReq.getCategoryId()).get();
        Goal goal = addTodoReq.getGoalId() == -1? null : goalRepository.findById(addTodoReq.getGoalId()).get();
        if (goal != null && goal.getCategory().getId() != category.getId()) return null;

        Todo todo = todoRepository.save(Todo.builder()
                .user(user)
                .category(category)
                .goal(goal)
                .text(addTodoReq.getText())
                .importance(addTodoReq.getImportance())
                .endDate(addTodoReq.getEndDate())
                .isDone(false)
                .build());

        return todo.getId();
    }

    @Override
    public Integer updateTodo(String email, UpdateTodoReq updateTodoReq) {
        // User user = userRepository.findByEmail(email).get();
        Category category = updateTodoReq.getCategoryId() == -1? null : categoryRepository.findById(updateTodoReq.getCategoryId()).get();
        Goal goal = updateTodoReq.getGoalId() == -1? null : goalRepository.findById(updateTodoReq.getGoalId()).get();

        todoRepositorySupport.updateTodo(updateTodoReq.getId(), updateTodoReq.getText(), updateTodoReq.getImportance(), updateTodoReq.getEndDate(), category, goal);

        return updateTodoReq.getId();
    }

    @Override
    public void deleteTodo(Integer id) {
        todoRepositorySupport.deleteById(id);
    }

    @Override
    public Boolean checkTodo(Integer id) {
        Boolean toThis = !todoRepository.findById(id).get().getIsDone();
        todoRepositorySupport.checkTodo(id, toThis);
        return toThis;
    }

    @Override
    public List<TodoListRes> getAllTodo(String email, Integer categoryId) {
        User user = userRepository.findByEmail(email).get();
        return todoRepositorySupport.getMainTodoList(user.getId(), categoryId);
    }

    @Override
    public List<TodoListRes> getCalendarTodoList(String email, Integer year, Integer month) {
        User user = userRepository.findByEmail(email).get();
        return todoRepositorySupport.getTodoListOfMonth(user.getId(), year, month);
    }

    @Override
    public List<TodoListRes> getDayTodoList(String email, Integer year, Integer month, Integer day) {
        User user = userRepository.findByEmail(email).get();
        return todoRepositorySupport.getTodoListOfDay(user.getId(), year, month, day);
    }
}
