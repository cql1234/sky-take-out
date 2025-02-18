package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
@Repository
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 增加员工
     */
    void addEmployee(Employee employee);

    /**
     * 员工分页查询
     * @param name
     * @return
     */
    Page<Employee> page(String name);

    @Update("update employee set status = #{status} where id = #{id}")
    void changeStatus(Integer status, Long id);

    /**
     * 根据id查询员工
     * @return
     */
    @Select("select * from Employee where id = #{id}")
    Employee getById(Long id);


    /**
     * 更新员工信息
     * @param employee
     */
    void updateEmployee(Employee employee);



}
