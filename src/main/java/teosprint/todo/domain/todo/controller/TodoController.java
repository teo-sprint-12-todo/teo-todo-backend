package teosprint.todo.domain.todo.controller;

import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teosprint.todo.domain.todo.data.dto.req.AddTodoReq;
import teosprint.todo.domain.todo.data.dto.req.CheckTodoReq;
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
import java.util.Optional;

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
    public ResponseEntity mainList(@RequestHeader("Authorization") String token,
                                 @RequestParam("category") Optional<String> categoryStr) {
        String str = categoryStr.orElse("-1");
        Integer categoryNum = Integer.parseInt(str);

        List<TodoListRes> todoList = todoService.getAllTodo(jwtTokenProvider.getUserEmail(token.substring(7)), categoryNum);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "TODO 목록 반환 완료", todoList), HttpStatus.OK);
    }

    @GetMapping("/list/calendar/{year}/{month}")
    public ResponseEntity listOfMonth(@RequestHeader("Authorization") String token, @PathVariable("year") String yearStr, @PathVariable("month") String monthStr) {
        Integer year = Integer.parseInt(yearStr);
        Integer month = Integer.parseInt(monthStr);

        List<TodoListRes> todoList = todoService.getCalendarTodoList(jwtTokenProvider.getUserEmail(token.substring(7)), year, month);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "TODO 목록 반환 완료", todoList), HttpStatus.OK);
    }

    @GetMapping("/list/day/{year}/{month}/{day}")
    public ResponseEntity listOfDay(@RequestHeader("Authorization") String token, @PathVariable("year") String yearStr, @PathVariable("month") String monthStr, @PathVariable("day") String dayStr) {
        Integer year = Integer.parseInt(yearStr);
        Integer month = Integer.parseInt(monthStr);
        Integer day = Integer.parseInt(dayStr);

        List<TodoListRes> todoList = todoService.getDayTodoList(jwtTokenProvider.getUserEmail(token.substring(7)), year, month, day);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "TODO 목록 반환 완료", todoList), HttpStatus.OK);
    }


    @PostMapping("/check")
    public ResponseEntity check(@RequestBody CheckTodoReq checkTodoReq) {
        Boolean result = todoService.checkTodo(checkTodoReq.getId());

        return result ? new ResponseEntity(DefaultRes.res(StatusCode.OK, "True로 변경 완료", result), HttpStatus.OK)
                : new ResponseEntity(DefaultRes.res(StatusCode.OK, "False로 변경 완료", result), HttpStatus.OK);
    }
}
