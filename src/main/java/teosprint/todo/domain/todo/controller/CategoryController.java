package teosprint.todo.domain.todo.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teosprint.todo.domain.todo.data.dto.req.AddCategoryReq;
import teosprint.todo.domain.todo.data.dto.res.CategoryListRes;
import teosprint.todo.domain.todo.data.dto.req.UpdateCategoryReq;
import teosprint.todo.domain.todo.service.CategoryService;
import teosprint.todo.domain.user.filter.JwtTokenProvider;
import teosprint.todo.response.DefaultRes;
import teosprint.todo.response.StatusCode;

import java.util.*;

@Api(tags="category", value = "카테고리 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/todo/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add")
    public ResponseEntity register(@RequestHeader("Authorization") String token, @RequestBody AddCategoryReq addCategoryReq) {
        Integer idx = categoryService.addCategory(jwtTokenProvider.getUserEmail(token.substring(7)), addCategoryReq);

        return idx != null ? new ResponseEntity(DefaultRes.res(StatusCode.OK, "카테고리 추가 완료", idx), HttpStatus.OK) :
                new ResponseEntity(DefaultRes.res(StatusCode.OK, "문자열 잘못된 요청", idx), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestHeader("Authorization") String token, @RequestBody UpdateCategoryReq updateCategoryReq) {
        Integer idx = categoryService.updateCategory(jwtTokenProvider.getUserEmail(token.substring(7)), updateCategoryReq);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "카테고리 수정 완료", idx), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token,  @PathVariable("id") String idStr) {
        categoryService.deleteCategory(Integer.parseInt(idStr));

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "카테고리 삭제 완료", idStr), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity update(@RequestHeader("Authorization") String token) {
        List<CategoryListRes> categoryList = categoryService.getAllCategory(jwtTokenProvider.getUserEmail(token.substring(7)));

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "카테고리 목록 반환 완료", categoryList), HttpStatus.OK);
    }

}
