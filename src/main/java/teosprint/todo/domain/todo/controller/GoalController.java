package teosprint.todo.domain.todo.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teosprint.todo.domain.todo.data.dto.req.AddCategoryReq;
import teosprint.todo.domain.todo.data.dto.req.AddGoalReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateCategoryReq;
import teosprint.todo.domain.todo.data.dto.req.UpdateGoalReq;
import teosprint.todo.domain.todo.data.dto.res.CategoryListRes;
import teosprint.todo.domain.todo.data.dto.res.GoalCurrStatRes;
import teosprint.todo.domain.todo.data.dto.res.GoalListRes;
import teosprint.todo.domain.todo.data.dto.res.GoalStatListRes;
import teosprint.todo.domain.todo.service.CategoryService;
import teosprint.todo.domain.todo.service.GoalService;
import teosprint.todo.domain.user.filter.JwtTokenProvider;
import teosprint.todo.response.DefaultRes;
import teosprint.todo.response.StatusCode;

import java.util.List;

@Api(tags="goal", value = "목표 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/todo/goal")
public class GoalController {
    private final GoalService goalService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add")
    public ResponseEntity register(@RequestHeader("Authorization") String token, @RequestBody AddGoalReq addGoalReq) {
        Integer idx = goalService.addGoal(jwtTokenProvider.getUserEmail(token.substring(7)), addGoalReq);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "목표 추가 완료", idx), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestHeader("Authorization") String token, @RequestBody UpdateGoalReq updateGoalReq) {
        Integer idx = goalService.updateGoal(jwtTokenProvider.getUserEmail(token.substring(7)), updateGoalReq);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "목표 수정 완료", idx), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity update(@RequestHeader("Authorization") String token) {
        List<GoalListRes> goalList = goalService.getAllGoal(jwtTokenProvider.getUserEmail(token.substring(7)));

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "목표 목록 반환 완료", goalList), HttpStatus.OK);
    }

    @GetMapping("/stat/list/{isEnd}")
    public ResponseEntity statList(@RequestHeader("Authorization") String token, @PathVariable("isEnd") String isEndStr) {
        Boolean isEnd = Boolean.parseBoolean(isEndStr);
        List<GoalStatListRes> goalList = goalService.getStatGoalList(jwtTokenProvider.getUserEmail(token.substring(7)), isEnd);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "목표 통계 목록 반환 완료", goalList), HttpStatus.OK);
    }

    @GetMapping("/stat/id/{id}")
    public ResponseEntity statCurr(@RequestHeader("Authorization") String token, @PathVariable("id") String idStr) {
        Integer id = Integer.parseInt(idStr);

        GoalCurrStatRes goalStat = goalService.getStatCurrGoal(jwtTokenProvider.getUserEmail(token.substring(7)), id);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "목표 통계 반환 완료", goalStat), HttpStatus.OK);
    }

}
