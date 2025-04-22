package org.qiaice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qiaice.Result;
import org.qiaice.dto.EmployeeLoginDTO;
import org.qiaice.service.EmployeeService;
import org.qiaice.vo.EmployeeLoginVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/employee", produces = "application/json; charset=utf-8")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(value = "/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO dto) {
        log.info("employee login dto: {}", dto);
        EmployeeLoginVO loginVO = employeeService.login(dto.getUname(), dto.getPasswd());
        return Result.ok("员工" + dto.getUname() + "登录成功", loginVO);
    }
}
