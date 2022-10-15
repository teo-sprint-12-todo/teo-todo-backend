package teosprint.todo.domain.todo.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teosprint.todo.domain.todo.data.dto.req.AddTodoReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateGoalReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateTodoReq;
import teosprint.todo.domain.todo.data.dto.res.GoalListRes;
import teosprint.todo.domain.todo.data.dto.res.TodoListRes;
import teosprint.todo.domain.todo.service.TodoService;
import teosprint.todo.domain.user.data.dto.SignUpDto;
import teosprint.todo.domain.user.filter.JwtTokenProvider;
import teosprint.todo.response.DefaultRes;
import teosprint.todo.response.StatusCode;

import java.util.List;

@Api(tags="todo", value = "TODO 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/todo/todo")
public class TodoController {
    private final TodoService todoService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add")
    public ResponseEntity register(@RequestHeader("Authorization") String token, @RequestBody AddTodoReq addTodoReq) {
        Integer idx = todoService.addTodo(jwtTokenProvider.getUserEmail(token.substring(7)), addTodoReq);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "TODO 추가 완료", idx), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestHeader("Authorization") String token, @RequestBody UpdateTodoReq updateTodoReq) {
        Integer idx = todoService.updateTodo(jwtTokenProvider.getUserEmail(token.substring(7)), updateTodoReq);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "TODO 수정 완료", idx), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity update(@RequestHeader("Authorization") String token) {
        List<TodoListRes> todoList = todoService.getAllTodo(jwtTokenProvider.getUserEmail(token.substring(7)));

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "TODO 목록 반환 완료", todoList), HttpStatus.OK);
    }
}
