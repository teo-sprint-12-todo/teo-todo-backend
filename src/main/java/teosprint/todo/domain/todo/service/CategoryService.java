package teosprint.todo.domain.todo.service;

import teosprint.todo.domain.todo.data.dto.req.AddCategoryReq;
import teosprint.todo.domain.todo.data.dto.res.CategoryListRes;
import teosprint.todo.domain.todo.data.dto.req.UpdateCategoryReq;

import java.util.*;

public interface CategoryService {
    public Integer addCategory(String email, AddCategoryReq addCategoryReq);
    public Integer updateCategory(String email, UpdateCategoryReq updateCategoryReq);
    public void deleteCategory(Integer id);
    public List<CategoryListRes> getAllCategory(String email);
}
