package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.DuplicateUsernameException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;



    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public Employee addEmployee(EmployeeDTO employeeDTO) {
        //校验，用户名是否已经存在
        if(employeeMapper.getByUsername(employeeDTO.getUsername())!=null){
            throw new DuplicateUsernameException(employeeDTO.getUsername() + MessageConstant.ACCOUNT_EXIST);
        }

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(StatusConstant.ENABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //从本地线程中取出当前用户id
        Long currentId = BaseContext.getCurrentId();
        employee.setCreateUser(currentId);
        employee.setUpdateUser(currentId);
        employeeMapper.addEmployee(employee);
        return employee;
    }

    /**
     * 分页查询员工列表
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageResult page(String name, int page, int pageSize) {

        //开始分页
        PageHelper.startPage(page, pageSize);

        Page<Employee> employeePage = employeeMapper.page(name);

        long total = employeePage.getTotal();
        List<Employee> records = employeePage.getResult();



        return new PageResult(total,records);
    }

    /**
     * 启用，禁用员工账号
     * @param status
     * @param id
     */
    @Override
    public void changeStatus(Integer status, Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employee.setStatus(status);
        employeeMapper.updateEmployee(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeMapper.getById(id);
    }

    @Override
    public void edit(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.getById(employeeDTO.getId());
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(employeeDTO.getId());
        employeeMapper.updateEmployee(employee);
    }

}
