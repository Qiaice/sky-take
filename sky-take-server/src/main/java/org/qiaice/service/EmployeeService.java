package org.qiaice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.qiaice.entity.Employee;
import org.qiaice.vo.EmployeeLoginVO;

public interface EmployeeService extends IService<Employee> {

    EmployeeLoginVO login(String uname, String passwd);
}
