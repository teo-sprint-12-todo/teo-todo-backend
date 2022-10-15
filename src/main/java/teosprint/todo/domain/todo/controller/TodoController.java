package teosprint.todo.domain.todo.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teosprint.todo.domain.user.data.dto.SignUpDto;
import teosprint.todo.response.DefaultRes;
import teosprint.todo.response.StatusCode;

@Api(tags="user", value = "TODO 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/todo/todo")
public class TodoController {

    @PostMapping("/add")
    public ResponseEntity register(@RequestBody SignUpDto signUpDto) {

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "카테고리 추가 완료"), HttpStatus.OK);
    }
}
