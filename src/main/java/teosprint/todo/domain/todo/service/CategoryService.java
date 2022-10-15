package teosprint.todo.domain.todo.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import teosprint.todo.domain.todo.data.dto.AddCategoryReq;
import teosprint.todo.domain.todo.data.dto.CategoryListRes;
import teosprint.todo.domain.todo.data.dto.UpdateCategoryReq;

import java.util.*;

public interface CategoryService {
    public Integer addCategory(String email, AddCategoryReq addCategoryReq);
    public Integer updateCategory(String email, UpdateCategoryReq updateCategoryReq);
    public void deleteCategory(Integer id);
    public List<CategoryListRes> getAllCategory(String email);
}
