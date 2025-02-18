package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryMapper {

    /**
     * 分类分页查询
     * @param name
     * @param type
     * @return
     */

    Page<Category> page(String name, Integer type);

    /**
     * 增加分类
     * @param category
     */

    @Insert("insert into category (type,name,sort,status,create_time,update_time,create_user,update_user) values (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addCategory(Category category);


    @Select("select * from category where id = #{id}")
    Category getCategoryById(Long id);


    /**
     * 更新分类信息
     * @param category
     */
    void updateCategory(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);


    List<Category> listByType(Integer type);

}
