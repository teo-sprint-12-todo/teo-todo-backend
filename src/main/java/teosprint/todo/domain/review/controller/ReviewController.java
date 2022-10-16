package teosprint.todo.domain.review.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teosprint.todo.domain.review.data.dto.req.AddReviewReq;
import teosprint.todo.domain.review.data.dto.res.ReviewListRes;
import teosprint.todo.domain.review.service.ReviewService;
import teosprint.todo.domain.todo.data.dto.req.AddTodoReq;
import teosprint.todo.domain.todo.data.dto.req.CheckTodoReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateTodoReq;
import teosprint.todo.domain.todo.data.dto.res.TodoListRes;
import teosprint.todo.domain.todo.service.TodoService;
import teosprint.todo.domain.user.filter.JwtTokenProvider;
import teosprint.todo.response.DefaultRes;
import teosprint.todo.response.StatusCode;

import java.util.List;
import java.util.Optional;

@Api(tags="review", value = "회고 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/add/possible/{periodType}")
    public ResponseEntity getPossible(@RequestHeader("Authorization") String token, @PathVariable("periodType") String periodType) {
        Boolean exist = reviewService.getPossible(jwtTokenProvider.getUserEmail(token.substring(7)), periodType);

        return exist? new ResponseEntity(DefaultRes.res(StatusCode.OK, "이미 회고 작성 완료", false), HttpStatus.OK) :
                new ResponseEntity(DefaultRes.res(StatusCode.OK, "작성된 회고 없음", true), HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity register(@RequestHeader("Authorization") String token, @RequestBody AddReviewReq addReviewReq) {
        Integer idx = reviewService.addReview(jwtTokenProvider.getUserEmail(token.substring(7)), addReviewReq);

        return idx != null? new ResponseEntity(DefaultRes.res(StatusCode.OK, "회고 추가 완료", idx), HttpStatus.OK) :
                new ResponseEntity(DefaultRes.res(StatusCode.OK, "회고 추가 불가", null), HttpStatus.OK);
    }

    @GetMapping("/list/{periodType}")
    public ResponseEntity getReviewList(@RequestHeader("Authorization") String token, @PathVariable("periodType") String periodType) {
        List<ReviewListRes> reviews = reviewService.getReviewList(jwtTokenProvider.getUserEmail(token.substring(7)), periodType);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "회고 목록 반환 완료", reviews), HttpStatus.OK);
    }
}
