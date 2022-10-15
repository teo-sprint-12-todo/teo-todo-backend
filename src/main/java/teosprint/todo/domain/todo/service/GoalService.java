package teosprint.todo.domain.todo.service;

import teosprint.todo.domain.todo.data.dto.req.AddCategoryReq;
import teosprint.todo.domain.todo.data.dto.req.AddGoalReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateCategoryReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateGoalReq;
import teosprint.todo.domain.todo.data.dto.res.CategoryListRes;
import teosprint.todo.domain.todo.data.dto.res.GoalListRes;

import java.util.List;

public interface GoalService {
    public Integer addGoal(String email, AddGoalReq addGoalReq);
    public Integer updateGoal(String email, UpdateGoalReq updateGoalReq);
    public void deleteGoal(Integer id);
    public List<GoalListRes> getAllGoal(String email);
}
