package org.qiaice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.qiaice.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
