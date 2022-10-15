package teosprint.todo.domain.todo.service;

import teosprint.todo.domain.todo.data.dto.req.AddTodoReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateTodoReq;
import teosprint.todo.domain.todo.data.dto.res.TodoListRes;

import java.util.List;

public interface TodoService {
    public Integer addTodo(String email, AddTodoReq addTodoReq);
    public Integer updateTodo(String email, UpdateTodoReq updateTodoReq);
    public void deleteTodo(Integer id);
    public Boolean checkTodo(Integer id);
    public List<TodoListRes> getAllTodo(String email, Integer categoryId);
    public List<TodoListRes> getCalendarTodoList(String email, Integer year, Integer month);
    public List<TodoListRes> getDayTodoList(String email, Integer year, Integer month, Integer day);
}
