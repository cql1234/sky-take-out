package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

/**
 * 分类管理业务层
 */
public interface CategoryService {

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);


    void addCategory(CategoryDTO categoryDTO);

    /**
     * 启用，禁用分类
     * @param status
     * @param id
     */
    void changeStatus(Integer status, Long id);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void editCategory(CategoryDTO categoryDTO);

    void deleteById(Long id);

    List<Category> listByType(Integer type);

}
