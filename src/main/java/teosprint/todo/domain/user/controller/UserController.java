package teosprint.todo.domain.user.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teosprint.todo.domain.user.data.dto.SignInDto;
import teosprint.todo.domain.user.data.dto.SignUpDto;
import teosprint.todo.domain.user.service.UserService;
import teosprint.todo.response.DefaultRes;
import teosprint.todo.response.StatusCode;

@Api(tags="user", value = "사용자 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity register(@RequestBody SignUpDto signUpDto) {
        if (!signUpDto.getPassword().equals(signUpDto.getPasswordCheck())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "비밀번호 확인과 일치하지 않습니다", null), HttpStatus.OK);
        }

        Integer idx = userService.signUp(signUpDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "회원 가입 완료", idx), HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody SignInDto signInDto) {
        String res = userService.signIn(signInDto);

        return res != null ? new ResponseEntity(DefaultRes.res(StatusCode.OK, "로그인 완료", res), HttpStatus.OK) :
                new ResponseEntity(DefaultRes.res(StatusCode.OK, "잘못된 로그인 정보", null), HttpStatus.OK) ;
    }

    // 토큰 테스트
    @GetMapping("/token-test")
    public ResponseEntity tokenTest(@RequestHeader("Authorization") String token) {
        String email = userService.getEmailByToken(token);

        return email != null ? new ResponseEntity(DefaultRes.res(StatusCode.OK, "토큰으로 이메일 조회 완료", email), HttpStatus.OK) :
                new ResponseEntity(DefaultRes.res(StatusCode.OK, "잘못된 토큰", null), HttpStatus.OK) ;
    }

    // 중복 확인
    @GetMapping("/check/{email}")
    public ResponseEntity checkEmail(@PathVariable("email") String email) {
        boolean exists = userService.checkEmail(email);
        String explain = exists ? "사용 불가능한 이메일" : "사용 가능한 이메일";

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, explain, !exists), HttpStatus.OK);
    }

    // 나의 티어 (관련 프로세스 추가 시 수정 필요)
    @GetMapping("/my/tier")
    public ResponseEntity checkTier(@RequestHeader("Authorization") String token) {

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "티어 반환 완료", 1), HttpStatus.OK);
    }

}
