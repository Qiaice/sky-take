package org.qiaice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.qiaice.entity.Employee;
import org.qiaice.enums.Status;
import org.qiaice.exception.AccountLockedException;
import org.qiaice.exception.AccountNotFoundException;
import org.qiaice.exception.PasswdErrorException;
import org.qiaice.mapper.EmployeeMapper;
import org.qiaice.service.EmployeeService;
import org.qiaice.utils.TokenUtil;
import org.qiaice.vo.EmployeeLoginVO;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public EmployeeLoginVO login(String uname, String passwd) {
        Employee employee = lambdaQuery().eq(Employee::getUname, uname).one();
        if (employee == null)
            throw new AccountNotFoundException("员工账户" + uname + "未注册");
        passwd = DigestUtils.md5DigestAsHex(passwd.getBytes(StandardCharsets.UTF_8));
        if (!passwd.equals(employee.getPasswd()))
            throw new PasswdErrorException("员工名或密码错误");
        if (Status.DISABLED.getCode().equals(employee.getStatus()))
            throw new AccountLockedException("员工账户" + uname + "已被禁用");
        return EmployeeLoginVO.builder()
                .eid(employee.getEid())
                .name(employee.getName())
                .uname(uname)
                .token(TokenUtil.createToken(Map.of(
                        "eid", employee.getEid(),
                        "uname", employee.getUname(),
                        "name", employee.getName()
                ))).build();
    }
}
